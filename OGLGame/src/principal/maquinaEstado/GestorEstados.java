package principal.maquinaEstado;

import java.awt.Graphics;

import principal.maquinaEstado.juego.GestorJuego;
import principal.maquinaEstado.juego.Pause;

public class GestorEstados {
	private EstadoJuego[] estados;
	private EstadoJuego estadoActual;

	public GestorEstados() {
		iniciarEstados();
		iniciarEstadoActual();
	}

	private void iniciarEstados() {
		estados = new EstadoJuego[2];
		estados[0] = new GestorJuego();
		estados[1] = new Pause();
	}

	private void iniciarEstadoActual() {
		estadoActual = estados[0];
	}

	public void actualizar() {
		estadoActual.actualizar();
	}

	public void dibujar(final Graphics g) {
		estadoActual.dibujar(g);
	}

	public void limpiarPantalla(final Graphics g) {
		estadoActual.limpiarPantalla(g);
	}

	public void cambiarEstadoActual(final int nuevoEstado) {
		estadoActual = estados[nuevoEstado];
		estadoActual.reanudar();
	}

	public EstadoJuego obtenerEstadoActual() {
		return estadoActual;
	}
}
