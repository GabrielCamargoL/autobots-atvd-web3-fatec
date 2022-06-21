package com.autobots.automanager.controles.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enums.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioLoginDTO {
  private Long id;
  private String email;
  private String nome;
  private Set<PerfilUsuario> perfil;

  public UsuarioLoginDTO(Usuario usuario) {
    this.id = usuario.getId();
    this.email = usuario.getEmail();
    this.nome = usuario.getNome();
    this.perfil = usuario.getPerfis();
  }

  public static UsuarioLoginDTO create(Usuario usuario) {
    return new UsuarioLoginDTO(usuario);
  }
}
