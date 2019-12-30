package ghp.tilegame.main.levels;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * this class is a thread that shoots the arrows in thr game
 * @author Eli Baz
 *
 */
public class ArrowManager extends Thread{
	
	public JLabel leftArrow, rightArrow;
	public int xPosLeft = -1;
	public int yPosLeft = -1;
	public int xPosRight = -1;
	public int yPosRight = -1;
	public Level l;
	public Random rn = new Random();
	public int num;
	
	public ArrowManager(Level l)
	{
		this.l = l;	
		leftArrow = new JLabel();
		leftArrow.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/myarrow2.png")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));	
		l.add(leftArrow);
		leftArrow.setBounds(-50, 470, 50,50);
		rightArrow = new JLabel();
		rightArrow.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/myarrow1.png")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));		
		l.add(rightArrow);	
		rightArrow.setBounds(695, 470, 50,50);
	}

	/**
	 * this method stops the thread
	 */
	public synchronized void pause() 
	{
	      l.isRunning = false;
	}

	@Override
	public void run() {
		
		while(l.isRunning){
			//Init arrow place
			leftArrow.setBounds(-50, 470, 50,50);
			rightArrow.setBounds(695, 470, 50,50);
			//random choose arrow
			num = rn.nextInt(2) + 1;
			int i = 0;
			do
			{	
				switch (num) {
				case (1):			
						l.point = leftArrow.getLocation();
						l.point.x += xPosLeft;
		                l.point.y += yPosLeft;
		                if (l.point.x < 0)
		                {
		                    l.point.x = 0;
		                    xPosLeft *= -1;
		                } 
		                else if (l.point.x + leftArrow.getWidth() > l.getWidth())
		                {
		                    l.point.x = l.getWidth() - leftArrow.getWidth();
		                    xPosLeft *= -1;
		                }
		                Area areaA = new Area(leftArrow.getBounds());
		                Rectangle ballRect = new Rectangle(l.ballposX,l.ballposY,20,20);
		                
	        			if(ballRect.intersects(areaA.getBounds2D()))
	        			{
	        				l.ballposX = rn.nextInt(600) + 1;
	        				l.ballposY = rn.nextInt(450) + 1;
	        			}
		                leftArrow.setLocation(l.point);	 
	
				break;
					
				case (2):
						l.point1 = rightArrow.getLocation();
						l.point1.x += xPosRight;
		                l.point1.y += yPosRight;
		                if (l.point1.x > 645) {
		                    l.point1.x = 645;
		                    xPosRight *= -1;
		                }
		                else if (l.point1.x + rightArrow.getWidth() > l.getWidth()) {
		                    l.point1.x = l.getWidth() - rightArrow.getWidth();
		                    xPosRight *= -1; 
		                }
		                Area areaB = new Area(rightArrow.getBounds());
		                Rectangle ballRect1 = new Rectangle(l.ballposX,l.ballposY,20,20);
		                
	        			if(ballRect1.intersects(areaB.getBounds2D()))
	        			{
	        				l.ballposX = rn.nextInt(600) + 1;
	        				l.ballposY = rn.nextInt(450) + 1;
	        			}
		                rightArrow.setLocation(l.point1);	   	               
				break;
		               		
				default:
					break;
				}
				
				 try {
						sleep((long) 1.5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
				 i++;
				 
			}while(i < 650);
		
			try {
				sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
