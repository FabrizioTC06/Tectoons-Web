/*package com.tectoons.tectoonsweb.security;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(identificador)
                .or(() -> usuarioRepository.findByNombreUsuario(identificador))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        var authorities = usuario.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getNombre().toUpperCase()))
                .collect(Collectors.toList());

        return new CustomUserDetails(usuario, authorities);
    }
}*/

package com.tectoons.tectoonsweb.security;

import com.tectoons.tectoonsweb.model.Usuario;
import com.tectoons.tectoonsweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String identificador) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByCorreo(identificador)
                .or(() -> usuarioRepository.findByNombreUsuario(identificador))
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Actualizar último login
        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        var authorities = usuario.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getNombre()))
                .collect(Collectors.toList());

        return new CustomUserDetails(usuario, authorities);
    }
}