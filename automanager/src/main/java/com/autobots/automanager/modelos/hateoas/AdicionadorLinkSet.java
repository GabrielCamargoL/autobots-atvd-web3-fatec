package com.autobots.automanager.modelos.hateoas;

import java.util.Set;

public interface AdicionadorLinkSet<T> {
	public void adicionarLink(Set<T> lista);

	public void adicionarLink(T objeto);
}