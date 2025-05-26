export default function fetchCards(page, id, cardId) {
    let cards = document.getElementById('cards')


    let url = `https://api.pokemontcg.io/v2/cards?page=${page}&pageSize=21`;

    if (id && id !== null) {
        url = `https://api.pokemontcg.io/v2/cards?page=${page}&pageSize=21&q=set.id:${id}`;
    }

    if (id === 'price') {
        cards = document.querySelector('[data-id="price"]');
        url = "https://api.pokemontcg.io/v2/cards?page=1&pageSize=21&orderBy=-cardmarket.prices.averageSellPrice";

    } else if (id === 'last') {
        cards = document.querySelector('[data-id="last"]');
        url = "https://api.pokemontcg.io/v2/cards?page=1&pageSize=21&orderBy=-set.releaseDate";
    }
    if (cardId) {
        url = `https://api.pokemontcg.io/v2/cards/${cardId}`;
        console.log("URL de cardId:", url);
    }

    fetch(url, {
        headers: {
            'X-Api-Key': '3baf4606-71dd-40e5-8bdf-f66551f04723'
        }
    })
        .then((response) => response.json())
        .then((data) => {
            // const sortedCards = data.data.sort((a, b) => {
            //     return parseInt(a.number) - parseInt(b.number); // Convertir a número para evitar problemas con strings
            // });
            // sortedCards.forEach(card => {
            //     console.log(card);
            // })
            console.log(data)
            

            if (cardId) {
                const averageSellPrice = data.data.cardmarket?.prices?.averageSellPrice || "N/A";

                let plantilla = `
            <article class="card" data-id="${data.data.id}">
                <img class='card-img' src="${data.data.images.large}" alt="${data.data.name}">
                <h3 class='card-price'>${averageSellPrice}$</h3>
            </article>
            `;
                if (cards) {
                    cards.innerHTML += plantilla;
                }
            } else {
                cards.innerHTML = "";
                data.data.forEach(card => {
                    console.log(card);

                    // Verificar si card.cardmarket y card.cardmarket.prices existen
                    const averageSellPrice = card.cardmarket?.prices?.averageSellPrice || "N/A";

                    let plantilla = `
            <article class="card" data-id="${card.id}">
                <img class='card-img' src="${card.images.large}" alt="${card.name}">
                <h3 class='card-price'>${averageSellPrice}$</h3>
            </article>
            `;

                    if (cards) {
                        cards.innerHTML += plantilla;
                    }
                });
            }

        });
}
