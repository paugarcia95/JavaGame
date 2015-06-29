package principal.control;

import estadoJuego.mapas.Mapa;

public final class GestorControles {
	private static Mapa mapa = null;

	public static final Teclado teclado = new Teclado();
	// public static final Raton raton = new Raton();

	public static void setMapa(Mapa map) {
		mapa = map;
	}

	public static Mapa getMapa() {
		return mapa;
	}
}
