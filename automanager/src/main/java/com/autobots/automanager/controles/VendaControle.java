package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;

@RestController
public class VendaControle {
	@Autowired
	private RepositorioVenda repositorio;

	@GetMapping("/venda/{id}")
	public ResponseEntity<Venda> obterVenda(@PathVariable long id) {
		Optional<Venda> venda = repositorio.findById(id);
		if (venda.isEmpty()) {
			ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<Venda> resposta = new ResponseEntity<Venda>(venda.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> obterVendas() {
		List<Venda> vendas = repositorio.findAll();
		if (vendas.isEmpty()) {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(vendas, HttpStatus.FOUND);
			return resposta;
		}
	}
}
