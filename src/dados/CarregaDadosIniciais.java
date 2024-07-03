package dados;

import aplicacao.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CarregaDadosIniciais {
    private JPanel panelMain;
    private JTextField arquivoTextField;
    private JButton carregarButton;
    private JButton voltarButton;
    private JTextArea mensagensTextArea;
    private JFrame frame;
    private ACMERobots sistema;

    public CarregaDadosIniciais(JFrame frame, ACMERobots sistema) {
        this.frame = frame;
        this.sistema = sistema;

        carregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDados();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });

    }

    private void carregarDados() {
        String nomeArquivo = arquivoTextField.getText().trim();
        if (nomeArquivo.isEmpty()) {
            mensagensTextArea.setText("Erro: Nome do arquivo não pode ser vazio.");
            return;
        }

        try {
            carregarRobos(nomeArquivo + "-ROBOS.CSV");
            carregarClientes(nomeArquivo + "-CLIENTES.CSV");
            carregarLocacoes(nomeArquivo + "-LOCACOES.CSV");
            mostrarDadosCadastrados();
        } catch (IOException | ParseException e) {
            mensagensTextArea.append("Erro ao carregar dados iniciais: " + e.getMessage() + "\n");
        }
    }

    private void carregarRobos(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        reader.readLine();
        String linha;
        String tipo;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(";");
            int id = Integer.parseInt(partes[0]);
            String modelo = partes[1];
            double valorDiario;
            Robo robo;
            switch (partes[2].toLowerCase()) {
                case "1":
                    int nivel = Integer.parseInt(partes[3]);
                    tipo = "DOMÉSTICO";
                    valorDiario = switch (nivel) {
                        case 1 -> 10.0;
                        case 2 -> 20.0;
                        case 3 -> 50.0;
                        default -> throw new IllegalArgumentException("Nível desconhecido: " + nivel);
                    };
                    robo = new Domestico(id, modelo, valorDiario, nivel, tipo);
                    break;
                case "2":
                    String setor = partes[3];
                    valorDiario = 90.0;
                    tipo = "INDUSTRIAL";
                    robo = new Industrial(id, modelo, valorDiario, setor,tipo);
                    break;
                case "3":
                    double area = Double.parseDouble(partes[3]);
                    String uso = partes[4];
                    valorDiario = area * 10.0;
                    tipo = "AGRÍCOLA";
                    robo = new Agricola(id, modelo, valorDiario, area, uso, tipo);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de robô desconhecido: " + partes[3]);
            }
            sistema.cadastrarNovoRobo(robo);
        }
        reader.close();
    }

    private void carregarClientes(String nomeArquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        reader.readLine(); // Pular a primeira linha
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(";");
            int codigo = Integer.parseInt(partes[0]);
            String nome = partes[1];
            Cliente cliente;
            switch (partes[2]) {
                case "1": // Individual
                    String cpf = partes[3];
                    cliente = new Individual(codigo, nome, cpf);
                    break;
                case "2": // Empresarial
                    int ano = Integer.parseInt(partes[3]);
                    cliente = new Empresarial(codigo, nome, ano);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de cliente desconhecido: " + partes[2]);
            }
            sistema.cadastrarNovoCliente(cliente);
        }
        reader.close();
    }


    private void carregarLocacoes(String nomeArquivo) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        reader.readLine(); // Pular a primeira linha
        String linha;
        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split(";");
            int numero = Integer.parseInt(partes[0]);
            Status situacao = Status.valueOf(partes[1]);
            Date dataInicio = new SimpleDateFormat("dd/MM/yyyy").parse(partes[2]);
            Date dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(partes[3]);
            Cliente cliente = buscarClientePorCodigo(Integer.parseInt(partes[4]));

            List<Robo> robosLocacao = new ArrayList<>();
            for (int i = 5; i < partes.length; i++) {
                robosLocacao.add(buscarRoboPorId(Integer.parseInt(partes[i])));
            }

            Locacao locacao = new Locacao(numero, cliente, robosLocacao, dataInicio, dataFim);
            locacao.setSituacao(situacao);
            sistema.cadastrarNovaLocacao(locacao);
        }
        reader.close();
    }



    private Cliente buscarClientePorCodigo(int codigo) {
        for (Cliente cliente : sistema.getClientes()) {
            if (cliente.getCodigo() == codigo) {
                return cliente;
            }
        }
        return null;
    }

    private Robo buscarRoboPorId(int id) {
        for (Robo robo : sistema.getRobos()) {
            if (robo.getId() == id) {
                return robo;
            }
        }
        return null;
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

    public JPanel getPanelMain() {
        return panelMain;
    }
}
