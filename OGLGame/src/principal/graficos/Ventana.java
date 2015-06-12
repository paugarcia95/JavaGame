package principal.graficos;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import principal.herramientas.CargadorRecursos;

public class Ventana extends JFrame {
	private static final long serialVersionUID = 5979421777239930009L;

	private String titulo;
	private final ImageIcon icono;

	public Ventana(final String titulo, final SuperficieDibujo sd) {
		this.titulo = titulo;

		BufferedImage imagen = CargadorRecursos.cargarImagenCompatibleTranslucida("/imagenes/iconos/iconoVentana.png");
		this.icono = new ImageIcon(imagen);

		configurarVentana(sd);
	}

	private void configurarVentana(final SuperficieDibujo sd) {
		setTitle(titulo);
		setIconImage(icono.getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setResizable(false);
		setLayout(new BorderLayout());
		add(sd, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
