//a simple christmas screensaver with adjustable snowflakes and toggleable music

//created by nathan mccloud

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
public class ChristmasScreenSaver extends JComponent implements ChangeListener, ActionListener {
		//instance variables
		protected Random rand = new Random();
		protected int lines=250;
		protected JSlider slider;
		protected Timer timer;
		protected Timer colorTimer;
		protected JPanel panel;
		protected JButton smile;
		protected JButton music;
		protected int size=10;
		//colors
		protected Color red2=Color.RED;
		protected Color green2=Color.GREEN;
		//counters
		protected int colorCount=1;
		protected int fontCount=1;
		protected int musicCount=1;
		//sound
		protected static Clip backgroundMusic;
		protected static AudioInputStream bmAIS;
		

	
	@Override
	public void paintComponent(Graphics g){
		//build font
		try {
			Font f=Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/I Love Christmas.ttf"));//font created by Misti's Fonts @http://www.dafont.com/i-love-christmas.font
			g.setFont(f.deriveFont(Font.ITALIC, 500f));} catch (FontFormatException | IOException ex) {
             ex.printStackTrace();
         }
		g.setColor(red2);
		g.drawString("M", 200, 550);
		g.setColor(green2);
		g.drawString("E", 435, 550);
		g.setColor(red2);
		g.drawString("R", 535, 550);
		g.setColor(green2);
		g.drawString("R", 640, 550);
		g.setColor(red2);
		g.drawString("Y", 755, 550);
		g.setColor(green2);
		g.drawString("*X", 890, 550);
		g.setColor(red2);
		g.drawString("M", 1195, 550);
		g.setColor(green2);
		g.drawString("A", 1370, 550);
		g.setColor(red2);
		g.drawString("S", 1470, 550);
		//snow
		for(int i=0;i<lines;i++)
		{
		g.setColor(Color.WHITE);
		g.fillOval(0+rand.nextInt(getWidth()),50+rand.nextInt(getHeight()),size,size);
		}
	}
		//gui
		public ChristmasScreenSaver(){
		setLayout(new BorderLayout());
		smile=new JButton(":)");
		smile.addActionListener(new buttonPressed());
		music=new JButton("\u266A");
		music.addActionListener(new buttonPressed2());
		slider=new JSlider(0,lines);
		slider.addChangeListener(this);
		add(slider);
		panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(slider);
		panel.add(smile);
		panel.add(music);
		panel.setBackground(Color.BLACK);
		add(panel,BorderLayout.NORTH);
		
		//colorTimer
		colorTimer=new Timer(200, new TimerCallBack2());
		colorTimer.start();		
				
		// timer
		timer=new Timer(175, new TimerCallBack());
		timer.start();}
		
		//TimerCallBack2
		protected class TimerCallBack2 implements ActionListener{
			public void actionPerformed(ActionEvent e){
				
				fontCount++;

				if(fontCount%2==0){
					red2=Color.RED;
					green2=Color.GREEN;
					repaint();
				}else{
					red2=Color.GREEN;
					green2=Color.RED;
					repaint();}
			}
		}
		
		//timer callback
		protected class TimerCallBack implements ActionListener{
			public void actionPerformed(ActionEvent e){
				repaint();
			}
		}
		
		
		
		//button method
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
		
		//music button method
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
		
			
	//canvas
	public static void main(String[] args) {
		//JFrame construction
		JFrame frame = new JFrame("ChristmasScreenSaver");
		ChristmasScreenSaver canvas = new ChristmasScreenSaver();
		frame.add(canvas);
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//construction of sound
		try{
			bmAIS = AudioSystem.getAudioInputStream(new File("yousuckcharlie.wav"));//music by George Miller and the Vince Guaraldi Trio. All credit to the original creators.
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(bmAIS);}
		catch(Exception e){System.out.println("Failure to load sound");}
	
		//play sound file
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


