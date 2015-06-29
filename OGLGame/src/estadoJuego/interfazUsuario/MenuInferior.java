package estadoJuego.interfazUsuario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.herramientas.Colores;
import principal.herramientas.DrawerClass;
import estadoJuego.elementosJuego.objetos.Objeto;
import estadoJuego.entes.Jugador;

public class MenuInferior {

	public static final int altoMenu = 64;
	public static final Rectangle areaInventario = new Rectangle(0, Constantes.ALTO_JUEGO - altoMenu,
			Constantes.ANCHO_JUEGO, altoMenu);
	public static final Rectangle bordeInventario = new Rectangle(areaInventario.x, areaInventario.y - 1,
			areaInventario.width, 1);

	private final Jugador jugador;

	private static boolean accioPremuda = false;
	private volatile static int elementoSelecionado = 0;

	public MenuInferior(final Jugador jugador) {
		this.jugador = jugador;
	}

	public void actualizar() {
		if (accioPremuda) {
			jugador.realizaAccio(elementoSelecionado);
			accioPremuda = false;
		}
	}

	public void dibujar(final Graphics g) {
		dibujarAreaRectangulo(g);
		dibujarBarraVT(g, jugador.getVt()); // és 100 pq de moment el jugador no té vt!
		dibujarBarraPM(g, jugador.getPm(), jugador.getRecargaPm());
		dibujarBarraResistencia(g, jugador.getResistencia(), jugador.getRecuperacion());
		dibujarBarraEXP(g, jugador.getExpericencia());
		dibujarObjetosMenu(g);
	}

	private void dibujarAreaRectangulo(final Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, areaInventario, Colores.ColorFondoInventario);
		DrawerClass.dibujarRectanguloRelleno(g, bordeInventario, Color.WHITE);
	}

	private void dibujarBarraVT(final Graphics g, int vt) {
		final int POSICIO_X = areaInventario.x + 35;
		final int POSICIO_Y = areaInventario.y + 4;

		// Dibuixo sombra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y, vt / 10, 2, Colores.ColorSBarraVT);
		// Dibuixo barra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y + 2, vt / 10, 6, Colores.ColorBarraVT);

		DrawerClass.dibujarString(g, "VT", POSICIO_X - 20, POSICIO_Y + 8, Color.WHITE);
		DrawerClass.dibujarString(g, vt + "", POSICIO_X + 110, POSICIO_Y + 8);
	}

	private void dibujarBarraPM(final Graphics g, int pm, int pmRecharge) {
		int anchoPM = 100 * pm / Constantes.MAX_VARIABLES;
		int anchoRecargaPM = 100 * pmRecharge / Constantes.MAX_RECARGA_PM;

		final int POSICIO_X = areaInventario.x + 35;
		final int POSICIO_Y = areaInventario.y + 18;

		// Dibuixo sombra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y, anchoRecargaPM, 2, Colores.ColorSBarraPM);
		// Dibuixo barra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y + 2, anchoPM, 6, Colores.ColorBarraPM);

		DrawerClass.dibujarString(g, "PM", POSICIO_X - 23, POSICIO_Y + 8, Color.WHITE);
		DrawerClass.dibujarString(g, pm + "", POSICIO_X + 110, POSICIO_Y + 8);
	}

	public void dibujarBarraResistencia(final Graphics g, int resistencia, int recuperacion) {
		int anchoResistencia = 100 * resistencia / 600;
		int anchoRecuperacion = 100 * recuperacion / Constantes.MAX_RECUPERACION;

		final int POSICIO_X = areaInventario.x + 35;
		final int POSICIO_Y = areaInventario.y + 32;

		// Dibuixo sombra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y, anchoRecuperacion, 2, Colores.ColorSBarraRES);
		// Dibuixo barra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y + 2, anchoResistencia, 6, Colores.ColorBarraRES);

		DrawerClass.dibujarString(g, "RES", POSICIO_X - 25, POSICIO_Y + 8, Color.WHITE);
		DrawerClass.dibujarString(g, resistencia + "", POSICIO_X + 110, POSICIO_Y + 8);
	}

	private void dibujarBarraEXP(final Graphics g, final int exp) {
		final int POSICIO_X = areaInventario.x + 35;
		final int POSICIO_Y = areaInventario.y + 46;

		// Dibuixo sombra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y, exp, 2, Colores.ColorSBarraEXP);
		// Dibuixo barra
		DrawerClass.dibujarRectanguloRelleno(g, POSICIO_X, POSICIO_Y + 2, exp, 6, Colores.ColorBarraEXP);

		DrawerClass.dibujarString(g, "EXP", POSICIO_X - 25, POSICIO_Y + 8, Color.WHITE);
		DrawerClass.dibujarString(g, exp + "", POSICIO_X + 110, POSICIO_Y + 8);
	}

	private void dibujarObjetosMenu(Graphics g) {
		final int POSICIO_X = areaInventario.x + 210;
		final int POSICIO_Y = areaInventario.y + 10;

		final BufferedImage icono = Constantes.HOJA_SPRITES_MENU.getSprite(2, 0).getImagen();
		final BufferedImage iconoSelecionado = Constantes.HOJA_SPRITES_MENU.getSprite(7, 0).getImagen();

		for (int x = 0; x < 10; ++x) {
			if (x != elementoSelecionado) {
				DrawerClass.dibujarImagen(g, icono, POSICIO_X + x * 38, POSICIO_Y);
			} else {
				DrawerClass.dibujarImagen(g, iconoSelecionado, POSICIO_X + x * 38, POSICIO_Y);
			}

		}

		for (int x = 0; x < 10; ++x) {
			Objeto a = jugador.getElementosMenu()[x];
			if (a != null) {
				DrawerClass.dibujarImagen(g, a.getSprite(), POSICIO_X + x * 38, POSICIO_Y);

				if (!a.sePuedeUtilizar()) {
					DrawerClass.dibujarRectanguloRelleno(g, 
							POSICIO_X + x * 38, 
							POSICIO_Y, 
							Constantes.LADO_SPRITE,
							Constantes.LADO_SPRITE, 
							new Color(150, 150, 150, 100 - a.getNextUse() * 100 / a.getMaxTimeToUse() + 80));
					
					DrawerClass.dibujarRectanguloRelleno(g, 
							POSICIO_X + x * 38, 
							POSICIO_Y + 29,
							32 - a.getNextUse() * 32 / a.getMaxTimeToUse(), 
							3, new Color (195, 0, 110));
				}
			}
		}
	}

	public static void seleccionarElemento(int x) {
		elementoSelecionado = x;
	}

	public static void accioPremuda() {
		accioPremuda = true;
	}
}
