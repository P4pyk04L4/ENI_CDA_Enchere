package fr.eni.eni_cda_enchere.controller;

import fr.eni.eni_cda_enchere.bll.ArticleService;
import fr.eni.eni_cda_enchere.bo.ArticleAVendre;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private ArticleService articleService;

    public IndexController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String showIndex(
            Model model
    ){
        List<ArticleAVendre> articleAVendreList = articleService.getAllActiveArticleAVendre();
        model.addAttribute("articleAVendreList", articleAVendreList);
        return "index";
    }

}
