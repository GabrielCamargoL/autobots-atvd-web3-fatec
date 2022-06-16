package com.autobots.automanager.modelos.atualizadores;

import java.util.List;

import com.autobots.automanager.entidades.Veiculo;

public class VeiculoAtualizador {
	private StringVerificadorNulo verificador = new StringVerificadorNulo();

	public void atualizar(Veiculo Veiculos, Veiculo atualizacao) {
		if (atualizacao != null) {
			if (!verificador.verificar(atualizacao.getModelo())) {
				Veiculos.setModelo(atualizacao.getModelo());
			}
			if (!verificador.verificar(atualizacao.getPlaca())) {
				Veiculos.setPlaca(atualizacao.getPlaca());
			}
			if (!verificador.verificar(atualizacao.getTipo())) {
				Veiculos.setTipo(atualizacao.getTipo());
			}
		}
	}

	public void atualizar(List<Veiculo> Veiculos, List<Veiculo> atualizacoes) {
		for (Veiculo atualizacao : atualizacoes) {
			for (Veiculo Veiculo : Veiculos) {
				if (atualizacao.getId() != null) {
					if (atualizacao.getId() == Veiculo.getId()) {
						atualizar(Veiculo, atualizacao);
					}
				}
			}
		}
	}
}
