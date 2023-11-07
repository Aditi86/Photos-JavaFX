package model;
/** 
 * This class helps user to create the object of Album
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable
{
	/** This is the seialVersionUID for serialization*/
	private static final long serialVersionUID = 2L;

	/** This stores the album name*/
	private String album_name;
	/** This stores the object of image classes which are in the album*/
	private ArrayList<Images> images = new ArrayList<>();

	/** This is the constructor to set the value of album name
	 * @param album_name     gets the name of the album*/
	public Album(String album_name)
	{
		this.album_name = album_name;
	}
	
	/** This method helps to return the string value of album name
	 * @return String value     album name*/
	
	public String getAlbum_name() {
		return album_name;
	}


	/** This method helps to set the name of album
	 * @param album_name     gets the name of the album
	 */
	public void setAlbum_name(String album_name) {
		this.album_name = album_name;
	}
	/** This method helps to return the ArrayList of Images object
	 * @return ArrayList<Images>    Images object*/
	public ArrayList<Images> getImages() {
		return images;
	}

	/** This method helps to set the images in the album
	 * @param images     ArrayList of Images object
	 */
	public void setImages(ArrayList<Images> images) {
		this.images = images;
	}
}
