package project4.mapdata;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A class that extends the Observation class. Adds more specificity to the
 * Observation class like a way to track the date of the Statistics occured, the
 * type of statistic the Statistics represents (AVERAGE, MINIMUM or MAXIMUM) and
 * the number of operating stations at the time when the Statistics occured
 * 
 * @author Tobias Lucero
 * @version 10-23-2018
 * 
 */
public class Statistics extends Observation implements DateTimeComparable {
	/*
	 * The specific format the GregorianCalendar and ZonedDateTime are formatted as
	 */
	protected String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss z";

	/*
	 * The DateTimeFormatter used for formatting the GregorianCalendar and
	 * ZonedDateTime
	 */
	protected DateTimeFormatter format;

	/*
	 * The GregorianCalendar used for tracking the date when the Statistics occured
	 */
	private GregorianCalendar utcDateTime;

	/*
	 * The ZonedDateTime used for tracking the date when the Statistics occured
	 */
	private ZonedDateTime zdtDateTime;

	/*
	 * The number of reporting stations that occured when the Statistics occured
	 */
	private int numberOfReportingStations;

	/*
	 * The StatsType that represent the type of Statistics this is (MAXIMUM, MINIMUM
	 * or AVERAGE)
	 */
	private StatsType statType;
	
	/*
	 * The parameter of the statistics ("TAIR", "SRAD", "TA9M", etc)
	 */
	private String parameter;

	/**
	 * Constructor for Statistics class. Constructs a Statistics with specified
	 * GregorianCalendar
	 * 
	 * @param value                 the value of the Statistics
	 * @param stid                  the station ID of the Statistics
	 * @param dateTime              the GregorianCalendar which represents the time
	 *                              when the Statistics occured
	 * @param numberOfValidStations the number of valid stations at the time when
	 *                              the Statistics was taken.
	 * @param inStatType            The StatsType which represents the Statistics
	 *                              (AVERAGE,MINIMUM,MAXIMUM, or TOTAL)
	 */
	public Statistics(double value, String stid, GregorianCalendar dateTime, int numberOfValidStations,
			StatsType inStatType, String parameter) {
		super(value, stid); // Constructor for Observation
		utcDateTime = dateTime;
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
		this.parameter = parameter;
	}

	/**
	 * Constructor which instantiates a Statistics with a ZonedDateTime rather than
	 * a GregorianCalendar
	 * 
	 * @param value                 the value of the Statistics
	 * @param stid                  the Station ID of where the Statistics occured
	 * @param dateTime              the ZonedDateTime which represents the time when
	 *                              the Statistics happened
	 * @param numberOfValidStations the number of valid stations at the time when
	 *                              the Statistics occured
	 * @param inStatType            the StatsType which represents the Statistics
	 *                              (AVERAGE, MINIMUM, MAXIMUM or TOTAL)
	 */
	public Statistics(double value, String stid, ZonedDateTime dateTime, int numberOfValidStations,
			StatsType inStatType) {
		super(value, stid);
		zdtDateTime = dateTime;
		this.numberOfReportingStations = numberOfValidStations;
		this.statType = inStatType;
		format = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
	}

	/**
	 * A method which takes a String in the format of "yyyy-MM-dd'T'HH:mm:ss z" and
	 * constructs a GregorianCalendar based on the String
	 * 
	 * @param inDateTimeStr a string in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 * @return a GregorianCalendar with the same aspects as the inDateTimeStr
	 */
	public GregorianCalendar createDateFromString(String inDateTimeStr) {
		return GregorianCalendar.from(ZonedDateTime.parse(inDateTimeStr, format));
	}

	/**
	 * A method which creates a ZonedDateTime based from a string in the format
	 * "yyyy-MM-dd'T'HH:mm:ss z".
	 * 
	 * @param inDateTimeStr a string in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 * @return a ZonedDateTime with the same aspects as the inDateTimeStr
	 */
	public ZonedDateTime createZDateFromString(String inDateTimeStr) {
		return ZonedDateTime.parse(inDateTimeStr, format);
	}

