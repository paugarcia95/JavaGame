package estadoJuego.entes;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.mapas.Mapa;
import estadoJuego.sprites.HojaSprites;
import principal.Constantes;
import principal.VariablesGlobales;
import principal.herramientas.DrawerClass;

public abstract class Enemigo extends Ente {
	protected Mapa mapa;

	private double posicionX = 0.0;
	private double posicionY = 0.0;

	private double relativaX = 0.0;
	private double relativaY = 0.0;

	protected int direccion = 0;
	protected double velocidad = 1.0;

	protected int animacion = 0;
	protected int estado = 0;
	protected boolean enMovimiento = false;
	protected int movimientoTurno = -1;

	protected final HojaSprites hs;
	protected final int anchoSprite;
	protected final int altoSprite;
	protected BufferedImage imagenActual;

	protected final int ANCHO_COLISION;
	protected final int ALTO_COLISION;
	protected final int COMPENSACION_COLISION_X;
	protected final int COMPENSACION_COLISION_Y;

	protected Rectangle COLISION;

	protected boolean estaVivo = true;

	private Accio accionTurno = null;

	public Enemigo(final Mapa mapa, final String rutaSprite, final int anchoSprite, final int altoSprite) {
		this.mapa = mapa;

		this.anchoSprite = anchoSprite;
		this.altoSprite = altoSprite;

		hs = new HojaSprites(rutaSprite, anchoSprite, altoSprite, false);
		imagenActual = hs.getSprite(0).getImagen();

		ANCHO_COLISION = anchoSprite * 2 / 3;
		ALTO_COLISION = altoSprite / 2;

		COMPENSACION_COLISION_X = anchoSprite / 6;
		COMPENSACION_COLISION_Y = altoSprite / 4;

		COLISION = new Rectangle((int) posicionX + COMPENSACION_COLISION_X, (int) posicionY + COMPENSACION_COLISION_Y,
				ANCHO_COLISION, ALTO_COLISION);

		this.nombre = "Enemigo";
		this.vt = 100;
		this.pm = 100;
		this.armery = 0;
		this.lvlF = 1;
		this.lvlP = 0;
	}

	public Enemigo(final Mapa mapa, final double posicionX, final double posicionY, final String rutaSprite,
			final int anchoSprite, final int altoSprite, final int anchoColision, final int altoColision,
			final int compensacionColisionX, final int compensacionColisionY, final int vt, final int pm,
			final double armery, final int lvlF, final int lvlP, final String nom) {
		this.mapa = mapa;
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		relativaX = -1;
		relativaY = -1;
		direccion = 0;

		this.anchoSprite = anchoSprite;
		this.altoSprite = altoSprite;

		hs = new HojaSprites(rutaSprite, anchoSprite, altoSprite, false);
		imagenActual = hs.getSprite(0).getImagen();

		if (anchoColision < 0) ANCHO_COLISION = anchoSprite * 2 / 3;
		else ANCHO_COLISION = anchoColision;
		if (altoColision < 0) ALTO_COLISION = altoSprite / 2;
		else ALTO_COLISION = altoColision;

		if (compensacionColisionX < 0) COMPENSACION_COLISION_X = anchoSprite / 6;
		else COMPENSACION_COLISION_X = compensacionColisionX;
		if (compensacionColisionY < 0)COMPENSACION_COLISION_Y = altoSprite / 2;
		else COMPENSACION_COLISION_Y = compensacionColisionX;
		

		COLISION = new Rectangle((int) posicionX + COMPENSACION_COLISION_X, (int) posicionY + COMPENSACION_COLISION_Y,
				ANCHO_COLISION, ALTO_COLISION);

		this.nombre = nom;
		this.vt = vt;
		this.pm = pm;
		this.armery = armery;
		this.lvlF = lvlF;
		this.lvlP = lvlP;
	}

	public abstract Rectangle getAreaAtaque();
	protected abstract Accio actualizarVariables(final Accio accio);
	protected void actualitzarVariablesDespres() {}

