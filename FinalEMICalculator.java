import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class FinalEMICalculator extends JFrame {

    private JTextField loanField, rateField, tenureField;
    private JLabel emiLabel, interestLabel, totalLabel;
    private VisualizationPanel visualizationPanel;

    public FinalEMICalculator() {
        setTitle("EMI Calculator - Final Project");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createInputPanel(), BorderLayout.WEST);
        add(createOutputPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(280, getHeight()));
        panel.setBorder(BorderFactory.createTitledBorder("Loan Details"));
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 10, 12, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loanLabel = new JLabel("Loan Amount (₹):");
        JLabel rateLabel = new JLabel("Interest Rate (%):");
        JLabel tenureLabel = new JLabel("Tenure (Years):");

        loanField = new JTextField();
        rateField = new JTextField();
        tenureField = new JTextField();

        JButton calculateBtn = new JButton("Calculate EMI");
        JButton clearBtn = new JButton("Clear");

        calculateBtn.setBackground(new Color(46, 139, 87));
        calculateBtn.setForeground(Color.WHITE);
        clearBtn.setBackground(new Color(220, 20, 60));
        clearBtn.setForeground(Color.WHITE);

        calculateBtn.addActionListener(e -> calculateEMI());
        clearBtn.addActionListener(e -> clearFields());

        gbc.gridx = 0; gbc.gridy = 0; panel.add(loanLabel, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(loanField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(rateLabel, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(rateField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(tenureLabel, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(tenureField, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(calculateBtn, gbc);
        gbc.gridx = 0; gbc.gridy++; panel.add(clearBtn, gbc);

        return panel;
    }

    private JPanel createOutputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("EMI Result"));

        JPanel resultTextPanel = new JPanel(new GridLayout(3, 1));
        resultTextPanel.setPreferredSize(new Dimension(400, 100));
        resultTextPanel.setBackground(new Color(250, 250, 250));

        emiLabel = new JLabel("Monthly EMI: ₹ --", SwingConstants.CENTER);
        interestLabel = new JLabel("Total Interest: ₹ --", SwingConstants.CENTER);
        totalLabel = new JLabel("Total Payment: ₹ --", SwingConstants.CENTER);

        emiLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        interestLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        totalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        resultTextPanel.add(emiLabel);
        resultTextPanel.add(interestLabel);
        resultTextPanel.add(totalLabel);

        visualizationPanel = new VisualizationPanel();

        panel.add(resultTextPanel, BorderLayout.NORTH);
        panel.add(visualizationPanel, BorderLayout.CENTER);

        return panel;
    }

    private void calculateEMI() {
        try {
            double principal = Double.parseDouble(loanField.getText());
            double annualRate = Double.parseDouble(rateField.getText());
            int years = Integer.parseInt(tenureField.getText());

            int months = years * 12;
            double monthlyRate = annualRate / (12 * 100);

            double emi = (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                         (Math.pow(1 + monthlyRate, months) - 1);

            double totalPayment = emi * months;
            double totalInterest = totalPayment - principal;

            DecimalFormat df = new DecimalFormat("#,##0.00");

            emiLabel.setText("Monthly EMI: ₹ " + df.format(emi));
            interestLabel.setText("Total Interest: ₹ " + df.format(totalInterest));
            totalLabel.setText("Total Payment: ₹ " + df.format(totalPayment));

            visualizationPanel.updateValues(principal, totalInterest);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        loanField.setText("");
        rateField.setText("");
        tenureField.setText("");
        emiLabel.setText("Monthly EMI: ₹ --");
        interestLabel.setText("Total Interest: ₹ --");
        totalLabel.setText("Total Payment: ₹ --");
        visualizationPanel.updateValues(0, 0);
    }

    // 👇 Custom JPanel to draw bar chart
    private class VisualizationPanel extends JPanel {
        private double principal = 0, interest = 0;

        public VisualizationPanel() {
            setPreferredSize(new Dimension(400, 250));
            setBackground(Color.WHITE);
        }

        public void updateValues(double principal, double interest) {
            this.principal = principal;
            this.interest = interest;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (principal == 0 && interest == 0) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            double total = principal + interest;
            int width = getWidth() - 100;

            int pWidth = (int) (width * (principal / total));
            int iWidth = (int) (width * (interest / total));

            g2.setColor(new Color(100, 149, 237));
            g2.fillRect(50, 80, pWidth, 30);
            g2.setColor(Color.BLACK);
            g2.drawString("Principal (₹" + (int) principal + ")", 50, 75);

            g2.setColor(new Color(255, 99, 71));
            g2.fillRect(50 + pWidth, 80, iWidth, 30);
            g2.setColor(Color.BLACK);
            g2.drawString("Interest (₹" + (int) interest + ")", 50 + pWidth, 75);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinalEMICalculator::new);
    }
}
