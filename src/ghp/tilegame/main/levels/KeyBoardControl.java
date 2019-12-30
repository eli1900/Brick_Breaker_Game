package ghp.tilegame.main.levels;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import ghp.tilegame.main.mapGenerators.MapGenerator1;
import ghp.tilegame.main.mapGenerators.MapGenerator2;
import ghp.tilegame.main.mapGenerators.MapGenerator3;

/**
 * this class controls the keyboard button that the user uses in the game
 * @author Eli Baz
 *
 */
public class KeyBoardControl implements KeyListener{
	private Level l;
	private static boolean[] keys = new boolean[256];
	
	public KeyBoardControl(Level l)
	{
		this.l = l;
		l.addKeyListener(this);
	}
	
	/**
	 * this method sets components to restart the game
	 */
	public void restart_game()
	{	
		l.ballNum = 3;
		l.balls_number();			
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(l.am);
		l.ballposX = l.playerX;
		l.ballposY = 520;
		l.ballXdir = -1;
		l.ballYdir = -2;		
		l.score = 0;
		l.count=0;
	}
	
	/**
	 * this method controls the keyboard buttons
	 */
	public void update()
	{
		if(!l.play)
		{
			if(keys[KeyEvent.VK_ENTER])
			{
				l.play = true;
				l.flag = false;
				l.tm.getRes().setText("");
				l.intersect = true;
				if(l.mf.getLevel_Running() == Level.LEVEL_ONE)
				{
					level1();	
				}
				else if(l.mf.getLevel_Running() == Level.LEVEL_TWO)
				{
					level2();
				}
				else if(l.mf.getLevel_Running() == Level.LEVEL_THREE)
				{
					level3();
				}				
				l.play = true;				
			}		
			if(keys[KeyEvent.VK_RIGHT])
			{
				l.playerX+=2;
				l.ballposX+=2;							
				if(l.playerX > 572)
				{
					l.playerX = 585;	
					l.ballposX = 630;
				}	
				l.repaint();
			}
			if(keys[KeyEvent.VK_LEFT])
			{
				l.playerX-=2;
				l.ballposX-=2;
				if(l.playerX < 0)
				{
					l.playerX = 0;		
					l.ballposX = 45;
				}	
				l.repaint();
			}
		}
		else
		{
			if(l.paddleWidth >= 700)
				keys[KeyEvent.VK_RIGHT] = false;
			else if(keys[KeyEvent.VK_RIGHT])
			{
				l.playerX+=2;					
				if(l.playerX >= 585)
				{
					l.playerX = 585;		
				}	
				l.repaint();
			}
			if(keys[KeyEvent.VK_LEFT])
			{
				l.playerX-=2; 
				if(l.playerX <= 0)
				{
					l.playerX = 0;		
				}	
				l.repaint();
			}
		}
	}
	
	
	/**
	 * this method runs the thread and timers for level 1
	 */
	public void level1()
	{
		if(l.totalBricks == 20 && l.count == 0)
		{
			l.tm.timers_control();
		}	
		else if(l.count >= 3 && l.totalBricks > 0)
		{
			l.tm.imageBorder();
			l.totalBricks = 20;
			l.timeCounter = 60;
			l.tm.getTimeLabel().setText("Time: "+l.timeCounter);
			l.tm.timers_control();
			restart_game();
			l.mp = new MapGenerator1(9,7, l);
			l.repaint();
		}
		else if(l.count < 3 || l.totalBricks == 20)
		{
			ball_position();
			l.repaint();
		}	
	}
	
	/**
	 * this method runs the thread and timers for level 2
	 */
	public void level2()
	{
		if(l.totalBricks == 70 && l.count == 0)
		{
			l.tm.timers_control();
			l.isRunning = true;
			l.am.start();
		}	
		else if(l.count >=3 && l.totalBricks > 0)
		{
			l.isRunning = true;
			l.tm.imageBorder();
			l.totalBricks = 70;	
			l.timeCounter = 90;
			l.tm.getTimeLabel().setText("Time: "+l.timeCounter);
			l.tm.timers_control();
			restart_game();
			l.mp = new MapGenerator2(13,7, l);
			l.repaint();
		}
		else if(l.count < 3 || l.totalBricks == 70)
		{
			ball_position();
			l.repaint();
		}
	}
	
	/**
	 * this method runs the thread and timers for level 3
	 */
	public void level3()
	{
		if(l.totalBricks == 93 && l.count == 0)
		{ 
			l.tm.timers_control();
			l.isRunning = true;
			l.am.start();
		}	
		else if(l.count>=3 && l.totalBricks > 0)
		{
			l.isRunning = true;
			l.tm.imageBorder();
			l.totalBricks = 93;	
			l.timeCounter = 120;
			l.tm.getTimeLabel().setText("Time: "+l.timeCounter);
			l.tm.timers_control();
			restart_game();
			l.mp = new MapGenerator3(19,7, l);
			l.repaint();
		}
		else if(l.count < 3 || l.totalBricks == 93)
		{
			ball_position();
			l.repaint();
		}
	}
	
	/**
	 * this method sets the ball position
	 */
	public void ball_position()
	{
		l.ballposX = l.playerX;
		l.ballposY = 520;
		l.ballXdir = -1;
		l.ballYdir = -2;
	}

	public static boolean[] getKeys() {
		return keys;
	}

	public static void setKeys(boolean[] keys) {
		KeyBoardControl.keys = keys;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
