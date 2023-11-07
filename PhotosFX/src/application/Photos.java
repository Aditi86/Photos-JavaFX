package application;

/** 
 * This class runs the application. 
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 
	
import java.util.ArrayList;

//import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Deserialization;
import model.Serialization;
import model.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Photos extends Application 
{
	/** To store user objects*/
	public static ArrayList<User> users = new ArrayList<>();
	
	/** This method prepares application to launch.
	 * @param primaryStage     will set the stage*/
	
	@Override
	public void start(Stage primaryStage) 
	{
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login_Page.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setTitle("Photos");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(e -> {
				e.consume(); 
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the application?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES)
		        {
					Serialization.serailzeUser(users);
					primaryStage.close();
		        }
			
				
				});		
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	/** The Main method, launches the application.
	 * @param args   */
	public static void main(String[] args)
	{
		Deserialization.deserializeUser();
		launch(args);
	}
	
	
}

