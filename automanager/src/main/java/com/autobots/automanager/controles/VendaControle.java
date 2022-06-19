package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.repositorios.RepositorioVenda;
import com.autobots.automanager.servicos.ServicoVenda;

@RestController
public class VendaControle {
	@Autowired
	private RepositorioVenda repositorio;
	@Autowired
	private ServicoVenda servicoVenda;

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

	@PostMapping("/venda/cadastro")
	public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (venda.getId() == null) {
			Venda vendaCriado = servicoVenda.cadastrar(venda);
			status = HttpStatus.CREATED;
			return new ResponseEntity<Venda>(vendaCriado, status);
		}
		return new ResponseEntity<Venda>(status);

	}

	@DeleteMapping("/venda/excluir")
	public ResponseEntity<?> excluirVenda(@RequestBody Venda venda) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Venda vendaEncontrado = repositorio.getById(venda.getId());
		if (vendaEncontrado != null) {
			repositorio.deleteById(venda.getId());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
