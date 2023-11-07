package controllers;

/** 
 * This class gets the search result of the user and shows  it.
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import application.Photos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Images;
import model.User;

public class SearchController implements Initializable
{
	@FXML
	ListView<String> imageListView = new ListView<>();
	@FXML
	TextField newAlbumName;
	@FXML 
	Label messg;
	@FXML 
	Label searchPrompt;
	
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	
	/** User instance to store the user object.*/
	User user;
	
	/** ArrayList of Images to store the searched result of user in form of objects of images class*/
	private ArrayList<Images> result = new ArrayList<>();
	
	/** This method runs first always and initalize things that we want.
	 * @param arg0   
       @param arg1
      */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		user = Photos.users.get(HomePageController.getIndexOfCurrentUser()); 
		searchPrompt.setText("You Searched for: " +HomePageController.search);
		search();
		populateListView();
	}

//---------------------------------------------------------------------------------------------------------
	
	/** This method helps user to create album from the search result 
	 * @param event
	*/
	public void createAlbum(ActionEvent event)
	{
		String albumName = newAlbumName.getText();
		Alert alert; 
		
		if(newAlbumName.getText().length() == 0 )
		{
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
	        alert.setHeaderText("Invalid input");
	        alert.setContentText("Please fill out the field.");
	        alert.showAndWait();
		}
		else
		{
			if(user.getAlbums().isEmpty())
			{
				messg.setText("The album has been created successfully.");
				HomePageController.home_albums.add(new Album(albumName));
				user.setAlbums(HomePageController.home_albums);
				user.getAlbums().get(user.getAlbums().size()-1).setImages(result);
			}
			else
			{
				int count = 0;
				for(int j = 0; j < user.getAlbums().size(); j++)
				{

					if(user.getAlbums().get(j).getAlbum_name().equals(albumName))
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
					messg.setText("The album has been created successfully.");
					HomePageController.home_albums.add(new Album(albumName));
					user.setAlbums(HomePageController.home_albums);
					user.getAlbums().get(user.getAlbums().size()-1).setImages(result);
							
				}
					
				
			}
		}
		
	}

//---------------------------------------------------------------------------------------------------------
	/** This method helps to search for the asked information from the usre and store it into the result ArrayList.
	 * @param event
	 */
	public void search()
	{
		Alert alert;
		String promptString = HomePageController.search;		
		if(promptString.contains("-"))
		{
			LocalDate firstDate, secondDate;
			if(promptString.toLowerCase().contains("to"))
			{
				String [] dates = promptString.toLowerCase().trim().split("to");
				
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd");
					
					try
					{
						firstDate = LocalDate.parse(dates[0].trim(), formatter);
						secondDate = LocalDate.parse(dates[1].trim(), formatter);
						
					
						if(!user.getAlbums().isEmpty())
						{
							for(int i = 0; i < user.getAlbums().size(); i++)
							{
								if(!user.getAlbums().get(i).getImages().isEmpty())
								{
									for(int j = 0; j < user.getAlbums().get(i).getImages().size(); j++)
									{
										if(user.getAlbums().get(i).getImages().get(j).getDate().isAfter(firstDate) && user.getAlbums().get(i).getImages().get(j).getDate().isBefore(secondDate))
										{
											result.add(user.getAlbums().get(i).getImages().get(j));
										}
										if(user.getAlbums().get(i).getImages().get(j).getDate().equals(firstDate) || user.getAlbums().get(i).getImages().get(j).getDate().equals(secondDate))
										{
											result.add(user.getAlbums().get(i).getImages().get(j));
										}
									}
								}
								else
								{
									messg.setText("No Photos were found: if Image empty");
								}
							}
						}
						else
						{
							messg.setText("No Photos were found: if Album Empty");
						}	
					}
					catch(DateTimeParseException e)
					{
						alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Invalid input");
						alert.setContentText("Date syntax is invalid: please next time try: yy-mm-dd");
						alert.showAndWait();
						
					}
					
				}
				
				
		}
		else if(promptString.contains("AND"))
		{
			ArrayList <String> parameter = new ArrayList<>();
			String [] split = promptString.toLowerCase().split("=");
			String [] splitAND = split[1].split("and");
			parameter.add(split[0]);
			parameter.add(splitAND[0]);
			parameter.add(splitAND[1]);
			parameter.add(split[2]);
			
			String containLocation, containPerson;
			if(!user.getAlbums().isEmpty())
			{
				for(int i = 0; i < user.getAlbums().size(); i++)
				{
					if(!user.getAlbums().get(i).getImages().isEmpty())
					{
						for(int j = 0; j < user.getAlbums().get(i).getImages().size(); j++)
						{
							if(user.getAlbums().get(i).getImages().get(j).getLocationTag()!=null && user.getAlbums().get(i).getImages().get(j).getPersonTag()!= null)
							{
								containLocation = user.getAlbums().get(i).getImages().get(j).getLocationTag().toLowerCase();
								containPerson = user.getAlbums().get(i).getImages().get(j).getPersonTag().toLowerCase();
								if(parameter.get(0).contains("per") && parameter.get(2).contains("loc"))
								{

									if(containPerson.contains(parameter.get(1).trim()) && parameter.get(3).contains(containLocation)) //&& parameter[2].contains(containLocation)
									{
										result.add(user.getAlbums().get(i).getImages().get(j));
									}
								}
								else if(parameter.get(0).contains("loc") && parameter.get(2).contains("per"))
								{
									if(containPerson.contains(parameter.get(3).trim()) && parameter.get(1).contains(containLocation))//&& parameter[1].contains(containLocation)))
									{
										result.add(user.getAlbums().get(i).getImages().get(j));									
									}
								}
								else
								{
									messg.setText("No Photos were found.");
								}
							}
							else
							{
								messg.setText("No Photos were found.");
							}
							
							
						}
					}
					else
					{
						messg.setText("No Photos were found.");
					}
				}
			}
			else
			{
				messg.setText("No Photos were found.");
			}
		}
		else if(promptString.contains("OR"))
		{
			ArrayList <String> parameter = new ArrayList<>();
			String [] split = promptString.toLowerCase().split("=");
			String [] splitAND = split[1].split("or");
			parameter.add(split[0]);
			parameter.add(splitAND[0]);
			parameter.add(splitAND[1]);
			parameter.add(split[2]);
			
			String containLocation, containPerson;
			if(!user.getAlbums().isEmpty())
			{
				for(int i = 0; i < user.getAlbums().size(); i++)
				{
					if(!user.getAlbums().get(i).getImages().isEmpty())
					{
						for(int j = 0; j < user.getAlbums().get(i).getImages().size(); j++)
						{
							if(user.getAlbums().get(i).getImages().get(j).getLocationTag()!=null && user.getAlbums().get(i).getImages().get(j).getPersonTag()!= null)
							{
								containLocation = user.getAlbums().get(i).getImages().get(j).getLocationTag().toLowerCase();
								containPerson = user.getAlbums().get(i).getImages().get(j).getPersonTag().toLowerCase();
								if(parameter.get(0).contains("per") && parameter.get(2).contains("loc"))
								{

									if(containPerson.contains(parameter.get(1).trim()) || parameter.get(3).contains(containLocation))
									{
										result.add(user.getAlbums().get(i).getImages().get(j));
									}
								}
								else if(parameter.get(0).contains("loc") && parameter.get(2).contains("per"))
								{
									if(containPerson.contains(parameter.get(3).trim()) || parameter.get(1).contains(containLocation))
									{
										result.add(user.getAlbums().get(i).getImages().get(j));									
									}
								}
								else
								{
									messg.setText("No Photos were found.");
								}
							}
							else
							{
								messg.setText("No Photos were found.");
							}
							
							
							
						}
					}
					else
					{
						messg.setText("No Photos were found.");
					}
				}
			}
			else
			{
				messg.setText("No Photos were found.");
			}
		}
	}
	
