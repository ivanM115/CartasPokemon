let sectionMasCaras = document.getElementById("imgCard_precio");

fetch("https://api.pokemontcg.io/v2/sets",{
    headers: {
        'X-Api-Key': '3baf4606-71dd-40e5-8bdf-f66551f04723'
    }
})
.then((response) => response.json())
.then((data) => {
    data.data.forEach(card => {
        console.log(card);
    })
})

fetch("https://api.pokemontcg.io/v2/cards?q=set.id:sv9", {
    headers: {
        'X-Api-Key': '3baf4606-71dd-40e5-8bdf-f66551f04723'
    }
})
.then((response) => response.json())
.then((data) => {
    data.data.forEach(card => {
        console.log(card);

        // Verificar si card.cardmarket y card.cardmarket.prices existen
        const averageSellPrice = card.cardmarket?.prices?.averageSellPrice || "N/A";

        let plantilla = `
        <article class="card">
            <img class='card-img' src="${card.images.large}" alt="${card.name}">
            <h3 class='card-price'>${averageSellPrice}$</h3>
        </article>
        `;
        if (sectionMasCaras) {
            sectionMasCaras.innerHTML += plantilla;
        }
    });
});

exp = document.getElementById('expansiones')

btn = document.getElementById('button-exp')

btn.addEventListener('click', () => {
    exp.classList.toggle('active');
})