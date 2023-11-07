package model;

/** 
 * This class helps to create object of a user.
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	
	/** This is the seialVersionUID for serialization*/
	private static final long serialVersionUID = 1L;
	
	/** This holds the arrayList of albums which user has */
	private ArrayList<Album> albums = new ArrayList<>();
	
	/** This is the string for user's name*/
	private String name;
	/** This is the string for user's username*/
	private String username;
	
	
	/** Constructor helps to set the values for name, username and the Album arraylist
	 * @param name      user's name
	 * @param username   user's username*/
	public User(String name, String username)
	{
 		this.name = name;
		this.username = username;
		this.setAlbums(albums);
	}
	
	
	/** This method helps to get the string value of user's name
	 * @return void*/
	public String getName() {
		return name;
	}
	/** This method helps to get the string value of user's username
	 * @return void*/
	public String getUsername() {
		return username;
	}

	/** This method helps to get the ArrayList of album
	 * @return void*/
	public ArrayList<Album> getAlbums() {
		return albums;
	}

	/** This method helps to set the AraryList of album
	 * @return void*/
	public void setAlbums(ArrayList<Album> albums) {
		this.albums = albums;
	}
	

	
}
