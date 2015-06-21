package principal.elementosJuego.objetos;

import principal.Constantes;
import principal.elementosJuego.accions.Accio;
import principal.elementosJuego.accions.Atac;
import principal.entes.Jugador;

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
