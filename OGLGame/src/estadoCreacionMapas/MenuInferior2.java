package estadoCreacionMapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.herramientas.DrawerClass;

public class MenuInferior2 {
	public static int altoMenu = 128;

	public static double posicionX = 0;
	public static double posicionY = 0;

	public static Rectangle areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu,
			Constantes.ANCHO_JUEGO, altoMenu);

	private static boolean encimaLadoDerecho = false;
	private static boolean encimaLadoIzquierdo = false;
	private static boolean encimaLadoSuperior = false;
	private static boolean encimaLadoInferior = false;
	
	private static boolean clickObjeto1 = false;
	private static boolean clickObjeto2 = false;
	private static boolean clickObjeto1o2 = false;

	private static int clickX = 0;
	private static int clickY = 0;

	private static BufferedImage imagenCursor = SuperficieCreacion.hs.getSprite(0, 0).getImagen();

	private static Rectangle objeto1 = new Rectangle(
										  (int) (Constantes.CENTRO_VENTANA_X) - 5,
												 Constantes.ALTO_JUEGO - altoMenu - 15, 
												 15, 15);
	private static Rectangle objeto2 = new Rectangle(
										  (int) (Constantes.CENTRO_VENTANA_X) - 5,
										  		0,
												15, 15);

	private static final int areaPermiso = 10;

	private static Rectangle ladoDerecho = new Rectangle(
												Constantes.ANCHO_JUEGO - areaPermiso, 
												Constantes.ALTO_JUEGO
												- altoMenu, 
												areaPermiso, 
												altoMenu);
	private static Rectangle ladoIzquierdo = new Rectangle(
												0, 
												Constantes.ALTO_JUEGO - altoMenu,
												areaPermiso, 
												altoMenu);

	private static final Rectangle ladoSuperior = new Rectangle(
												0, 
												0,
												Constantes.ANCHO_JUEGO, 
												areaPermiso);
	private static final Rectangle ladoInferior = new Rectangle(
												0, 
												Constantes.ALTO_JUEGO - areaPermiso,
												Constantes.ANCHO_JUEGO, 
												areaPermiso);

	public void actualizar() {
		actualizarClicks();
		actualizarBordes();

	}

	private void actualizarBordes() {
		ladoDerecho = new Rectangle(
				Constantes.ANCHO_JUEGO - areaPermiso,
				Constantes.ALTO_JUEGO - altoMenu, 
				areaPermiso, 
				altoMenu);
		ladoIzquierdo = new Rectangle(
				0, 
				Constantes.ALTO_JUEGO - altoMenu, 
				areaPermiso,
				altoMenu);

		areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);

	}

	private void actualizarClicks() {
		// Control del desplacament lateral
		if (encimaLadoDerecho && posicionX < 11) {
			posicionX += 0.3;
		}
		if (encimaLadoIzquierdo && posicionX > 0) {
			posicionX -= 0.3;
		}
		if (encimaLadoInferior && posicionY < 18) {
			posicionY += 0.3;
		}
		if (encimaLadoSuperior && posicionY > 0) {
			posicionY -= 0.3;
		}

		encimaLadoInferior = encimaLadoSuperior = encimaLadoDerecho = encimaLadoIzquierdo = false;

		// Control del desplegament del menú teclat

		if (clickObjeto1o2) {
			clickObjeto1o2 = false;
			if (altoMenu > 128)
				clickObjeto2 = true;
			else
				clickObjeto1 = true;
		}

		// Control del desplegament del menú per ratolí
		if (clickObjeto1) {
			if (altoMenu < Constantes.ALTO_JUEGO) {
				altoMenu += 4;
			} else
				clickObjeto1 = false;
		}

		if (clickObjeto2) {
			clickObjeto1 = false;
			if (altoMenu > 128) {
				altoMenu -= 4;
			} else
				clickObjeto2 = false;
		}
		imagenCursor = SuperficieCreacion.hs.getSprite(clickX, clickY).getImagen();

	}

	public void dibujar(final Graphics g) {
		dibujarBase(g);
		dibujarSprites(g);
		dibujarLineas(g);
		dibujarObjetosInteractivos(g);
	}

	private void dibujarObjetosInteractivos(Graphics g) {
		if (!clickObjeto1) {
			DrawerClass.dibujarString(g, "/|\\", 
					Constantes.CENTRO_VENTANA_X - 5, 
					Constantes.ALTO_JUEGO - altoMenu - 2,
					Color.WHITE);
		}

		if (clickObjeto1 || altoMenu == Constantes.ALTO_JUEGO) {
			DrawerClass.dibujarString(g, "\\|/", 
					Constantes.CENTRO_VENTANA_X - 5, 
					15, 
					Color.WHITE);
		}
	}

	private void dibujarBase(final Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, areaInventario, Color.BLACK);
	}

	private void dibujarSprites(Graphics g) {
		final int posicionX = (int) this.posicionX;
		final int posicionY = (int) this.posicionY;

		for (int y = posicionY; y < altoMenu / Constantes.LADO_SPRITE + 1 + posicionY; ++y) {
			for (int x = posicionX; x < 21 + posicionX; ++x) {

				BufferedImage img = SuperficieCreacion.hs.getSprite(x, y).getImagen();

				DrawerClass.dibujarImagen(
						g, img, 
						(x - posicionX) * Constantes.LADO_SPRITE, 
						(y - posicionY) * Constantes.LADO_SPRITE + Constantes.ALTO_JUEGO - altoMenu
						);
			}
		}
	}

	private void dibujarLineas(Graphics g) {
		g.setColor(Color.WHITE);
		for (int y = 0; y < altoMenu / Constantes.LADO_SPRITE + 1; ++y) {
			DrawerClass.dibujarLineaHorizontal(
					g, 
					0, 
					y * Constantes.LADO_SPRITE + Constantes.ALTO_JUEGO - altoMenu,
					Constantes.ANCHO_JUEGO
					);
		}

		for (int x = 0; x < 22; ++x) {
			DrawerClass.dibujarLineaVertical(
					g, 
					x * Constantes.LADO_SPRITE, 
					Constantes.ALTO_JUEGO - altoMenu, 
					altoMenu
					);
		}
		
	}

	public static void encimaLadoDerecho() {
		encimaLadoDerecho = true;
	}

	public static void encimaLadoIzquierdo() {
		encimaLadoIzquierdo = true;
	}

	public static void encimaLadoSuperior() {
		encimaLadoSuperior = true;
	}

	public static void encimaLadoInferior() {
		encimaLadoInferior = true;
	}

	public static Rectangle getObjeto1() {
		return objeto1;
	}

	public static Rectangle getObjeto2() {
		return objeto2;
	}

	public static void clickObjeto1() {
		clickObjeto1 = true;
	}

	public static void clickObjeto2() {
		clickObjeto2 = true;
	}

	public static void clickObjeto1o2() {
		clickObjeto1o2 = true;
	}

	public static void clickMenu(final int x, final int y) {
		clickX = x / Constantes.LADO_SPRITE + (int) posicionX;
		clickY = (y - (Constantes.ALTO_JUEGO - altoMenu)) / Constantes.LADO_SPRITE + (int) posicionY;
	}

	public static Rectangle getLadoDerecho() {
		return ladoDerecho;
	}

	public static Rectangle getLadoIzquierdo() {
		return ladoIzquierdo;
	}

	public static Rectangle getLadoSuperior() {
		return ladoSuperior;
	}

	public static Rectangle getLadoInferior() {
		return ladoInferior;
	}

	public static Rectangle getAreaMenu() {
		return new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);
	}

	public static BufferedImage getImagenSelecionada() {
		return imagenCursor;
	}

	public static Point getPosicionImagen() {
		return new Point(clickX, clickY);
	}
}
