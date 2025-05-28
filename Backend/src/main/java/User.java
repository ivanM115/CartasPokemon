import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Clase que representa un usuario del sistema
public class User {

    // Atributos del usuario
    private int id;
    private String password;
    private String username;
    private String authToken; // Token de autenticación

    private static int counter; // Contador estático para IDs

    // Constructor para crear un usuario nuevo
    public User(String username, String password) {
        updateCounter(); // Actualiza el contador de IDs desde la BBDD
        this.setId(this.getCounter() + 1); // Asigna un nuevo ID
        this.setUsername(username);
        this.setPassword(password);
        this.setCounter(); // Incrementa el contador
    }

    // Constructor para cargar un usuario existente (con id y token)
    public User(int id,String username, String password, String authToken) {
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setAuthToken(authToken);
    }

    // Método estático para crear un usuario y añadirlo a la lista y la BBDD
    public static void createUser(String username, String password, ArrayList<User> users) {
        try {
            System.out.println("Creando usuario");
            User newUser = new User(username, password);
            users.add(newUser);
            try {
                addUserDB(newUser); // Añade el usuario a la BBDD
                System.out.println("Usuario creado");
            } catch (Exception e) {
                System.out.println("Error al añadir usuario a la base de datos: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Error al crear usuario: " + e.getMessage());
        }
    }

    // Añade el usuario a la base de datos MySQL
    public static void addUserDB(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }
        
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }
        
        try {
            String query = "INSERT INTO usuarios (id,nombre,contraseña) VALUES ('"+user.getId()+"','"+user.getUsername()+"','"+user.getPassword()+"')";
            System.out.println(query);
            mStm.executeUpdate(query);
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }
    }

    // Actualiza el contador de IDs al máximo valor de la BBDD
    public static void updateCounter() {
        try {
            // Cargamos el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectamos con la base de datos
            Connection conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon","root", "");

            // Creamos la statement
            Statement mStm = conBD.createStatement();

            // Ejecutamos la query para obtener el ID más alto
            String query = "SELECT MAX(id) as max_id FROM usuarios";
            ResultSet rs = mStm.executeQuery(query);

            if (rs.next()) {
                User.counter = rs.getInt("max_id");
            }

            // Cerramos la conexión
            mStm.close();
            conBD.close();

        } catch (Exception e) {
            System.out.println("Error al actualizar el id de los usuarios: " + e.getMessage());
        }
    }

    // Añade una carta a la colección del usuario en la BBDD
    public void addCard(String cardId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }
        
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }
        
        try {
            String query = "INSERT INTO contenido (usuario_id,external_id) VALUES ('"+this.getId()+"','"+cardId+"')";
            System.out.println(query);
            mStm.executeUpdate(query);
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }
    }

    // Devuelve los IDs de las cartas del usuario como string separado por comas
    public String getCards() {
        String result = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }

        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }

        try {
            String query = "SELECT external_id FROM contenido WHERE usuario_id = '" + this.getId() + "'";
            ResultSet rs = mStm.executeQuery(query);

            while (rs.next()) {
                result = result + rs.getString("external_id") + ",";
            }
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }
        return result;

    }

    // Elimina una carta de la colección del usuario en la BBDD
    public void deleteCard(String cardId) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }

        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }

        try {
            String query = "DELETE FROM contenido WHERE usuario_id = '" + this.getId() + "' AND external_id = '" + cardId + "'";
            mStm.executeUpdate(query);
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }
    }

    // Getters y setters para los atributos
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter() {
        this.counter++;
    }
}
