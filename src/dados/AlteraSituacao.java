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

        atualizarListas();
    }

    private void alterarSituacao() {
        try {
            int numero = Integer.parseInt(numeroLocacaoTextField.getText().trim());
            Locacao locacao = sistema.buscarLocacaoPorNumero(numero);

            if (locacao == null) {
                mensagensTextArea.setText("Erro: Não há locação com o número indicado.");
                return;
            }

            if (locacao.getSituacao() == Status.FINALIZADA || locacao.getSituacao() == Status.CANCELADA) {
                mensagensTextArea.setText("Erro: Não é possível alterar uma locação FINALIZADA ou CANCELADA.");
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
                mensagensTextArea.setText("Locação " + locacao.getNumero() + " foi " + novoStatus + ".");
            } else {
                mensagensTextArea.setText("Erro: Situação inválida.");
            }

            atualizarListas();

        } catch (NumberFormatException ex) {
            mensagensTextArea.setText("Erro: Número inválido.");
        }
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
