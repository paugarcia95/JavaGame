package principal.herramientas;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class CargadorRecursos {
	public static BufferedImage cargarImagenCompatibleOpaca(String ruta) {
		Image imagen = null;

		try {
			imagen = ImageIO.read(ClassLoader.class.getResource(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null),
				Transparency.OPAQUE);

		Graphics g = imagenAcelerada.getGraphics();
		DrawerClass.dibujarImagen(g, (BufferedImage) imagen, 0, 0);
		g.dispose();

		return imagenAcelerada;
	}

	public static BufferedImage cargarImagenCompatibleTranslucida(final String ruta) {
		Image imagen = null;

		try {
			imagen = ImageIO.read(ClassLoader.class.getResource(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null),
				Transparency.TRANSLUCENT);

		Graphics g = imagenAcelerada.getGraphics();
		DrawerClass.dibujarImagen(g, (BufferedImage) imagen, 0, 0);
		g.dispose();

		return imagenAcelerada;
	}

	public static String leerArchivoTexto(final String ruta) {
		String contenido = "";

		// Nos devuelbe un flujo de bytes del archivo q puede ser lo que quieras
		InputStream entradaBytes = ClassLoader.class.getResourceAsStream(ruta);
		// Nos pone los bytes como texto
		BufferedReader lector = new BufferedReader(new InputStreamReader(entradaBytes));

		String linea;

		try {
			while ((linea = lector.readLine()) != null) {
				contenido += linea;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // el finally se ejecuta haya o no funcionado el try/carch

			try {
				if (entradaBytes != null) {
					entradaBytes.close();
				}
				if (lector != null) {
					lector.close();
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return contenido;
	}

	public static Font cargarFuente(final String ruta, final float size) {
		Font fuente = null;

		InputStream entradaBytes = ClassLoader.class.getResourceAsStream(ruta);

		try {
			fuente = Font.createFont(Font.TRUETYPE_FONT, entradaBytes);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		fuente = fuente.deriveFont(size);

		return fuente;
	}

}
