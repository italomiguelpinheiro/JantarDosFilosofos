package semaforos;

public enum Estado {
	PENSANDO(0), COM_FOME(1), COMENDO(2);

	public int estado;

	Estado(int estado) {
	        this.estado= estado;
	    }

	public int getValue() {
		return this.estado;
	}

}
