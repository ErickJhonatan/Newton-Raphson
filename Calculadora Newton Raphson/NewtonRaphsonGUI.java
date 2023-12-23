import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewtonRaphsonGUI extends JFrame {
    private JTextField initialGuessField;
    private JTextField maxIterationsField;
    private JTextArea resultArea;
    private JTable table;
    private DefaultTableModel tableModel;

    public NewtonRaphsonGUI() {
        super("Calculadora de Newton-Raphson ");

        initialGuessField = new JTextField(10);
        maxIterationsField = new JTextField(10);
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Iteração");
        tableModel.addColumn("Aproximação (x)");
        tableModel.addColumn("f(x)");
        tableModel.addColumn("f'(x)");

        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        JButton calculateButton = new JButton("Calcular");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateNewtonRaphson();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Aproximação Inicial (x0):"));
        panel.add(initialGuessField);
        panel.add(new JLabel("Número Máximo de Iterações:"));
        panel.add(maxIterationsField);
        panel.add(calculateButton);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(panel, "North");
        add(tableScrollPane, "Center");
        add(scrollPane, "South");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void calculateNewtonRaphson() {
        tableModel.setRowCount(0); // Limpar a tabela antes de cada cálculo

        try {
            double x0 = Double.parseDouble(initialGuessField.getText());
            int maxIterations = Integer.parseInt(maxIterationsField.getText());

            double x = x0;
            for (int i = 0; i < maxIterations; i++) {
                double fx = f(x);
                double fPrimeX = fPrime(x);

                tableModel.addRow(new Object[]{i + 1, x, fx, fPrimeX});

                if (Math.abs(fx) < 1e-8) {
                    resultArea.setText("Convergiu após " + (i + 1) + " iterações.\nA raiz aproximada é: " + x);
                    return;
                }

                if (fPrimeX == 0) {
                    resultArea.setText("A derivada é zero. O método de Newton-Raphson não pode continuar.");
                    return;
                }

                x = x - fx / fPrimeX;
            }

            resultArea.setText("Não convergiu após " + maxIterations + " iterações.\nA última aproximação é: " + x);
        } catch (NumberFormatException ex) {
            resultArea.setText("Por favor, insira valores válidos para x0 e o número de iterações.");
        }
    }

    private double f(double x) {
        // Função f(x) = x^2 - 5
        return x * x - 5;
    }

    private double fPrime(double x) {
        // Derivada de f(x) = 2x
        return 2 * x;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewtonRaphsonGUI();
            }
        });
    }
}
