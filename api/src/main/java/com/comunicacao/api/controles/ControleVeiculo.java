package com.comunicacao.api.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Veiculo;

@RestController
public class ControleVeiculo {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @GetMapping("/todos-veiculos")
  public ResponseEntity<?> obterVeiculos() {
    List<Veiculo> veiculos = new ArrayList<>();

    ResponseEntity<? extends List> resposta = new RestTemplate()
        .getForEntity("http://localhost:8080/empresas/veiculos", veiculos.getClass());
    veiculos = resposta.getBody();

    return new ResponseEntity<List<Veiculo>>(veiculos, HttpStatus.FOUND);
  }
}