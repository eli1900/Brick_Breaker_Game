package ghp.tilegame.main.levels;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import ghp.tilegame.main.file.ResultFile;
import ghp.tilegame.main.main.Main;
import ghp.tilegame.main.main.MainFrame;
import ghp.tilegame.main.mapGenerators.MapGenerator;

/**
 * this class is an abstract class and has the paintComponent method which draws the all components on screen
 * and activates the listeners of buttons to move to next level
 * @author Eli Baz
 *
 */
public abstract class Level extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int score = 0, count = 0, ballNum = 3;
	protected int imageposX3, imageposY3, imageposX4, imageposY4, imageposX5, imageposY5, imageposX6, imageposY6;
	protected Point point, point1;
	protected JLabel BallLabel;
	protected int brickX, brickY, brickWidth, brickHeight;   
	protected int ballposX = 337, ballposY = 530, ballXdir = -1, ballYdir = -2;
	protected MapGenerator mp;
	protected final int xBorder = 580; 
	protected int playerX = 290;
	protected int paddleWidth = 110, paddleHeight = 13;
	protected String line;
	protected ResultFile rf;
	protected boolean bClose = false, intersect = false;
	protected MainFrame mf;
	protected JButton b; 
	protected boolean isRunning = false, flag = false, play = false;
	protected Random rn; 
	protected int rand;
	protected ArrowManager am;
	protected TimerManager tm;
	protected KeyBoardControl kc;
	protected int totalBricks;
	protected int timeCounter;
	protected Container pane;
	protected static final int LEVEL_ONE = 1;
	protected static final int LEVEL_TWO = 2;
	protected static final int LEVEL_THREE = 3;

	public Level(MainFrame mf) throws IOException
	{
		this.mf = mf;
		setLayout(null);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);			
		BallLabel = new JLabel();
		add(BallLabel);
		BallLabel.setBounds(285, 11, 100, 20);
		BallLabel.setFont(new Font("serif",Font.BOLD,25));
		BallLabel.setForeground(Color.white);
		BallLabel.setText("Balls: 3");
		am = new ArrowManager(this);
		tm = new TimerManager(this);
		kc = new KeyBoardControl(this);	
		rf = new ResultFile();		
		b = new JButton("Click to Next Level");	
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(mf.getLevel_Running() == LEVEL_ONE)
				{
					pane = mf.getContentPane();
					pane.remove(mf.getL1());
						
						if(e.getSource()==b)
						{		
							mf.setLevel_Running(2);
							pane.add(mf.getL2()).requestFocusInWindow();
						}			
					pane.validate(); 
				}
				else if(mf.getLevel_Running() == LEVEL_TWO)
				{
					pane = mf.getContentPane();
					pane.remove(mf.getL2());
						
						if(e.getSource()==b)
						{		
							mf.setLevel_Running(3);
							pane.add(mf.getL3()).requestFocusInWindow();
						}			
					pane.validate(); 
				}
			}
		});
		imageposY3 = xBorder;
		imageposY4 = xBorder;
		imageposY5 = xBorder;
		imageposY6 = xBorder;
		
	}
	
	/**
	 * this method draws components(ball, paddle, score label)
	 */	
	public void paintComponent(Graphics g)
	{
		kc.update();
		//jpanel background
		if(mf.getLevel_Running() == LEVEL_ONE)
		{
			Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/alien4.jpg")); 
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this); 
		}
		else if(mf.getLevel_Running() == LEVEL_TWO)
		{
			Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/matrix.jpg")); 
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		else if(mf.getLevel_Running() == LEVEL_THREE)
		{
			Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/stars.jpg")); 
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
		//jpanel drawimg map
		mp.draw(g);
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Score: "+score,570,30);
		
		//paddle		
		Graphics2D g2d = (Graphics2D)g;		
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.setColor(new Color(204, 0, 0));
	    g2d.fillOval(playerX,550,paddleWidth,paddleHeight); 
		
		//ball
		g.setColor(new Color(78,221, 9));
		g.fillOval(ballposX,ballposY,20,20);
		
		//checks if player broke all bricks
		if(totalBricks <= 0)
		{
			tm.getClockTimer().stop();
			am.pause();
			play=false;
			ballXdir = 0;
			ballYdir = 0;
			count = 3;
			ballposY = xBorder;
			tm.imageBorder();
			if(mf.getLevel_Running() == LEVEL_ONE || mf.getLevel_Running() == LEVEL_TWO)
			{
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("You Won,",260,300);
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("Your score is: "+score,230,350);
			}
			else if(mf.getLevel_Running() == LEVEL_THREE)
			{
				Image winnerImage = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/winner.jpg")); 
				g.drawImage(winnerImage, 240, 100, 250,150, this);
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("You finished all levels successfully",155,300);
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("Your score is: "+score,230,350);
			}		
			
			//write to external file
			if(!bClose)
			{
				write_to_file(score);
			}	
			if(mf.getLevel_Running() == LEVEL_ONE || mf.getLevel_Running() == LEVEL_TWO)
			{
				next_level_btn();
			}
			
		}
		
		//checks if the ball falled beneath the paddle
		if(ballposY > xBorder)
		{
			play=false;
			ballXdir = 0;
			ballYdir = 0;
			count++;
			ballNum--;
			balls_number();
			
			//checks if all 3 balls fell beneath the paddle
			if(count >=3)
			{
				am.pause();
				tm.imageBorder();
				ballNum = 0;
				balls_number();
				g.setColor(Color.cyan);
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("Game Over, Your Score: "+score,190,300);			
				g.setFont(new Font("serif",Font.BOLD,30));
				g.drawString("click enter to restart",230,350);
				if(mf.getLevel_Running() ==LEVEL_ONE)
				{
					timeCounter = 60;
				}
				else if(mf.getLevel_Running() == LEVEL_TWO)
				{
					timeCounter = 90;
				}
				else if(mf.getLevel_Running() == LEVEL_THREE)
				{
					timeCounter = 120;
				}
				
			}
			else
			{			
				ballposX = playerX + 45;
				ballposY = 530;
				ballXdir = -ballXdir;
				ballYdir = -ballYdir;
			}
			
		}
	
	}
	
	/**
	 * this method writes the game result to external file
	 * @param game_score
	 */
	public void write_to_file(int game_score)
	{
		try
		{	
			if(mf.getLevel_Running() == LEVEL_ONE)
			{
				line = "your score in level 1 is: "+game_score;
			}
			else if(mf.getLevel_Running() == LEVEL_TWO)
			{
				line = "your score in level 2 is: "+game_score;
			}
			else if(mf.getLevel_Running() == LEVEL_THREE)
			{
				line = "your score in level 3 is: "+game_score;
			}
			rf.getBw().write(line + System.lineSeparator());
			System.out.println("File written Successfully");
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{ 
		   try{
		      if (rf.getBw() != null)
		      {
		    	  rf.getBw().close();
		    	  bClose = true;
		      }		    	  		 		    	  			          	  
		   }catch(Exception ex){
		       System.out.println("Error in closing the BufferedWriter"+ex);
		    }
		}
	}
	
	/**
	 * this method adsd button when the level is finished 
	 * to move on to next level
	 */
	public void next_level_btn()
	{
		b.setBounds(30, 30, 200, 50);
		b.setBackground(Color.red);
		b.setFont(mf.getNewFont());
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(b);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * this method sets the balls value in jlabel
	 */
	public void balls_number()
	{
		BallLabel.setText("Balls: " + String.valueOf(ballNum));	
	}

	public static int getLevelOne() {
		return LEVEL_ONE;
	}

	public static int getLevelTwo() {
		return LEVEL_TWO;
	}

	public static int getLevelThree() {
		return LEVEL_THREE;
	}

	public JLabel getBallLabel() {
		return BallLabel;
	}

	public void setBallLabel(JLabel ballLabel) {
		BallLabel = ballLabel;
	}

	public MainFrame getMf() {
		return mf;
	}

	public void setMf(MainFrame mf) {
		this.mf = mf;
	}

	public int getxBorder() {
		return xBorder;
	}

	public TimerManager getTm() {
		return tm;
	}

	public void setTm(TimerManager tm) {
		this.tm = tm;
	}

	public int getImageposY3() {
		return imageposY3;
	}

	public void setImageposY3(int imageposY3) {
		this.imageposY3 = imageposY3;
	}

	public int getImageposY4() {
		return imageposY4;
	}

	public void setImageposY4(int imageposY4) {
		this.imageposY4 = imageposY4;
	}

	public int getImageposY5() {
		return imageposY5;
	}

	public void setImageposY5(int imageposY5) {
		this.imageposY5 = imageposY5;
	}

	public int getImageposY6() {
		return imageposY6;
	}

	public void setImageposY6(int imageposY6) {
		this.imageposY6 = imageposY6;
	}

	public int getImageposX3() {
		return imageposX3;
	}

	public void setImageposX3(int imageposX3) {
		this.imageposX3 = imageposX3;
	}

	public int getImageposX4() {
		return imageposX4;
	}

	public void setImageposX4(int imageposX4) {
		this.imageposX4 = imageposX4;
	}

	public int getImageposX5() {
		return imageposX5;
	}

	public void setImageposX5(int imageposX5) {
		this.imageposX5 = imageposX5;
	}

	public int getImageposX6() {
		return imageposX6;
	}

	public void setImageposX6(int imageposX6) {
		this.imageposX6 = imageposX6;
	}
	
}
