package principal.mapas;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

public class Mapa {
	private String[] partes;

	private final int ancho;
	private final int alto;

	private final Sprite[] paleta;

	private final boolean[] colisiones;
	private final int[] sprites;

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

	public void dibujar(Graphics g, int posicionX, int posicionY) {
		// VIGILAR!! AQui tracta els sprites iguals
		int anchoSprite = Constantes.LADO_SPRITE;
		int altoSprite = anchoSprite;

		for (int y = 0; y < this.alto; ++y) {
			for (int x = 0; x < this.ancho; ++x) {
				BufferedImage img = paleta[sprites[x + y * this.ancho]].getImagen();
				g.drawImage(img, x * anchoSprite - posicionX, y * altoSprite - posicionY, null);
			}
		}
	}


}
