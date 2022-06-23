package com.autobots.automanager.controles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.adicionadoresLinks.AdicionadorLinkVeiculo;
import com.autobots.automanager.modelos.atualizadores.VeiculoAtualizador;
import com.autobots.automanager.repositorios.RepositorioVeiculo;
import com.autobots.automanager.servicos.ServicoVeiculo;

@RestController
public class VeiculoControle {
	@Autowired
	private RepositorioVeiculo repositorio;
	@Autowired
	private ServicoVeiculo servicoVeiculo;
	@Autowired
	private AdicionadorLinkVeiculo adicionadorLink;

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/veiculo/{id}")
	public ResponseEntity<Veiculo> obterVeiculo(@PathVariable long id) {
		Optional<Veiculo> veiculo = repositorio.findById(id);
		if (veiculo.isEmpty()) {
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(veiculo.get());
			ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(veiculo.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> obterVeiculos() {
		List<Veiculo> veiculos = repositorio.findAll();
		if (veiculos.isEmpty()) {
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(veiculos);
			ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(veiculos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/veiculo/cadastro")
	public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (veiculo.getId() == null) {
			Veiculo veiculoCriado = servicoVeiculo.cadastrar(veiculo);
			status = HttpStatus.CREATED;
			return new ResponseEntity<Veiculo>(veiculoCriado, status);
		}
		return new ResponseEntity<Veiculo>(status);

	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PutMapping("/veiculo/atualizar")
	public ResponseEntity<Veiculo> atualizarVeiculo(@RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.CONFLICT;
		Veiculo veiculoEncontrado = repositorio.getById(veiculo.getId());
		if (veiculoEncontrado != null) {
			VeiculoAtualizador atualizador = new VeiculoAtualizador();
			atualizador.atualizar(veiculoEncontrado, veiculo);
			repositorio.save(veiculoEncontrado);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Veiculo>(status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@DeleteMapping("/veiculo/excluir")
	public ResponseEntity<?> excluirVeiculo(@RequestBody Veiculo veiculo) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Veiculo veiculoEncontrado = repositorio.getById(veiculo.getId());
		if (veiculoEncontrado != null) {
			repositorio.deleteById(veiculo.getId());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
