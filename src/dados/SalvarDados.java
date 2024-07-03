package dados;

import aplicacao.ACMERobots;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class SalvarDados {
    private JPanel mainPanel;
    private JTextField nomeDoArquivoTextField;
    private JTextArea textArea1;
    private JButton voltarButton;
    private JButton salvarButton;
    private JFrame frame;
    private ACMERobots sistema;

    public SalvarDados(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDados();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void salvarDados() {
        String nomeDoArquivo = nomeDoArquivoTextField.getText().trim();

        if (nomeDoArquivo.isEmpty()) {
            textArea1.setText("Erro: O nome do arquivo não pode ser vazio.");
            return;
        }

        try {
            salvarRobos(nomeDoArquivo + "-ROBOS.csv");
            salvarClientes(nomeDoArquivo + "-CLIENTES.csv");
            salvarLocacoes(nomeDoArquivo + "-LOCACOES.csv");
            textArea1.setText("Dados salvos com sucesso.");
        } catch (IOException e) {
            textArea1.setText("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private void salvarRobos(String nomeDoArquivo) throws IOException {
        FileWriter writer = new FileWriter(nomeDoArquivo);
        for (Robo robo : sistema.getRobos()) {
            writer.write(robo.toString() + "\n");
        }
        writer.close();
    }

    private void salvarClientes(String nomeDoArquivo) throws IOException {
        FileWriter writer = new FileWriter(nomeDoArquivo);
        for (Cliente cliente : sistema.getClientes()) {
            writer.write(cliente.toString() + "\n");
        }
        writer.close();
    }

    private void salvarLocacoes(String nomeDoArquivo) throws IOException {
        FileWriter writer = new FileWriter(nomeDoArquivo);
        for (Locacao locacao : sistema.getLocacoes()) {
            writer.write(locacao.toString() + "\n");
        }
        writer.close();
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
