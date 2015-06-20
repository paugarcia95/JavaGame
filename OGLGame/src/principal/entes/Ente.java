package principal.entes;

import principal.Constantes;

public abstract class Ente {
	String nombre = "¿?";

	protected int lvlF = 1;
	protected int lvlP = 0;

	protected int vt = 1000;
	protected int pm = 188;

	protected int recargaPm = 0;

	protected double armery = 0;

	public Ente() {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return nombre;
	}

	public int getVt() {
		return vt;
	}

	public void modificaVt(int incVT) {
		vt += incVT;

		if (vt < 0)
			vt = 0;
		if (vt > Constantes.MAX_VARIABLES)
			vt = Constantes.MAX_VARIABLES;
	}

	public int getPm() {
		return pm;
	}

	public int getLvlF() {
		return lvlF;
	}

	public int getLvlP() {
		return lvlP;
	}

	public int getRecargaPm() {
		return recargaPm;
	}

	public double getArmery() {
		return armery;
	}

}
