package principal.maquinaEstado.juego;

import java.awt.Graphics;

import principal.mapas.Mapa;
import principal.maquinaEstado.EstadoJuego;

public class GestorJuego implements EstadoJuego {
	
	private GestorMapa gestorMapa;
	Mapa mapa = new Mapa("/texto/prueba");

	// String texto = CargadorRecursos.leerArchivoTexto("/texto/prueba");

	@Override
	public void actualizar() {

	}

	@Override
	public void dibujar(Graphics g) {
		mapa.dibujar(g);
	}

}
