package fr.cesi.ylalanne.lang;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "fr.cesi.ylalanne.lang.messages"; //$NON-NLS-1$
	private final ResourceBundle RESOURCE_BUNDLE;
	
	public Messages(Locale locale) {
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	}

	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "the following key is absent: " + key;
		}
	}
}
