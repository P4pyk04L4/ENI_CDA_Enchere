package fr.eni.eni_cda_enchere.sheduling;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ActualisationStatutVentes {

    ArticleService articleService;

    public ActualisationStatutVentes(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void activationCronStatutVentes() {
        System.out.println("Mise à jour des ventes effectuée");

        List<ArticleAVendre> articleAVendreList = articleService.cronGetAllArticles();
        LocalDate today = LocalDate.now();

        for (ArticleAVendre art : articleAVendreList) {
            LocalDate dateDebut = art.getDate_debut_encheres();
            LocalDate dateFin = art.getDate_fin_encheres();

            switch(art.getStatut_enchere()) {
                case 0:
                    if(dateDebut.isBefore(today) || dateDebut.isEqual(today)) {
                        art.setStatut_enchere(1);
                    }
                    break;
                case 1:
                    if(dateFin.isBefore(today) || dateFin.isEqual(today)) {
                        art.setStatut_enchere(2);
                    }
                    break;
            }
            articleService.cronUpdateArticleAVendre(art);
        }
    }
}
