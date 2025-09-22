import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PermutationBoxGUI {

    // Substitution rules
    private static final Map<Character, Character> SUBSTITUTION = new HashMap<>();
    static {
        SUBSTITUTION.put('2', 'M'); // replace 2 with M
        SUBSTITUTION.put('4', 'J'); // replace 4 with J
        SUBSTITUTION.put('5', 'P'); // replace 4 with PS
    }

    // Example permutation tables
    private static final int[] SPBOX = {4, 2, 0, 3, 1}; // Straight P-box
    private static final int[] EPBOX = {0, 1, 2, 3, 4, 2, 1}; // Expansion P-box
    private static final int[] CPBOX = {3, 1, 4}; // Compression P-box

    // ----------- MAIN MENU -----------
    private static void createMainMenu() {
        JFrame frame = new JFrame("PERMUTATIONS BOX");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel title = new JLabel("PERMUTATIONS BOX", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title);

        JButton spButton = new JButton("SPBOX");
        spButton.setBackground(Color.RED);
        spButton.setForeground(Color.WHITE);
        spButton.addActionListener(e -> openPermutationWindow("SP-BOX"));
        frame.add(spButton);

        JButton cpButton = new JButton("CPBOX");
        cpButton.setBackground(Color.GREEN);
        cpButton.setForeground(Color.WHITE);
        cpButton.addActionListener(e -> openPermutationWindow("CP-BOX"));
        frame.add(cpButton);

        JButton epButton = new JButton("EPBOX");
        epButton.setBackground(Color.BLUE);
        epButton.setForeground(Color.WHITE);
        epButton.addActionListener(e -> openPermutationWindow("EP-BOX"));
        frame.add(epButton);

        frame.setVisible(true);
    }

    // ----------- PERMUTATION WINDOW -----------
    private static void openPermutationWindow(String selected) {
        JFrame frame = new JFrame(selected + " Window");
        frame.setSize(450, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter Data:"));
        JTextField inputField = new JTextField(15);
        topPanel.add(inputField);

        JButton processBtn = new JButton("Process");
        topPanel.add(processBtn);

        frame.add(topPanel, BorderLayout.NORTH);

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        processBtn.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please enter data.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Substitution first
            String substituted = applySubstitution(input);

            // Apply all permutations
            String spOutput = safeApplyPBox(substituted, SPBOX);
            String cpOutput = safeApplyPBox(substituted, CPBOX);
            String epOutput = safeApplyPBox(substituted, EPBOX);

            outputArea.setText("");
            outputArea.append("Results\n\n");
            outputArea.append("SP-BOX: " + spOutput + "\n");
            outputArea.append("CP-BOX: " + cpOutput + "\n");
            outputArea.append("EP-BOX: " + epOutput + "\n");
        });

        frame.setVisible(true);
    }

    // ----------- SUBSTITUTION -----------
    private static String applySubstitution(String input) {
        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            result.append(SUBSTITUTION.getOrDefault(c, c));
        }
        return result.toString();
    }

    // ----------- SAFE PERMUTATION -----------
    private static String safeApplyPBox(String input, int[] pbox) {
        if (input.length() < Arrays.stream(pbox).max().orElse(0) + 1) {
            return "[Input too short for this P-box]";
        }
        char[] output = new char[pbox.length];
        for (int i = 0; i < pbox.length; i++) {
            output[i] = input.charAt(pbox[i]);
        }
        return new String(output);
    }

    // ----------- MAIN METHOD -----------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PermutationBoxGUI::createMainMenu);
    }
}