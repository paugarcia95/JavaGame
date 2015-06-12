package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Teclado implements KeyListener {
	private final int numeroTeclas = 256;
	private final boolean[] teclas = new boolean[numeroTeclas];

	private boolean arriba;
	private boolean abajo;
	private boolean izquierda;
	private boolean derecha;

	private boolean correr;
	private boolean salir;

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

	public boolean pulsadoArriba() {
		return arriba;
	}

	public boolean pulsadoAbajo() {
		return abajo;
	}

	public boolean pulsadoIzquierda() {
		return izquierda;
	}

	public boolean pulsadoDerecha() {
		return derecha;
	}

	public boolean pulsadoCorrer() {
		return correr;
	}

	public boolean pulsadoSalir() {
		return salir;
	}

}