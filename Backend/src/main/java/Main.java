import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Clase principal que gestiona la lógica de usuarios y conexión con la base de datos
public class Main {
    // Lista de usuarios cargados en memoria
    public static ArrayList<User> users = new ArrayList<>();

    // Método para autenticar un usuario por nombre y contraseña
    public static boolean authUser(String username, String password) {
        boolean found = false;
        int i = 0;
        do {
            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                found = true;
            } else {
                i++;
            }
        } while (!found && i < users.size());

        return found;
    }

    // Actualiza la lista de usuarios desde la base de datos
    public static void updateUsers() {
        // Vaciamos la lista actual
        users = new ArrayList<User>();

        // Obtenemos los usuarios de la base de datos
        String usersDB = "";
        try {
            usersDB = getUsersFromDB();
        } catch (Exception error) {
            System.out.println("Error al obtener los usuarios de la base de datos: " + error.getMessage());
        }

        // Variables para procesar los datos recibidos
        int position = 0; // Determina si el valor es id, nombre, password o token
        String id = "";
        String username = "";
        String password = "";
        String authToken = "";

        try {
            for (int i = 0; i < usersDB.length(); i++) {
                if (usersDB.charAt(i) == ',') {
                    position++; // Cuando encuentra una coma, pasa al siguiente valor
                } else if (usersDB.charAt(i) == '<') {
                    position = 0; // Si encuentra '<', pasa de línea y reinicia la posición
                    i += 3; // Adelanta para saltar el <br>

                    // Si la id no está vacía, crea el usuario y lo añade a la lista
                    if (!id.isEmpty()) {
                        User user = new User(Integer.parseInt(id), username, password, authToken);
                        if (!authToken.isEmpty()) {
                            user.setAuthToken(authToken);
                        }
                        users.add(user);
                    }

                    // Reinicia los valores para el siguiente usuario
                    id = "";
                    username = "";
                    password = "";
                    authToken = "";

                } else {
                    // Dependiendo de la posición, añade la información al campo correspondiente
                    if (position == 0) {
                        id += usersDB.charAt(i);
                    } else if (position == 1) {
                        username += usersDB.charAt(i);
                    } else if (position == 2) {
                        password += usersDB.charAt(i);
                    } else if (position == 3) {
                        authToken += usersDB.charAt(i);
                    }
                }
            }
        } catch (Exception error) {
            System.out.println("Error al cargar los usuarios en la lista: " + error.getMessage());
        }
        // Actualiza el contador de IDs con el más reciente
        User.updateCounter();
    }

    // Obtiene los usuarios de la base de datos y los devuelve como string
    public static String getUsersFromDB() {
        String result = "";

        // Carga el driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }

        // Conecta con la base de datos
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Crea el statement
        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }

        // Ejecuta la consulta SQL para obtener los usuarios
        try {
            String query = "SELECT id, nombre, contraseña, auth_token FROM usuarios";
            ResultSet rs = mStm.executeQuery(query);

            while (rs.next()) {
                result = result + rs.getInt("id") + "," + rs.getString("nombre") + "," + rs.getString("contraseña") + "," + (rs.getString("auth_token") != null ? rs.getString("auth_token") : "") + "<br>";
            }
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Cierra la conexión
        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Devuelve el resultado
        return result;
    }

    // Asigna un token a un usuario y lo actualiza en la base de datos
    public static void assignToken(String username, String token) {
        int i = 0;
        boolean found = false;
        do{
            if (users.get(i).getUsername().equals(username)) {
                found = true;
            } else {
                i++;
            }
        } while (!found && i < users.size());
        // Actualiza el token en la lista de usuarios
        Main.users.get(i).setAuthToken(token);
        // Actualiza el token en la base de datos
        updateToken(username, token);
    }

    // Actualiza el token de un usuario en la base de datos
    public static void updateToken(String username, String authToken) {
        // Carga el driver JDBC
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + e.getMessage());
        }

        // Conecta con la base de datos
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException e) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + e.getMessage());
        }

        // Crea el statement
        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException e) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + e.getMessage());
        }

        // Ejecuta la consulta SQL para actualizar el token
        try {
            String query = "UPDATE usuarios SET auth_token = '" + authToken + "' WHERE nombre = '" + username + "'";
            mStm.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + e.getMessage());
        }

        // Cierra la conexión
        try {
            mStm.close();
            conBD.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + e.getMessage());
        }
    }

    // Busca el índice de usuario en la lista por su token
    public static int searchUser(String token){
        int i = 0;
        boolean found = false;
        do{
            if (users.get(i).getAuthToken().equals(token)) {
                found = true;
            } else {
                i++;
            }
        } while (!found && i < users.size());
        if (found) {
            return i;
        } else {
            return -1; // Si no se encuentra el usuario, devolvemos -1
        }
    }
}


