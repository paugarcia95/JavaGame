package estadoJuego.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import principal.Constantes;
import principal.VariablesGlobales;
import principal.control.GestorControles;
import principal.herramientas.DrawerClass;
import principal.sprites.HojaSprites;
import estadoJuego.elementosJuego.accions.Accio;
import estadoJuego.elementosJuego.accions.Atac;
import estadoJuego.elementosJuego.objetos.EspadaBasica;
import estadoJuego.elementosJuego.objetos.Fuerza;
import estadoJuego.elementosJuego.objetos.Objeto;
import estadoJuego.elementosJuego.objetos.VaritaBasica;
import estadoJuego.mapas.Mapa;

public class Jugador extends Ente {
	private double posicionX;
	private double posicionY;

	private int velocidad = 1;

	// La direccio és per la fulla d'sprites:
	// 0 -> abaix
	// 1 -> esquerra
	// 2 -> dreta
	// 3 -> dalt
	private int direccion;

	protected int expericencia = 75;
	protected int resistencia = 600;
	protected int recuperacion = 0;

	private boolean enMovimiento;
	private int animacion;
	private int estado;

	private HojaSprites hs;
	private volatile BufferedImage imagenActual;

	private Mapa mapa;
	
	public static final int ANCHO_COLISION = 18;
	public static final int ALTO_COLISION = 15;

	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, ANCHO_COLISION, 1);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION * 2, ANCHO_COLISION, 1);
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, 1, ALTO_COLISION);
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, 1, ALTO_COLISION);
	
	private final Rectangle areaAtaque = new Rectangle(
			Constantes.CENTRO_VENTANA_X - Constantes.ANCHO_PERSONAJE / 2, 
			Constantes.CENTRO_VENTANA_Y - Constantes.ANCHO_PERSONAJE / 2, 
			Constantes.ANCHO_PERSONAJE,
			Constantes.ALTO_PERSONAJE);

	private Objeto[] elementosMenu = new Objeto[10];
	private int elementoSelecionado = 0;

	public Jugador(Mapa mapa) {
		posicionX = mapa.getPosicionInicial().getX();
		posicionY = mapa.getPosicionInicial().getY();

		direccion = 0;
		animacion = 0;
		estado = 0;

		enMovimiento = false;
		this.mapa = mapa;
		
		hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.ANCHO_PERSONAJE,
				Constantes.ALTO_PERSONAJE, false);
		imagenActual = hs.getSprite(0).getImagen();

		elementosMenu[0] = new Fuerza();
		elementosMenu[1] = new EspadaBasica();
		elementosMenu[2] = new VaritaBasica();
		elementosMenu[3] = new EspadaBasica();

		VariablesGlobales.RectanguloColisionJugador = new Rectangle(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y,
				LIMITE_ARRIBA.width + 1, LIMITE_DERECHA.height + 1);
	}

	public void mover(Mapa mapa) {
		posicionX = mapa.getPosicionInicial().getX();
		posicionY = mapa.getPosicionInicial().getY();

		this.mapa = mapa;
	}

	private void gestionarResistencia() {
		if (recuperacion < Constantes.MAX_RECUPERACION) {
			++recuperacion;
		}
		if (recuperacion == Constantes.MAX_RECUPERACION && resistencia < 600) {
			++resistencia;
		}

		if (GestorControles.teclado.shift && resistencia > 0) {
			velocidad = 2;
		} else {
			velocidad = 1;
		}
	}

	private void restarResistencia() {
		if (GestorControles.teclado.shift) {
			if (resistencia > 0) {
				resistencia -= 2;
			} else
				resistencia = 0;
			if (recuperacion > 0) {
				recuperacion -= 2;
			}
		}
	}

	private void gestionarPm() {
		if (lvlP > 0) {
			if (recargaPm < Constantes.MAX_RECARGA_PM) {
				++recargaPm;
			}
			if (recargaPm == Constantes.MAX_RECARGA_PM && pm < Constantes.MAX_VARIABLES) {
				++pm;
			}
		}
	}

	public void realizaAccio(int x) {
		elementoSelecionado = x;
		if (elementosMenu[x] == null) {
			mapa.setError(Constantes.E_OBJECTE_BUIT);
		} else {
			if (elementosMenu[x].getFormaUs() == 1) {
				if (mapa.getEnemicClicat() != null) {
					if (elementosMenu[x].dentroAreaAccion(this, mapa.getEnemicClicat()))
						mapa.getEnemicClicat().setAccion(elementosMenu[x].usar(this));
					else
						mapa.setError(Constantes.E_ENEMIGO_FUERA_ALCANZE);
				} else {
					mapa.setError(Constantes.E_CAP_ENEMIC_SELECCIONAT);
				}
			}
		}
	}

	public void actualizar() {
		actualizarElementosMenu();
		gestionarAccionesPendientes();
		gestionarResistencia();
		gestionarPm();
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();
	}

	private void actualizarElementosMenu() {
		for (int x = 0; x < 10; ++x) {
			if (elementosMenu[x] != null)
				elementosMenu[x].actualizar();
		}
	}

	private void gestionarAccionesPendientes() {
		ArrayList<Accio> accions = mapa.getAccionsPendents();

		for (int i = 0; i < accions.size(); ++i) {
			if (accions.get(i).getType().equals("ATAC")) {
				Atac atac = (Atac) accions.get(i);
				atac.getHurt(this);
			}
		}
		mapa.clearAccionsPendents();
	}

	private void cambiarAnimacionEstado() {
		// Múltiples de 4 (com més petit, més ràpid: mínim 4)
		final int velocitatAnimacio = 40;

		if (animacion < velocitatAnimacio) {
			++animacion;
		} else {
			animacion = 0;
		}

		if (animacion < velocitatAnimacio / 4) {
			estado = 1;
		} else if (animacion < velocitatAnimacio / 2) {
			estado = 0;
		} else if (animacion < velocitatAnimacio * 3 / 4) {
			estado = 2;
		} else {
			estado = 0;
		}
	}

	private void determinarDireccion() {
		final int velocidadX = evaluarVelocidadX();
		final int velocidadY = evaluarVelocidadY();

		if (velocidadX == 0 && velocidadY == 0) {
			return;
		}

		// Trenca el moviment diagonal!
		if ((velocidadX != 0 && velocidadY == 0) || (velocidadX == 0 && velocidadY != 0)) {
			mover(velocidadX, velocidadY);
		} else {
			// Izquierda y arriba
			if (velocidadX == -1 && velocidadY == -1) {
				if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.arriba
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// Izquierda y abajo
			if (velocidadX == -1 && velocidadY == 1) {
				if (GestorControles.teclado.izquierda.getUltimaPulsacion() > GestorControles.teclado.abajo
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// Derecha y arriba
			if (velocidadX == 1 && velocidadY == -1) {
				if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.arriba
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
			// Derecha y abajo
			if (velocidadX == 1 && velocidadY == 1) {
				if (GestorControles.teclado.derecha.getUltimaPulsacion() > GestorControles.teclado.abajo
						.getUltimaPulsacion()) {
					mover(velocidadX, 0);
				} else {
					mover(0, velocidadY);
				}
			}
		}

	}

	private void mover(final int velocidadX, final int velocidadY) {
		enMovimiento = true;

		cambiarDireccion(velocidadX, velocidadY);

		if (!fueraMapa(velocidadX, velocidadY)) {
			if (velocidadX == -1 && !enColisionIzquierda(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarResistencia();
				return;
			}
			if (velocidadX == 1 && !enColisionDerecha(velocidadX)) {
				posicionX += velocidadX * velocidad;
				restarResistencia();
				return;
			}
			if (velocidadY == -1 && !enColisionArriba(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarResistencia();
				return;
			}
			if (velocidadY == 1 && !enColisionAbajo(velocidadY)) {
				posicionY += velocidadY * velocidad;
				restarResistencia();
				return;
			}
		}
	}

	private boolean enColisionArriba(int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); ++r) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * velocidad + 3 * velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_ARRIBA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionAbajo(int velocidadY) {
		for (int r = 0; r < mapa.areasColision.size(); ++r) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x;
			int origenY = area.y + velocidadY * velocidad - 3 * velocidad;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_ABAJO.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionIzquierda(int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); ++r) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * velocidad + 3 * velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_IZQUIERDA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean enColisionDerecha(int velocidadX) {
		for (int r = 0; r < mapa.areasColision.size(); ++r) {
			final Rectangle area = mapa.areasColision.get(r);

			int origenX = area.x + velocidadX * velocidad - 3 * velocidad;
			int origenY = area.y;

			final Rectangle areaFutura = new Rectangle(origenX, origenY, area.width, area.height);

			if (LIMITE_DERECHA.intersects(areaFutura)) {
				return true;
			}
		}
		return false;
	}

	private boolean fueraMapa(final int velocidadX, final int velocidadY) {
		int posicionFuturaX = (int) posicionX + velocidadX * velocidad;
		int posicionFuturaY = (int) posicionY + velocidadY * velocidad;

		final Rectangle bordesMapa = mapa.getBordes(posicionFuturaX, posicionFuturaY, ANCHO_COLISION, ALTO_COLISION);
		
		final boolean fuera;

		if (LIMITE_ARRIBA.intersects(bordesMapa) || LIMITE_ABAJO.intersects(bordesMapa)
				|| LIMITE_IZQUIERDA.intersects(bordesMapa) || LIMITE_DERECHA.intersects(bordesMapa)) {
			fuera = false;
		} else {
			fuera = true;
		}
		
		return fuera;
	}

	private void cambiarDireccion(int velocidadX, int velocidadY) {
		if (velocidadX == 1) {
			direccion = 2;
		} else if (velocidadX == -1) {
			direccion = 1;
		}

		if (velocidadY == 1) {
			direccion = 0;
		} else if (velocidadY == -1) {
			direccion = 3;
		}
	}

	private int evaluarVelocidadX() {
		int velocidadX = 0;
		if (GestorControles.teclado.izquierda.estaPulsada() && !GestorControles.teclado.derecha.estaPulsada()) {
			velocidadX = -1;
		} else if (GestorControles.teclado.derecha.estaPulsada() && !GestorControles.teclado.izquierda.estaPulsada()) {
			velocidadX = 1;
		}
		return velocidadX;
	}

	private int evaluarVelocidadY() {
		int velocidadY = 0;
		if (GestorControles.teclado.arriba.estaPulsada() && !GestorControles.teclado.abajo.estaPulsada()) {
			velocidadY = -1;
		} else if (GestorControles.teclado.abajo.estaPulsada() && !GestorControles.teclado.arriba.estaPulsada()) {
			velocidadY = 1;
		}
		return velocidadY;
	}

	private void animar() {
		if (!enMovimiento) {
			estado = 0;
			animacion = 0;
		}

		imagenActual = hs.getSprite(estado, direccion).getImagen();
	}

	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_JUEGO / 2 - Constantes.LADO_SPRITE / 2;
		
		DrawerClass.dibujarImagen(g, imagenActual, centroX, centroY);

		if (VariablesGlobales.debug2)
			dibuixaRectanglesColisio(g);
	}

	private void dibuixaRectanglesColisio(Graphics g) {
		DrawerClass.dibujarRectanguloContorno(g, new Rectangle(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y,
				LIMITE_ARRIBA.width + 1, LIMITE_DERECHA.height + 1), Color.MAGENTA);

		// Dibuixa area d'atac
		DrawerClass.dibujarRectanguloContorno(g, this.getAreaAtaqueReal(), Color.BLUE);
	}
	
	public Rectangle getAreaAtaque() {
		return areaAtaque;
	}

	public Rectangle getAreaAtaqueReal() {
		if (elementosMenu[elementoSelecionado] != null)
			return elementosMenu[elementoSelecionado].getAreaAtaque(this);
		else
			return new Rectangle();
	}

	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}

	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public void setVelocitat(int velocidad) {
		this.velocidad = velocidad;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public double getVelocitat() {
		return velocidad;
	}

	public Objeto[] getElementosMenu() {
		return elementosMenu;
	}

	public void setElementosMenu(Objeto[] elementosMenu) {
		this.elementosMenu = elementosMenu;
	}

	public int getResistencia() {
		return resistencia;
	}

	public int getRecuperacion() {
		return recuperacion;
	}

	public int getExpericencia() {
		return expericencia;
	}

	public Rectangle getLIMITE_ARRIBA() {
		return LIMITE_ARRIBA;
	}

	public Rectangle getLIMITE_ABAJO() {
		return LIMITE_ABAJO;
	}

	public Rectangle getLIMITE_IZQUIERDA() {
		return LIMITE_IZQUIERDA;
	}

	public Rectangle getLIMITE_DERECHA() {
		return LIMITE_DERECHA;
	}
}
