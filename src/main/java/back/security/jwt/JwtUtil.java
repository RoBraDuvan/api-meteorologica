package back.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class JwtUtil {

    @Value(value = "${jwt.secret}")
    private String secret;

    @Value(value = "${jwt.time.expired}")
    private int timeExp;

    public String generatedToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", "admin");
        return crearToken(claims, username);
    }

    private String crearToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + this.timeExp))
                .signWith(SignatureAlgorithm.HS256, this.secret)
                .compact();
    }

    public String extraerUsername(String token) {
        return this.extraerClaim(token, Claims::getSubject);
    }
    
    public Date extraerExpiracion (String token ){
        return this.extraerClaim(token, Claims::getExpiration);
    }

    public <T> T extraerClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extraerAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extraerAllClaims(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    }
    
    private boolean isTokenExpiro (String token){
        return this.extraerExpiracion(token).before(new Date());
    }
    
    public boolean validarToken(String token, UserDetails userDetails){
        final String username = this.extraerUsername(token);
        return (username.equals(userDetails.getUsername()) && !this.isTokenExpiro(token));
    }

}
