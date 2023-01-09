package packWork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ShowImage extends Abstract{
	public ShowImage() {
		super();
	}
	public ShowImage(BufferedImage file) {
		super(file);
	}
	public ShowImage(BufferedImage file, String title) {
		super(file,title);
		this.saveImage();
	}
	public void saveImage() {
		BufferedImage temp = this.data;
		File outputFile = new File(this.title +".bmp");
		try {
			ImageIO.write(temp,  "png", outputFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		System.out.println("Apelare metoda mostenita din interfata");
	}
}
