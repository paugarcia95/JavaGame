package principal.maquinaEstado;

import java.awt.Graphics;

import principal.maquinaEstado.estados.GestorCreacionMapas;
import principal.maquinaEstado.estados.GestorJuego;
import principal.maquinaEstado.estados.Pause;

public class GestorEstados {
	private EstadoJuego[] estados;
	private volatile EstadoJuego estadoActual;

	private static int numEstadoActual;

	public GestorEstados() {
		iniciarEstados();
		iniciarEstadoPrincipal();
	}

	private void iniciarEstados() {
		estados = new EstadoJuego[3];

		estados[0] = new GestorJuego();
		estados[1] = new Pause();
		estados[2] = new GestorCreacionMapas();
	}

	private void iniciarEstadoPrincipal() {
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
		numEstadoActual = nuevoEstado;

		estadoActual = estados[nuevoEstado];
		estadoActual.iniciar();
	}

	public static int getEstadoActual() {
		return numEstadoActual;
	}

}
