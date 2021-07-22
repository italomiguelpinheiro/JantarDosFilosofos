package semaforos;



import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

	static Semaphore mutex = new Semaphore(1);
    static Semaphore[] semaforos = new Semaphore[5];
    static Filosofo[] filosofos = new Filosofo[5];
    static int[] estado = new int[5];

    public static void main(String[] args) {

        filosofos[0] = new Filosofo("Sócrates", 0);
        filosofos[1] = new Filosofo("Aristóteles", 1);
        filosofos[2] = new Filosofo("Platão", 2);
        filosofos[3] = new Filosofo("Descartes", 3);
        filosofos[4] = new Filosofo("Kant", 4);

        Arrays.fill(estado, Estado.PENSANDO.getValue());
        Arrays.fill(semaforos, new Semaphore(0));

        for (Filosofo filosofo : filosofos) {
            filosofo.start();
        }

        try {
            Thread.sleep(10000);
            System.exit(0);
        } catch (InterruptedException exception) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
