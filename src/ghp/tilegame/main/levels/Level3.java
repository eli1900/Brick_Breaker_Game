package ghp.tilegame.main.levels;
import java.io.IOException;
import ghp.tilegame.main.main.MainFrame;
import ghp.tilegame.main.mapGenerators.MapGenerator3;

/**
 * this class sets the bricks and the clock time in level 3
 * @author Eli Baz
 *
 */
public class Level3 extends Level{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Level3(MainFrame mf) throws IOException
	{
		super(mf);
		totalBricks = 93;
		timeCounter = 120;
		tm.getTimeLabel().setText("Time: " + timeCounter);
		mp = new MapGenerator3(19,7, this);
	}

}
