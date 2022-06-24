package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.controles.dto.ServicoMercadoriaEmpresaDTO;
import com.autobots.automanager.controles.dto.UsuariosPorEmpresaDTO;
import com.autobots.automanager.controles.dto.VeiculosPorEmpresaDTO;
import com.autobots.automanager.controles.dto.VendasPorEmpresaDTO;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	// ---------------------------------------------------------------------------------------------------------

	@GetMapping("/usuarios")
	public ResponseEntity<List<UsuariosPorEmpresaDTO>> obterUsuariosPorEmpresa() {
		List<Empresa> listaEmpresas = repositorioEmpresa.findAll();
		if (listaEmpresas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<UsuariosPorEmpresaDTO> usuariosPorEmpresa = new ArrayList<>();
		for (Empresa empresa : listaEmpresas) {
			UsuariosPorEmpresaDTO empresaUsuarioDto = new UsuariosPorEmpresaDTO(empresa);
			usuariosPorEmpresa.add(empresaUsuarioDto);
		}

		return new ResponseEntity<List<UsuariosPorEmpresaDTO>>(usuariosPorEmpresa, HttpStatus.FOUND);
	}

	@GetMapping("/vendas")
	public ResponseEntity<List<VendasPorEmpresaDTO>> obterVendasPorEmpresa() {
		List<Empresa> listaEmpresas = repositorioEmpresa.findAll();
		if (listaEmpresas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<VendasPorEmpresaDTO> vendasPorEmpresa = new ArrayList<>();
		for (Empresa empresa : listaEmpresas) {
			VendasPorEmpresaDTO empresaVendaDto = new VendasPorEmpresaDTO(empresa);
			vendasPorEmpresa.add(empresaVendaDto);
		}

		return new ResponseEntity<List<VendasPorEmpresaDTO>>(vendasPorEmpresa, HttpStatus.FOUND);
	}

	@GetMapping("/mercadorias-servicos")
	public ResponseEntity<List<ServicoMercadoriaEmpresaDTO>> obterMercadoriaServicoPorEmpresa() {
		List<Empresa> listaEmpresas = repositorioEmpresa.findAll();
		if (listaEmpresas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<ServicoMercadoriaEmpresaDTO> mercadoriasServicosPorEmpresa = new ArrayList<>();
		for (Empresa empresa : listaEmpresas) {
			ServicoMercadoriaEmpresaDTO empresaMercadoriaServicosDto = new ServicoMercadoriaEmpresaDTO(empresa);
			mercadoriasServicosPorEmpresa.add(empresaMercadoriaServicosDto);
		}

		return new ResponseEntity<List<ServicoMercadoriaEmpresaDTO>>(mercadoriasServicosPorEmpresa, HttpStatus.FOUND);
	}

	@GetMapping("/veiculos")
	public ResponseEntity<List<VeiculosPorEmpresaDTO>> obterVeiculosAtendidoPorEmpresa() {
		List<Empresa> listaEmpresas = repositorioEmpresa.findAll();
		if (listaEmpresas.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		List<VeiculosPorEmpresaDTO> veiculosPorEmpresaDTOs = new ArrayList<>();
		for (Empresa empresa : listaEmpresas) {
			Set<Venda> vendas = empresa.getVendas();
			List<Veiculo> veiculos = new ArrayList<Veiculo>();
			for (Venda venda : vendas) {
				Veiculo veiculoDaVenda = venda.getVeiculo();
				veiculos.add(veiculoDaVenda);
			}
			VeiculosPorEmpresaDTO veiculoPorEmpresaDto = new VeiculosPorEmpresaDTO(empresa, veiculos);
			veiculosPorEmpresaDTOs.add(veiculoPorEmpresaDto);
		}

		return new ResponseEntity<List<VeiculosPorEmpresaDTO>>(veiculosPorEmpresaDTOs, HttpStatus.FOUND);
	}

	// ---------------------------------------------------------------------------------------------------------

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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

	@PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('GERENTE')")
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
