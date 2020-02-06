/**
 * Controller for UI and Guassian/Poission distrubution of random numbers 
 * 
 * Name: Main.java
 *  
 * Written by: Niall Quinn - September 2018 
 * 
 * Purpose: loads the UI 
 * 
 * usage: put Main.java, Lab Controller.java and LabController.fxml in the same folder 
 * and do javac *.java and then java Main. Ensure that javafx is installed. 
 * Its much easier to use an IDE with javafx as most IDE's allow for easy installation and running 
 * 
 * Subroutines/Libraries: See import statements below 
 * 
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[]args) {
		launch(args);	
	}

	/**
	 * A method that runs when the application is started 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//the fxml file that has all the style information in it is loaded 
		Parent root = FXMLLoader.load(getClass().getResource("LabController.fxml"));
		//sets the background colour to white 
		root.setStyle("-fx-background-color: #ffffff;");
		Scene scene = new Scene(root);
		//sets the title 
		primaryStage.setTitle("Normal and Poisson Distrubtion");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	

}