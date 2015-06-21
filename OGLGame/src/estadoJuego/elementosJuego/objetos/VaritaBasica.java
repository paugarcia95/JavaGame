package estadoJuego.elementosJuego.objetos;

import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.entes.Jugador;
import principal.Constantes;

public class VaritaBasica extends Objeto {

	public VaritaBasica() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(2, 1).getImagen();

		radioAccion = 15;
	}

	@Override
	public Accio usar(Jugador j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFormaUs() {
		// TODO Auto-generated method stub
		return 1;
	}

}
