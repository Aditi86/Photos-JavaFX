package controllers;
/** 
 * This class helps usre to display the selected image.
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Album;

public class DisplayImageController implements Initializable
{
	@FXML
	ImageView myImageView;
	@FXML
	Label date;
	@FXML
	Label caption;
	@FXML
	Label locationTag;;
	@FXML
	Label peopleTag;
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	/** imagePath stores the path to the image which will be displayed.*/
	private static String imagePath;
	
	/** This method helps to set the image path selected 
	 * @param imagePath   contains the path of the selected image*/
	public static void setImagePath(String imagePath)
	{
		DisplayImageController.imagePath = imagePath;
	}
	
	/** This method runs first always and initalize things that we want this time it is helping to display the image.
	 * @param arg0   
       @param arg1 */
	@SuppressWarnings("deprecation")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		Album album = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex);
		if(!album.getImages().isEmpty())
		{
			if(imagePath != null)
			{
				File file = new File(imagePath);
				Image myImage;
				try {
					myImage = new Image(file.toURL().toString());
					myImageView.setImage(myImage);
					
					date.setText(album.getImages().get(AlbumController.selectedImageIndex).getDate().toString());
					caption.setText(album.getImages().get(AlbumController.selectedImageIndex).getCaptions());
					locationTag.setText(album.getImages().get(AlbumController.selectedImageIndex).getLocationTag());
					peopleTag.setText(album.getImages().get(AlbumController.selectedImageIndex).getPersonTag());
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		
		
	}
	/** This method helps user to navigate back to the previous scene of JavaFX application
	 * @param event
	 **/
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
	/** This method helps user to logout of the application.
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
