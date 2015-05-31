package fr.cesi.ylalanne.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class to get translated String from translation keys.
 */
public class Messages {
	private static final String BUNDLE_NAME = "fr.cesi.ylalanne.lang.messages";
	private final ResourceBundle RESOURCE_BUNDLE;
	
	/**
	 * Instantiates a new messages.
	 *
	 * @param locale the locale
	 */
	public Messages(Locale locale) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	/**
	 * Gets the translated string.
	 *
	 * @param key the translation key (identifying a message)
	 * @return the translated string
	 */
	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "the following key is absent: " + key;
		}
	}
}
