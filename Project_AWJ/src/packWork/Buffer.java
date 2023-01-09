package packWork;

public class Buffer {
    private byte[] data;
    private boolean available = false;
    private int aux;

    public Buffer(int size) {
    	//initializare vectori unde vom stoca datele primite de la Producer
        data = new byte[size];
        this.aux = 0;
        System.out.println("S-a apelat constructorul din Buffer");
    }
    //img contine 1/4 din informatia totala ce va fi stocata in Buffer
    public synchronized void put(byte[] img) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException e) {
            	// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        available = true;
        notifyAll();
        int j = 0; //index folosit pentru preluarea datelor din vectorul img
        
        //pe durata programului aux va contine ultima pozitie din vectorii in care se stocheaza informatia totala
        for (int i = aux; i < aux + img.length; i++) {
            data[i] = img[j];
            j++;
        }
        aux += img.length; //adun lungimea datelor curente pentru a sti cand ajung la final
    }
    
    //returnare date pentru poza
    public synchronized byte[] getImage() {
        while (!available) {
            try {
                wait();

            } catch (InterruptedException e) {
            	// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        available = false;
        notifyAll();
        return data;
    }
    //returnare aux curent
    public int getAux() {
        return aux;
    }

}
