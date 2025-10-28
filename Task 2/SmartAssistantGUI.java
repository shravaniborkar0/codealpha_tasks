package codeAlpha;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmartAssistantGUI extends JFrame implements ActionListener {

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public SmartAssistantGUI() {
        // Window setup
        setTitle("Smart Assistant - CodeAlpha Task 3");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // added gaps for clarity
        getContentPane().setBackground(Color.WHITE);

        // Chat area setup
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom input panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setToolTipText("Type your message here...");

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Add listeners
        sendButton.addActionListener(this);
        inputField.addActionListener(this);

        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);

        // Show initial bot messages
        showBotMessage("Hello! I'm your Smart Assistant.");
        showBotMessage("How can I help you today?");

        // Make sure everything displays properly
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        showUserMessage(userInput);
        inputField.setText("");

        String response = getBotResponse(userInput.toLowerCase());
        if (!response.isEmpty()) {
            showBotMessage(response);
        }
    }

    private void showUserMessage(String message) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        chatArea.append("\nYou (" + time + "): " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void showBotMessage(String message) {
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        chatArea.append("Bot (" + time + "): " + message + "\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private String getBotResponse(String input) {
        if (input.contains("hello") || input.contains("hi")) {
            return "Hello there!";
        } else if (input.contains("how are you")) {
            return "I'm doing great! How about you?";
        } else if (input.contains("time")) {
            return "Current time is " + new SimpleDateFormat("HH:mm:ss").format(new Date());
        } else if (input.contains("date")) {
            return "Today's date is " + new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        } else if (input.contains("your name")) {
            return "I'm SmartAssistant, your virtual helper!";
        } else if (input.contains("bye") || input.contains("exit")) {
            showBotMessage("Goodbye! Have a nice day.");

            Timer timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            timer.setRepeats(false);
            timer.start();

            return "";
        } else {
            return "I'm not sure about that. Try asking something else!";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SmartAssistantGUI();
            }
        });
    }
}