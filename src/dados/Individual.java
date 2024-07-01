package dados;

public class Individual extends Cliente {
	private String cpf;

	public Individual(int codigo, String nome, String cpf) {
		super(codigo, nome);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	@Override
	public double calculaDesconto(int qtdeRobos) {
		if (qtdeRobos > 1) {
			return 0.05; // Desconto de 5%
		}
		return 0.0; // Sem desconto
	}

	@Override
	public String toString() {
		return super.toString() + ", CPF: " + cpf;
	}
}
