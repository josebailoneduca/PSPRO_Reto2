package reto2;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class HebraReloj extends Thread {
	
	JLabel visorReloj;
	
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
