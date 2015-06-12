package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.control.GestorControles;
import principal.mapas.Mapa;
import principal.sprites.HojaSprites;

public class Jugador {
	private double posicionX;
	private double posicionY;

	private int velocidad = 1;

	// La direccio és per la fulla d'sprites:
	// 0 -> abaix
	// 1 -> esquerra
	// 2 -> dreta
	// 3 -> dalt
	private int direccion;

	private boolean enMovimiento;
	private int animacion;
	private int estado;

	private HojaSprites hs;
	private BufferedImage imagenActual;

	private Mapa mapa;
	
	private final int ANCHO_COLISION = 18;
	private final int ALTO_COLISION = 15;

	private final Rectangle LIMITE_ARRIBA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, ANCHO_COLISION, 1);
	private final Rectangle LIMITE_ABAJO = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION * 2, ANCHO_COLISION, 1);
	private final Rectangle LIMITE_IZQUIERDA = new Rectangle(Constantes.CENTRO_VENTANA_X - ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, 1, ALTO_COLISION);
	private final Rectangle LIMITE_DERECHA = new Rectangle(Constantes.CENTRO_VENTANA_X + ANCHO_COLISION / 2,
			Constantes.CENTRO_VENTANA_Y + ALTO_COLISION, 1, ALTO_COLISION);

	public Jugador(double x, double y, Mapa mapa) {
		posicionX = x;
		posicionY = y;

		direccion = 0;
		animacion = 0;
		estado = 0;

		enMovimiento = false;
		this.mapa = mapa;
		
		hs = new HojaSprites(Constantes.RUTA_PERSONAJE, Constantes.ANCHO_PERSONAJE,
				Constantes.ALTO_PERSONAJE, false);
		imagenActual = hs.getSprite(0).getImagen();
	}

	public void actualizar() {
		cambiarAnimacionEstado();
		enMovimiento = false;
		determinarDireccion();
		animar();
	}

	private void cambiarAnimacionEstado() {
		if (animacion < 30) {
			++animacion;
		} else {
			animacion = 0;
		}

		if (animacion < 15) {
			estado = 1;
		} else {
			estado = 2;
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
			posicionX += velocidadX * velocidad;
			posicionY += velocidadY * velocidad;
		}
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
		final int centroX = Constantes.ANCHO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_VENTANA / 2 - Constantes.LADO_SPRITE / 2;
		
		g.drawImage(imagenActual, centroX, centroY, null);

		g.setColor(Color.MAGENTA);
		g.drawRect(LIMITE_ARRIBA.x, LIMITE_ARRIBA.y, LIMITE_ARRIBA.width, LIMITE_ARRIBA.height);
		g.drawRect(LIMITE_ABAJO.x, LIMITE_ABAJO.y, LIMITE_ABAJO.width, LIMITE_ABAJO.height);
		g.drawRect(LIMITE_IZQUIERDA.x, LIMITE_IZQUIERDA.y, LIMITE_IZQUIERDA.width, LIMITE_IZQUIERDA.height);
		g.drawRect(LIMITE_DERECHA.x, LIMITE_DERECHA.y, LIMITE_DERECHA.width, LIMITE_DERECHA.height);
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
}
