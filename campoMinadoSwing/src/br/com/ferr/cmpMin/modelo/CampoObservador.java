package br.com.ferr.cmpMin.modelo;

@FunctionalInterface
public interface CampoObservador {

	public void eventoOcorreu(Campo c, CampoEvento e);
}
