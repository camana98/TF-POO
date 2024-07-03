package dados;

import aplicacao.ACMERobots;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MostrarRelatorioGeral {
    private JPanel mainPanel;
    private JScrollPane ScrollPaneClientes;
    private JScrollPane ScrollPaneRobos;
    private JScrollPane ScrollPaneLocacoes;
    private JButton voltarButton;
    private ACMERobots sistema;
    private JFrame frame; // Adicione esta linha

    public MostrarRelatorioGeral(JFrame frame, ACMERobots sistema) {
        this.sistema = sistema;
        this.frame = frame; // Inicialize a variável frame

        // Initialize and set JTextAreas to ScrollPanes
        JTextArea textAreaClientes = new JTextArea();
        textAreaClientes.setEditable(false);
        ScrollPaneClientes.setViewportView(textAreaClientes);

        JTextArea textAreaRobos = new JTextArea();
        textAreaRobos.setEditable(false);
        ScrollPaneRobos.setViewportView(textAreaRobos);

        JTextArea textAreaLocacoes = new JTextArea();
        textAreaLocacoes.setEditable(false);
        ScrollPaneLocacoes.setViewportView(textAreaLocacoes);

        // Display data
        mostrarRelatorio(textAreaClientes, textAreaRobos, textAreaLocacoes);

        // Add action listener to the voltar button
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void mostrarRelatorio(JTextArea textAreaClientes, JTextArea textAreaRobos, JTextArea textAreaLocacoes) {
        StringBuilder clientesSB = new StringBuilder("Clientes cadastrados:\n");
        List<Cliente> clientes = sistema.getClientes();
        if (clientes.isEmpty()) {
            clientesSB.append("Nenhum cliente cadastrado.\n");
        } else {
            for (Cliente cliente : clientes) {
                clientesSB.append(cliente).append("\n");
            }
        }
        textAreaClientes.setText(clientesSB.toString());

        StringBuilder robosSB = new StringBuilder("Robôs cadastrados:\n");
        List<Robo> robos = sistema.getRobos();
        if (robos.isEmpty()) {
            robosSB.append("Nenhum robô cadastrado.\n");
        } else {
            for (Robo robo : robos) {
                robosSB.append(robo).append("\n");
            }
        }
        textAreaRobos.setText(robosSB.toString());

        StringBuilder locacoesSB = new StringBuilder("Locações cadastradas:\n");
        List<Locacao> locacoes = sistema.getLocacoes();
        if (locacoes.isEmpty()) {
            locacoesSB.append("Nenhuma locação cadastrada.\n");
        } else {
            for (Locacao locacao : locacoes) {
                locacoesSB.append("Número: ").append(locacao.getNumero()).append("\n");
                locacoesSB.append("Cliente: ").append(locacao.getCliente()).append("\n");
                locacoesSB.append("Data Início: ").append(locacao.getDataInicio()).append("\n");
                locacoesSB.append("Data Fim: ").append(locacao.getDataFim()).append("\n");
                locacoesSB.append("Situação: ").append(locacao.getSituacao()).append("\n");
                locacoesSB.append("Robôs:\n");
                for (Robo robo : locacao.getRobos()) {
                    locacoesSB.append("\t").append(robo).append("\n");
                }
                locacoesSB.append("Valor Final: ").append(locacao.calculaValorFinal()).append("\n");
                locacoesSB.append("-------------------------------\n");
            }
        }
        textAreaLocacoes.setText(locacoesSB.toString());
    }

    private void voltar() {
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
