package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.atualizadores.EnderecoAtualizador;
import com.autobots.automanager.modelos.hateoas.AdicionadorLinkEndereco;
import com.autobots.automanager.repositorios.RepositorioCliente;
import com.autobots.automanager.repositorios.RepositorioEndereco;

@Controller
public class EnderecoControle {

  @Autowired
  private RepositorioEndereco repositorioEndereco;
  @Autowired
  private RepositorioCliente repositorioCliente;
  @Autowired
  private AdicionadorLinkEndereco adicionadorLink;

  @GetMapping("/enderecos")
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    HttpStatus status = HttpStatus.NOT_FOUND;
    List<Endereco> enderecos = repositorioEndereco.findAll();
    if (enderecos.isEmpty()) {
      return new ResponseEntity<List<Endereco>>(status);
    } else {
      status = HttpStatus.FOUND;
      adicionadorLink.adicionarLink(enderecos);
      return new ResponseEntity<List<Endereco>>(enderecos, status);
    }
  }

  @GetMapping("/endereco/{enderecoId}")
  public ResponseEntity<Endereco> obterEndereco(@PathVariable long enderecoId) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    Optional<Endereco> endereco = repositorioEndereco.findById(enderecoId);

    if (endereco.isEmpty()) {
      return new ResponseEntity<Endereco>(status);
    } else {
      adicionadorLink.adicionarLink(endereco.get());
      status = HttpStatus.FOUND;
      return new ResponseEntity<Endereco>(endereco.get(), status);
    }
  }

  @GetMapping("/enderecoCliente/{clienteId}")
  public ResponseEntity<Endereco> obterEnderecoPorCliente(@PathVariable long clienteId) {
    Optional<Cliente> cliente = repositorioCliente.findById(clienteId);
    if (cliente.isEmpty()) {
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      adicionadorLink.adicionarLink(cliente.get().getEndereco());
      ResponseEntity<Endereco> resposta = new ResponseEntity<>(cliente.get().getEndereco(), HttpStatus.FOUND);
      return resposta;
    }
  }

  @PostMapping("/endereco/cadastro")
  public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco endereco) {
    HttpStatus status = HttpStatus.CONFLICT;
    Optional<Cliente> optionalCliente = repositorioCliente.findById(endereco.getId());

    if (optionalCliente.isEmpty()) {
      Cliente cliente = optionalCliente.get();
      Endereco enderecoEncontrado = cliente.getEndereco();
      if (enderecoEncontrado == null) {
        cliente.setEndereco(endereco);
        repositorioCliente.save(cliente);
        status = HttpStatus.CREATED;
      } else {
        status = HttpStatus.METHOD_NOT_ALLOWED;
      }
    }
    return new ResponseEntity<>(status);
  }

  @PutMapping("/endereco/atualizar")
  public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco atualizacao) {
    HttpStatus status = HttpStatus.CONFLICT;
    Cliente cliente = repositorioCliente.getById(atualizacao.getId());

    if (cliente != null) {
      Endereco endereco = cliente.getEndereco();
      EnderecoAtualizador atualizador = new EnderecoAtualizador();
      atualizador.atualizar(endereco, atualizacao);
      repositorioCliente.save(cliente);
      status = HttpStatus.OK;
    } else {
      status = HttpStatus.BAD_REQUEST;
    }
    return new ResponseEntity<>(status);
  }
}