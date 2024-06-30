package dados;

public class Domestico extends Robo {
	private int nivel;

	public Domestico(int id, String modelo, double valorDiario, int nivel) {
		super(id, modelo, valorDiario);
		this.nivel = nivel;
	}

	@Override
	public double calculaLocacao(int dias) {
		return dias * getValorDiario();
	}

	@Override
	public String toString() {
		return super.toString() + ", Nível: " + nivel;
	}
}

