package monitores;


import semaforos.Estado;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static Filosofo[] filosofos = new Filosofo[5];
    static int[] estado = new int[5];

    public static void main(String[] args) {

        Arrays.fill(estado, Estado.PENSANDO.getValue());

        filosofos[0] = new Filosofo("Pondé", 0);
        filosofos[1] = new Filosofo("Olavo de Carvalho", 1);
        filosofos[2] = new Filosofo("Clóvis de Barros", 2);
        filosofos[3] = new Filosofo("Cortella", 3);
        filosofos[4] = new Filosofo("Leandro Karnal", 4);

        for (Filosofo filosofo : filosofos) {
            filosofo.start();
        }

        try {
            Thread.sleep(10000);
            System.exit(0);
        } catch (InterruptedException exception) {
            Logger.getLogger(semaforos.Main.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}