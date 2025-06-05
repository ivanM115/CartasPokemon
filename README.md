# Projecte de Programació - Grau Superior DAW

## Objectiu general

Els alumnes han de desenvolupar una aplicació web funcional, amb un tema lliure, aplicant els coneixements adquirits durant el mòdul de Programació. El projecte ha de tenir un frontend atractiu, un backend robust en Java, i una base de dades MySQL per gestionar la informació.

---

## 1. Anàlisi de requisits

### Objectiu general

Desenvolupar una aplicació web per a la gestió de col·leccions de cartes Pokémon, on els usuaris poden registrar-se, iniciar sessió, afegir i eliminar cartes de la seva col·lecció, i consultar informació de les expansions i cartes més valuoses.

### Funcionalitats principals

- Registre i autenticació d’usuaris (amb token).
- Visualització d’expansions i consulta de cartes per expansió.
- Visualització de cartes més cares i últimes cartes.
- Gestió de la col·lecció personal: afegir i eliminar cartes.
- Persistència de dades a MySQL.
- Interacció frontend-backend via HTTP (AJAX).

---

## 2. Disseny del sistema

### Arquitectura general

- **Frontend**: HTML, CSS, JavaScript (peticions AJAX).
- **Backend**: Java (Servlets), lògica de negoci i accés a MySQL.
- **Base de dades**: MySQL (taules: usuaris, cartes, expansions, col·lecció).

### Diagrames UML

- Diagrama de casos d’ús
- Diagrama de classes
- Diagrama de seqüència (afegir carta a la col·lecció)

---

## 3. Model de dades

### Esquema MySQL

```sql
-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS cartas_pokemon;
USE cartas_pokemon;

-- Crear tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    auth_token VARCHAR(255) UNIQUE DEFAULT NULL
);

-- Crear tabla de contenido
CREATE TABLE contenido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    external_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

```
---

## 4. Guia d’instal·lació i ús

### Requisits previs

- Java 17+
- Tomcat 10+
- MySQL
- Navegador modern

### Passos

1. Importa el projecte a Eclipse/IDE.
2. Configura la connexió a la BBDD a les classes Java.
3. Executa el servidor Tomcat.
4. Utilitza la web per registrar-te, iniciar sessió i gestionar la col·lecció.

---

## 5. Proves realitzades

- Proves d’alta i login d’usuaris.
- Proves d’afegir i eliminar cartes.
- Proves de consulta d’expansions i cartes.
- Proves d’integració frontend-backend.
- Proves d’errors (login incorrecte, afegir carta sense token, etc.).
