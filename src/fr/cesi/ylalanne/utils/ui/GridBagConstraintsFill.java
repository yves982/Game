package fr.cesi.ylalanne.utils.ui;

import java.awt.GridBagConstraints;

/**
 * Handles how a component will occupy extra space.
 */
public enum GridBagConstraintsFill {

    NONE(GridBagConstraints.NONE),
    HORIZONTAL(GridBagConstraints.HORIZONTAL),
    VERTICAL(GridBagConstraints.VERTICAL),
    BOTH(GridBagConstraints.BOTH); 

    private final int value;
    
    private GridBagConstraintsFill(int value) {
    	this.value = value;
    }
    
    /**
     * Gets the value.
     *
     * @return the value
     */
    public int getValue() {
    	return value;
    }
    
}
