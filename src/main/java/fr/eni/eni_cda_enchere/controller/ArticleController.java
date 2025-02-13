package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.*;
import fr.eni.eni_cda_enchere.bo.*;
import fr.eni.eni_cda_enchere.bo.custom.HandleSellRequest;
import fr.eni.eni_cda_enchere.exceptions.BusinessException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Controller
@RequestMapping("/articles")
@SessionAttributes({"sessionUser"})
public class ArticleController {
    private final UtilisateurService utilisateurService;
    private final AdresseService adresseService;
    private final ArticleService articleService;
    private final EnchereService enchereService;
    private final CategorieService categorieService;

    public ArticleController(UtilisateurService utilisateurService,
                             AdresseService adresseService,
                             ArticleService articleService,
                             EnchereService enchereService, CategorieService categorieService) {
        this.utilisateurService = utilisateurService;
        this.adresseService = adresseService;
        this.articleService = articleService;
        this.enchereService = enchereService;
        this.categorieService = categorieService;
    }

    @GetMapping("/detail_enchere/{noArticle}")
    public String detailArticle(
            @PathVariable int noArticle,
            @ModelAttribute Enchere enchere,
            @ModelAttribute ArticleAVendre article,
            Model model) {

        ArticleAVendre a = articleService.getArticleAVendre(noArticle);

        if(a == null) {
            return "redirect:/";
        } else {
            setModelAttributesDetailEnchere(model, noArticle, a);
            return "article/view-detail-enchere";
        }
    }

    @Transactional(rollbackFor = BusinessException.class)
    @PostMapping("/bid/{noArticle}")
    public String bidArticle(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable int noArticle,
            @ModelAttribute("enchere") Enchere enchere,
            BindingResult bindingResult,
            Model model
    ){
        String connected_username = utilisateurService.getConnectedUsername();
        Optional<Utilisateur> connected_user = utilisateurService.findByPseudo(connected_username);
        ArticleAVendre a = articleService.getArticleAVendre(noArticle);
        int ancien_montant = enchereService.getMeilleurPrix(noArticle);

        LocalDateTime now = LocalDateTime.now();
        enchere.setArticleAVendre(a);
        enchere.setAcquereur(connected_user);
        enchere.setDate(now);

        if(bindingResult.hasErrors()) {
            return "article/view-detail-enchere";
        } else {
            if(connected_user.get().getCredit() >= enchere.getMontant()){
                connected_user.get().setCredit(connected_user.get().getCredit() - enchere.getMontant());
                try{
                    enchereService.createEnchere(enchere, a.getNo_article(), ancien_montant);
                    utilisateurService.updateUser(connected_user.get());
                    System.out.println("Enchère créée : " + enchere);
                    return "redirect:/articles/detail_enchere/" + noArticle;
                } catch (BusinessException be){
                    be.getClefsExternalisations().forEach(key -> {
                        ObjectError err = new ObjectError("globalError", key);
                        bindingResult.addError(err);
                    });
                    setModelAttributesDetailEnchere(model, noArticle, a);
                    model.addAttribute("errors", be.getClefsExternalisations());
                    return "article/view-detail-enchere";
                }
            } else {
                return "redirect:/articles/detail_enchere/" + noArticle + "?echecEnchere=true";
            }
        }
    }

