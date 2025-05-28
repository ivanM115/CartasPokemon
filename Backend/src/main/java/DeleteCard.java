import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class DeleteCard
 */
@WebServlet("/DeleteCard")
public class DeleteCard extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCard() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recupera el token de autenticación y el id de la carta del request
        String token = request.getParameter("token");
        String cardid = request.getParameter("id");

        // Busca el usuario correspondiente al token
        int user = Main.searchUser(token);
        if (user != -1) {
            // Si el usuario existe, elimina la carta de su colección
            Main.users.get(user).deleteCard(cardid);
            response.getWriter().append("Card added successfully");
        }
    }

}
