package principal.maquinaEstado.estados;

import java.awt.Color;
import java.awt.Graphics;

import estadoJuego.interfazUsuario.MenuInferior;
import principal.Constantes;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.EstadoJuego;

public class Pause implements EstadoJuego {
	private int times = 0;

	@Override
	public void actualizar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dibujar(Graphics g) {
		if (times < 10) {
			DrawerClass.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA,
					(int) (Constantes.ALTO_PANTALLA_COMPLETA - MenuInferior.altoMenu * Constantes.FACTOR_ESCALADO_Y),
					new Color(0, 0, 0, 30));
			g.setFont(Constantes.FUENTE_PRINCIPAL_GRANDE);
			DrawerClass.dibujarString(g, "PAUSA", Constantes.CENTRO_VENTANA_X - 15, Constantes.CENTRO_VENTANA_Y,
					Color.RED);
			++times;
		}
	}

	@Override
	public void limpiarPantalla(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void iniciar() {
		times = 0;
	}

}
