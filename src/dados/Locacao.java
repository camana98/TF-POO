package dados;

import java.text.SimpleDateFormat;
import java.util.*;


public class Locacao {
	private int numero;
	private Status situacao;
	private Date dataInicio;
	private Date dataFim;
	private Cliente cliente;
	private List<Robo> robos;

	public Locacao(int numero, Cliente cliente, List<Robo> robos, Date dataInicio, Date dataFim) {
		this.numero = numero;
		this.cliente = cliente;
		this.robos = robos;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.situacao = Status.CADASTRADA;
	}

	public int getNumero() {
		return numero;
	}

	public Status getSituacao() {
		return situacao;
	}

	public void setSituacao(Status situacao) {
		if (this.situacao != Status.FINALIZADA && this.situacao != Status.CANCELADA) {
			this.situacao = situacao;
		}
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<Robo> getRobos() {
		return robos;
	}

	private int getDuracaoDias() {
		long diff = dataFim.getTime() - dataInicio.getTime();
		return (int) ((diff + 1000 * 60 * 60 * 24 - 1) / (1000 * 60 * 60 * 24)); // Adiciona 1 para garantir arredondamento correto
	}


	public double calculaValorFinal() {
		double valorLocacao = 0;
		for (Robo robo : robos) {
			valorLocacao += robo.calculaLocacao(getDuracaoDias());
		}
		double desconto = cliente.calculaDesconto(getRobos().size());
		return valorLocacao - (valorLocacao * desconto);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Número: ").append(numero).append("\n")
				.append("Situação: ").append(situacao).append("\n")
				.append("Data Início: ").append(new SimpleDateFormat("dd/MM/yyyy").format(dataInicio)).append("\n")
				.append("Data Fim: ").append(new SimpleDateFormat("dd/MM/yyyy").format(dataFim)).append("\n")
				.append("Cliente: ").append(cliente).append("\n")
				.append("Robôs: ").append("\n");
		for (Robo robo : robos) {
			sb.append("\t").append(robo).append("\n");
		}
		return sb.toString();
	}

}
