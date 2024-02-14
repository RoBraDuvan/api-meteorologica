
package back.service;

import org.springframework.http.ResponseEntity;

public interface ClimaService {
    
    public ResponseEntity<?> getWeatherCity(String ciudad);
    
    public ResponseEntity<?> getFiveDaysWeather (String ciudad);
    
    public ResponseEntity<?> getContaminacionAir( String ciudad );
    
    
}
