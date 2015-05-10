package settings.model;

import javax.swing.DefaultButtonModel;

public class ResolutionButtonModel extends DefaultButtonModel {
	private static final long serialVersionUID = -4606101712310504461L;
	private Resolution resolution;
	
	/**
	 * @param resolution the resolution to set
	 */
	public ResolutionButtonModel(Resolution resolution) {
		setResolution(resolution);
	}
	
	
	/**
	 * @return the resolution
	 */
	public Resolution getResolution() {
		return resolution;
	}
	/**
	 * @param resolution the resolution to set
	 */
	public void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}
	
}
