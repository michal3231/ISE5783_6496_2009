package unittests.renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter;

class ImageWriterTest {

	@Test
	void testWriteToImage() {
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
