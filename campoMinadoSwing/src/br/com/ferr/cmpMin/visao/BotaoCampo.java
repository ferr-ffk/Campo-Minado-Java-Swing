package br.com.ferr.cmpMin.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.ferr.cmpMin.modelo.Campo;
import br.com.ferr.cmpMin.modelo.CampoEvento;
import br.com.ferr.cmpMin.modelo.CampoObservador;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {
	private static final long serialVersionUID = 1L;

	private final Campo campo;
	
	private static final Color BG_PADRAO = new Color(184, 184, 184);
	private static final Color BG_MARCAR = new Color(8, 179, 247);
	private static final Color BG_EXPLODIR = new Color(189, 66, 68);
	private static final Color TEXTO_VERDE = new Color(0, 100, 0);
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		addMouseListener(this);
		campo.registrarObservador(this);
	}

	@Override
	public void eventoOcorreu(Campo c, CampoEvento e) {
		switch(e) {
		case ABRIR:
			aplicarEstiloAbrir();
			break;
		case MARCAR:
			aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			aplicarEstiloExplodir();
			break;
		default:
			setBackground(BG_PADRAO);
			setBorder(BorderFactory.createBevelBorder(0));
			setText("");
			break;
		}
		
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void aplicarEstiloExplodir() {
		setBackground(BG_EXPLODIR);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void aplicarEstiloMarcar() {
		setBackground(BG_MARCAR);
		setForeground(Color.BLACK);
		setText("M");		
	}

	private void aplicarEstiloAbrir() {
		
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			setBackground(BG_EXPLODIR);
			return;
		}
		
		setBackground(BG_PADRAO);
		
		setNumeroBotao(campo.minasNaVizinhanca());
	}

	private void setNumeroBotao(int minasNaVizinhanca) {
		switch (minasNaVizinhanca) {
		case 1:
			setForeground(TEXTO_VERDE);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
		case 4:
			setForeground(Color.RED);
		case 5:
			setForeground(Color.RED);
		case 7:
			setForeground(Color.RED);
		default:
			setForeground(Color.PINK);
			break;
		}
		
		String valor = !campo.vizinhancaSeguranca() ?
				campo.minasNaVizinhanca() + "" : "";
		
		setText(valor);
	}

	// interface dos eventos de mouse
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}

	// eventos desnecessários
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}

