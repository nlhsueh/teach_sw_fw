// This is a simulation of the Web MVC (Model 2) components
import java.util.*;

// --- MODEL ---
class Hello {
    private Map<String, String> messages;
    
    public String doHello(String user) {
        String message = messages.get(user);
        if (message == null) message = "Hello";
        return message + ", " + user + "!";
    }
    
    public Hello() {
        messages = new HashMap<String, String>();
        messages.put("Nick", "Hello");
        messages.put("Mary", "Welcome");
        messages.put("John", "Hi");
    }
}

// --- CONTROLLER (Servlet) ---
// Note: Requires Java Servlet API
/*
@WebServlet(name="Hello", urlPatterns={"/hello.do"})
public class HelloServlet extends HttpServlet {
    private Hello hello = new Hello(); // create MODEL

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("user");
        String message = hello.doHello(name);
        req.setAttribute("message", message);
        req.getRequestDispatcher("hello.jsp").forward(req, resp); // forward to VIEW
    }
}
*/

// --- VIEW (JSP) ---
/*
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>${param.user}</title>
  </head>
  <body>
      <h1>${message}</h1>
  </body>
</html>
*/
