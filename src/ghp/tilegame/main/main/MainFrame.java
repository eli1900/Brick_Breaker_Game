package ghp.tilegame.main.main;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import ghp.tilegame.main.levels.Level;
import ghp.tilegame.main.levels.Level1;
import ghp.tilegame.main.levels.Level2;
import ghp.tilegame.main.levels.Level3;

/**
 * this class adds components to the jframe
 * @author Eli Baz
 *
 */
public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JButton b4;
	private Font myFont = new Font("Serif", Font.ITALIC | Font.BOLD, 12);
	private Font newFont = myFont.deriveFont(20F);
	private Level l1, l2, l3; 
	private JLabel label;
	private FileReader reader;
	private BufferedReader bufferedReader;
	private String row;
	private int level_Running; //checks which level is on play

	public MainFrame() throws IOException, AWTException
	{
		super("BrickOut");
		setSize(700,600);
		setLocationRelativeTo(null);
		setResizable(false);	
		setFocusTraversalKeysEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l1 = new Level1(this);
		l2 = new Level2(this);
		l3 = new Level3(this);
				
		b1 = new JButton("Level 1");
		b2 = new JButton("Level 2");
		b3 = new JButton("Level 3");
		b4 = new JButton("Exit Game");
		label = new JLabel();
		label.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/images/world.jpg")).getImage().getScaledInstance(700,600, Image.SCALE_SMOOTH)));
		add(label);
		b1.setBounds(20, 20, 200, 30);
		b1.setBackground(Color.red);
		b1.setFont(newFont);
		b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.add(b1);
		b2.setBounds(20, 60, 200, 30);		
		b2.setFont(newFont);
		b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.add(b2);
		b2.setBackground(Color.red);
		b2.setEnabled(false);
		b3.setBounds(20, 100, 200, 30);
		b3.setFont(newFont);
		b3.setBackground(Color.red);
		b3.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.add(b3);
		b4.setBounds(20, 140, 200, 30);
		b4.setFont(newFont);
		b4.setBackground(Color.red);
		b4.setCursor(new Cursor(Cursor.HAND_CURSOR));
		label.add(b4);
		b3.setEnabled(false);
		reader = new FileReader(new File("C:\\Brick_Breaker_Game\\result.txt"));  //(file.getF1());
		bufferedReader = new BufferedReader(reader);
		if(isEnableLevel("level 1"))
			b2.setEnabled(true);
		if(isEnableLevel("level 2"))
			b3.setEnabled(true);
		
		//start play level 1
		b1.addActionListener(new ActionListener() {
					
			@Override 
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(label);
				try 
				{
					level_Running = 1; //level 1 is played
					getContentPane().add(l1).requestFocusInWindow();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				validate();
				cursor();
			}
		});
			
		//start play level 2
		b2.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(label);
				try 
				{
					level_Running = 2; //level 2 is played
					getContentPane().add(l2).requestFocusInWindow();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				validate();
				cursor();
			}
		});
			
		//start play level 3
		b3.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				getContentPane().remove(label);
				try 
				{
					level_Running = 3; //level 3 is played
					getContentPane().add(l3).requestFocusInWindow();
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				validate();
				cursor();
				
			}
		});
				
		//exit game
		b4.addActionListener(new ActionListener() {
					
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
						
			}
		});
				
	}
	
	public boolean isEnableLevel(String l) throws IOException
	{
		while ((row = bufferedReader.readLine()) != null) 
		{
            if(row.contains(l))
            {
            	System.out.println("true");
            	return true;
            }      
        }
		return false;
	}

	//remove mouse cursor from jpanel
	public void cursor()
	{
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
			cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
	}

	public int getLevel_Running() {
		return level_Running;
	}

	public void setLevel_Running(int level_Running) {
		this.level_Running = level_Running;
	}

	public Level getL1() {
		return l1;
	}

	public void setL1(Level l1) {
		this.l1 = l1;
	}

	public Level getL2() {
		return l2;
	}

	public void setL2(Level l2) {
		this.l2 = l2;
	}

	public Level getL3() {
		return l3;
	}

	public void setL3(Level l3) {
		this.l3 = l3;
	}

	public void setL3(Level3 l3) {
		this.l3 = l3;
	}

	public Font getMyFont() {
		return myFont;
	}

	public void setMyFont(Font myFont) {
		this.myFont = myFont;
	}

	public Font getNewFont() {
		return newFont;
	}

	public void setNewFont(Font newFont) {
		this.newFont = newFont;
	}
				
}


