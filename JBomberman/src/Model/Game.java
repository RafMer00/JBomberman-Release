package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Game represents a single game of JBomberman.
 * @author Raf&Vinz
 *
 */
public class Game extends Observable implements Serializable{
	private static final long serialVersionUID = 1L;
	private int time;
	private Bomberman bm;
	private Maze maze;
	private int level;
	private List<Enemy> enemies;
	private int score;
	private static final int[] levelNPowerUps = {2, 3, 3, 0, 2, 3, 3, 0};
	
	/**
	 * This is the class builder which set the game in its initial state.
	 */
	public Game() {
		setGame();
	}
	
	/**
	 * Getter of the score.
	 * @return score of the game.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Set the game in its initial state.
	 */
	public void setGame() {
		time = 364;
		bm = new Bomberman();
		maze = new Maze(40);
		maze.setGate();
		level = 1;
		enemies = addEnemies();
		score = 0;
		addPowerUp();
	}
	
	/**
	 * Add enemies based on the level.
	 * @return the list of enemies.
	 */
	private List<Enemy> addEnemies() {
		enemies = new ArrayList<>();
		switch(level) {
		case 1: addEnemy("Propeller"); 
				addEnemy("Propeller"); break;
				
		case 2: addEnemy("Propeller"); 
				addEnemy("Propeller"); 
				addEnemy("Propeller"); 
				break;

		case 3: addEnemy("Propeller"); 
				addEnemy("Propeller"); 
				addEnemy("Propeller"); 
				addEnemy("Bulb"); 
				addEnemy("Bulb"); break;
		
		case 4: addBoss("BigBomb"); break;
		
		case 5: addEnemy("Ant"); 
				addEnemy("Ant"); 
				addEnemy("Ant");
				addEnemy("Ant");
				addEnemy("Ant"); 
				addEnemy("Ant");break;
				
		case 6: addEnemy("Bulb"); 
				addEnemy("Bulb"); 
				addEnemy("EvilBomb"); 
				addEnemy("EvilBomb"); 
				addEnemy("EvilBomb"); break;
		
		case 7: addEnemy("BombEater"); 
				addEnemy("BombEater"); 
				addEnemy("EvilBomb"); 
				addEnemy("EvilBomb"); 
				addEnemy("EvilBomb"); break;
		
		case 8: addBoss("Clown");
		}
		return enemies;
	}
	
	/**
	 * Add the boss.
	 * @param bossName name of the boss class.
	 */
	private void addBoss(String bossName) {
		Position pos = new Position(4, 4);
		int y = pos.getY()*Maze.cellSide;
		int x = pos.getX()*Maze.cellSide;
		
		enemies.add(switch (bossName) {
		case "BigBomb" -> new BigBomb(y, x);
		case "Clown" -> new Clown(y, x);
		default -> null;
		});
		
	}
	
	/**
	 * Add the enemy in the list of enemies of the game.
	 * @param enemyName name of the enemy class.
	 */
	private void addEnemy(String enemyName) {
		Position pos = Position.randomPosition((y, x) -> (!(maze.getMatrix()[y][x] instanceof Floor) || (y < 5 && x < 5)));
		int y = pos.getY()*Maze.cellSide;
		int x = pos.getX()*Maze.cellSide;
		
		enemies.add(switch (enemyName) {
		case "Propeller" -> new Propeller(y, x);
		case "Bulb" -> new Bulb(y, x);
		case "Ant" -> new Ant(y, x);
		case "EvilBomb" -> new EvilBomb(y, x);
		case "BombEater" -> new BombEater(y, x);
		default -> null;
		});
	}
	
	/**
	 * Add the powerups in the level under a wall or a enemy.
	 */
	public void addPowerUp() {
		//If the level is a boss level don't insert any powerup.
		if (enemies.get(0) instanceof Boss) 
			return;
		//Choose an enemy randomly and set its powerup.
		Random r = new Random();
		enemies.get(r.nextInt(0, enemies.size())).setPowerup(PowerUp.randomPowerUp(level));
		
		//Choose a powerup based on level and set it under a wall.
		ArrayList<PowerUp> powerups = choosePowerups();
		for (Enemy e: enemies)
			if (e.getPowerup() != null)
				for(int k = 0; k < powerups.size(); k++) {
	            	Position pos = Position.randomPosition((y, x) -> (!(maze.getMatrix()[y][x] instanceof DestructibleCell)));
	            	DestructibleCell wall = (DestructibleCell)maze.getMatrix()[pos.getY()][pos.getX()];
	            	Gate gate = maze.getGate();
					if(!(pos.getY() == gate.getY() && pos.getX() == gate.getX()) && wall.getPowerup() == null) 
						wall.setPowerup(powerups.get(k));
					else 
						k--;
				}
	}
	
	/**
	 * Choose the powerups to add based on the level.
	 * @return a list of powerups.
	 */
	private ArrayList<PowerUp> choosePowerups() {
		ArrayList<PowerUp> powerups = new ArrayList<>();
		if (level == 1) {
			powerups.add(new ExplosionExpander());
			powerups.add(new ExtraBomb()); 
		}
		else {
			for (int i = 0; i < levelNPowerUps[level - 1]; i++)
				powerups.add(PowerUp.randomPowerUp(level));
		}
		
		return powerups;
	}

