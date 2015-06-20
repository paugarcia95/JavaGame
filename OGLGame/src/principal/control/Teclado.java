package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import principal.VariablesGlobales;

public final class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla derecha = new Tecla();
	public Tecla izquierda = new Tecla();

	public boolean corriendo = false;

	@Override
	public void keyPressed(KeyEvent e) {
		if (VariablesGlobales.pause) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_P :
					VariablesGlobales.pause = !VariablesGlobales.pause;
					VariablesGlobales.cambioEstado = true;
					break;
				case KeyEvent.VK_ESCAPE :
					System.exit(0);
			}
			return;
		}

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
			case KeyEvent.VK_1 :
				GestorControles.getMenuInferior().seleccionarElemento(0);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_2 :
				GestorControles.getMenuInferior().seleccionarElemento(1);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_3 :
				GestorControles.getMenuInferior().seleccionarElemento(2);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_4 :
				GestorControles.getMenuInferior().seleccionarElemento(3);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_5 :
				GestorControles.getMenuInferior().seleccionarElemento(4);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_6 :
				GestorControles.getMenuInferior().seleccionarElemento(5);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_7 :
				GestorControles.getMenuInferior().seleccionarElemento(6);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_8 :
				GestorControles.getMenuInferior().seleccionarElemento(7);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_9 :
				GestorControles.getMenuInferior().seleccionarElemento(8);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_0 :
				GestorControles.getMenuInferior().seleccionarElemento(9);
				GestorControles.getMenuInferior().accioPremuda();
				break;
			case KeyEvent.VK_PAGE_UP :
				VariablesGlobales.debug1 = !VariablesGlobales.debug1;
				break;
			case KeyEvent.VK_PAGE_DOWN :
				VariablesGlobales.debug2 = !VariablesGlobales.debug2;
				break;
			case KeyEvent.VK_P :
				VariablesGlobales.pause = !VariablesGlobales.pause;
				VariablesGlobales.cambioEstado = true;
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