package com.comunicacao.api.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Venda;

@RestController
public class ControleVenda {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @GetMapping("/todas-vendas")
  public ResponseEntity<?> obterVendas() {
    List<Venda> vendas = new ArrayList<>();

    ResponseEntity<? extends List> resposta = new RestTemplate()
        .getForEntity("http://localhost:8080/empresas/vendas", vendas.getClass());
    vendas = resposta.getBody();

    return new ResponseEntity<List<Venda>>(vendas, HttpStatus.FOUND);
  }
}