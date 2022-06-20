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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.adicionadoresLinks.AdicionadorLinkEmpresa;
import com.autobots.automanager.modelos.atualizadores.EmpresaAtualizador;
import com.autobots.automanager.repositorios.RepositorioEmpresa;
import com.autobots.automanager.servicos.ServicoEmpresa;

@RestController
@RequestMapping("/empresas")
public class EmpresaControle {
	@Autowired
	private RepositorioEmpresa repositorioEmpresa;
	@Autowired
	private ServicoEmpresa servicoEmpresa;
	@Autowired
	private AdicionadorLinkEmpresa adicionadorLink;

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> obterEmpresa(@PathVariable long id) {
		Optional<Empresa> empresa = repositorioEmpresa.findById(id);
		if (empresa.isEmpty()) {
			ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresa.get());
			ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/")
	public ResponseEntity<List<Empresa>> obterEmpresas() {
		List<Empresa> empresas = repositorioEmpresa.findAll();
		if (empresas.isEmpty()) {
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(empresas);
			ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(empresas, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/cadastro")
	public ResponseEntity<Empresa> cadastrarEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (empresa.getId() == null) {
			Empresa empresaCriada = servicoEmpresa.cadastrar(empresa);
			status = HttpStatus.CREATED;
			return new ResponseEntity<Empresa>(empresaCriada, status);
		}
		return new ResponseEntity<Empresa>(status);
	}

	@PostMapping("/cadastro/servico/{empresaId}")
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

	@PostMapping("/cadastro/venda/{empresaId}")
	public ResponseEntity<Venda> cadastrarVenda(@PathVariable long empresaId, @RequestBody Venda venda) {
		HttpStatus status = HttpStatus.CONFLICT;
		Optional<Empresa> empresa = repositorioEmpresa.findById(empresaId);

		if (empresa.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}

		Venda vendaCriada = servicoEmpresa.cadastrarVenda(empresa.get(), venda);
		status = HttpStatus.CREATED;
		return new ResponseEntity<Venda>(vendaCriada, status);
	}

	@PostMapping("/cadastro/funcionario/{empresaId}")
	public ResponseEntity<?> cadastrarFuncionario(@PathVariable long empresaId, @RequestBody Usuario funcionario) {

		HttpStatus status = HttpStatus.CONFLICT;
		Optional<Empresa> empresa = repositorioEmpresa.findById(empresaId);

		if (empresa.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}

		Empresa empresaCadastrada = servicoEmpresa.cadastrarFuncionario(empresa.get(), funcionario);
		status = HttpStatus.OK;
		return new ResponseEntity<>(empresaCadastrada, status);
	}

	@PutMapping("/atualizar")
	public ResponseEntity<Empresa> atualizarEmpresa(@RequestBody Empresa Empresa) {
		HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresaEncontrado = repositorioEmpresa.getById(Empresa.getId());
		if (empresaEncontrado != null) {
			EmpresaAtualizador atualizador = new EmpresaAtualizador();
			atualizador.atualizar(empresaEncontrado, Empresa);
			repositorioEmpresa.save(empresaEncontrado);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Empresa>(status);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<?> excluirEmpresa(@RequestBody Empresa empresa) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Empresa empresaEncontrado = repositorioEmpresa.getById(empresa.getId());
		if (empresaEncontrado != null) {
			repositorioEmpresa.deleteById(empresa.getId());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
