package ghp.tilegame.main.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JLabel;
import javax.swing.Timer;
import ghp.tilegame.main.mapGenerators.MapGenerator1;
import ghp.tilegame.main.mapGenerators.MapGenerator2;
import ghp.tilegame.main.mapGenerators.MapGenerator3;

/**
 * this class declares and activates all timers for the game
 * @author Eli Baz
 *
 */
public class TimerManager implements ActionListener{
	private Timer t3, t4, t5, t6, timer, clockTimer, brickTimer;
	private static final int BALL_DELAY = 2;
	private static final int IMAGE_DELAY = 4;
	private static final int CLOCK_DELAY = 1000;
	private static final int IMAGE_SUSPEND = 5000;
	private Level l;
	private JLabel paddleLabel, skullLabel, paddle_skull, arrow_skull, timeLabel, res;
	private Area areaA;
	private Rectangle paddle;
	
	public TimerManager(Level l)
	{
		this.l = l;
		timer = new Timer(BALL_DELAY, this);
		timer.start();		
		brickTimer = new Timer(15, null);
		brickTimer.start();
		res = new JLabel();
		timeLabel = new JLabel();		
		l.add(timeLabel);    
		timeLabel.setBounds(20, 11, 110, 20);	
		timeLabel.setFont(new Font("serif",Font.BOLD,25));
		timeLabel.setForeground(Color.white);
		
		clockTimer = new Timer(CLOCK_DELAY, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {			
				setTime();							
				l.repaint();			
			}
		});
		
