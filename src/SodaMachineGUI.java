import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SodaMachineGUI extends JFrame {
    private int quarterCount;
    private final ArrayList<Soda> sodas;
    private final JLabel quarterLabel;
    private final ArrayList<JLabel> sodaLabels;
    private final JLabel messageLabel;
    private final JTextArea logTextArea;

    public SodaMachineGUI() {
        quarterCount = 0;
        sodas = new ArrayList<>();
        sodas.add(new Soda("Cola", 10));
        sodas.add(new Soda("Lemon-Lime", 10));
        sodas.add(new Soda("Root Beer", 10));
        sodas.add(new Soda("Orange Soda", 10));

        setTitle("Soda Machine");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        quarterLabel = new JLabel("Quarters: 0");
        sodaLabels = new ArrayList<>();
        for (Soda soda : sodas) {
            sodaLabels.add(new JLabel(soda.getName() + ": " + soda.getCount()));
        }

        JButton insertQuarterButton = new JButton("Insert Quarter");
        insertQuarterButton.addActionListener(new InsertQuarterListener());

        JButton removeQuarterButton = new JButton("Remove Quarter");
        removeQuarterButton.addActionListener(new RemoveQuarterListener());

        ArrayList<JButton> sodaButtons = new ArrayList<>();
        for (Soda soda : sodas) {
            sodaButtons.add(new JButton(soda.getName()));
            sodaButtons.get(sodaButtons.size() - 1).addActionListener(new SodaButtonListener(soda));
        }

        messageLabel = new JLabel("Welcome to the soda machine!");

        logTextArea = new JTextArea();
        logTextArea.setEditable(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(sodas.size(), 1));
        for (JButton button : sodaButtons) {
            buttonPanel.add(button);
        }

        JPanel quarterPanel = new JPanel();
        quarterPanel.setLayout(new GridLayout(2, 1));
        quarterPanel.add(insertQuarterButton);
        quarterPanel.add(removeQuarterButton);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(sodas.size() + 1, 1));
        infoPanel.add(quarterLabel);
        for (JLabel label : sodaLabels) {
            infoPanel.add(label);
        }

        JPanel messagePanel = new JPanel();
        messagePanel.add(messageLabel);

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(1, 1));
        logPanel.add(logTextArea);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(quarterPanel, BorderLayout.EAST);
        mainPanel.add(infoPanel, BorderLayout.WEST);
        mainPanel.add(messagePanel, BorderLayout.NORTH);
        mainPanel.add(logPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private class Soda {
        private String name;
        private int count;

        public Soda(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    private class InsertQuarterListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            quarterCount++;
            quarterLabel.setText("Quarters: " + quarterCount);
        }
    }

    private class RemoveQuarterListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (quarterCount > 0) {
                quarterCount--;
                quarterLabel.setText("Quarters: " + quarterCount);
            } else {
                messageLabel.setText("No quarters to remove.");
            }
        }
    }

    private class SodaButtonListener implements ActionListener {
        private final Soda soda;

        public SodaButtonListener(Soda soda) {
            this.soda = soda;
        }

        public void actionPerformed(ActionEvent event) {
            if (quarterCount >= 1 && soda.getCount() >= 1) {
                quarterCount--;
                soda.setCount(soda.getCount() - 1);
                quarterLabel.setText("Quarters: " + quarterCount);
                for (int i = 0; i < sodas.size(); i++) {
                    sodaLabels.get(i).setText(sodas.get(i).getName() + ": " + sodas.get(i).getCount());
                }
                logTextArea.append("Purchased a " + soda.getName() + " for one quarter at " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n");
            } else if (quarterCount < 1) {
                messageLabel.setText("Please insert a quarter.");
            } else if (soda.getCount() < 1) {
                messageLabel.setText("Out of " + soda.getName() + ".");
            }
        }
    }

    public static void main(String[] args) {
        SodaMachineGUI sodaMachine = new SodaMachineGUI();
        sodaMachine.setVisible(true);
    }
}

