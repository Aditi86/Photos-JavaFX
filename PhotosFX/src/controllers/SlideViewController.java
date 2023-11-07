package controllers;

/** 
 * This class helps user to see the slide view of the album
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;

public class SlideViewController implements Initializable
{
	@FXML
	public ImageView myImageView;
	
	/** this is the variable stores the current index of the image is displaying*/
	private int currentIndex = 0;
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set  the root*/
	private Parent root;
	
	/** This Album object to store the object of album*/
	Album album;
	
	
	/** This method runs first always and initalize things that we want.
	 * @param arg0   
       @param arg1
       */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		album = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex);
		
		
		try {
			setImage(currentIndex);
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		}
	}
	/** This method helps user to see the next photo in album
	 * @param event
	 */
	public void next(ActionEvent event) throws MalformedURLException
	{
		currentIndex = (currentIndex+1)%album.getImages().size();
		setImage(currentIndex);
	}
	/** This method helps user to see the previous photo in album
	 * @param event
	 */
	public void previous(ActionEvent event) throws MalformedURLException
	{
		currentIndex = (currentIndex - 1 + album.getImages().size()) % album.getImages().size();
        setImage(currentIndex);
	}
	/** This method sets the the current slideview photo
	 * @param event
	 */
	public void setImage(int index) throws MalformedURLException
	{
		String imageUrl = album.getImages().get(index).getUrl();
		if(imageUrl!=null)
		{
			 File file = new File(imageUrl);
			 @SuppressWarnings("deprecation")
			 Image myImage = new Image(file.toURL().toString());
			 myImageView.setImage(myImage);
		}
		
	}
	/** This method sends back user to the previous page of javaFX application
	 * @param event
	 */
	public void goBack(ActionEvent event)
	{
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
	
	/** This method logs out the user form the application
	 * @param event
	*/
	
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
}
