package dados;

public class Individual extends Cliente {
	private int cpf;

	public Individual(int codigo, String nome, int cpf) {
		super(codigo, nome);
		this.cpf = cpf;
	}

	public int getCpf() {
		return cpf;
	}

	@Override
	public double calculaDesconto(int quantidadeRobos) {
		// Implementação específica para Individual
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + ", CPF: " + cpf;
	}
}