//---------------------------------------------------------------------------------------------------------

	/** This method helps user to log out from the application
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
	
//---------------------------------------------------------------------------------------------------------
	/** This method helps user to go back to the previouse javaFX page.
	 * @param event
	*/
		public void goBack(ActionEvent event)
		{
			try {
				root = FXMLLoader.load(getClass().getResource("/fxml/Home_Page.fxml"));
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
		/** This method helps user to populate the listview with the result of searching . 
		 */
		public void populateListView()
		{
			imageListView.setCellFactory(new Callback < ListView<String>, ListCell<String> > ()
			{

						@Override
						public ListCell<String> call(ListView<String> arg0) {
							return new ListCell<String>()
							{
								private ImageView imageView = new ImageView();
								
								protected void updateItem(String item, boolean empty)
								{
									super.updateItem(item, empty);
									
									if(empty || item == null)
									{
										setText(null);
										setGraphic(null);
									}
									else
									{
										Image image = new Image("file:"+item);
										Label caption = new Label();
										Label date = new Label();
										Label location = new Label();
										Label people = new Label();
										imageView.setFitWidth(150);
										imageView.setFitHeight(120);
										imageView.setImage(image);
										
										Album a = null;
										ArrayList<Images> photos;
										
										for(int b = 0; b < Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().size(); b++)
										{
											a = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(b);
											photos = a.getImages();
											for(Images im: photos)
											{
												if(item.equals(im.getUrl()))
												{
													caption.setText("Captions: "+ im.getCaptions());
												    date.setText("Date: "+ im.getDate());
												    location.setText("Location Tag: "+ im.getLocationTag());
												    people.setText("People Tag: "+ im.getPersonTag());
													
												}
											}
											HBox hb = new HBox();
											VBox vb = new VBox();
											vb.getChildren().addAll(caption, date, location, people);
											vb.setPadding(new Insets(20, 0, 0, 40));
											hb.getChildren().addAll(imageView, vb);
											setGraphic(hb);
										}
										
										
										
									}
								}
							};
						}
				
			});
			
			for(int i = 0; i < result.size(); i++)
			{
				imageListView.getItems().add(result.get(i).getUrl());
			}

		}

}
