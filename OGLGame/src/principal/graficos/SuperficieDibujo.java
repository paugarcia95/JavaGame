package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.Constantes;
import principal.control.GestorControles;
import principal.control.Raton;
import principal.herramientas.DrawerClass;
import principal.maquinaEstado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	// Atributos
	private static final long serialVersionUID = -6227038142688953660L;

	private int ancho;
	private int alto;

	public static Raton raton;

	// Construcor
	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		raton = new Raton(this);

		setIgnoreRepaint(true);

		// Canvia el cursor (si vull treure, comenta aquesta instrucció)
		setCursor(raton.getCursor());

		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		setFocusable(true);
		requestFocus();
	}
	
	public void actualizar() {
		raton.actualizar(this);
	}


	public void dibujar(final GestorEstados ge) {
		BufferStrategy buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(4);
			return;
		}

		Graphics2D g = (Graphics2D) buffer.getDrawGraphics();
		g.setFont(Constantes.FUENTE_PRINCIPAL);

		// Esborro el que hi ha a pantalla
		DrawerClass.dibujarRectanguloRelleno(g, 0, 0, Constantes.ANCHO_PANTALLA_COMPLETA,
				Constantes.ALTO_PANTALLA_COMPLETA, Color.black);

		if (Constantes.FACTOR_ESCALADO_X != 1.0 || Constantes.FACTOR_ESCALADO_Y != 1.0) {
			g.scale(Constantes.FACTOR_ESCALADO_X, Constantes.FACTOR_ESCALADO_Y);
		}
		
		ge.dibujar(g);

		// Serveix perqe pinti les imatges sincronitzades amb la teva pantalla
		Toolkit.getDefaultToolkit().sync();

		g.dispose(); // Ja puc esborrar el que hi ha a g
		buffer.show();
	}

	public int getAlto() {
		return alto;
	}

	public int getAncho() {
		return ancho;
	}
}
