package com.autobots.automanager.controles.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Venda;

import lombok.Data;

@Data
public class VendasPorEmpresaDTO {
  private String nomeFantasia;
  private String razaoSocial;
  private Set<Venda> vendas;

  public VendasPorEmpresaDTO(Empresa empresa) {
    this.nomeFantasia = empresa.getNomeFantasia();
    this.razaoSocial = empresa.getRazaoSocial();
    this.vendas = empresa.getVendas();
  }
}
