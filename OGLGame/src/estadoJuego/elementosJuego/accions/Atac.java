package estadoJuego.elementosJuego.accions;

import java.util.Random;

import estadoJuego.entes.Ente;

public class Atac extends Accio {
	private int lvlP1;

	public Atac(final Ente j1) {
		this.lvlP1 = j1.getLvlF();
	}

	public Atac(final Ente j1, final int fuerzaObjeto) {
		this.lvlP1 = j1.getLvlF() + fuerzaObjeto;
	}

	public void getHurt(final Ente j2) {
		int dany = lvlP1 - j2.getLvlF();
		double armery = j2.getArmery();

		Random r = new Random();

		if (dany >= 0)
			dany = (dany + 1) * 14;
		else
			dany += 7;

		if (r.nextBoolean())
			dany += r.nextInt(4);
		else
			dany -= r.nextInt(4);

		// Si te suficient armadura, aquesta el protegirà però alhora es danyarà
		// una mica
		if (armery >= 0.2) {
			dany -= armery * 5;
			armery -= 0.2;
			if (armery < 0.1)
				armery = 0;
		} else
			armery = 0;

		// Si restant coses, el mal que li fa es negatiu, no li pot pujar la
		// vida.
		if (dany < 0)
			dany = 0;

		j2.modificaVt(-dany);
	}

	@Override
	public String getType() {
		return "ATAC";
	}
}
