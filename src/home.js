import fetchCards from './fetch.js';

export function loadHome() {
    if (window.location.pathname.includes('index')) {
        fetchCards(1, "price");
        fetchCards(1, "last");
    }
}