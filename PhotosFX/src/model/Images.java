package model;

/** 
 * This is image class to store the images.
 * @author Aditi Patel
 * @author Aakaash Prakash Hemdev */ 

import java.io.Serializable;
import java.time.LocalDate;

public class Images implements Serializable
{
	/** This is the seialVersionUID for serialization*/
	private static final long serialVersionUID = 3L;
	
	/** This url String stores the path of the image*/
	private String url;
	/** This locationTag String stores location Tag of the image*/
	private String locationTag;
	/** This captions String stores captions of the image*/
	private String captions;
	/** This personTag String stores personTag of the image*/
	private String personTag ;
	/** This date object of LocalDate stores current Date.*/
	private LocalDate date;
	
	/** This constructor helps to set the the values of the image class
	 * @param url    stores the image path
	 * @param date   stores the current date of the image*/
	public Images(String url, LocalDate date)
	{
		this.url = url;
		this.date = date;
		this.setCaptions(captions);
		this.setLocationTag(locationTag);
		this.setPersonTag(personTag);
	}
	
	/** This method helps to return the string value of image path
	 * @return String value    image Path*/
	public String getUrl() {
		return url;
	}

	/** This method helps to set the value for the image path
	     */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/** This method gets the string value of location Tag
	 * @return String value    Location tag*/
	public String getLocationTag() {
		return locationTag;
	}
	
	/** This method helps to set the value for the location Tag
	    */
	public void setLocationTag(String locationTag) {
		this.locationTag = locationTag;
	}

	/** This method helps to get the value of personTag
	 * @return String value     personTag*/
	public String getPersonTag() {
		return personTag;
	}
	/** This method helps to set the value for the person Tag
	     */
	public void setPersonTag(String personTag) {
		this.personTag = personTag;
	}

	/** This method helps to return the LocalDate value of imageDate
	 * @return LocalDate    current Date*/
	public LocalDate getDate() {
		return date;
	}

	/** This method helps to set the value for the current Date
	 * @return void     */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/** This method helps to return the string value of captions
	 * @return String value     album name*/
	public String getCaptions() {
		return captions;
	}

	/** This method helps to set the string value of captions
	    */
	public void setCaptions(String captions) {
		this.captions = captions;
	}

	
	
	
}
