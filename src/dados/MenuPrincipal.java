package dados;
import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal {
    private JPanel panelMain;
    private JButton btnCadastraCliente;
    private JButton btnCadastraLocacao;
    private JButton btnCadastraRobo;
    private JFrame frame;
    private ACMERobots sistema;

    public MenuPrincipal(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        btnCadastraCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastraCliente cadastraClientePanel = new CadastraCliente(frame, sistema);
                frame.setContentPane(cadastraClientePanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCadastraLocacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastraLocacao cadastraLocacaoPanel = new CadastraLocacao(frame, sistema);
                cadastraLocacaoPanel.atualizarModelos(); //Atualiza a lista de robôs e clientes
                frame.setContentPane(cadastraLocacaoPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCadastraRobo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastraRobo cadastraRoboPanel = new CadastraRobo(frame, sistema);
                frame.setContentPane(cadastraRoboPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    public JPanel getPanelMain() {
        return panelMain;
    }
}
