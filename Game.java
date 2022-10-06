import java.io.File;
import java.util.*;

public class Game {
	public ArrayList<Level> defaultLevels = new ArrayList<>();
	public ArrayList<Level> levels = new ArrayList<>();

	public Game() throws Exception{
		//Allows it to read files by directing it to the readLevel method in the default constructor
		readLevel("level1.txt");
		readLevel("level2.txt"); 
		readLevel("level3.txt");
		readLevel("level4.txt");
		readLevel("level5.txt");
		readLevel("level6.txt");
		readLevel("level7.txt");
		readLevel("level8.txt");
		readLevel("level9.txt");
		
	}

	public Level readLevel(String fileName) throws Exception {
			File file = new File(fileName);
			Scanner input = new Scanner(file);
			Level level = new Level();
			Cell[][] tempBoard = new Cell[4][4];
			//With the while loop, the file is read line by line until there is a space and the read tiles are thrown to the tempBoard with their coordinates.
			int k=1;
			while (input.hasNextLine()) {
				String line = input.next();
				if (!(line == " ")) {
					String[] words = line.split(",");
					Cell cell = new Cell(Integer.parseInt(words[0]), words[1], words[2]);
					tempBoard[(cell.getIdOfCell()-1)/4][(cell.getIdOfCell()-1)%4] = cell;			
				}
				k++;
				if(k == 17)
				break;
			}
			//Tiles read with the setBoard method are installed on the board
			input.close();
			level.setBoard(tempBoard);
			this.getLevels().add(level);
			//it is also added again with the help of clone for the previous button
			this.getDefaultLevels().add(level.clone());
			return level;
	}
	
	///////////////////////////////////////
	//GETTER AND SETTER
	//////////////////////////////////////
	public ArrayList<Level> getDefaultLevels() {
		return defaultLevels;
	}
	public void setDefaultLevels(ArrayList<Level> levels) {
		this.defaultLevels = levels;
	}

	public ArrayList<Level> getLevels() {
		return levels;
	}

	public void setLevels(ArrayList<Level> levels) {
		this.levels = levels;
	}
}