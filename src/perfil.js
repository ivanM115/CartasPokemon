import fetchCards from './fetch.js';

function cargarCartas(){
    let http = new XMLHttpRequest();
    http.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            let idsArray = http.responseText.split(',').map(id => id.trim());
            console.log(idsArray);
            for (let i = 0; i < idsArray.length; i++) {
                let id = idsArray[i];
                if (id) { // Asegurarse de que el id no esté vacío
                    fetchCards("", "", id);
                }
            }
        }
    }
    let token = localStorage.getItem("token");

    http.open("POST", "http://localhost:8080/Prueba1/Perfil", true);
    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    http.send("token=" + token);
}

cargarCartas();

function eliminarCarta(id) {
    let http = new XMLHttpRequest();
    http.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            window.location.reload(); // Recargar la página para reflejar los cambios
            console.log("Carta eliminada con éxito");
        }
    }
    let token = localStorage.getItem("token");

    http.open("POST", "http://localhost:8080/Prueba1/DeleteCard", true);
    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    http.send("token=" + token + "&id=" + id);
}

let container = document.querySelector('.cards-container');
container.addEventListener('click', function(e) {
    if (e.target.closest('.card')) {
        let card = e.target.closest('.card');
        let cardId = card.dataset.id;
        console.log(cardId);
        eliminarCarta(cardId);
    }
});

