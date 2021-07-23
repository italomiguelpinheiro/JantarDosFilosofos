package monitores;


import semaforos.Estado;

public class Filosofo extends Thread {
    int id;
    long TEMPO_OPERACAO = 1000L;

    public Filosofo(String nome, int id) {
        super(nome);
        this.id = id;
    }

    public void comFome() {
        monitores.Main.estado[this.id] = Estado.COM_FOME.getValue();
        System.out.println(getName() + ": com fome");
    }

    public void comer() {
        monitores.Main.estado[this.id] = Estado.COMENDO.getValue();
        System.out.println(getName() + ": comendo");
        try {
            Thread.sleep(TEMPO_OPERACAO);
        } catch (InterruptedException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void pensar() {
        monitores.Main.estado[this.id] = Estado.PENSANDO.getValue();
        System.out.println(getName() + ": pensando");
        try {
            Thread.sleep(TEMPO_OPERACAO);
        } catch (InterruptedException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public synchronized void largarGarfo() {
        this.pensar();
        monitores.Main.filosofos[vizinhoEsquerda()].tentarPegar();
        monitores.Main.filosofos[vizinhoDireita()].tentarPegar();
    }

    public synchronized void pegarGarfo() {
        this.comFome();
        tentarPegar();
    }

    public void tentarPegar() {
        if (monitores.Main.estado[this.id] == Estado.COM_FOME.getValue()
                && monitores.Main.estado[vizinhoEsquerda()] != Estado.COMENDO.getValue()
                && monitores.Main.estado[vizinhoDireita()] != Estado.COMENDO.getValue()) {
            this.comer();
        } else {
            System.out.println(getName() + ": não pode comer");
        }
    }

    @Override
    public void run() {
        try {
            this.pensar();
            while (true) {
                this.pegarGarfo();
                Thread.sleep(TEMPO_OPERACAO);
                this.largarGarfo();
            }
        } catch (InterruptedException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            return;
        }
    }

    private int vizinhoDireita() {
        return  (this.id + 1) % 5;
    }

    private int vizinhoEsquerda() {
        if (this.id == 0) {
            return 4;
        } else {
            return  (this.id - 1) % 5;
        }
    }
}