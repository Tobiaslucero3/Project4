package project4.mapdata;

/**
 * A class which holds specific information about an observation
 * 
 * @author Tobias Lucero
 * @version 10-2-2018
 */
public class Observation extends AbstractObservation {
	/** The id of the station which had this value */
	private String stid;

	/** the value of the observation */
	private Double value;

	/**
	 * The basic constructor for the observation class
	 * 
	 * @param value the value of the observation
	 * @param stid  the name of the station which had the observation
	 */
	public Observation(double value, String stid) {
		super(value >= -900);
		this.stid = stid;
		this.value = value;
	}

	/**
	 * Getter for the value of the observation
	 * 
	 * @return double value representing the value of the observation
	 */
	public Double getValue() {
		return value;
	}

	/**
	 * Getter for the stid of the observation
	 * 
	 * @return String representing the name of the station
	 */
	public String getStid() {
		return stid;
	}

	/**
	 * Getter for the validity of the observation i.e value >-900
	 * 
	 * @return boolean representing the validity of the observation
	 */
	public boolean isValid() {
		return super.valid;
	}

	/**
	 * A concrete way of representing the value of the station with station name
	 * 
	 * @return String in format of "The station <station name> had a value of
	 *         <value>
	 */
	public String toString() {
		return String.format("The station %s had a value of %.02f", stid, value);
	}
}
