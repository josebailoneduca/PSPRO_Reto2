package reto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Clase main del reto 2
 * 
 * Contiene un cronometro y la hora actual. El cronometro se puede pausar, 
 * continuar o resetar(puesta a cero e inicio del conteo). La propia clase sirve 
 * de runable para controlar el cronometro. Se inicia una hebra para el control 
 * del cronometro y otra hebra independiente para controlar la actualizacion de 
 * la hora actual
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Cronometro extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	
	//ELEMENTOS GUI
	/**
	 * Etiqueta para el tiempo del cronometro
	 */
	private JLabel lbTiempo;

	/**
	 * Etiqueta para la hora actual
	 */
	private JLabel lbHoraActual;

	/**
	 * Panel contenedor de los botones
	 */
	private JPanel panelBotones;

	/**
	 * Boton de pausa del cronometro
	 */
	private JButton btnPause;

	/**
	 * Boton de continuacion del cronometro
	 */
	private JButton btnResume;

	/**
	 * Bton de reinicio del cronometro
	 */
	private JButton btnReset;

	
	
	//VARIABLES DE CONTROL DEL CRONOMETRO
	/**
	 * Define si el cronometro esta pausado
	 */
	private boolean cronometroPausado = false;

	/**
	 * minutos del cronometro
	 */
	private int minutos = 0;
	/**
	 * segundos del cronometro
	 */
	private int segundos = 0;
	/*
	 * milesimas del cronometro
	 */
	private int milesimas = 0;

	/**
	 * Constructor
	 */
	public Cronometro() {
		// Configuracion de la ventana JFrame
		setTitle("Cronos");
		setSize(300, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// etiqueta de tiempo
		lbTiempo = new JLabel("00:00:000");
		lbTiempo.setFont(new Font(Font.SERIF, Font.BOLD, 50));
		lbTiempo.setHorizontalAlignment(JLabel.CENTER);
		lbTiempo.setForeground(Color.BLUE);
		lbTiempo.setBackground(Color.WHITE);
		lbTiempo.setOpaque(true);

		// etiqueta hora actual
		lbHoraActual = new JLabel("LA HORA");
		lbHoraActual.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		lbHoraActual.setHorizontalAlignment(JLabel.CENTER);
		lbHoraActual.setForeground(Color.RED);
		lbHoraActual.setBackground(Color.WHITE);
		lbHoraActual.setOpaque(true);

		// botonera
		panelBotones = new JPanel();
		btnPause = new JButton("Pause");
		btnResume = new JButton("Play/Resume");
		btnReset = new JButton("Reset");

		// eventos de botones
		btnResume.addActionListener(eve -> {
			cronometroPausado = false;
		});
		btnPause.addActionListener(eve -> {
			cronometroPausado = true;
		});
		btnReset.addActionListener(eve -> {
			this.reiniciarCronometro();
		});

		// agregar elementos a interface
		add(lbHoraActual, BorderLayout.NORTH);
		add(lbTiempo, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);
		panelBotones.add(btnResume, BorderLayout.CENTER);
		panelBotones.add(btnPause, BorderLayout.WEST);
		panelBotones.add(btnReset, BorderLayout.EAST);

		// centrado en pantalla y visualizacion del JFrame
		this.setLocationRelativeTo(null);
		setVisible(true);

		// iniciar hilos
		this.iniciarHilos();
	}

	
	/**
	 * Actualiza la hora a mostrar
	 * 
	 * @param msg El texto a poner
	 */
	public void actualizarHora(String msg) {
		this.lbHoraActual.setText(msg);
	}

	@Override
	public void run() {
		// inicializacion de las variables numericas del cronometro

		// inicializacion de los string del cronometro
		String min = "", seg = "", mil = "";
		try {

			while (true) {

				// gestiona de la pausa del cronometro
				while (cronometroPausado) {
					Thread.sleep(10);
				}

				// avance normal del cronometro
				Thread.sleep(4);

				// aumentar milesimas
				milesimas += 5;

				// calculo de tiempo a minutos segundos milesimas
				if (milesimas == 1000) {
					milesimas = 0;
					segundos += 1;

					if (segundos == 60) {
						segundos = 0;
						minutos++;
					}
				}

				if (minutos < 10)
					min = "0" + minutos;
				else
					min = minutos + "";
				if (segundos < 10)
					seg = "0" + segundos;
				else
					seg = segundos + "";

				if (milesimas < 10)
					mil = "00" + milesimas;
				else if (milesimas < 100)
					mil = "0" + milesimas;
				else
					mil = milesimas + "";

				// actualiza etiqueta del cronometro con el nuevo tiempo calculado
				lbTiempo.setText(min + ":" + seg + ":" + mil);
			}
		} catch (Exception e) {
		}

	}

	
	/**
	 * Pone el cronometro a 0
	 */
	private void reiniciarCronometro() {
		minutos = 0;
		segundos = 0;
		milesimas = 0;
		lbTiempo.setText("00:00:000");
	}
	/**
	 * Pone en marcha el cronometro y el conteo de hora actual
	 */
	private void iniciarHilos() {

		// hilo de control del cronometro
		Thread hiloCronometro = new Thread(this);
		hiloCronometro.start();

		// hilo de control de la hora actual
		Thread hebraReloj = new Thread(new ControlHora(this));
		hebraReloj.start();
	}

	public static void main(String[] args) {
		new Cronometro();
	}

}
