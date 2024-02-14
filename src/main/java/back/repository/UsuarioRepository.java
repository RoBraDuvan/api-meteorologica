
package back.repository;

import back.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{
    
    Usuario findByUsername(@Param("username") String username);
    
    boolean existsByUsername (String username); 
    
}
