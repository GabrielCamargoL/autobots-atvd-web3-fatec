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

import com.autobots.automanager.entidades.Usuario;
// import com.autobots.automanager.modelos.AdicionadorLinkUsuario;
import com.autobots.automanager.modelos.UsuarioAtualizador;
import com.autobots.automanager.repositorios.RepositorioUsuario;

@RestController
public class UsuarioControle {
	@Autowired
	private RepositorioUsuario repositorio;
	// @Autowired
	// private AdicionadorLinkUsuario adicionadorLink;

	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> obterUsuario(@PathVariable long id) {
		Optional<Usuario> usuario = repositorio.findById(id);
		if (usuario.isEmpty()) {
			ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			// adicionadorLink.adicionarLink(usuario.get());
			ResponseEntity<Usuario> resposta = new ResponseEntity<Usuario>(usuario.get(), HttpStatus.FOUND);
			return resposta;
		}
	}

	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> obterUsuarios() {
		List<Usuario> usuarios = repositorio.findAll();
		if (usuarios.isEmpty()) {
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			// adicionadorLink.adicionarLink(usuarios);
			ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(usuarios, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PostMapping("/usuario/cadastro")
	public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
		HttpStatus status = HttpStatus.CONFLICT;
		if (usuario.getId() == null) {
			repositorio.save(usuario);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<Usuario>(status);

	}

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
