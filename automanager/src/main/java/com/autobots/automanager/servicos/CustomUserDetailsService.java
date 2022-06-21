package com.autobots.automanager.servicos;

import java.util.Optional;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private RepositorioUsuario userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<Usuario> user = userRepository.findByEmail(username);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("Este usuario não existe!: - " + username);
    }

    return UserPrincipal.create(user.get());
  }

}
