package back.security.jwt;

import back.security.UsuarioDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    UsuarioDetailService usuarioDetail;

    Claims claims = null;

    private String username = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            if (request.getServletPath().matches("/api/user/login") || request.getServletPath().matches("/api/user/signup")) {
                filterChain.doFilter(request, response);
            } else {
                String authorizationHeader = request.getHeader("Authorization");
                String token = null;

                if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                    token = authorizationHeader.substring(7);
                    this.username = this.jwtUtil.extraerUsername(token);
                    this.claims = this.jwtUtil.extraerAllClaims(token);
                }

                if (this.username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetail = this.usuarioDetail.loadUserByUsername(username);

                    if (this.jwtUtil.validarToken(token, userDetail)) {
                        UsernamePasswordAuthenticationToken userPassAuthTok
                                = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());

                        new WebAuthenticationDetailsSource().buildDetails(request);

                        SecurityContextHolder.getContext().setAuthentication(userPassAuthTok);
                    }
                }

                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            log.error("Error FILTER: {}", e);
        }

    }

    public Boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) this.claims.get("role"));
    }

    public Boolean isUser() {
        return "user".equalsIgnoreCase((String) this.claims.get("role"));
    }

    public String getCurrentUsuario() {
        return this.username;
    }

}
