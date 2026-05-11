import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// Custom wrapper to modify request parameters
public class HttpServletRequestWrapperExample extends HttpServletRequestWrapper {
    public HttpServletRequestWrapperExample(HttpServletRequest request) {
        super(request);
    }

    // 這裡展現了 Wrapper 的應用：
    // 1. 繼承 Wrapper 讓我們不必手動實作 HttpServletRequest 介面的數十個方法。
    // 2. 只針對需要客製化的方法 (如 getParameter) 進行攔截與修改。
    // 3. 其他未覆寫的方法，Wrapper 會自動委託 (Delegate) 給原始的 Request 物件處理。
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(name);
        if (value != null) {
            // 過濾輸入內容，防止 XSS (跨站腳本) 攻擊
            return value.replace("<", "&lt;").replace(">", "&gt;");
        }
        return value;
    }
}

// Usage in a Filter
/*
 * public void doFilter(ServletRequest request, ServletResponse response,
 * FilterChain chain)
 * throws IOException, ServletException {
 * HttpServletRequest httpRequest = (HttpServletRequest) request;
 * HttpServletRequestWrapperExample customRequest = new
 * HttpServletRequestWrapperExample(httpRequest);
 * chain.doFilter(customRequest, response);
 * }
 */
