package principal.control;

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.VariablesGlobales;
import principal.entes.Enemigo;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;
import principal.interfazUsuario.MenuInferior;
import principal.mapas.Mapa;

public class Raton extends MouseAdapter {
	private final Cursor cursor;
	private Point posicion;
	
	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();

		BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.RUTA_ICONO_RATON);

		this.cursor = configuracion.createCustomCursor(icono, new Point(0, 0), "Cursor joc");

		posicion = new Point();
		actualizar(sd);
	}

	public void actualizar(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		posicion.setLocation(posicionInicial);
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		if (VariablesGlobales.pause)
			return;

		int altoMenu = 64;
		final Rectangle areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO,
				altoMenu);

		final int POSICIO_X = areaInventario.x + 210;
		final int POSICIO_Y = areaInventario.y + 10;

		final MenuInferior menuInferior = GestorControles.getMenuInferior();
		if (menuInferior != null) {
			for (int x = 0; x < 10; ++x) {
				Rectangle r = new Rectangle(POSICIO_X + x * 38, POSICIO_Y, Constantes.LADO_SPRITE,
						Constantes.LADO_SPRITE);

				if (r.contains(posicion.x * Constantes.ANCHO_JUEGO / Constantes.ANCHO_PANTALLA_COMPLETA, posicion.y
						* Constantes.ALTO_JUEGO / Constantes.ALTO_PANTALLA_COMPLETA)) {
					menuInferior.seleccionarElemento(x);
					menuInferior.accioPremuda();
					return;
				}
			}
		}
		
		final Mapa mapa = GestorControles.getMapa();
		if (mapa != null) {
			ArrayList<Enemigo> objetos = mapa.getObjetos();
			for (int i = 0; i < objetos.size(); ++i) {
				Enemigo e = objetos.get(i);
				Rectangle r = (new Rectangle(	(int) e.getPosicioXRelativa(), 
												(int) e.getPosicioYRelativa(), 
												e.getAnchoSprite(),
												e.getAltoSprite()));
				if (r.contains(
						posicion.x * Constantes.ANCHO_JUEGO	/ Constantes.ANCHO_PANTALLA_COMPLETA, 
						posicion.y * Constantes.ALTO_JUEGO / Constantes.ALTO_PANTALLA_COMPLETA)
						) {

					mapa.setEnemicClicat(e);
					return;
				}
			}
			mapa.setEnemicClicat(null);
		}
	}

	public Cursor getCursor() {
		return this.cursor;
	}

	public Point getPosicion() {
		return posicion;
	}

}
