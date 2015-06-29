package principal.control;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
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
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.GestorEstados;
import estadoCreacionMapas.MenuInferior2;
import estadoCreacionMapas.SuperficieCreacion;
import estadoJuego.entes.Enemigo;
import estadoJuego.interfazUsuario.MenuInferior;
import estadoJuego.mapas.Mapa;

public class Raton extends MouseAdapter {
	private static Cursor cursor;
	private static Point posicion;
	private static Point posicionRelativa;
	private static BufferedImage icono = CargadorRecursos
			.cargarImagenCompatibleTranslucida(Constantes.RUTA_ICONO_RATON);
	private boolean click = false;
	
	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();
		cursor = configuracion.createCustomCursor(icono, new Point(0, 0), "Cursor joc");

		posicion = new Point();
		posicionRelativa = new Point();

		actualizar(sd);
	}

	public static void dibujar(Graphics g) {
		Rectangle r = new Rectangle(SuperficieCreacion.getPosicion().x, SuperficieCreacion.getPosicion().y,
				SuperficieCreacion.getAncho(), SuperficieCreacion.getAlto());
		DrawerClass.dibujarRectanguloRelleno(g, r, Color.BLUE);
	}

	public void actualizar(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		posicion.setLocation(posicionInicial);

		posicionRelativa.x = (int) (posicion.x / Constantes.FACTOR_ESCALADO_X);
		posicionRelativa.y = (int) (posicion.y / Constantes.FACTOR_ESCALADO_Y);

		switch (GestorEstados.getEstadoActual()) {
			case 2 :
				funcionsMousePositionCreacioMapes();
				break;
		}
	}
	
	private void funcionsMousePositionCreacioMapes() {
		if (MenuInferior2.getLadoDerecho().contains(posicionRelativa)) {
			MenuInferior2.encimaLadoDerecho();
		}
		if (MenuInferior2.getLadoIzquierdo().contains(posicionRelativa)) {
			MenuInferior2.encimaLadoIzquierdo();
		}
		if (MenuInferior2.getLadoSuperior().contains(posicionRelativa)) {
			MenuInferior2.encimaLadoSuperior();
		}
		if (MenuInferior2.getLadoInferior().contains(posicionRelativa)) {
			MenuInferior2.encimaLadoInferior();
		}

		Toolkit configuracion = Toolkit.getDefaultToolkit();
		cursor = configuracion.createCustomCursor(MenuInferior2.getImagenSelecionada(), new Point(0, 0),
				"Cursor joc");

		Rectangle r = new Rectangle(SuperficieCreacion.getPosicion().x, SuperficieCreacion.getPosicion().y,
				SuperficieCreacion.getAncho(), SuperficieCreacion.getAlto());

		// Diu que s'ha de dibuixar allá on s'ha clicat
		if (click && r.contains(posicionRelativa) && !MenuInferior2.getAreaMenu().contains(posicionRelativa)) {
			SuperficieCreacion.clickOn(posicionRelativa.x, posicionRelativa.y);
		}
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		switch (GestorEstados.getEstadoActual()) {
			case 0 :
				funcionsMousePressedJoc(ev);
				break;
			case 2 :
				funcionsMousePressedCreacioMapes(ev);
				break;
		}
	}

	private void funcionsMousePressedCreacioMapes(MouseEvent ev) {
		if (MenuInferior2.getObjeto1().contains(posicionRelativa)) {
			MenuInferior2.clickObjeto1();
		}
		if (MenuInferior2.getObjeto2().contains(posicionRelativa)) {
			MenuInferior2.clickObjeto2();
		}
		if (MenuInferior2.getAreaMenu().contains(posicionRelativa)) {
			MenuInferior2.clickMenu(posicionRelativa.x, posicionRelativa.y);
		}
		click = true;
	}

	private void funcionsMousePressedJoc(MouseEvent ev) {
		int altoMenu = 64;
		final Rectangle areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO,
				altoMenu);

		final int POSICIO_X = areaInventario.x + 210;
		final int POSICIO_Y = areaInventario.y + 10;

		for (int x = 0; x < 10; ++x) {
			Rectangle r = new Rectangle(POSICIO_X + x * 38, POSICIO_Y, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

			if (r.contains(posicionRelativa)) {
				MenuInferior.seleccionarElemento(x);
				MenuInferior.accioPremuda();
				return;
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
				if (r.contains(posicionRelativa)) {
					mapa.setEnemicClicat(e);
					return;
				}
			}
			mapa.setEnemicClicat(null);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		click = false;
	}

	public Cursor getCursor() {
		return cursor;
	}

	public Point getPosicion() {
		return posicion;
	}

	public static void setImagenPrincipal() {
		Toolkit configuracion = Toolkit.getDefaultToolkit();
		cursor = configuracion.createCustomCursor(icono, new Point(0, 0), "Cursor joc");
	}

}
