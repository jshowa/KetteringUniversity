import ArrayADT.*;

/**
*
*Author: Jacob S. Howarth
*
*Assignment #2
*
*TennisPlayer.java
*
*This class defines methods and variables for a tennis player
*
*/


public class TennisPlayer {
	
	private String playerName;
	private String grandSlamTitleNum;
	private String countryOfOrigin;
	private String playerGender;
	
	/**
	*
	*Constructor used to create a tennis player instance with no pre-set data
	*
	*/
	public TennisPlayer() {
		
		playerName = "";
		grandSlamTitleNum = "";
		countryOfOrigin = "";
		playerGender = "";
		
	}
	
	/**
	*
	*Constructor used to create a tennis player instance with a given name, number of grand slams won,
	*country of origin, and gender
	*
	* @param playerName   name of a tennis player
	* @param grandSlamTitleNum   number of grand slam titles won
	* @param countryOfOrigin   country of birth/origin
	* @param playerGender   gender of the tennis player
	*
	*/
	public TennisPlayer(String playerName, String grandSlamTitleNum, String countryOfOrigin, String playerGender) {
		
		this.playerName = playerName;
		this.grandSlamTitleNum = grandSlamTitleNum;
		this.countryOfOrigin = countryOfOrigin;
		this.playerGender = playerGender;
		
	}
	
	/**
	*
	*Accessor for the name of a tennis player instance
	*
	* @return   The tennis player instances name
	*
	*/
	public String getPlayerName() {
		
		return playerName;
	
	}
	
	/**
	*
	*Accessor for the number of grand slam titles won by a tennis player instance
	*
	* @return   number of titles won
	*
	*/
	public String getGrandSlamTitleNum() {
		
		return grandSlamTitleNum;
		
	} 
	
	/**
	*
	*Accessor for a tennis player instances country of origin/birth
	*
	* @return   String containing the name of a country
	*
	*/
	public String getCountryOfOrigin() {
		
		return countryOfOrigin;
		
	} 
	
	/**
	*
	*Accessor for a tennis player instances gender
	*
	* @return   String containing the initial of the gender, (F) for female, (M) for male
	*
	*/
	public String getPlayerGender() {
		
		return playerGender;
		
	}
	
	/**
	*
	*Mutator for a tennis player instances name
	*
	* @param playerName   sets the name data field of a tennis player instance to a given name
	*
	*/
	public void setPlayerName(String playerName) {
	
		this.playerName = playerName;
		
	}
	
	/**
	*
	*Mutator for a tennis player instances number of grand slam titles won
	*
	* @param grandSlamTitleNum   sets the number of grand slam titles won by a tennis player instance
	*
	*/
	public void setGrandSlamTitleNum(String grandSlamTitleNum) {
	
		this.grandSlamTitleNum = grandSlamTitleNum;
	
	}
	
	/**
	*
	*Mutator for a tennis player instances country of birth/origin
	*
	* @param countryOfOrigin   sets the country of origin for a tennis player instance
	*
	*/
	public void setCountryOfOrigin(String countryOfOrigin) {
	
		this.countryOfOrigin = countryOfOrigin;
		
	}
	
	/**
	*
	*Mutator for a tennis player instances gender
	*
	* @param playerGender   sets the gender of a tennis player instance
	*
	*/
	public void setPlayerGender(String playerGender) {
	
		this.playerGender = playerGender;
		
	}
	
	
	/**
	*
	*Determines if two tennis player objects are equal based on their instance data
	*
	* @param t   tennis player instance to compare with the calling object
	*
	* @return   true if the instance data of the parameter is the same as the instance data of the calling object, false if otherwise
	*
	*/
	public boolean equals(TennisPlayer t) {
	
		if ((this.playerName).equalsIgnoreCase(t.playerName) && (this.grandSlamTitleNum).equals(t.grandSlamTitleNum) &&
					(this.countryOfOrigin).equalsIgnoreCase(t.countryOfOrigin) && (this.playerGender).equalsIgnoreCase(t.playerGender))
			return true;
		else 
			return false;	
	
	}
	
	/**
	*
	*Converts the instance data of a tennis player object to type String
	*
	* @return   String containing the instance data of a tennis player object
	*
	*/
	public String toString() {
		
		return getPlayerName() + "\n" + getGrandSlamTitleNum() + "\n" + getCountryOfOrigin() + "\n" +
					getPlayerGender() + "\n";
	
	}
}