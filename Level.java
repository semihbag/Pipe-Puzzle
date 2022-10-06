import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.VLineTo;

public class Level implements Cloneable{
	private Cell[][] board ;
	private Path_Direction pathDirection;
	private Path path = new Path();
	private boolean bool = true;

	private int xS;
	private int yS;
	private int xC;
	private int yC;
	private int xE;
	private int yE;
	
	public boolean isLevelCompleted() {
		// Find start index
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
				if (this.getBoard()[i][j].isStarter()) {
					if (this.getBoard()[i][j].getProperty().equals("Vertical")) {
						this.setPathDirection(Path_Direction.DOWN);
						this.getBoard()[i][j].setOutGateDirection(Cell_Direction.SOUTH);
						this.getBoard()[i][j].setHasOutGate(true);
						this.setxS(i);
						this.setxC(i);
						this.setyS(j);
						this.setyC(j);
						break;
					}
					else if(this.getBoard()[i][j].getProperty().equals("Horizontal")) {
						this.setPathDirection(Path_Direction.LEFT);
						this.getBoard()[i][j].setOutGateDirection(Cell_Direction.WEST);
						this.getBoard()[i][j].setHasOutGate(true);
						this.setxS(i);
						this.setxC(i);
						this.setyS(j);
						this.setyC(j);
						break;
					}
				}
			}
		}
		
		// Find end index
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
				if (this.getBoard()[i][j].isFinisher()) {
					this.setxE(i);
					this.setyE(j);
					break;
				}
			}
		}
		
		
		try {
			while (true) {
				if (!(nextStep(xC, yC, this.getPathDirection()))) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (this.getBoard()[this.getxC()][this.getyC()].isFinisher()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	
	//is there next step ? if there return true
	public boolean nextStep(int xC, int yC, Path_Direction pathDirection) {
		boolean bool = false;
		switch (pathDirection) {
			case UP:
				if (isNextCoordinateLegal(xC-1, yC)) {
					if (this.getBoard()[xC-1][yC].isHasSouthGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.NORTH)) {
								this.getBoard()[xC-1][yC].setGates(pathDirection);
								if (this.getBoard()[xC-1][yC].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC-1][yC].getOutGateDirection());
									bool = true;
								}
								else {
									bool = false;
								}
								setxC(xC-1);
								setyC(yC);
							}
							else {
								bool = false;
							}
						}
						else {
							bool = false;
						}
					}
					else {
						bool = false;
					}
				}
				else {
					bool = false;
				}
				break;
				
			case RIGHT:
				if (isNextCoordinateLegal(xC, yC+1)) {
					if (this.getBoard()[xC][yC+1].isHasWestGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.EAST)) {
								this.getBoard()[xC][yC+1].setGates(pathDirection);
								if (this.getBoard()[xC][yC+1].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC][yC+1].getOutGateDirection());
									bool = true;
								}
								else {
									bool = false;
								}
								setxC(xC);
								setyC(yC+1);
							}
							else {
								bool = false;
							}
						}
						else {
							bool = false;
						}
					}
					else {
						bool = false;
					}
				}
				else {
					bool = false;
				}
				break;
				
			case DOWN:
				if (isNextCoordinateLegal(xC+1, yC)) {
					if (this.getBoard()[xC+1][yC].isHasNorthGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.SOUTH)) {
								this.getBoard()[xC+1][yC].setGates(pathDirection);
								if (this.getBoard()[xC+1][yC].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC+1][yC].getOutGateDirection());
									bool = true;
								}
								else {
									bool = false;
								}
								setxC(xC+1);
								setyC(yC);
							}
							else {
								bool = false;
							}
						}
						else {
							bool = false;
						}
					}
					else {
						bool = false;
					}
				}
				else {
					bool = false;
				}
				
				break;
			case LEFT:
				if (isNextCoordinateLegal(xC, yC-1)) {
					if (this.getBoard()[xC][yC-1].isHasEastGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.WEST)) {
								this.getBoard()[xC][yC-1].setGates(pathDirection);
								if (this.getBoard()[xC][yC-1].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC][yC-1].getOutGateDirection());
									bool = true;	
								}
								else {
									bool = false;
								}
								setxC(xC);
								setyC(yC-1);
							}
							else {
								bool = false;
							}
						}
						else {
							bool = false;
						}
					}
					else {
						bool = false;
					}
				}
				else {
					bool = false;
				}
				break;
		}
		
		return bool;
	}
	
	
	
	
	
	
	public void createPath() {
		this.setPath(new Path());
		// Find start index
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
				if (this.getBoard()[i][j].isStarter()) {
					if (this.getBoard()[i][j].getProperty().equals("Vertical")) {
						this.setPathDirection(Path_Direction.DOWN);
						this.getBoard()[i][j].setOutGateDirection(Cell_Direction.SOUTH);
						this.getBoard()[i][j].setHasOutGate(true);
						this.setxS(i);
						this.setxC(i);
						this.setyS(j);
						this.setyC(j);
						this.getPath().getElements().add(new MoveTo(yS*160+27,xS*160+20));
				        this.getPath().getElements().add(new VLineTo(160*xS+120));	
				        
						break;
					}
					else if(this.getBoard()[i][j].getProperty().equals("Horizontal")) {
						this.setPathDirection(Path_Direction.LEFT);
						this.getBoard()[i][j].setOutGateDirection(Cell_Direction.WEST);
						this.getBoard()[i][j].setHasOutGate(true);
						this.setxS(i);
						this.setxC(i);
						this.setyS(j);
						this.setyC(j);
						this.getPath().getElements().add(new MoveTo(yS*160+55,xS*160+55));
				        this.getPath().getElements().add(new HLineTo(yS*160-27));
				        
						break;
					}
			        System.out.println(xS);
			        System.out.println(yS);

				}
			}
		}
		
		try {
			while (true) {
				if (!(nextStepForPath(xC, yC, this.getPathDirection()))) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		// Find end index
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
				if (this.getBoard()[i][j].isFinisher()) {
					this.setxE(i);
					this.setyE(j);
					if (this.getBoard()[i][j].getProperty().equals("Vertical")) {
				        this.getPath().getElements().add(new VLineTo(xC*160+20));
						break;
					}
					else if(this.getBoard()[i][j].getProperty().equals("Horizontal")) {
				        this.getPath().getElements().add(new HLineTo(yC*160+50));
						break;
					}
				}
			}
		}
	}
	
	
	
	
	
	//is there next step ? if there return true
	public boolean nextStepForPath(int xC, int yC, Path_Direction pathDirection) {
		boolean bool = false;
		switch (pathDirection) {
			case UP:
				if (isNextCoordinateLegal(xC-1, yC)) {
					if (this.getBoard()[xC-1][yC].isHasSouthGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.NORTH)) {
								this.getBoard()[xC-1][yC].setGates(pathDirection);
								if (this.getBoard()[xC-1][yC].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC-1][yC].getOutGateDirection());
									this.getBoard()[xC-1][yC].setPathPieces(xC-1, yC, pathDirection);
									this.getPath().getElements().add(this.getBoard()[xC-1][yC].getPathPiece());
									bool = true;
								}
								setxC(xC-1);
								setyC(yC);
							}
						}
					}
				}
			
				break;
				
			case RIGHT:
				if (isNextCoordinateLegal(xC, yC+1)) {
					if (this.getBoard()[xC][yC+1].isHasWestGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.EAST)) {
								this.getBoard()[xC][yC+1].setGates(pathDirection);
								if (this.getBoard()[xC][yC+1].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC][yC+1].getOutGateDirection());
									this.getBoard()[xC][yC+1].setPathPieces(xC, yC+1, pathDirection);
									this.getPath().getElements().add(this.getBoard()[xC][yC+1].getPathPiece());
									bool = true;
								}
								setxC(xC);
								setyC(yC+1);
							}
						}
					}
				}

				break;
				
			case DOWN:
				if (isNextCoordinateLegal(xC+1, yC)) {
					if (this.getBoard()[xC+1][yC].isHasNorthGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.SOUTH)) {
								this.getBoard()[xC+1][yC].setGates(pathDirection);
								if (this.getBoard()[xC+1][yC].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC+1][yC].getOutGateDirection());
									this.getBoard()[xC+1][yC].setPathPieces(xC+1, yC, pathDirection);
									this.getPath().getElements().add(this.getBoard()[xC+1][yC].getPathPiece());
									bool = true;
								}
								setxC(xC+1);
								setyC(yC);
							}
						}
					}
				}
				
				break;
			case LEFT:
				if (isNextCoordinateLegal(xC, yC-1)) {
					if (this.getBoard()[xC][yC-1].isHasEastGate()) {
						if (this.getBoard()[xC][yC].isHasOutGate()) {
							if (this.getBoard()[xC][yC].getOutGateDirection().equals(Cell_Direction.WEST)) {
								this.getBoard()[xC][yC-1].setGates(pathDirection);
								if (this.getBoard()[xC][yC-1].isHasOutGate()) {
									findNewPathDirection(this.getBoard()[xC][yC-1].getOutGateDirection());
									this.getBoard()[xC][yC-1].setPathPieces(xC, yC-1, pathDirection);
									this.getPath().getElements().add(this.getBoard()[xC][yC-1].getPathPiece());
									bool = true;	
								}
								setxC(xC);
								setyC(yC-1);
							}
						}
					}
				}
				
				break;
		}
		
		return bool;
	}
	
	
	
	
	
	
	
	public boolean isNextCoordinateLegal(int x, int y) {
		if(((x > -1)&&(x < 4)) && ((y > -1)&&(y <4))) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void findNewPathDirection(Cell_Direction cellDirection) {
		
		switch (cellDirection) {
			case NORTH:
				this.setPathDirection(Path_Direction.UP);
				break;
			case EAST:
				this.setPathDirection(Path_Direction.RIGHT);
				break;
			case SOUTH:
				this.setPathDirection(Path_Direction.DOWN);
				break;
			case WEST:
				this.setPathDirection(Path_Direction.LEFT);
				break;
		}
	}
	
	public Level clone() {
		Level level = new Level();
		Cell[][] temp = new Cell[4][4];
		
		for (int i = 0 ; i < 4 ; i++) {
			for (int j = 0 ; j < 4 ; j++) {
				temp[i][j] = board[i][j];
			}
		}
		level.setBoard(temp);
		level.setPathDirection(pathDirection);
		return level;
	}
	
	
	
	//////////////////////////
	//////////////////////////
	public Cell[][] getBoard() {
		return board;
	}
	
	public void setBoard(Cell[][] board) {
		this.board = board;
	}
	
	public Path_Direction getPathDirection() {
		return pathDirection;
	}

	public void setPathDirection(Path_Direction pathDirection) {
		this.pathDirection = pathDirection;
	}

	public int getxS() {
		return xS;
	}

	public void setxS(int xS) {
		this.xS = xS;
	}

	public int getyS() {
		return yS;
	}

	public void setyS(int yS) {
		this.yS = yS;
	}

	public int getxC() {
		return xC;
	}

	public void setxC(int xC) {
		this.xC = xC;
	}

	public int getyC() {
		return yC;
	}

	public void setyC(int yC) {
		this.yC = yC;
	}

	public int getxE() {
		return xE;
	}

	public void setxE(int xE) {
		this.xE = xE;
	}

	public int getyE() {
		return yE;
	}

	public void setyE(int yE) {
		this.yE = yE;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
}
