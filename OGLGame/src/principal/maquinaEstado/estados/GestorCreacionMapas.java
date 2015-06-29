package principal.maquinaEstado.estados;

import java.awt.Color;
import java.awt.Graphics;

import principal.Constantes;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.EstadoJuego;
import estadoCreacionMapas.MenuInferior2;
import estadoCreacionMapas.SuperficieCreacion;

public class GestorCreacionMapas implements EstadoJuego {
	
	private volatile SuperficieCreacion sc;
	private volatile MenuInferior2 menu;

	@Override
	public void actualizar() {
		sc.actualizar();
		menu.actualizar();
	}

	@Override
	public void dibujar(Graphics g) {
		if (sc != null && menu != null) {
			sc.dibujar(g);
			menu.dibujar(g);
		}
	}

	@Override
	public void limpiarPantalla(Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA,
				(int) (Constantes.ALTO_PANTALLA_COMPLETA), Color.black);

	}

	@Override
	public void iniciar() {
		sc = new SuperficieCreacion();
		menu = new MenuInferior2();
	}

}
