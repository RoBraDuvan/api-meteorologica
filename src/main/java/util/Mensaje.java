
package util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Mensaje {
    
    public static ResponseEntity<?> respuesta (Object resp, HttpStatus status){
        return new ResponseEntity<> (resp, status);
    }
    
}

class Log {
    private String log;
    
    public Log(String log){
        this.log = log;
    }
    
    public String getLog(){
        return this.log;
    }

}

