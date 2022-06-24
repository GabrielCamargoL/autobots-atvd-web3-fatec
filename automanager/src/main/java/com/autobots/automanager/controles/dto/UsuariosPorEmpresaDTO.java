package com.autobots.automanager.controles.dto;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class UsuariosPorEmpresaDTO {
  private String nomeFantasia;
  private String razaoSocial;
  private List<Usuario> usuarios;

  public UsuariosPorEmpresaDTO(Empresa empresa) {
    this.nomeFantasia = empresa.getNomeFantasia();
    this.razaoSocial = empresa.getRazaoSocial();
    this.usuarios = empresa.getUsuarios();
  }
}
