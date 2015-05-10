package utils.ui;

import java.awt.GridBagConstraints;

public enum GridBagConstraintsFill {

    NONE(GridBagConstraints.NONE),
    HORIZONTAL(GridBagConstraints.HORIZONTAL),
    VERTICAL(GridBagConstraints.VERTICAL),
    BOTH(GridBagConstraints.BOTH); 

    private final int value;
    
    private GridBagConstraintsFill(int value) {
    	this.value = value;
    }
    
    public int getValue() {
    	return value;
    }
    
}
