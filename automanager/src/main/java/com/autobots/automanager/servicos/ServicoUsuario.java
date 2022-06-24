package com.autobots.automanager.servicos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.hateaosDAO.UsuarioHateoas;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioVeiculo;

@Service
public class ServicoUsuario {
  @Autowired
  RepositorioUsuario repositorioUsuario;
  @Autowired
  RepositorioMercadoria repositorioMercadoria;
  @Autowired
  RepositorioVeiculo repositorioVeiculo;

  public List<UsuarioHateoas> findAll() {

    List<Usuario> usuarios = repositorioUsuario.findAll();
    List<UsuarioHateoas> usuariosHateoas = new ArrayList<UsuarioHateoas>();
    for (Usuario usuario : usuarios) {
      usuariosHateoas.add(new UsuarioHateoas(usuario));
    }

    return usuariosHateoas;
  }

  public UsuarioHateoas findById(long id) {

    Optional<Usuario> usuario = repositorioUsuario.findById(id);
    if (usuario.isEmpty()) {
      return null;
    }
    UsuarioHateoas usuariosHateoas = new UsuarioHateoas(usuario.get());
    return usuariosHateoas;
  }

  public Usuario cadastrar(Usuario usuario) {
    Usuario usuarioCriado = repositorioUsuario.save(usuario);
    return usuarioCriado;
  }

  public Veiculo cadastrarVeiculo(Usuario usuario, Veiculo veiculo) {
    Set<Veiculo> listaVeiculos = usuario.getVeiculos();
    listaVeiculos.add(veiculo);
    usuario.setVeiculos(listaVeiculos);
    veiculo.setProprietario(usuario);
    repositorioUsuario.save(usuario);
    return repositorioVeiculo.save(veiculo);
  }
}
