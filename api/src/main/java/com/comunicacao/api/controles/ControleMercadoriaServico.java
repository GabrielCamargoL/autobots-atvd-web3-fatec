package com.comunicacao.api.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.MercadoriaServico;

@RestController
public class ControleMercadoriaServico {
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @GetMapping("/todas-mercadorias-servicos")
  public ResponseEntity<?> obterMercadoriaServico() {
    List<MercadoriaServico> mercadoriaServico = new ArrayList<>();

    ResponseEntity<? extends List> resposta = new RestTemplate()
        .getForEntity("http://localhost:8080/empresas/mercadorias-servicos", mercadoriaServico.getClass());
    mercadoriaServico = resposta.getBody();

    return new ResponseEntity<List<MercadoriaServico>>(mercadoriaServico, HttpStatus.FOUND);
  }
}