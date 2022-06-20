package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "Usuario", "funcionario", "veiculo" })
@Entity
public class Venda {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Date cadastro;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Usuario Usuario;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Usuario funcionario;

  @OneToMany(fetch = FetchType.EAGER)
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER)
  private Set<Servico> servicos = new HashSet<>();

  @OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
  private Veiculo veiculo;
}