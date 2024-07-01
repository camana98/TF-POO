package dados;

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

	public double calculaValorFinal() {
		double valorLocacao = 0;
		for (Robo robo : robos) {
			valorLocacao += robo.calculaLocacao((int) ((dataFim.getTime() - dataInicio.getTime()) / (1000 * 60 * 60 * 24)));
		}
		double desconto = cliente.calculaDesconto();
		return valorLocacao - (valorLocacao * desconto);
	}
}
