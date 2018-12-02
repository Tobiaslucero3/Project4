package project4.mapdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;

import java.util.TreeMap;

/**
 * A class which holds data about certain weather statistics from a certain day,
 * hour, minute and second of the data.
 * 
 * @author Tobias Lucero
 * @version 10-22-2018
 *
 */
public class MapData {
	/**
	 * A HashMap that stores all the data from the input. Stored by a String key,
	 * either "TAIR", "TA9M" or "SRAD"
	 * 
	 * Each key is assigned to a list of the observations from the data for that
	 * specific measurement.
	 */
	private HashMap<String, ArrayList<Observation>> dataCatalog;

	/**
	 * An EnumMap which stores all the maximum, minimum or average Statistics from
	 * the data.
	 * 
	 * The EnumMap uses a key of StatsType. The key is either AVERAGE, MINIMUM or
	 * MAXIMMUM The key used links to a TreeMap which it's keys are either "TAIR",
	 * "TA9M" or "SRAD". Each key is assigned to the minimum, average, maximum
	 * statistics of the specified measurement.
	 */
	private EnumMap<StatsType, TreeMap<String, Statistics>> statistics;

	/**
	 * A TreeMap used to store the specific indexes of which row specific
	 * measurement can be found (Based on the header; "TAIR", "TA9M" or "SRAD)
	 */
	private TreeMap<String, Integer> paramPositions;

	/**
	 * A int representation of the maximum number of stations data measurements can
	 * have.
	 */
	private int NUMBER_OF_MISSING_OBSERVATIONS = 10;

	/**
	 * The number of stations found in the data (not counting those with invalid
	 * data)
	 */
	private Integer numberOfStations = null;

	/** A string representation of the air temperature at 9.0m header */
	private String TA9M = "TA9M";

	/** A string representation of the air temperature at 1.5m header */
	private String TAIR = "TAIR";

	/** A string representation of the solar radiation header */
	private String SRAD = "SRAD";
	
	/** A string representation of the WSPD header */
	private String WSPD = "WSPD";
	
	/** A string representation of the PRES header */
	private String PRES = "PRES";

	/** A string representation of the "station ID" header */
	private String STID = "STID";

	/** The station ID used for average statistics */
	private String MESONET = "Mesonet";

	/** A string representation of where the data can be found */
	private String fileName;

	/** A GregorianCalendar representation of the date and time the data occured */
	private GregorianCalendar utcDateTime;

	/**
	 * Constructor. Takes in the 5 parameters needed to make a GregorianCalendar and
	 * the directory to make the fileName used for data extraction
	 * 
	 * @param year      the year in which the data occured
	 * @param month     the month in which the data occured
	 * @param day       the day in which the data occured
	 * @param hour      the hour in which the data occured
	 * @param minute    the minute in which the data occured
	 * @param directory the directory where the data files can be found
	 */
	public MapData(int year, int month, int day, int hour, int minute, String directory) {
		// Creates a new GregorianCalendar with the date in which the data occured
		utcDateTime = new GregorianCalendar(year, month - 1, day, hour, minute);

		// Creates the fileName by running createFileName method with specified
		// parameters
		this.fileName = this.createFileName(year, month, day, hour, minute, directory);

		// Parses the file and gets all the statistics in place
		// this.parseFile();
	}

	/**
	 * Creates the fileName for the specific file with the specific date sent by the
	 * main method
	 * 
	 * @param year      the year in which the data occured
	 * @param month     the month in which the data occured
	 * @param day       the day in which the data occured
	 * @param hour      the hour in which the data occured
	 * @param minute    the minute in which the data occured
	 * @param directory the directory where the data files can be found
	 * @return a String representation of the fileName with directory
	 */
	public String createFileName(int year, int month, int day, int hour, int minute, String directory) {
		return String.format("%s\\%04d%02d%02d%02d%02d.mdf", directory, year, month, day, hour, minute);
	}

