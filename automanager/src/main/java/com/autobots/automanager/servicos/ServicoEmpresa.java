package com.autobots.automanager.servicos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.repositorios.RepositorioVenda;

@Service
public class ServicoEmpresa {
  @Autowired
  RepositorioEmpresa repositorioEmpresa;
  @Autowired
  RepositorioServico repositorioServico;
  @Autowired
  RepositorioUsuario repositorioUsuario;
  @Autowired
  RepositorioVeiculo repositorioVeiculo;
  @Autowired
  RepositorioMercadoria repositorioMercadoria;
  @Autowired
  RepositorioVenda repositorioVenda;

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
    List<Usuario> listaUsuario = empresa.getUsuarios();

    listaUsuario.add(funcionario);
    empresa.setUsuarios(listaUsuario);
    return repositorioEmpresa.save(empresa);
  }

  public Venda cadastrarVenda(Empresa empresa, Venda venda) {

    Optional<Usuario> usuarioVenda = repositorioUsuario.findById(venda.getUsuario().getId());
    if (usuarioVenda.isEmpty()) {
      return null;
    }

    Optional<Usuario> funcionarioVenda = repositorioUsuario.findById(venda.getFuncionario().getId());
    if (funcionarioVenda.isEmpty()) {
      return null;
    }

    Optional<Veiculo> veiculoVenda = repositorioVeiculo.findById(venda.getVeiculo().getId());
    if (veiculoVenda.isEmpty()) {
      return null;
    }

    List<Mercadoria> listaMercadorias = new ArrayList<Mercadoria>();
    for (Mercadoria itemMercadoria : venda.getMercadorias()) {
      Mercadoria mercadoria = repositorioMercadoria.getById(itemMercadoria.getId());
      listaMercadorias.add(mercadoria);
    }

    List<Servico> listaServicos = new ArrayList<Servico>();
    for (Servico itemServico : venda.getServicos()) {
      Servico servico = repositorioServico.getById(itemServico.getId());
      listaServicos.add(servico);
    }

    // --------------------------------------------------------------

    Venda vendaCorpo = new Venda();
    vendaCorpo.setUsuario(usuarioVenda.get());
    vendaCorpo.setFuncionario(funcionarioVenda.get());

    vendaCorpo.setVeiculo(veiculoVenda.get());

    vendaCorpo.setMercadorias(listaMercadorias);
    vendaCorpo.setServicos(listaServicos);

    vendaCorpo.setCadastro(new Date());

    Set<Venda> empresaVendas = empresa.getVendas();

    empresaVendas.add(vendaCorpo);

    veiculoVenda.get().getVendas().add(vendaCorpo);
    Venda vendaCriada = repositorioVenda.save(vendaCorpo);
    repositorioEmpresa.save(empresa);
    return vendaCriada;
  }

  public Empresa cadastrarMercadoria(Empresa empresa, Mercadoria mercadoria) {

    Set<Mercadoria> listaMercadorias = empresa.getMercadorias();
    listaMercadorias.add(mercadoria);
    empresa.setMercadorias(listaMercadorias);
    repositorioMercadoria.save(mercadoria);
    return repositorioEmpresa.save(empresa);
  }
}
