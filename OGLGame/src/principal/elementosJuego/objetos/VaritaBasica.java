package principal.elementosJuego.objetos;

import principal.Constantes;
import principal.elementosJuego.accions.Accio;
import principal.entes.Jugador;

public class VaritaBasica extends Objeto {

	public VaritaBasica() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(2, 1).getImagen();
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