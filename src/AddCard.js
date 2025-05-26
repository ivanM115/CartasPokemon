function addcard(id) {
    let http = new XMLHttpRequest();
    http.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
           
        }
    }
    let token = localStorage.getItem("token");

    console.log("se ejecuta");
    http.open("POST", "http://localhost:8080/Prueba1/AddCard", true);
    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    http.send("token=" + token + "&id=" + id);
}

let container = document.querySelector('.cards-container');
container.addEventListener('click', function(e) {
    if (e.target.closest('.card')) {
        let card = e.target.closest('.card');
        let cardId = card.dataset.id;
        console.log(cardId);
        addcard(cardId);
    }
});