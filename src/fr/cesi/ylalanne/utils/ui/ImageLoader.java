package fr.cesi.ylalanne.utils.ui;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.cesi.ylalanne.utils.ResourcesManager;

/**
 * Utility class to load images.
 */
public class ImageLoader {
	
	/**
	 * Load an image.
	 *
	 * @param imagePath the path to the image (relatives to {@link ResourcesManager#IMAGES_BASE})
	 * @return the image
	 */
	public static Image LoadImage(String imagePath) {
		try
		{
			URL fileUrl = ImageLoader.class.getResource(ResourcesManager.IMAGES_BASE + imagePath);
			return ImageIO.read(fileUrl);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
