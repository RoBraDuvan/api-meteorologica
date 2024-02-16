
package back.repository;

import back.entity.Historial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface HistorialRepository extends JpaRepository <Historial, Integer> {
    
    List<Historial> findByIdUser(@Param("usuario")int id);
    
}
