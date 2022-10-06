import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.PathElement;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;




public class Cell extends Rectangle {
	private int id;
	private String type;
	private String property;
	private Image image;
	private Cell_Direction inGateDirection;
	private Cell_Direction outGateDirection;
	private boolean hasNorthGate = false;
	private boolean hasEastGate = false;
	private boolean hasSouthGate = false;
	private boolean hasWestGate = false;
	private boolean hasInGate = false;
	private boolean hasOutGate = false;
	private boolean canChangeDirection = false;
	private boolean isStarter = false;
	private boolean isFinisher = false;
	private PathElement pathPiece;
	

    //Constructor that takes id type and property values
	public Cell(int id ,String type, String property) {
		super(160,160,Color.WHITE);
		this.setId(id);
		this.setType(type);
		this.setProperty(property);
    //set all variables
		switch (type) {
    //switch case to find the pipe type assigned to the tile and set them
		case "Starter":
			switch (property) {
			//to find out if the assigned pipe type starter is vertical or horizontal
				case "Vertical":
					//to add starter and south gate if vertical and to give starter feature
					this.setStarter(true);
	                this.setHasSouthGate(true);
					break;
				case "Horizontal":
					//to add starter and west gate if horizontal and to give starter feature
					this.setStarter(true);
					this.setHasWestGate(true);
					break;
			}
			break;
			
		case "End":
			switch (property) {
			//to find out if the assigned pipe type end is vertical or horizontal
				case "Vertical":
					//to add finisher and south gate if vertical and to give finisher feature
					this.setFinisher(true);
					this.setHasSouthGate(true);
			break;
				case "Horizontal":
					//to add finisher and west gate if horizontal and to give finisher feature
					this.setFinisher(true);
					this.setHasWestGate(true);
					break;
			}
			break;
			
		case "Empty":
			//to find if the assigned pipe type is empty, free or none
			switch (property) {
				case "none":
                    break;
				case "Free":
                    break;
			}
			break;
			
		case "Pipe":
			switch (property) {
			//If the assigned pipe is "Pipe", to find whether it is vertical, vertical or curve
				case "Vertical":
					//If the assigned pipe is "pipe, vertical", it is used to install the south and north door.
						this.setHasNorthGate(true);
						this.setHasSouthGate(true);
                    break;
				case "Horizontal":
					//if the assigned pipe is "pipe, horizontal" to install the east and west gate
						this.setHasEastGate(true);
						this.setHasWestGate(true);
                    break;
				case "00":
					//if the pipe assigned is "pipe, 00", it is used to install the north and west gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasNorthGate(true);
						this.setHasWestGate(true);
					break;
				case "01":
					//if the pipe assigned is "pipe, 01", it is used to install the north and east gate and indicate that the direction can be changed.

						this.setCanChangeDirection(true);
						this.setHasNorthGate(true);
						this.setHasEastGate(true);
					break;
				case "10":
					//if the pipe assigned is "pipe, 10", it is used to install the west and south gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasWestGate(true);
						this.setHasSouthGate(true);
					break;
				case "11":
					//if the pipe assigned is "pipe, 11", it is used to install the east and south gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasEastGate(true);
						this.setHasSouthGate(true);
					break;
			}
			break;
			
		case "PipeStatic":
			switch (property) {
			//If the assigned pipe is "PipeStatic", to find whether it is vertical, vertical or curve
				case "Vertical":
					//If the assigned pipe is "pipeStatic, vertical", it is used to install the south and north door.
						this.setHasNorthGate(true);
						this.setHasSouthGate(true);
                    break;
				case "Horizontal":
					//if the assigned pipe is "pipeStatic, horizontal" to install the east and west gate
						this.setHasEastGate(true);
						this.setHasWestGate(true);
                    break;
				case "00":
					//if the pipe assigned is "pipeStatic, 00", it is used to install the north and west gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasNorthGate(true);
						this.setHasWestGate(true);
                    break;
				case "01":
					//if the pipe assigned is "pipeStatic, 01", it is used to install the north and east gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasNorthGate(true);
						this.setHasEastGate(true);
                    break;
				case "10":
					//if the pipe assigned is "pipeStatic, 10", it is used to install the west and south gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasWestGate(true);
						this.setHasSouthGate(true);
                    break;
				case "11":
					//if the pipe assigned is "pipeStatic, 11", it is used to install the east and south gate and indicate that the direction can be changed.
						this.setCanChangeDirection(true);
						this.setHasEastGate(true);
						this.setHasSouthGate(true);
                    break;
			}
			break;
		}
	}
	
