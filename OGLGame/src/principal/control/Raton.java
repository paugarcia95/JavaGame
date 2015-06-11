package principal.control;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import principal.herramientas.CargadorRecursos;

public class Raton {
	private final Cursor cursor;

	public Raton() {
		Toolkit configuracion = Toolkit.getDefaultToolkit();

		BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoCursor.png");

		Point punta = new Point(0, 0);

		this.cursor = configuracion.createCustomCursor(icono, punta, "Cursor joc");
	}

	public Cursor getCursor() {
		return this.cursor;
	}
}
