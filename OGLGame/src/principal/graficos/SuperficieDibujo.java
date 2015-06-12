package principal.graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import principal.control.GestorControles;
import principal.control.Raton;
import principal.maquinaEstado.GestorEstados;

public class SuperficieDibujo extends Canvas {

	// Atributos
	private static final long serialVersionUID = -6227038142688953660L;

	private int ancho;
	private int alto;

	private Raton raton;

	// Construcor
	public SuperficieDibujo(final int ancho, final int alto) {
		this.ancho = ancho;
		this.alto = alto;

		raton = new Raton();

		setIgnoreRepaint(true);

		// Canvia el cursor (si vull treure, comenta aquesta instrucció)
		setCursor(raton.getCursor());

		setPreferredSize(new Dimension(ancho, alto));
		addKeyListener(GestorControles.teclado);
		setFocusable(true);
		requestFocus();
	}
	
	public void dibujar(final GestorEstados ge) {
		BufferStrategy buffer = getBufferStrategy();

		if (buffer == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, ancho, alto);

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
