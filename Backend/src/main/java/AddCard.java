import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AddCard
 */
@WebServlet("/AddCard")
public class AddCard extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * Constructor del servlet AddCard
     */
    public AddCard() {
        super();
        // Constructor auto-generado
    }

    /**
     * Método GET (no utilizado en este caso, solo responde con el contexto)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Responde con el contexto del servlet
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * Método POST para añadir una carta a la colección del usuario
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera el token de autenticación y el id de la carta del request
        String token = request.getParameter("token");
        String cardid = request.getParameter("id");

        // Busca el usuario correspondiente al token
        int user = Main.searchUser(token);
        if (user != -1) {
            // Si el usuario existe, añade la carta a su colección
            Main.users.get(user).addCard(cardid);
            response.getWriter().append("Card added successfully");
        }
    }

}
