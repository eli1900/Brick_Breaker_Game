package ghp.tilegame.main.file;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * this class writes the level score to external file located in the project folder
 * @author Eli Baz
 *
 */
public class ResultFile {
	
	private File f1 = new File("C:\\Brick_Breaker_Game\\result.txt");
	private FileWriter writer;
	private BufferedWriter bw;
	
	public ResultFile() throws IOException
	{
		try
		{	
	        writer =new FileWriter(f1, true);
	        bw = new BufferedWriter(writer);

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public FileWriter getWriter() {
		return writer;
	}

	public void setWriter(FileWriter writer) {
		this.writer = writer;
	}

	public File getF1() {
		return f1;
	}

	public void setF1(File f1) {
		this.f1 = f1;
	}

	public BufferedWriter getBw() {
		return bw;
	}

	public void setBw(BufferedWriter bw) {
		this.bw = bw;
	}
	
}
