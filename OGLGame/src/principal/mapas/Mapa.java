package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.DrawerClass;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {
	private String[] partes;

	private final int ancho;
	private final int alto;

	private final Point posicionInicial;
	private final Point puntoSalida;
	private String siguienteMapa;
	private Rectangle zonaSalida;

	private final Sprite[] paleta;

	private final boolean[] colisiones;
	public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();

	private final int[] sprites;

	private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.ANCHO_PERSONAJE / 2;
	private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.ALTO_PERSONAJE / 2;

	public Mapa(final String ruta) {
		final String contenido = CargadorRecursos.leerArchivoTexto(ruta);
		partes = contenido.split("\\*"); // Separa els strings cada cop que
											// troba una *
											// S'utilitza la doble barra per
											// indicar q es un char especial

		ancho = Integer.parseInt(partes[0]);
		alto = Integer.parseInt(partes[1]);

		String hojasUtilizadas = partes[2];
		String[] hojasSeparadas = hojasUtilizadas.split(",");

		// Lectura de paleta de sprites
		String paletaEntera = partes[3];
		String[] partesPaleta = paletaEntera.split("#");
		
		paleta = assignarSprites(partesPaleta, hojasSeparadas);

		String colisionesEnteras = partes[4];
		colisiones = extraerColisiones(colisionesEnteras);
		
		String spritesEnteros = partes[5];
		String[] cadenasSprites = spritesEnteros.split(" ");
		sprites = extraerSprites(cadenasSprites);

		String posicion = partes[6];
		String[] posiciones = posicion.split("-");

		posicionInicial = new Point();
		posicionInicial.x = Integer.parseInt(posiciones[0]) * Constantes.LADO_SPRITE;
		posicionInicial.y = Integer.parseInt(posiciones[1]) * Constantes.LADO_SPRITE;

		String salida = partes[7];
		String[] datosSalida = salida.split("-");

		puntoSalida = new Point();
		puntoSalida.x = Integer.parseInt(datosSalida[0]);
		puntoSalida.y = Integer.parseInt(datosSalida[1]);
		siguienteMapa = datosSalida[2];

		zonaSalida = new Rectangle();
	}
	
	private Sprite[] assignarSprites(final String[] partesPaleta, final String[] hojasSeparadas) {
		Sprite[] paleta = new Sprite[partesPaleta.length];

		HojaSprites hoja = new HojaSprites("/imagenes/hojasTexturas/" + hojasSeparadas[0] + ".png", 32, false);
		
		for (int i = 0; i < partesPaleta.length; ++i) {
			String spriteTemporal = partesPaleta[i];
			String[] partesSprite = spriteTemporal.split("-");

			int indicePaleta = Integer.parseInt(partesSprite[0]);
			// XAPUSSA!!! Aquest 16 es pq la meva fulla d'sprites ARA té 16x¿16?
			// sprites
			// PERO SINO NO FUNCIONARA, aixo el javadev no se on ho fa pro ho he
			// hagut d'arreglar jo, aprox capitol 60-61
			int indiceSpriteHoja = Integer.parseInt(partesSprite[1]) * 16 + Integer.parseInt(partesSprite[2]);

			paleta[indicePaleta] = hoja.getSprite(indiceSpriteHoja);
		}
		
		return paleta;
	}

	private boolean[] extraerColisiones(String colisionesEnteras) {
		boolean[] colisiones = new boolean[colisionesEnteras.length()];
		
		for (int i = 0; i < colisionesEnteras.length(); i++) {
			if (colisionesEnteras.charAt(i) == '0') colisiones[i] = false;
			else colisiones[i] = true;
		}
		return colisiones;
	}
	
	private int[] extraerSprites(final String[] cadenaSprites) {
		ArrayList<Integer> sprites = new ArrayList<Integer>();
		
		for (int i = 0; i < cadenaSprites.length; i++) {
			if (cadenaSprites[i].length() == 2) {
				sprites.add(Integer.parseInt(cadenaSprites[i]));
			} else {
				String uno = "";
				String dos = "";
				String error = cadenaSprites[i];
				
				uno += error.charAt(0);
				uno += error.charAt(1);
				
				dos += error.charAt(2);
				dos += error.charAt(3);
				
				sprites.add(Integer.parseInt(uno));
				sprites.add(Integer.parseInt(dos));
			}
		}
		int[] vectorSprites = new int[sprites.size()];
		
		for (int i = 0; i < sprites.size(); ++i) {
			vectorSprites[i] = sprites.get(i);
		}
		
		return vectorSprites;
	}

	public void actualizar(final int posicionX, final int posicionY) {
		actualizarAreasColision(posicionX, posicionY);
		actualizarZonaSalida(posicionX, posicionY);
	}

	private void actualizarAreasColision(final int posicionX, final int posicionY) {
		if (!areasColision.isEmpty()) {
			areasColision.clear();
		}
		
		for (int y = 0; y < this.alto; ++y) {
			for (int x = 0; x < this.ancho; ++x) {
				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;
				
				if (colisiones[x + y * this.ancho]) {
					final Rectangle r = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
					areasColision.add(r);
				}
			}
		}
	}

	private void actualizarZonaSalida(final int posicionX, final int posicionY) {
		int puntoX = puntoSalida.x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
		int puntoY = puntoSalida.y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

		zonaSalida = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
	}

	public void dibujar(Graphics g, final int posicionX, final int posicionY) {
		for (int y = 0; y < this.alto; ++y) {
			for (int x = 0; x < this.ancho; ++x) {
				BufferedImage img = paleta[sprites[x + y * this.ancho]].getImagen();

				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

				DrawerClass.dibujarImagen(g, img, puntoX, puntoY);
			}
		}
		if (Constantes.debug2)
			dibuixaRectanglesColisio(g);
		if (Constantes.debug1 || Constantes.debug2) {
			dibuixarRectangleSortida(g);
		}
	}

	private void dibuixaRectanglesColisio(Graphics g) {
		g.setColor(Color.GREEN);
		for (int i = 0; i < areasColision.size(); ++i) {
			Rectangle aux = areasColision.get(i);
			DrawerClass.dibujarRectanguloContorno(g, aux.x, aux.y, aux.width, aux.height);
		}
	}

	private void dibuixarRectangleSortida(Graphics g) {
		g.setColor(Color.MAGENTA);
		DrawerClass.dibujarRectanguloRelleno(g, zonaSalida.x, zonaSalida.y, zonaSalida.width, zonaSalida.height);
	}

	public Rectangle getBordes(final int posicionX, final int posicionY, final int anchoJugador, final int altoJugador) {
		int x = MARGEN_X - posicionX + anchoJugador;
		int y = MARGEN_Y - posicionY + altoJugador;
		int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;

		return new Rectangle(x, y, ancho, alto);
	}

	public Point getPosicionInicial() {
		return posicionInicial;
	}

	public Point getPuntoSalida() {
		return puntoSalida;
	}

	public String getSiguienteMapa() {
		return siguienteMapa;
	}

	public Rectangle getZonaSalida() {
		return zonaSalida;
	}
}
