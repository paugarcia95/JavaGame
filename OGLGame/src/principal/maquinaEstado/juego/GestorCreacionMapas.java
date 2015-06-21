package principal.maquinaEstado.juego;

import java.awt.Color;
import java.awt.Graphics;

import estadoJuego.interfazUsuario.MenuInferior;
import principal.Constantes;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.EstadoJuego;

public class GestorCreacionMapas implements EstadoJuego {

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dibujar(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void limpiarPantalla(Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA,
				(int) (Constantes.ALTO_PANTALLA_COMPLETA - MenuInferior.altoMenu * Constantes.FACTOR_ESCALADO_Y),
				Color.black);

	}

	@Override
	public void reanudar() {
	}

}
