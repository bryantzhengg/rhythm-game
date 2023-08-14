//Name:  Bryant Zheng
//Final Culminating Project
// Due Date: January 27, 2020

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class CulminatingProjectBryantZheng extends JPanel implements Runnable, KeyListener, ActionListener {
	
	//Rectangle arrays for the the white squares falling down on the corresponding lines
	Rectangle[] pieceOnRed = new Rectangle [57];
	Rectangle[] pieceOnYellow = new Rectangle [30];
	Rectangle[] pieceOnGreen = new Rectangle [51];
	Rectangle[] pieceOnBlue = new Rectangle [53];
	
	//Rectangle arrays for the coloured lines on the background
	Rectangle[] backHighlights = new Rectangle[4];
	Rectangle[] targets = new Rectangle[4];
	
	int screenWidth = 1000;
	int screenHeight = 650;
	Thread thread;
	int FPS = 60;
	static int score = 0;
	
	Color darkRed = new Color(150, 0, 0);
	Color darkYellow = new Color (200, 150, 12);
	Color darkGreen = new Color (30, 150, 30);
	Color darkBlue = new Color (0, 0, 130);
	Color darkCyan = new Color (0, 51, 56);
	
	static boolean titleScreen = true;
	static boolean instructionsPage = false;
	static boolean aboutPage = false;
	static boolean backBoolean = false;
	
	//Buttons on the main menu and to go back
	static JButton start, instructions, about, back;
	
	JFrame frame;
	JPanel myPanel;
	
	Timer timer;
	int time;
	
	//Constructor
	public CulminatingProjectBryantZheng () {
		
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setVisible(true);

		thread = new Thread(this);
		thread.start();
		
		//Setting action commands to the JButtons
		start = new JButton("Start");
			start.setActionCommand("start");
			start.addActionListener(this);
			start.setBounds(200, 300, 200, 50);
					
		instructions = new JButton ("Instructions");
			instructions.setActionCommand("instructions");
			instructions.addActionListener(this);
			instructions.setBounds(400, 300, 200, 50);
			
		about = new JButton ("About");
			about.setActionCommand("about");
			about.addActionListener(this);
			about.setBounds(600, 300, 200, 50);
			
		back = new JButton ("Back");
			back.setActionCommand("back");
			back.addActionListener(this);
			back.setBounds(10, 10, 80, 50);
			
		//Setting the buttons to not be the focus; otherwise, the keylistener would not work
		start.setFocusable(false);
		instructions.setFocusable(false);
		about.setFocusable(false);
		back.setFocusable(false);
		back.setVisible(false);
			
		time = 0;
		timer = new Timer (500, new TimerEventHandler ());
	}
	
	//Private class for the timer
	private class TimerEventHandler implements ActionListener{
		
		public void actionPerformed (ActionEvent event){
			
				time++;

		}
	}
	
	//This method takes the action given by the JButton and does something with it. The parameter is an actionevent event. It returns void.
	public void actionPerformed (ActionEvent event) {
		
		String nameEvent = event.getActionCommand();
		
		//When the start JButton is pressed, the timer starts, the song starts, and the JButtons are not visible.
		if (nameEvent.equals("start")) {
			
			timer.start();
			playSound ("bestroot.wav");
			
			titleScreen = false;
			
			start.setVisible(false);
			instructions.setVisible(false);
			about.setVisible(false);
		}
		
		//Opens the instructions
		 if (nameEvent.equals("instructions")) {
			instructionsPage = true;
			back.setVisible(true);
			
			start.setVisible(false);
			instructions.setVisible(false);
			about.setVisible(false);
		}
		
		 //Opens an about page
		 if (nameEvent.equals("about")) {
				aboutPage = true;
				back.setVisible(true);
				
				start.setVisible(false);
				instructions.setVisible(false);
				about.setVisible(false);
			}
		 
		 //Bring the user back to the main menu
		 if (nameEvent.equals("back")) {
			 
			 	instructionsPage = false;
			 	aboutPage = false;
			 	back.setVisible(false);
				
				start.setVisible(true);
				instructions.setVisible(true);
				about.setVisible(true);
				
				time = 0;
				timer.stop();
				
				if(time == 0) {
					score = 0;
					initialize();
					
				}
			}
	}

	@Override
	public void run() {
		initialize();
		while(true) {
			update();
			this.repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	//This method actually creates the rectangles for the coloured lines, the targets, and the pieces that will be falling down. There are no parameters and will return void.
	public void initialize() {
		backHighlights [0] = new Rectangle (310, 0, 80, 650);
		backHighlights [1] = new Rectangle (410, 0, 80, 650);
		backHighlights [2] = new Rectangle (510, 0, 80, 650);
		backHighlights [3] = new Rectangle (610, 0, 80, 650);
		
		targets [0] = new Rectangle (305, 550, 90, 20);
		targets [1] = new Rectangle (405, 550, 90, 20);
		targets [2] = new Rectangle (505, 550, 90, 20);
		targets [3] = new Rectangle (605, 550, 90, 20);
		
		int[] redY = {-6660, -6740, -6920, -7020, -7120, -7450, -7530, -7710, -7790, -7880, -8190, -8270, -8470, -8570, -8670, -8980, -9060, -9260, -9360, -9460, -9770, -9850,
				-10050, -10150, -10250, -10560, -10640, -10820, -10920, -11020, -11330, -11410, -11590, -11690, -11790, -12100, -12180, -12380, -12480, -12580,
				 -12710, -12870, -13360, -13520, -13980, -14280, -14410, -15050, -15210, -15290, -15570, -15900, -16460, -16660, -16870, -17030, -17150};

			for (int i = 0; i < 57; i++) {
				pieceOnRed[i] = new Rectangle(330, redY[i], 40, 40);
	
			}
		
		int[] yellowY = {-6820, -7200, -7590, -7960, -8340, -8720, -9180, -9530, -9920, -10310, -10690, -11070, -11460, -11850, -12270,
				-12790, -13280, -13440, -13620, -13700, -14080, -14330, -14970, -15130, -15490, -15840, -15970,
				-16560, -16760, -16970, -17090, -17350};

			for (int i = 0; i < 30; i++) {
				pieceOnYellow[i] = new Rectangle(430, yellowY[i], 40, 40);
	
			}
		
		int[] greenY = {-100, -300, -720, -900, -1150, -1470, -1750, -2040, -2440, -2650, -2850, -3030, -3310, -3850, -4130, -4600, -4950, -5550, -5820, -6200, -6350, -6820, 
				-7590, -8340, -9180, -9920, -10690, -10690, -11460, -12270, -12710, -12870, -13360, -13520, -13980, -14280, -14410, -15050, -15210, -15290, -15570, -15900,
				-16460, -16660, -16870, -17030, -17150, -17450, -18050, -18250, -18630};
	
			for (int i = 0; i < 51; i++) {
				pieceOnGreen[i] = new Rectangle(530, greenY[i], 40, 40);
			}
		
		int[] blueY = {-40, -200, -610, -810, -990, -1360, -1690, -1900, -2240, -2530, -2730, -2930, -3220, -3400, -3940, -4030, -4230, -4750, -4850, -5050, -5650, -6100, -6460,
				-7200, -7960, -8720, -9530, -10310, -10310, -11070, -11850, -12790, -13280, -13440, -13620, -13700, -14080, -14330, -14970, -15130, -15490, -15840, -15970,
				-16560, -16760, -16970, -17090, -17350, -17550, -18150, -18350, -18430, -18730};
		
			for (int i = 0; i < 53; i++) {
				pieceOnBlue[i] = new Rectangle(630, blueY[i], 40, 40);
				
			}
	}

	public void update() {
		moveAndCheck();

	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//Drawing the snorlax in the background.
		Image snorlax = Toolkit.getDefaultToolkit ().getImage ("snorlax.png");
		g.drawImage(snorlax, 0, 0, 1000, 650, this);
		
		//Drawing the coloured lines
		g2.setColor(darkRed);
			g2.fill(backHighlights[0]);
		g2.setColor(darkYellow);
			g2.fill(backHighlights[1]);
		g2.setColor(darkGreen);
			g2.fill(backHighlights[2]);
		g2.setColor(darkBlue);
			g2.fill(backHighlights[3]);
		
		//Drawing the targets.
		g2.setColor(Color.WHITE);
		for(int i = 0; i < targets.length; i++) {
			g2.fill(targets[i]);}
		
		//Drawing all of the falling pieces.
		g2.setColor(Color.WHITE);
		for (int i = 0; i < 57; i++) {
			if (pieceOnRed[i].getY() > -30 && pieceOnRed[i].getY() < 750) {
				g2.fill(pieceOnRed[i]);}
		}
		
		for (int i = 0; i < 30; i++) {
			if (pieceOnYellow[i].getY() > -30 && pieceOnYellow[i].getY() < 750) {
				g2.fill(pieceOnYellow[i]);}
		}
		
		for (int i = 0; i < 51; i++) {
			if (pieceOnGreen[i].getY() > -30 && pieceOnGreen[i].getY() < 750) {
				g2.fill(pieceOnGreen[i]);}
		}
		
		for (int i = 0; i < 53; i++) {
			if (pieceOnBlue[i].getY() > -30 && pieceOnBlue[i].getY() < 750) {
				g2.fill(pieceOnBlue[i]);
				}
		}
		
		//Drawing the score on the top left
		g2.setColor(darkCyan);
		String scoreString = "x" + score * 10;
		g.setFont(new Font("Dialog", Font.BOLD, 60)); 
		g.drawString (scoreString, 30, 80);
		
		//Drawing the song name and creator of the remix
		g2.setColor(darkCyan);
		String song = "Pokemon R/S/E Littleroot Town Remix";
		g.setFont(new Font("Dialog", Font.BOLD, 15)); 
		g.drawString (song, 30, 110);
		g2.setColor(darkCyan);
		String creator = " By VanilluxePavillion";
		g.setFont(new Font("Dialog", Font.BOLD, 10)); 
		g.drawString (creator, 27, 130);

		//Drawing the titlescreen
		if (titleScreen == true) {
			g2.setColor(Color.PINK);
			g2.fillRect(0, 0, screenWidth, screenHeight);
				
				String title = "really good rhythm game.";
				g2.setColor(Color.WHITE);
				g.setFont(new Font("Dialog", Font.BOLD, 70)); 
				g.drawString (title, 80, 200);
		}
		
		//Drawing the instruction page
		if (instructionsPage == true) {
			g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, screenWidth, screenHeight);
				
				String instructions1 = "For this rhythm game, white squares will be falling from the top of the screen on four coloured lines."; 
				String instructions2 = "You will be trying to press a specific key on your keyboard at the same time the pieces reach the"; 
				String instructions3 = "white targets at the bottom. Press the F-key for the pieces on the red line, the G-key for the pieces ";
				String instructions4 = "on the yellow line, the H-key for the pieces on the green line, and the J-key for the pieces on the blue line.";
				
				String instructions5 = "You will earn points for hitting the pieces on time and lose points for missing pieces and pressing the keys ";
				String instructions6 = "at the incorrect times. The game gets progressively more difficult.";
				
				g2.setColor(Color.BLACK);
				g.setFont(new Font("Dialog", Font.PLAIN, 20));
				
				g.drawString (instructions1, 10, 100);
				g.drawString (instructions2, 10, 125);
				g.drawString (instructions3, 10, 150);
				g.drawString (instructions4, 10, 175);
				
				g.drawString (instructions5, 10, 225);
				g.drawString (instructions6, 10, 250);
				
				Image score = Toolkit.getDefaultToolkit ().getImage ("scorepic.png");
				g.drawImage(score, 100, 300, 300, 150, this);
				
				Image line = Toolkit.getDefaultToolkit ().getImage ("line.png");
				g.drawImage(line, 500, 300, 350, 150, this);
				
		}
		
		//Drawing the about page
		if (aboutPage == true) {
				g2.setColor(Color.WHITE);
				g2.fillRect(0, 0, screenWidth, screenHeight);
				
				String about = "This game, “really good rhythm game.” is by Bryant Zheng in grade 11 computer science (ICS3U1/ICS3C1).";
				
				g2.setColor(Color.BLACK);
				g.setFont(new Font("Dialog", Font.PLAIN, 20)); 
				g.drawString (about, 10, 100);
		}
		
		//Drawing the end screen
		if (time > 130) {
			g2.setColor(Color.WHITE);
			g2.fillRect(50, 50, screenWidth - 90, screenHeight - 90);
			
			String congrats1 = "CONGRATULATIONS! YOUR HIGHSCORE IS: ";
			String congrats2 = scoreString;
			
			g2.setColor(Color.BLACK);
			g.setFont(new Font("Dialog", Font.BOLD, 40)); 
			g.drawString (congrats1, 70, 200);
			
			g2.setColor(Color.BLACK);
			g.setFont(new Font("Dialog", Font.BOLD, 100));
			g.drawString (congrats2, 350, 400);
			
			back.setVisible(true);
			titleScreen = true;
			
		}
		
		
	}
	
	//Moves the falling pieces down. Also checks if the falling pieces are missed by the user.
	void moveAndCheck() {
		
		if (titleScreen == false ) {
			for (int i = 0; i < 57; i++) {
				pieceOnRed[i].y += 5;
				
				if (pieceOnRed[i].y == 650) {
					score--;
					pieceOnRed[i] = new Rectangle (0, 700, 0, 0);
				}
			
			}
			
			for (int i = 0; i < 30; i++) {
				pieceOnYellow[i].y += 5;
				
				if (pieceOnYellow[i].y == 650) {
					score--;
					pieceOnYellow[i] = new Rectangle (0, 700, 0, 0);
				}
			
			}
		
			for (int i = 0; i < 51; i++) {
				pieceOnGreen[i].y += 5;
				
				if (pieceOnGreen[i].y == 650) {
					score--;
					pieceOnGreen[i] = new Rectangle (0, 700, 0, 0);
				}
			
			}
		
			for (int i = 0; i < 53; i++) {
				pieceOnBlue[i].y += 5;
				
				if (pieceOnBlue[i].y == 650) {
					score--;
					pieceOnBlue[i] = new Rectangle (0, 700, 0, 0);
				}
			
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	//If one of the corresponding keys is pressed, it checks to see if the falling piece is aligned with the target. If it is, then points are added, if not, then points are removed.
	@Override
	public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
		
			for(int i = 0; i < 57; i++) {
				if ((key == KeyEvent.VK_F) && (pieceOnRed[i].getY() < 575) && (pieceOnRed[i].getY() > 500)) {
					score = score + 2;
					pieceOnRed[i] = new Rectangle (0, 700, 0, 0);
				}
			}
			
				if ((key == KeyEvent.VK_G)) {
					score--;	
				}
		
				
			for(int i = 0; i < 30; i++) {
				if ((key == KeyEvent.VK_G) && (pieceOnYellow[i].getY() < 575) && (pieceOnYellow[i].getY() > 500)) {
					score = score + 2;
					pieceOnYellow[i] = new Rectangle (0, 700, 0, 0);
					
				}
			}
			
				if ((key == KeyEvent.VK_G)) {
					score--;	
				}
			
		
			for(int i = 0; i < 51; i++) {
				if ((key == KeyEvent.VK_H) && (pieceOnGreen[i].getY() < 575) && (pieceOnGreen[i].getY() > 500)) {
					score = score + 2;
					pieceOnGreen[i] = new Rectangle (0, 700, 0, 0);
			
					}
			}
			
				if ((key == KeyEvent.VK_H)) {
					score--;	
				}
			
			
			for(int i = 0; i < 53; i++) {
				if ((key == KeyEvent.VK_J) && (pieceOnBlue[i].getY() < 555) && (pieceOnBlue[i].getY() > 510)) {
					score = score + 2;
					pieceOnBlue[i] = new Rectangle (0, 700, 0, 0);
				
			
				}
			}
			
				if ((key == KeyEvent.VK_J)) {
					score--;	
				}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
	}
	
	//This method plays a sound. The parameter is a String and returns void.
	public void playSound(String soundName) {
      try {
       AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile( ));
       Clip clip = AudioSystem.getClip( );
       clip.open(audioInputStream);
       clip.start( );
      }
      catch(Exception ex)
      {
        System.out.println("Error with playing sound.");
        ex.printStackTrace( );
      }
    }
	
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame ("really good rhythm game.");
		CulminatingProjectBryantZheng myPanel = new CulminatingProjectBryantZheng ();
		myPanel.setLayout(null);
		frame.add(myPanel);
		
		frame.addKeyListener(myPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		myPanel.add(back);
		myPanel.add(start);
		myPanel.add(instructions);
		myPanel.add(about);	
	}
}