		t3 = new Timer(IMAGE_DELAY, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paddleLabel.setLocation(l.imageposX3, l.imageposY3++);	
				areaA = new Area(paddleLabel.getBounds());
				paddle = new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight);
				if(paddle.intersects(areaA.getBounds2D()))
				{				
					l.playerX = 0;		
					l.paddleWidth = 700;		
					KeyBoardControl.getKeys()[KeyEvent.VK_LEFT] = false;
					l.imageposY3 = l.xBorder;
					new Timer(IMAGE_SUSPEND, new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {						
							l.paddleWidth = 110;
							l.playerX = 290;
							((Timer)e.getSource()).stop();
							l.repaint();
						}
					}).start();
										
				}	
				l.repaint();
			}
		});	
			
		t4 = new Timer(IMAGE_DELAY, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					skullLabel.setLocation(l.imageposX4, l.imageposY4++);
					areaA = new Area(skullLabel.getBounds());
					paddle = new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight);
					if(paddle.intersects(areaA.getBounds2D()))
					{
						l.imageposY4 = l.xBorder;
						l.intersect = false;
						new Timer(IMAGE_SUSPEND, new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								l.intersect = true;
								((Timer)e.getSource()).stop();
								l.repaint();
							}
						}).start();
					}
					l.repaint();
				}
		});									
		
		t5 = new Timer(IMAGE_DELAY, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					paddle_skull.setLocation(l.imageposX5, l.imageposY5++);
					areaA = new Area(paddle_skull.getBounds());
					paddle = new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight);
					if(paddle.intersects(areaA.getBounds2D()))
					{
						l.imageposY5 = l.xBorder; 
						if(l.mf.getLevel_Running() == Level.LEVEL_ONE)
						{
							l.mp = new MapGenerator1(9,7, l);
							l.totalBricks = 20;
						}
						else if(l.mf.getLevel_Running() == Level.LEVEL_TWO)
						{
							l.mp = new MapGenerator2(13,7, l);
							l.totalBricks = 70;
						}
						else if(l.mf.getLevel_Running() == Level.LEVEL_THREE)
						{
							l.mp = new MapGenerator3(19,7, l);
							l.totalBricks = 93;
						}
					}
					l.repaint();
				}
		});									
			
		t6 = new Timer(IMAGE_DELAY, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					arrow_skull.setLocation(l.imageposX6, l.imageposY6++);
					areaA = new Area(arrow_skull.getBounds());
					paddle = new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight);
					if(paddle.intersects(areaA.getBounds2D()))
					{
						l.am.pause();
						l.imageposY6 = l.xBorder;
						new Timer(IMAGE_SUSPEND, new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								l.isRunning = true;
								ExecutorService executor = Executors.newSingleThreadExecutor();
								executor.submit(l.am);
								((Timer)e.getSource()).stop();
								l.repaint();
							}
						}).start();
						
						
					}
					l.repaint();
				}
		});				
	}
	
	/**
	 * this method sets the time in the game
	 */
	public void setTime()
	{
		timeLabel.setText("Time: " + String.valueOf(l.timeCounter));
		l.timeCounter--;
		if(l.timeCounter <=-1 && l.count < 3 && l.totalBricks > 0)
		{		
			clockTimer.stop();
			l.am.pause();
			intersection();					
		}	
		else if(l.timeCounter <=-1)
		{
			clockTimer.stop();
			l.am.pause();
		}
	}
	
	/**
	 * this method controls the ball movement
	 */
	@Override
	public void actionPerformed(ActionEvent e) {	
		if(l.play)
		{		
			if(new Rectangle(l.ballposX,l.ballposY,20,20).intersects(new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight)))
			{
				l.ballYdir = -l.ballYdir;
			}
	
		A:	for(int i=0;i<l.mp.getMap().length;i++)
			{
				for(int j=0;j<l.mp.getMap()[0].length;j++)
				{
					if(l.mp.getMap()[i][j] > 0)
					{
						l.brickX = j * l.mp.getBrickposX() + 70; 
						l.brickY = i * l.mp.getBrickposY() + 60;
						l.brickWidth = l.mp.getBrickposX();
						l.brickHeight = l.mp.getBrickposY();
						
						Rectangle rect = new Rectangle(l.brickX,l.brickY,l.brickWidth,l.brickHeight);
						Rectangle ballRect = new Rectangle(l.ballposX,l.ballposY,20,20);
						Rectangle brickRect = rect;
						
						//checks if ball intersect brick
						if(ballRect.intersects(brickRect) && l.intersect)
						{			
							if(l.mp.getMap()[i][j] == 2)
							{
								l.mp.setBrickValue(0,i,j);
								l.totalBricks--;
								l.score+=5;	 
							}
							else
							{
								
								l.rn = new Random();
								l.rand = l.rn.nextInt(6) + 1;
								
								switch (l.rand) {
									case (2):
										l.mp.setBrickValue(2,i,j);//to add label and to setbounds according to i,j
									break;
									case (3):
										l.mp.setBrickValue(0,i,j);	
											
										if(l.mp.getMap()[i][j] == 0)
										{ 													
											l.mp.setBrickValue(3,i,j);								
											return;									
										}
									break;
									case (4):
										l.mp.setBrickValue(0,i,j);
										
										if(l.mp.getMap()[i][j] == 0)
										{												
											l.mp.setBrickValue(4,i,j);										
											return;
										}
									break;
									case (5):
										l.mp.setBrickValue(0,i,j);
										
										if(l.mp.getMap()[i][j] == 0)
										{
											l.mp.setBrickValue(5,i,j);
											return;
										}
									break;
									case (6):
										l.mp.setBrickValue(0,i,j);
										if(l.mf.getLevel_Running() == Level.LEVEL_TWO && l.mp.getMap()[i][j] == 0)
										{	
											l.mp.setBrickValue(6,i,j);
											return;										
										}
										else
										{
											l.totalBricks--;
											l.score+=5;
											break;
										}
									default:
										l.mp.setBrickValue(0,i,j);
										l.totalBricks--;
										l.score+=5;										
									}
								
							}
							
							if(l.ballposX +19 <= brickRect.x || l.ballposX +1 >= brickRect.x + brickRect.width)
							{
								l.ballXdir = -l.ballXdir;
							}
							else
							{
								l.ballYdir = -l.ballYdir;
							}
							break A;
						}
					}				
				}
				
			}
			
			l.ballposX += l.ballXdir;
			l.ballposY += l.ballYdir;
			if(l.ballposX < 0)
			{
				l.ballXdir = -l.ballXdir;
			}
			if(l.ballposY < 0)
			{
				l.ballYdir = -l.ballYdir;
			}
			if(l.ballposX > 670)
			{  
				l.ballXdir = -l.ballXdir;
			}
			
		}
		l.repaint();

	}
	
	/**
	 * this method sets the y coordinate location of images outside the jframe
	 */
	public void imageBorder()
	{
		l.imageposY3 = l.xBorder;
		l.imageposY4 = l.xBorder;
		l.imageposY5 = l.xBorder;
		l.imageposY6 = l.xBorder;
	}

	/**
	 * this method checks if there was an intersection of paddle and bricks after the time is up
	 */
	public void intersection()
	{
			brickTimer.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<l.mp.getMap().length;i++)
					{
						for(int j=0;j<l.mp.getMap()[0].length;j++)
						{	
							if(l.mp.getMap()[i][j] > 0)
							{
								l.mp.brickposY++;									 
								if(new Rectangle(j * l.mp.brickposX +70,i * l.mp.brickposY +60,l.brickWidth,l.brickHeight).intersects(new Rectangle(l.playerX,550,l.paddleWidth,l.paddleHeight)))
								{ 			
									l.flag = true;
									intersection_result();
								}								
								else if(l.mp.brickposY > l.xBorder && !l.flag)	
								{
									l.totalBricks = 0;
								}
								if(l.mp.brickposY > l.xBorder)
									l.mp.getMap()[i][j] = 0;
								
							}
							
						}
					}
					l.repaint();
				}
			});
			brickTimer.start();
	}
	
	/**
	 * this method shows message of intersection 
	 */
	public void intersection_result()
	{
		imageBorder();
		l.count = 3; 
		res.setBounds(230, 250, 300, 100);	
		l.add(res);
		res.setText("<html>you had a collision <br> click enter to restart</html>");
		res.setFont(new Font("serif",Font.BOLD,25));
		res.setForeground(Color.white);	
	}
	
	/**
	 * this method starts the clock running and stops the the brick timer
	 * if new level is started
	 */
	public void timers_control()
	{
		clockTimer.start();
		brickTimer.stop();
	}
	
	public Timer getT3() {
		return t3;
	}


	public void setT3(Timer t3) {
		this.t3 = t3;
	}


	public Timer getT4() {
		return t4;
	}


	public void setT4(Timer t4) {
		this.t4 = t4;
	}


	public Timer getT5() {
		return t5;
	}


	public void setT5(Timer t5) {
		this.t5 = t5;
	}


	public Timer getT6() {
		return t6;
	}


	public void setT6(Timer t6) {
		this.t6 = t6;
	}


	public Timer getTimer() {
		return timer;
	}


	public void setTimer(Timer timer) {
		this.timer = timer;
	}


	public Timer getClockTimer() {
		return clockTimer;
	}


	public void setClockTimer(Timer clockTimer) {
		this.clockTimer = clockTimer;
	}


	public Timer getBrickTimer() {
		return brickTimer;
	}


	public void setBrickTimer(Timer brickTimer) {
		this.brickTimer = brickTimer;
	}


	public JLabel getTimeLabel() {
		return timeLabel;
	}


	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}


	public JLabel getPaddleLabel() {
		return paddleLabel;
	}


	public void setPaddleLabel(JLabel paddleLabel) {
		this.paddleLabel = paddleLabel;
	}


	public JLabel getSkullLabel() {
		return skullLabel;
	}


	public void setSkullLabel(JLabel skullLabel) {
		this.skullLabel = skullLabel;
	}


	public JLabel getPaddle_skull() {
		return paddle_skull;
	}


	public void setPaddle_skull(JLabel paddle_skull) {
		this.paddle_skull = paddle_skull;
	}


	public JLabel getArrow_skull() {
		return arrow_skull;
	}


	public void setArrow_skull(JLabel arrow_skull) {
		this.arrow_skull = arrow_skull;
	}

	public JLabel getRes() {
		return res;
	}

	public void setRes(JLabel res) {
		this.res = res;
	}
	
}
