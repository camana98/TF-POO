package dados;

import aplicacao.ACMERobots;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class CadastraRobo {
    private JPanel mainPanel;
    private JRadioButton domesticoRadioButton;
    private JRadioButton industrialRadioButton;
    private JRadioButton agricolaRadioButton;
    private JTextField idTextField;
    private JTextField modeloTextField;
    private JTextField valorDiarioTextField;
    private JTextField nivelTextField;
    private JTextField setorTextField;
    private JTextField areaTextField;
    private JTextField usoTextField;
    private JButton cadastrarButton;
    private JButton limparButton;
    private JButton mostrarButton;
    private JButton fecharButton;
    private JTextArea textArea1;
    private JFrame frame;
    private Map<Integer, Robo> robos;
    private ACMERobots sistema;

    public CadastraRobo(JFrame frame, ACMERobots sistema) {

        this.frame = frame;
        this.sistema = sistema;

        this.robos = new TreeMap<>();

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(this.domesticoRadioButton);
        grupo.add(this.industrialRadioButton);
        grupo.add(this.agricolaRadioButton);

        this.domesticoRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelTextField.setEnabled(true);
                CadastraRobo.this.setorTextField.setEnabled(false);
                CadastraRobo.this.areaTextField.setEnabled(false);
                CadastraRobo.this.usoTextField.setEnabled(false);
            }
        });
        this.industrialRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelTextField.setEnabled(false);
                CadastraRobo.this.setorTextField.setEnabled(true);
                CadastraRobo.this.areaTextField.setEnabled(false);
                CadastraRobo.this.usoTextField.setEnabled(false);
            }
        });
        this.agricolaRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.nivelTextField.setEnabled(false);
                CadastraRobo.this.setorTextField.setEnabled(false);
                CadastraRobo.this.areaTextField.setEnabled(true);
                CadastraRobo.this.usoTextField.setEnabled(true);
            }
        });

        this.cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.cadastrarRobo();
            }
        });
        this.limparButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.limparCampos();
            }
        });
        this.mostrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CadastraRobo.this.mostrarRobos();
            }
        });
        this.fecharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void cadastrarRobo() {
        try {
            int id = Integer.parseInt(this.idTextField.getText().trim());
            String modelo = this.modeloTextField.getText().trim();
            double valorDiario = Double.parseDouble(this.valorDiarioTextField.getText().trim());

            if (this.robos.containsKey(id)) {
                this.textArea1.setText("Erro: ID de robô já existente.");
            } else {
                Robo robo;
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
                    this.textArea1.setText("Erro: Selecione um tipo de robô.");
                    return;
                }

                this.robos.put(id, robo);
                this.textArea1.setText("Robô cadastrado com sucesso.");
            }
        } catch (NumberFormatException e) {
            this.textArea1.setText("Erro: ID, valor diário e área devem ser números.");
        }
    }

    private void limparCampos() {
        this.idTextField.setText("");
        this.modeloTextField.setText("");
        this.valorDiarioTextField.setText("");
        this.nivelTextField.setText("");
        this.setorTextField.setText("");
        this.areaTextField.setText("");
        this.usoTextField.setText("");
        this.textArea1.setText("");
    }

    private void mostrarRobos() {
        StringBuilder sb = new StringBuilder("Robôs cadastrados:\n");
        Iterator<Robo> iterator = this.robos.values().iterator();

        while (iterator.hasNext()) {
            Robo robo = iterator.next();
            sb.append(robo).append("\n");
        }

        this.textArea1.setText(sb.toString());
    }

/*    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Cadastrar Robô");
                CadastraRobo cadastraRobo = new CadastraRobo();
                frame.setContentPane(cadastraRobo.mainPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }*/

    private void voltar(){
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanelMain() {
        return mainPanel;
    }
}
