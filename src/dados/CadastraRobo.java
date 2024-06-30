package dados;
import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastraRobo {
    private JTextField idTextField;
    private JTextField modeloTextField;
    private JTextField valorDiarioTextField;
    private JButton cadastrarButton;
    private JButton mostrarButton;
    private JPanel mainPanel;
    private JTextArea taMensagens;
    private JRadioButton domesticoRadioButton;
    private JRadioButton industrialRadioButton;
    private JRadioButton agricolaRadioButton;
    private JTextField nivelTextField;
    private JTextField setorTextField;
    private JTextField areaTextField;
    private JTextField usoTextField;
    private JButton voltarButton;
    private JButton limparButton;
    private JFrame frame;
    private ACMERobots sistema;

    public CadastraRobo(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarRobo();
            }
        });

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRobos();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
    }

    private void cadastrarRobo() {
        try {
            int id = Integer.parseInt(this.idTextField.getText().trim());
            String modelo = this.modeloTextField.getText().trim();
            double valorDiario = Double.parseDouble(this.valorDiarioTextField.getText().trim());
            Robo robo = null;

            if (this.domesticoRadioButton.isSelected()) {
                int nivel = Integer.parseInt(this.nivelTextField.getText().trim());
                robo = new Domestico(id, modelo, valorDiario, nivel);
            } else if (this.industrialRadioButton.isSelected()) {
                String setor = this.setorTextField.getText().trim();
                robo = new Industrial(id, modelo, valorDiario, setor);
            } else if (this.agricolaRadioButton.isSelected()) {
                double area = Double.parseDouble(this.areaTextField.getText().trim());
                String uso = this.usoTextField.getText().trim();
                robo = new Agricola(id, modelo, valorDiario, area, uso);
            } else {
                this.taMensagens.setText("Erro: Selecione um tipo de robô.");
                return;
            }

            this.sistema.cadastrarNovoRobo(robo);
            this.taMensagens.setText("Robô cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            this.taMensagens.setText("Erro: ID, valor diário e área devem ser números.");
        }
    }

    private void mostrarRobos() {
        StringBuilder sb = new StringBuilder("Robôs cadastrados:\n");
        for (Robo robo : this.sistema.getRobos()) {
            sb.append(robo).append("\n");
        }
        this.taMensagens.setText(sb.toString());
    }

    private void voltar() {
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    private void limparCampos() {
        this.idTextField.setText("");
        this.modeloTextField.setText("");
        this.valorDiarioTextField.setText("");
        this.nivelTextField.setText("");
        this.setorTextField.setText("");
        this.areaTextField.setText("");
        this.usoTextField.setText("");
        this.taMensagens.setText("");
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