	public Accio actualizar(final int compensacionX, final int compensacionY) {
		if (recargaPm < Constantes.MAX_RECARGA_PM) {
			++recargaPm;
		}
		if (recargaPm == Constantes.MAX_RECARGA_PM && pm < Constantes.MAX_VARIABLES) {
			++pm;
		}

		this.relativaX = (this.posicionX - compensacionX + mapa.getMargenX());
		this.relativaY = (this.posicionY - compensacionY + mapa.getMargenY());

		// Funció que on cada enemic podrà determinar com es vol moure

		enMovimiento = false;
		Accio accio = actualizarVariables(accionTurno);

		if (movimientoTurno == 0 || movimientoTurno == 1 || movimientoTurno == 2 || movimientoTurno == 3) {
			if (movimientoCorrecto(movimientoTurno, -compensacionX + mapa.getMargenX(),
					-compensacionY + mapa.getMargenY())) {
				mover(movimientoTurno, -compensacionX + mapa.getMargenX(), -compensacionY + mapa.getMargenY());
				enMovimiento = true;
			} else {
				actualizarColision();
			}
		} else {
			actualizarColision();
		}
		
		actualitzarVariablesDespres();

		// Esborro totes les variables que he fet servir i retorno
		movimientoTurno = -1;
		accionTurno = null;
		
		return accio;
	}

	private void actualizarColision() {
		COLISION = new Rectangle((int) relativaX + COMPENSACION_COLISION_X,
				(int) (relativaY + COMPENSACION_COLISION_Y), ANCHO_COLISION, ALTO_COLISION);
	}

	private void mover(final int movimiento, final int compensacionX, final int compensacionY) {
		direccion = movimiento;
		
		// La direccio és per la fulla d'sprites:
		// 0 -> abaix
		// 1 -> esquerra
		// 2 -> dreta
		// 3 -> dalt

		switch (movimiento) {
			case 0 :
				posicionY += velocidad;
				break;
			case 1 :
				posicionX -= velocidad;
				break;
			case 2 :
				posicionX += velocidad;
				break;
			case 3 :
				posicionY -= velocidad;
				break;
		}
	}

	protected boolean movimientoCorrecto(final int desplacament, final int x, final int y) {
		ArrayList<Rectangle> colisiones = mapa.areasColision;
		Rectangle futuraPosicio = null;

		switch (desplacament) {
			case 0 :
				futuraPosicio = new Rectangle((int) relativaX + COMPENSACION_COLISION_X, (int) (relativaY
						+ COMPENSACION_COLISION_Y + velocidad), ANCHO_COLISION, ALTO_COLISION);
				break;
			case 1 :
				futuraPosicio = new Rectangle((int) (relativaX - velocidad + COMPENSACION_COLISION_X), (int) relativaY
						+ COMPENSACION_COLISION_Y, ANCHO_COLISION, ALTO_COLISION);
				break;
			case 2 :
				futuraPosicio = new Rectangle((int) (relativaX + velocidad + COMPENSACION_COLISION_X), (int) relativaY
						+ COMPENSACION_COLISION_Y, ANCHO_COLISION, ALTO_COLISION);
				break;
			case 3 :
				futuraPosicio = new Rectangle((int) relativaX + COMPENSACION_COLISION_X, (int) (relativaY
						+ COMPENSACION_COLISION_Y - velocidad), ANCHO_COLISION, ALTO_COLISION);
				break;
		}

		// Si interesecta amb algun objecte colisionable
		if (colisiones != null) {
			for (int i = 0; i < colisiones.size(); ++i) {
				if (futuraPosicio.intersects(colisiones.get(i)))
					return false;
			}
		}

		// Si interescta amb el personatje
		if (futuraPosicio.intersects(VariablesGlobales.RectanguloColisionJugador))
			return false;

		// Si se surt del mapa
		if ((new Rectangle(x, y, mapa.getAncho() * Constantes.LADO_SPRITE, mapa.getAlto() * Constantes.LADO_SPRITE)
				.contains(futuraPosicio))) {
			// Actualitzo l'area de colisio i retorno cert
			COLISION = futuraPosicio;
			return true;
		} else
			return false;
	}

	public void dibujar(Graphics g, int compensacionX, int compensacionY) {
		DrawerClass.dibujarImagen(g, imagenActual, (int) relativaX, (int) relativaY);
	}

	protected int getDireccion() {
		return direccion;
	}

	public double getPosicionX() {
		return posicionX / anchoSprite;
	}

	public double getPosicionY() {
		return posicionY / altoSprite;
	}

	public double getPosicioXRelativa() {
		return relativaX;
	}

	public double getPosicioYRelativa() {
		return relativaY;
	}

	public int getAnchoSprite() {
		return anchoSprite;
	}

	public int getAltoSprite() {
		return altoSprite;
	}

	public Rectangle getAreaColision() {
		return COLISION;
	}

	public boolean estaVivo() {
		return estaVivo;
	}

	public void setAccion(Accio accion) {
		accionTurno = accion;
	}


}

