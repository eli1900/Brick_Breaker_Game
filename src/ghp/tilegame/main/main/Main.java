package ghp.tilegame.main.main;
import java.awt.AWTException;
import java.io.IOException;

/**
 * this is the main class which runs the game
 * @author Eli Baz
 *
 */
public class Main {

	public static void main(String[] args) throws IOException, AWTException {	
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {							
				try {
					MainFrame f = new MainFrame();
					f.setVisible(true);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
							
			}
		});
			

			
	}

}
