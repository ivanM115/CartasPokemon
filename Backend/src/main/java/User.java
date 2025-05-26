
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class User {

    //Atributos
    private int id;
    private String password;
    private String username;
    private String authToken;

    private static int counter;

    // Constructor
    public User(String username, String password) {
        updateCounter();
        this.setId(this.getCounter() + 1);
        this.setUsername(username);
        this.setPassword(password);
        this.setCounter();
    }

    public User(int id,String username, String password, String authToken) {
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setAuthToken(authToken);
    }

    //methods
    public static void createUser(String username, String password, ArrayList<User> users) {
    try {
        System.out.println("Creando usuario");
        User newUser = new User(username, password);
        users.add(newUser);
        try {
            addUserDB(newUser);
            System.out.println("Usuario creado");
        } catch (Exception e) {
            System.out.println("Error al añadir usuario a la base de datos: " + e.getMessage());
        }
    } catch (Exception e) {
        System.out.println("Error al crear usuario: " + e.getMessage());
    }
}

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

    //getters and setters
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
