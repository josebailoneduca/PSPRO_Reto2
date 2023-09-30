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
  * Contiene un cronometro y la hora actual. El cronometro se puede pausar, continuar o resetar(puesta a cero e inicio del conteo.
  * La propia clase sirve de runable para controlar el cronometro. Se inicia una hebra para el control del cronometro y otra
  * hebra independiente para controlar la actualizacion de la hora actual
  */
public class MainCronometro extends JFrame implements Runnable { 
	
	private static final long serialVersionUID = 1L;
	/**
	 * Etiqueta para el tiempo del cronometro
	 */
	JLabel lbTiempo;
	
	/**
	 * Etiqueta para la hora actual
	 */
	JLabel lbHoraActual;
	
	/**
	 * Panel contenedor de los botones
	 */
	JPanel panelBotones;
	
	/**
	 * Boton de pausa del cronometro
	 */
	JButton btnPause;
	
	/**
	 * Boton de continuacion del cronometro
	 */
	JButton btnResume;
	
	/**
	 * Bton de reinicio del cronometro
	 */
	JButton btnReset;
	
	/**
	 * Hebra de control del cronometro
	 */
    Thread hiloCronometro;
    
    /**
     * Hebra de control de actualizacion de la hora actual
     */
    HebraReloj hebraReloj;
    
    /**
     * Define si el cronometro esta andando
     */
    boolean cronometroActivo;
    
    /**
     * Define si el cronometro esta pausado
     */
	boolean cronometroPausado=false;
	
	/**
	 * Define si hay que reiniciar el cronometro
	 */
	boolean reiniciarCronometro=false;
	
	/**
	 * Constructor 
	 */
    public MainCronometro() {
    	//Configuracion de la ventana JFrame
        setTitle("Cronos");
        setSize( 300, 200 );
        setResizable(false);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );
        
        //etiqueta de tiempo
        lbTiempo = new JLabel( "00:00:000" );
        lbTiempo.setFont( new Font( Font.SERIF, Font.BOLD, 50 ) );
        lbTiempo.setHorizontalAlignment( JLabel.CENTER );
        lbTiempo.setForeground( Color.BLUE );
        lbTiempo.setBackground( Color.WHITE );
        lbTiempo.setOpaque( true );
        
        //etiqueta hora actual
        lbHoraActual = new JLabel( "LA HORA" );
        lbHoraActual.setFont( new Font( Font.SANS_SERIF, Font.BOLD, 20 ) );
        lbHoraActual.setHorizontalAlignment( JLabel.CENTER );
        lbHoraActual.setForeground( Color.RED );
        lbHoraActual.setBackground( Color.WHITE );
        lbHoraActual.setOpaque( true );
        
        //botonera
        panelBotones = new JPanel();
        btnPause = new JButton("Pause");
        btnResume= new JButton("Resume");
        btnReset= new JButton("Reset");
        
        //eventos de botones
        btnPause.addActionListener(eve->{cronometroPausado=true;});
        btnResume.addActionListener(eve->{cronometroPausado=false;});
        btnReset.addActionListener(eve->{reiniciarCronometro=true;});
        
        
        // agregar elementos a interface
        add(lbHoraActual,BorderLayout.NORTH);
        add( lbTiempo, BorderLayout.CENTER );
        add( panelBotones, BorderLayout.SOUTH);
        panelBotones.add(btnPause, BorderLayout.WEST);
        panelBotones.add(btnResume, BorderLayout.CENTER);
        panelBotones.add(btnReset, BorderLayout.EAST);
        
        //centrado en pantalla y visualizacion del JFrame
        this.setLocationRelativeTo( null );
        setVisible( true );
    }
  
    @Override
    public void run(){
    	//inicializacion de las variables numericas del cronometro
        int minutos = 0 , segundos = 0, milesimas = 0;
        //inicializacion de los string del cronometro
        String min="", seg="", mil="";
        try
        {
            
        	//
            while( cronometroActivo )
            {
            	//gestion del reinicio del tiempo a 0
        		if (reiniciarCronometro) {
        			minutos=0;
        			segundos=0;
        			milesimas=0;
        			reiniciarCronometro=false;
        			lbTiempo.setText( "00:00:000" );
        		}
        		
        		//gestiona de la pausa de avance del cronometro
            	while (cronometroPausado) {
            		//gestion de reinicio del tiempo a 0 durante la pausa
            		if (reiniciarCronometro) {
            			minutos=0;
            			segundos=0;
            			milesimas=0;
            			lbTiempo.setText( "00:00:000" );
            			reiniciarCronometro=false;
            		}
            		Thread.sleep( 100 );
            	}
            	
            	//avance normal del cronometro
                Thread.sleep( 4 );
                
                milesimas += 5;
                 
                
                if( milesimas == 1000 )
                {
                    milesimas = 0;
                    segundos += 1;
                    
                    if( segundos == 60 )
                    {
                        segundos = 0;
                        minutos++;
                    }
                }
    
                if( minutos < 10 ) min = "0" + minutos;
                else min = minutos+"";
                if( segundos < 10 ) seg = "0" + segundos;
                else seg = segundos+"";
                 
                if( milesimas < 10 ) mil = "00" + milesimas;
                else if( milesimas < 100 ) mil = "0" + milesimas;
                else mil = milesimas+"";
                 
               
                lbTiempo.setText( min + ":" + seg + ":" + mil );                
            }
        }catch(Exception e){}
        
        lbTiempo.setText( "00:00:000" );
    }
  
     
    /**
     * Pone en marcha el cronometro y el conteo de hora actual
     */
    public void iniciarCronometro() {
    	//define el cronometro como activo
        cronometroActivo = true;
        //hilo de control del cronometro
        hiloCronometro = new Thread( this );
        hiloCronometro.start();
        
        //hilo de control de la hora actual
        hebraReloj = new HebraReloj(lbHoraActual);
        hebraReloj.start();
        
    }
  
    /**
     * Parar completamente el cronometro
     */
    public void pararCronometro(){
        cronometroActivo = false;
    }
  
    public static void main(String[] args) {
        new MainCronometro().iniciarCronometro();
    }

	
    
}



