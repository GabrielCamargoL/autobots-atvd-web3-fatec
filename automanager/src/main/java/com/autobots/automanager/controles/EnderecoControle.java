package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@Controller
public class EnderecoControle {

  @Autowired
  private EnderecoRepositorio repositorio;

  @GetMapping("/enderecos")
  public ResponseEntity<List<Endereco>> obterEnderecos() {
    HttpStatus status = HttpStatus.NOT_FOUND;
    List<Endereco> enderecos = repositorio.findAll();
    if (enderecos == null) {
      return new ResponseEntity<List<Endereco>>(status);
    } else {
      status = HttpStatus.FOUND;
      return new ResponseEntity<List<Endereco>>(enderecos, status);
    }
  }

  @GetMapping("/endereco/{id}")
  public ResponseEntity<List<Endereco>> obterEndereco(@PathVariable long id) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    List<Endereco> enderecos = repositorio.findAll();
    if (enderecos == null) {
      return new ResponseEntity<List<Endereco>>(status);
    } else {
      status = HttpStatus.FOUND;
      return new ResponseEntity<List<Endereco>>(enderecos, status);
    }
  }
}