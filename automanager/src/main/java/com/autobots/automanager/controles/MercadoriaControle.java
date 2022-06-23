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

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import com.autobots.automanager.servicos.ServicoUsuario;

@RestController
public class MercadoriaControle {
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private RepositorioMercadoria repositorioMercadoria;

	@Autowired
	private ServicoUsuario servicoUsuario;

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
	@GetMapping("/mercadoriasUsuario/{usuarioId}")
	public ResponseEntity<Set<Mercadoria>> obterMercadoriasPorUsuario(@PathVariable long usuarioId) {
		Usuario usuario = repositorioUsuario.getById(usuarioId);
		if (usuario == null) {
			ResponseEntity<Set<Mercadoria>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta;
		} else {
			Set<Mercadoria> mercadorias = usuario.getMercadorias();
			ResponseEntity<Set<Mercadoria>> resposta = new ResponseEntity<Set<Mercadoria>>(mercadorias, HttpStatus.FOUND);
			return resposta;
		}
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@PostMapping("/mercadoria/cadastro/{usuarioId}")
	public ResponseEntity<?> cadastrarMercadoria(@PathVariable long usuarioId, @RequestBody Mercadoria mercadoria) {

		HttpStatus status = HttpStatus.CONFLICT;
		Optional<Usuario> usuario = repositorioUsuario.findById(usuarioId);

		if (usuario.isEmpty()) {
			status = HttpStatus.NOT_FOUND;
			return new ResponseEntity<>(status);
		}

		Usuario usuarioCadastrado = servicoUsuario.cadastrarMercadoria(usuario.get(), mercadoria);
		status = HttpStatus.CREATED;
		return new ResponseEntity<>(usuarioCadastrado, status);
	}

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE') or hasRole('VENDEDOR')")
	@DeleteMapping("/mercadoria/excluir/{usuarioId}/{mercadoriaId}")
	public ResponseEntity<?> excluirMercadoriaUsuario(@PathVariable long usuarioId, @PathVariable long mercadoriaId) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		Optional<Usuario> UsuarioOptional = repositorioUsuario.findById(usuarioId);
		if (UsuarioOptional.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Usuario usuario = UsuarioOptional.get();
		Set<Mercadoria> listaMercadoria = usuario.getMercadorias();

		Mercadoria mercadoriaEncontrada = null;
		for (Mercadoria mercadoriaIterada : listaMercadoria) {
			if (mercadoriaIterada.getId() == mercadoriaId) {
				mercadoriaEncontrada = mercadoriaIterada;
			}
		}

		listaMercadoria.remove(mercadoriaEncontrada);
		repositorioUsuario.save(usuario);
		status = HttpStatus.OK;

		return new ResponseEntity<>(status);
	}
}
