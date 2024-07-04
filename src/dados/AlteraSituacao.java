package dados;

import aplicacao.ACMERobots;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlteraSituacao {
    private JPanel mainPanel;
    private JButton alterarButton;
    private JTextArea mensagensTextArea;
    private JTextArea pendentesTextArea;
    private JTextArea todasTextArea;
    private JButton voltarButton;
    private JTextField numeroLocacaoTextField;
    private JComboBox<Status> statusComboBox;
    private ACMERobots sistema;
    private JFrame frame;
    private JButton processarLocacoesButton; // Novo botão

    public AlteraSituacao(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        for (Status status : Status.values()) {
            if (status == Status.FINALIZADA || status == Status.CANCELADA) {
                statusComboBox.addItem(status);
            }
        }

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alterarSituacao();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

        processarLocacoesButton.addActionListener(new ActionListener() { // Listener para o novo botão
            @Override
            public void actionPerformed(ActionEvent e) {
                processarLocacoes();
            }
        });

        atualizarListas();
    }

    private void alterarSituacao() {
        try {
            int numero = Integer.parseInt(numeroLocacaoTextField.getText().trim());
            Locacao locacao = sistema.buscarLocacaoPorNumero(numero);

            if (locacao == null) {
                mensagensTextArea.setText("Erro: Não há locação com o número indicado.\n");
                return;
            }

            if (locacao.getSituacao() == Status.FINALIZADA || locacao.getSituacao() == Status.CANCELADA) {
                mensagensTextArea.setText("Erro: Não é possível alterar uma locação FINALIZADA ou CANCELADA.\n");
                return;
            }

            Status novoStatus = (Status) statusComboBox.getSelectedItem();

            if (novoStatus == Status.CANCELADA || novoStatus == Status.FINALIZADA) {
                locacao.setSituacao(novoStatus);
                for (Robo robo : locacao.getRobos()) {
                    robo.setSituacao(Status.DISPONIVEL);
                }
                if (novoStatus == Status.FINALIZADA || novoStatus == Status.CANCELADA) {
                    sistema.getLocacoesPendentes().remove(locacao);
                }
                mensagensTextArea.setText("Locação " + locacao.getNumero() + " foi " + novoStatus + ".\n");
            } else {
                mensagensTextArea.setText("Erro: Situação inválida.\n");
            }

            atualizarListas();

        } catch (NumberFormatException ex) {
            mensagensTextArea.setText("Erro: Número inválido.\n");
        }
    }

    private void processarLocacoes() {
        if (sistema.getLocacoesPendentes().isEmpty()) {
            mensagensTextArea.setText("Erro: Não há locações na fila de locações pendentes.\n");
            return;
        }

        Locacao locacao = sistema.getLocacoesPendentes().peek(); // Obter a primeira locação da fila

        if (locacao != null) {
            boolean todosRobosDisponiveis = true;
            for (Robo robo : locacao.getRobos()) {
                if (robo.getSituacao() == Status.OCUPADO) {
                    todosRobosDisponiveis = false;
                    break;
                }
            }

            if (todosRobosDisponiveis) {
                for (Robo robo : locacao.getRobos()) {
                    robo.setSituacao(Status.OCUPADO);
                }
                locacao.setSituacao(Status.EXECUTANDO);
                sistema.getLocacoesPendentes().remove(locacao); // Remover a locação da fila de pendentes
                mensagensTextArea.append("Locação " + locacao.getNumero() + " está agora EXECUTANDO.\n");
            } else {
                mensagensTextArea.append("Locação " + locacao.getNumero() + " não pode ser processada, um ou mais robôs estão ocupados.\n");
            }
        }

        atualizarListas();
    }


    private void atualizarListas() {
        StringBuilder pendentes = new StringBuilder("Locações Pendentes:\n");
        for (Locacao locacao : sistema.getLocacoesPendentes()) {
            pendentes.append(locacao.getNumero()).append(" - ").append(locacao.getSituacao()).append("\n");
        }
        pendentesTextArea.setText(pendentes.toString());

        StringBuilder todas = new StringBuilder("Todas as Locações:\n");
        for (Locacao locacao : sistema.getLocacoes()) {
            todas.append(locacao.getNumero()).append(" - ").append(locacao.getSituacao()).append("\n");
        }
        todasTextArea.setText(todas.toString());
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
