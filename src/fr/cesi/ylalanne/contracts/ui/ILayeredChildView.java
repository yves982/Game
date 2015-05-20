package fr.cesi.ylalanne.contracts.ui;

/**
 * A child view, with a layer, meant to be used with a layered parent view<br>
 * <b>Note: </b> {@code IView#build()} should be the first method ever called on any IView Implementation
 */
public interface ILayeredChildView extends IChildView {
	/**
	 * @return the layer, fr.cesi.ylalanne.contracts component belongs to (the lowest in foreground, highest in the background)
	 */
	int getLayer();
}
