package principal.entes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import principal.Constantes;
import principal.control.GestorControles;
import principal.sprites.HojaSprites;

public class Jugador {
	private double posicionX;
	private double posicionY;

	private double velocitat;

	// La direccio és per la fulla d'sprites:
	// 0 -> abaix
	// 1 -> esquerra
	// 2 -> dreta
	// 3 -> dalt
	private int direccion;

	private int estadoAnimacion;
	private boolean enMovimiento;

	private HojaSprites hs;
	private BufferedImage imagenActual;

	public Jugador(double x, double y) {
		posicionX = x;
		posicionY = y;

		velocitat = 1;
		direccion = 0;

		estadoAnimacion = 0;
		enMovimiento = false;
		
		hs = new HojaSprites("/imagenes/hojasPersonajes/jugador1.png", Constantes.ANCHO_PERSONAJE,
				Constantes.ALTO_PERSONAJE, false);
		imagenActual = hs.getSprite(0).getImagen();
	}

	public void actualizar() {

		if (GestorControles.teclado.pulsadoArriba()) {
			direccion = 3;
			posicionY = posicionY - velocitat;
			enMovimiento = true;
		}

		if (GestorControles.teclado.pulsadoAbajo()) {
			direccion = 0;
			posicionY = posicionY + velocitat;
			enMovimiento = true;
		}

		if (GestorControles.teclado.pulsadoIzquierda()) {
			direccion = 1;
			posicionX = posicionX - velocitat;
			enMovimiento = true;
		}

		if (GestorControles.teclado.pulsadoDerecha()) {
			direccion = 2;
			posicionX = posicionX + velocitat;
			enMovimiento = true;
		}

		animar(direccion);

		enMovimiento = false;
	}

	private void animar(int direccion) {
		int frequenciaAnimacion = 10;
		int limiteEstados = 4;

		if (enMovimiento) {
			if (Constantes.APS % frequenciaAnimacion == 0) {

				++estadoAnimacion;

				if (estadoAnimacion >= limiteEstados) {
					estadoAnimacion = 0;
				}

				switch (estadoAnimacion) {
					case 0 :
						imagenActual = hs.getSprite(1, direccion).getImagen();
						break;
					case 1 :
						imagenActual = hs.getSprite(0, direccion).getImagen();
						break;
					case 2 :
						imagenActual = hs.getSprite(2, direccion).getImagen();
						break;
					case 3 :
						imagenActual = hs.getSprite(0, direccion).getImagen();
				}
			}
		} else {
			imagenActual = hs.getSprite(0, direccion).getImagen();
		}
	}

	public void dibujar(Graphics g) {
		final int centroX = Constantes.ANCHO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;
		final int centroY = Constantes.ALTO_PANTALLA / 2 - Constantes.LADO_SPRITE / 2;
		
		g.drawImage(imagenActual, centroX, centroY, null);

		g.setColor(Color.MAGENTA);
		g.drawRect(centroX, centroY, Constantes.ANCHO_PERSONAJE, Constantes.ALTO_PERSONAJE);
	}

	public void setPosicionX(double posicionX) {
		this.posicionX = posicionX;
	}

	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public void setVelocitat(double velocitat) {
		this.velocitat = velocitat;
	}

	public double getPosicionX() {
		return posicionX;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public double getVelocitat() {
		return velocitat;
	}
}
