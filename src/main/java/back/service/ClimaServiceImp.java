package back.service;

import back.entity.Ciudad;
import back.entity.Clima;
import back.entity.Usuario;
import back.security.UsuarioDetailService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import util.Mensaje;

@Service
public class ClimaServiceImp implements ClimaService {

    private final RestTemplate restTemplate;

    @Autowired
    private UsuarioDetailService usuarioDetail;

    @Autowired
    private HistorialServiceImp historialService;

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
    @Cacheable("cacheClima")
    public ResponseEntity<?> getWeatherCity(String ciudad) {

        try {

            Ciudad[] ciudades = this.getUrlCities(ciudad);

            if (!Objects.isNull(ciudades[0])) {

                this.guardarEnElHistorial(ciudades[0]);

                return Mensaje.respuesta(ciudades[0], HttpStatus.OK);
            }

            return Mensaje.respuesta("No existen ciudades con el nombre:  " + ciudades[0].getName(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Mensaje.respuesta("Ocurrio un error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Ciudad[] getUrlCities(String ciudad) {
        StringBuilder url = new StringBuilder();

        url.append(this.urlPrincipalCiudad);
        url.append("q=").append(ciudad);
        url.append("&limit=1");
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Ciudad[].class);
    }

    
    @Override
    @Cacheable("cacheClima")
    public ResponseEntity<?> getFiveDaysWeather(String ciudad) {

        try {
            Ciudad[] ciudades = this.getUrlCities(ciudad);

            if (!Objects.isNull(ciudades[0])) {

                Clima clima = this.getUrlWeather(ciudades[0]);

                this.guardarEnElHistorial(ciudades[0]);

                return Mensaje.respuesta(this.getUrlForecast(clima), HttpStatus.OK);

            }

            return Mensaje.respuesta("Ciudad no encontrada", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Mensaje.respuesta("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @Cacheable("cacheClima")
    public ResponseEntity<?> getContaminacionAir(String ciudad) {

        try {
            Ciudad[] ciudades = this.getUrlCities(ciudad);

            if (!Objects.isNull(ciudades[0])) {

                this.guardarEnElHistorial(ciudades[0]);
                
                return Mensaje.respuesta(this.getUrlAirPollution(ciudades[0]), HttpStatus.OK);
            }

            return Mensaje.respuesta("Ciudad no encontrada", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Mensaje.respuesta("Error interno", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private Clima getUrlWeather(Ciudad ciudad) {

        StringBuilder url = new StringBuilder();

        url.append(this.urlWeatherCiudad).append("weather?");
        url.append("lat=").append(ciudad.getLat());
        url.append("&lon=").append(ciudad.getLon());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Clima.class);
    }
    
    private Object getUrlForecast(Clima clima) {

        StringBuilder url = new StringBuilder();

        url.append(this.urlWeatherCiudad).append("forecast?");
        url.append("id=").append(clima.getId());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Object.class);
    }

    private Object getUrlAirPollution(Ciudad ciudad) {

        StringBuilder url = new StringBuilder();

        url.append(this.urlWeatherCiudad).append("air_pollution?");
        url.append("lat=").append(ciudad.getLat());
        url.append("&lon=").append(ciudad.getLon());
        url.append("&appid=").append(this.api);

        return this.restTemplate.getForObject(url.toString(), Object.class);
    }

    private void guardarEnElHistorial(Ciudad ciudad) {
        
        StringBuilder sb = new StringBuilder();
        Usuario usuario = this.usuarioDetail.getUsuarioDetail();
        
        sb.append("name: ").append(ciudad.getName());
        sb.append(" &lat: ").append(ciudad.getLat());
        sb.append(" &lon: ").append(ciudad.getLon());
        sb.append(" &country: ").append(ciudad.getCountry());
        
        this.historialService.saveHistorial( sb.toString() , usuario);
    }
}
