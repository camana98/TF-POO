package dados;

public class Empresarial extends Cliente {
	private int ano;

	public Empresarial(int codigo, String nome, int ano) {
		super(codigo, nome);
		this.ano = ano;
	}

	public int getAno() {
		return ano;
	}

	@Override
	public double calculaDesconto(int qtdeRobos) {
		if (qtdeRobos >= 2 && qtdeRobos <= 9) {
			return 0.03; // Desconto de 3%
		} else if (qtdeRobos > 10) {
			return 0.07; // Desconto de 7%
		}
		return 0.0; // Sem desconto
	}

	@Override
	public String toString() {
		return super.toString() + ", Ano: " + ano;
	}
}
