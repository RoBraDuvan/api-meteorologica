package back.entity;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Clima {
    
    private Map<String, Integer> coord;
    private Object[] weather;
    private String base;
    private Long visibility;
    private Map<String, Double> wind;
    private Map<String, Double> clouds;
    private Double dt;
    private Map<String, Object> sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;
    
    
}
