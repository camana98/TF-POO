package dados;

import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.util.List;

public class CarregaDados {
    private JPanel panelMain;
    private JTextField pastaTextField;
    private JButton selecionarPastaButton;
    private JList<String> arquivosList;
    private JButton listarArquivosButton;
    private JButton carregarButton;
    private JButton voltarButton;
    private JTextArea mensagensTextArea;
    private JButton limparButton;
    private DefaultListModel<String> listModel;
    private JFrame frame;
    private ACMERobots sistema;

    public CarregaDados(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;
        listModel = new DefaultListModel<>();
        arquivosList.setModel(listModel);
        arquivosList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        selecionarPastaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarPasta();
            }
        });

        listarArquivosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarArquivos();
            }
        });

        carregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDados();
            }
        });

        limparButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

    }

    private void selecionarPasta() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File pasta = fileChooser.getSelectedFile();
            pastaTextField.setText(pasta.getAbsolutePath());
            listarArquivos(pasta);
        }
    }

    private void listarArquivos() {
        listModel.clear();
        String caminhoPasta = pastaTextField.getText().trim();
        if (caminhoPasta.isEmpty()) {
            mensagensTextArea.setText("Erro: Caminho da pasta não pode ser vazio.");
            return;
        }
        File pasta = new File(caminhoPasta);
        if (!pasta.isDirectory()) {
            mensagensTextArea.setText("Erro: Caminho da pasta inválido.");
            return;
        }
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".CSV"));
        if (arquivos != null && arquivos.length > 0) {
            for (File arquivo : arquivos) {
                listModel.addElement(arquivo.getAbsolutePath());
            }
            mensagensTextArea.setText("Arquivos listados com sucesso.");
        } else {
            mensagensTextArea.setText("Erro: Nenhum arquivo CSV encontrado na pasta.");
        }
    }


    private void listarArquivos(File pasta) {
        listModel.clear();
        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".CSV"));
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                listModel.addElement(arquivo.getAbsolutePath());
            }
        } else {
            mensagensTextArea.setText("Erro: Nenhum arquivo CSV encontrado na pasta.");
        }
    }

    private void carregarDados() {
        List<String> arquivosSelecionados = arquivosList.getSelectedValuesList();
        if (arquivosSelecionados.isEmpty()) {
            mensagensTextArea.setText("Erro: Nenhum arquivo selecionado.");
            return;
        }

        CarregaDadosIniciais dadosIniciais = new CarregaDadosIniciais(frame, sistema);

        //carrega robôs e clientes
        for (String caminhoArquivo : arquivosSelecionados) {
            try {
                if (caminhoArquivo.contains("-ROBOS")) {
                    dadosIniciais.carregarRobos(caminhoArquivo);
                    mensagensTextArea.append("Robôs carregados com sucesso: " + caminhoArquivo + "\n");
                } else if (caminhoArquivo.contains("-CLIENTES")) {
                    dadosIniciais.carregarClientes(caminhoArquivo);
                    mensagensTextArea.append("Clientes carregados com sucesso: " + caminhoArquivo + "\n");
                }
            } catch (IOException e) {
                mensagensTextArea.append("Erro ao carregar dados do arquivo " + caminhoArquivo + ": " + e.getMessage() + "\n");
            }
        }

        //carrega locações
        for (String caminhoArquivo : arquivosSelecionados) {
            try {
                if (caminhoArquivo.contains("-LOCACOES")) {
                    dadosIniciais.carregarLocacoes(caminhoArquivo);
                    mensagensTextArea.append("Locações carregadas com sucesso: " + caminhoArquivo + "\n");
                }
            } catch (IOException | ParseException e) {
                mensagensTextArea.append("Erro ao carregar dados do arquivo " + caminhoArquivo + ": " + e.getMessage() + "\n");
            }
        }

        mostrarDadosCadastrados();
    }

    private void mostrarDadosCadastrados() {
        StringBuilder sb = new StringBuilder();
        sb.append("Robôs cadastrados:\n");
        for (Robo robo : sistema.getRobos()) {
            sb.append(robo).append("\n");
        }
        sb.append("\nClientes cadastrados:\n");
        for (Cliente cliente : sistema.getClientes()) {
            sb.append(cliente).append("\n");
        }
        sb.append("\nLocações cadastradas:\n");
        for (Locacao locacao : sistema.getLocacoes()) {
            sb.append(locacao).append("\n");
        }
        mensagensTextArea.setText(sb.toString());
    }

    private void voltar() {
        MenuPrincipal mainMenu = new MenuPrincipal(frame, sistema);
        frame.setContentPane(mainMenu.getPanelMain());
        frame.revalidate();
        frame.repaint();
    }

    public void limparCampos(){
        this.pastaTextField.setText("");
        this.mensagensTextArea.setText("");
        this.listModel.clear();
    }


    public JPanel getPanelMain() {
        return panelMain;
    }
}
