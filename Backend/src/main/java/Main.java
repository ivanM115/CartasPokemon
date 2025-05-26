
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    //attributes

    public static ArrayList<User> users = new ArrayList<>();

    //methods
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

        // Los añadimos a la lista de usuarios
        int position = 0; // variable usada para determinar si el valor es el id, nombre o password
        String data; // variable donde guardaremos el valor actual

        String id = "";
        String username = "";
        String password = "";
        String authToken = "";
        try {
            for (int i = 0; i < usersDB.length(); i++) {
                if (usersDB.charAt(i) == ',') {
                    position++; // cuando encuentra una coma, pasamos significa que pasamos al siguiente valor

                } else if (usersDB.charAt(i) == '<') {
                    position = 0; // si encontramos el signo '<', significa que pasamos de linea, así que
                    // reiniciamos la posición
                    i += 3; // adelantamos 3 posiciones para pasar el br

                    // si la id no esta vacia, creamos el usuario y lo añadimos a la lista
                    if (!id.isEmpty()) {
                        User user = new User(Integer.parseInt(id), username, password, authToken);
                        if (!authToken.isEmpty()) {
                            user.setAuthToken(authToken);
                        }
                        users.add(user);
                    }

                    // reiniciamos los valores
                    id = "";
                    username = "";
                    password = "";
                    authToken = "";

                } else {
                    // dependiendo de la posición, añadimos la información en lugares diferentes
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
        // Actualizamos el id con el mas reciente
        User.updateCounter();
    }

    public static String getUsersFromDB() {
        String result = "";

        // Cargamos el driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException error) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + error.getMessage());
        }

        // Conectamos con la base de datos
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException error) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Creamos el statement
        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException error) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + error.getMessage());
        }
// Ejecutamos la query
        try {
            String query = "SELECT id, nombre, contraseña, auth_token FROM usuarios";
            ResultSet rs = mStm.executeQuery(query);

            while (rs.next()) {
                result = result + rs.getInt("id") + "," + rs.getString("nombre") + "," + rs.getString("contraseña") + "," + (rs.getString("auth_token") != null ? rs.getString("auth_token") : "") + "<br>";
            }
        } catch (SQLException error) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Cerramos la conexión
        try {
            mStm.close();
            conBD.close();
        } catch (SQLException error) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + error.getMessage());
        }

        // Devolvemos el resultado
        return result;
    }

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
		// Actualizamos el token en la lista de usuarios
		Main.users.get(i).setAuthToken(token);
		
		updateToken(username, token);
	}

	public static void updateToken(String username, String authToken) {
	// Cargamos el driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC de MySQL: " + e.getMessage());
        }

        // Conectamos con la base de datos
        Connection conBD = null;
        try {
            conBD = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/cartas_pokemon",
                    "root", "");
        } catch (SQLException e) {
            System.out.println("Error al conectar con el servidor MySQL/MariaDB: " + e.getMessage());
        }

        // Creamos la statement
        Statement mStm = null;
        try {
            mStm = conBD.createStatement();
        } catch (SQLException e) {
            System.out.println("Error al establecer declaración de conexión MySQL/MariaDB: " + e.getMessage());
        }

        // Ejecutamos la query
        try {
            String query = "UPDATE usuarios SET auth_token = '" + authToken + "' WHERE nombre = '" + username + "'";
            mStm.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar SQL en servidor MySQL/MariaDB: " + e.getMessage());
        }

        // Cerramos la conexión
        try {
            mStm.close();
            conBD.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión a servidor MySQL/MariaDB: " + e.getMessage());
        }
	}
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


