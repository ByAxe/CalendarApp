package core.commons;

import java.time.format.DateTimeFormatter;

/**
 * Created by byaxe on 26.11.16.
 */
public class Utils {

    public static final String TODAY = "СЕГОДНЯ";
    public static final String TOMORROW = "ЗАВТРА";

    public static final DateTimeFormatter PRETTY_TIME_FORMATTER = DateTimeFormatter.ofPattern("d MMM HH:mm");

}
