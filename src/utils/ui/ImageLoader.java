package utils.ui;

import java.awt.Image;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import main.ResourcesManager;

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
			return ImageIO.read(Paths.get(imagePath).toUri().toURL());
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new RuntimeException(ioe);
		}
	}

	public static Image LoadImageWithShortPath(String shortPath) {
		String fullPath = Paths.get(ResourcesManager.RESOURCES_BASE, shortPath).toString();
		return LoadImage(fullPath);
	}
}