package Control;


import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;
import Model.Cell;
import Model.Clown;
import Model.Floor;
import View.Bomb;
import View.Boss;
import View.DestructibleCell;
import View.Enemy;
import View.ExplosionCell;
import View.ExplosionCellC;
import View.ExplosionCellO;
import View.ExplosionCellV;
import View.GameData;
import View.Gate;
import View.MazeWindow;
import View.PowerUpCell;
import View.Shoot;
import View.Window;
import Model.Position;
import Model.PowerUp;
import Model.Game;
import Model.Maze;

/**
 * This class represents the Manager for the windows that paint the real game.
 * @author Raf&Vinz
 *
 */
public class MazeManager {
	public HashMap<Model.Enemy, Enemy> enemies; 
	public HashMap<Model.Bomb, Bomb> bombs; 
	public HashMap<ExplosionCell, Integer> explosions; 
	private HashMap<Position, DestructibleCell> dc; 
	private HashMap<PowerUp, PowerUpCell> powerups; 
	private ArrayList<Shoot> shoots; 
	private Gate gate;
	private MazeWindow mazeWindow;
	
	public Timer timer = new Timer(25, new ActionListener() {
		private double dSeconds = 364 - Manager.game.getTime();
		private int deathTimer;
		private boolean tutorialRead;

		@Override
		public void actionPerformed(ActionEvent e) {
			dSeconds += 0.25;
			
			if (dSeconds == 0.25) {
				if (mazeWindow.getTutorial() == null) {
					JLabel tutorial = new JLabel(new ImageIcon("img/DesignElements/Tutorial.jpg"));
					tutorial.setBounds(100, 100, 600, 530);
					
					JLabel playButton = new JLabel(new ImageIcon("img/DesignElements/PlayButton.png"));
					playButton.setBounds(100, 630, 600, 70);
					playButton.addMouseListener(new MouseAdapter() {

						@Override
			            public void mouseClicked(MouseEvent e) {
							tutorialRead = true;
							mazeWindow.remove(tutorial);
							mazeWindow.remove(playButton);
							mazeWindow.getMoveController().setPaused(false);
							
			            }
						
						@Override
						public void mouseEntered(MouseEvent e) {
							playButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
						}

					});
					
					mazeWindow.setTutorial(tutorial, playButton);
					mazeWindow.getMoveController().setPaused(true);
				}
				if (!tutorialRead)
					dSeconds = 0;
			}
			else {
				if (Manager.bm.getLives() <= 0 || Manager.game.getTime() <= 0) {
					Manager.game.setGame();
					Manager.window.getWindowManager().setWindow(Window.lost);
				}
				else if (deathTimer > 0) {
					deathTimer++;
					if (enemies.keySet().size() == 1 && enemies.keySet().stream().findFirst().get() instanceof Model.Boss && enemies.keySet().stream().findFirst().get().getHp() == -3) {
						if (deathTimer == 2) 
							gate = new View.Gate(mazeWindow, convertMapVw(MazeWindow.xMargin, Manager.game.getMaze().getGate().getX(), MazeWindow.cellSize), 
								convertMapVw(MazeWindow.yMargin, Manager.game.getMaze().getGate().getY(), MazeWindow.cellSize));
						if (shoots.size() > 0)
							for (Shoot shoot: shoots) 
								mazeWindow.remove(shoot);
						((Boss)enemies.get(enemies.keySet().stream().findFirst().get())).die();
					}
					if (Manager.bm.getHp() == 0) {
						mazeWindow.getMoveController().setPaused(true);
						mazeWindow.getBomberMan().die();
						if (deathTimer == 10) {
							mazeWindow.getBomberMan().die();
							Manager.bm.die();
							mazeWindow.getMoveController().setPaused(false);
							reset();
						}
					}
					if (deathTimer == 10) {
						deathTimer = 0;
					}
					mazeWindow.getGameBar().reload(Manager.bm.getLives(), Manager.game.getScore());
					mazeWindow.sortComponents();
					mazeWindow.revalidate();
					mazeWindow.repaint();
				}
				else {
					Manager.bm.updatePosition(null, Manager.game);
					if (dSeconds%10 == 0) {
						Manager.game.passTime();
						mazeWindow.getGameBar().paintTime(Manager.game.getTime());
						mazeWindow.getGameBar().chooseHand();
					}
					
					if (dSeconds%5 == 0) {
						for (PowerUp pu: powerups.keySet())
							powerups.get(pu).animate();
						if (gate != null && Manager.game.getMaze().getGate().isOpened())
							gate.animate();
					}
					
					if (dSeconds%1 == 0) {
						if (!Manager.bm.isArmor() && Manager.game.controlExplosion(Manager.bm)) {
							deathTimer = 1;
							Manager.bm.setHp(0);
							Manager.window.getWindowManager().stopAudio();
							Manager.window.getWindowManager().playSoundEffect("audio/death-sound.wav");
						}
						
						Manager.bm.decrArmor(100);
						
						String path = "img/Bomberman/";
						if (Manager.bm.isArmor() && !(mazeWindow.getBomberMan().getPath().contains("White"))) {
							path += "White/";
						}
						mazeWindow.getBomberMan().setPath(path + Manager.bm.getLastMove() + ".png");
						mazeWindow.getBomberMan().rePaint();

						
						
						HashMap<Model.Bomb, Bomb> newBombs = new HashMap<>();
						for (Model.Bomb bomb: bombs.keySet()) {
							bomb.deltaTime(-100);
							if (bomb.getTimer() == 0) {
								Manager.bm.addBomb();
								Bomb b = bombs.get(bomb);
								HashMap<String, List<Position>> cells = Manager.game.insertExplosion(
										convertVwMap(MazeWindow.xMargin, b.getX(), MazeWindow.cellSize),
										convertVwMap(MazeWindow.yMargin, b.getY(), MazeWindow.cellSize));
								b.explode();
								paintExp(cells);
							}
							else
								newBombs.put(bomb, bombs.get(bomb));
						}
						bombs = newBombs;
						
						for (Position p: dc.keySet()) 
							dc.get(p).animate();
						
						HashMap<ExplosionCell, Integer> newExp = new HashMap<>();
						for (ExplosionCell exp: explosions.keySet()) {
							int i = explosions.get(exp) - 100;
							if (i > 0)
								newExp.put(exp.animate(), i);
							else {
								mazeWindow.remove(exp);
								exp.explode();
								Manager.game.restoreCell(convertVwMap(MazeWindow.xMargin, exp.getX(), MazeWindow.cellSize),
														convertVwMap(MazeWindow.yMargin, exp.getY(), MazeWindow.cellSize));
							}
						}
						explosions = newExp;
						
						HashMap<PowerUp, PowerUpCell> newPU = new HashMap<>();
						for (PowerUp pu: powerups.keySet()) {
							if (pu.isVisible())
								newPU.put(pu, powerups.get(pu));
							else {
								mazeWindow.remove(powerups.get(pu));
							}
						}
						powerups = newPU;
					
						if (enemies.keySet().size() == 0)
							Manager.game.getMaze().getGate().setOpened(true);
						for (int i = 0; i < Manager.game.getEnemies().size(); i++) {
							Model.Enemy en = Manager.game.getEnemies().get(i);
							if (en.hitBomberman(Manager.game) && !Manager.bm.isArmor()) {
								deathTimer = 1;
								Manager.bm.setHp(0);
								Manager.window.getWindowManager().stopAudio();
								Manager.window.getWindowManager().playSoundEffect("audio/death-sound.wav");
							}
							if (en.getHp() == -3) {
								Manager.game.incrScore(en.getKillScore());
								mazeWindow.remove(enemies.get(en));
								enemies.remove(en);
								PowerUp pu = en.getPowerup();
								if (pu != null) {
									Manager.game.getMaze().getMatrix()[en.getY()/Maze.cellSide][en.getX()/Maze.cellSide] = new Floor(pu);							
									powerups.put(pu, new PowerUpCell(
											mazeWindow, pu.getClass().getSimpleName(), 
											convertMapVw(MazeWindow.xMargin, en.getX()/Maze.cellSide, MazeWindow.cellSize), 
											convertMapVw(MazeWindow.yMargin, en.getY()/Maze.cellSide, MazeWindow.cellSize)));
								}
								Manager.game.getEnemies().remove(en);
							}				
							else if (Manager.game.controlExplosion(en)) {
								en.setHp(en.getHp() - 50);
								if (en instanceof Model.Boss)
									Manager.window.getWindowManager().playSoundEffect("audio/bosspain-sound.wav");
								else
									Manager.window.getWindowManager().playSoundEffect("audio/enemypain-sound.wav");
								if (en.getHp() <= 0) {
									en.setHp(-3);
									if (en instanceof Model.Boss) {
										deathTimer = 1;
										Manager.window.getWindowManager().playSoundEffect("audio/bossdeath-sound.wav");
									}
								}
							}
							en.updatePosition(Manager.game);
						}
						paintEnemies();
					}
				}
			}
			mazeWindow.getGameBar().reload(Manager.bm.getLives(), Manager.game.getScore());
			mazeWindow.sortComponents();
			mazeWindow.revalidate();
			mazeWindow.repaint();
		}
	});
	
