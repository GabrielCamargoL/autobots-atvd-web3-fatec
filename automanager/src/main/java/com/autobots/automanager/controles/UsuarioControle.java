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

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.hateaosDAO.UsuarioHateoas;
import com.autobots.automanager.modelos.adicionadoresLinks.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.atualizadores.UsuarioAtualizador;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.servicos.ServicoUsuario;

@RestController
public class UsuarioControle {
	@Autowired
	private RepositorioUsuario repositorio;
	@Autowired
	private ServicoUsuario servicoUsuario;
	@Autowired
	private AdicionadorLinkUsuario adicionadorLink;

	@PreAuthorize("hasRole('CLIENTE') or hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/usuario/{id}")
	public ResponseEntity<UsuarioHateoas> obterUsuario(@PathVariable long id) {

		UsuarioHateoas usuario = servicoUsuario.findById(id);
		if (usuario == null) {
			ResponseEntity<UsuarioHateoas> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuario);
			ResponseEntity<UsuarioHateoas> resposta = new ResponseEntity<UsuarioHateoas>(usuario, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuarioHateoas>> obterUsuarios() {

		List<UsuarioHateoas> usuarios = servicoUsuario.findAll();
		if (usuarios.isEmpty()) {
			ResponseEntity<List<UsuarioHateoas>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<List<UsuarioHateoas>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/usuario/cadastro")
	public ResponseEntity<Usuario> cadastrarCliente(@RequestBody Usuario usuario) {

		HttpStatus status = HttpStatus.CONFLICT;
		if (usuario.getId() == null) {
			Usuario usuarioCriado = servicoUsuario.cadastrar(usuario);
			status = HttpStatus.CREATED;
			return new ResponseEntity<Usuario>(usuarioCriado, status);
		}
		return new ResponseEntity<Usuario>(status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/usuario/veiculo/cadastro/{usuarioId}")
	public ResponseEntity<Veiculo> cadastrarCliente(@PathVariable Long usuarioId, @RequestBody Veiculo veiculo) {

		Optional<Usuario> usuario = repositorio.findById(usuarioId);
		if (usuario.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		Veiculo veiculoCriado = servicoUsuario.cadastrarVeiculo(usuario.get(), veiculo);
		return new ResponseEntity<Veiculo>(veiculoCriado, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PutMapping("/usuario/atualizar")
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) {

		HttpStatus status = HttpStatus.CONFLICT;
		Usuario usuarioEncontrado = repositorio.getById(usuario.getId());
		if (usuarioEncontrado != null) {
			UsuarioAtualizador atualizador = new UsuarioAtualizador();
			atualizador.atualizar(usuarioEncontrado, usuario);
			repositorio.save(usuarioEncontrado);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<Usuario>(status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@DeleteMapping("/usuario/excluir")
	public ResponseEntity<?> excluirUsuario(@RequestBody Usuario usuario) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		Usuario usuarioEncontrado = repositorio.getById(usuario.getId());
		if (usuarioEncontrado != null) {
			repositorio.deleteById(usuario.getId());
			status = HttpStatus.OK;
		}
		return new ResponseEntity<>(status);
	}
}
