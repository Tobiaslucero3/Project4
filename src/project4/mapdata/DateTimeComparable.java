package project4.mapdata;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

public interface DateTimeComparable {
	public abstract boolean newerThan(GregorianCalendar inDateTimeUTC);

	public abstract boolean olderThan(GregorianCalendar inDateTimeUTC);

	public abstract boolean sameAs(GregorianCalendar inDateTimeUTC);

	public abstract boolean newerThan(ZonedDateTime inDateTimeUTC);

	public abstract boolean olderThan(ZonedDateTime inDateTimeUTC);

	public abstract boolean sameAs(ZonedDateTime inDateTimeUTC);

}
