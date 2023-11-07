package model;
/** 
 * This class helps to write serialzed data to a file
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.*;
import java.util.ArrayList;

public class Serialization
{
	/** This method helps to write the data to a file as searialized data the serialized data 
	 */
	public static void serailzeUser(ArrayList<User> users)
	{
		User user = null;
		try
		{
				
			FileOutputStream fileOut = new FileOutputStream("src/applicationData/userList.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			for(int i =0; i < users.size(); i++)
			{
				user = users.get(i);
				out.writeObject(user);
				
			}
			out.close();
			fileOut.close();
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
