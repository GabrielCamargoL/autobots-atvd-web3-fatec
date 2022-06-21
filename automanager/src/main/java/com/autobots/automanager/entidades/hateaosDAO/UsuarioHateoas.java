package com.autobots.automanager.entidades.hateaosDAO;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.enums.PerfilUsuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = { "mercadorias", "vendas", "veiculos" }, callSuper = false)
public class UsuarioHateoas extends RepresentationModel<UsuarioHateoas> {
  private Long id;
  private String nome;
  private String nomeSocial;
  private Endereco endereco;
  private String email;
  private String senha;

  private Set<PerfilUsuario> perfis;
  private Set<Telefone> telefones;
  private Set<Documento> documentos;
  private Set<Mercadoria> mercadorias;
  private Set<Venda> vendas;
  private Set<Veiculo> veiculos;

  public UsuarioHateoas(Usuario usuario) {
    this.id = usuario.getId();
    this.nome = usuario.getNome();
    this.nomeSocial = usuario.getNomeSocial();
    this.email = usuario.getEmail();
    this.senha = usuario.getSenha();
    this.endereco = usuario.getEndereco();
    this.perfis = usuario.getPerfis();
    this.telefones = usuario.getTelefones();
    this.documentos = usuario.getDocumentos();
    this.mercadorias = usuario.getMercadorias();
    this.vendas = usuario.getVendas();
    this.veiculos = usuario.getVeiculos();
  }
}