package reto2;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 * Hebra que controla la actualizacion de la etiqueta de la hora actual
 */
public class ControlHora implements Runnable {

	/**
	 * Referencia a la etiqueta donde se muestra la hora
	 */
	Cronometro visorReloj;

	/**
	 * Constructor
	 * 
	 * @param visorReloj Etiqueta en la que se muestra la hora
	 */
	public ControlHora(Cronometro visorReloj) {
		this.visorReloj = visorReloj;
	}

	@Override
	public void run() {
		//Actualizar etiqueta del reloj cada 200ms 
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		while (true) {
			Date fechaActual = new Date();
			visorReloj.actualizarHora("Son las " + formato.format(fechaActual));
			try {
				Thread.currentThread().sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
