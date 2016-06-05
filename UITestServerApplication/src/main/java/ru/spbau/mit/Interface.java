package ru.spbau.mit;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class Interface {
    private static final JFrame FRAME = new JFrame("Servers");

    private static final Container CONTENT_PANE = FRAME.getContentPane();

    public static void main(String[] args) {
        JLabel ipLabel = new JLabel("Server ip: ");
        JTextField ipTextField = new JTextField("192.168.1.2");
        ipTextField.setPreferredSize(new Dimension(80, 25));

        JLabel architectureLabel = new JLabel("Architecture: ");
        String[] architectures = {"TCP, new client - new tread", "TCP, one thread", "TCP, CachedThreadPool",
                "UDP, new query - new thread", "UDP, FixedThreadPool"};
        JComboBox architectureChoice = new JComboBox(architectures);

        JLabel changeableParameterLabel = new JLabel("Changeable parameter: ");
        String[] changeableParameter = {"clients", "delta", "number of elements"};
        JComboBox changeableParameterChoice = new JComboBox(changeableParameter);

        Box selectParametersBox = Box.createHorizontalBox();
        selectParametersBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));
        selectParametersBox.add(Box.createHorizontalStrut(20));
        selectParametersBox.add(ipLabel);
        selectParametersBox.add(ipTextField);

        selectParametersBox.add(Box.createHorizontalStrut(20));

        selectParametersBox.add(architectureLabel);
        selectParametersBox.add(architectureChoice);

        selectParametersBox.add(Box.createHorizontalStrut(20));

        selectParametersBox.add(changeableParameterLabel);
        selectParametersBox.add(changeableParameterChoice);

        selectParametersBox.add(Box.createHorizontalStrut(20));

        Box configureSettingsBox = Box.createVerticalBox();
        configureSettingsBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        JLabel settingsLabel = new JLabel("Settings:");
        settingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        configureSettingsBox.add(settingsLabel);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel firstConstantParameterLabel = new JLabel("First constant parameter:");
        JTextField firstConstantParameterText = new JTextField("1");
        firstConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(firstConstantParameterLabel);
        configureSettingsBox.add(firstConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel secondConstantParameterLabel = new JLabel("Second constant parameter: ");
        JTextField secondConstantParameterText = new JTextField("1");
        secondConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(secondConstantParameterLabel);
        configureSettingsBox.add(secondConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel changeableSettingLabelFrom = new JLabel("Changeable parameter, from: ");
        JTextField changeableSettingTextFrom = new JTextField();
        changeableSettingTextFrom.setPreferredSize(new Dimension(100, 25));

        JLabel changeableSettingLabelTo = new JLabel("to: ");
        JTextField changeableSettingTextTo = new JTextField();
        changeableSettingTextTo.setPreferredSize(new Dimension(100, 25));

        JLabel changeableSettingLabelStep = new JLabel("with step: ");
        JTextField changeableSettingTextStep = new JTextField();
        changeableSettingTextStep.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(changeableSettingLabelFrom);
        configureSettingsBox.add(changeableSettingTextFrom);

        configureSettingsBox.add(changeableSettingLabelTo);
        configureSettingsBox.add(changeableSettingTextTo);

        configureSettingsBox.add(changeableSettingLabelStep);
        configureSettingsBox.add(changeableSettingTextStep);

        configureSettingsBox.add(Box.createVerticalStrut(10));

        JButton countButton = new JButton("count");
        countButton.setPreferredSize(new Dimension(350, 25));

        JPanel countButtonPanel = new JPanel();
        countButtonPanel.add(countButton);

        CONTENT_PANE.add(selectParametersBox, BorderLayout.NORTH);
        CONTENT_PANE.add(configureSettingsBox, BorderLayout.WEST);
        CONTENT_PANE.add(countButtonPanel, BorderLayout.SOUTH);

        FRAME.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        FRAME.setSize(800, 400);
        FRAME.setResizable(false);
        FRAME.setVisible(true);

    }
}