	/**
	 * A method which takes in the 3rd line from the data and parses the data to
	 * locate the indexes of specific data we are looking for.
	 * 
	 * Stores the indexes into a TreeMap with keys "TAIR", "TA9M", "SRAD" or "STID".
	 * Each key links to an Integer which represents the index of where the
	 * measurement can be found in the data.
	 * 
	 * @param inParamStr The line that is used to parse and find the indexes
	 */
	public void parseParamHeader(String inParamStr) {
		// Splits up the String into an array and deletes all whitespace
		String[] temp = inParamStr.trim().split("\\s+");

		// Instatiates the TreeMap used
		paramPositions = new TreeMap<String, Integer>();

		// Loops through the array and checks to see whether each String at
		// index i equals one of the desired Strings
		for (int i = 0; i < temp.length; ++i) {
			if (temp[i].equals(TAIR)) {
				paramPositions.put(TAIR, i);
			} else if (temp[i].equals(TA9M)) {
				paramPositions.put(TA9M, i);
			} if (temp[i].equals(SRAD)) {
				paramPositions.put(SRAD, i);
			} if (temp[i].equals(STID)) {
				paramPositions.put(STID, i);
			} if(temp[i].equals(WSPD)){
				paramPositions.put(WSPD, i);
			} if(temp[i].equals(PRES)) {
				paramPositions.put(PRES, i);
			}
		}
	}

	/**
	 * A method that looks at each line of the data and constructs Observations with
	 * the specified measurements. Checks to see whether the Observation is valid
	 * and if it is, adds it into an array with the respective measurement. After
	 * adding all observations into respective arrays, The arrays are all linked to
	 * the string representation of the measurement in a HashMap called dataCatalog.
	 * Once all the data has been parsed, calcualateStatistics is ran.
	 */
	public void parseFile() {
		// FileReader may throw an exception. Try catch loops handles it.
		try {
			// Instantiates FileReader with the fileName
			BufferedReader br = new BufferedReader(new FileReader(this.fileName));

			// Skips the first 2 lines
			br.readLine();
			br.readLine();

			// Runs the parseParamHeader with the 3rd line of the data
			this.parseParamHeader(br.readLine());

			// First line of the data
			String line = br.readLine();

			// Instantiates numberOfStations
			numberOfStations = 0;

			// Instantiates arrayList to log all the Observations of measurements
			ArrayList<Observation> tairObservations = new ArrayList<>();
			ArrayList<Observation> ta9mObservations = new ArrayList<>();
			ArrayList<Observation> sradObservations = new ArrayList<>();
			ArrayList<Observation> wspdObservations = new ArrayList<>();
			ArrayList<Observation> presObservations = new ArrayList<>();

			// Prepares the dataCatalog
			this.prepareDataCatalog();

			// Loops over all lines in the data until there is no more
			while (line != null) {
				// Keeps track of the number of stations
				++numberOfStations;

				// Splits up the String into an array and deletes all whitespace
				String[] temp = line.trim().split("\\s+");

				// Creates observations with the specified data and measurements
				Observation tairObs = new Observation(Double.parseDouble(temp[paramPositions.get(TAIR)]),
						temp[paramPositions.get(STID)]);
				Observation ta9mObs = new Observation(Double.parseDouble(temp[paramPositions.get(TA9M)]),
						temp[paramPositions.get(STID)]);
				Observation sradObs = new Observation(Double.parseDouble(temp[paramPositions.get(SRAD)]),
						temp[paramPositions.get(STID)]);
				Observation presObs = new Observation(Double.parseDouble(temp[paramPositions.get(PRES)]),
						temp[paramPositions.get(STID)]);
				Observation wspdObs = new Observation(Double.parseDouble(temp[paramPositions.get(WSPD)]),
						temp[paramPositions.get(STID)]);

				// Checks whether Observations are valid (value>-900)
				// If observation is valid, adds into array
				if (tairObs.isValid()) {
					tairObservations.add(tairObs);
				}
				if (ta9mObs.isValid()) {
					ta9mObservations.add(ta9mObs);
				}
				if (sradObs.isValid()) {
					sradObservations.add(sradObs);
				}
				if(presObs.isValid()) {
					presObservations.add(presObs);
				}
				if(wspdObs.isValid()) {
					wspdObservations.add(wspdObs);
				}
				line = br.readLine();
			}

			// Checking to see whether the amount of missing observations exceed
			// The maximum number of missing observations allowed
			if (numberOfStations - tairObservations.size() > NUMBER_OF_MISSING_OBSERVATIONS) {
				System.out.print(String.format("Over %f observations in the air temperature data"
						+ "[1.5m] are not valid. Proceed with caution.", NUMBER_OF_MISSING_OBSERVATIONS));
			}
			if (numberOfStations - ta9mObservations.size() > NUMBER_OF_MISSING_OBSERVATIONS) {
				System.out.print(String.format("Over %f observations in the air temperature data"
						+ "[9.0m] are not valid. Proceed with caution.", NUMBER_OF_MISSING_OBSERVATIONS));
			}
			if (numberOfStations - sradObservations.size() > NUMBER_OF_MISSING_OBSERVATIONS) {
				System.out.print(String.format(
						"Over %f observations in the solar radiation data" + " are not valid. Proceed with caution.",
						NUMBER_OF_MISSING_OBSERVATIONS));
			}

			// Adds all the ArrayLists with all the information to the HashMap
			// while linking them to the specific measurement.
			dataCatalog.put(TAIR, tairObservations);
			dataCatalog.put(TA9M, ta9mObservations);
			dataCatalog.put(SRAD, sradObservations);
			dataCatalog.put(PRES, presObservations);
			dataCatalog.put(WSPD, wspdObservations);

			// Calculates all the statistics
			this.calculateStatistics();
			br.close();
		}

		// Catches an empty file or a non existent file
		catch (IOException e) {
			System.out.println("File is empty or does not exist");
		}
	}

