package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Teclado implements KeyListener {
	private final static int NUMERO_TECLAS = 256;
	private final boolean[] teclas = new boolean[NUMERO_TECLAS];

	public boolean arriba;
	public boolean abajo;
	public boolean izquierda;
	public boolean derecha;

	public boolean correr;
	public boolean salir;

	public void actualizar() {
		arriba = teclas[KeyEvent.VK_W];
		abajo = teclas[KeyEvent.VK_S];
		izquierda = teclas[KeyEvent.VK_A];
		derecha = teclas[KeyEvent.VK_D];

		correr = teclas[KeyEvent.VK_SHIFT];
		salir = teclas[KeyEvent.VK_ESCAPE];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		teclas[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) { // Soltar la tecla
		teclas[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) { // Pulsar y soltar la tecla

	}

}