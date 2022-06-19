package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVenda;

@Service
public class ServicoVenda {
  @Autowired
  RepositorioUsuario repositorioUsuario;
  @Autowired
  RepositorioVenda repositorioVenda;
  @Autowired
  RepositorioMercadoria repositorioMercadoria;

  public Venda cadastrar(Venda venda) {
    Venda vendaCriada = repositorioVenda.save(venda);
    return vendaCriada;
  }
}
