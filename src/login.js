function register() {
    const username = document.getElementById("register-username").value;
    const password = document.getElementById("register-password").value;

    let http = new XMLHttpRequest();
    http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            alert(http.responseText);
        }
    }
    console.log("se ejecuta");
    http.open("POST", "http://localhost:8080/Prueba1/Register", true);
    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    http.send("username=" + username + "&password=" + password);
}

let btn = document.getElementById("register-button");
if (btn) {
    btn.addEventListener("click", function (event) {
        event.preventDefault();
        register();
    });
}

function login() {
    const username = document.getElementById("login-username").value;
    const password = document.getElementById("login-password").value;

    let http = new XMLHttpRequest();
    http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log(http.responseText);
            localStorage.setItem("token", http.responseText);
            localStorage.setItem("username", username);
            window.location.href = "http://127.0.0.1:5500/index.html";
        }
    }
    console.log("se ejecuta");
    http.open("POST", "http://localhost:8080/Prueba1/Login", true);
    http.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    http.send("username=" + username + "&password=" + password);
}

let btn2 = document.getElementById("login-button");
if (btn2) {
    btn2.addEventListener("click", function (event) {
        event.preventDefault();
        login();
    });
}

function closeLogin() {
    localStorage.removeItem("token");
    window.location.href = "http://127.0.0.1:5500/pages/login.html"
}

let btn3 = document.getElementById("cerrarSesion");

btn3.addEventListener("click", function (event) {
    closeLogin();
});

function comprobarLogin() {
    let token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "http://127.0.0.1:5500/pages/login.html";
    }else{
        window.location.href = "http://127.0.0.1:5500/pages/profile.html";
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const btn4 = document.getElementById("profile-img");
    if (btn4) {
        btn4.addEventListener("click", comprobarLogin);
    }
});

