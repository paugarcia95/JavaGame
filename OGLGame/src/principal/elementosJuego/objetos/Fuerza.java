package principal.elementosJuego.objetos;

import principal.Constantes;
import principal.elementosJuego.accions.Accio;
import principal.entes.Jugador;

public class Fuerza extends Objeto {

	public Fuerza() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(0, 2).getImagen();
	}

	@Override
	public Accio usar(Jugador j) {
		return null;
	}

	@Override
	public int getFormaUs() {
		return 1;
	}
}
