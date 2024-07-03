package aplicacao;
import dados.*;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

public class ACMERobots {
    private List<Robo> robos;
    private List<Cliente> clientes;
    private List<Locacao> locacoes;
    private Queue<Locacao> locacoesPendentes;
    private ScheduledExecutorService scheduler;

    public ACMERobots() {
        this.robos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.locacoes = new ArrayList<>();
        this.locacoesPendentes = new LinkedList<>();
        this.scheduler = Executors.newScheduledThreadPool(1);

        // Adicionar alguns robôs e clientes para teste
        robos.add(new Domestico(1, "tggghhh", 10.0, 1, "DOMÉSTICO"));
        robos.add(new Industrial(2, "frethethh", 90.0, "Setor A", "INDUSTRIAL"));
        robos.add(new Agricola(3, "etgethththt", 20.0, 100.0, "Plantação", "AGRÍCOLA"));

        clientes.add(new Individual(1, "Cliente Individual", "123.456.789-00"));
        clientes.add(new Empresarial(2, "Cliente Empresarial", 2005));

        // Iniciar agendamento automático
        scheduler.scheduleAtFixedRate(this::verificarLocacoes, 0, 1, TimeUnit.SECONDS);
    }

    public void cadastrarNovoRobo(Robo novoRobo) {

        robos.add(novoRobo);
        robos.sort(Comparator.comparingInt(Robo::getId));
    }

    public void cadastrarNovoCliente(Cliente novoCliente) {
        clientes.add(novoCliente);
        clientes.sort(Comparator.comparingInt(Cliente::getCodigo));
    }

    public void cadastrarNovaLocacao(Locacao novaLocacao) {
        locacoes.add(novaLocacao);
        locacoesPendentes.add(novaLocacao);
    }

    private void verificarLocacoes() {
        Iterator<Locacao> iterator = locacoesPendentes.iterator();
        while (iterator.hasNext()) {
            Locacao locacao = iterator.next();
            if (locacao.getSituacao() == Status.CADASTRADA) {
                boolean todosDisponiveis = true;
                for (Robo robo : locacao.getRobos()) {
                    if (robo.getSituacao() != Status.DISPONIVEL) {
                        todosDisponiveis = false;
                        break;
                    }
                }
                if (todosDisponiveis) {
                    for (Robo robo : locacao.getRobos()) {
                        robo.setSituacao(Status.OCUPADO);
                    }
                    locacao.setSituacao(Status.EXECUTANDO);
                    System.out.println("Locação " + locacao.getNumero() + " está agora EXECUTANDO."); //comentar depois
                }
            } else if (locacao.getSituacao() == Status.EXECUTANDO) {
                if (new Date().after(locacao.getDataFim())) {
                    locacao.setSituacao(Status.FINALIZADA);
                    for (Robo robo : locacao.getRobos()) {
                        robo.setSituacao(Status.DISPONIVEL);
                    }
                    System.out.println("Locação " + locacao.getNumero() + " foi FINALIZADA."); //comentar depois
                    iterator.remove(); // Remover a locação da fila de pendentes
                }
            }
        }
    }


    public Locacao buscarLocacaoPorNumero(int numero) {
        for (Locacao locacao : locacoes) {
            if (locacao.getNumero() == numero) {
                return locacao;
            }
        }
        return null;
    }

    public List<Robo> getRobos() {
        return robos;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public List<Locacao> getLocacoes() {
        return locacoes;
    }

    public Queue<Locacao> getLocacoesPendentes() {
        return locacoesPendentes;
    }

    public static void main(String[] args) {
        ACMERobots sistema = new ACMERobots();

        JFrame frame = new JFrame("ACMERobots - Menu Principal");
        frame.setContentPane(new MenuPrincipal(frame, sistema).getPanelMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setVisible(true);
    }
}
