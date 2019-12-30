package ghp.tilegame.main.mapGenerators;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import ghp.tilegame.main.levels.Level;

/**
 * this class is an abstact class that draws the bricks and adds the images on the screen
 * @author Eli Baz
 *
 */
public abstract class MapGenerator {
	protected int map[][];
	public int brickposX;
	public int brickposY;
	public int brickWidth;
	public int brickHeight;
	protected Level l;
	
	public MapGenerator(Level l)
	{
		this.l = l;
	}
	
	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	
	public int getBrickposX() {
		return brickposX;
	}

	public void setBrickposX(int brickposX) {
		this.brickposX = brickposX;
	}

	public int getBrickposY() {
		return brickposY;
	}

	public void setBrickposY(int brickposY) {
		this.brickposY = brickposY;
	}

	/**
	 * this method draws the bricks and images in the jpanel
	 * @param g
	 */
	public void draw(Graphics g)
	{
		
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				if(map[i][j]==1)
				{		
					if(l.getMf().getLevel_Running() == Level.getLevelOne())
					{
						g.setColor(new Color(102-102-102));
					}
					else if(l.getMf().getLevel_Running() == Level.getLevelTwo())
					{
						g.setColor(new Color(255,153,51));
					}
					else if(l.getMf().getLevel_Running() == Level.getLevelThree())
					{
						g.setColor(new Color(102,102,102));
					}
					g.fillRect(j * brickposX +70,i * brickposY +60,brickWidth,brickHeight);
					g.setColor(Color.black);
					g.drawRect(j * brickposX +70,i * brickposY +60,brickWidth,brickHeight);
				} 
				if(map[i][j]==2)
				{
					g.setColor(new Color(32, 255, 223));
					g.fillRect(j * brickposX +70,i * brickposY +60,brickWidth,brickHeight);
					g.setColor(Color.black);
					g.drawRect(j * brickposX +70,i * brickposY +60,brickWidth,brickHeight);
				}
				if(map[i][j]==3)
				{ 	
					if(l.getImageposY3() >= l.getxBorder())
					{
						image3(i,j);
					}
					else return;
				}
				if(map[i][j]==4)
				{
					if(l.getImageposY4() >= l.getxBorder())
					{
						image4(i,j);
					}
					else return; 
				}
				if(map[i][j]==5)
				{
					if(l.getImageposY5() >= l.getxBorder())
					{
						image5(i,j);
					}
					else return;
				}
				if(map[i][j]==6)
				{
					if(l.getImageposY6() >= l.getxBorder())
					{
						image6(i,j);
					}
					else return;
				}
			}
		}
		
	}
	
	/**
	 * this method adds the paddle image on the brick location.
	 * if the paddle image intersects the paddle it extends for 5 seconds
	 * @param x
	 * @param y
	 */
	public void image3(int x,int y)
	{
		l.getTm().setPaddleLabel(new JLabel());	
		l.add(l.getTm().getPaddleLabel());
		l.getTm().getPaddleLabel().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paddle.jpg")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
		l.getTm().getPaddleLabel().setSize(50, 20);
		l.setImageposX3(y * brickposX +20);
		l.setImageposY3(x * brickposY +45);
		l.getTm().getPaddleLabel().setLocation(l.getImageposX3(), l.getImageposY3());
		l.getTm().getT3().start();
	}
	
	/**
	 * this method adds the skull image on the brick location.
	 * if the skull image intersects the paddle the ball go through them without
	 * breaking the bricks for 5 seconds
	 * @param x
	 * @param y
	 */
	public void image4(int x,int y)
	{
		l.getTm().setSkullLabel(new JLabel());
		l.add(l.getTm().getSkullLabel());
		l.getTm().getSkullLabel().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/skull.jpg")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
		l.getTm().getSkullLabel().setSize(50, 40);
		l.setImageposX4(y * brickposX +55);
		l.setImageposY4(x * brickposY +60);
		l.getTm().getSkullLabel().setLocation(l.getImageposX4(), l.getImageposY4());
		l.getTm().getT4().start();
	}
	
	/**
	 * this method adds the black skull image on the brick location.
	 * it returns all bricks that the ball broke
	 * @param x
	 * @param y
	 */
	public void image5(int x,int y)
	{
		l.getTm().setPaddle_skull(new JLabel());
		l.add(l.getTm().getPaddle_skull());
		l.getTm().getPaddle_skull().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/paddle_skull.jpg")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
		l.getTm().getPaddle_skull().setSize(50, 40);
		l.setImageposX5(y * brickposX +90);
		l.setImageposY5(x * brickposY +60);  
		l.getTm().getPaddle_skull().setLocation(l.getImageposX5(), l.getImageposY5());
		l.getTm().getT5().start();
	}
	
	/**
	 * this method adds the white skull with arrows image on the brick location.
	 * it stops the arrow shooting for 5 seconds
	 * @param x
	 * @param y
	 */
	public void image6(int x,int y)
	{
		l.getTm().setArrow_skull(new JLabel());
		l.add(l.getTm().getArrow_skull());
		l.getTm().getArrow_skull().setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/arrow_skull.jpg")).getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH)));
		l.getTm().getArrow_skull().setSize(50, 40);
		l.setImageposX6(y * brickposX +125);
		l.setImageposY6(x * brickposY +60);
		l.getTm().getArrow_skull().setLocation(l.getImageposX6(), l.getImageposY6());
		l.getTm().getT6().start();
	}
	
	public void setBrickValue(int value,int row,int col)
	{
		map[row][col]=value;
	}
	
}