	/**
	 * This is the class builder.
	 * @param mazeWindow window that paint the real game.
	 */
	public MazeManager(MazeWindow mazeWindow) {
		this.mazeWindow = mazeWindow;
		enemies = new HashMap<>(); 
		bombs = new HashMap<>(); 
		explosions = new HashMap<>(); 
		dc = new HashMap<>(); 
		powerups = new HashMap<>(); 
		shoots = new ArrayList<>(); 
		
		GameData gameBar = new GameData(mazeWindow, Manager.game.getTime(), Manager.bm.getLives(), Manager.game.getScore());
        mazeWindow.setGameBar(gameBar);
		mazeWindow.add(gameBar);
		timer.start();

	}

	public void paintMaze(String path) {
		Cell cell;
		
		for(int i = 0; i < 11; i++) {
        	for(int j = 0; j < 13; j++) {
        		//Caso in cui siamo sul Bordo superiore del Labirinto
        		cell = Manager.game.getMaze().getMatrix()[i][j];
        		if (i == 0 && cell instanceof Model.Floor)
        			new View.Floor(mazeWindow, path + "/floorUp.png", convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize));
        		else if (cell instanceof Model.IndestructibleCell) 
        			new View.IndestructibleCell(mazeWindow, path, convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize));
        		else if (cell instanceof Model.DestructibleCell) 
        			dc.put(new Position(i, j), new DestructibleCell(mazeWindow, path, convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize)));
        		else 
        			new View.Floor(mazeWindow, path + "/floor.png", convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize));
        	}
        }
        
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 13; j++) {
				cell = Manager.game.getMaze().getMatrix()[i][j];
				if (!(cell instanceof Model.Floor)) {
					if (i == 0)
	        			new View.Floor(mazeWindow, path + "/floorUp.png", convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize));
					else
	        			new View.Floor(mazeWindow, path + "/floor.png", convertMapVw(MazeWindow.xMargin, j, MazeWindow.cellSize), convertMapVw(MazeWindow.yMargin, i, MazeWindow.cellSize));
				}
			}
		}

	}
	
	public void paintEnemies() {
		
		for (Model.Enemy e: Manager.game.getEnemies()) {
			if (enemies.size() < Manager.game.getEnemies().size()) {
				Enemy enemy;
				if (e instanceof Model.Boss) {
					enemy = new Boss(mazeWindow, convertMapVw(MazeWindow.xMargin, e.getX(), 6), 
        					convertMapVw(MazeWindow.yMargin, e.getY(), 6) + MazeWindow.cellSize - 189, 
							e.getClass().getSimpleName());
				}
	        	else {
	        		enemy = new Enemy(mazeWindow, convertMapVw(MazeWindow.xMargin, e.getX(), 6), 
	        					convertMapVw(MazeWindow.yMargin, e.getY(), 6) + MazeWindow.cellSize, 
								e.getClass().getSimpleName());
	        	}
				enemies.put(e, enemy);
			}
	        else {
	    		Enemy enemy = enemies.get(e);
	    		int i = enemy.incrTimer();
    			String path;
	    		if (e instanceof Model.Boss) {
		    		path = e.getLastMove() == null ? enemy.getPath().substring(0, enemy.getPath().lastIndexOf("/") + 1) + "Hit" + i + ".png" : enemy.getPath().substring(0, enemy.getPath().lastIndexOf("/") + 1) + "0.png";
		    		enemy.setPath(path);
		    		enemy.paint(convertMapVw(MazeWindow.xMargin, e.getX(), 6), 
	    					convertMapVw(MazeWindow.yMargin, e.getY(), 6) + MazeWindow.cellSize - 189);
		    		if (e instanceof Clown) {
						Clown c = (Clown)e;
						Position[] ss = c.getShoots();
						if (ss[0] != null) {
							if (shoots.size() == 0) { 
								Manager.window.getWindowManager().playSoundEffect("audio/energyball-sound.wav");
								for (int k = 0; k < 8; k++) 
									shoots.add(new Shoot(convertMapVw(MazeWindow.xMargin, ss[k].getX(), 6), 
												convertMapVw(MazeWindow.yMargin, ss[k].getY(), 6), 
												mazeWindow));
							}
							else {
								for (int k = 0; k < 8; k++) 
									shoots.get(k).paint(convertMapVw(MazeWindow.xMargin, ss[k].getX(), 6), 
												convertMapVw(MazeWindow.yMargin, ss[k].getY(), 6));		
							}
						}
						else
							if (shoots.size() > 0) {
								for (Shoot s: shoots)
									mazeWindow.remove(s);
								shoots = new ArrayList<>();
							}
					}
		    		else {
		    			String p = enemy.getPath();
		    			if(p.substring(p.length() - 8, p.length() - 5).equals("Hit")) 
		    				Manager.window.getWindowManager().playSoundEffect("audio/hit-sound.wav");
		    		}
	    		}
	    		else {
		    		path = enemy.getPath().substring(0, enemy.getPath().lastIndexOf("/", enemy.getPath().length() - 8) + 1) + e.getLastMove() + "/" + i + ".png";
		    		enemy.setPath(path);
		    		enemy.paint(convertMapVw(MazeWindow.xMargin, e.getX(), 6), 
		    					convertMapVw(MazeWindow.yMargin, e.getY(), 6) + MazeWindow.cellSize);
	    		}
	        }
		}
	}
	
	public void update(Observable o, Object arg) {
		if (arg != null) {
			if (o instanceof Model.Bomberman) {
				Position pos = (Position)arg;
				String path = mazeWindow.getBomberMan().getPath();
				path = path.substring(0, path.lastIndexOf("/", path.length()) + 1);
		        path += Manager.bm.getLastMove().toString() + ".png";
				mazeWindow.getBomberMan().setPath(path);
				mazeWindow.getBomberMan().paint(convertMapVw(MazeWindow.xMargin, pos.getX(), 6),
							convertMapVw(MazeWindow.yMargin, pos.getY(), 6) - 50);
			}
			else if (o instanceof Game) {
				if (arg instanceof Model.Bomb)
					paintBomb((Model.Bomb)arg);
				else if (arg instanceof Game) {
					if (Manager.game.getLevel() == 9) {
						Manager.user.gameWon(Manager.game);
						Manager.game.setGame();
						Manager.window.getWindowManager().setWindow(Window.won);
					}
					else {
						newLevel();
						Manager.window.getWindowManager().stopAudio();
						playAudio();
					}
				}
			}
		}
	}
	
	private void newLevel() {
		reset();
		for (Component c: mazeWindow.getComponents())
			if (c instanceof View.Cell)
				mazeWindow.remove(c);
		paintMaze("img/World" + Manager.game.getWorld());
		paintEnemies();
		timer.restart();
		mazeWindow.sortComponents();
		mazeWindow.repaint();
	}

	public void paintBomb(Model.Bomb bomb) {
		Bomb b = new Bomb(mazeWindow);
		bombs.put(bomb, b);
		b.paint(convertMapVw(MazeWindow.xMargin, bomb.getPos().getX(), MazeWindow.cellSize), 
				convertMapVw(MazeWindow.yMargin, bomb.getPos().getY(), MazeWindow.cellSize));
		mazeWindow.sortComponents();
		mazeWindow.repaint();
	}

	public void paintExp(HashMap<String, List<Position>> cells) {
		for (String k: cells.keySet()) {
			for (Position pos: cells.get(k)) {
				Cell c = Manager.game.getMaze().getCell(pos);
				int x = convertMapVw(MazeWindow.xMargin, pos.getX(), MazeWindow.cellSize);
				int y = convertMapVw(MazeWindow.yMargin, pos.getY(), MazeWindow.cellSize);
				if (c instanceof Model.ExplosionCell) {
					ExplosionCell ec = switch (k) {
					case "c" -> new ExplosionCellC(mazeWindow, x, y);
					case "v" -> new ExplosionCellV(mazeWindow, x, y);
					case "o" -> new ExplosionCellO(mazeWindow, x, y);
					default -> throw new IllegalArgumentException("Unexpected value: " + k);
					};
					explosions.put(ec, 1000);
					ec.paint(x, y);
				}
				else if (c instanceof Floor && dc.get(pos) != null) {
					mazeWindow.remove(dc.get(pos));
					dc.remove(pos);
					PowerUp pu = ((Floor) c).getPowerup();
					if (pu != null) 
						powerups.put(pu, new PowerUpCell(
								mazeWindow, pu.getClass().getSimpleName(), 
								convertMapVw(MazeWindow.xMargin, pos.getX(), MazeWindow.cellSize), 
								convertMapVw(MazeWindow.yMargin, pos.getY(), MazeWindow.cellSize)));
					else {
						Model.Gate g = Manager.game.getMaze().getGate();
						if (pos.getX() == g.getX() && pos.getY() == g.getY()) {
							gate = new View.Gate(mazeWindow, convertMapVw(MazeWindow.xMargin, pos.getX(), MazeWindow.cellSize), 
												convertMapVw(MazeWindow.yMargin, pos.getY(), MazeWindow.cellSize));
						}
					}
				}
			}
		}
		mazeWindow.sortComponents();
		mazeWindow.repaint();
	}
	
	public static int convertVwMap(int margin, int pos, int prop) {
		return (pos - margin)/prop;
	}
	
	public static int convertMapVw(int margin, int pos, int prop) {
		return margin + prop*pos;
	}
	
	public void cheatCode() {
		for (Model.Enemy e: enemies.keySet()) {
			mazeWindow.remove(enemies.get(e));
		}
		enemies = new HashMap<>();
		Manager.game.incrLevel();
	}
	
	public void reset() {		
		//reset time
		Manager.game.resetTime();
		
		//restart music
		Manager.window.getWindowManager().stopAudio();
		playAudio();
		
		//reset explosions
		for (ExplosionCell exp: explosions.keySet()) {
			mazeWindow.remove(exp);
			exp.explode();
			Manager.game.restoreCell(convertVwMap(MazeWindow.xMargin, exp.getX(), MazeWindow.cellSize),
									convertVwMap(MazeWindow.yMargin, exp.getY(), MazeWindow.cellSize));
			}
		explosions = new HashMap<>();
		
		//reset bomb
		for (Model.Bomb bomb: bombs.keySet()) {
			Manager.bm.addBomb();
			Bomb b = bombs.get(bomb);
			Manager.game.removeBomb(bomb.getPos().getY(), bomb.getPos().getX());
			b.explode();
		}
		bombs = new HashMap<>();

		
		//reset Bomberman
		mazeWindow.remove(mazeWindow.getBomberMan());
		Manager.bm.setPosition(new Position(0, 0));
		if (Manager.bm.getHp() <= 0) {
			Manager.bm.setHp(1);
			Manager.bm.setArmor(5100);
		}
		mazeWindow.getBomberMan().paint(convertMapVw(MazeWindow.xMargin, 0, 6),
										convertMapVw(MazeWindow.yMargin, 0, 6) - 50);
		mazeWindow.add(mazeWindow.getBomberMan());
		
		//reset gamebar
		mazeWindow.getGameBar().reload(Manager.bm.getLives(), Manager.game.getScore());
		mazeWindow.sortComponents();
		mazeWindow.revalidate();
		mazeWindow.repaint();
	}

	public void stop() {
		timer.stop();
	}

	public void moveToPosition(Position pos) {
		Manager.bm.moveToPosition(pos);
	}
	
	public void playAudio() {
		if (Manager.game.getLevel()%4 == 0)
			Manager.window.getWindowManager().playAudio("audio/boss-tone.wav");
		else
			Manager.window.getWindowManager().playAudio("audio/maze-tone.wav");
	}
	
	public void pause() {
		stop();
		
		JLabel pauseWindow = new JLabel(new ImageIcon("img/DesignElements/Pause.png"));
		pauseWindow.setBounds(100, 100, 600, 600);
		
		JLabel saveButton = new JLabel(new ImageIcon("img/DesignElements/Save.png"));
		saveButton.setBounds(250, 450, 300, 50);

		JLabel resumeButton = new JLabel(new ImageIcon("img/DesignElements/Resume.png"));
		resumeButton.setBounds(250, 525, 300, 50);

		JLabel exitButton = new JLabel(new ImageIcon("img/DesignElements/Exit.png"));
		exitButton.setBounds(250, 600, 300, 50);

		saveButton.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				Manager.game.saveGame();						
			}
				
			@Override
			public void mouseEntered(MouseEvent e) {
				saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
	
		});
	
		resumeButton.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				mazeWindow.remove(pauseWindow);
				mazeWindow.remove(saveButton);
				mazeWindow.remove(resumeButton);
				mazeWindow.remove(exitButton);
				mazeWindow.getMoveController().setPaused(false);
				timer.restart();
		    }
			
			@Override
			public void mouseEntered(MouseEvent e) {
				resumeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		
		exitButton.addMouseListener(new MouseAdapter() {
	
			@Override
			public void mouseClicked(MouseEvent e) {
				Manager.window.getWindowManager().setWindow(Window.menu);
		    }
			
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});

		mazeWindow.setPause(pauseWindow, saveButton, resumeButton, exitButton);
		mazeWindow.sortComponents();
		mazeWindow.repaint();
		mazeWindow.getMoveController().setPaused(true);
	}
}
