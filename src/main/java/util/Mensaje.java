
package util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Mensaje {
    
    public static ResponseEntity<?> respuesta (Object resp, HttpStatus status){
        return new ResponseEntity<> (resp, status);
    }
    
}


