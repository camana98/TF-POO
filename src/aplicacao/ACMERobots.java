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

        // Adicionar alguns robôs e clientes para teste
        robos.add(new Domestico(1, "Robô Doméstico", 10.0, 1));
        robos.add(new Industrial(2, "Robô Industrial", 90.0, "Setor A"));
        robos.add(new Agricola(3, "Robô Agrícola", 20.0, 100.0, "Plantação"));

        clientes.add(new Individual(1, "Cliente Individual", "123.456.789-00"));
        clientes.add(new Empresarial(2, "Cliente Empresarial", 2005));
    }

    public void cadastrarNovoRobo(Robo novoRobo) {
        for (Robo robo : robos) {
            if (robo.getId() == novoRobo.getId()) {
                System.out.println("Erro: Já existe um robô com o ID " + novoRobo.getId());
                return;
            }
        }
        robos.add(novoRobo);
        robos.sort(Comparator.comparingInt(Robo::getId));
        System.out.println("Robô cadastrado com sucesso!");
    }

    public void cadastrarNovoCliente(Cliente novoCliente) {
        for (Cliente cliente : clientes) {
            if (cliente.getCodigo() == novoCliente.getCodigo()) {
                System.out.println("Erro: Já existe um cliente com o código " + novoCliente.getCodigo());
                return;
            }
        }
        clientes.add(novoCliente);
        clientes.sort(Comparator.comparingInt(Cliente::getCodigo));
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void cadastrarNovaLocacao(Locacao novaLocacao) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == novaLocacao.getNumero()) {
                System.out.println("Erro: Já existe uma locação com o número " + novaLocacao.getNumero());
                return;
            }
        }
        locacoes.add(novaLocacao);
        System.out.println("Locação cadastrada com sucesso!");
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
