package packWork;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Consumer extends Thread{
		private File image;
		private Buffer buf;
		private byte[] data;
		private long timeWork, timeWrite;
		BufferedImage input;
		BufferedImage output;
		private double contrast, blackWhite;
		
		public Consumer(Buffer buf, File image, double contrast, double blackWhite) {
			this.contrast = contrast/100;//consider input-ul ca fiind in procente
										// adica contrast = 150 va insemna o creste cu 50% a contrastului
										// adica inmultesc cu 1.5 valorile RGB
			this.blackWhite = blackWhite;
			this.buf = buf;
			this.image = image;
			this.data = new byte[(int) image.length()];
			System.out.println("S-a apelat constructorul din Consumer: " + getName());
		}
		
		@Override
		public synchronized void start() {
			System.out.println("Inceput Thread in Consumer: "+ getName());
			super.start();
		}
		
		@SuppressWarnings("unused")//<- pentru a scapa de warning-urile de compilare
		@Override
		public void run() {
			System.out.println("Inceput run in Consumer");
			//salvam informatia procedural intr-un vector
			while(buf.getAux() < image.length()- 1) {
				byte[] temp = buf.getImage();
				System.out.println("Consumer primeste datele din buffer");
				
				for(int i = 0; i< temp.length; i++) {
					data[i] = temp[i];
				}
			}
			//dupa citirea intregii imagini se poate incepe prelucrarea
			System.out.println("Incepere prelucrare date in Consumer");
			long time = System.currentTimeMillis();
			try {
				//construiesc vectorii de biti intr-o variabila auxiliara 
				//pe care o salvez in Input -> imaginea originala pentru afisare
				BufferedImage tempImage = ImageIO.read(new ByteArrayInputStream(data));
				this.input = tempImage;
				
				//initializez variabila temporara tempOutput
				BufferedImage tempOutput = ImageIO.read(new ByteArrayInputStream(data));
				//parcurgere bit cu bit
				for(int i = 0;i<tempImage.getWidth();i++)
					for(int j = 0; j<  tempImage.getHeight(); j++) {
						//extragem pixelul de la pozitia i si j
						Color color = new Color(tempImage.getRGB(i, j));
						//efectuam modificarile de contrast si black/white pentru fiecare culoare din pixel
						int r = color.getRed();
						int g = color.getGreen();
						int b = color.getBlue();
						//dupa initializarea cu valorile din imaginea data ca input incep prelucrarea contrastului
						// si a nivelului de alb/negru
						if(contrast == 0 && blackWhite != 0) {
							r = (int) (color.getRed() + blackWhite);
							g = (int) (color.getGreen() + blackWhite);
							b = (int) (color.getBlue() + blackWhite);
						}
						if(contrast != 0 && blackWhite == 0) {
							r = (int) (color.getRed() * contrast);
							g = (int) (color.getGreen() * contrast);
							b = (int) (color.getBlue() * contrast);
						}
						if(contrast != 0 && blackWhite != 0) {
							r = (int) ((color.getRed() + blackWhite)*contrast);
							g = (int) ((color.getGreen() + blackWhite)*contrast);
							b = (int) ((color.getBlue() + blackWhite)*contrast);
						}
						//verificarile de siguranta pentru culori unde 0 e minim si 255 e maxim
						if(r>255) {
							r = 255;
						}
						if(r<0) {
							r = 0;
						}
						if(g>255) {
							g = 255;
						}
						if(g<0) {
							g = 0;
						}
						if(b>255) {
							b = 255;
						}
						if(b<0) {
							b = 0;
						}
						tempOutput.setRGB(i, j, (new Color(r, g, b)).getRGB());
							
					}
					this.output = tempOutput;
					System.out.println("Sfarsit prelucrare in Consumer");
					this.timeWork = System.currentTimeMillis() - time;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("\n\nSalvare in fisier imagini before si after");
			this.timeWrite =System.currentTimeMillis();
			ShowImage original = new ShowImage(this.input, "Before");
			ShowImage modified = new ShowImage(this.output, "After");
			this.timeWrite = System.currentTimeMillis()-this.timeWrite;
		}

	public long getTimeWork() {
		return this.timeWork;
	}

	public long getTimeWrite() {
		return this.timeWrite;
	}
}
