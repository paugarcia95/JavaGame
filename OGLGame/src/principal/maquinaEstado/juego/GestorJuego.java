package principal.maquinaEstado.juego;

import java.awt.Color;
import java.awt.Graphics;

import principal.Constantes;
import principal.GestorPrincipal;
import principal.entes.Jugador;
import principal.graficos.SuperficieDibujo;
import principal.herramientas.DrawerClass;
import principal.interfaz_usuario.MenuInferior;
import principal.mapas.Mapa;
import principal.maquinaEstado.EstadoJuego;

public class GestorJuego implements EstadoJuego {
	
	private Mapa mapa;
	private Jugador jugador;

	private MenuInferior menuInferior;

	public GestorJuego() {
		iniciarMapa(Constantes.RUTA_MAPA);
		iniciarJugador();

		menuInferior = new MenuInferior(jugador);
	}

	private void recargarJuego() {
		final String ruta = "/mapas/" + mapa.getSiguienteMapa();

		iniciarMapa(ruta);
		iniciarJugador();
	}

	private void iniciarMapa(final String ruta) {
		mapa = new Mapa(ruta);
	}

	private void iniciarJugador() {
		jugador = new Jugador(mapa);
	}

	@Override
	public void actualizar() {
		if (jugador.getLIMITE_ARRIBA().intersects(mapa.getZonaSalida())) {
			recargarJuego();
		}

		jugador.actualizar();
		mapa.actualizar((int) jugador.getPosicionX(), (int) jugador.getPosicionY());
	}

	@Override
	public void dibujar(final Graphics g) {
		mapa.dibujar(g, (int) jugador.getPosicionX(), (int) jugador.getPosicionY());
		jugador.dibujar(g);

		menuInferior.dibujar(g, jugador);

		mostrarInformacio(g);
	}

	private void mostrarInformacio(final Graphics g) {
		// Posicio
		DrawerClass.dibujarString(g, "X = " + (int) jugador.getPosicionX() / Constantes.LADO_SPRITE, 20, 20, Color.RED);
		DrawerClass.dibujarString(g, "Y = " + (int) (jugador.getPosicionY() / Constantes.LADO_SPRITE + 1), 20, 30);

		// Posicio Ratoli
		if (Constantes.debug1) {
			DrawerClass.dibujarString(g, " | RX = " + SuperficieDibujo.raton.getPosicion().getX(), 60, 20);
			DrawerClass.dibujarString(g, " | RY = " + SuperficieDibujo.raton.getPosicion().getY(), 60, 30);

			DrawerClass.dibujarString(g, "OPF: " + DrawerClass.getContadorObjetos(), 20, 65, Color.GRAY);
		}
		DrawerClass.reiniciarContador();

		// FPS & APS
		DrawerClass.dibujarString(g, "FPS: " + GestorPrincipal.getFps(), 20, 45, Color.GRAY);
		DrawerClass.dibujarString(g, "APS: " + GestorPrincipal.getAps(), 20, 55);

		// Barra resistencia
		MenuInferior.dibujarBarraResistencia(g, jugador.resistencia, jugador.recuperacion);
	}

}
