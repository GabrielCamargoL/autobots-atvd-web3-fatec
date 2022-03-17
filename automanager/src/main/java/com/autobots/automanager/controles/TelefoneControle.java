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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
public class TelefoneControle {
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private TelefoneRepositorio repositorioTelefone;

	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;


	@GetMapping("/telefone/{id}")
	public Optional<Telefone> obterTelefone(@PathVariable long id) {
		Optional<Telefone> telefone = repositorioTelefone.findById(id);
		if (telefone == null) {
			ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return telefone;
		} else {
			// adicionadorLink.adicionarLink(telefone);
			// ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
			return telefone;
		}
	}

	@GetMapping("/telefones/{clienteId}")
	public List<Telefone> obterTelefonesPorCliente(@PathVariable long clienteId) {
		Cliente cliente = repositorioCliente.getById(clienteId);
		if (cliente == null) {
			// ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return null;
		} else {
			List<Telefone> telefones = cliente.getTelefones();
			return telefones;
		}
	}

  @GetMapping("/telefones")
	public List<Telefone> obterTelefones() {
		List<Telefone> telefones = repositorioTelefone.findAll();
		return telefones;
	}
	

	@PostMapping("/telefone/cadastro/{clientId}")
	public ResponseEntity<?> cadastrarTelefone(@PathVariable long clientId, @RequestBody Telefone telefone) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.getById(clientId);
		List<Telefone> listaTelefones = cliente.getTelefones();

		if (cliente != null || telefone != null) {
			listaTelefones.add(telefone);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}

	@PutMapping("/telefone/atualizar")
	public ResponseEntity<?> atualizarTelefone(@RequestBody Telefone atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Telefone telefone = repositorioTelefone.getById(atualizacao.getId());
		if (telefone != null) {
			TelefoneAtualizador atualizador = new TelefoneAtualizador();
			atualizador.atualizar(telefone, atualizacao);
			repositorioTelefone.save(telefone);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/telefone/excluir/{clienteId}/{telefoneId}")
	public ResponseEntity<?> excluirTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Cliente cliente = repositorioCliente.getById(clienteId);
		List<Telefone> listaTelefone = cliente.getTelefones();

		if (cliente != null) {
			Telefone telefoneEncontrado = null;
			
      for (Telefone telefone : listaTelefone) {
				if (telefone.getId() == telefoneId) {
					telefoneEncontrado = telefone;
				}
			}
			
			if (telefoneEncontrado == null) {
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<>(status);
			}

			listaTelefone.remove(telefoneEncontrado);
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<>(status);
	}
}
