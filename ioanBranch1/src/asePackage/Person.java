package asePackage;

/**
 * @author Ali
 * Person class that holds details about a person (first name and last name)
 * and methods to get and set first name and last name
 */
public class Person
{
	/**
	 * Instance variables
	 */
	private String firstName;
	private String lastName;

	/**
	 * Creates a person with a first name and last name
	 * @param firstName
	 * @param lastName
	 */
	public Person(String firstName, String lastName) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Gets the first name of the person
	 * @return the first name of the person
	 */
	public String getFirstName() 
	{
		return firstName;
	}
	/**
	 * Modifies the first name of the person
	 * @param firstName the first name of the person
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	/**
	 * Gets the last name of the person
	 * @return the last name of the person
	 */
	public String getLastName() 
	{
		return lastName;
	}
	/**
	 * Modifies the last name of the person
	 * @param lastName the last name of the person
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
}