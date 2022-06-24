package com.autobots.automanager.controles.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;

import lombok.Data;

@Data
public class ServicoMercadoriaEmpresaDTO {
  private String nomeFantasia;
  private String razaoSocial;
  private Set<Mercadoria> mercadorias;
  private Set<Servico> servicos;

  public ServicoMercadoriaEmpresaDTO(Empresa empresa) {
    this.nomeFantasia = empresa.getNomeFantasia();
    this.razaoSocial = empresa.getRazaoSocial();
    this.mercadorias = empresa.getMercadorias();
    this.servicos = empresa.getServicos();
  }
}
