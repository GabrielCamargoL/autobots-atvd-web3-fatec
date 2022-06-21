package com.autobots.automanager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		Usuario funcionario = new Usuario();
		funcionario.setNome("Pedro Alcântara de Bragança e Bourbon");
		funcionario.setNomeSocial("Dom Pedro");
		funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
		funcionario.setEmail("a@a.com");
		funcionario.setSenha("1234");

		Endereco enderecoFuncionario = new Endereco();
		enderecoFuncionario.setEstado("São Paulo");
		enderecoFuncionario.setCidade("São Paulo");
		enderecoFuncionario.setBairro("Jardins");
		enderecoFuncionario.setRua("Av. São Gabriel");
		enderecoFuncionario.setNumero("00");
		enderecoFuncionario.setCodigoPostal("01435-001");

		funcionario.setEndereco(enderecoFuncionario);

		empresa.getUsuarios().add(funcionario);

		Telefone telefoneFuncionario = new Telefone();
		telefoneFuncionario.setDdd("011");
		telefoneFuncionario.setNumero("9854633728");

		funcionario.getTelefones().add(telefoneFuncionario);

		Documento cpf = new Documento();
		cpf.setDataEmissao(new Date());
		cpf.setNumero("856473819229");
		cpf.setTipo(TipoDocumento.CPF);

		funcionario.getDocumentos().add(cpf);

		Usuario fornecedor = new Usuario();
		fornecedor.setNome("Componentes varejo de partes automotivas ltda");
		fornecedor.setNomeSocial("Loja do carro, vendas de componentes automotivos");
		fornecedor.getPerfis().add(PerfilUsuario.FORNECEDOR);
		fornecedor.setEmail("fornecedor@fornece.com");
		fornecedor.setSenha("1234");

		Documento cnpj = new Documento();
		cnpj.setDataEmissao(new Date());
		cnpj.setNumero("00014556000100");
		cnpj.setTipo(TipoDocumento.CNPJ);

		fornecedor.getDocumentos().add(cnpj);

		Endereco enderecoFornecedor = new Endereco();
		enderecoFornecedor.setEstado("Rio de Janeiro");
		enderecoFornecedor.setCidade("Rio de Janeiro");
		enderecoFornecedor.setBairro("Centro");
		enderecoFornecedor.setRua("Av. República do chile");
		enderecoFornecedor.setNumero("00");
		enderecoFornecedor.setCodigoPostal("20031-170");

		fornecedor.setEndereco(enderecoFornecedor);

		empresa.getUsuarios().add(fornecedor);

		Mercadoria rodaLigaLeve = new Mercadoria();
		rodaLigaLeve.setCadastro(new Date());
		rodaLigaLeve.setFabricao(new Date());
		rodaLigaLeve.setNome("Roda de liga leva modelo toyota etios");
		rodaLigaLeve.setValidade(new Date());
		rodaLigaLeve.setQuantidade(30);
		rodaLigaLeve.setValor(300.0);
		rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyta para modelos do tipo hatch");

		empresa.getMercadorias().add(rodaLigaLeve);

		fornecedor.getMercadorias().add(rodaLigaLeve);

		Usuario Usuario = new Usuario();
		Usuario.setNome("Pedro Alcântara de Bragança e Bourbon");
		Usuario.setNomeSocial("Dom pedro Usuario");
		Usuario.getPerfis().add(PerfilUsuario.USUARIO);
		Usuario.setEmail("usuario@usuario.com");
		Usuario.setSenha("1234");

		Documento cpfUsuario = new Documento();
		cpfUsuario.setDataEmissao(new Date());
		cpfUsuario.setNumero("12584698533");
		cpfUsuario.setTipo(TipoDocumento.CPF);

		Usuario.getDocumentos().add(cpfUsuario);

		Endereco enderecoUsuario = new Endereco();
		enderecoUsuario.setEstado("São Paulo");
		enderecoUsuario.setCidade("São José dos Campos");
		enderecoUsuario.setBairro("Centro");
		enderecoUsuario.setRua("Av. Dr. Nelson D'Ávila");
		enderecoUsuario.setNumero("00");
		enderecoUsuario.setCodigoPostal("12245-070");

		Usuario.setEndereco(enderecoUsuario);

		Veiculo veiculo = new Veiculo();
		veiculo.setPlaca("ABC-0000");
		veiculo.setModelo("corolla-cross");
		veiculo.setTipo(TipoVeiculo.SUV);
		veiculo.setProprietario(Usuario);

		Usuario.getVeiculos().add(veiculo);

		empresa.getUsuarios().add(Usuario);

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