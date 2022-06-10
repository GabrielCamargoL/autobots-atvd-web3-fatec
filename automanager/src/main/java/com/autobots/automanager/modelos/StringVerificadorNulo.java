package com.autobots.automanager.modelos;

import com.autobots.automanager.enums.TipoDocumento;

public class StringVerificadorNulo {

	public boolean verificar(String dado) {
		boolean nulo = true;
		if (!(dado == null)) {
			if (!dado.isBlank()) {
				nulo = false;
			}
		}
		return nulo;
	}

	public boolean verificar(TipoDocumento tipo) {
		boolean nulo = true;
		if (!(tipo == null)) {
			nulo = false;
		}
		return nulo;
	}
}