	/**
	 * A method which calculates the Maximum, Minimum and Average Observations found
	 * in the data forthe specified measurements and adds them to corresponding
	 * TreeMaps which are then added to an EnumMap with key of StatsType and links
	 * to a TreeMap of key String and links to a type Statistics.
	 */
	private void calculateAllStatistics() {
		// Setting the maximum and minimum as the first observation
		Observation tairMax = dataCatalog.get(TAIR).get(0);
		Observation tairMin = dataCatalog.get(TAIR).get(0);

		// Instantiate a sum
		int sum = 0;

		// Loops over all observations of the air temperature at 1.5m
		for (Observation obs : dataCatalog.get(TAIR)) {

			// Checks to see whether the current observation has a higher/lower
			// value than current max and mins.
			if (obs.getValue() > tairMax.getValue()) {
				tairMax = obs;
			} else if (obs.getValue() < tairMin.getValue()) {
				tairMin = obs;
			}

			// Adds the values
			sum += obs.getValue();
		}

		// Creates TreeMaps for maximum, minimum and average. Adds the
		// specified Statistics into the corresponding TreeMap
		TreeMap<String, Statistics> treeMapMax = new TreeMap<String, Statistics>();
		TreeMap<String, Statistics> treeMapMin = new TreeMap<String, Statistics>();
		TreeMap<String, Statistics> treeMapAvg = new TreeMap<String, Statistics>();
		treeMapMax.put(TAIR, new Statistics(tairMax.getValue(), tairMax.getStid(), utcDateTime, numberOfStations,
				StatsType.MAXIMUM, TAIR) );
		treeMapMin.put(TAIR, new Statistics(tairMin.getValue(), tairMin.getStid(), utcDateTime, numberOfStations,
				StatsType.MINIMUM, TAIR) );
		treeMapAvg.put(TAIR, new Statistics(sum / dataCatalog.get(TAIR).size(), MESONET, utcDateTime, numberOfStations,
				StatsType.AVERAGE, TAIR));

		// Same process repeated twice with TA9M and SRAD

		Observation ta9mMax = dataCatalog.get(TA9M).get(0);
		Observation ta9mMin = dataCatalog.get(TA9M).get(0);

		sum = 0;

		for (Observation obs : dataCatalog.get(TA9M)) {
			if (obs.getValue() > ta9mMax.getValue()) {
				ta9mMax = obs;
			} else if (obs.getValue() < ta9mMin.getValue()) {
				ta9mMin = obs;
			}

			sum += obs.getValue();
		}

		treeMapMax.put(TA9M, new Statistics(ta9mMax.getValue(), ta9mMax.getStid(), utcDateTime, numberOfStations,
				StatsType.MAXIMUM, TA9M));
		treeMapMin.put(TA9M, new Statistics(ta9mMin.getValue(), ta9mMin.getStid(), utcDateTime, numberOfStations,
				StatsType.MINIMUM, TA9M));
		treeMapAvg.put(TA9M, new Statistics(sum / dataCatalog.get(TA9M).size(), MESONET, utcDateTime, numberOfStations,
				StatsType.AVERAGE, TA9M));

		Observation sradMax = dataCatalog.get(SRAD).get(0);
		Observation sradMin = dataCatalog.get(SRAD).get(0);

		sum = 0;

		for (Observation obs : dataCatalog.get(SRAD)) {
			if (obs.getValue() > sradMax.getValue()) {
				sradMax = obs;
			} else if (obs.getValue() < sradMin.getValue()) {
				sradMin = obs;
			}
			sum += obs.getValue();
		}

		treeMapMax.put(SRAD, new Statistics(sradMax.getValue(), sradMax.getStid(), utcDateTime, numberOfStations,
				StatsType.MAXIMUM, SRAD));
		treeMapMin.put(SRAD, new Statistics(sradMin.getValue(), sradMin.getStid(), utcDateTime, numberOfStations,
				StatsType.MINIMUM, SRAD));
		treeMapAvg.put(SRAD, new Statistics(sum / dataCatalog.get(SRAD).size(), MESONET, utcDateTime, numberOfStations,
				StatsType.AVERAGE, SRAD));
		statistics = new EnumMap<StatsType, TreeMap<String, Statistics>>(StatsType.class);
		
		Observation presMax = dataCatalog.get(PRES).get(0);
		Observation presMin = dataCatalog.get(PRES).get(0);

		sum = 0;

		for (Observation obs : dataCatalog.get(PRES)) {
			if (obs.getValue() > presMax.getValue()) {
				presMax = obs;
			} else if (obs.getValue() < presMin.getValue()) {
				presMin = obs;
			}

			sum += obs.getValue();
		}

		treeMapMax.put(PRES, new Statistics(presMax.getValue(), presMax.getStid(), utcDateTime, numberOfStations,
				StatsType.MAXIMUM, PRES));
		treeMapMin.put(PRES, new Statistics(presMin.getValue(), presMin.getStid(), utcDateTime, numberOfStations,
				StatsType.MINIMUM, PRES));
		treeMapAvg.put(PRES, new Statistics(sum / dataCatalog.get(PRES).size(), MESONET, utcDateTime, numberOfStations,
				StatsType.AVERAGE, PRES));
		
		Observation wspdMax = dataCatalog.get(WSPD).get(0);
		Observation wspdMin = dataCatalog.get(WSPD).get(0);

		sum = 0;

		for (Observation obs : dataCatalog.get(WSPD)) {
			if (obs.getValue() > wspdMax.getValue()) {
				wspdMax = obs;
			} else if (obs.getValue() < wspdMin.getValue()) {
				wspdMin = obs;
			}

			sum += obs.getValue();
		}

		treeMapMax.put(WSPD, new Statistics(wspdMax.getValue(), wspdMax.getStid(), utcDateTime, numberOfStations,
				StatsType.MAXIMUM, WSPD));
		treeMapMin.put(WSPD, new Statistics(wspdMin.getValue(), wspdMin.getStid(), utcDateTime, numberOfStations,
				StatsType.MINIMUM, WSPD));
		treeMapAvg.put(WSPD, new Statistics(sum / dataCatalog.get(WSPD).size(), MESONET, utcDateTime, numberOfStations,
				StatsType.AVERAGE, WSPD));

		// Once all Maximum, Minimum and Average statistics have been placed in
		// appropriate TreeMap, adds the TreeMaps to the corresponding
		// StatsType key.
		statistics.put(StatsType.MAXIMUM, treeMapMax);
		statistics.put(StatsType.MINIMUM, treeMapMin);
		statistics.put(StatsType.AVERAGE, treeMapAvg);
	}

