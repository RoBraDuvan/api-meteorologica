package back.controller;

import back.service.ClimaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.Mensaje;



@RestController
@RequestMapping("/api/clima")
public class ClimaRestController {
    
    @Autowired
    private ClimaServiceImp climaService;
    
    @GetMapping("/current/{ciudad}")
    public ResponseEntity<?> getWeatherCity( @PathVariable String ciudad ){
        
        try {
            return this.climaService.getWeatherCity(ciudad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Algo ha salido mal", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping("/forecast/{ciudad}")
    public Object getFiveDaysWeather (@PathVariable String ciudad){
        try {
            return this.climaService.getFiveDaysWeather(ciudad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Algo ha salido mal", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @GetMapping( "/air/{ciudad}" )
    public Object getContaminacionAir(@PathVariable String ciudad){
        try {
            return this.climaService.getContaminacionAir(ciudad);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Algo ha salido mal", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    
    
}
