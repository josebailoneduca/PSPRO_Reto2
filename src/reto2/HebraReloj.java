package reto2;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;


/**
 * Hebra que controla la actualizacion de la etiqueta de la hora actual
 */
public class HebraReloj extends Thread {
	
	/**
	 * Referencia a la etiqueta donde se muestra la hora
	 */
	JLabel visorReloj;
	
	
	/**
	 * Constructor
	 * 
	 * @param visorReloj Etiqueta en la que se muestra la hora
	 */
	public HebraReloj(JLabel visorReloj) {
		this.visorReloj=visorReloj;
	}

	@Override
	public void run() {
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm:ss");
		while(true) {
		Date fechaActual = new Date();
		visorReloj.setText("Son las "+formato.format(fechaActual));
		try {
			sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	}
}
