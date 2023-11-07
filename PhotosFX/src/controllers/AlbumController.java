package controllers;

/** 
 * This class helps users to control their album view.
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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

public class AlbumController implements Initializable
{
	@FXML
	Label albumName;
	@FXML
	TextField caption;
	@FXML
	TextField locationTag;
	@FXML
	TextArea peopleTag;
	@FXML
	Label messg;
	@FXML
	ListView <String> imageListView = new ListView<>();
	@FXML
	ChoiceBox <String> copyPhoto;
	@FXML
	ChoiceBox <String> movePhoto;
	
	/** This arrayList store the object of Images so we can easily manipulate around*/
	ArrayList<Images> getImage = new ArrayList<>();
	/** This will get us the selected image index*/
	static int selectedImageIndex;
	/** to set the stage in javaFX*/
	private Stage stage;
	/** to set the scene in javaFX*/
	private Scene scene;
	/** to set lonch the root*/
	private Parent root;
	


	
	
//--------------------------------------------------------------------------------------------------------
	/** This method runs first always and initalize things that we want.
	 * @param arg0   
       @param arg1
       */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		getImage = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages();
		if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().isEmpty())
		{
			for(int i = 0; i< Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().size(); i++)
			{
				if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getAlbum_name().equals(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).getAlbum_name()))
				{
					copyPhoto.getItems().add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).getAlbum_name());
					movePhoto.getItems().add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).getAlbum_name());
				}
				
			}
		}
	
		
		albumName.setText("Album: "+ Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getAlbum_name());
		populateListView();
	}
	
//---------------------------------------------------------------------------------------------------------
	/** This method helps user to navigate back to the previous scene of JavaFX application
	 * @param event
	 * @return void*/
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
	
//---------------------------------------------------------------------------------------------------------
	/** This method transfers user to another javaFX scene to add the images.
	 * @param event
	 * */
	public void addImage(ActionEvent event)
	{
		try
		{
			root = FXMLLoader.load(getClass().getResource("/fxml/AddImage.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}catch (IOException e)
		{
			e.printStackTrace();
		}

		
	}
	
//---------------------------------------------------------------------------------------------------------
	/** This method delted the images selected by the user
	 * @param event
	 **/
	public void deleteImage(ActionEvent event) throws IOException
	{
		if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
		{
			Alert alert;
			if(imageListView.getSelectionModel().isEmpty())
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select the image to delete.");
				alert.showAndWait();
			}
			else
			{
				int i = imageListView.getSelectionModel().getSelectedIndex();
				alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want delete the image?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES)
				{
					getImage.remove(i);
					Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(getImage);
					
					root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
					stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
				}
			}
		}
		else
		{
			messg.setText("There are no images to delete.");
		}
		
		
		
	}
	
//---------------------------------------------------------------------------------------------------------
	/** This method helps to recaption images selected by the user
	 * @param event
	 **/
	public void reCaption(ActionEvent event) throws IOException
	{
		if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
		{
			Alert alert;

			if(caption.getText().isEmpty())
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please fill out the caption field.");
				alert.showAndWait();

			}
			if(imageListView.getSelectionModel().isEmpty())
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select the image for caption.");
				alert.showAndWait();
			}
			
			else if(!caption.getText().isEmpty() && ! imageListView.getSelectionModel().isEmpty())
			{
				int i = imageListView.getSelectionModel().getSelectedIndex();
				
				messg.setText("Caption has been successfully added.");
				getImage.get(i).setCaptions(caption.getText());
				getImage.get(i).setDate(LocalDate.now());
				Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(getImage);
				
				root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}
		else
		{
			messg.setText("There are no images.");
		}
		
	}

