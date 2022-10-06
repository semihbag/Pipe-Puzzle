import java.io.*;
import java.util.*;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private int levelNumber;
	private int numberOfMoves;
	private int xb = -99;
	private int yb = -99;
	private int xt = -99;
	private int yt = -99;
	private Game game;
	private GridPane gridPane;
	private Scene scene;
	private Stage stage;
	private boolean controlUnlockAudio = true;
	private boolean sound = true;
	


	 //the method by which the game screen is set up
		public void start(Stage primaryStage) throws Exception {
			this.stage = primaryStage;
			//the stage to be used is defined
			Media seeYou = new Media(new File("seeyou.wav").toURI().toString());
			MediaPlayer seeYouPlayer = new MediaPlayer(seeYou);
			seeYouPlayer.setVolume(1);
			//the sounds to be used are defined
			StackPane stack = new StackPane();
			stack.setStyle("-fx-background-color: #" + 0x0000FF);
	        //the pane to be used is defined and colored
			VBox vb = new VBox(200);
			HBox hb = new HBox(300);
			vb.setAlignment(Pos.CENTER);
			hb.setAlignment(Pos.CENTER);
			//hbox and vbox are defined
			Text title = new Text("Pipe Game"); // 
			title.setFont(Font.font("Courier", FontWeight.BOLD, 50));
			title.setFill(Color.ORANGE);
			//the name of the game is written and the font etc. is set
			Rectangle rec = new Rectangle(1000,1000);
			rec.setStroke(Color.BLACK);
			rec.setOpacity(0);
		    FadeTransition blackScreen = new FadeTransition(Duration.seconds(3), rec);
		    blackScreen.setFromValue(0);
		    blackScreen.setToValue(1);
		    /*When the quit button is clicked, a rectangular shape of the same size is defined to dim the screen, 
		    and it is opaque with the fade, and the tab is closed when it turns dark.*/

			Image imagemainmenu=new Image("ball1.png");
			ImageView imageView = new ImageView(imagemainmenu); 
			imageView.setFitHeight(120); 
		    imageView.setFitWidth(120);
		    FadeTransition ft = new FadeTransition(Duration.millis(3000), imageView);
		    ft.setFromValue(1.0);
		    ft.setToValue(0);
		    ft.setCycleCount(Timeline.INDEFINITE);
		    ft.setAutoReverse(true);
		    ft.play(); 
		    Image imagemainmenu2=new Image("ball2.png");
			ImageView imageView2 = new ImageView(imagemainmenu2); 	
			imageView2.setFitHeight(120); 
		    imageView2.setFitWidth(120);
		    FadeTransition ft2 = new FadeTransition(Duration.millis(3000), imageView2);
		    ft2.setFromValue(0);
		    ft2.setToValue(1.0);
		    ft2.setCycleCount(Timeline.INDEFINITE);
		    ft2.setAutoReverse(true);
		    ft2.play(); 
		    //Define the balls object shown in the main menu, set the opacity times, enter the vicious circle

		    StackPane images = new StackPane();
		    images.getChildren().addAll(imageView,imageView2);
		    //used images are fixed to the pane
			Button startButton= new Button("Start");
			startButton.setStyle("-fx-border-color: orange");
			Button quitButton=new Button("Quit");	
			//When the start button is clicked, it is sent to the other method to display the other screen.
			startButton.setOnAction(e -> {
				Media mediaUnlocked = new Media(new File("unlocked2.wav").toURI().toString());
				MediaPlayer mediaplayerUnlocked = new MediaPlayer(mediaUnlocked);
				mediaplayerUnlocked.setVolume(1);
				mediaplayerUnlocked.play();
				mediaplayerUnlocked.seek(Duration.ZERO);
				try {
					mapLoad();
					primaryStage.setTitle("Pipe Puzzle");
					primaryStage.setScene(this.getScene());
					primaryStage.show();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			});
			//Clicking the quit button closes the window with System.exit
			quitButton.setOnAction(e -> {
				stack.getChildren().add(rec);
				blackScreen.play();
				seeYouPlayer.play();
				blackScreen.setOnFinished(a->{
					   System.exit(0);	 

				});
			});
			//actions on buttons are set
			
	///////////////////////////////////////////////////////////////////////////////
			hb.getChildren().addAll(startButton,quitButton);
			vb.getChildren().addAll(title,images,hb);
			stack.getChildren().add(vb);
			Scene scene1 = new Scene(stack,675,750);
			primaryStage.setScene(scene1);
			primaryStage.setTitle("Pipe Game");
			primaryStage.setResizable(false);
			primaryStage.show();	
			//All buttons are added to the images scene, the window title is assigned and finally it is displayed with the show method.
			
		}
	
		//SetGame and setScene methods are called to set up the game screen with the mapLoad method.
		public void mapLoad () throws Exception {
			setGame(new Game());
			setScene();			
		}
	
		//With createGridPane the tiles in the game are printed to the screen
		public GridPane createGridPane(Cell[][] board) {
	        //!!The concept of "free" is turned into "Free" with the for loop in order to resolve the differences in the discarded files and to read the file correctly.
			for (int i = 1 ; i < 17 ; i++) {
				String tempType = board[(i-1)/4][(i-1)%4].getType().toString();
				String tempProperty = board[(i-1)/4][(i-1)%4].getProperty().toString();
				if (tempProperty.equals("free")) {
					board[(i-1)/4][(i-1)%4].setProperty("Free");
					tempProperty = "Free";
				}
				Image temp;
				switch (tempType) {
				//enters a different case block for each type
					case "Starter":
						switch (tempProperty) {
							case "Vertical":
								//If the pipe vertical on the tile is using the remainder and the section, its coordinates are taken and that pipe type is installed on that tile with setImage              
				                    temp = new Image("start_vertical.png");
				                    board[(i-1)/4][(i-1)%4].setImage(temp);
				                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
								break;
							case "Horizontal":
								//If the pipe horizontal on the tile is using the remainder and the section, its coordinates are taken and that pipe type is installed on that tile with setImage
									 temp = new Image("start_horizontal.png");
									 board[(i-1)/4][(i-1)%4].setImage(temp);
					                 makeMovable(board[(i-1)/4][(i-1)%4]);
					                  // the movement ability is assigned with makemovable.  

								break;
						}
						break;
				
					case "End":
						switch (tempProperty) {
							case "Vertical":
								//With (/) and remainder, the coordinate of that tile is taken, then the pipe is assigned to the image variable and finally it is installed on that tile with setImage
									 temp = new Image("end_vertical.png");
									 board[(i-1)/4][(i-1)%4].setImage(temp);
				                     makeMovable(board[(i-1)/4][(i-1)%4]);
					                  // the movement ability is assigned with makemovable.  

						break;
							case "Horizontal":
									 temp = new Image("end_horizontal.png");
									 board[(i-1)/4][(i-1)%4].setImage(temp);
				                     makeMovable(board[(i-1)/4][(i-1)%4]);
					                  // the movement ability is assigned with makemovable.  

								 break;
						}
						break;
					case "Empty":
						switch (tempProperty) {
							case "none":
								//With (/) and remainder, the coordinate of that tile is taken, then the pipe is assigned to the image variable and finally it is installed on that tile with setImage
			                    temp = new Image("empty.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    break;
							case "Free":
			                    temp = new Image("empty_free.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    break;
						}
						break;
						
					case "Pipe":
						switch (tempProperty) {
							case "Vertical":
								//With (/) and remainder, the coordinate of that tile is taken, then the pipe is assigned to the image variable and finally it is installed on that tile with setImage
			                    temp = new Image("pipe_vertical.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    break;
							case "Horizontal":
			                    temp = new Image("pipe_horizontal.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    break;
							case "00":
			                    temp = new Image("curved_pipe_00.png");
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
								break;
							case "01":
			                    temp = new Image("curved_pipe_01.png");
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
								break;
							case "10":
			                    temp = new Image("curved_pipe_10.png");
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
								break;
							case "11":
			                    temp = new Image("curved_pipe_11.png");
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
				                  // the movement ability is assigned with makemovable.  
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
								break;
						}
						break;
						
					case "PipeStatic":
						switch (tempProperty) {
							case "Vertical":
								//With (/) and remainder, the coordinate of that tile is taken, then the pipe is assigned to the image variable and finally it is installed on that tile with setImage
			                    temp = new Image("pipe_static_vertical.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
							case "Horizontal":
			                    temp = new Image("pipe_static_horizontal.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
							case "00":
			                    temp = new Image("curved_pipe_static_00.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
							case "01":
			                    temp = new Image("curved_pipe_static_01.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
							case "10":
			                    temp = new Image("curved_pipe_static_10.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
							case "11":
			                    temp = new Image("curved_pipe_static_11.png");
			                    board[(i-1)/4][(i-1)%4].setImage(temp);
			                    makeMovable(board[(i-1)/4][(i-1)%4]);
			                    break;
						}
						break;
				}
			}	
			//a temporary tempgrid is created
			GridPane tempGrid = new GridPane();
			tempGrid.setAlignment(Pos.CENTER);
			tempGrid.setPadding(new Insets(10,10,10,10));
			tempGrid.setVgap(0);
			tempGrid.setHgap(0);
			//With the for loop, the temporary grid is transferred to the master board
			for(int i = 0 ; i < 4 ; i++) {
				for (int j = 0 ; j < 4 ; j++) {
					tempGrid.add(board[i][j], j, i);
				}
			}
			this.setGridPane(tempGrid);
			//gridpane is installed
			return this.getGridPane();
		}
		
	
		// Determine the base cell
		public void move1(Cell cell) {
			//Sets the value of the property onMousePressed.
			 cell.setOnMousePressed(e->{
				 Cell temp = (Cell) e.getSource();
				 //The coordinates of the selected cell are taken with the for loop
				 for(int i = 0 ; i <4 ; i++) {
					 for (int j = 0 ; j < 4 ; j++) {
						 if (this.getGame().getLevels().get(levelNumber).getBoard()[i][j].equals(temp)) {
							 setXb(i);
							 setYb(j);
							 break;
						 }
					 }
				 }
				 e.consume();
			 });
		}

		
		// Determine the target cell	
		 public void move2(Cell cell) {
				//Sets the value of the property onMousePressed.
			 cell.setOnMouseDragReleased(e->{
				 Cell temp = (Cell) e.getSource();
				 
				 //The coordinates of the selected cell are taken with the for loop
				 for(int i = 0 ; i <4 ; i++) {
					 for (int j = 0 ; j < 4 ; j++) {
						 if (this.getGame().getLevels().get(levelNumber).getBoard()[i][j].equals(temp)) {
							 if (temp.getType().equals("Empty") && (temp.getProperty().equals("Free"))){
								 setXt(i);
								 setYt(j);
								 break; 
							 }
						 }
					 }
				 }
				 exchangeCell(xb, yb, xt, yt);
				 //The transaction is completed by directing it to the exchange method
				 e.consume();
			 });
		 }
	 
		 //to give mobility
		 public void makeMovable(Cell cell) {
			 move1(cell);
			 move2(cell);
		 }
	 
		 public void move() {
			 //Sets the value of the property onDragDetected
			 gridPane.setOnDragDetected(e ->{
				 //Starts a full press-drag-release gesture with this node as gesture source.
				 gridPane.startFullDrag();
				 e.consume();
			 });
		 }

	 
	 public void exchangeCell(int xb, int yb, int xt, int yt) {
		 if  (!((xb==xt)&&(yb==yt))) {
			 if (((xt==xb-1)&&(yt==yb)) || ((xt==xb+1)&&(yt==yb)) || ((yt==yb-1)&&(xt==xb)) || ((yt==yb+1)&&(xt==xb))) {
				
				 Cell base = this.getGame().getLevels().get(levelNumber).getBoard()[xb][yb];
				 Cell target = this.getGame().getLevels().get(levelNumber).getBoard()[xt][yt];
				 
				 if (!((base.getType().equals("Starter")) || (base.getType().equals("End")) || (base.getType().equals("PipeStatic")) || ((base.getType().equals("Empty")) && (base.getProperty().equals("Free"))) || (!((target.getType().equals("Empty")) && (target.getProperty().equals("Free")))))) {
					
					 gridPane.getChildren().remove(this.getGame().getLevels().get(levelNumber).getBoard()[xb][yb]);
					 gridPane.getChildren().remove(this.getGame().getLevels().get(levelNumber).getBoard()[xt][yt]);
					 
					 gridPane.add(base, yt ,xt);
					 
					 this.getGame().getLevels().get(levelNumber).getBoard()[xb][yb] = target;
					 this.getGame().getLevels().get(levelNumber).getBoard()[xt][yt] = base;
					 
					 System.out.println("basex:"+base.getLayoutX());
					 System.out.println("basey:"+base.getLayoutY());
					 System.out.println("targetx:"+ target.getLayoutX());
					 System.out.println("targety"+ target.getLayoutY());
					 System.out.println();
					 
					 // Cell Animatoin
					 Line line = new Line();
					 //down
					 if (xt > xb) {
						  line = new Line(80,-80,80,80);
					 }
					 //right
					 if (yt > yb) {
						  line = new Line(-80,80,80,80);
					 }
					 //up
					 if (xt < xb) {
						  line = new Line(80,240,80,80);
					 }
					 //left
					 if (yt < yb) {
						  line = new Line(240,80,80,80);
					 }
					 PathTransition pt = new PathTransition();
					 pt.setDuration(Duration.millis(300));
					 pt.setPath(line);
					 pt.setNode(base);
					 pt.setOrientation( PathTransition.OrientationType.NONE);
					 pt.setAutoReverse(false);
					 pt.play();
					 pt.setOnFinished(e->{
						 gridPane.add(target, yb, xb);
					 });
					 
					 Media mediaMove = new Media(new File("cell_swap.mp4").toURI().toString());
					 MediaPlayer mediaplayerMove = new MediaPlayer(mediaMove);
					 EventHandler<ActionEvent> eventHandler = (e -> {
						 if(sound) {
							 mediaplayerMove.setVolume(0.05);
						 }
						 else {
							 mediaplayerMove.setVolume(0);
						 }
					 });
					Timeline animation = new Timeline(new KeyFrame(Duration.millis(1), eventHandler));

					 mediaplayerMove.setVolume(0.05);
					 mediaplayerMove.play();
					 //moves are increased by 1 for each tile displacement
					 this.setNumberOfMoves(this.getNumberOfMoves()+1);

				 }
			 }
		 }
	 }
	 
	 //to install the next level
		public void nextLevel() {
			if(this.getGame().getLevels().get(this.getLevelNumber()).isLevelCompleted()) {
				//It takes the current level and checks whether it is completed with the isLevelCompleted method
				if (this.getGame().getLevels().size() - 1> this.getLevelNumber()) {
					//If completed, increase the level by one
					this.setLevelNumber(this.getLevelNumber()+1);
					this.getGame().getLevels().remove(levelNumber);
					//also installs the cloned level for the next level
					this.getGame().getLevels().add(levelNumber, this.getGame().getDefaultLevels().get(levelNumber).clone());
					this.getGame().getLevels().get(levelNumber).isLevelCompleted();
					System.out.println(this.getGame().getLevels().get(levelNumber).getxS());
					System.out.println(this.getGame().getLevels().get(levelNumber).getyS());
				}
				//Sends the nextlevel to the setScene method and prints the next level to the screen
				setScene();
				this.getStage().setScene(this.getScene());
			}
			else {
			}
		}
	
		 //to install the previous level
		public void previousLevel() {
			//If there is a previous level (!=0), it returns that level by subtracting 1 from the number of levels.
			if (this.getLevelNumber() != 0) {
				this.setLevelNumber(this.getLevelNumber()-1);
			}
			this.getGame().getLevels().remove(levelNumber);
			//also installs the cloned level for the previous level
			this.getGame().getLevels().add(levelNumber, this.getGame().getDefaultLevels().get(levelNumber).clone());
			setScene();
			this.getGame().getLevels().get(levelNumber).isLevelCompleted();
			this.getStage().setScene(this.getScene());

		}
	
	
		public void setScene() {
			
			////////sounds to be used are defined, volume levels adjusted and played
			Media mediaUnlocked = new Media(new File("unlocked2.wav").toURI().toString());
			MediaPlayer mediaplayerUnlocked = new MediaPlayer(mediaUnlocked);
			
			Media mediaWrong = new Media(new File("wrong.wav").toURI().toString());
			MediaPlayer mediaplayerWrong = new MediaPlayer(mediaWrong);
			
			Media mediaLastLevel = new Media(new File("yetersiz_bakiye.wav").toURI().toString());
			MediaPlayer mediaplayerLastLevel = new MediaPlayer(mediaLastLevel);
			//////
			/////the ball to be used is added and its animation is set up
			ImageView ball1 = createBall1();
			ImageView ball2 = createBall2();
			int animatiomTime = 5;
			PathTransition ball1Animation = new PathTransition();
			ball1Animation.setDuration(Duration.seconds(animatiomTime));
			ball1Animation.setNode(ball1);
			
			PathTransition ball2Animation = new PathTransition();
			ball2Animation.setDuration(Duration.seconds(animatiomTime));
			ball2Animation.setNode(ball2);
			/////////
			/////////The opacity between the 2 ball objects is changed and after a certain time the other one appears.
			FadeTransition fadeBall1Animatoin = new FadeTransition(Duration.seconds(animatiomTime),ball1);
			fadeBall1Animatoin.setFromValue(0);
			fadeBall1Animatoin.setToValue(1.0);

			FadeTransition fadeBall2Animatoin = new FadeTransition(Duration.seconds(animatiomTime),ball2);
			fadeBall2Animatoin.setFromValue(1.0);
			fadeBall2Animatoin.setToValue(0);
			///////////////
			/////The pane to be used is created, the blank image of the same size is loaded for the background, the gridpane is set up according to the number of levels.
			StackPane stack = new StackPane();
			ImageView img = new ImageView(new Image("empty_free.png"));
			img.setFitHeight(640);
			img.setFitWidth(640);
			stack.getChildren().add(img);
			stack.getChildren().add(createGridPane(this.getGame().getLevels().get(levelNumber).getBoard()));
			///////
			///////
			VBox vb = new VBox();
			HBox hbTop = new HBox(30);
			hbTop.setPadding(new Insets(10,10,0,10));
			hbTop.setAlignment(Pos.CENTER);
			///////
			///////The form in which the level number on the game screen will be written is created.
			Rectangle rectangleLeft = new Rectangle(120,60);
			//color size is adjusted
			rectangleLeft.setFill(Color.LIGHTSKYBLUE);
			rectangleLeft.setArcWidth(25);
			rectangleLeft.setArcHeight(25);
			StackPane stackTopLeft = new StackPane();
			//the text that will be written on the shape is defined
			Text textLeft = new Text("LEVEL");
			textLeft.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
			Text textLeft2 = new Text(""+(this.getLevelNumber()+1));
			textLeft2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
			VBox vbTopLeft = new VBox();
			vbTopLeft.setAlignment(Pos.CENTER);
			//text and shape are fixed to the pane
			vbTopLeft.getChildren().addAll(textLeft,textLeft2);
			stackTopLeft.getChildren().addAll(rectangleLeft,vbTopLeft);
			hbTop.getChildren().add(stackTopLeft);
	        ///////

	        ///////The shape that will write the Pipe Puzzle is defined
			Rectangle rectangleCenter = new Rectangle(300,60);
			rectangleCenter.setFill(Color.LIGHTSKYBLUE);
			rectangleCenter.setArcWidth(25);
			rectangleCenter.setArcHeight(25);
			StackPane stackTopCenter = new StackPane();
			stackTopCenter.getChildren().add(rectangleCenter);
			Text textCenter = new Text("PIPE PUZZLE");
			textCenter.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 30));

			 ///////When the level is completed, it is checked with if else statements to write the "LEVEL UNLOCKED".
			 EventHandler<ActionEvent> eventHandler = e -> {
					if (this.getGame().getLevels().get(levelNumber).isLevelCompleted()) {
						textCenter.setText("LEVEL UNLOCKED");
						rectangleCenter.setFill(Color.GREENYELLOW);
						//for the sound to play when the level is completed
						if (controlUnlockAudio) {
							mediaplayerUnlocked.play();
							mediaplayerUnlocked.seek(Duration.ZERO);
							setControlUnlockAudio(false);
						}
					}
					else {
						//to constantly replace with "PIPE PUZZLE" if level is not completed yet
						rectangleCenter.setFill(Color.ORANGE);
						setControlUnlockAudio(true);
						if (textCenter.getText().length() != 14) {
							textCenter.setText("COMPLETE LEVEL");
						}
						else {
							rectangleCenter.setFill(Color.LIGHTSKYBLUE);
							textCenter.setText("PIPE PUZZLE");
						}
					}
				e.consume();
			};
			//If level is not completed, animation is set up to switch between "PIPE PUZLLE" and "COMPLETE LEVEL"
			Timeline animation = new Timeline(new KeyFrame(Duration.millis(1300), eventHandler));
			animation.setCycleCount(Timeline.INDEFINITE);
			//is put in a vicious circle
			animation.play();
			//texts are fixed in the pane
			stackTopCenter.getChildren().add(textCenter);
			hbTop.getChildren().add(stackTopCenter);
			
			///////shape is created to print "MOVES" counting mouse movement
			Rectangle rectangleRight = new Rectangle(120,60);
			rectangleRight.setFill(Color.LIGHTSKYBLUE);
			rectangleRight.setArcWidth(25);
			rectangleRight.setArcHeight(25);
			StackPane stackTopRight = new StackPane();
			Text textRight = new Text("MOVES");
			textRight.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
			Text textRight2 = new Text(""+this.getNumberOfMoves());	
			textRight2.setFont(Font.font("Courier", FontWeight.BOLD, FontPosture.ITALIC, 25));
			//
			 EventHandler<ActionEvent> eventHandlerMoves = e -> {
				textRight2.setText(""+this.getNumberOfMoves());
				e.consume();
			};
			//Moves animation is set to increase with each mouse movement
			Timeline animationMoves = new Timeline(new KeyFrame(Duration.millis(1), eventHandlerMoves));
			animationMoves.setCycleCount(Timeline.INDEFINITE);
			animationMoves.play();

			//moves text is fixed to the pane
			VBox vbTopRight = new VBox(0);
			vbTopRight.setAlignment(Pos.CENTER);
			vbTopRight.getChildren().addAll(textRight,textRight2);
			stackTopRight.getChildren().addAll(rectangleRight,vbTopRight);
			hbTop.getChildren().add(stackTopRight);

			
			
			vb.getChildren().add(hbTop);
			vb.getChildren().add(stack);
			
			//An action is created for the previous button.
			Button btPrevious = new Button("Previous Level");
			btPrevious.setOnAction(e->{
				EventHandler<ActionEvent> eventHandler2 = a -> {
					//gives a warning if there is no level
					if (this.getLevelNumber() == 0) {
						animation.stop();
						System.out.println("level yok la");
						textCenter.setText("NO PREVIOUS LEVEL");
						rectangleCenter.setFill(Color.RED);
						mediaplayerWrong.play();
						mediaplayerWrong.seek(Duration.ZERO);
						//beeps
					}
					a.consume();
				};
				Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(1), eventHandler2));
				animation2.play();
				animation2.setOnFinished(a->{
					//If the previous level exists, it is sent to the previousLevel method.
					if (this.getLevelNumber() != 0) {
						previousLevel();
						System.out.println(this.getGame().getLevels().get(levelNumber).getxS());
						System.out.println(this.getGame().getLevels().get(levelNumber).getyS());
						//mouse movement is also reset
						this.setNumberOfMoves(0);
					}
					animation.play();
					a.consume();
				});
				e.consume();
			});
			
			Button btRestart = new Button("Restart Level");
			btRestart.setOnAction(e->{
				this.getGame().getLevels().remove(levelNumber);
				this.getGame().getLevels().add(levelNumber,this.getGame().getDefaultLevels().get(levelNumber).clone());
				setScene();
				this.getStage().setScene(this.getScene());
			});
			
			//The main menu action is installed
			Button btMain = new Button("Main Menu");
			btMain.setOnAction(e->{
				try {
					//sent to the start method to create the main menu again
					start( stage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			Button btsound = new Button();
			btsound.setText("Sound On");
			btsound.setOnAction(e->{
				if (btsound.getText().length() == 8) {
					btsound.setText("Sound Off");
					sound = false;
					mediaplayerUnlocked.setVolume(0);
					mediaplayerWrong.setVolume(0);
					mediaplayerLastLevel.setVolume(0);

				}
				else {
					btsound.setText("Sound On");
					sound = true;
					mediaplayerUnlocked.setVolume(1);
					mediaplayerWrong.setVolume(1);
					mediaplayerLastLevel.setVolume(1);

				}
			});
			
			//button action is set to go to the next level
			Button btNext = new Button("Next Level");
			btNext.setOnAction(e->{
				EventHandler<ActionEvent> eventHandler3 = a -> {
					//if the level is completed
					if (this.getGame().getLevels().get(levelNumber).isLevelCompleted()) {
						//path is created
						this.getGame().getLevels().get(levelNumber).createPath();
//						Path pp = new Path();
//						pp.getElements().add(new MoveTo(3*160+55,3*160+50));
//						
//						pp.getElements().add(new HLineTo(2*160-55));
//						//pp.getElements().add(new HLineTo(1*160-55));
//						pp.getElements().add(new ArcTo(80, 80, 90, (1+1)*160-135, (3-1)*160+120, false, true));
//						pp.getElements().add(new ArcTo(80, 80, 90, (1)*160-55, 2*160+50, false, false));
						System.out.println(levelNumber);
						//sets up the ball animation
						ball1Animation.setPath(this.getGame().getLevels().get(levelNumber).getPath());
						ball2Animation.setPath(this.getGame().getLevels().get(levelNumber).getPath());
						
//						ball1Animation.setPath(pp);
//						ball2Animation.setPath(pp);
						//gives a warning if there is no other level
						if ( this.getGame().getLevels().size() -1 == this.getLevelNumber()) {
							textCenter.setText("NO NEXT LEVEL");
							rectangleCenter.setFill(Color.RED);
							animation.stop();
							mediaplayerLastLevel.play();
							mediaplayerLastLevel.seek(Duration.ZERO);
							ball1Animation.play();
							fadeBall1Animatoin.play();
							ball2Animation.play();
							fadeBall2Animatoin.play();
							//gives a warning with sound
							
						}
						else {
							//When the level is completed and there is another level, well done text will appear and the ball animation will start.
							textCenter.setText("WELL DONE");
							System.out.println();
							rectangleCenter.setFill(Color.SPRINGGREEN);
							animation.stop();
							ball1Animation.play();
							fadeBall1Animatoin.play();
							ball2Animation.play();
							fadeBall2Animatoin.play();
							//plays the "ball" animation
							
							for(int i = 0 ; i <this.getGame().getLevels().get(levelNumber).getPath().getElements().size();i++) {
								System.out.println(this.getGame().getLevels().get(levelNumber).getPath().getElements().get(i));
							}
						}
					}
					else {
						//If the level is not completed, it says "TRY AGAIN" and gives a beep.
						textCenter.setText("TRY AGAIN");
						rectangleCenter.setFill(Color.RED);
						animation.stop();
						mediaplayerWrong.play();
						mediaplayerWrong.seek(Duration.ZERO);
					}
				a.consume();
				};
				Timeline animation3 = new Timeline(new KeyFrame(Duration.millis(1), eventHandler3));
				animation3.play();
				animation3.setOnFinished(a->{
					if(this.getGame().getLevels().size()-1 > this.getLevelNumber()) {
						ball1Animation.setOnFinished(j->{
							//After the ball animation, it is sent to the nextLevel method and the new level is displayed, at the same time the moves are reset.
							this.getGame().getLevels().get(levelNumber).setPath(new Path());
							nextLevel();
							this.setNumberOfMoves(0);
							j.consume();
						});
						a.consume();
					}
					animation.play();
				});
				e.consume();
			});
			//buttons are added to hbox
			HBox hbBottom = new HBox(52);
			hbBottom.setPadding(new Insets(0,10,10,10));
			hbBottom.setAlignment(Pos.CENTER);
			hbBottom.getChildren().add(btPrevious);
			hbBottom.getChildren().add(btRestart);
			hbBottom.getChildren().add(btMain);
			hbBottom.getChildren().add(btsound);
			hbBottom.getChildren().add(btNext);
			//hbox is added to vbox
			vb.getChildren().add(hbBottom);
			vb.setStyle("-fx-background-color: black");
			
			////////////BALL
			
			
			//gridpane and used ball objects are added to the pane
			Pane pane = new Pane();
			pane.getChildren().add(gridPane);
			pane.getChildren().add(ball1);
			pane.getChildren().add(ball2);
					
			stack.getChildren().add(pane);
			move();
			Scene scene = new Scene(vb);
			this.scene = scene;
		}
	

		public ImageView createBall1() {
			//the ball object used is loaded to be installed on the gridpane
			Image ball1 = new Image("ball1.png");
			ImageView ball1View = new ImageView(ball1);
			ball1View.setFitHeight(40.5);
			ball1View.setFitWidth(52.5);
			//The location of the starter is determined according to the level number.
			Level level = this.getGame().getLevels().get(this.getLevelNumber());
			//It is placed on the tile with certain coordinates according to whether it is vertical or horizontal.
			if (level.getBoard()[level.getxS()][level.getyS()].getProperty().equals("Vertical")) {
				ball1View.setLayoutX((level.getxS()*160)+65);
				ball1View.setLayoutY((level.getyS()*160)+40);
			}
			if (level.getBoard()[level.getxS()][level.getyS()].getProperty().equals("Horizontal")) {
				ball1View.setLayoutX((level.getyS()*160)+90);
				ball1View.setLayoutY((level.getxS()*160)+70);
			}

			return ball1View;
		}
		
		public ImageView createBall2() {
			//the ball object used is loaded to be installed on the gridpane
			Image ball2 = new Image("ball2.png");
			ImageView ball2View = new ImageView(ball2);
			ball2View.setFitHeight(37.5);
			ball2View.setFitWidth(51);
			//The location of the starter is determined according to the level number.
			Level level = this.getGame().getLevels().get(this.getLevelNumber());
			//It is placed on the tile with certain coordinates according to whether it is vertical or horizontal.
			if (level.getBoard()[level.getxS()][level.getyS()].getProperty().equals("Vertical")) {
				ball2View.setLayoutX((level.getxS()*160)+65);
				ball2View.setLayoutY((level.getyS()*160)+40);
			}
			if (level.getBoard()[level.getxS()][level.getyS()].getProperty().equals("Horizontal")) {
				ball2View.setLayoutX((level.getyS()*160)+90);
				ball2View.setLayoutY((level.getxS()*160)+70);
			}

			return ball2View;
		}



	    //main
		public static void main(String[] args) {
			 Application.launch(args);
			 }

		 
	///////////////////////////////////////
	//GETTER AND SETTER
	//////////////////////////////////////
		public int getNumberOfMoves() {
			return numberOfMoves;
		}

		public void setNumberOfMoves(int numberOfMoves) {
			this.numberOfMoves = numberOfMoves;
		}

		public int getLevelNumber() {
			return levelNumber;
		}


		public void setLevelNumber(int levelNumber) {
			this.levelNumber = levelNumber;
		}


		public Game getGame() {
			return game;
		}


		public void setGame(Game game) {
			this.game = game;
		}


		public GridPane getGridPane() {
			return gridPane;
		}

		public void setGridPane(GridPane gridPane) {
			this.gridPane = gridPane;
		}

		public int getXb() {
			return xb;
		}

		public void setXb(int xb) {
			this.xb = xb;
		}

		public int getYb() {
			return yb;
		}

		public void setYb(int yb) {
			this.yb = yb;
		}

		public int getXt() {
			return xt;
		}

		public void setXt(int xt) {
			this.xt = xt;
		}

		public int getYt() {
			return yt;
		}

		public void setYt(int yt) {
			this.yt = yt;
		}
		 
		 public Scene getScene() {
			return scene;
		}
		 
		public Stage getStage() {
			return stage;
		}

		public void setStage(Stage stage) {
			this.stage = stage;
		}

		public boolean isControlUnlockAudio() {
			return controlUnlockAudio;
		}

		public void setControlUnlockAudio(boolean controlUnlockAudio) {
			this.controlUnlockAudio = controlUnlockAudio;
		}
	}