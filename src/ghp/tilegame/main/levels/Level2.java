package ghp.tilegame.main.levels;
import java.io.IOException;
import ghp.tilegame.main.main.MainFrame;
import ghp.tilegame.main.mapGenerators.MapGenerator2;

/**
 * this class sets the bricks and the clock time in level 2
 * @author Eli Baz
 *
 */
public class Level2 extends Level {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Level2(MainFrame mf) throws IOException
	{
		super(mf);
		totalBricks = 70;
		timeCounter = 90;
		tm.getTimeLabel().setText("Time: " + timeCounter);
		mp = new MapGenerator2(13,7, this);
	}

}
