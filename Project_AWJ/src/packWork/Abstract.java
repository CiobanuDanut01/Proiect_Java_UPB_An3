package packWork;

import java.awt.image.BufferedImage;

public class Abstract implements Interface{
	protected BufferedImage data;
	protected String title = "Default";
	protected int height, width;

	public Abstract() {
		System.err.println("Eroare, trimite datele ca imagine");
	}
	public Abstract(BufferedImage file) {
		this.data = file;
		this.width = file.getWidth();
		this.height= file.getHeight();
	}
	public Abstract(BufferedImage file, String title) {
		this(file);
		this.title = title;
	}
	public void show() {
	}
}
