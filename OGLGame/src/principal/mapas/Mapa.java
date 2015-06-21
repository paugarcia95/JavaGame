package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.VariablesGlobales;
import principal.control.GestorControles;
import principal.elementosJuego.accions.Accio;
import principal.entes.Enemigo;
import principal.entes.enemigos.Sombra;
import principal.herramientas.CargadorRecursos;
import principal.herramientas.Colores;
import principal.herramientas.DrawerClass;
import principal.interfazUsuario.MenuInferior;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {
	private String[] partes;

	private final int ancho;
	private final int alto;

	private Point posicionInicial;

	private final Sprite[] paleta;
	private final int[] sprites;

	private final boolean[] colisiones;
	public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();

	private final int MARGEN_X = Constantes.ANCHO_JUEGO / 2 - Constantes.ANCHO_PERSONAJE / 2;
	private final int MARGEN_Y = Constantes.ALTO_JUEGO / 2 - Constantes.ALTO_PERSONAJE / 2;

	private ArrayList<Point> puntosSalida;
	private ArrayList<Point> puntoLlegada;
	private ArrayList<String> siguientesMapa;
	private ArrayList<Rectangle> zonaSalida;

	// L'hauria de tenir el jugador aquest atribut!!!
	private ArrayList<Accio> accionsPendents = new ArrayList<Accio>();

	private ArrayList<Enemigo> enemigos;
	private volatile Enemigo enemigoSelecionado;

	private String error = "WELLCOME";
	private int intensitatError = Constantes.MAX_INTENSITAT_COLOR_ERROR;

	public Mapa(final String ruta) {
		GestorControles.setMapa(this);

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
		String[] datosSalidas = salida.split("#");

		puntosSalida = new ArrayList<Point>();
		puntoLlegada = new ArrayList<Point>();
		siguientesMapa = new ArrayList<String>();
		zonaSalida = new ArrayList<Rectangle>();

		for (int i = 0; i < datosSalidas.length; ++i) {
			String[] datosUnaSalida = datosSalidas[i].split("/");
			puntosSalida.add(new Point(Integer.parseInt(datosUnaSalida[0].split(",")[0]), Integer.parseInt(datosUnaSalida[0].split(",")[1])));
			puntoLlegada.add(new Point(Integer.parseInt(datosUnaSalida[1].split(",")[0]) * Constantes.LADO_SPRITE,
					Integer.parseInt(datosUnaSalida[1].split(",")[1]) * Constantes.LADO_SPRITE));
			siguientesMapa.add(datosUnaSalida[2]);
			zonaSalida.add(new Rectangle());
		}
		
		enemigos = new ArrayList<>();
		enemigos.add(new Sombra(this, 500, 390, 25, 34, 0, 1, 1));
		enemigos.add(new Sombra(this, 500, 10, 50, 10, 0, 2, 4));
		enemigos.add(new Sombra(this, 300, 390, 1000, 1000, 0, 1, 8));
		enemigos.add(new Sombra(this, 200, 375, 1000, 1000, 0, 4, 7));
		enemigos.add(new Sombra(this, 100, 370, 1000, 1000, 0, 5, 10));
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
		actualizarEnemigos(posicionX, posicionY);
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
		for (int i = 0; i < puntosSalida.size(); ++i) {
			int puntoX = puntosSalida.get(i).x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
			int puntoY = puntosSalida.get(i).y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

			zonaSalida.set(i, new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE));
		}
	}

	private void actualizarEnemigos(final int posicionX, final int posicionY) {
		for (int i = 0; i < enemigos.size(); ++i) {
			Enemigo e = enemigos.get(i);
			Accio accioRetorn;
			
			if ((accioRetorn = e.actualizar(posicionX, posicionY)).getType().equals("MORT")) {
				if (enemigos.get(i).equals(enemigoSelecionado)) {
					enemigoSelecionado = null;
				}
				enemigos.remove(i);
			} else {
				areasColision.add(new Rectangle(e.getAreaColision()));
				
				if (accioRetorn.getType().equals("ATAC")) {
					setAccionTurno(accioRetorn);
				}
			}
		}
	}

	public void dibujar(Graphics g, final int posicionX, final int posicionY) {
		dibuixarMapa(g, posicionX, posicionY);
		dibuixarEnemics(g, posicionX, posicionY);
		dibuixarClicats(g);
		dibuixarErrors(g);

		if (VariablesGlobales.debug2)
			dibuixaRectanglesColisio(g, posicionX, posicionY);
		if (VariablesGlobales.debug1 || VariablesGlobales.debug2) {
			dibuixarRectangleSortida(g);
		}
	}

	private void dibuixarErrors(Graphics g) {
		if (error != null) {
			Color colorError = new Color(255, 0, 0, intensitatError);

			DrawerClass.dibujarString(g, error, (int) (Constantes.CENTRO_VENTANA_X - error.length() * 6.5 / 2),
					Constantes.ALTO_JUEGO - MenuInferior.altoMenu - 10, colorError);

			if (intensitatError > 0) {
				--intensitatError;
			} else {
				intensitatError = Constantes.MAX_INTENSITAT_COLOR_ERROR;
				error = null;
			}
		}

	}

	private void dibuixarClicats(Graphics g) {
		Enemigo e = enemigoSelecionado;

		if (e != null) {
			Rectangle seleccio = new Rectangle((int) e.getAreaColision().x - 2, (int) e.getAreaColision().y - 2,
					e.getAreaColision().width + 4, e.getAreaColision().height + 4);
			
			// Dibuixa contorn de seleccio
			DrawerClass.dibujarRectanguloContorno(g, seleccio, Color.ORANGE);
			// Dibuixa NOM
			DrawerClass.dibujarString(g, e.getNombre(), seleccio.x - 3, seleccio.y - 5, Color.black);

			Point posicioIniciLvl = new Point(seleccio.x + e.getNombre().length() * 7 + 3, seleccio.y - 21);

			final int colorLvlF = (e.getLvlF() * 255) / Constantes.MAX_LVL_F;
			final int colorCircumfF = 255 - (e.getLvlF() * 255) / Constantes.MAX_LVL_F;

			final int colorLvlP = 50 + (e.getLvlP() * 100) / Constantes.MAX_LVL_P;
			final int colorCircumfP = (e.getLvlP() * 100) / Constantes.MAX_LVL_P;

			final Color colorTextoF = new Color(255, colorLvlF, colorLvlF);
			final Color colorCircumferenciaF = new Color(255, colorCircumfF, 0);

			final Color colorTextP = new Color(colorLvlP / 50, colorLvlP, colorLvlP);
			final Color colorCircumferenciaP = new Color(102 - colorCircumfP, 204 - colorCircumfP * 2, 0);


			if (e.getLvlF() < 10) {
				// Dibuixa LVLP
				DrawerClass.dibujarCircumferenciaRellena(g, seleccio.x + e.getNombre().length() * 7 + 16,
						seleccio.y - 15,
						14, 14, colorCircumferenciaP);
				g.setFont(Constantes.FUENTE_PRINCIPAL);
				DrawerClass.dibujarString(g, e.getLvlP() + "", seleccio.x + e.getNombre().length() * 7 + 20,
						seleccio.y - 3, colorTextP);

				// Dibuixa LVLF
				DrawerClass.dibujarCircumferenciaRellena(g, posicioIniciLvl, 18, 18, colorCircumferenciaF);
				g.setFont(Constantes.FUENTE_PRINCIPAL_GRANDE);
				posicioIniciLvl.translate(6, 14);
				DrawerClass.dibujarString(g, e.getLvlF() + "", posicioIniciLvl, colorTextoF);
			} else {
				// Dibuixa LVLP
				DrawerClass.dibujarCircumferenciaRellena(g, seleccio.x + e.getNombre().length() * 7 + 20,
						seleccio.y - 15,
						14, 14, colorCircumferenciaP);
				g.setFont(Constantes.FUENTE_PRINCIPAL);
				DrawerClass.dibujarString(g, e.getLvlP() + "", seleccio.x + e.getNombre().length() * 7 + 25,
						seleccio.y - 2, colorTextP);

				// Dibuixa LVLF
				DrawerClass.dibujarCircumferenciaRellena(g, posicioIniciLvl.x, posicioIniciLvl.y - 2, 24, 20,
						colorCircumferenciaF);
				g.setFont(Constantes.FUENTE_PRINCIPAL_GRANDE);
				posicioIniciLvl.translate(4, 14);
				DrawerClass.dibujarString(g, e.getLvlF() + "", posicioIniciLvl, colorTextoF);

			}


			// DibuixaBarraVT
			DrawerClass.dibujarRectanguloRelleno(g, seleccio.x + e.getAnchoSprite(), seleccio.y + 5, e.getVt()
					* 50 / Constantes.MAX_VARIABLES, 5,	Colores.ColorBarraVT);
			g.setFont(Constantes.FUENTE_PRINCIPAL_PEQUEÑA);


			// Dibuixar Barra Armadura
			DrawerClass.dibujarRectanguloRelleno(g, seleccio.x + e.getAnchoSprite(), seleccio.y + 10,
					(int) (e.getArmery() * 50 / Constantes.MAX_ARMERY), 3,
					Colores.ColorBarraArmadura);

			// Dibuixar VT
			DrawerClass.dibujarString(g, e.getVt() * 100 / Constantes.MAX_VARIABLES + "%",
					seleccio.x + e.getAnchoSprite() + 4, seleccio.y + 11,
					Color.WHITE);

			// Dibuixar PM

			DrawerClass.dibujarRectanguloRelleno(g, seleccio.x + e.getAnchoSprite(), seleccio.y + 15, e.getPm() * 50
					/ Constantes.MAX_VARIABLES, 5, Colores.ColorBarraPM);
			DrawerClass.dibujarRectanguloRelleno(g, seleccio.x + e.getAnchoSprite(), seleccio.y + 20, e.getRecargaPm()
					* 50 / Constantes.MAX_RECARGA_PM, 2, Colores.ColorSBarraPM);

			DrawerClass.dibujarString(g, e.getPm() * 100 / Constantes.MAX_VARIABLES + "%",
					seleccio.x + e.getAnchoSprite() + 4, seleccio.y + 20, Color.WHITE);



			g.setFont(Constantes.FUENTE_PRINCIPAL);
		}
	}

	private void dibuixarEnemics(Graphics g, int posicionX, int posicionY) {
		for (int i = 0; i < enemigos.size(); ++i) {
			enemigos.get(i).dibujar(g, posicionX, posicionY);
		}
	}

	private void dibuixarMapa(Graphics g, int posicionX, int posicionY) {
		for (int y = 0; y < this.alto; ++y) {
			for (int x = 0; x < this.ancho; ++x) {
				BufferedImage img = paleta[sprites[x + y * this.ancho]].getImagen();

				int puntoX = x * Constantes.LADO_SPRITE - posicionX + MARGEN_X;
				int puntoY = y * Constantes.LADO_SPRITE - posicionY + MARGEN_Y;

				if (puntoY < Constantes.ALTO_JUEGO - MenuInferior.altoMenu)
					DrawerClass.dibujarImagen(g, img, puntoX, puntoY);
			}
		}
	}

	private void dibuixaRectanglesColisio(Graphics g, int posicionX, int posicionY) {
		g.setColor(Color.GREEN);
		for (int i = 0; i < areasColision.size(); ++i) {
			Rectangle aux = areasColision.get(i);
			DrawerClass.dibujarRectanguloContorno(g, aux.x, aux.y, aux.width, aux.height);
		}

		DrawerClass.dibujarRectanguloContorno(g, new Rectangle(-posicionX + MARGEN_X, -posicionY + MARGEN_Y, ancho
				* Constantes.LADO_SPRITE, alto * Constantes.LADO_SPRITE));
	}

	private void dibuixarRectangleSortida(Graphics g) {
		g.setColor(Color.MAGENTA);

		for (int i = 0; i < zonaSalida.size(); ++i) {
			DrawerClass.dibujarRectanguloRelleno(g, zonaSalida.get(i).x, zonaSalida.get(i).y, zonaSalida.get(i).width,
					zonaSalida.get(i).height);
		}
	}

	public Rectangle getBordes(final int posicionX, final int posicionY, final int anchoJugador, final int altoJugador) {
		int x = MARGEN_X - posicionX + anchoJugador;
		int y = MARGEN_Y - posicionY + altoJugador;
		int ancho = this.ancho * Constantes.LADO_SPRITE - anchoJugador * 2;
		int alto = this.alto * Constantes.LADO_SPRITE - altoJugador * 2;

		return new Rectangle(x, y, ancho, alto);
	}

	public void setEnemicClicat(Enemigo e) {
		enemigoSelecionado = e;
	}

	public Enemigo getEnemicClicat() {
		return enemigoSelecionado;
	}

	public Point getPosicionInicial() {
		return posicionInicial;
	}

	public ArrayList<Point> getPuntoSalida() {
		return puntosSalida;
	}

	public String getSiguienteMapa(final int i) {
		return siguientesMapa.get(i);
	}

	public ArrayList<Rectangle> getZonaSalida() {
		return zonaSalida;
	}

	public Point getPuntoLlegada(int i) {
		return puntoLlegada.get(i);
	}

	public void setPosicionInicial(Point p) {
		posicionInicial = p;
	}

	public int getAncho() {
		return ancho;
	}

	public int getAlto() {
		return alto;
	}

	public int getMargenX() {
		return MARGEN_X;
	}

	public int getMargenY() {
		return MARGEN_Y;
	}

	public ArrayList<Enemigo> getObjetos() {
		return enemigos;
	}

	public void setError(String e) {
		error = e;
		intensitatError = Constantes.MAX_INTENSITAT_COLOR_ERROR;
	}

	// Provisional
	private void setAccionTurno(Accio accioRetorn) {
		accionsPendents.add(accioRetorn);
	}

	public ArrayList<Accio> getAccionsPendents() {
		return accionsPendents;
	}

	public void clearAccionsPendents() {
		accionsPendents.clear();
	}
}