//---------------------------------------------------------------------------------------------------------
	/** This method helps to upadte the tag of selected images by user and if will catch the error if user enters something wrong.
	 * @param event
	 * */
	public void upadteTag(ActionEvent event) throws IOException
	{
		if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
		{
			int i = 0;
			Alert alert;
			if(imageListView.getSelectionModel().isEmpty() || locationTag.getText().isEmpty() && peopleTag.getText().isEmpty())
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select the image and at least fill one of the tag fields.");
				alert.showAndWait();
				
			}
			else if(!locationTag.getText().isEmpty() || !peopleTag.getText().isEmpty())
			{
				i = imageListView.getSelectionModel().getSelectedIndex();
				if(!locationTag.getText().isEmpty() && i > -1)
				{
					if(locationTag.getText().contains(","))
					{
						alert = new Alert(Alert.AlertType.INFORMATION, "You can only have one value for location Tag.");
						alert.showAndWait();
					}
					else
					{
						messg.setText("Tags has been successfully updated.");
						getImage.get(i).setLocationTag(locationTag.getText());
						getImage.get(i).setDate(LocalDate.now());

					}
				}
				if(!peopleTag.getText().isEmpty() && i > -1)
				{
					getImage.get(i).setPersonTag(peopleTag.getText());
					getImage.get(i).setDate(LocalDate.now());

				}
				
				Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(getImage);
				root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();

			}
		}
		else
		{
			messg.setText("There are no images.");
		}
		
	}
	
//---------------------------------------------------------------------------------------------------------
	/** This image forwards user to a new javaFX scene where user can see the image selected image as big image.
	 * @param event
	 */
	public void displayImage(ActionEvent event) throws IOException
	{
		Alert alert;
		if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
		{
			if(imageListView.getSelectionModel().isEmpty())
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select the image to view");
				alert.showAndWait();	
			}
			else
			{
				selectedImageIndex = imageListView.getSelectionModel().getSelectedIndex();
				DisplayImageController.setImagePath(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().get(selectedImageIndex).getUrl());
				root = FXMLLoader.load(getClass().getResource("/fxml/DisplayPhoto.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}
		else
		{
			messg.setText("There is no image in the album to display.");
		}
		
		
	}
//---------------------------------------------------------------------------------------------------------
	/** This method helps to delete the tag of selected image by the user
	 * @param event
	 */
		public void deleteTags(ActionEvent event) throws IOException
		{
			if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
			{
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete tags?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES)
		        {
					int i = 0;
					if(imageListView.getSelectionModel().isEmpty())
					{
						alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setContentText("Please select image to delete tags.");
						alert.showAndWait();
						
					}
					else
					{
						i = imageListView.getSelectionModel().getSelectedIndex();
						getImage.get(i).setLocationTag("");
						getImage.get(i).setPersonTag("");
						
						Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).setImages(getImage);
						root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
						stage = (Stage)((Node)event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();

					}
		        }
			}
			else
			{
				messg.setText("There are no Imaegs.");
			}
			
		}
//---------------------------------------------------------------------------------------------------------
		/** This method will forward user to a new javsaFX scene where user can view album images as slide view.
		 * @param event
		 */		
		public void slideView(ActionEvent event) throws IOException
		{
			if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
			{
				root = FXMLLoader.load(getClass().getResource("/fxml/SlideView.fxml"));
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else
			{
				messg.setText("There is no image in the album to have slide view.");
			}
			
		}
//---------------------------------------------------------------------------------------------------------
		/** This method helps to copy photo from one album to another.
		 * @param event
		*/	
		public void copyPhoto(ActionEvent event)
		{
			Alert alert;
			User mm = Photos.users.get(HomePageController.getIndexOfCurrentUser());
			int selectedID = imageListView.getSelectionModel().getSelectedIndex();
			String albumname = copyPhoto.getValue();
			ArrayList<Images> copy;
			
			if(imageListView.getSelectionModel().isEmpty() || copyPhoto.getValue()==null)
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select image and album of your choice.");
				alert.showAndWait();	
			}
			else
			{
				if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
				{

					for(int i = 0; i < mm.getAlbums().size(); i++)
					{
						if(mm.getAlbums().get(i).getAlbum_name().equals(albumname))
						{
							if(!mm.getAlbums().get(i).getImages().isEmpty())
							{
								int count = 0;
								for(int j = 0; j < mm.getAlbums().get(i).getImages().size(); j++)
								{
									if(mm.getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID).equals(mm.getAlbums().get(i).getImages().get(j)))
									{
										count++;
									}
								}
								if(count > 0)
								{
									messg.setText("The same photo already exists in the chosen album.");
								}
								else
								{
									messg.setText("The image has been successfully copied.");
									copy = mm.getAlbums().get(i).getImages();
									copy.add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID));
									Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).setImages(copy);
								}
							}
							else
							{
								messg.setText("The image has been successfully copied.");
								copy = mm.getAlbums().get(i).getImages();
								copy.add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID));
								Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).setImages(copy);
							}
						}
							
					}
				}
				else
				{
					messg.setText("There are no images.");
				}
			}
			
		}

