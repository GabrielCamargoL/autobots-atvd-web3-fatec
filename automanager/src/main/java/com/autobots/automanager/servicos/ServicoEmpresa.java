package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;

@Service
public class ServicoEmpresa {
  @Autowired
  RepositorioEmpresa repositorioEmpresa;
  @Autowired
  RepositorioServico repositorioServico;

  public Empresa cadastrar(Empresa empresa) {

    if (!empresa.getTelefones().isEmpty()) {
      empresa.setTelefones(empresa.getTelefones());
    }
    if (!empresa.getUsuarios().isEmpty()) {
      empresa.setUsuarios(empresa.getUsuarios());
    }
    if (!empresa.getMercadorias().isEmpty()) {
      empresa.setMercadorias(empresa.getMercadorias());
    }
    if (!empresa.getServicos().isEmpty()) {
      empresa.setServicos(empresa.getServicos());
    }
    if (!empresa.getVendas().isEmpty()) {
      empresa.setVendas(empresa.getVendas());
    }

    empresa.setCadastro(new Date());
    Empresa empresaCriada = repositorioEmpresa.save(empresa);
    return empresaCriada;
  }

  public Empresa cadastrarServico(Empresa empresa, Servico servico) {

    Set<Servico> listaServicos = empresa.getServicos();
    listaServicos.add(servico);
    empresa.setServicos(listaServicos);
    repositorioServico.save(servico);
    return repositorioEmpresa.save(empresa);
  }
}
