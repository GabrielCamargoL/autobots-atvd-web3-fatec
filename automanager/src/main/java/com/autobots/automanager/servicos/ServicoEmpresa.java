package com.autobots.automanager.servicos;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class ServicoEmpresa {
  @Autowired
  RepositorioEmpresa repositorioEmpresa;
  @Autowired
  RepositorioServico repositorioServico;
  @Autowired
  RepositorioUsuario repositorioUsuario;

  public Empresa cadastrar(Empresa empresa) {
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

  public Empresa cadastrarFuncionario(Empresa empresa, Usuario funcionario) {
    Set<Usuario> listaUsuario = empresa.getUsuarios();

    listaUsuario.add(funcionario);
    empresa.setUsuarios(listaUsuario);
    return repositorioEmpresa.save(empresa);
  }
}
