package com.autobots.automanager.modelos.atualizadores;

import com.autobots.automanager.entidades.Usuario;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

	private void atualizarDados(Usuario usuario, Usuario atualizacao) {

		if (!verificador.verificar(atualizacao.getNome())) {
			usuario.setNome(atualizacao.getNome());
		}

		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			usuario.setNomeSocial(atualizacao.getNomeSocial());
		}

		if (!verificador.verificar(atualizacao.getDocumentos())) {
			usuario.setDocumentos(atualizacao.getDocumentos());
		}

		if (!verificador.verificar(atualizacao.getEmails())) {
			usuario.setEmails(atualizacao.getEmails());
		}

		if (!verificador.verificar(atualizacao.getMercadorias())) {
			usuario.setMercadorias(atualizacao.getMercadorias());
		}

		if (!verificador.verificar(atualizacao.getVeiculos())) {
			usuario.setVeiculos(atualizacao.getVeiculos());
		}

		if (!verificador.verificar(atualizacao.getVendas())) {
			usuario.setVendas(atualizacao.getVendas());
		}

		if (!verificador.verificar(atualizacao.getPerfis())) {
			usuario.setPerfis(atualizacao.getPerfis());
		}

		if (!verificador.verificar(atualizacao.getCredenciais())) {
			usuario.setCredenciais(atualizacao.getCredenciais());
		}
	}

	public void atualizar(Usuario Usuario, Usuario atualizacao) {

		atualizarDados(Usuario, atualizacao);
		enderecoAtualizador.atualizar(Usuario.getEndereco(), atualizacao.getEndereco());
	}
}
