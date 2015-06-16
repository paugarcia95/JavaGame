package principal;

import java.awt.Font;

import principal.herramientas.CargadorRecursos;

public class Constantes {

	public static final int LADO_SPRITE = 32;

	public static final int ALTO_PERSONAJE = 48;
	public static final int ANCHO_PERSONAJE = 31;

	public static final int ANCHO_JUEGO = 683;
	public static final int ALTO_JUEGO = 384;

	public static final int ANCHO_PANTALLA_COMPLETA = 1366;
	public static final int ALTO_PANTALLA_COMPLETA = 768;

	public static final double FACTOR_ESCALADO_X = (double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_JUEGO;
	public static final double FACTOR_ESCALADO_Y = (double) ALTO_PANTALLA_COMPLETA / (double) ALTO_JUEGO;

	public static final int CENTRO_VENTANA_X = ANCHO_JUEGO / 2;
	public static final int CENTRO_VENTANA_Y = ALTO_JUEGO / 2;

	public static final String RUTA_MAPA = "/mapas/1.gmap";
	public static final String RUTA_ICONO_RATON = "/imagenes/iconos/iconoCursor.png";
	public static final String RUTA_ICONO_VENTANA = "/imagenes/iconos/iconoVentana.png";
	public static final String RUTA_PERSONAJE = "/imagenes/hojasPersonajes/jugador1.png";
	public static final Font FUENTE_PRINCIPAL = CargadorRecursos.cargarFuente("/fuentes/FuentePrincipal.ttf");

	// CONSTANTS DEL JOC

	public static final int MAX_RECUPERACION = 100;

	// VARIABLES GLOBALS

	public static boolean debug1 = false; // OPS + posicio ratolí
	public static boolean debug2 = false; // Col·lisions

}
