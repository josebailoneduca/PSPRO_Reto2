package reto2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

 
public class MainCronometro extends JFrame implements Runnable { 
	
	private static final long serialVersionUID = 1L;
	JLabel lbTiempo;
	JLabel lbHoraActual;
	JPanel panelBotones;
	JButton btnPause;
	JButton btnResume;
	JButton btnReset;
    Thread hiloCronometro;
    HebraReloj hebraReloj;
    
    boolean cronometroActivo;
	boolean cronometroPausado=false;
	boolean reiniciarCronometro=false;
    public MainCronometro() {
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
        
        //eventos de botnoes
        btnPause.addActionListener(e->{cronometroPausado=true;});
        btnResume.addActionListener(e->{cronometroPausado=false;});
        btnReset.addActionListener(e->{cronometroPausado=true; reiniciarCronometro=true;});
        
        
        // agregar elementos a interface
        add(lbHoraActual,BorderLayout.NORTH);
        add( lbTiempo, BorderLayout.CENTER );
        add( panelBotones, BorderLayout.SOUTH);
        panelBotones.add(btnPause, BorderLayout.WEST);
        panelBotones.add(btnResume, BorderLayout.CENTER);
        panelBotones.add(btnReset, BorderLayout.EAST);
          
        this.setLocationRelativeTo( null );
        setVisible( true );
    }
  
    public void run(){
        int minutos = 0 , segundos = 0, milesimas = 0;
        
        String min="", seg="", mil="";
        try
        {
            
            while( cronometroActivo )
            {
            	while (cronometroPausado) {
            		if (reiniciarCronometro) {
            			minutos=0;
            			segundos=0;
            			milesimas=0;
            			reiniciarCronometro=false;
            			cronometroPausado=false;
            		}
            		Thread.sleep( 1 );
            	}
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
  
      
    public void iniciarCronometro() {
        cronometroActivo = true;
        hiloCronometro = new Thread( this );
        hiloCronometro.start();
        hebraReloj = new HebraReloj(lbHoraActual);
        hebraReloj.start();
        
    }
  
    
    public void pararCronometro(){
        cronometroActivo = false;
    }
  
    public static void main(String[] args) {
        new MainCronometro().iniciarCronometro();
    }

	
    
}



