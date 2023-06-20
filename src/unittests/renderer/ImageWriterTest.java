package unittests.renderer;

import renderer.ImageWriter;
import org.junit.jupiter.api.Test;
import primitives.Color;

/**
 * test class to check all function of Image Writer class
 * 
 * @author David
 *
 */
class ImageWriterTest {

	/** create grid for test */
	@Test
	void testImageWriter() {
		final Color color1 = new Color(java.awt.Color.WHITE);
		final Color color2 = new Color(java.awt.Color.BLUE);
		final int width = 800;
		final int height = 500;
		final int step = 50;

		final ImageWriter picture = new ImageWriter("grid", width, height);
		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				picture.writePixel(i, j, i % step == 0 || j % step == 0 ? color1 : color2);
		picture.writeToImage();
	}

}
