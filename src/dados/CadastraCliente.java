package dados;
import aplicacao.ACMERobots;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.TreeMap;

public class CadastraCliente {
    private JPanel panelMain;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfCpf;
    private JTextField tfAno;
    private JTextArea taMensagens;
    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnMostrarClientes;
    private JButton btnVoltar;
    private JRadioButton rbIndividual;
    private JRadioButton rbEmpresarial;
    private Map<Integer, Cliente> clientes;
    private JFrame frame;
    private ACMERobots sistema;

    public CadastraCliente(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        clientes = new TreeMap<>();

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbIndividual);
        grupo.add(rbEmpresarial);

        rbIndividual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfCpf.setEnabled(true);
                tfAno.setEnabled(false);
            }
        });

        rbEmpresarial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfCpf.setEnabled(false);
                tfAno.setEnabled(true);
            }
        });

        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        btnLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        btnMostrarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarClientes();
            }
        });

        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void cadastrarCliente() {
        try {
            int codigo = Integer.parseInt(tfCodigo.getText().trim());
            String nome = tfNome.getText().trim();

            if (clientes.containsKey(codigo)) {
                taMensagens.setText("Erro: Código de cliente já existente.");
            } else {
                Cliente cliente;
                if (rbIndividual.isSelected()) {
                    String cpf = tfCpf.getText().trim();
                    cliente = new Individual(codigo, nome, cpf);
                } else if (rbEmpresarial.isSelected()) {
                    int ano = Integer.parseInt(tfAno.getText().trim());
                    cliente = new Empresarial(codigo, nome, ano);
                } else {
                    taMensagens.setText("Erro: Selecione um tipo de cliente.");
                    return;
                }
                clientes.put(codigo, cliente);
                taMensagens.setText("Cliente cadastrado com sucesso.");
            }
        } catch (NumberFormatException e) {
            taMensagens.setText("Erro: Código, ano e CPF devem ser números inteiros.");
        }
    }

    private void limparCampos() {
        tfCodigo.setText("");
        tfNome.setText("");
        tfCpf.setText("");
        tfAno.setText("");
        taMensagens.setText("");
    }

    private void mostrarClientes() {
        StringBuilder sb = new StringBuilder("Clientes cadastrados:\n");
        for (Cliente cliente : clientes.values()) {
            sb.append(cliente).append("\n");
        }
        taMensagens.setText(sb.toString());
    }

    private void voltar(){
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

}
