import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// Custom wrapper to modify request parameters
public class HttpServletRequestWrapperExample extends HttpServletRequestWrapper {
    public HttpServletRequestWrapperExample(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        if ("username".equals(name)) {
            return "MODIFIED_USERNAME"; // Intercept and modify
        }
        return super.getParameter(name); // Delegate to original request
    }
}

// Usage in a Filter
/*
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletRequestWrapperExample customRequest = new HttpServletRequestWrapperExample(httpRequest);
    chain.doFilter(customRequest, response);
}
*/
