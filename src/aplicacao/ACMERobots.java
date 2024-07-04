package aplicacao;
import dados.*;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class ACMERobots {
    private List<Robo> robos;
    private List<Cliente> clientes;
    private List<Locacao> locacoes;
    private Queue<Locacao> locacoesPendentes;

    public ACMERobots() {
        this.robos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoes = new ArrayList<>();
        this.locacoesPendentes = new LinkedList<>();

    }

    public void cadastrarNovoRobo(Robo novoRobo) {

        robos.add(novoRobo);
        robos.sort(Comparator.comparingInt(Robo::getId));
    }

    public void cadastrarNovoCliente(Cliente novoCliente) {
        clientes.add(novoCliente);
        clientes.sort(Comparator.comparingInt(Cliente::getCodigo));
    }

    public void cadastrarNovaLocacao(Locacao novaLocacao) {
        locacoes.add(novaLocacao);
        locacoesPendentes.add(novaLocacao);
    }

    public Locacao buscarLocacaoPorNumero(int numero) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == numero) {
                return locacao;
            }
        }
        return null;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public Queue<Locacao> getLocacoesPendentes() {
        return locacoesPendentes;
    }

    public static void main(String[] args) {
        ACMERobots sistema = new ACMERobots();

        JFrame frame = new JFrame("ACMERobots - Menu Principal");
        frame.setContentPane(new MenuPrincipal(frame, sistema).getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setVisible(true);
    }
}
