
package back.entity;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ciudad{
    
    private String name;
    private Map<String, String> local_names;
    private Double lat;
    private Double lon;
    private String country;
    
}
