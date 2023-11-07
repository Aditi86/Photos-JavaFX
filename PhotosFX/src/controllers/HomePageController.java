package controllers;
/** 
 * This class helps user to manage the home page where user can create, rename, open and delete the album and search images.
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;


public class HomePageController implements Initializable
{
	@FXML
	Label nameOfUser;
	@FXML 
	TextField createAlbumName;
	@FXML
	TextField renameAlbum;
	@FXML 
	TextField searchphotos;
	@FXML 
	ListView <String> albumListView;
	
	/** home_albums stores the objects of the Album class so we can easily manipulate it */
	public static ArrayList<Album> home_albums = new ArrayList<>();
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	
	/** this int varibale albumIndex helps user to store the current album's index*/
	static int albumIndex;
	/** this in string  search stores the search prompt of the user so we can user that in to a diffrent class*/
	static String search;
	

 
//----------------------------------------------------------------------------------------------------------------------
	/** This method runs first always and initalize things that we want this time it is helping to display the image.
	 * @param arg0   
       @param arg1
       @return void*/
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		nameOfUser.setText( "Welcom to the photo gallary "+ LoginController.name.getName() + "!");
		populateListView();
				
	}

//----------------------------------------------------------------------------------------------------------------------
	/** This method helps to create the new album on the home page. 
	 * @param event */
	public void createAlbum(ActionEvent event)
	{
		String albumName = createAlbumName.getText();
		Alert alert; 
		
		if(createAlbumName.getText().length() == 0 )
		{
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
	        alert.setHeaderText("Invalid input");
	        alert.setContentText("Please fill out the field.");
	        alert.showAndWait();
		}
		else
		{
			if(Photos.users.get(getIndexOfCurrentUser()).getAlbums().isEmpty())
			{
				home_albums.add(new Album(albumName));
				Photos.users.get(getIndexOfCurrentUser()).setAlbums(home_albums);
				albumListView.getItems().add(albumName);
			}
			else
			{
				int count = 0;
				for(int j = 0; j < Photos.users.get(getIndexOfCurrentUser()).getAlbums().size(); j++)
				{

					if(Photos.users.get(getIndexOfCurrentUser()).getAlbums().get(j).getAlbum_name().equals(albumName))
					{
						count++;
					}
				}
				if(count >0)
				{
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText("Invalid input");
					alert.setContentText("Album already exists in the system.");
					alert.showAndWait();
				}
				else if(count == 0)
				{
					home_albums.add(new Album(albumName));
					Photos.users.get(getIndexOfCurrentUser()).setAlbums(home_albums);
					albumListView.getItems().add(albumName);
							
				}
					
				
			}
		}
		
		
		
	}

//----------------------------------------------------------------------------------------------------------------------
	/** This method helps user to delete the selected album. 
	 * @param event
	 * */
	public void deleteAlbum(ActionEvent event)
	{

		Alert alert;
		if(albumListView.getSelectionModel().isEmpty())
		{
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Please first select the album to Delete.");
			alert.showAndWait();
		}
		else
		{
			alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the Album?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			if (alert.getResult() == ButtonType.YES)
	        {
				int selectedID = albumListView.getSelectionModel().getSelectedIndex();
				albumListView.getItems().remove(selectedID);
				home_albums.remove(selectedID);
					
				
	        }
		}
		

	}

//----------------------------------------------------------------------------------------------------------------------

	/** This method helps user to rename the album.
	 * @param event
	 * */
	public void renameAlbum(ActionEvent event)
	{
		Alert alert;
		String rename = renameAlbum.getText();
		int selectedID = albumListView.getSelectionModel().getSelectedIndex();
		
		if(renameAlbum.getText().length() == 0 )
		{
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
	        alert.setHeaderText("Invalid input");
	        alert.setContentText("Please fill out the field.");
	        alert.showAndWait();
		}
		else
		{
			int count = 0;
			for(int j = 0; j < Photos.users.get(getIndexOfCurrentUser()).getAlbums().size(); j++)
			{

				if(Photos.users.get(getIndexOfCurrentUser()).getAlbums().get(j).getAlbum_name().equals(rename))
				{
					count++;
				}
			}
			if(count >0)
			{
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Invalid input");
				alert.setContentText("Album already exists in the system.");
				alert.showAndWait();
			}
			else if(count == 0)
			{
				home_albums.get(selectedID).setAlbum_name(rename);
				Photos.users.get(getIndexOfCurrentUser()).setAlbums(home_albums);
				albumListView.getItems().set(selectedID, rename);						
			}
		}
		
		
	}

