import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Método GET no implementado, solo responde con el contexto
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Main.updateUsers(); // Actualiza la lista de usuarios desde la base de datos
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                // Crea el usuario y lo añade a la lista y la base de datos
                User.createUser(username, password, Main.users);
                response.getWriter().append("User created successfully: " + username);
            } catch (Exception e) {
                // Si ocurre un error al crear el usuario, lo muestra
                response.getWriter().append("Error creating user: " + e.getMessage());
            }
        } catch (Exception ex) {
            // Si ocurre un error general en la petición, lo muestra
            response.getWriter().append("Invalid request: " + ex.getMessage());
        }
    }

}
