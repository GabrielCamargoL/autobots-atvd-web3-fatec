package com.autobots.automanager.servicos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.autobots.automanager.entidades.Usuario;

public class UserPrincipal implements UserDetails {

  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(Usuario usuario) {
    this.email = usuario.getEmail();
    this.password = usuario.getSenha();

    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(usuario.getPerfis().toString()));

    this.authorities = authorities;
  }

  public static UserPrincipal create(Usuario usuario) {
    return new UserPrincipal(usuario);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
