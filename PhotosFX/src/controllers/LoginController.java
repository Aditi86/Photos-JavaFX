package controllers;
/** 
 * This class helps user to login, it will check for current username of the user. 
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */
import java.io.IOException;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

public class LoginController
{
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	/** User object name will be copied to the user who is currently logged in to keep track of their activity*/
	static User name;
	
	@FXML
	TextField username; 
	
	/** This method checks for the current username, if the username is wrong then it won't let the user log in. 
	 * @param event
	 */
	public void loginCheck(ActionEvent event)
	{
		String user_Admin = username.getText();
		int count = 0;
		Alert alert;
		
		try
		{
			if(user_Admin.equals("admin"))  
			{
				root = FXMLLoader.load(getClass().getResource("/fxml/Admin_Page.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else
			{
				if(!Photos.users.isEmpty())
				{
					for(int i = 0; i < Photos.users.size(); i++)
					{
						if(Photos.users.get(i).getUsername().equals(user_Admin))
						{
							count++;
							name = Photos.users.get(i);

							root = FXMLLoader.load(getClass().getResource("/fxml/Home_Page.fxml"));
							stage = (Stage)((Node)event.getSource()).getScene().getWindow();
							scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
							
						}
							
					}
					if(count == 0)
					{	
						alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
				        alert.setHeaderText("This username is not in the system");

				        alert.showAndWait();
					}
					
				}
				else
				{			
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
			        alert.setHeaderText("This username is not in the system");

			        alert.showAndWait();
				}
					
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
}

