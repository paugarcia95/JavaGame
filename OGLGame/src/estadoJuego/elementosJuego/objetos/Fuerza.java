package estadoJuego.elementosJuego.objetos;

import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.entes.Jugador;
import principal.Constantes;

public class Fuerza extends Objeto {

	public Fuerza() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(0, 2).getImagen();

		radioAccion = 4;
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
