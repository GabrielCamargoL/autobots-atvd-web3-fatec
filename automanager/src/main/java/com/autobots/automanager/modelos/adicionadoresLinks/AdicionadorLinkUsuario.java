package com.autobots.automanager.modelos.adicionadoresLinks;

import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.EmpresaControle;
import com.autobots.automanager.controles.UsuarioControle;
import com.autobots.automanager.entidades.hateaosDAO.UsuarioHateoas;

@Component
public class AdicionadorLinkUsuario implements AdicionadorLink<UsuarioHateoas> {
  @Override
  public void adicionarLink(List<UsuarioHateoas> lista) {
    for (UsuarioHateoas usuario : lista) {
      long id = usuario.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder
              .methodOn(UsuarioControle.class)
              .obterUsuario(id))
          .withSelfRel();
      usuario.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(UsuarioHateoas usuario) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(UsuarioControle.class)
            .obterUsuarios())
        .withRel("usuarios");
    usuario.add(linkProprio);
  }

  public void adicionarLinkUsuarioEmpresa(UsuarioHateoas usuario) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EmpresaControle.class)
            .obterUsuariosPorEmpresa())
        .withRel("usuarios");
    usuario.add(linkProprio);
  }
}