//----------------------------------------------------------------------------------------------------------------------

	/** This method helps user to open the album. This will transfer user to a new scene where user can see the 
	 * detailed view of album.
	 * @param event
	 * */
	public void openAlbum(ActionEvent event)
	{
		Alert alert;
		if(albumListView.getSelectionModel().isEmpty())
		{
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Please first select the album to open.");
			alert.showAndWait();
		}
		else
		{
			int selectedID = albumListView.getSelectionModel().getSelectedIndex();
			albumIndex = selectedID;
			
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
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

//----------------------------------------------------------------------------------------------------------------------
	/** This method helps user to search for the photo. This method will transfer user to a new javaFX scene where 
	 * user will get the searched result of image. 
	 * @param event
	 * */
	public void searchPhotos(ActionEvent event)
	{
		Alert alert;
		if(searchphotos.getText().isEmpty())
		{
			alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText("Please fill out the search feild.");
			alert.showAndWait();
		}
		else
		{
			try
			{
				String searchthing = searchphotos.getText();
				
				if(searchthing.contains("-"))
				{
					if(!searchthing.contains("TO"))
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please search the date range like:  yy-mm-dd TO yy-mm-dd");
						alert.showAndWait();
					}
					else
					{
						search = searchphotos.getText();
						root = FXMLLoader.load(getClass().getResource("/fxml/SearchImage.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}
				}
				else if(searchthing.contains("OR"))
				{
					if(!searchthing.contains("="))
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please search the tags like:  person = tag OR location = tag");
						alert.showAndWait();
					}
					String [] split = searchthing.split("=");
					if(split.length < 3)
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please search the tags like:  person = tag OR location = tag");
						alert.showAndWait();
					}
					else
					{
						search = searchphotos.getText();
						root = FXMLLoader.load(getClass().getResource("/fxml/SearchImage.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}
				}
				else if(searchthing.contains("AND"))
				{
					if(!searchthing.contains("="))
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please search the tags like:  person = tag AND location = tag");
						alert.showAndWait();
					}
					String [] split = searchthing.split("=");
					if(split.length < 3)
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please search the tags like:  person = tag OR location = tag");
						alert.showAndWait();
					}
					else
					{
						search = searchphotos.getText();
						root = FXMLLoader.load(getClass().getResource("/fxml/SearchImage.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
					}
				}
				else
				{
					alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Please search Dates like:  yy-mm-dd TO yy-mm-dd \nPlease search the tags like:\nOption1: person = tag AND location = tag \nOption2: person = tag OR location = tag ");
					alert.showAndWait();
				}
			
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
//----------------------------------------------------------------------------------------------------------------------	
	/** This method will help user to log out from the application
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

	
//----------------------------------------------------------------------------------------------------------------------
	/** This method helps user to populate listview will album names for user to see.
	 * @param event
	 * */
	public void populateListView()
	{
		int albumSize = Photos.users.get(getIndexOfCurrentUser()).getAlbums().size();
		home_albums = Photos.users.get(getIndexOfCurrentUser()).getAlbums();
		for(int j = 0; j < albumSize; j++)
		{
			albumListView.getItems().add(Photos.users.get(getIndexOfCurrentUser()).getAlbums().get(j).getAlbum_name());
		}

		
	}

//----------------------------------------------------------------------------------------------------------------------
	/** This method returns the index of current user logged in from the system.
	 * 
	 * @reutrn int     will return the index of current user*/
	public static int getIndexOfCurrentUser()
	{
		for(int i = 0; i < Photos.users.size(); i++)
		{
			if(LoginController.name.getUsername().equals(Photos.users.get(i).getUsername()))
			{
				return i;
			}
		}
		
		return 0;
	}
}
