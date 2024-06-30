package dados;
import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastraCliente {
    private JTextField codigoTextField;
    private JTextField nomeTextField;
    private JButton cadastrarButton;
    private JButton mostrarButton;
    private JPanel mainPanel;
    private JTextArea taMensagens;
    private JRadioButton individualRadioButton;
    private JRadioButton empresarialRadioButton;
    private JTextField cpfTextField;
    private JTextField anoTextField;
    private JButton voltarButton;
    private JButton limparButton;
    private JFrame frame;
    private ACMERobots sistema;

    public CadastraCliente(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
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

    private void cadastrarCliente() {
        try {
            int codigo = Integer.parseInt(this.codigoTextField.getText().trim());
            String nome = this.nomeTextField.getText().trim();
            Cliente cliente = null;

            if (this.individualRadioButton.isSelected()) {
                String cpf = this.cpfTextField.getText().trim();
                cliente = new Individual(codigo, nome, cpf);
            } else if (this.empresarialRadioButton.isSelected()) {
                int ano = Integer.parseInt(this.anoTextField.getText().trim());
                cliente = new Empresarial(codigo, nome, ano);
            } else {
                this.taMensagens.setText("Erro: Selecione um tipo de cliente.");
                return;
            }

            this.sistema.cadastrarNovoCliente(cliente);
            this.taMensagens.setText("Cliente cadastrado com sucesso.");
        } catch (NumberFormatException e) {
            this.taMensagens.setText("Erro: Código e ano devem ser números.");
        }
    }

    private void mostrarClientes() {
        StringBuilder sb = new StringBuilder("Clientes cadastrados:\n");
        for (Cliente cliente : this.sistema.getClientes()) {
            sb.append(cliente).append("\n");
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
        this.codigoTextField.setText("");
        this.nomeTextField.setText("");
        this.cpfTextField.setText("");
        this.anoTextField.setText("");
        this.taMensagens.setText("");
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
