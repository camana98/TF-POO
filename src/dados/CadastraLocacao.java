package dados;
import aplicacao.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class CadastraLocacao {
    private JPanel panelMain;
    private JTextField numeroField;
    private JComboBox<Cliente> clienteComboBox;
    private JList<Robo> robosList;
    private JSpinner dataInicioSpinner;
    private JSpinner dataFimSpinner;
    private JButton addButton;
    private JButton btnVoltar;
    private JFrame frame;
    private ACMERobots sistema;
    private DefaultComboBoxModel<Cliente> clienteModel;
    private DefaultListModel<Robo> robosListModel;

    public CadastraLocacao(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        // Inicializar o modelo do JComboBox
        clienteModel = new DefaultComboBoxModel<>();
        for (Cliente cliente : sistema.getClientes()) {
            clienteModel.addElement(cliente);
        }
        clienteComboBox.setModel(clienteModel);

        // Inicializar o modelo do JList
        robosListModel = new DefaultListModel<>();
        for (Robo robo : sistema.getRobos()) {
            robosListModel.addElement(robo);
        }
        robosList.setModel(robosListModel);

        // Configurar JSpinners para datas
        dataInicioSpinner.setModel(new SpinnerDateModel());
        dataFimSpinner.setModel(new SpinnerDateModel());

        // Adicionar ação ao botão de cadastro
        addButton.addActionListener(e -> cadastrarLocacao());

        // Adicionar ação ao botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void cadastrarLocacao() {
        try {
            int numero = Integer.parseInt(numeroField.getText());
            Cliente cliente = (Cliente) clienteComboBox.getSelectedItem();
            List<Robo> robosSelecionados = robosList.getSelectedValuesList();
            Date dataInicio = (Date) dataInicioSpinner.getValue();
            Date dataFim = (Date) dataFimSpinner.getValue();

            if (cliente == null || robosSelecionados.isEmpty()) {
                JOptionPane.showMessageDialog(panelMain, "Erro: Cliente ou Robôs não selecionados.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Locacao novaLocacao = new Locacao(numero, cliente, robosSelecionados, dataInicio, dataFim);
            sistema.cadastrarNovaLocacao(novaLocacao);
            JOptionPane.showMessageDialog(panelMain, "Locação cadastrada com sucesso!");
            limparCampos();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panelMain, "Erro: Dados inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        numeroField.setText("");
        clienteComboBox.setSelectedIndex(0);
        robosList.clearSelection();
        dataInicioSpinner.setValue(new Date());
        dataFimSpinner.setValue(new Date());
    }

    private void voltar() {
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    public JPanel getPanelMain() {
        return panelMain;
    }
}
