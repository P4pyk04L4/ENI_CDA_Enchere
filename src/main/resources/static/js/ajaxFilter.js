$(document).ready(function() {
    function toggleSelects() {
        if ($('#achats').is(':checked')) {
            $('#typeAchat').prop('disabled', false);
        } else {
            $('#typeAchat').prop('disabled', true);
        }

        if ($('#ventes').is(':checked')) {
            $('#typeVente').prop('disabled', false);
        } else {
            $('#typeVente').prop('disabled', true);
        }
    }

    toggleSelects();

    $('input[name="achatVente"]').change(function() {
        toggleSelects();
    });

    $("#search-btn").click(function(event) {
        event.preventDefault();

        let csrfToken = $("meta[name='_csrf']").attr("content");
        let csrfHeader = $("meta[name='_csrf_header']").attr("content");

        let searchText = $("#search-bar").val();
        let noCategorie = $("#category-select").val();
        let estAchat = $('#achats').is(':checked');
        let estVente = $('#ventes').is(':checked');
        let critere = null;

        if(estAchat){
            critere = $('#typeAchat').val();
        } else {
            critere = $('#typeVente').val();
        }

        $.ajax({
            url: "http://localhost:8080/filter/articles",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify({
                searchText: searchText,
                noCategorie: noCategorie,
                estAchat: estAchat,
                estVente: estVente,
                critere: critere
            }),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(response) {
                console.log("Réponse : " + JSON.stringify(response, null, 2));
                $("#articles-cards").html("");
                response.forEach(function(item){
                    createTile(item.nom_article, item.prix_initial, item.meilleure_offre, item.date_fin_encheres, item.pseudo, item.no_article, item.chemin_vendeur);
                })
            },
            error: function(xhr, status, error) {
                console.error("Erreur lors de la recherche : ", error);
            }
        })
    })

    function createTile(nomArticle, prixDepart, offreActuelle, dateFinEnchere, vendeur, articleId, cheminVendeur){
        const cardBlock = document.createElement('div');
        cardBlock.classList.add('col-lg-4', 'col-md-6', 'col-sm-12', 'p-3');

        const card = document.createElement('div');
        card.classList.add('card', 'customZoom');
        // card.setAttribute('data-category', `Categorie{no_categorie =${noCategorie}, libelle='Informatique'}`);

        const cardBody = document.createElement('div');
        cardBody.classList.add('card-body');

        const row = document.createElement('div');
        row.classList.add('row');

        const colTitle = document.createElement('div');
        colTitle.classList.add('col-10');
        const cardTitle = document.createElement('h5');
        cardTitle.classList.add('card-title');
        cardTitle.textContent = nomArticle;
        colTitle.appendChild(cardTitle);

        const colLink = document.createElement('div');
        colLink.classList.add('col-2');
        const link = document.createElement('a');
        link.setAttribute('id', 'article-path');
        link.setAttribute('href', `/articles/detail_enchere/${articleId}`);
        const img = document.createElement('img');
        img.classList.add('image-path');
        img.setAttribute('src', '/images/dollar-green-d83d6d7f8fb2946b655c9a8529449d45.png');
        img.setAttribute('alt', '');
        img.setAttribute('height', '30');
        img.setAttribute('width', '30');
        link.appendChild(img);
        colLink.appendChild(link);

        row.appendChild(colTitle);
        row.appendChild(colLink);

        cardBody.appendChild(row);

        const cardText = document.createElement('div');
        cardText.classList.add('card-text');

        const prixElement = document.createElement('span');
        prixElement.textContent = `Prix de départ : ${prixDepart}€`;
        cardText.appendChild(prixElement);
        cardText.appendChild(document.createElement('br'));

        const offerElement = document.createElement('span');
        offerElement.classList.add('actual-offer');
        if(offreActuelle === 0){
            offerElement.textContent = `Offre actuelle : Pas d'offres pour l'instant.`;
        } else {
            offerElement.textContent = `Offre actuelle : ${offreActuelle}€`;

        }
        cardText.appendChild(offerElement);
        cardText.appendChild(document.createElement('br'));

        const dateElement = document.createElement('span');
        let formattedDate = new Date(dateFinEnchere).toLocaleDateString('fr-FR');
        dateElement.textContent = ` Fin de l'enchère : ${formattedDate}`;
        cardText.appendChild(dateElement);
        cardText.appendChild(document.createElement('br'));

        const sellerElement = document.createElement('span');
        sellerElement.textContent = ` Vendeur : `;
        let sellerName = null;
        if(cheminVendeur != null){
            sellerName = document.createElement('a');
            sellerName.href = cheminVendeur;
        } else {
            sellerName = document.createElement('span');
        }
        sellerName.classList.add('seller-name');
        sellerName.textContent = vendeur;
        sellerElement.appendChild(sellerName);
        cardText.appendChild(sellerElement);

        cardBody.appendChild(cardText);
        card.appendChild(cardBody);
        cardBlock.appendChild(card);
        $("#articles-cards").append(cardBlock);
    }
})