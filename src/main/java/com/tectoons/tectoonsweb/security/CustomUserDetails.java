/*package com.tectoons.tectoonsweb.security;

import com.tectoons.tectoonsweb.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    // ⚡ IMPORTANTE: devolvemos el ID para poder usarlo en tus controladores REST
    public Long getId() {
        return usuario.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    // ✔ Mejor usar el correo como username interno (porque tu login permite ambos)
    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return usuario.isActivo();
    }

    // ⚡ Getters útiles para la vista
    public String getNombreUsuario() {
        return usuario.getNombreUsuario();
    }

    public String getNombreCompleto() {
        return usuario.getNombreCompleto();
    }
}*/

package com.tectoons.tectoonsweb.security;

import com.tectoons.tectoonsweb.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    public Long getId() {
        return usuario.getId(); // ⚡ IMPORTANTE
    }

    public String getNombreUsuario() {
        return usuario.getNombreUsuario();
    }

    public String getNombreCompleto() {
        return usuario.getNombreCompleto();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getNombreUsuario();
    }

     // Helper para Thymeleaf o lógica interna
    public boolean isAdmin() {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isArtista() {
        return authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ARTISTA"));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return usuario.isActivo(); }
}