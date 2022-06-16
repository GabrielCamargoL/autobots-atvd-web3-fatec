package com.autobots.automanager.modelos.hateoas;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;

@Component
public class AdicionadorLinkTelefone implements AdicionadorLink<Telefone> {

  @Override
  public void adicionarLink(List<Telefone> lista) {
    for (Telefone telefone : lista) {
      long id = telefone.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder
              .methodOn(TelefoneControle.class)
              .obterTelefone(id))
          .withSelfRel();
      telefone.add(linkProprio);
    }

  }

  @Override
  public void adicionarLink(Telefone objeto) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControle.class)
            .obterTelefones())
        .withRel("telefones");
    objeto.add(linkProprio);
  }

  public void adicionarLinks(List<Telefone> lista) {
    for (Telefone telefone : lista) {
      long id = telefone.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder
              .methodOn(TelefoneControle.class)
              .obterTelefonesPorCliente(id))
          .withSelfRel();
      telefone.add(linkProprio);
    }

  }

}