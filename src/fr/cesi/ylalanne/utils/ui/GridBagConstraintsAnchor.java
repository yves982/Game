package fr.cesi.ylalanne.utils.ui;

import java.awt.GridBagConstraints;
/**
 * Store {@link GridBagConstraints}'s possible anchors.
 */
public enum GridBagConstraintsAnchor {
	CENTER(GridBagConstraints.CENTER),
	PAGE_START(GridBagConstraints.PAGE_START),
	PAGE_END(GridBagConstraints.PAGE_END),
	LINE_START(GridBagConstraints.LINE_START),
	LINE_END(GridBagConstraints.LINE_END),
	FIRST_LINE_START(GridBagConstraints.FIRST_LINE_START),
	FIRST_LINE_END(GridBagConstraints.FIRST_LINE_END),
	LAST_LINE_START(GridBagConstraints.LAST_LINE_START),
	LAST_LINE_END(GridBagConstraints.LAST_LINE_END),
	BASELINE(GridBagConstraints.BASELINE),
	BASELINE_LEADING(GridBagConstraints.BASELINE_LEADING),
	BASELINE_TRAILING(GridBagConstraints.BASELINE_TRAILING),
	ABOVE_BASELINE(GridBagConstraints.BASELINE),
	ABOVE_BASELINE_LEADING(GridBagConstraints.ABOVE_BASELINE_LEADING),
	ABOVE_BASELINE_TRAILING(GridBagConstraints.ABOVE_BASELINE_TRAILING),
	BELOW_BASELINE(GridBagConstraints.BELOW_BASELINE),
	BELOW_BASELINE_LEADING(GridBagConstraints.BELOW_BASELINE_LEADING),
	BELOW_BASELINE_TRAILING(GridBagConstraints.BELOW_BASELINE_TRAILING);
	
	private final int value;
	
	private GridBagConstraintsAnchor(int value) {
		this.value = value;
	}
	
	/**
	 * Gets the anchor value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