	/**
	 * Getter of the time.
	 * @return the time of the game.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Set the timer to 0.
	 */
	public void resetTime() {
		time = 364;
	}
	
	/**
	 * Decrement the timer.
	 */
	public void passTime() {
		time--;
	}
	
	/**
	 * Bomberman's getter.
	 * @return Bomberman.
	 */
	public Bomberman getBm() {
		return bm;
	}
	
	/**
	 * Bomberman's setter. 
	 * @param bm Bomberman.
	 */
	public void setBm(Bomberman bm) {
		this.bm = bm;
	}
	
	/**
	 * Maze getter.
	 * @return maze.
	 */
	public Maze getMaze() {
		return maze;
	}

	/**
	 * Maze setter.
	 * @param maze maze if the game.
	 */
	public void setMaze(Maze maze) {
		this.maze = maze;
	}
	
	/**
	 * Level getter.
	 * @return level.
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Level setter.
	 * @param level level of the game
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * This method increase the level.
	 */
	public void incrLevel() {
		level++;
		enemies = new ArrayList<>();
		if (level < 9) {
			time = 360;
			bm.setPosition(new Position(0, 0));
			if (level % 4 == 0) {
				maze = new Maze(0);
				maze.setGate(new Position(0, 1));
			}
			else {
				maze = new Maze(40);
				maze.setGate();		
			}
			enemies = addEnemies();
			addPowerUp();
		}
		notifyView(this);
	}
	
	/**
	 * This method notify all the observers.
	 * @param obj the object to send to observers.
	 */
	public void notifyView(Object obj) {
		setChanged();
		notifyObservers(obj);
	}
	
	/**
	 * Enemies getter.
	 * @return List of enemy.
	 */
	public List<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Score setter.
	 * @param score score to save.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * This method increase the score based on a parameter deltaScore.
	 * @param deltaScore is the incremental score.
	 */
	public void incrScore(int deltaScore) {
		score += deltaScore;
	}	
	
	/**
	 * This method plant a Bomb on the Bomberman's position.
	 */
	public void placeBomb() {
		int y = (bm.getY() + (bm.getHeight()/2) + 1)/Maze.cellSide;
		int x = (bm.getX() + (bm.getWidth()/2) + 1)/Maze.cellSide;
		if (bm.getBombs().size() > 0) {
			Bomb bomb = bm.getBombs().poll();
			bomb.setPosition(new Position(y, x));
			maze.getMatrix()[y][x] = new BombCell(bomb);
			notifyView(bomb);
		}
	}
	
	/**
	 * This method remove a bomb from a cell of the matrix.
	 * @param y coordinate y in the the maze.
	 * @param x coordinate x in the the maze.
	 */
	public void removeBomb(int y, int x) {
		maze.getMatrix()[y][x] = new Floor();
	}
	
	/**
	 * This method create explosions cells.
	 * @param x coordinate x of the cell with the bomb.
	 * @param y coordinate y of the cell with the bomb.
	 * @return map of cells with the explosion divided based on position relative to the bomb.
	 */
	public HashMap<String, List<Position>> insertExplosion(int x, int y) {
		//Remove bomb
		int range = ((BombCell)maze.getCell(new Position(y, x))).getBomb().getRange();
		removeBomb(y, x);
		
		//Save all the direction where the explosion can go.
		int i = 1;
		List<Direction> dir = new ArrayList<>();
		dir.add(Direction.UP);
		dir.add(Direction.LEFT);
		dir.add(Direction.RIGHT);
		dir.add(Direction.DOWN);
		
		//Initialize three lists, one for every direction.
		HashMap<String, List<Position>> cells = new HashMap<>();
		cells.put("c", List.of(new Position(y, x)));
		maze.getMatrix()[y][x] = new ExplosionCell();		
		cells.put("v", new ArrayList<>());
		cells.put("o", new ArrayList<>());
		
		//While the explosion is not arrived to the limit change cells to explosion cells.
		while (i <= range) {
			for (Direction d: dir) {
				boolean b = false;
				switch(d) {
				case UP : 
					if (y - i >= 0) {
						b = modifyCell(x, y - i); 
						cells.get("v").add(new Position(y - i, x));  
					}
					break;
				case LEFT : 					
					if (x - i >= 0) {
						b = modifyCell(x - i, y); 
						cells.get("o").add(new Position(y, x - i)); 
					}	
					break;
				case DOWN : 
					if (y + i < Maze.height) {
						b = modifyCell(x, y + i); 
						cells.get("v").add(new Position(y + i, x)); 
					}
					break;
				case RIGHT : 
					if (x + i < Maze.width) {
						b = modifyCell(x + i, y); 
						cells.get("o").add(new Position(y, x + i)); 	
					}
					break;
				};
				if (b)
					dir = dir.stream().filter(s -> !s.equals(d)).collect(Collectors.toList());
			}
			i++;
		}
		return cells;
	}
	