//---------------------------------------------------------------------------------------------------------
		/** This method helps to move photo from one album to another.
		 * @param event
		 */
		public void moveThePhoto(ActionEvent event) throws IOException
		{
			Alert alert;
			User mm = Photos.users.get(HomePageController.getIndexOfCurrentUser());
			int selectedID = imageListView.getSelectionModel().getSelectedIndex();
			ArrayList<Images> copy;
			
			String albumname = movePhoto.getValue();
			if(imageListView.getSelectionModel().isEmpty() || movePhoto.getValue()==null)
			{
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Please select image and album of your choice.");
				alert.showAndWait();	
			}
			else
			{
				if(!Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().isEmpty())
				{
					
					for(int i = 0; i < mm.getAlbums().size(); i++)
					{
						if(mm.getAlbums().get(i).getAlbum_name().equals(albumname))
						{
							if(!mm.getAlbums().get(i).getImages().isEmpty())
							{
								int count = 0;
								for(int j = 0; j < mm.getAlbums().get(i).getImages().size(); j++)
								{
									if(mm.getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID).equals(mm.getAlbums().get(i).getImages().get(j)))
									{

										count++;
									}
								}
								if(count > 0)
								{
									messg.setText("The same photo already exists in the chosen album.");
								}
								else
								{
									messg.setText("The photo has been successfully moved.");
									copy = mm.getAlbums().get(i).getImages();
									copy.add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID));
									mm.getAlbums().get(i).setImages(copy);
									getImage.remove(selectedID);
									mm.getAlbums().get(HomePageController.albumIndex).setImages(getImage);
									
									root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
									stage = (Stage)((Node)event.getSource()).getScene().getWindow();
									scene = new Scene(root);
									stage.setScene(scene);
									stage.show();
									
								}
							}
							else
							{
								messg.setText("The photo has been successfully moved.");
								copy = mm.getAlbums().get(i).getImages();
								copy.add(Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex).getImages().get(selectedID));
								Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(i).setImages(copy);
								getImage.remove(selectedID);
								mm.getAlbums().get(HomePageController.albumIndex).setImages(getImage);
								
								root = FXMLLoader.load(getClass().getResource("/fxml/Album_Page.fxml"));
								stage = (Stage)((Node)event.getSource()).getScene().getWindow();
								scene = new Scene(root);
								stage.setScene(scene);
								stage.show();
							}
						}
							
					}
					
					
				}
				else
				{
					messg.setText("There are no images.");
				}
			}
			
		}

//---------------------------------------------------------------------------------------------------------
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

//---------------------------------------------------------------------------------------------------------
	/** This method helps to populate the ListView of images when user opens the album.
	 * @param event
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
									imageView.setFitWidth(150);
									imageView.setFitHeight(120);
									imageView.setImage(image);
									
									Album a = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex);
									ArrayList<Images> photos = a.getImages();
									
									for(Images im: photos)
									{
										if(item.equals(im.getUrl()))
										{
											if(im.getCaptions()==null)
											{
												caption.setText("Caption: N/A");
												date.setText("Date: "+ im.getDate());
											}
											else
											{
												caption.setText("Captions: "+ im.getCaptions());
												date.setText("Date: "+ im.getDate());
											}
										}
									}
									HBox hb = new HBox();
									VBox vb = new VBox();
									vb.getChildren().addAll(caption, date);
									vb.setPadding(new Insets(40, 0, 0, 40));
									hb.getChildren().addAll(imageView, vb);
									setGraphic(hb);
									
								}
							}
						};
					}
			
		});
		
		Album album = Photos.users.get(HomePageController.getIndexOfCurrentUser()).getAlbums().get(HomePageController.albumIndex);
		for(int i = 0; i < album.getImages().size(); i++)
		{
			imageListView.getItems().add(album.getImages().get(i).getUrl());
		}

	}

}
