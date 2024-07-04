package dados;

import aplicacao.ACMERobots;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConsultarLocacoes {
    private JPanel mainPanel;
    private JButton voltarButton;
    private JScrollPane textArea;
    private ACMERobots sistema;
    private JFrame frame;

    public ConsultarLocacoes(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;


        JTextArea textAreaContent = new JTextArea();
        textAreaContent.setEditable(false);
        textArea.setViewportView(textAreaContent);

        mostrarLocacoes(textAreaContent);

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void mostrarLocacoes(JTextArea textAreaContent) {
        List<Locacao> locacoes = sistema.getLocacoes();
        if (locacoes.isEmpty()) {
            textAreaContent.setText("Nenhuma locação cadastrada.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Locacao locacao : locacoes) {
                sb.append("Número: ").append(locacao.getNumero()).append("\n");
                sb.append("Cliente: ").append(locacao.getCliente()).append("\n");
                sb.append("Data Início: ").append(locacao.getDataInicio()).append("\n");
                sb.append("Data Fim: ").append(locacao.getDataFim()).append("\n");
                sb.append("Situação: ").append(locacao.getSituacao()).append("\n");
                sb.append("Robôs:\n");
                for (Robo robo : locacao.getRobos()) {
                    sb.append("\t").append(robo).append("\n");
                }
                sb.append("Valor Final: ").append(locacao.calculaValorFinal()).append("\n");
                sb.append("-------------------------------\n");
            }
            textAreaContent.setText(sb.toString());
        }
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
