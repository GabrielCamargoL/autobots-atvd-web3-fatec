package com.autobots.automanager.controles;

import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioServico;
import com.autobots.automanager.servicos.ServicoEmpresa;

@RestController
public class ServicoControle {
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private RepositorioServico repositorioServico;

	@Autowired
	private ServicoEmpresa servicoEmpresa;

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> obterServicos() {
		List<Servico> servicos = repositorioServico.findAll();

		if (servicos.size() == 0) {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/servico/{id}")
	public ResponseEntity<Servico> obterServico(@PathVariable long id) {
		Optional<Servico> servico = repositorioServico.findById(id);
		if (servico == null) {
			ResponseEntity<Servico> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/servicosEmpresa/{empresaId}")
	public ResponseEntity<Set<Servico>> obterServicosPorEmpresa(@PathVariable long empresaId) {
		Empresa empresa = repositorioEmpresa.getById(empresaId);
		if (empresa == null) {
			ResponseEntity<Set<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			Set<Servico> servicos = empresa.getServicos();
			ResponseEntity<Set<Servico>> resposta = new ResponseEntity<Set<Servico>>(servicos, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/servico/cadastro/{empresaId}")
	public ResponseEntity<?> cadastrarServico(@PathVariable long empresaId, @RequestBody Servico servico) {

		HttpStatus status = HttpStatus.CONFLICT;
		Optional<Empresa> empresa = repositorioEmpresa.findById(empresaId);

		if (empresa.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}

		Empresa empresaCadastrada = servicoEmpresa.cadastrarServico(empresa.get(), servico);
		status = HttpStatus.CREATED;
		return new ResponseEntity<>(empresaCadastrada, status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@DeleteMapping("/servico/excluir/{empresaId}/{servicoId}")
	public ResponseEntity<?> excluirServicoEmpresa(@PathVariable long empresaId, @PathVariable long servicoId) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		Optional<Empresa> empresaOptional = repositorioEmpresa.findById(empresaId);
		if (empresaOptional.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Empresa empresa = empresaOptional.get();
		Set<Servico> listaServico = empresa.getServicos();

		Servico servicoEncontrado = null;
		for (Servico servicoIterado : listaServico) {
			if (servicoIterado.getId() == servicoId) {
				servicoEncontrado = servicoIterado;
			}
		}

		listaServico.remove(servicoEncontrado);
		repositorioEmpresa.save(empresa);
		status = HttpStatus.OK;

		return new ResponseEntity<>(status);
	}
}
