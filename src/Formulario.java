//Vinnicius Oliveira Rodrigues e Luiz Carlos Mendes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Formulario extends JFrame {
    private final JTextField campoEntradaMaxima;
    private final JComboBox<Integer> comboPrioridade1;
    private final JComboBox<Integer> comboPrioridade2;
    private final DefaultListModel<String> modeloLista1;
    private final DefaultListModel<String> modeloLista2;
    private final JLabel rotuloFim1;
    private final JLabel rotuloFim2;

    public Formulario() {
        setTitle("Trabalho prático 05");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JLabel rotuloMaximo = new JLabel("Máximo:");
        JLabel rotuloPrioridade1 = new JLabel("Prioridade 1:");
        JLabel rotuloPrioridade2 = new JLabel("Prioridade 2:");

        rotuloFim1 = new JLabel("Final");
        rotuloFim1.setHorizontalAlignment(SwingConstants.CENTER);
        rotuloFim2 = new JLabel("Final");
        rotuloFim2.setHorizontalAlignment(SwingConstants.CENTER);

        rotuloFim1.setVisible(false);
        rotuloFim2.setVisible(false);

        campoEntradaMaxima = new JTextField(10);
        comboPrioridade1 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        comboPrioridade2 = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});

        modeloLista1 = new DefaultListModel<>();
        modeloLista2 = new DefaultListModel<>();

        JList<String> lista1 = new JList<>(modeloLista1);
        JList<String> lista2 = new JList<>(modeloLista2);

        JButton botaoIniciar = new JButton("Iniciar");
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarThreads();
            }
        });

        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limparListas();
            }
        });

        setLayout(new BorderLayout());

        JPanel painelFormThread = new JPanel(new GridLayout(3, 2, 5, 5));
        painelFormThread.add(rotuloMaximo);
        painelFormThread.add(campoEntradaMaxima);
        painelFormThread.add(rotuloPrioridade1);
        painelFormThread.add(comboPrioridade1);
        painelFormThread.add(rotuloPrioridade2);
        painelFormThread.add(comboPrioridade2);

        JPanel painelBotaoThread = new JPanel();
        painelBotaoThread.add(botaoIniciar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelFormThread, BorderLayout.NORTH);
        painelSuperior.add(painelBotaoThread, BorderLayout.SOUTH);

        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelFim1 = new JPanel(new BorderLayout());
        painelFim1.add(new JScrollPane(lista1), BorderLayout.NORTH);
        painelFim1.add(rotuloFim1, BorderLayout.CENTER);

        JPanel painelFim2 = new JPanel(new BorderLayout());
        painelFim2.add(new JScrollPane(lista2), BorderLayout.NORTH);
        painelFim2.add(rotuloFim2, BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCentral.add(painelFim1);
        painelCentral.add(painelFim2);
        add(painelCentral, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel();
        painelInferior.add(botaoLimpar);
        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void iniciarThreads() {
        int maximo = Integer.parseInt(campoEntradaMaxima.getText());
        int prioridade01 = (Integer) comboPrioridade1.getSelectedItem();
        int prioridade02 = (Integer) comboPrioridade2.getSelectedItem();

        Thread thread01 = new Thread(new RunnableTask(modeloLista1, maximo, rotuloFim1));
        thread01.setPriority(prioridade01);

        Thread thread02 = new Thread(new RunnableTask(modeloLista2, maximo, rotuloFim2));
        thread02.setPriority(prioridade02);

        thread01.start();
        thread02.start();
    }

    private void limparListas() {
        modeloLista1.clear();
        modeloLista2.clear();

        rotuloFim1.setVisible(false);
        rotuloFim2.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Formulario();
        });
    }

    private static class RunnableTask implements Runnable {
        private final DefaultListModel<String> modeloLista;
        private final int maximo;
        private final JLabel rotulo;

        public RunnableTask(DefaultListModel<String> modeloLista, int maximo, JLabel rotulo) {
            this.modeloLista = modeloLista;
            this.maximo = maximo;
            this.rotulo = rotulo;
        }

        @Override
        public void run() {
            for (int i = 0; i < maximo; i++) {
                modeloLista.addElement(String.valueOf(i));
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            rotulo.setVisible(true);
        }
    }
}
