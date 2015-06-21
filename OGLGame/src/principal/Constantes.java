package principal;

import java.awt.Font;

import principal.herramientas.CargadorRecursos;
import principal.sprites.HojaSprites;

public final class Constantes {

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
	public static final String RUTA_ICONO_RATON = "/imagenes/iconos/iconoCursor2.png";
	public static final String RUTA_ICONO_VENTANA = "/imagenes/iconos/iconoVentana.png";
	public static final String RUTA_PERSONAJE = "/imagenes/hojasPersonajes/jugador1.png";
	public static final String RUTA_ICONOS_MENU = "/imagenes/menu/MenuTiles.png";

	public static final HojaSprites HOJA_SPRITES_MENU = new HojaSprites(Constantes.RUTA_ICONOS_MENU, 32, 32, false);

	public static final Font FUENTE_PRINCIPAL = CargadorRecursos.cargarFuente("/fuentes/FuentePrincipal.ttf", 12f);
	public static final Font FUENTE_PRINCIPAL_PEQUEÑA = CargadorRecursos.cargarFuente("/fuentes/FuentePrincipal.ttf", 8f);
	public static final Font FUENTE_PRINCIPAL_GRANDE = CargadorRecursos.cargarFuente("/fuentes/FuentePrincipal.ttf",16f);

	public static final String RUTA_ENEMIGO_SOMBRA = "/imagenes/enemigos/Shadow (by Nemu (modified)).png";

	// CONSTANTS DEL JOC

	public static final int MAX_LVL_F = 100;
	public static final int MAX_LVL_P = 10;

	public static final int MAX_RECUPERACION = 100;
	public static final int MAX_VARIABLES = 1000;
	public static final int MAX_ARMERY = 5;

	public static final int MAX_RECARGA_PM = 2000;
	public static final int MAX_DISTANCIA_PARA_ATACAR = 15;

	// Missatges ERROR
	public static final String E_CAP_ENEMIC_SELECCIONAT = "No puedes realizar esta acción. Debes seleccionar un enemigo";
	public static final String E_OBJECTE_BUIT = "No tienes ninguna acción asociada a este botón";
	public static final String E_ENEMIGO_FUERA_ALCANZE = "El objetivo está fuera de tu alcanze";

	public static final int MAX_INTENSITAT_COLOR_ERROR = 0xff;

}
