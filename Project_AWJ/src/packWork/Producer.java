package packWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Producer extends Thread{
	private File image;
	private Buffer buf;
	private FileInputStream fis;
	private long time;

	public Producer(Buffer buf, File image) {
		this.buf = buf;
		this.image = image;
		System.out.println("S-a apelat constructorul din Producer: "+ getName());
	}
	
	@Override
	public synchronized void start() {
		System.out.println("Inceput Thread in Producer: "+ getName());
		super.start();
	}
	
	@Override
	public void run() {
		System.out.println("Inceput run in Producer");
		long aux = System.currentTimeMillis();
		
		int length = (int) image.length();//lungimea vectorului
		byte[] imageQuarter = new byte[(int) (length/4)];//alocam spatiu pentru cate un sfert de imagine
		//pentru ultima parte luam inclusiv restul impartirii la 4 in caz ca imaginea
		//nu se imparte perfect la 4
		byte[] imageFinalQuarter = new byte[(int) (length/4 + length%4)];
		
		try {
			fis = new FileInputStream(this.image);
			
			System.out.println("Citim 1/4 din imagine!");
			try {
				fis.read(imageQuarter, 0, (int) (length/4));
				//unde:
				//-imageQuarter e destinatia sfertului de imagine
				//-0 indicele de start
				//-(length/4) este numarul maxim de biti
				buf.put(imageQuarter);
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //ii pun timp de sleep de peste o secunda
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Citim 2/4 din imagine!");
			try {
				fis.read(imageQuarter, 0, (int) (length/4));
				buf.put(imageQuarter);
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Citim 3/4 din imagine!");
			try {
				fis.read(imageQuarter, 0, (int) (length/4));
				buf.put(imageQuarter);
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Citim 4/4 din imagine!");
			try {
				fis.read(imageFinalQuarter, 0, (int) (length/4 + length%4));
				buf.put(imageFinalQuarter);
				try {
					Thread.sleep(1300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println("Sfarsit citire in Producer");
			this.time = System.currentTimeMillis()-aux;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//retin timpul
	public long getTimeRead() {
		return this.time;
	}

}
