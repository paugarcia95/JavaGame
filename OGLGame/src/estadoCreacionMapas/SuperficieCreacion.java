package estadoCreacionMapas;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import principal.Constantes;
import principal.control.GestorControles;
import principal.herramientas.DrawerClass;
import principal.sprites.HojaSprites;

public class SuperficieCreacion {
	private volatile static int posicionX = 0;
	private volatile static int posicionY = 0;

	private volatile static int ancho;
	private volatile static int alto;

	private volatile static int[][] sprites;
	private volatile boolean[][] colisiones;
	
	public static final HojaSprites hs = new HojaSprites(Constantes.RUTA_HOJA_TEXTURAS, 32, false);

	public SuperficieCreacion() {

		String anchura = null;
		String altura = null;

		while (anchura == null) {
			anchura = JOptionPane.showInputDialog(null, "Que anchura tendrá tu mapa?", "Anchura",
					JOptionPane.QUESTION_MESSAGE);
			try {
				ancho = Integer.parseInt(anchura);
			} catch (Exception e) {
				anchura = null;
			}

			if (ancho <= 0 || ancho > 50) {
				anchura = null;
			}

			if (anchura == null) {
				JOptionPane.showMessageDialog(null, "Entrada no válida! El tamaño debe ser de 0 a 50",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		while (altura == null) {
			altura = JOptionPane.showInputDialog(null, "Que altura tendrá tu mapa?", "Altura",
					JOptionPane.QUESTION_MESSAGE);
			try {
				alto = Integer.parseInt(altura);
			} catch (Exception e) {
				altura = null;
			}

			if (alto <= 0 || alto > 50) {
				altura = null;
			}

			if (altura == null) {
				JOptionPane.showMessageDialog(null, "Entrada no válida! El tamaño debe ser de 0 a 50", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		
		sprites = new int[ancho][alto];
		colisiones = new boolean[ancho][alto];
		
		// NO CAL!
		for (int i = 0; i < ancho; ++i) {
			for (int j = 0; j < alto; ++j) {
				sprites[i][j] = 355;
				colisiones[i][j] = false;
			}
		}

	}

	public void actualizar() {
		actualizarPosicion();
	}

	private void actualizarPosicion() {
		if (GestorControles.teclado.arriba.estaPulsada()) {
			posicionY -= 2;
		}
		if (GestorControles.teclado.abajo.estaPulsada()) {
			posicionY += 2;
		}
		if (GestorControles.teclado.izquierda.estaPulsada()) {
			posicionX -= 2;
		}
		if (GestorControles.teclado.derecha.estaPulsada()) {
			posicionX += 2;
		}
	}


	public void dibujar(Graphics g) {
		dibujarMapa(g);
	}
	
	private void dibujarMapa(Graphics g) {
		for (int y = 0; y < alto; ++y) {
			for (int x = 0; x < ancho; ++x) {
				BufferedImage img = hs.getSprite(sprites[x][y]).getImagen();

				int puntoX = x * Constantes.LADO_SPRITE - posicionX;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY;

				DrawerClass.dibujarImagen(g, img, puntoX, puntoY);
			}
		}
	}

	public static void clickOn(final int x, final int y) {
		int posX = (x + posicionX) / Constantes.LADO_SPRITE;
		int posY = (y + posicionY) / Constantes.LADO_SPRITE;

		int imgX = MenuInferior2.getPosicionImagen().x;
		int imgY = MenuInferior2.getPosicionImagen().y;

		sprites[posX][posY] = imgX + imgY * 32;
	}

	public static Point getPosicion() {
		return new Point(-posicionX, -posicionY);
	}

	public static int getAncho() {
		return ancho * Constantes.LADO_SPRITE;
	}

	public static int getAlto() {
		return alto * Constantes.LADO_SPRITE;
	}

	public static void guardar() {

	}

}
