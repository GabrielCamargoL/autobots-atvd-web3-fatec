package com.autobots.automanager.controles.dto;

import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class TokenDTO {

  private String token;
  private String tipo;
  private UsuarioLoginDTO usuario;

  public TokenDTO(String token, String tipo, Usuario usuario) {
    this.token = token;
    this.tipo = tipo;

    this.usuario = UsuarioLoginDTO.create(usuario);
  }

}
