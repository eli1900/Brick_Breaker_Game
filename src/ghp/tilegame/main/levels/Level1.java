package ghp.tilegame.main.levels;
import java.io.IOException;
import ghp.tilegame.main.main.MainFrame;
import ghp.tilegame.main.mapGenerators.MapGenerator1;

/**
 * this class sets the bricks and the clock time in level 1
 * @author Eli Baz
 *
 */
public class Level1 extends Level {		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Level1(MainFrame mf) throws IOException  
	{
		super(mf);
		totalBricks = 20;
		timeCounter = 60;
		tm.getTimeLabel().setText("Time: " + timeCounter);
		mp = new MapGenerator1(9,7, this);			
	}

}