	/**
	 * This method change cells after bomb explosion.
	 * @param x coordinate x of the cell.
	 * @param y coordinate y of the cell.
	 * @return true if the cell is changed.
	 */
	private boolean modifyCell(int x, int y){
		Cell c = maze.getMatrix()[y][x];
		if(c instanceof Floor) { 
			maze.getMatrix()[y][x] = new ExplosionCell(); 
			PowerUp pu = ((Floor)c).getPowerup();
			if(pu != null)
				((ExplosionCell)maze.getMatrix()[y][x]).setPowerup(pu);
		}
		if(c instanceof DestructibleCell) {
			DestructibleCell dc = (DestructibleCell)c;
			maze.getMatrix()[y][x] = dc.getPowerup() == null ? new Floor() : new Floor(dc.getPowerup());
			score += 10;
			return true;
		}
		if (c instanceof IndestructibleCell) {
			return true;
		}	
		return false;
	}
	
	/**
	 * This method check if a character is on a explosionCell.
	 * @param c the character.
	 * @return true if the character is on an explosion cell, false otherwise.
	 */
	public boolean controlExplosion(Character c) {
		if (c.getPos().isOutOfRange(c)) 
			return false;
		
		int y0 = (c.getPos().getY());
		int x0 = (c.getPos().getX());
		int y1 = (y0 + c.getHeight() - 1)/Maze.cellSide;
		int x1 = (x0 + c.getWidth() - 1)/Maze.cellSide;
		
		y0 /= Maze.cellSide;
		x0 /= Maze.cellSide;
		
		for (int i = y0; i <= y1; i++) 
			for (int j = x0; j <= x1; j++) 
				if (maze.getMatrix()[i][j] instanceof ExplosionCell)
					return true;
			
		return false;
	}

	/**
	 * This method checks something touched Bomberman.
	 * @param bmPos Bomberman's position.
	 * @param bmWidth Bomberman's width.
	 * @param bmHeight Bomberman's height.
	 * @param obPos obj position.
	 * @param obWidth is object width.
	 * @param obHeight object height.
	 * @return true if something touched Bomberman, false otherwise.
	 */
	public static boolean hitBomberman(Position bmPos, int bmWidth, int bmHeight, Position obPos, int obWidth, int obHeight) {
		//Calculate Bomberman coordinates
		int bmX1 = bmPos.getX() + bmWidth;
		int bmY1 = bmPos.getY() + bmHeight;
		
		//Calculate obstacle coordinates
		int obX1 = obPos.getX() + obWidth;
		int obY1 = obPos.getY() + obHeight;
		
		//If they touch return true.
        return (obPos.getX() <= bmX1 && obX1 >= bmPos.getX()) && (obPos.getY() <= bmY1 && obY1 >= bmPos.getY());	
	}
	
	/**
	 * This method return the world number.
	 * @return world world number.
	 */
	public int getWorld() {
		return level <= 4 ? 1 : 2;
	}
	
	/**
	 * This method restore a cell making it become a floor again.
	 * @param x coordinate x of the cell.
	 * @param y coordinate y of the cell.
	 */
	public void restoreCell(int x, int y) {
		ObjectCell cell = (ObjectCell)maze.getCell(new Position(y, x));
		PowerUp pu = cell.getPowerup();
		if (!(cell instanceof Floor))
			maze.getMatrix()[y][x] = new Floor();
		if (pu != null)
			((Floor)maze.getMatrix()[y][x]).setPowerup(pu);
	}
	
	/**
	 * This method save the state of the game in a file.
	 */
	public void saveGame(){
        ObjectOutputStream oos;
		try{
            oos = new ObjectOutputStream(new FileOutputStream("save/save.txt"));
            //Write time
            oos.writeInt(time);
            //Write BomberMan
            bm.writeObject(oos);
            //Write maze
            maze.writeObject(oos);
            //Write level
            oos.writeInt(level);
            //Write enemies
            oos.writeInt(enemies.size());
            for (int i = 0; i < enemies.size(); i++)
            	enemies.get(i).writeObject(oos);
            //Write score
            oos.writeInt(score);
            oos.close();
        }
        catch(Exception e){
            System.out.println("Error while saving the game");
            e.printStackTrace();
        }
	}

	/**
	 * This method load the game from a save file.
	 * @return game game loaded.
	 */
	public static Game loadGame(){
        Game g = new Game();
        ObjectInputStream ois;
        try{
            ois = new ObjectInputStream(new FileInputStream("save/save.txt"));
            //Load time
            g.time = ois.readInt();
            //Load Bomberman
            g.bm = Bomberman.loadObject(ois);
            //Load maze
            g.maze = Maze.loadObject(ois);
            //Load level
            g.level = ois.readInt();
            //Load enemies
            int n = ois.readInt();
            g.enemies = new ArrayList<>();
            for (int i = 0; i < n; i++)
            	g.enemies.add(Enemy.loadObject(ois));
            //Load score
            g.score = ois.readInt();
            ois.close();
        }
        catch(Exception e){
        	System.out.println("Error while loading the game");
            e.printStackTrace();
        }
        return g;
    }
}
