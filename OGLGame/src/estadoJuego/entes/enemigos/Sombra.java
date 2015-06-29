package estadoJuego.entes.enemigos;

import java.awt.Rectangle;
import java.util.Random;

import principal.Constantes;
import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.elementosJuego.accions.Atac;
import estadoJuego.elementosJuego.accions.Mort;
import estadoJuego.entes.Enemigo;
import estadoJuego.mapas.Mapa;

public class Sombra extends Enemigo {

	public Sombra(final Mapa mapa, final double posicionX, final double posicionY, final int vt,
				final int pm, final double armery, final int lvlF, final int lvlP) {
		super(mapa, posicionX, posicionY, Constantes.RUTA_ENEMIGO_SOMBRA, 40, 51, -1, -1, -1, -1, vt, pm, armery, lvlF,
				lvlP, "Sombra");
	}
	
	private int anteriorMoviment = 0;
	private int comptaPassos = 0;

	private boolean siendoAtacado = false;

	@Override
	protected Accio actualizarVariables(final Accio accio) {
		Accio accioRetorn = new Accio();

		if (vt > 0 && accio != null && accio.getType().equals("ATAC")) {
			siendoAtacado = true;
			animacion = 0;
			actualitzaAtacat((Atac) accio);
			accioRetorn = new Atac(this);
		} else if (!siendoAtacado && !acabadoNacer) {
			actualizarMovimiento();
		}

		if (!estaVivo && estado == 10)
			return new Mort();

		return accioRetorn;
	}

	protected void actualizarMovimiento() {
		if (vt <= 0) {
			movimientoTurno = -1;
			return;
		}

		Random r = new Random();

		if (comptaPassos < 5) {
			++comptaPassos;
			movimientoTurno = anteriorMoviment;
			return;
		} else
			comptaPassos = 0;

		if (r.nextBoolean() && r.nextBoolean() && r.nextBoolean()) {
			if (anteriorMoviment == 0) {
				while ((anteriorMoviment = r.nextInt(4)) == 3);
			} else if (anteriorMoviment == 1) {
				while ((anteriorMoviment = r.nextInt(4)) == 2);
			} else if (anteriorMoviment == 2) {
				while ((anteriorMoviment = r.nextInt(4)) == 1);
			} else {
				while ((anteriorMoviment = r.nextInt(4)) == 0);
			}
		}

		movimientoTurno = anteriorMoviment;
	}

	private void actualitzaAtacat(Atac accio) {
		accio.getHurt(this);
	}

	protected void actualitzarVariablesDespres() {
		// Actualitzo l'sprite
		if (vt == 0) {
			actualitzarSpriteMort();
			estaVivo = false;
		} else if (acabadoNacer) {
			actualizarSpriteNacer();
		} else if (siendoAtacado) {
			actualitzarSpriteAtacat();
		} else {
			actualizarSpriteMoviment();
		}
	}

	private void actualizarSpriteNacer() {
		final int velocitatAnimacio = 54;

		if (animacion < velocitatAnimacio) {
			++animacion;
		} else {
			animacion = 0;
		}

		if (animacion < velocitatAnimacio / 9) {
			estado = 0;
		} else if (animacion < velocitatAnimacio * 2 / 9) {
			estado = 1;
		} else if (animacion < velocitatAnimacio * 3 / 9) {
			estado = 2;
		} else if (animacion < velocitatAnimacio * 4 / 9) {
			estado = 3;
		} else if (animacion < velocitatAnimacio * 5 / 9) {
			estado = 4;
		} else if (animacion < velocitatAnimacio * 6 / 9) {
			estado = 5;
		} else if (animacion < velocitatAnimacio * 7 / 9) {
			estado = 6;
		} else if (animacion < velocitatAnimacio * 8 / 9) {
			estado = 7;
		} else {
			estado = 8;
			acabadoNacer = false;
		}

		imagenActual = hs.getSprite(estado, 4).getImagen();
	}

	private void actualizarSpriteMoviment() {
		final int velocitatAnimacio = 60;

		if (animacion < velocitatAnimacio) {
			++animacion;
		} else {
			animacion = 0;
		}

		if (animacion < velocitatAnimacio / 12) {
			estado = 0;
		} else if (animacion < velocitatAnimacio / 6) {
			estado = 1;
		} else if (animacion < velocitatAnimacio / 4) {
			estado = 2;
		} else if (animacion < velocitatAnimacio / 3) {
			estado = 3;
		} else if (animacion < velocitatAnimacio * 5 / 12) {
			estado = 4;
		} else if (animacion < velocitatAnimacio / 2) {
			estado = 5;
		} else if (animacion < velocitatAnimacio * 7 / 12) {
			estado = 6;
		} else if (animacion < velocitatAnimacio * 2 / 3) {
			estado = 7;
		} else if (animacion < velocitatAnimacio * 3 / 4) {
			estado = 8;
		} else if (animacion < velocitatAnimacio * 5 / 6) {
			estado = 9;
		} else if (animacion < velocitatAnimacio * 11 / 12) {
			estado = 10;
		} else {
			estado = 11;
		}

		if (!enMovimiento) {
			estado = 0;
			animacion = 0;
		}

		imagenActual = hs.getSprite(estado, direccion).getImagen();
	}

	private void actualitzarSpriteMort() {
		final int velocitatAnimacio = 66;

		if (estaVivo)
			animacion = 0;
		else
			++animacion;

		if (animacion < velocitatAnimacio / 11) {
			estado = 0;
		} else if (animacion < velocitatAnimacio * 2 / 11) {
			estado = 1;
		} else if (animacion < velocitatAnimacio * 3 / 11) {
			estado = 2;
		} else if (animacion < velocitatAnimacio * 4 / 11) {
			estado = 3;
		} else if (animacion < velocitatAnimacio * 5 / 11) {
			estado = 4;
		} else if (animacion < velocitatAnimacio * 6 / 11) {
			estado = 5;
		} else if (animacion < velocitatAnimacio * 7 / 11) {
			estado = 6;
		} else if (animacion < velocitatAnimacio * 8 / 11) {
			estado = 7;
		} else if (animacion < velocitatAnimacio * 9 / 11) {
			estado = 8;
		} else if (animacion < velocitatAnimacio * 10 / 11) {
			estado = 9;
		} else {
			estado = 10; // Sobra una animcio (es buida)
		}

		imagenActual = hs.getSprite(estado, 6).getImagen();
	}

	private void actualitzarSpriteAtacat() {
		final int velocitatAnimacio = 36;

		if (animacion < velocitatAnimacio) {
			++animacion;
		} else {
			animacion = 0;
		}

		if (animacion < velocitatAnimacio / 6) {
			estado = 0;
		} else if (animacion < velocitatAnimacio / 3) {
			estado = 1;
		} else if (animacion < velocitatAnimacio / 2) {
			estado = 2;
		} else if (animacion < velocitatAnimacio * 2 / 3) {
			estado = 3;
		} else if (animacion < velocitatAnimacio * 5 / 6) {
			estado = 4;
		} else {
			estado = 5;
			siendoAtacado = false;
		}

		imagenActual = hs.getSprite(estado, 7).getImagen();
	}

	public Rectangle getAreaAtaque() {
		return COLISION;
	}
}
