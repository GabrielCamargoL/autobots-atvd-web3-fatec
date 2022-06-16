package com.autobots.automanager.servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@Service
public class ServicoUsuario {
  @Autowired
  RepositorioUsuario repositorioUsuario;

  public Usuario cadastrar(Usuario usuario) {

    if (!usuario.getTelefones().isEmpty()) {
      usuario.setTelefones(usuario.getTelefones());
    }
    if (!usuario.getEmails().isEmpty()) {
      usuario.setEmails(usuario.getEmails());
    }
    if (!usuario.getMercadorias().isEmpty()) {
      usuario.setMercadorias(usuario.getMercadorias());
    }
    if (!usuario.getDocumentos().isEmpty()) {
      usuario.setDocumentos(usuario.getDocumentos());
    }
    if (!usuario.getVendas().isEmpty()) {
      usuario.setVendas(usuario.getVendas());
    }
    if (!usuario.getCredenciais().isEmpty()) {
      usuario.setCredenciais(usuario.getCredenciais());
    }
    if (!usuario.getPerfis().isEmpty()) {
      usuario.setPerfis(usuario.getPerfis());
    }

    Usuario usuarioCriado = repositorioUsuario.save(usuario);
    return usuarioCriado;
  }
}
