package dados;

public class Industrial extends Robo {
	private String setor;

	public Industrial(int id, String modelo, double valorDiario, String setor, String tipo) {
		super(id, modelo, valorDiario, tipo);
		this.setor = setor;
	}

	@Override
	public double calculaLocacao(int dias) {
		return dias * getValorDiario();
	}

	@Override
	public String toString() {
		return super.toString() + ", Setor: " + setor;
	}
}
