package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla derecha = new Tecla();
	public Tecla izquierda = new Tecla();

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W :
				arriba.teclaPulsada();
				break;
			case KeyEvent.VK_S :
				abajo.teclaPulsada();
				break;
			case KeyEvent.VK_D :
				derecha.teclaPulsada();
				break;
			case KeyEvent.VK_A :
				izquierda.teclaPulsada();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_W :
				arriba.teclaLiberada();
				break;
			case KeyEvent.VK_S :
				abajo.teclaLiberada();
				break;
			case KeyEvent.VK_D :
				derecha.teclaLiberada();
				break;
			case KeyEvent.VK_A :
				izquierda.teclaLiberada();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


}