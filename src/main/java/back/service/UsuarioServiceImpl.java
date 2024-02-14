package back.service;

import back.entity.Usuario;
import back.repository.UsuarioRepository;
import back.security.UsuarioDetailService;
import back.security.jwt.JwtUtil;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import util.Mensaje;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    
    @Autowired
    private AuthenticationManager AuthenticationManager;
    
    @Autowired
    private UsuarioDetailService usuarioDetailService;
   
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> signUp(Map<String, String> body) {
        log.info("Registro de Usuarios || UsuarioService");
        try {
            if (this.validarDatos(body)) {
                
                Usuario usuario = this.usuarioRepository.findByUsername(body.get("username"));

                if (Objects.isNull(usuario)) {
                    this.usuarioRepository.save(this.getUsuarioByMap(body));
                    
                    return Mensaje.respuesta("Usuario registrado con exito", HttpStatus.CREATED);
                } else {
                    return Mensaje.respuesta("Usuario existente", HttpStatus.BAD_REQUEST);
                }
                
            } else {
                return Mensaje.respuesta("Faltan algunos datos", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        
        return Mensaje.respuesta("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> logIn(Map<String, String> requestMap) {
       log.info("Dentro del Login || UsuarioServiceImp");
       
        try {
            Authentication authentication = this.AuthenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("username"), requestMap.get("password"))
            );
            
            if (authentication.isAuthenticated()){
                return Mensaje.respuesta(this.jwtUtil.generatedToken(this.usuarioDetailService.getUsuarioDetail().getUsername()), HttpStatus.OK);
            } else {
                return Mensaje.respuesta("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.error("Exception {}", e);
        }
       
        
        return Mensaje.respuesta("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
       
    }

    private boolean validarDatos(Map<String, String> body) {
        return body.containsKey("username") && body.containsKey("password");
    }
    
    private Usuario getUsuarioByMap( Map<String, String> body ){
        Usuario usuario = new Usuario();
        
        usuario.setUsername(body.get("username"));
        usuario.setPassword(body.get("password"));
        
        return usuario;
    }

}
