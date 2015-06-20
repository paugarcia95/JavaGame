package principal.control;

import principal.interfazUsuario.MenuInferior;
import principal.mapas.Mapa;

public final class GestorControles {
	private static Mapa mapa = null;
	private static MenuInferior menuInferior = null;

	public static final Teclado teclado = new Teclado();
	// public static final Raton raton = new Raton();

	public static void setMapa(Mapa map) {
		mapa = map;
	}

	public static void setMenuInferior(MenuInferior menu) {
		menuInferior = menu;
	}

	public static Mapa getMapa() {
		return mapa;
	}

	public static MenuInferior getMenuInferior() {
		return menuInferior;
	}
}
