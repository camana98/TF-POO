package dados;

public class Agricola extends Robo {
	private double area;
	private String uso;

	public Agricola(int id, String modelo, double valorDiario, double area, String uso, String tipo) {
		super(id, modelo, valorDiario, tipo);
		this.area = area;
		this.uso = uso;
	}

	@Override
	public double calculaLocacao(int dias) {
		return dias * getValorDiario();
	}

	public double getArea() {
		return area;
	}

	public String getUso() {
		return uso;
	}

	@Override
	public String toString() {
		return super.toString() + ", Área: " + area + ", Uso: " + uso;
	}
}
