package br.com.ferr.cmpMin.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.ferr.cmpMin.modelo.Tabuleiro;

public class PainelTabuleiro extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public PainelTabuleiro(Tabuleiro t) {
		setLayout(new GridLayout(t.getnLinhas(), t.getnColunas()));
		
		t.paraCadaCampo(c -> {
			add(new BotaoCampo(c));
		});
		
		t.registrarObservador(e -> {
			// TODO: mostrar resultado pro usuáriokjdfgdkg
			
			SwingUtilities.invokeLater(() -> {
				
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Você ganhou! :)");
				} else {
					JOptionPane.showMessageDialog(this, "Você perdeu :(");
				}
				
				t.reiniciar();
			});
		});
	}
}
