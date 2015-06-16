package principal;

import principal.graficos.SuperficieDibujo;
import principal.graficos.Ventana;
import principal.maquinaEstado.GestorEstados;
/*
 * Capitol 67
 */
public class GestorPrincipal {

	private boolean enFuncionamiento = false;
	private String titulo;
	private int ancho;
	private int alto;

	SuperficieDibujo sd;
	Ventana ventana;
	GestorEstados ge;

	private static int fps = 0;
	private static int aps = 0;

	private GestorPrincipal(final String titulo, final int ancho, final int alto) {
		this.titulo = titulo;
		this.ancho = ancho;
		this.alto = alto;
	}

	public static void main(String[] args) {
		GestorPrincipal gp = new GestorPrincipal("OGLGame", Constantes.ANCHO_PANTALLA_COMPLETA,
				Constantes.ALTO_PANTALLA_COMPLETA);

		gp.iniciarJuego();
		gp.iniciarBuclePrincipal();

	}

	private void iniciarJuego() {
		enFuncionamiento = true;
		inicializar();
	}

	private void inicializar() {
		sd = new SuperficieDibujo(ancho, alto);
		ventana = new Ventana(titulo, sd);
		ge = new GestorEstados();
	}

	private void iniciarBuclePrincipal() {
		int actualizacionesAcomuladas = 0;
		int framesAcomulados = 0;

		final int NS_POR_SEGUNDO = 1000000000;
		final int APS_OBJETIVO = 60; // Actualizaciones por segundo
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;

		long referenciaActualizacion = System.nanoTime();
		long referenciaContador = System.nanoTime();

		double tiempoTranscurrido;
		double delta = 0; // Cantidad de tiempo entre actualizacion

		while (enFuncionamiento) {
			final long inicioBucle = System.nanoTime();

			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;

			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;

			while (delta >= 1) {
				actualizar();
				++actualizacionesAcomuladas;
				--delta;
			}

			dibujar();
			++framesAcomulados;

			if (System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				aps = actualizacionesAcomuladas;
				fps = framesAcomulados;

				actualizacionesAcomuladas = 0;
				framesAcomulados = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}

	private void actualizar() {
		ge.actualizar();
		sd.actualizar();
	}

	private void dibujar() {
		sd.dibujar(ge);
	}

	public static int getFps() {
		return fps;
	}

	public static int getAps() {
		return aps;
	}

}
