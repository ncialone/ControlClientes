package com.test.prueba.Servicio;

import com.test.prueba.dao.UsuarioDao;
import com.test.prueba.entidades.Rol;
import com.test.prueba.entidades.Usuario;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//indicamos que es un bean de spring de servicio
@Service("UserDetailsService")
@Slf4j
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    @Transactional(readOnly=true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        
        if (usuario==null) {
            throw new UsernameNotFoundException(username);
        }
        
        //este tipo necesita Spring para manejar los roles, no podemos utilizar la clase de roles
        //para q funcione security con implementacion de JPA debemos envolver los roles en esta clase
        //GRANTED AUTHORITY
        var roles = new ArrayList<GrantedAuthority>();
        
        for(Rol rol: usuario.getRoles()){
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        
    //con esto creamos un objeto User de Spring utilizado por security de forma automatica
    //por eso recuperamos y pasamos por parametro el username, password y roles asociados
    //regresa un tipo de interface UserDetails
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
