package fr.eni.eni_cda_enchere.bll;

import fr.eni.eni_cda_enchere.bo.Categorie;
import fr.eni.eni_cda_enchere.dal.CategorieDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService{
    private final CategorieDAO categorieDAO;

    public CategorieServiceImpl(CategorieDAO categorieDAO) {
        this.categorieDAO = categorieDAO;
    }

    @Override
    public Categorie findByNoCategory(long no_categorie) {
        return categorieDAO.read(no_categorie);
    }

    @Override
    public List<Categorie> findAllCategories() {
        return categorieDAO.findAll();
    }

    @Override
    public void saveCategory(String libelle) {
        categorieDAO.createCategory(libelle);
    }

    @Override
    public void deleteCategory(long no_categorie) {
        categorieDAO.deleteCategory(no_categorie);
    }

    @Override
    public void updateCategory(Categorie cat) {
        categorieDAO.updateCategory(cat);
    }
}
