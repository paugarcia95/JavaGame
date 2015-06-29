package principal.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import principal.VariablesGlobales;
import principal.maquinaEstado.GestorEstados;
import estadoCreacionMapas.MenuInferior2;
import estadoCreacionMapas.SuperficieCreacion;
import estadoJuego.interfazUsuario.MenuInferior;

public final class Teclado implements KeyListener {

	public Tecla arriba = new Tecla();
	public Tecla abajo = new Tecla();
	public Tecla derecha = new Tecla();
	public Tecla izquierda = new Tecla();

	public boolean shift = false;

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_M :
				VariablesGlobales.EstadoCreacioMapes = !VariablesGlobales.EstadoCreacioMapes;
				VariablesGlobales.cambioEstado = true;
				break;
			case KeyEvent.VK_P :
				VariablesGlobales.EstadoPause = !VariablesGlobales.EstadoPause;
				VariablesGlobales.cambioEstado = true;
				break;
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
			case KeyEvent.VK_ESCAPE :
				System.exit(0);
				break;
		}

		switch (GestorEstados.getEstadoActual()) {
			case 0 :
				funcionsKeyPressedJoc(e);
				break;
			case 2 :
				funcionsKeyPressedCreacioMapes(e);
				break;
		}

	}


	private void funcionsKeyPressedCreacioMapes(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE :
				MenuInferior2.clickObjeto1o2();
				break;
			case KeyEvent.VK_G :
				SuperficieCreacion.guardar();
		}

	}

	private void funcionsKeyPressedJoc(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SHIFT :
				shift = true;
				break;
			case KeyEvent.VK_1 :
				MenuInferior.seleccionarElemento(0);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_2 :
				MenuInferior.seleccionarElemento(1);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_3 :
				MenuInferior.seleccionarElemento(2);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_4 :
				MenuInferior.seleccionarElemento(3);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_5 :
				MenuInferior.seleccionarElemento(4);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_6 :
				MenuInferior.seleccionarElemento(5);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_7 :
				MenuInferior.seleccionarElemento(6);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_8 :
				MenuInferior.seleccionarElemento(7);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_9 :
				MenuInferior.seleccionarElemento(8);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_0 :
				MenuInferior.seleccionarElemento(9);
				MenuInferior.accioPremuda();
				break;
			case KeyEvent.VK_PAGE_UP :
				VariablesGlobales.debug1 = !VariablesGlobales.debug1;
				break;
			case KeyEvent.VK_PAGE_DOWN :
				VariablesGlobales.debug2 = !VariablesGlobales.debug2;
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
				shift = false;
				break;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
	}

}