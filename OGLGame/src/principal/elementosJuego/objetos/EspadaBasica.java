package principal.elementosJuego.objetos;

import principal.Constantes;
import principal.elementosJuego.accions.Atac;
import principal.entes.Jugador;

public class EspadaBasica extends Objeto {

	private final int fuerza = 2;

	public EspadaBasica() {
		super();
		sprite = Constantes.HOJA_SPRITES_MENU.getSprite(0, 1).getImagen();
	}

	@Override
	public Atac usar(Jugador j) {
		return new Atac(j, fuerza);
	}

	@Override
	public int getFormaUs() {
		return 1;
	}

}