	/**
	 * A method which creates a string in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 * based on the GregorianCalendar's values
	 * 
	 * @param calendar the GregorianCalendar which shall be parsed
	 * @return A string in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 */
	public String createStringFromDate(GregorianCalendar calendar) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date now = calendar.getTime();
		String ret = formatter.format(now);
		return ret;
	}
	
	public String createStringForDate()
	{
		return this.createStringFromDate(utcDateTime);
	}

	/**
	 * A method which creates a string in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 * based on the ZonedDateTime's values
	 * 
	 * @param calendar the ZonedDateTime to be parsed
	 * @return a String in the format of "yyyy-MM-dd'T'HH:mm:ss z"
	 */
	public String createDateFromString(ZonedDateTime calendar) {
		return calendar.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
	}

	/**
	 * A inherited method which compares this GregorianCalendar to another
	 * GregorianCalendar to determine whether this happened more recently than
	 * inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the GregorianCalendar which this GregorianCalendar is
	 *                      being compared to.
	 * 
	 * @return representing whether this calendar happened after inDateTimeUTC
	 */
	@Override
	public boolean newerThan(GregorianCalendar inDateTimeUTC) {
		return utcDateTime.after(inDateTimeUTC);
	}

	/**
	 * A inherited method which compares this GregorianCalendar to another
	 * GregorianCalendar to determine whether this happened before inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the GregorianCalendar which this GregorianCalendar is
	 *                      being compared to.
	 * 
	 * @return representing whether this calendar happened before inDateTimeUTC
	 */
	@Override
	public boolean olderThan(GregorianCalendar inDateTimeUTC) {
		return utcDateTime.before(inDateTimeUTC);
	}

	/**
	 * A inherited method which compares this GregorianCalendar to another
	 * GregorianCalendar to determine whether this happened at the same time as
	 * inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the GregorianCalendar which this GregorianCalendar is
	 *                      being compared to.
	 * 
	 * @return representing whether this calendar happened at the same time as
	 *         inDateTimeUTC
	 */
	@Override
	public boolean sameAs(GregorianCalendar inDateTimeUTC) {
		return utcDateTime.equals(inDateTimeUTC);
	}

	/**
	 * A inherited method which compares this ZonedDateTime to another ZonedDateTime
	 * to determine whether this happened more recently than inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the ZonedDateTime which this ZonedDateTime is being
	 *                      compared to.
	 * 
	 * @return representing whether this ZonedDateTime happened after inDateTimeUTC
	 */
	@Override
	public boolean newerThan(ZonedDateTime inDateTimeUTC) {
		return zdtDateTime.isAfter(inDateTimeUTC);
	}

	/**
	 * A inherited method which compares this ZonedDateTime to another ZonedDateTime
	 * to determine whether this happened less recently than inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the ZonedDateTime which this ZonedDateTime is being
	 *                      compared to.
	 * 
	 * @return representing whether this ZonedDateTime happened before inDateTimeUTC
	 */
	@Override
	public boolean olderThan(ZonedDateTime inDateTimeUTC) {
		return zdtDateTime.isBefore(inDateTimeUTC);
	}

	/**
	 * A inherited method which compares this ZonedDateTime to another ZonedDateTime
	 * to determine whether this happened at the same time as inDateTimeUTC
	 * 
	 * @param inDateTimeUTC the ZonedDateTime which this ZonedDateTime is being
	 *                      compared to.
	 * 
	 * @return representing whether this ZonedDateTime happened at the same time as
	 *         inDateTimeUTC
	 */
	@Override
	public boolean sameAs(ZonedDateTime inDateTimeUTC) {
		return zdtDateTime.equals(inDateTimeUTC);
	}

	/**
	 * Getter for the total number of reporting stations
	 * 
	 * @return int representation of total number of reporting stations at the time
	 *         when the Statistics occured
	 * 
	 */
	public int getNumberOfReportingStations() {
		return this.numberOfReportingStations;
	}

	/**
	 * Returns a String representation of the Statistics
	 * 
	 * @return String in the format of "The <StatsType> came from the station <STID>
	 *         and had a value of <value>
	 */
	public String toString() {
		return String.format("The %s came from the station %s and had a value" + " of %.01f", statType.name(),
				this.getStid(), this.getValue());
	}
	
	public String getParameter()
	{
		return parameter;
	}

	public StatsType getStatType()
	{
		return statType;
	}

}
