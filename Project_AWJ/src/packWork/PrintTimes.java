package packWork;

import java.io.FileWriter;
import java.io.IOException;

public class PrintTimes {
	private long timeTotal = 0L;
	
	//utilizare varargs pentru constructorul de printare timpuri
	public PrintTimes(long ...numbers) {
		int i = 0;
		String text = "";
		for(long item:numbers) {
			i++;
			text=getText(i);
			 try {
				 timeTotal+=item;
				 //creare de nou fisiser in care scriem;
			      FileWriter myWriter = new FileWriter("time_"+i+".txt");
			      //pentru fiecare argument trimis il scriu intr-un nou fisier;
			      myWriter.write(text+String.valueOf(item)); //transformare long in String
			      myWriter.close();//inchdiere fisier
			      System.out.println("\nTime Successfully wrote to the file.");
			    } catch (IOException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
		}
		System.out.print(this.getTimeTotal());
	}
	public long getTimeTotal()
	{
		try {
		      FileWriter myWriter = new FileWriter("timeTotal.txt");
		      myWriter.write("Timp total: "+String.valueOf(this.timeTotal)); //transformare long in String
		      myWriter.close();//inchdiere fisier
		      System.out.println("\nTotal Time Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		return this.timeTotal;
	}
	private String getText(int index){
		if(index==1)
			return "Timp Citire: ";
		else if(index==2)
			return "Timp Procesare: ";
		else if(index==3)
			return "Timp Scriere: ";
		
		return null;
		
	}
}