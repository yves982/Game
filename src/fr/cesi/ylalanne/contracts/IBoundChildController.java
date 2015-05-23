package fr.cesi.ylalanne.contracts;

import java.beans.PropertyChangeListener;

public interface IBoundChildController extends IChildController {
	void addPropertyChangeListener(PropertyChangeListener listener);
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
	void removePropertyChangeListener(PropertyChangeListener listener);
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
