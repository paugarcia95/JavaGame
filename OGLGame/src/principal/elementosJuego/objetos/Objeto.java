package principal.elementosJuego.objetos;

import java.awt.image.BufferedImage;

import principal.elementosJuego.accions.Accio;
import principal.entes.Jugador;

public abstract class Objeto {
	protected BufferedImage sprite;

	public BufferedImage getSprite() {
		return sprite;
	}

	public abstract Accio usar(Jugador j);

	/**
	 * 1 - Cap a l'enemic 2 - Per tu mateix 3 - Área
	 */
	public abstract int getFormaUs();

}
