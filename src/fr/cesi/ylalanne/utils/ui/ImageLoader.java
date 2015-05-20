package fr.cesi.ylalanne.utils.ui;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Utility class to load images
 */
public class ImageLoader {
	/**
	 * @param imagePath the path to the image
	 * @return the image
	 * @throws RuntimeException in case of an IO error while trying to access file or an unhandled URL.
	 */
	public static Image LoadImage(String imagePath) {
		try
		{
			URL fileUrl = ImageLoader.class.getClass().getResource(ResourcesManager.RESOURCES_BASE + imagePath);
			return ImageIO.read(fileUrl);
		} catch(IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
