package back.controller;

import back.service.UsuarioService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Mensaje;

@RestController
@RequestMapping("/api/user")
public class UsuarioRestController {
    
    @Autowired
    UsuarioService usuarioService;
    
    
    @PostMapping("/signup")
    public ResponseEntity<?> signUp( @RequestBody Map<String, String> body ){
        try {
            return this.usuarioService.signUp(body);           
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        
        return Mensaje.respuesta("Algo ha salido mal || Controller ", HttpStatus.INTERNAL_SERVER_ERROR);
    
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody(required = true) Map<String, String> body ){
        try {
            return this.usuarioService.logIn(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Algo ha salido mal", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
