package aplicacao;
import dados.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ACMERobots {
    private List<Robo> robos;
    private List<Cliente> clientes;
    private List<Locacao> locacoes;

    public ACMERobots() {
        this.robos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoes = new ArrayList<>();
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
        //método para entrar na fila de locacoes pendentes
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

    public static void main(String[] args) {
        ACMERobots sistema = new ACMERobots();

        JFrame frame = new JFrame("ACMERobots - Menu Principal");
        frame.setContentPane(new MenuPrincipal(frame, sistema).getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }
}
