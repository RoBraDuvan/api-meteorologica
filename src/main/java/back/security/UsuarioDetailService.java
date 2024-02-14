/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package back.security;

import back.entity.Usuario;
import back.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsuarioDetailService implements UserDetailsService{

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private Usuario usuarioDetail;
            
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("dentro de loadUserByUsername {}", username);
        
        this.usuarioDetail = this.usuarioRepository.findByUsername(username);
        
        if(!Objects.isNull(this.usuarioDetail)){
            return new User(this.usuarioDetail.getUsername(), this.usuarioDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
    }
    
    public Usuario getUsuarioDetail(){
        return this.usuarioDetail;
    }
    
}
