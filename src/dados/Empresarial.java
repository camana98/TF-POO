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
	public double calculaDesconto(int quantidadeRobos) {
		// Implementação específica para Empresarial
		return 0.0;
	}

	@Override
	public String toString() {
		return super.toString() + ", Ano: " + ano;
	}
}
