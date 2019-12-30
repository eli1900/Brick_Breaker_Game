package ghp.tilegame.main.mapGenerators;

import ghp.tilegame.main.levels.Level;

public class MapGenerator2 extends MapGenerator{
	
	public MapGenerator2(int row,int col, Level l)
	{
		super(l);
		map=new int[row][col];
		
		//value of the bricks that appear in jpanel
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map[0].length;j++)
			{
				if(j%2==0 && i%2==0 || i%2==1)
				{
					map[i][j]=1;
				}
			}
		}
		
		//set the location and size of the bricks in jpanel
		brickposX=540/col;
		brickposY=190/row;
		brickWidth = brickposX;
		brickHeight = brickposY;
	}
	
}
