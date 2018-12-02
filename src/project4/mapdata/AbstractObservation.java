package project4.mapdata;

/**
 * An abstract class which is used for the implementation of the observation
 * class
 * 
 * @author Tobias Lucero
 * @version 10-3-2018
 */
public abstract class AbstractObservation {
	
	/**
	 * a boolean which represents whether the observation is valid i.e. value>-900
	 */
	protected boolean valid;

	/**
	 * Generic constructor for the abstract observation class. Referenced from
	 * Observation
	 * 
	 * @param valid whether the observation is valid i.e. value>-900
	 */
	public AbstractObservation(boolean valid) {
		this.valid = valid;
	}

	/**
	 * An abstract method to be defined in the child of the class
	 * 
	 * @return boolean representing the validity of the observation
	 */
	public boolean isValid()
	{
		return this.valid;
	}

}
