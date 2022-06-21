package com.autobots.automanager.modelos.atualizadores;

import java.util.Set;

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

      if (!verificador.verificar(atualizacao.getEndereco())) {
        empresas.setEndereco(atualizacao.getEndereco());
      }

      if (!verificador.verificar(atualizacao.getMercadorias())) {
        empresas.setMercadorias(atualizacao.getMercadorias());
      }

      if (!verificador.verificar(atualizacao.getServicos())) {
        empresas.setServicos(atualizacao.getServicos());
      }

      if (!verificador.verificar(atualizacao.getTelefones())) {
        empresas.setTelefones(atualizacao.getTelefones());
      }

      if (!verificador.verificar(atualizacao.getVendas())) {
        empresas.setVendas(atualizacao.getVendas());
      }
    }
  }

  public void atualizar(Set<Empresa> empresas, Set<Empresa> atualizacoes) {
    for (Empresa atualizacao : atualizacoes) {
      for (Empresa empresa : empresas) {
        if (atualizacao.getId() != null) {
          if (atualizacao.getId() == empresa.getId()) {
            atualizar(empresa, atualizacao);
          }
        }
      }
    }
  }
}
