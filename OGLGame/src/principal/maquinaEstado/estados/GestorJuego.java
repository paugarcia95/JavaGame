package principal.maquinaEstado.estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.VariablesGlobales;
import principal.control.Raton;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.EstadoJuego;
import estadoJuego.entes.Jugador;
import estadoJuego.interfazUsuario.MenuInferior;
import estadoJuego.mapas.Mapa;

public class GestorJuego implements EstadoJuego {
	
	private volatile Mapa mapa;
	private volatile Jugador jugador;

	private volatile MenuInferior menuInferior;

	public GestorJuego() {
		iniciarMapa(Constantes.RUTA_MAPA);
		iniciarJugador();

		menuInferior = new MenuInferior(jugador);
	}

	private void recargarJuego(final int i) {
		final String ruta = "/mapas/" + mapa.getSiguienteMapa(i);

		iniciarMapa(ruta, mapa.getPuntoLlegada(i));
		jugador.mover(mapa);
	}

	private void iniciarMapa(final String ruta) {
		mapa = new Mapa(ruta);
	}

	private void iniciarMapa(final String ruta, final Point posicionInicial) {
		mapa = new Mapa(ruta);
		mapa.setPosicionInicial(posicionInicial);
	}

	private void iniciarJugador() {
		jugador = new Jugador(mapa);
	}


	@Override
	public void actualizar() {
		ArrayList<Rectangle> zonasSalida = mapa.getZonaSalida();
		for (int i = 0; i < zonasSalida.size(); ++i) {
			if (jugador.getLIMITE_ARRIBA().intersects(zonasSalida.get(i))) {
				recargarJuego(i);
			}
		}

		jugador.actualizar();
		mapa.actualizar((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
		menuInferior.actualizar();
	}

	@Override
	public void dibujar(final Graphics g) {
		if (mapa != null && jugador != null && menuInferior != null) {
			mapa.dibujar(g, (int) jugador.getPosicionX(), (int) jugador.getPosicionY());
			jugador.dibujar(g);
			menuInferior.dibujar(g);

			mostrarInformacio(g);
		}
	}

	private void mostrarInformacio(final Graphics g) {
		// Posicio
		DrawerClass.dibujarString(g, "X = " + (int) jugador.getPosicionX() / Constantes.LADO_SPRITE, 20, 20, Color.RED);
		DrawerClass.dibujarString(g, "Y = " + (int) (jugador.getPosicionY() / Constantes.LADO_SPRITE + 1), 20, 30);

		// Posicio Ratoli
		if (VariablesGlobales.debug1) {
			DrawerClass.dibujarString(g, " | RX = " + SuperficieDibujo.raton.getPosicion().getX(), 60, 20);
			DrawerClass.dibujarString(g, " | RY = " + SuperficieDibujo.raton.getPosicion().getY(), 60, 30);

			DrawerClass.dibujarString(g, "OPF: " + DrawerClass.getContadorObjetos(), 20, 65, Color.GRAY);
		}
		DrawerClass.reiniciarContador();

		// FPS & APS
		DrawerClass.dibujarString(g, "FPS: " + GestorPrincipal.getFps(), 20, 45, Color.GRAY);
		DrawerClass.dibujarString(g, "APS: " + GestorPrincipal.getAps(), 20, 55);
	}

	@Override
	public void limpiarPantalla(Graphics g) {
		DrawerClass.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA,
				(int) (Constantes.ALTO_PANTALLA_COMPLETA - (MenuInferior.altoMenu + 1) * Constantes.FACTOR_ESCALADO_Y),
				Color.black);
	}

	@Override
	public void iniciar() {
		Raton.setImagenPrincipal();
	}

}
