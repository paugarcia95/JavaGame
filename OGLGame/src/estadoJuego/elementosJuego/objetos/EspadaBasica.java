package estadoJuego.elementosJuego.objetos;

import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.elementosJuego.accions.Atac;
import estadoJuego.entes.Jugador;
import principal.Constantes;

public class EspadaBasica extends Objeto {

	private final int fuerza = 2;

	public EspadaBasica() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(0, 1).getImagen();
		maxTimeToUse = 100;

		radioAccion = 5;
	}

	@Override
	public Accio usar(Jugador j) {
		if (nextUse >= maxTimeToUse) {
			nextUse = 0;
			return new Atac(j, fuerza);
		} else
			return new Accio();
	}

	@Override
	public int getFormaUs() {
		return 1;
	}

}
