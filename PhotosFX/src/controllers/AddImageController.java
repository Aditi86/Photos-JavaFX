package controllers;

/** 
 * This class add's the image in the album 
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Images;

public class AddImageController implements Initializable
{
	@FXML
	TextField locationTag;
	@FXML
	TextArea peopleTag;
	@FXML
	TextField caption;
	@FXML
	Label update;
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	
	/** To store the image objects so we can store the uploaded files by user */
	ArrayList<Images> image = new ArrayList<>();

//----------------------------------------------------------------------------------------------------------------
	/** This method runs first always and initalize things that we want.
	 * @param arg0   
       @param arg1
       */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		
		image = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages();
		
	}
	
//----------------------------------------------------------------------------------------------------------------
	/** This method uploads the file in to javaFX application.
	 * @param event
	 * */
	public void uploadFile(ActionEvent event) throws ParseException
	{
		int count = 0;
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG image", "*.jpg"));
		File file = fc.showOpenDialog(stage);  
		
		if(file == null)
		{
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "You have not selected any file.");
			alert.showAndWait();
		}
		else
		{
			LocalDate date = LocalDate.now();
				
			if(image!=null)
			{
				for(int i = 0; i<image.size(); i++)
				{
					if(image.get(i).getUrl().equals(file.toString()))
					{
						count ++;
					}
				}
				if(count > 0)
				{
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "This phtots already exists in album this.");
					alert.showAndWait();

				}
				else
				{
					if(caption.getText().isEmpty())
					{
						Alert alert = new Alert(Alert.AlertType.INFORMATION, "You must fill out the caption");
						alert.showAndWait();
					}
					else
					{
						image.add(new Images(file.toString(), date)); 

						image.get(image.size()-1).setCaptions(caption.getText());
						
						if(!locationTag.getText().isEmpty())
						{
							if(locationTag.getText().contains(","))
							{
								Alert alert = new Alert(Alert.AlertType.INFORMATION, "You can only have one value for location Tag.");
								alert.showAndWait();
							}
							else
							{
								image.get(image.size()-1).setLocationTag(locationTag.getText());
							}
						}
						if(!peopleTag.getText().isEmpty())
						{
							image.get(image.size()-1).setPersonTag(peopleTag.getText());
						}
									
						Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(image);
						update.setText("The image has been successfully uploaded!");
					}
				}
			}
			else
			{				
				if(caption.getText().isEmpty())
				{
					Alert alert = new Alert(Alert.AlertType.INFORMATION, "You must fill out the caption");
					alert.showAndWait();
				}
				else
				{
					image.add(new Images(file.toString(), date)); 

					image.get(image.size()-1).setCaptions(caption.getText());
					if(!locationTag.getText().isEmpty())
					{
						image.get(image.size()-1).setLocationTag(locationTag.getText());
					}
					if(!peopleTag.getText().isEmpty())
					{
						image.get(image.size()-1).setPersonTag(peopleTag.getText());
					}
								
					Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(image);
					update.setText("The image has been successfully uploaded!");
				}
			}
			
		} 
		
				
	}

//--------------------------------------------------------------------------------------------------------
	/** This method helps user to navigate back to the previous scene of JavaFX application
	 * @param event
	 * */
	 
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

//--------------------------------------------------------------------------------------------------------
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
	
	

}
