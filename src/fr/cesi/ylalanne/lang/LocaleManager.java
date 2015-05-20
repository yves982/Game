package fr.cesi.ylalanne.lang;

import java.util.Locale;

/**
 * utility class to get a mutable application scope locale.
 */
public class LocaleManager {
	private static Locale locale;
	private static Messages messages;
	
	public static String getString(String key) {
		return messages.getString(key);
	}
	
	/**
	 * @return the locale
	 */
	public static Locale getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public static void setLocale(Locale locale) {
		LocaleManager.locale = locale;
		messages = new Messages(locale);
	}
}
