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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.atualizadores.EmpresaAtualizador;
import com.autobots.automanager.modelos.hateoas.AdicionadorLinkEmpresa;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.servicos.ServicoEmpresa;

@RestController
public class EmpresaControle {
	@Autowired
	private RepositorioEmpresa repositorio;
	@Autowired
	private ServicoEmpresa servicoEmpresa;
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;

	@GetMapping("/empresa/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
		Optional<Empresa> empresa = repositorio.findById(id);
		if (empresa.isEmpty()) {
			ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresa.get());
			ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> obterEmpresas() {
		List<Empresa> empresas = repositorio.findAll();
		if (empresas.isEmpty()) {
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresas);
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/empresa/cadastro")
	public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (empresa.getId() == null) {
			Empresa empresaCriada = servicoEmpresa.cadastrar(empresa);
			status = HttpStatus.CREATED;
			return new ResponseEntity<Empresa>(empresaCriada, status);
		}
		return new ResponseEntity<Empresa>(status);
	}

	@PutMapping("/empresa/atualizar")
	public ResponseEntity<Empresa> atualizarEmpresa(@RequestBody Empresa Empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresaEncontrado = repositorio.getById(Empresa.getId());
		if (empresaEncontrado != null) {
			EmpresaAtualizador atualizador = new EmpresaAtualizador();
			atualizador.atualizar(empresaEncontrado, Empresa);
			repositorio.save(empresaEncontrado);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Empresa>(status);
	}

	@DeleteMapping("/empresa/excluir")
	public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Empresa empresaEncontrado = repositorio.getById(empresa.getId());
		if (empresaEncontrado != null) {
			repositorio.deleteById(empresa.getId());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
