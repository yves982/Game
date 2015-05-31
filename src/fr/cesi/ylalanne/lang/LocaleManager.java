package fr.cesi.ylalanne.lang;

import java.util.Locale;

/**
 * utility class to get an application scope locale.
 * <p>Note: while we can set the Locale at any time, as the first View will be loaded<br>
 * before most objects, we should not change this<br> after MainFrameController has been initialized</p>
 */
public class LocaleManager {
	private static Locale locale;
	private static Messages messages;
	
	/**
	 * Gets the string.
	 *
	 * @param key the key
	 * @return the string
	 */
	public static String getString(String key) {
		return messages.getString(key);
	}
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public static Locale getLocale() {
		return locale;
	}
	
	/**
	 * Sets the locale.
	 *
	 * @param locale the locale to set
	 */
	public static void setLocale(Locale locale) {
		LocaleManager.locale = locale;
		messages = new Messages(locale);
	}
}
