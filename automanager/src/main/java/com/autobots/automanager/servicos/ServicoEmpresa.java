package com.autobots.automanager.servicos;

import java.util.Date;
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

  public Venda cadastrarVenda(Empresa empresa, Venda venda) {

    venda.setCadastro(new Date());
    venda.setUsuario(venda.getUsuario());
    venda.setFuncionario(venda.getFuncionario());

    venda.setVeiculo(venda.getVeiculo());
    Veiculo veiculo = venda.getVeiculo();
    veiculo.getVendas().add(venda);

    empresa.getVendas().add(venda);
    repositorioEmpresa.save(empresa);
    return venda;
  }
}
