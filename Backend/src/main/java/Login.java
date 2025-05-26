
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
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
			Main.updateUsers();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                boolean found = Main.authUser(username, password);
                if (found) {
					
					String token = UUID.randomUUID().toString();
					Main.assignToken(username, token);
                    response.getWriter().append(token);
					System.out.println("found"+token);

                } else {
					System.out.println("else");
                    response.getWriter().append("User already exists: " + username);
                }
            } catch (Exception e) {
                response.getWriter().append("Error creating user: " + e.getMessage());
            }
        } catch (Exception ex) {
            response.getWriter().append("Invalid request: " + ex.getMessage());
        }
    }

}
