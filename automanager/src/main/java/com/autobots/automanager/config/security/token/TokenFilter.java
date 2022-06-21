package com.autobots.automanager.config.security.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.autobots.automanager.config.security.UserDetailsImpl;
import com.autobots.automanager.repositorios.RepositorioUsuario;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenFilter extends OncePerRequestFilter {

  private TokenService tokenService;
  private RepositorioUsuario repositorioUsuario;

  public TokenFilter(TokenService tokenService, RepositorioUsuario repositorioUsuario) {
    this.tokenService = tokenService;
    this.repositorioUsuario = repositorioUsuario;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    String token = getToken(request);

    boolean isTokenValid = tokenService.validateToken(token);

    if (isTokenValid) {
      authenticateUser(token);
    }

    chain.doFilter(request, response);
  }

  private void authenticateUser(String token) {
    String email = tokenService.getUsername(token);

    UserDetailsImpl user = UserDetailsImpl.create(
        repositorioUsuario.findByEmail(email).get());

    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
        user.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");

    if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
      return null;
    }

    return token = token.replace("Bearer ", "");
  }

}