    @PostMapping("/bid/handle-sell")
    public ResponseEntity<Map<String, String>> handleSell(
            @RequestBody HandleSellRequest hsr
    ) {
        Utilisateur vendeur = utilisateurService.findByPseudo(hsr.getVendeur()).get();
        ArticleAVendre article = articleService.getArticleAVendre(hsr.getNo_article());
        int prix = hsr.getPrix();

        vendeur.setCredit((vendeur.getCredit()) + prix);
        utilisateurService.updateUser(vendeur);
        article.setStatut_enchere(3);
        article.setPrix_vente(prix);
        articleService.updateArticleAVendre(article);

        Map<String, String> response = new HashMap<>();
        response.put("message" , "success");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/creer")
    public String creerArticle(
            Model model,
            @AuthenticationPrincipal
            UserDetails userDetails
    ) {
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
        ArticleAVendre article = new ArticleAVendre();
        article.setRetrait(utilisateur.getAdresse());
        article.setVendeur(utilisateur);

        List<Categorie> categorieList = categorieService.findAllCategories();

        List<Adresse> adressesList = adresseService.getEniAdresses();
        adressesList.add(0, utilisateur.getAdresse());

        model.addAttribute("article", article);
        model.addAttribute("categorieList", categorieList);
        model.addAttribute("adressesList", adressesList);
        return "article/view-article-creation";
    }

    @PostMapping("/creer")
    public String enregistrerArticle(
            @Valid @ModelAttribute("article") ArticleAVendre article,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model, Locale locale) {

        if (bindingResult.hasErrors()) {
            System.out.println("Erreur = " + bindingResult.getAllErrors());
            Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();

            article.setRetrait(utilisateur.getAdresse());
            article.setVendeur(utilisateur);

            List<Categorie> categorieList = categorieService.findAllCategories();

            List<Adresse> adressesList = adresseService.getEniAdresses();
            adressesList.add(0, utilisateur.getAdresse());

            model.addAttribute("article", article);
            model.addAttribute("categorieList", categorieList);
            model.addAttribute("adressesList", adressesList);
            return "article/view-article-creation";
        }

            if(article.getDate_debut_encheres().isEqual(LocalDate.now())){
                article.setStatut_enchere(1);
            }

            // Récupère l'utilisateur connecté
            Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();
            article.setVendeur(utilisateur);

            // Crée l'article dans la base de données
            try {
                int no_Article = articleService.createArticleAVendre(article);
                return "redirect:/articles/detail_enchere/" + no_Article;
            } catch (BusinessException be) {
                be.getClefsExternalisations().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", ResourceBundle.getBundle("messages", locale).getString(key));
                    bindingResult.addError(error);
                });
                System.out.println("Erreur = " + bindingResult.getAllErrors());
                article.setRetrait(utilisateur.getAdresse());
                article.setVendeur(utilisateur);

                List<Categorie> categorieList = categorieService.findAllCategories();

                List<Adresse> adressesList = adresseService.getEniAdresses();
                adressesList.add(0, utilisateur.getAdresse());

                model.addAttribute("article", article);
                model.addAttribute("categorieList", categorieList);
                model.addAttribute("adressesList", adressesList);
                return "article/view-article-creation";
            }
        }



    @GetMapping({"/modifier/", "/modifier/{idArticle}"})
    public String modifierArticle(
            @PathVariable int idArticle,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        ArticleAVendre articleAVendre = articleService.getArticleAVendre(idArticle);
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();

        if(!utilisateur.getPseudo().equals(articleAVendre.getVendeur().getPseudo())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à modifier cet article.");
        }


        if(articleAVendre.getStatut_enchere() != 0){
            return "redirect:/utilisateurs/myProfile?echecModifVente=true";
        } else {
            List<Adresse> adressesList = adresseService.getEniAdresses();
            adressesList.add(0, articleAVendre.getRetrait());

            List<Categorie> categorieList = categorieService.findAllCategories();

            model.addAttribute("article", articleAVendre);
            model.addAttribute("adressesList", adressesList);
            model.addAttribute("categorieList", categorieList);

            return "article/view-article-creation";
        }


    }

    @PostMapping({"/modifier/", "/modifier/{idArticle}"})
    public String enregistrerModificationsArticle(
            @PathVariable int idArticle,
            @Valid @ModelAttribute("article") ArticleAVendre article,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            ArticleAVendre articleAVendre = articleService.getArticleAVendre(idArticle);

            List<Adresse> adressesList = adresseService.getEniAdresses();
            adressesList.add(0, articleAVendre.getRetrait());

            List<Categorie> categorieList = categorieService.findAllCategories();

            model.addAttribute("article", articleAVendre);
            model.addAttribute("adressesList", adressesList);
            model.addAttribute("categorieList", categorieList);
            return "article/view-article-creation";
        }

        // Récupère l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.findByPseudo(userDetails.getUsername()).get();

        if(!utilisateur.getPseudo().equals(article.getVendeur().getPseudo())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Vous n'êtes pas autorisé à modifier cet article.");
        }

        article.setVendeur(utilisateur);

        articleService.updateArticleAVendre(article);

        return "redirect:/articles/detail_enchere/" + idArticle;
    }



    public void setModelAttributesDetailEnchere(
            Model model,
            int noArticle,
            ArticleAVendre a
    ){
        String connected_username = utilisateurService.getConnectedUsername();
        Utilisateur user = utilisateurService.findByPseudo(connected_username).get();
        int meilleur_prix = enchereService.getMeilleurPrix(noArticle);
        String meilleur_enchereur = enchereService.getMeilleurEnchereur(noArticle);

        a.setMeilleure_offre(meilleur_prix);
        model.addAttribute("article", a);
        model.addAttribute("credit", user.getCredit());

        if(!Objects.equals(a.getVendeur().getPseudo(), connected_username)){
            model.addAttribute("acquereur", true);
        } else {
            model.addAttribute("acquereur", false);
        }

        if(!Objects.equals(meilleur_enchereur, connected_username)){
            model.addAttribute("enchere_lead", false);
        } else {
            model.addAttribute("enchere_lead", true);
        }

        if(a.getStatut_enchere() == 2){
            model.addAttribute("sold", true);
        } else {
            model.addAttribute("sold", false);
        }

        model.addAttribute("meilleur_enchereur", meilleur_enchereur);
    }


}
