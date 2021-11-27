package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

/****************************************************************************************************
 * @authors Robert Casas (sgk782), Dylan Johnson (gug903)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * Main is a subclass of Application that contains the main method for Maze, which is 
 * an application for a game where the user navigates a maze based on hints. If the user 
 * navigates correctly they will escape the maze, if not they will run into the Minotaur.
 ***************************************************************************************************/
public class Main extends Application {
	
	/**************************** public void start(Stage primaryStage) *********************************
	 * - Method overrides application start method to load Home.fxml for an object hierarchy 
	 * and application.css for CSS customization. Method also 
	 * sets the scene and shows the application window.
	 ***************************************************************************************************/
	@Override
	public void start(Stage primaryStage) {		
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/application/view/Home.fxml"));
			Scene scene = new Scene(root,800,800);
			scene.getStylesheets().add(getClass().getResource("/application/view/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// public static void main(String[] args): main method which launches the application.
	public static void main(String[] args) {
		launch(args);
	}
}
