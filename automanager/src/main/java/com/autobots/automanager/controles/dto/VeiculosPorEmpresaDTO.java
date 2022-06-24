package com.autobots.automanager.controles.dto;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Veiculo;
import lombok.Data;

@Data
public class VeiculosPorEmpresaDTO {
  private String nomeFantasia;
  private String razaoSocial;
  private List<Veiculo> veiculos;

  public VeiculosPorEmpresaDTO(Empresa empresa, List<Veiculo> veiculos) {
    this.nomeFantasia = empresa.getNomeFantasia();
    this.razaoSocial = empresa.getRazaoSocial();
    this.veiculos = veiculos;
  }
}
