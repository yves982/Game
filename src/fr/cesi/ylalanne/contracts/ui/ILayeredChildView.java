package fr.cesi.ylalanne.contracts.ui;

/**
 * A child view, with a layer, meant to be used with a layered parent view<br>
 * <b>Note: </b> {@code IView#build()} should be called on any IView Implementation before adding it to its parent view.
 */
public interface ILayeredChildView extends IChildView {
	int getLayer();
	int getWidth();
	int getHeight();
}
