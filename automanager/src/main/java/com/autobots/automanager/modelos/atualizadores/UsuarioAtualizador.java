package com.autobots.automanager.modelos.atualizadores;

import com.autobots.automanager.entidades.Usuario;

public class UsuarioAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();
	private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();

	private void atualizarDados(Usuario Usuario, Usuario atualizacao) {
		if (!verificador.verificar(atualizacao.getNome())) {
			Usuario.setNome(atualizacao.getNome());
		}
		if (!verificador.verificar(atualizacao.getNomeSocial())) {
			Usuario.setNomeSocial(atualizacao.getNomeSocial());
		}
	}

	public void atualizar(Usuario Usuario, Usuario atualizacao) {
		atualizarDados(Usuario, atualizacao);
		enderecoAtualizador.atualizar(Usuario.getEndereco(), atualizacao.getEndereco());
	}
}
