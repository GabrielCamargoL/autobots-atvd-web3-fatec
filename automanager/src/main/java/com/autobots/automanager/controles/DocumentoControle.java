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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelos.AdicionadorLinkCliente;
import com.autobots.automanager.modelos.ClienteSelecionador;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
public class DocumentoControle {
	@Autowired
	private ClienteRepositorio repositorioCliente;
	@Autowired
	private DocumentoRepositorio repositorioDocumento;

	@Autowired
	private ClienteSelecionador selecionador;
	@Autowired
	private AdicionadorLinkCliente adicionadorLink;


	@GetMapping("/documento/{id}")
	public Optional<Documento> obterDocumento(@PathVariable long id) {
		Optional<Documento> documento = repositorioDocumento.findById(id);
		if (documento.isEmpty()) {
			ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return documento;
		} else {
			// adicionadorLink.adicionarLink(documento);
			// ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
			return documento;
		}
	}

	@GetMapping("/documentos/{clienteId}")
	public List<Documento> obterDocumentos(@PathVariable long clienteId) {
		Cliente cliente = repositorioCliente.getById(clienteId);
		if (cliente == null) {
			// ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return null;
		} else {
			List<Documento> documentos = cliente.getDocumentos();
			return documentos;
		}
	}

	@PostMapping("/documento/cadastro/{clientId}")
	public ResponseEntity<?> cadastrarDocumento(@PathVariable long clientId, @RequestBody Documento documento) {
		HttpStatus status = HttpStatus.CONFLICT;
		Cliente cliente = repositorioCliente.getById(clientId);
		List<Documento> listaDocumentos = cliente.getDocumentos();

		if (cliente != null || documento != null) {
			listaDocumentos.add(documento);
			repositorioCliente.save(cliente);
			status = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(status);

	}

	@PutMapping("/documento/atualizar")
	public ResponseEntity<?> atualizarDocumento(@RequestBody Documento atualizacao) {
		HttpStatus status = HttpStatus.CONFLICT;
		Documento documento = repositorioDocumento.getById(atualizacao.getId());
		if (documento != null) {
			DocumentoAtualizador atualizador = new DocumentoAtualizador();
			atualizador.atualizar(documento, atualizacao);
			repositorioDocumento.save(documento);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		return new ResponseEntity<>(status);
	}

	@DeleteMapping("/documento/excluir/{clienteId}/{documentoId}")
	public ResponseEntity<?> excluirDocumento(@PathVariable long clienteId, @PathVariable long documentoId) {
		HttpStatus status = HttpStatus.BAD_REQUEST;

		Cliente cliente = repositorioCliente.getById(clienteId);
		List<Documento> listaDocumento = cliente.getDocumentos();

		if (cliente != null) {
			Documento documentoEncontrado = null;
			for (Documento documento : listaDocumento) {
				if (documento.getId() == documentoId) {
					documentoEncontrado = documento;
				}
			}
			
			if (documentoEncontrado == null) {
				status = HttpStatus.NOT_FOUND;
				return new ResponseEntity<>(status);
			}

			listaDocumento.remove(documentoEncontrado);
			repositorioCliente.save(cliente);
			status = HttpStatus.OK;
		}

		return new ResponseEntity<>(status);
	}
}