	/**
	 * Prepares the HashMap which stores a key ("TAIR", "TA9M", or "SRAD) and links
	 * to an ArrayList of Observations about specified measurement.
	 */
	private void prepareDataCatalog() {
		dataCatalog = new HashMap<String, ArrayList<Observation>>();
	}

	/**
	 * A method which runs the calculateAllStatistics method
	 */
	private void calculateStatistics() {
		this.calculateAllStatistics();
	}

	/**
	 * A getter for a specific Statistics
	 * 
	 * @param type    StatsType which represents the type of Statistics that should
	 *                be returned (AVERAGE, MINIMUM, or MAXMIMUM)
	 * 
	 * @param paramId the String representation of the measurement that should be
	 *                returned ("TAIR", "TA9M" or "SRAD")
	 * 
	 * @return A statistics of the StatsType type and of the measurement specified
	 *         by paramId
	 */
	public Statistics getStatistics(StatsType type, String paramId) {
		try {
			return statistics.get(type).get(paramId);
		} catch (NullPointerException e) {
			return null;
		}
	}

	/**
	 * Returns a string representation of the air temperature statistics and solar
	 * radiation statistics in the form:
	 * ======================================================== === YEAR-MONTH-DAY
	 * HOUR:MINUTE === ========================================================
	 * Maximum Air Temperature[1.5m] = <max air temperature at 1.5 m> C at <STID
	 * where max temp occured> Minimum Air Temperature[1.5m] = <min air temperature
	 * at 1.5 m> C at <STID where min temp occured> Average Air Temperature[1.5m] =
	 * <average air temperature at 1.5 m> C at <STID where average temp occured>
	 * ========================================================
	 * ======================================================== Maximum Air
	 * Temperature[9.0m] = <max air temperature at 9m> C at <STID where max temp
	 * occured> Minimum Air Temperature[9.0m] = <min air temperature at 9m> C at
	 * <STID where min temp occured > Average Air Temperature[9.0m] = <average air
	 * temperature at 9m> C at <STID where average temp occured>
	 * ========================================================
	 * ======================================================== Maximum Solar
	 * Radiation[1.5m] = <Max solar radiation> W/m^2 at <STID where max solar
	 * radiation occured> Minimum Solar Radiation[1.5m] = <Min solar radiation>
	 * W/m^2 at <STID where min solar radiation occured> Average
	 * SolarRadiation[1.5m] = <Average solar radiation> W/m^2 at <STID where average
	 * solar radiation occured>
	 * ========================================================
	 *
	 */
	public String toString() {

		String output = "";
		output += "================================================\n";
		output += String.format("=== %s ===\n",
				statistics.get(StatsType.AVERAGE).get(TAIR).createStringFromDate(utcDateTime));
		output += "================================================\n";
		output += String.format("Maximum Air Temperature[1.5m] = %.01f C at %s\n",
				statistics.get(StatsType.MAXIMUM).get(TAIR).getValue(),
				statistics.get(StatsType.MAXIMUM).get(TAIR).getStid());
		output += String.format("Minimum Air Temperature[1.5m] = %.01f C at %s\n",
				statistics.get(StatsType.MINIMUM).get(TAIR).getValue(),
				statistics.get(StatsType.MINIMUM).get(TAIR).getStid());
		output += String.format("Average Air Temperature[1.5m] = %.01f C at %s\n",
				statistics.get(StatsType.AVERAGE).get(TAIR).getValue(),
				statistics.get(StatsType.AVERAGE).get(TAIR).getStid());
		output += "================================================\n";
		output += "================================================\n";
		output += String.format("Maximum Air Temperature[9.0m] = %.01f C at %s\n",
				statistics.get(StatsType.MAXIMUM).get(TA9M).getValue(),
				statistics.get(StatsType.MAXIMUM).get(TA9M).getStid());
		output += String.format("Minimum Air Temperature[9.0m] = %.01f C at %s\n",
				statistics.get(StatsType.MINIMUM).get(TA9M).getValue(),
				statistics.get(StatsType.MINIMUM).get(TA9M).getStid());
		output += String.format("Average Air Temperature[9.0m] = %.01f C at %s\n",
				statistics.get(StatsType.AVERAGE).get(TA9M).getValue(),
				statistics.get(StatsType.AVERAGE).get(TA9M).getStid());
		output += "================================================\n";
		output += "================================================\n";
		output += String.format("Maximum Solar Radiation = %.01f W/m^2 at %s\n",
				statistics.get(StatsType.MAXIMUM).get(SRAD).getValue(),
				statistics.get(StatsType.MAXIMUM).get(SRAD).getStid());
		output += String.format("Minimum Solar Radiation = %.01f W/m^2 at %s\n",
				statistics.get(StatsType.MINIMUM).get(SRAD).getValue(),
				statistics.get(StatsType.MINIMUM).get(SRAD).getStid());
		output += String.format("Average Solar Radiation = %.01f W/m^2 at %s\n",
				statistics.get(StatsType.AVERAGE).get(SRAD).getValue(),
				statistics.get(StatsType.AVERAGE).get(SRAD).getStid());
		output += "================================================\n";
		return output;
	}
}
