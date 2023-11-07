package controllers;

/** 
 * This class helps admin to manage admin view such as delete, view and add users.
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.User;

public class AdminController implements Initializable
{
	@FXML
	TableView <User>userList;
	@FXML
	TableColumn<User, String> name_column;
	@FXML
	TableColumn<User, String> username_column;
	
	@FXML
	TextField name;
	@FXML
	TextField username;
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	
//----------------------------------------------------------------------------------------------------------------------------
	/** This method runs first always and initalize things that we want.
	 * @param arg0   
       @param arg1
       */
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		name_column.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		username_column.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		populateTableView(Photos.users);
	}
	
//----------------------------------------------------------------------------------------------------------------------------
	/** This method helps admin to create new user in the system.
	 * @param event
	 */
	public void createUser(ActionEvent event)
	{
		Alert alert; 
		String name_of_user = name.getText();
		String username_of_user = username.getText();
		
		if(name.getText().length() == 0 || username.getText().length() == 0)
		{
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
	        alert.setHeaderText("Invalid input");
	        alert.setContentText("Please fill out both fields.");
	        alert.showAndWait();
		}
		else
		{
			int count = 0;
			for(int i = 0; i < Photos.users.size(); i++)
			{
				if(Photos.users.get(i).getUsername().equals(username_of_user))
				{
					count++;
				}
			}
			if(count >0  || username_of_user.equals("admin"))
			{
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
		        alert.setHeaderText("Invalid input");
		        alert.setContentText("Username already exists in the system.");
		        alert.showAndWait();
			}
			else if(count == 0)
			{
				User newUser = new User(name_of_user, username_of_user);
				userList.getItems().add(newUser);
				Photos.users.add(newUser);
				
			}

		}
	
	}
	
//----------------------------------------------------------------------------------------------------------------------------
	/** This method helps admin to delete the user form the system.
	 * @param event
	 * */
	public void deleteUser(ActionEvent event)
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the user?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
        {
			int selectedID = userList.getSelectionModel().getSelectedIndex();
			userList.getItems().remove(selectedID);
			Photos.users.remove(selectedID);
        }

	}
	
//----------------------------------------------------------------------------------------------------------------------------
	/** This method helps user to logout of the application. 
	 * @param event
	 * */
	public void logOut(ActionEvent event)
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want log out?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
        {
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/Login_Page.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
        }
	}
	
//----------------------------------------------------------------------------------------------------------------------------
	/** This method helps to populate the table view from the user objects.
	 * @param users
	 * */
	public void populateTableView(ArrayList<User> users)
	{
		if(!users.isEmpty())
		{
			for(int i = 0; i < users.size(); i++)
			{
				userList.getItems().add(users.get(i));
			}
		}
		
	}
	
//----------------------------------------------------------------------------------------------------------------------------

	
}
