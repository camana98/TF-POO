package dados;

public abstract class Robo {
	private int id;
	private String modelo;
	private double valorDiario;
	private Status situacao;

	public Robo(int id, String modelo, double valorDiario) {
		this.id = id;
		this.modelo = modelo;
		this.valorDiario = valorDiario;
		this.situacao = Status.DISPONIVEL;
	}

	public int getId() {
		return id;
	}

	public Status getSituacao() { return situacao;}

	public void setSituacao(Status status) { this.situacao = status; }

	public String getModelo() {
		return modelo;
	}

	public double getValorDiario() {
		return valorDiario;
	}

	public abstract double calculaLocacao(int dias);

	@Override
	public String toString() {
		return "ID: " + id + ", Modelo: " + modelo + ", Valor Diário: " + valorDiario;
	}



}
