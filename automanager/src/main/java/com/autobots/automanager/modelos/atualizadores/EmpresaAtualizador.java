package com.autobots.automanager.modelos.atualizadores;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;

public class EmpresaAtualizador {
  private StringVerificadorNulo verificador = new StringVerificadorNulo();

  public void atualizar(Empresa empresas, Empresa atualizacao) {
    if (atualizacao != null) {
      if (!verificador.verificar(atualizacao.getRazaoSocial())) {
        empresas.setRazaoSocial(atualizacao.getRazaoSocial());
      }
      if (!verificador.verificar(atualizacao.getNomeFantasia())) {
        empresas.setNomeFantasia(atualizacao.getNomeFantasia());
      }
    }
  }

  public void atualizar(List<Empresa> empresas, List<Empresa> atualizacoes) {
    for (Empresa atualizacao : atualizacoes) {
      for (Empresa Empresa : empresas) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == Empresa.getId()) {
            atualizar(Empresa, atualizacao);
          }
        }
      }
    }
  }
}
