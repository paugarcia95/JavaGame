package principal.elementosJuego.objetos;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.elementosJuego.accions.Accio;
import principal.entes.Ente;
import principal.entes.Jugador;

public abstract class Objeto {
	protected BufferedImage sprite;

	protected int maxTimeToUse = -1;
	protected int nextUse = -1;
	protected boolean sePuedeUtilizar;

	protected int radioAccion = 1;

	public BufferedImage getSprite() {
		return sprite;
	}

	/**
	 * 1 - Cap a l'enemic 2 - Per tu mateix 3 - Área
	 */
	public abstract int getFormaUs();
	public abstract Accio usar(Jugador j);

	public void actualizar() {
		if (nextUse < maxTimeToUse) {
			sePuedeUtilizar = false;
			++nextUse;
		} else
			sePuedeUtilizar = true;
	}

	public int getNextUse() {
		return nextUse;
	}

	public int getMaxTimeToUse() {
		return maxTimeToUse;
	}

	public boolean sePuedeUtilizar() {
		return sePuedeUtilizar;
	}

	public boolean dentroAreaAccion(Ente e1, Ente e2) {
		Rectangle area = getAreaAtaque(e1);

		return area.intersects(e2.getAreaAtaque());
	}

	public Rectangle getAreaAtaque(Ente e) {
		Rectangle area = new Rectangle(e.getAreaAtaque());
		area.translate(-radioAccion, -radioAccion);
		area.grow(radioAccion * radioAccion, radioAccion * radioAccion);

		return area;
	}
}
