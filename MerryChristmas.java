//a simple christmas screensaver with adjustable snowflakes and toggleable music

//created by nathan mccloud

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JSlider;


import javax.swing.Timer;

@SuppressWarnings({ "serial", "unused" })
public class MerryChristmas extends JComponent implements ChangeListener, ActionListener {
		//instance variables
		protected Random rand = new Random();
		protected int lines=250;
		protected JSlider slider;
		protected Timer timer;
		protected Timer colorTimer;
		protected JPanel panel;
		protected JButton smile;
		protected JButton music;
		protected JButton plus;
		protected JButton minus;
		protected int size=10;
		//colors
		protected Color red=new Color(255,0,20);
		protected Color green=new Color(0,255,0);
		//counters
		protected int colorCount=1;
		protected int fontCount=0;
		protected int musicCount=1;
		//music
		protected static Clip backgroundMusic;
		protected static AudioInputStream bmAIS;
		protected static FloatControl FC;
		protected static float vol;
	
	@Override
	public void paintComponent(Graphics g){
		//build font
		try {
			Font f=Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/MUSICNET.ttf"));
			g.setFont(f.deriveFont(Font.ITALIC, 300f));} catch (FontFormatException | IOException ex) {
             ex.printStackTrace();
         }
		g.setColor(red);
		
		if(fontCount%2==0){
		g.drawString("merry", 350, 600);
		}
		else if(fontCount%2!=0){
		g.drawString("christmas", 0, 600);
		}

		//snow
		for(int i=0;i<lines;i++){
			g.setColor(Color.WHITE);
		g.fillOval(0+rand.nextInt(getWidth()),50+rand.nextInt(getHeight()),size,size);}
	}
		//gui
		public MerryChristmas(){
		setLayout(new BorderLayout());
		smile=new JButton(":)");
		smile.addActionListener(new buttonPressed());
		music=new JButton("\u266A");
		music.addActionListener(new buttonPressed2());
		slider=new JSlider(0,lines);
		slider.addChangeListener(this);
		add(slider);
		minus=new JButton("--");
		minus.addActionListener(new buttonPressed3());
		add(minus);
		plus=new JButton("+");
		plus.addActionListener(new buttonPressed4());
		add(plus);
		panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(minus);
		panel.add(plus);
		panel.add(slider);
		panel.add(smile);
		panel.add(music);
		panel.setBackground(Color.BLACK);
		add(panel,BorderLayout.NORTH);
		
		//colorTimer
		colorTimer=new Timer(1350, new TimerCallBack2());
		colorTimer.start();		
				
		// timer
		timer=new Timer(100, new TimerCallBack());
		timer.start();}
		
		//TimerCallBack2
		protected class TimerCallBack2 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				fontCount++;
				if(fontCount%2==0){
					red=new Color(255,0,20);
					green=new Color(0,255,0);
					repaint();
				}else{
					red=new Color(0,255,0);
					green=new Color(255,0,20);
					repaint();}
			}
		}
		
		//timer callback
		protected class TimerCallBack implements ActionListener{
			public void actionPerformed(ActionEvent e){
				repaint();
			}
		}
		
		
		
		//snow button method
		public class buttonPressed implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==smile){
					colorCount++;
					if(colorCount%2==0)
					{
					size+=10;
					}
					else
					{
					size=10;
					}
					}
				
				repaint();				
			}
			
		}
		
		//pause music button method
		public class buttonPressed2 implements ActionListener{
			
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==music){
					musicCount++;
					if(musicCount%2==0){
						backgroundMusic.stop();
					}
					else{
						backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
					}
				}
			}
		}
		
		//decrease music volume button method
		public class buttonPressed3 implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==minus){
				try{
					FC.setValue(FC.getValue() - 5.0f);
				}
			catch(Exception E){
				FC.setValue(FC.getMinimum());
					}
				}
			}
		}
		
		//increase music volume button method
		public class buttonPressed4 implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==plus){
				try{
				FC.setValue(FC.getValue() + 5.0f);
			}
			catch(Exception ex){
				FC.setValue(FC.getMaximum());
					}
				}
			}
		}
			
	//canvas
	public static void main(String[] args) {
		//JFrame construction
		JFrame frame = new JFrame("MerryChristmas");
		MerryChristmas canvas = new MerryChristmas();
		frame.add(canvas);
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//background music 
		try{
			bmAIS = AudioSystem.getAudioInputStream(new File("Vince Guaraldi Trio - Christmas Time Is Here (Instrumental).wav"));//music by the Vince Guaraldi Trio. All credit to the creators
			backgroundMusic = AudioSystem.getClip();
	
			backgroundMusic.open(bmAIS);}
		catch(Exception e){System.out.println("Failure to load sound");}
	
		//play sound file
		FC = (FloatControl) backgroundMusic.getControl(FloatControl.Type.MASTER_GAIN);
		FC.setValue(vol);
		backgroundMusic.setFramePosition(0);//plays sound from beginning
		backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);//plays sound looped forever
	}


	@Override
	//slider method
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider){
			lines=slider.getValue();
			String slide=Integer.toString(lines);}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
	}


