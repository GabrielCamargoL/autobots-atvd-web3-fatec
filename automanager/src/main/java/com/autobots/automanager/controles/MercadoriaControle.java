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
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.servicos.ServicoEmpresa;

@RestController
public class MercadoriaControle {
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private RepositorioMercadoria repositorioMercadoria;

	@Autowired
	private ServicoEmpresa servicoEmpresa;

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> obterMercadorias() {
		List<Mercadoria> mercadorias = repositorioMercadoria.findAll();

		if (mercadorias.size() == 0) {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/mercadoria/{id}")
	public ResponseEntity<Mercadoria> obterMercadoria(@PathVariable long id) {
		Optional<Mercadoria> mercadoria = repositorioMercadoria.findById(id);
		if (mercadoria == null) {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/mercadoriasUsuario/{empresaId}")
	public ResponseEntity<Set<Mercadoria>> obterMercadoriasPorEmpresa(@PathVariable long empresaId) {
		Empresa empresa = repositorioEmpresa.getById(empresaId);
		if (empresa == null) {
			ResponseEntity<Set<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			Set<Mercadoria> mercadorias = empresa.getMercadorias();
			ResponseEntity<Set<Mercadoria>> resposta = new ResponseEntity<Set<Mercadoria>>(mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/mercadoria/cadastro/{empresaId}")
	public ResponseEntity<?> cadastrarMercadoria(@PathVariable long empresaId, @RequestBody Mercadoria mercadoria) {

		HttpStatus status = HttpStatus.CONFLICT;
		Optional<Empresa> empresa = repositorioEmpresa.findById(empresaId);

		if (empresa.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}

		Empresa empresaAtualizada = servicoEmpresa.cadastrarMercadoria(empresa.get(), mercadoria);
		status = HttpStatus.CREATED;
		return new ResponseEntity<>(empresaAtualizada, status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@DeleteMapping("/mercadoria/excluir/{empresaId}/{mercadoriaId}")
	public ResponseEntity<Empresa> excluirMercadoriaUsuario(@PathVariable long empresaId,
			@PathVariable long mercadoriaId) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		Optional<Empresa> empresaOptional = repositorioEmpresa.findById(empresaId);
		if (empresaOptional.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Empresa empresa = empresaOptional.get();
		Set<Mercadoria> listaMercadoria = empresa.getMercadorias();

		Mercadoria mercadoriaEncontrada = null;
		for (Mercadoria mercadoriaIterada : listaMercadoria) {
			if (mercadoriaIterada.getId() == mercadoriaId) {
				mercadoriaEncontrada = mercadoriaIterada;
			}
		}

		listaMercadoria.remove(mercadoriaEncontrada);
		repositorioEmpresa.save(empresa);
		status = HttpStatus.OK;

		return new ResponseEntity<>(status);
	}
}
