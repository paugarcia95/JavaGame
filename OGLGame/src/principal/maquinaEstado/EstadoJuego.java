package principal.maquinaEstado;

import java.awt.Graphics;

public interface EstadoJuego {
	//Tots els mètodes són public i abstract
	
	void actualizar();
	void dibujar(final Graphics g);
}
