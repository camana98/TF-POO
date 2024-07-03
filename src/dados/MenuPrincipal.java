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
    private JButton btnAlteraSituacao;
    private JButton btnConsultarLocacoes;
    private JButton bntMostrarRelatorioGeral;
    private JButton btnCarregaDadosIniciais;
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

        btnAlteraSituacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AlteraSituacao alteraSituacaoPanel = new AlteraSituacao(frame, sistema);
                frame.setContentPane(alteraSituacaoPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

        btnConsultarLocacoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConsultarLocacoes consultarLocacoesPanel = new ConsultarLocacoes(frame, sistema);
                frame.setContentPane(consultarLocacoesPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

        bntMostrarRelatorioGeral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MostrarRelatorioGeral mostarRelatorioGeralPanel = new MostrarRelatorioGeral(frame, sistema);
                frame.setContentPane(mostarRelatorioGeralPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

        btnCarregaDadosIniciais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarregaDadosIniciais carregaDadosIniciaisPanel = new CarregaDadosIniciais(frame, sistema);
                frame.setContentPane(carregaDadosIniciaisPanel.getPanelMain());
                frame.revalidate();
                frame.repaint();
            }
        });

    }

    public JPanel getPanelMain() {
        return panelMain;
    }
}
