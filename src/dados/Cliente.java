package dados;

public abstract class Cliente {
	private int codigo;
	private String nome;

	public Cliente(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public abstract double calculaDesconto(int quantidadeRobos);

	@Override
	public String toString() {
		return "Código: " + codigo + ", Nome: " + nome;
	}
}
