package packTest;

import java.io.File;
import java.util.Scanner;

import packWork.Buffer;
import packWork.Consumer;
import packWork.PrintTimes;
import packWork.Producer;

public class TestClass {

	private static boolean isNumeric(String str){
		return str != null && (str.matches("-[0-9.]+") || str.matches("[0-9.]+"));
	}
	//am creat o clasa pentru a verifica daca ce introducem de la tastatura este un numar valid sau nu

	public static void main(String[] args) {
		System.out.print("Introduceti numele fisierului cu extensia .bmp: ");
		int i = 0;
		try (Scanner scanner = new Scanner(System.in)) {
			String fisier = scanner.nextLine();//citim textul care trebuie sa reprezinte numele pozei

			//cream obiectele de tip File cu textul citit de la tastatura
			File f = new File(fisier);
			while (true)
				//daca fisierele exista si nu sunt directoare mergem mai departe cu programul
				//si le trimitem in Producer;
				if (f.exists() && !f.isDirectory()) {
					//initializare Buffer, Producer, Consumer
					System.out.println("\n\nIntroduceti valorile pentru contrast(>0) si black/white(-100 ~ 100)");
					System.out.println("Apasati ENTER pentru a seta valoarea default 0 la fiecare!\n");
					String temp1,temp2;
					double contrast = 0, blackWhite = 0;
					System.out.print("Contrast: ");
					temp1 = scanner.nextLine();
					while(!isNumeric(temp1)) {
						if(temp1.equals("")) {
							System.out.println("Contrast va avea valoarea default: " + contrast);
							break;
						}
						System.out.println("Reintroduceti o valoare numerica!!\nContrast: ");
						temp1 = scanner.nextLine();
					}
					while(isNumeric(temp1)) {
						if(Double.parseDouble(temp1) < 0) {
							System.out.println("Reintroduceti o valoare mai mare decat 0 !!\nContrast: ");
							temp1 = scanner.nextLine();
						} else {
							contrast = Double.parseDouble(temp1);
							System.out.println("Contrast va avea valoarea: " + contrast + "\n\n");
							break;
						}
					}
					System.out.print("Black/White: ");
					temp2 = scanner.nextLine();
					while(!isNumeric(temp2)) {
						if(temp2.equals("")) {
							System.out.println("Black/White va avea valoarea default: " + blackWhite);
							break;
						}
						System.out.print("Reintroduceti o valoare numerica!!\nBlack/White: ");
						temp2 = scanner.nextLine();
					}
					while(isNumeric(temp2)) {
						if(Double.parseDouble(temp2) < -100 || Double.parseDouble(temp2) > 100) {
							System.out.print("Reintroduceti o valoare intre -100 si 100 !!\nBlack/White: ");
							temp2 = scanner.nextLine();
						}else {
							blackWhite = Double.parseDouble(temp2);
							System.out.println("Black/White va avea valoarea: " + blackWhite + "\n\n");
							break;
						}
					}
					Buffer b = new Buffer((int) f.length());
					Producer p1 = new Producer(b, f);
					Consumer c1 = new Consumer(b, f, contrast, blackWhite);
					//pornire Threaduri
					p1.start();
					c1.start();
					try {
						p1.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					try {
						c1.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					long timeRead 	 = p1.getTimeRead();
					long timeWork	 = c1.getTimeWork();
					long timeWrite 	 = c1.getTimeWrite();
					new PrintTimes(timeRead,timeWork, timeWrite);
					break;
				} else {
					//dupa 5 incercari programul se inchide automat
					if(++i==5){
						System.err.println("Fisierele nu au putut fi gasite, Programul se inchide !");
						break;
					}
					//daca fisierele nu sunt valide atunci repetam operatia de citire
					System.err.println("Fisiere nu au fost gasite, va rugam introduceti un nume valid de fisier!");
					System.out.print("Numele pozei: ");
					fisier = scanner.nextLine();
					f = new File(fisier);
				}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
