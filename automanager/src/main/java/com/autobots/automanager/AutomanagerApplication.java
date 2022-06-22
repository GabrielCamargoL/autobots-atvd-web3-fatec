package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enums.PerfilUsuario;
import com.autobots.automanager.enums.TipoDocumento;
import com.autobots.automanager.enums.TipoVeiculo;
import com.autobots.automanager.repositorios.RepositorioEmpresa;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private RepositorioEmpresa repositorioEmpresa;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Car service toyota ltda");
		empresa.setNomeFantasia("Car service manutenção veicular");
		empresa.setCadastro(new Date());

		Endereco enderecoEmpresa = new Endereco();
		enderecoEmpresa.setEstado("São Paulo");
		enderecoEmpresa.setCidade("São Paulo");
		enderecoEmpresa.setBairro("Centro");
		enderecoEmpresa.setRua("Av. São João");
		enderecoEmpresa.setNumero("00");
		enderecoEmpresa.setCodigoPostal("01035-000");

		empresa.setEndereco(enderecoEmpresa);

		Telefone telefoneEmpresa = new Telefone();
		telefoneEmpresa.setDdd("011");
		telefoneEmpresa.setNumero("986454527");

		empresa.getTelefones().add(telefoneEmpresa);

		Usuario adminstrador = new Usuario();
		adminstrador.setNome("Pedro Alcântara de Bragança e Bourbon");
		adminstrador.setNomeSocial("Dom Pedro");
		adminstrador.getPerfis().add(PerfilUsuario.ADMINISTRADOR);
		adminstrador.setEmail("a@a.com");
		adminstrador.setSenha("1234");
		String senhaEncriptada = bcrypt.encode(adminstrador.getSenha());
		adminstrador.setSenha(senhaEncriptada);

		Endereco enderecoAdminstrador = new Endereco();
		enderecoAdminstrador.setEstado("São Paulo");
		enderecoAdminstrador.setCidade("São Paulo");
		enderecoAdminstrador.setBairro("Jardins");
		enderecoAdminstrador.setRua("Av. São Gabriel");
		enderecoAdminstrador.setNumero("00");
		enderecoAdminstrador.setCodigoPostal("01435-001");

		adminstrador.setEndereco(enderecoAdminstrador);

		empresa.getUsuarios().add(adminstrador);

		Telefone telefoneAdminstrador = new Telefone();
		telefoneAdminstrador.setDdd("011");
		telefoneAdminstrador.setNumero("9854633728");

		adminstrador.getTelefones().add(telefoneAdminstrador);

		Documento cpf = new Documento();
		cpf.setDataEmissao(new Date());
		cpf.setNumero("856473819229");
		cpf.setTipo(TipoDocumento.CPF);

		adminstrador.getDocumentos().add(cpf);

		Usuario gerente = new Usuario();
		gerente.setNome("Componentes varejo de partes automotivas ltda");
		gerente.setNomeSocial("Loja do carro, vendas de componentes automotivos");
		gerente.getPerfis().add(PerfilUsuario.GERENTE);
		gerente.setEmail("gerente@gerente.com");
		gerente.setSenha("1234");
		String senhaGerenteEncriptada = bcrypt.encode(gerente.getSenha());
		gerente.setSenha(senhaGerenteEncriptada);

		Documento cnpj = new Documento();
		cnpj.setDataEmissao(new Date());
		cnpj.setNumero("00014556000100");
		cnpj.setTipo(TipoDocumento.CNPJ);

		gerente.getDocumentos().add(cnpj);

		Endereco enderecoGerente = new Endereco();
		enderecoGerente.setEstado("Rio de Janeiro");
		enderecoGerente.setCidade("Rio de Janeiro");
		enderecoGerente.setBairro("Centro");
		enderecoGerente.setRua("Av. República do chile");
		enderecoGerente.setNumero("00");
		enderecoGerente.setCodigoPostal("20031-170");

		gerente.setEndereco(enderecoGerente);

		empresa.getUsuarios().add(gerente);

		Mercadoria rodaLigaLeve = new Mercadoria();
		rodaLigaLeve.setCadastro(new Date());
		rodaLigaLeve.setFabricao(new Date());
		rodaLigaLeve.setNome("Roda de liga leva modelo toyota etios");
		rodaLigaLeve.setValidade(new Date());
		rodaLigaLeve.setQuantidade(30);
		rodaLigaLeve.setValor(300.0);
		rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyta para modelos do tipo hatch");

		empresa.getMercadorias().add(rodaLigaLeve);

		gerente.getMercadorias().add(rodaLigaLeve);

		Usuario cliente = new Usuario();
		cliente.setNome("Pedro Alcântara de Bragança e Bourbon");
		cliente.setNomeSocial("Dom pedro Usuario");
		cliente.getPerfis().add(PerfilUsuario.CLIENTE);
		cliente.setEmail("usuario@usuario.com");
		cliente.setSenha("1234");
		String senhaUsuarioEncriptada = bcrypt.encode(cliente.getSenha());
		cliente.setSenha(senhaUsuarioEncriptada);

		Documento cpfCliente = new Documento();
		cpfCliente.setDataEmissao(new Date());
		cpfCliente.setNumero("12584698533");
		cpfCliente.setTipo(TipoDocumento.CPF);

		cliente.getDocumentos().add(cpfCliente);

		Endereco enderecoCliente = new Endereco();
		enderecoCliente.setEstado("São Paulo");
		enderecoCliente.setCidade("São José dos Campos");
		enderecoCliente.setBairro("Centro");
		enderecoCliente.setRua("Av. Dr. Nelson D'Ávila");
		enderecoCliente.setNumero("00");
		enderecoCliente.setCodigoPostal("12245-070");

		cliente.setEndereco(enderecoCliente);

		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca("ABC-0000");
		veiculo.setModelo("corolla-cross");
		veiculo.setTipo(TipoVeiculo.SUV);
		veiculo.setProprietario(cliente);

		cliente.getVeiculos().add(veiculo);

		empresa.getUsuarios().add(cliente);

		Servico trocaRodas = new Servico();
		trocaRodas.setDescricao("Troca das rodas do carro por novas");
		trocaRodas.setNome("Troca de rodas");
		trocaRodas.setValor(50);

		Servico alinhamento = new Servico();
		alinhamento.setDescricao("Alinhamento das rodas do carro");
		alinhamento.setNome("Alinhamento de rodas");
		alinhamento.setValor(50);

		empresa.getServicos().add(alinhamento);
		empresa.getServicos().add(trocaRodas);

		repositorioEmpresa.save(empresa);
	}
}