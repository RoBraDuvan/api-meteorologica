
package back.service;
    
import java.util.Map;
import org.springframework.http.ResponseEntity;
import util.Mensaje;


public interface UsuarioService {
    
    ResponseEntity<?> signUp(Map<String, String> requestMap);
    
    ResponseEntity<?> logIn(Map<String, String> requestMap);
    
}
