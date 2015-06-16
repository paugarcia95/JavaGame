package principal.control;

import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import principal.Constantes;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.CargadorRecursos;

public class Raton extends MouseAdapter {
	private final Cursor cursor;
	private Point posicion;

	public Raton(final SuperficieDibujo sd) {
		Toolkit configuracion = Toolkit.getDefaultToolkit();

		BufferedImage icono = CargadorRecursos.cargarImagenCompatibleTranslucida(Constantes.RUTA_ICONO_RATON);

		this.cursor = configuracion.createCustomCursor(icono, new Point(0, 0), "Cursor joc");

		posicion = new Point();
		actualizarPosicion(sd);
	}

	public void actualizar(final SuperficieDibujo sd) {
		actualizarPosicion(sd);
	}

	public Cursor getCursor() {
		return this.cursor;
	}

	public Point getPosicion() {
		return posicion;
	}

	private void actualizarPosicion(final SuperficieDibujo sd) {
		final Point posicionInicial = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(posicionInicial, sd);
		posicion.setLocation(posicionInicial);
	}

}
