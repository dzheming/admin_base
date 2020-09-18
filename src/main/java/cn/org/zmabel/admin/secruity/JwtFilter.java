package cn.org.zmabel.admin.secruity;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.org.zmabel.admin.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@WebFilter(filterName = "jwtFilter", urlPatterns = "/*")
public class JwtFilter implements Filter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("jwtFilter init ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token");
            response.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        }
        String url = request.getRequestURI();
        if (! url.contains("auth") && !url.contains("test")) {
            Claims claims = jwtTokenUtil.getClaimsFromToken(request);
            if (claims == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            response.setHeader("access_token", jwtTokenUtil.refreshToken(request.getHeader(jwtTokenUtil.HEADER_STRING)));
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
