package dados;

public abstract class Robo {
	private int id;
	private String modelo;
	private double valorDiario;
	private Status situacao;
	private String tipo;

	public Robo(int id, String modelo, double valorDiario, String tipo) {
		this.id = id;
		this.modelo = modelo;
		this.valorDiario = valorDiario;
		this.situacao = Status.DISPONIVEL;
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo; // Método getter para o tipo
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
		return "ID: " + id + ", Modelo: " + modelo + ", Valor Diário: " + valorDiario + ", Tipo: " + tipo;
	}


}
