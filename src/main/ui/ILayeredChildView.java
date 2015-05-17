package main.ui;

/**
 * A child view, with a layer, meant to be used with a layered parent view
 */
public interface ILayeredChildView extends IChildView {
	/**
	 * @return the layer, main component belongs to (the lowest in foreground, highest in the background)
	 */
	int getLayer();
}
