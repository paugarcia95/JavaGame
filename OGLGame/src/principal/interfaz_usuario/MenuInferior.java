package principal.interfaz_usuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import principal.Constantes;
import principal.entes.Jugador;
import principal.herramientas.DrawerClass;

public class MenuInferior {

	private final Rectangle areaInventario;
	private final Rectangle bordeInventario;

	private final Color ColorFondoInventario = new Color(23, 23, 23);
	private final Color ColorBarraVT = new Color(202, 24, 24);
	private final Color ColorSBarraVT = new Color(202, 60, 60);

	public MenuInferior(final Jugador jugador) {

		int altoMenu = 64;
		areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu, Constantes.ANCHO_JUEGO, altoMenu);
		bordeInventario = new Rectangle(areaInventario.x, areaInventario.y - 1, areaInventario.width, 1);
	}

	public void dibujar(final Graphics g, final Jugador jugador) {
		dibujarAreaRectangulo(g);
		dibujarBarraVT(g, 100); // és 100 pq de moment el jugador no té vt!
	}

	private void dibujarAreaRectangulo(final Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, areaInventario, ColorFondoInventario);
		DrawerClass.dibujarRectanguloRelleno(g, bordeInventario, Color.white);
	}

	private void dibujarBarraVT(final Graphics g, final int vt) {

		// Dibuixo barra
		DrawerClass.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + 6, vt, 6, ColorBarraVT);
		// Dibuixo sombra
		DrawerClass.dibujarRectanguloRelleno(g, areaInventario.x + 35, areaInventario.y + 4, vt, 2, ColorSBarraVT);

		DrawerClass.dibujarString(g, "VT", areaInventario.x + 15, areaInventario.y + 12, Color.WHITE);
		DrawerClass.dibujarString(g, vt + "", areaInventario.x + 145, areaInventario.y + 12);
	}


	public static void dibujarBarraResistencia(final Graphics g, int resistencia, int recuperacion) {
		int anchoResistencia = 100 * resistencia / 600;
		int anchoRecuperacion = 100 * recuperacion / Constantes.MAX_RECUPERACION;

		final int POSICIO_X = 35;
		final int POSICIO_Y = 90;

		// Contorn del rectangle
		DrawerClass.dibujarRectanguloContorno(g, POSICIO_X, POSICIO_Y, 101, 16, Color.white);

		// Barra recuperacio
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y, anchoRecuperacion, 5, new Color(225, 225, 128));
		// Barra resistencia
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y + 5, anchoResistencia, 10, new Color(225, 225, 0));

		// Text
		DrawerClass.dibujarString(g, "RESISTENCIA", POSICIO_X, POSICIO_Y - 5);
		// Valor
		DrawerClass.dibujarString(g, "" + resistencia, POSICIO_X + 10, POSICIO_Y + 10, Color.DARK_GRAY);
	}
}
