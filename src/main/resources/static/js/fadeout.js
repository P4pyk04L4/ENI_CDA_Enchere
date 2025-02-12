document.addEventListener('DOMContentLoaded', () => {
    let alert = document.getElementById('alert');
    let params = new URLSearchParams(window.location.search);
    let reason = params.keys().next().value;
    let message = "";

    switch(reason) {
        case 'echecModifVente' :
           message = "Vous ne pouvez pas modifer un article dont l'enchère est en cours !" ;
           alertBuilder(reason, message);
           break;
        case 'echecEnchere' :
            message = "Vous ne pouvez pas enchérir plus que vous n'avez actuellement d'argent !"
            alertBuilder(reason, message)
    }



    function alertBuilder(reason, message) {
        if(params.get(reason) === 'true') {
            alert.innerHTML =
                `
    <div id="fading" class="alert alert-danger">${message}</div>
            `
            setTimeout(() => {
                document.getElementById('fading').classList.add('fadeout');
            }, 2000)
        }
    }

})