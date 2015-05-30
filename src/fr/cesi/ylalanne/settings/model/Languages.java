package fr.cesi.ylalanne.settings.model;

import java.util.Locale;

/**
 * Supported Languages and their locales
 */
public enum Languages {
	FRENCH(Locale.FRENCH, "Lang_Fr"),
	// as an implementation detail, english is the default fallback Locale but we do not have a message_EN_en.properties lang file
	ENGLISH(Locale.ROOT, "Lang_En");
	
	private Locale locale;
	private String key;
	
	private Languages(Locale locale, String key) {
		this.locale = locale;
		this.key = key;
	}
	
	/**
	 * @return the corresponding Locale
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * @return the translation key to get the Languages's name
	 */
	public String getKey() {
		return key;
	}
}
