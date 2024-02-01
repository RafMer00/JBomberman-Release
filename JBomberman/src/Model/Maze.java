package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

/**
 * Maze represents the maze in a level of the game.
 * @author Raf&Vinz
 *
 */
public class Maze implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int height = 11;
	public static final int width = 13;
	public static final int cellSide = 9;
	public static final int totalWidth = width*cellSide;
	public static final int totalHeight = height*cellSide;
	
	private Cell[][] maze;
	private Gate gate;
	
	/**
	 * Maze class builder. Builds the maze based on the required walls.
	 * @param maxWall is the number of walls.
	 */
	public Maze(int maxWall) {
		int[][] tmpMaze = new int[height][width];
		
		//Place unbreakable wall in the labyrinth
		while (Arrays.stream(tmpMaze).flatMapToInt(Arrays::stream).anyMatch(x -> x==0)) {
			maze = init();
			for(int i = 0; i < height; i++) 
				for(int j = 0; j < width; j++) 
					tmpMaze[i][j] = maze[i][j] == null ? 0 : -1;
			tmpMaze = controlInit(tmpMaze, 0, 0, true);
		}
		//Place breakable wall in the labyrinth
		loadWall(maxWall);
		//Load the other cells.
		loadFloor();
	}
	
	/**
	 * Gate getter.
	 * @return gate.
	 */
	public Gate getGate() {
		return gate;
	}

	/**
	 * This method initialize the maze. 
	 * @return matrix of cells.
	 */
	private Cell[][] init(){
		maze = new Cell[height][width];
		
		//Adding default walls
		for (int i = 0; i < maze.length; i++) 
			for (int j = 0; j < maze[0].length; j++) 
				if (i%2 == 1 && j%2 == 1)
					maze[i][j] = new IndestructibleCell();
		
		//Adding unbreakable walls in random position
		for (int k = 0; k < 6; k++) {
			Position pos = Position.randomPosition((y, x) -> maze[y][x] != null || (y < 2 && x < 2));
			maze[pos.getY()][pos.getX()] = new IndestructibleCell();
		}		
		return maze;
	}
	
	/**
	 * Matrix getter.
	 * @return matrix of cells.
	 */
	public Cell[][] getMatrix() {
		return maze;
	}
	
	/**
	 * This method selects recursively reachable cells.
	 * @param tmpMaze the temporary labyrinth to check.
	 * @param x coordinate x of the cell to check.
	 * @param y coordinate y of the cell to check.
	 * @param changed Boolean flag if the maze has changed or not. 
	 * @return a maze without unreachable areas.
	 */
	private static int[][] controlInit(int[][] tmpMaze, int x, int y, boolean changed ){
		if (!changed) 
			return tmpMaze;
		
		tmpMaze[y][x] = 1;
		if(y>0 && tmpMaze[y-1][x]==0) {
			changed=true;
			tmpMaze = controlInit(tmpMaze, x, y-1, changed);
		}
		if(x>0 && tmpMaze[y][x-1]==0) {
			changed=true;
			tmpMaze = controlInit(tmpMaze, x-1, y, changed);
		}
		if(y < height - 1 && tmpMaze[y+1][x]==0) {
			changed=true;
			tmpMaze = controlInit(tmpMaze, x, y+1, changed);
		}
		if(x < width - 1 && tmpMaze[y][x+1]==0) {
			changed=true;
			tmpMaze = controlInit(tmpMaze, x+1, y, changed);
		}
		return controlInit(tmpMaze, x, y, false);
	}
	
	/**
	 * This method loads the destructible walls in the labyrinth.
	 * @param maxWall number of walls to load.
	 */
	private void loadWall(int maxWall) {		
		Random r = new Random();
		int nWall = maxWall == 0 ? 0 : r.nextInt(maxWall/2, maxWall);
		Position pos = new Position(0, 0); 
		for(int k = 0; k < nWall; k++) {
			pos = Position.randomPosition((y, x) -> maze[y][x] != null || (y<2 && x<2));
			maze[pos.getY()][pos.getX()] = new DestructibleCell();
		}
	}
	
	/**
	 * This method place the floors in the maze.
	 */
	private void loadFloor() {
		for (int i = 0; i < height; i++) 
			for (int j = 0; j < width; j++)
				if(maze[i][j] == null)
					maze[i][j] = new Floor();			
	}
	
	/**
	 * This method place the gate in the labyrinth.
	 */
	public void setGate() {
		Position pos = Position.randomPosition((y, x) -> !(maze[y][x] instanceof DestructibleCell));
		setGate(pos);
	}
	
	/**
	 * Gate setter.
	 * @param pos the position where the gate will be put.
	 */
	public void setGate(Position pos) {
		gate = new Gate(pos.getY(), pos.getX());
	}
	
	/**
	 * This method return the cell in the position given as parameter.
	 * @param pos the position of the cell.
	 * @return the cell.
	 */
	public Cell getCell(Position pos) {
		return maze[pos.getY()][pos.getX()];
	}

	/**
	 * This method save the state of the Maze in a ObjectOutputStream, which will then be saved in a file.
	 * @param oos ObjectOutputStream stream to save on the file.
	 */
	public void writeObject(ObjectOutputStream oos) {
		try{
			//Write cells
			for (int i = 0; i < maze.length; i++)
				for (int j = 0; j < maze[0].length; j++)
					oos.writeObject(maze[i][j]);
            //Write gate
			gate.writeObject(oos);
        }
        catch(Exception e){
            System.out.println("Error while saving the maze");
            e.printStackTrace();
        }
	}
	
	/**
	 * This method loads a Maze reading its attribute from ObjectInputStream.
	 * @param ois ObjectInputStream stream to read from file.
	 * @return a Maze.
	 */
	public static Maze loadObject(ObjectInputStream ois){
        Maze m = new Maze(0);
        try{
			for (int i = 0; i < m.maze.length; i++)
				for (int j = 0; j < m.maze[0].length; j++)
					m.maze[i][j] = (Cell)ois.readObject();
            m.gate = Gate.loadObject(ois);
        }
        catch(Exception e){
        	System.out.println("Error while loading the maze");
            e.printStackTrace();
        }
        return m;
    }
}


