package back.service;

import back.entity.Ciudad;
import back.entity.Clima;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import util.Mensaje;

@Service
public class ClimaServiceImp implements ClimaService {

    private final RestTemplate restTemplate;

    @Value(value = "${open.weather.urlCiudad}")
    private String urlPrincipalCiudad;

    @Value(value = "${open.weather.urlClima}")
    private String urlWeatherCiudad;

    @Value(value = "${open.weather.api}")
    private String api;

    @Autowired
    public ClimaServiceImp(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<?> getWeatherCity(String ciudad) {

        try {

            Ciudad[] ciudades = this.getCities(ciudad);

            if (!Objects.isNull(ciudades[0])) {
                return Mensaje.respuesta(this.getWeatherFromCity(ciudades[0]), HttpStatus.OK);
            }

            return Mensaje.respuesta("No existen ciudades con el nombre:  " + ciudades[0].getName(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mensaje.respuesta("Ocurrio un error", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private Ciudad[] getCities(String ciudad) {
        StringBuilder url = new StringBuilder();

        url.append(this.urlPrincipalCiudad);
        url.append("q=").append(ciudad);
        url.append("&limit=1");
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Ciudad[].class);
    }

    private Clima getWeatherFromCity(Ciudad ciudad) {

        StringBuilder url = new StringBuilder();

        //https://api.openweathermap.org/data/2.5/weather?
        //lat=44.34 &lon=10.99&appid=b5544bbd41d04fb47658d3757079d6b3
        url.append(this.urlWeatherCiudad).append("weather?");
        url.append("lat=").append(ciudad.getLat());
        url.append("&lon=").append(ciudad.getLon());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Clima.class);
    }

    @Override
    public ResponseEntity<?> getFiveDaysWeather(String ciudad) {
        
        try {
            Ciudad[] ciudades = this.getCities(ciudad);
            
            if (!Objects.isNull(ciudades[0])) {
                
                Clima clima = this.getWeatherFromCity(ciudades[0]);
                
                return Mensaje.respuesta(this.getForecast5(clima), HttpStatus.OK);
                
            }
            
            return Mensaje.respuesta("Ciudad no encontrada", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);   
    }
    
    
    
    private Object getForecast5 (Clima clima) {

        StringBuilder url = new StringBuilder();

        url.append(this.urlWeatherCiudad).append("forecast?");
        url.append("id=").append(clima.getId());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Object.class);
    }

    @Override
    public ResponseEntity<?> getContaminacionAir(String ciudad) {
        
        try {
            Ciudad[] ciudades = this.getCities(ciudad);

            
            if (!Objects.isNull(ciudades[0])){
                return Mensaje.respuesta(this.getAirPollutionFromCity(ciudades[0]), HttpStatus.OK);
            }
            
            return Mensaje.respuesta("Ciudad no encontrada", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return Mensaje.respuesta("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private Object getAirPollutionFromCity(Ciudad ciudad) {

        StringBuilder url = new StringBuilder();

        //https://api.openweathermap.org/data/2.5/air_pollution?
        //lat=44.34 &lon=10.99&appid=b5544bbd41d04fb47658d3757079d6b3
        url.append(this.urlWeatherCiudad).append("air_pollution?");
        url.append("lat=").append(ciudad.getLat());
        url.append("&lon=").append(ciudad.getLon());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Object.class);
    }
}
