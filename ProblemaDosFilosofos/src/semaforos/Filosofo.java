package semaforos;

public class Filosofo extends Thread {
	int id;
	long TEMPO_OPERACAO = 1000L;

	public Filosofo(String nome, int id) {
		super(nome);
		this.id = id;
	}

	public void comFome() {
		Main.estado[this.id] = Estado.COM_FOME.getValue();
		System.out.println(getName() + ": com fome");
	}

	public void comer() {
		Main.estado[this.id] = Estado.COMENDO.getValue();
		System.out.println(getName() + ": comendo");
		try {
			Thread.sleep(TEMPO_OPERACAO);
		} catch (InterruptedException e) {
			System.out.println("ERROR >> " + e.getMessage());
		}
	}

	public void pensar() {
		Main.estado[this.id] = Estado.PENSANDO.getValue();
		System.out.println(getName() + ": pensando");
		try {
			Thread.sleep(TEMPO_OPERACAO);
		} catch (InterruptedException e) {
			System.out.println("ERROR:  " + e.getMessage());
		}
	}

	public void largarGarfo() throws InterruptedException {
		Main.mutex.acquire();
		this.pensar();
		Main.filosofos[vizinhoEsquerda()].tentaPegar();
		Main.filosofos[vizinhoDireita()].tentaPegar();
		Main.mutex.release();
	}

	public void pegarGarfo() throws InterruptedException {
		Main.mutex.acquire();
		this.comFome();
		tentaPegar();
		Main.mutex.release();
		Main.semaforos[this.id].acquire();
	}

	public void tentaPegar() {
		if (Main.estado[this.id] == Estado.COM_FOME.getValue()
				&& Main.estado[vizinhoEsquerda()] != Estado.COMENDO.getValue()
				&& Main.estado[vizinhoDireita()] != Estado.COMENDO.getValue()) {
			this.comer();
			Main.semaforos[this.id].release();
		} else {
			System.out.println(getName() + " nao pode comer");
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
		return (this.id + 1) % 5;
	}

	private int vizinhoEsquerda() {
		if (this.id == 0) {
			return 4;
		} else {
			return (this.id - 1) % 5;
		}
	}

	

}
