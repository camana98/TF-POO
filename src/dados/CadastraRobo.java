package dados;
import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastraRobo {
    private JTextField idTextField;
    private JTextField modeloTextField;
    private JButton cadastrarButton;
    private JButton mostrarButton;
    private JPanel mainPanel;
    private JTextArea taMensagens;
    private JRadioButton domesticoRadioButton;
    private JRadioButton industrialRadioButton;
    private JRadioButton agricolaRadioButton;
    private JTextField setorTextField;
    private JTextField areaTextField;
    private JTextField usoTextField;
    private JButton voltarButton;
    private JButton limparButton;
    private JSpinner nivelSpinner;
    private JFrame frame;
    private ACMERobots sistema;

    public CadastraRobo(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        SpinnerModel nivelModel = new SpinnerNumberModel(1, 1, 3, 1);
        nivelSpinner.setModel(nivelModel);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(this.domesticoRadioButton);
        grupo.add(this.industrialRadioButton);
        grupo.add(this.agricolaRadioButton);

        this.domesticoRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelSpinner.setEnabled(true);
                CadastraRobo.this.setorTextField.setEnabled(false);
                CadastraRobo.this.areaTextField.setEnabled(false);
                CadastraRobo.this.usoTextField.setEnabled(false);
            }
        });
        this.industrialRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelSpinner.setEnabled(false);
                CadastraRobo.this.setorTextField.setEnabled(true);
                CadastraRobo.this.areaTextField.setEnabled(false);
                CadastraRobo.this.usoTextField.setEnabled(false);
            }
        });
        this.agricolaRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelSpinner.setEnabled(false);
                CadastraRobo.this.setorTextField.setEnabled(false);
                CadastraRobo.this.areaTextField.setEnabled(true);
                CadastraRobo.this.usoTextField.setEnabled(true);
            }
        });

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
            Robo robo = null;
            String tipo = null;

            // Verificar se já existe um robô com o mesmo ID
            for (Robo r : this.sistema.getRobos()) {
                if (r.getId() == id) {
                    this.taMensagens.setText("Erro: Já existe um robô com o ID " + id);
                    return;
                }
            }

            if (this.domesticoRadioButton.isSelected()) {
                int nivel = (Integer) this.nivelSpinner.getValue();
                double valorDiario = 0.0;
                if (nivel == 1) {
                    valorDiario = 10.0;
                } else if (nivel == 2) {
                    valorDiario = 20.0;
                } else if (nivel == 3) {
                    valorDiario = 50.0;
                }
                tipo = "DOMÉSTICO";
                robo = new Domestico(id, modelo, valorDiario, nivel, tipo);
            } else if (this.industrialRadioButton.isSelected()) {
                String setor = this.setorTextField.getText().trim();
                double valorDiario = 90.0;
                tipo = "INDUSTRIAL";
                robo = new Industrial(id, modelo, valorDiario, setor, tipo);
            } else if (this.agricolaRadioButton.isSelected()) {
                double area = Double.parseDouble(this.areaTextField.getText().trim());
                String uso = this.usoTextField.getText().trim();
                double valorDiario = area * 10.0;
                tipo = "AGRÍCOLA";
                robo = new Agricola(id, modelo, valorDiario, area, uso, tipo);
            } else {
                this.taMensagens.setText("Erro: Selecione um tipo de robô.");
                return;
            }

            this.sistema.cadastrarNovoRobo(robo);
            this.taMensagens.setText("Robô cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            this.taMensagens.setText("Erro: ID e área devem ser números.");
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
        this.setorTextField.setText("");
        this.areaTextField.setText("");
        this.usoTextField.setText("");
        this.taMensagens.setText("");
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
