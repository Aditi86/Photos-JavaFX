package model;

/** 
 * This class helps to deserialzed the data from the file
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import application.Photos;

public class Deserialization
{
	/** This method helps to read from the data file and deserialzed the serialized data 
	 */
	public static void deserializeUser()
	{
		User user = null;
		File file = new File("src/applicationData/userList.ser");
		if(file.length() != 0)
		{
			try 
			{
				FileInputStream fileIn = new FileInputStream("src/applicationData/userList.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				
				while(true)
				{
					try
					{
						user = (User)in.readObject();
						Photos.users.add(user);
						
					}catch(EOFException e)
					{
						break;
					}
				}
				
				in.close();
				fileIn.close();
			} 
			catch (IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		
		
	}
}
