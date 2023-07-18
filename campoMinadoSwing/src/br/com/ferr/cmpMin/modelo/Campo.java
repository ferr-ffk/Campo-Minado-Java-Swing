package br.com.ferr.cmpMin.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Campo {
	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	/* se o campo foi clicado ou não */

	private boolean minado = false;
	/* se contém uma bomba ou não */

	private boolean marcado;
	/* se o usuario marcar o campo */

	private final List<Campo> vizinhos = new ArrayList<>();
	
	private final Set<CampoObservador> observadores =
			new HashSet<>();
	/* a implementação de um conjunto faz com que nn haja
	 * duplicação de observers */

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	public void registrarObservador(CampoObservador o) {
		observadores.add(o);
	}
	
	private void notificarObservadores(CampoEvento e) {
		observadores.stream()
			.forEach(o -> o.eventoOcorreu(this, e));
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int dLinha = Math.abs(linha - vizinho.linha);
		int dColuna = Math.abs(coluna - vizinho.coluna);
		int dGeral = dLinha + dColuna;

		if (dGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (dGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	public void alternarMarcacao() {
		if (!aberto) {
			marcado = !marcado;
			
			if(marcado) {
				notificarObservadores(CampoEvento.MARCAR);
			} else {
				notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}

			setAberto(true);
			
			
			if (vizinhancaSeguranca()) {
				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean vizinhancaSeguranca() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;

	}
	
	public boolean isMinado() {
		return minado;
	}

	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
	
}
