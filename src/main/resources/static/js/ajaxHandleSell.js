document.addEventListener('DOMContentLoaded', () => {
    let handleSell = document.getElementById("handleSell");
    handleSell.addEventListener('click', () => {
        handleSellFunction();
        console.log('click');
    })
})


function handleSellFunction() {
    let handleSell = document.getElementById("handleSell");
    let acquereur = document.getElementById("nomAcquereur").value;
    let prix = document.getElementById("prixItem").value;
    let vendeur = document.getElementById("nomVendeur").value;
    let no_article = document.getElementById("noItem").value;
    let bodyModal = document.getElementById('bodyModal');
    let csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    let csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    let confirmRetrait = document.getElementById('retraitConfirm');

    fetch('http://localhost:8080/articles/bid/handle-sell' , {
        method: "POST",
        headers: {
            'Content-Type' : 'application/json',
            [csrfHeader] : csrfToken
        },
        body: JSON.stringify({
            acquereur: acquereur,
            vendeur: vendeur,
            prix: prix,
            no_article: no_article,
        })
    })
        .then(response => response.json())
        .then(data => {
            handleSell.style.display = 'none';
            bodyModal.innerHTML = "";
            bodyModal.innerHTML = `
                    <div class="alert alert-success"> Transaction effectuée ! Merci d'utiliser notre site. </div>
                `
            confirmRetrait.disabled = true;
            confirmRetrait.innerText = "Retrait confirmé"
        })
        .catch(err => {
            console.log('Erreur Ajax : ' + err);
        })
}