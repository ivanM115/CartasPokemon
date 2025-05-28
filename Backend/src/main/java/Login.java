import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor del servlet Login
     */
    public Login() {
        super();
        // Constructor auto-generado
    }

    /**
     * Método GET (no utilizado en este caso)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Método no implementado para GET
    }

    /**
     * Método POST para procesar el inicio de sesión
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Main.updateUsers(); // Actualiza la lista de usuarios desde la base de datos
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                boolean found = Main.authUser(username, password); // Comprueba si el usuario existe y la contraseña es correcta
                if (found) {
                    // Si las credenciales son correctas, genera y asigna un token
                    String token = UUID.randomUUID().toString();
                    Main.assignToken(username, token);
                    response.getWriter().append(token); // Devuelve el token al frontend
                    System.out.println("found"+token);
                } else {
                    // Si no se encuentra el usuario o la contraseña es incorrecta
                    System.out.println("else");
                    response.getWriter().append("User already exists: " + username);
                }
            } catch (Exception e) {
                // Manejo de errores durante la autenticación
                response.getWriter().append("Error creating user: " + e.getMessage());
            }
        } catch (Exception ex) {
            // Manejo de errores generales
            response.getWriter().append("Invalid request: " + ex.getMessage());
        }
    }

}