	public void setImage(Image image) {
		this.image = image;
		this.setFill(new ImagePattern(image));
	}
	
	
	public void setGates(Path_Direction pathDirection) {

		Cell_Direction north = Cell_Direction.NORTH;
		Cell_Direction east = Cell_Direction.EAST;
		Cell_Direction south = Cell_Direction.SOUTH;
		Cell_Direction west = Cell_Direction.WEST;
		
		switch (pathDirection) {
		//to create the entry and exit directions of the tile relative to the path path.
			case UP:
				//If the path is upwards, the entrance end of the tile must be south.
				this.setInGateDirection(south);
				this.setHasInGate(true);
				//if else to install the tile according to the specification of the outlet
				if(this.hasNorthGate) {				
					this.setOutGateDirection(north);
					this.setHasOutGate(true);
				}
				else if (this.hasEastGate) {
					this.setOutGateDirection(east);
					this.setHasOutGate(true);
					
				}
				else if (this.hasWestGate) {
					this.setOutGateDirection(west);
					this.setHasOutGate(true);
				}
				
				break;
			case RIGHT:
				//if the path is to the right, the entrance end of the tile must be west.
				this.setInGateDirection(west);
				this.setHasInGate(true);
				
				//if else to install the tile according to the specification of the outlet
				if(this.hasNorthGate) {
					this.setOutGateDirection(north);
					this.setHasOutGate(true);
				}
				else if (this.hasEastGate) {
					this.setOutGateDirection(east);
					this.setHasOutGate(true);
				}
				else if (this.hasSouthGate) {
					this.setOutGateDirection(south);
					this.setHasOutGate(true);
				}
				
				break;
			case DOWN:
				//If the path is downwards, the entrance end of the tile must be north.			
				this.setInGateDirection(north);
				this.setHasInGate(true);
				
				//if else to install the tile according to the specification of the outlet
				if(this.hasWestGate) {
					this.setOutGateDirection(west);
					this.setHasOutGate(true);
				}
				else if (this.hasEastGate) {
					this.setOutGateDirection(east);
					this.setHasOutGate(true);
				}
				else if (this.hasSouthGate) {
					this.setOutGateDirection(south);
					this.setHasOutGate(true);
				}
				
				break;
			case LEFT:
				//if the path is to the left, the entrance end of the tile must be east.
				this.setInGateDirection(east);
				this.setHasInGate(true);
				
				//if else to install the tile according to the specification of the outlet
				if(this.hasNorthGate) {
					this.setOutGateDirection(north);
					this.setHasOutGate(true);
				}
				else if (this.hasWestGate) {
					this.setOutGateDirection(west);
					this.setHasOutGate(true);
				}
				else if (this.hasSouthGate) {
					this.setOutGateDirection(south);
					this.setHasOutGate(true);					
				}
				
				break;
		}
	}
	
	
	
	
	
	
	
