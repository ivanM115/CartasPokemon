import fetchCards from './fetch.js';

document.addEventListener('DOMContentLoaded', () => {
    const sectionExpansiones = document.getElementById('expansiones');
    const navExpansiones = document.getElementById('button-exp');
    const images = document.querySelectorAll('.img-exp');

    if (!sectionExpansiones || !navExpansiones) {
        console.error('No se encontraron los elementos necesarios en el DOM.');
        return;
    }

    // Ocultar el section al hacer clic en una imagen
    images.forEach(image => {
        image.addEventListener('click', () => {
            sectionExpansiones.style.display = 'none';
        });
    });

    // Mostrar el section al hacer clic en el apartado de expansiones en el nav
    navExpansiones.addEventListener('click', () => {
        if (sectionExpansiones.style.display === 'none' || sectionExpansiones.style.display === '') {
            sectionExpansiones.style.display = 'block';
        } else {
            sectionExpansiones.style.display = 'none';
        }
    });
});


let id;
let page = 1;

function showExpansion() {
    const expansiones = document.getElementById('expansiones');

    if (!expansiones) {
        console.error('No se encontró el elemento con ID "expansiones".');
        return;
    }

    expansiones.addEventListener('click', (e) => {
        if (e.target.closest('.img-exp')) {
            id = e.target.closest('.img-exp').dataset.id;
            page=1;
            fetchCards(page, id);
        }
    });
}

function updatePage() {
    if (window.location.pathname.includes('expansiones')) {
        // Obtener los botones de navegación
        const buttonder = document.getElementById('flecha-der');
        const buttonizq = document.getElementById('flecha-izq');

        // Verificar que los botones existan
        if (!buttonder || !buttonizq) {
            console.error('No se encontraron los botones de navegación.');
            return;
        }

        // Configurar eventos para las flechas
        buttonder.addEventListener('click', () => {
            page++;
            fetchCards(page, id);
            console.log(`Página actual: ${page}`);
        });

        buttonizq.addEventListener('click', () => {
            if (page > 1) {
                page--;
                fetchCards(page, id);
                console.log(`Página actual: ${page}`);
            }
        });
    }
}

export { showExpansion, updatePage };