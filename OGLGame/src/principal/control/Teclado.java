package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import principal.Constantes;

public final class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla derecha = new Tecla();
	public Tecla izquierda = new Tecla();



	public boolean corriendo = false;

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
				break;
			case KeyEvent.VK_SHIFT :
				corriendo = true;
				break;
			case KeyEvent.VK_PAGE_UP :
				Constantes.debug1 = !Constantes.debug1;
				break;
			case KeyEvent.VK_PAGE_DOWN :
				Constantes.debug2 = !Constantes.debug2;
				break;
			case KeyEvent.VK_ESCAPE :
				System.exit(0);
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
				break;
			case KeyEvent.VK_SHIFT :
				corriendo = false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}


}