	public void setPathPieces(int xC, int yC, Path_Direction pathDirection) {
		
		switch (pathDirection) {
		//With pathDirection, it is clear which direction it will go.
		
			case UP:
				//with if else sentences pathPieces are set up according to the probability of the exit direction of the pipe in each tile
				if(this.hasNorthGate) {
					this.setPathPiece(new VLineTo((xC-1)*160+120));
					//shifts in coordinates are corrected by calculations
				}
				else if (this.hasEastGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, (yC+1)*160-55, xC*160+50, false, true));
					
				}
				else if (this.hasWestGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, (yC)*160-55, xC*160+50, false, false));
				}
				
				break;
			case RIGHT:
				//with if else sentences pathPieces are set up according to the probability of the exit direction of the pipe in each tile

				if(this.hasNorthGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, yC*160+27, (xC-1)*160+120, false, false));
					//shifts in coordinates are corrected by calculations

				}
				else if (this.hasEastGate) {
					this.setPathPiece(new HLineTo((yC+1)*160-55));
				}
				else if (this.hasSouthGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, yC*160+27, (xC+1)*160-40, false, true));
				}
				
				break;
			case DOWN:
				//with if else sentences pathPieces are set up according to the probability of the exit direction of the pipe in each tile

				if(this.hasWestGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, yC*160-35, xC*160+50, false, true));
					//shifts in coordinates are corrected by calculations

				}
				else if (this.hasEastGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, (yC+1)*160-55, xC*160+50, false, false));
				}
				else if (this.hasSouthGate) {
					this.setPathPiece(new VLineTo(xC*160+120));
				}
				
				break;
			case LEFT:
				//with if else sentences pathPieces are set up according to the probability of the exit direction of the pipe in each tile

				if(this.hasNorthGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, (yC+1)*160-135, (xC-1)*160+120, false, true));
					//shifts in coordinates are corrected by calculations

				}
				else if (this.hasWestGate) {
					this.setPathPiece(new HLineTo(yC*160-55));
				}
				else if (this.hasSouthGate) {
					this.setPathPiece(new ArcTo(80, 80, 90, yC*160+27, (xC+1)*160-40, false, false));
					
				}
				
				break;
		}
	}
	
	
	
	
	
	///////////////////////////////////////
	//GETTER AND SETTER
	//////////////////////////////////////
	public int getIdOfCell() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}

	public Image getImage() {
		return image;
	}

	public boolean isHasNorthGate() {
		return hasNorthGate;
	}

	public void setHasNorthGate(boolean hasNorthGate) {
		this.hasNorthGate = hasNorthGate;
	}

	public boolean isHasEastGate() {
		return hasEastGate;
	}

	public void setHasEastGate(boolean hasEastGate) {
		this.hasEastGate = hasEastGate;
	}

	public boolean isHasSouthGate() {
		return hasSouthGate;
	}

	public void setHasSouthGate(boolean hasSouthGate) {
		this.hasSouthGate = hasSouthGate;
	}

	public boolean isHasWestGate() {
		return hasWestGate;
	}

	public void setHasWestGate(boolean hasWestGate) {
		this.hasWestGate = hasWestGate;
	}

	public boolean isCanChangeDirection() {
		return canChangeDirection;
	}

	public void setCanChangeDirection(boolean canChangeDirection) {
		this.canChangeDirection = canChangeDirection;
	}

	public boolean isStarter() {
		return isStarter;
	}

	public void setStarter(boolean isStarter) {
		this.isStarter = isStarter;
	}

	public boolean isFinisher() {
		return isFinisher;
	}

	public void setFinisher(boolean isFinisher) {
		this.isFinisher = isFinisher;
	}
	
	public boolean isHasInGate() {
		return hasInGate;
	}

	public void setHasInGate(boolean hasInGate) {
		this.hasInGate = hasInGate;
	}

	public boolean isHasOutGate() {
		return hasOutGate;
	}

	public void setHasOutGate(boolean hasOutGate) {
		this.hasOutGate = hasOutGate;
	}
	public PathElement getPathPiece() {
		return pathPiece;
	}

	public void setPathPiece(PathElement pathPiece) {
		this.pathPiece = pathPiece;
	}
	
	
	public Cell_Direction getInGateDirection() {
		return inGateDirection;
	}

	public void setInGateDirection(Cell_Direction inGateDirection) {
		
		switch (inGateDirection) {
			case NORTH:
				if (this.isHasNorthGate()) {
					this.inGateDirection = inGateDirection;
				}
				break;
			case EAST:
				if (this.isHasEastGate()) {
					this.inGateDirection = inGateDirection;
				}
				break;
			case SOUTH:
				if (this.isHasSouthGate()) {
					this.inGateDirection = inGateDirection;
				}
				break;
			case WEST:
				if (this.isHasWestGate()) {
					this.inGateDirection = inGateDirection;
				}
				break;
		}
	}

	public Cell_Direction getOutGateDirection() {
		return outGateDirection;
	}

	public void setOutGateDirection(Cell_Direction outGateDirection) {
		
		switch (outGateDirection) {
			case NORTH:
				if (this.isHasNorthGate()) {
					this.outGateDirection = outGateDirection;
				}
				break;
			case EAST:
				if (this.isHasEastGate()) {
					this.outGateDirection = outGateDirection;
				}
				break;
			case SOUTH:
				if (this.isHasSouthGate()) {
					this.outGateDirection = outGateDirection;
				}
				break;
			case WEST:
				if (this.isHasWestGate()) {
					this.outGateDirection = outGateDirection;
				}
				break;
		}
	}
	
	
	
	
	
	
}