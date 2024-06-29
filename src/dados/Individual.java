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
	public double calculaDesconto() {
		// Implementação específica para Individual
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + ", CPF: " + cpf;
	}
}
