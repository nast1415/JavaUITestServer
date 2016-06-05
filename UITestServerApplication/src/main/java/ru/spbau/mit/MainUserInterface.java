package ru.spbau.mit;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public final class MainUserInterface {
    private static final JFrame FRAME = new JFrame("Servers");

    private static final Container CONTENT_PANE = FRAME.getContentPane();

    public static void main(String[] args) {
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
        selectParametersBox.add(architectureLabel);
        selectParametersBox.add(architectureChoice);

        selectParametersBox.add(Box.createHorizontalStrut(20));

        selectParametersBox.add(changeableParameterLabel);
        selectParametersBox.add(changeableParameterChoice);

        selectParametersBox.add(Box.createHorizontalStrut(20));

        Box configureSettingsBox = Box.createVerticalBox();
        configureSettingsBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        JLabel firstConstantParameterLabel = new JLabel("Number of clients:");
        JTextField firstConstantParameterText = new JTextField("1");
        firstConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(firstConstantParameterLabel);
        configureSettingsBox.add(firstConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel secondConstantParameterLabel = new JLabel("Delta: ");
        JTextField secondConstantParameterText = new JTextField("1");
        secondConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(secondConstantParameterLabel);
        configureSettingsBox.add(secondConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel thirdConstantParameterLabel = new JLabel("Number of elements: ");
        JTextField thirdConstantParameterText = new JTextField("1");
        thirdConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(thirdConstantParameterLabel);
        configureSettingsBox.add(thirdConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        Box mainHorizontalBox = Box.createHorizontalBox();
        mainHorizontalBox.add(configureSettingsBox);

        Box changeableParameterBox = Box.createVerticalBox();
        changeableParameterBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        JLabel changeableSettingLabelFrom = new JLabel("Changeable parameter, from: ");
        JTextField changeableSettingTextFrom = new JTextField();
        changeableSettingTextFrom.setPreferredSize(new Dimension(100, 25));

        JLabel changeableSettingLabelTo = new JLabel("to: ");
        JTextField changeableSettingTextTo = new JTextField();
        changeableSettingTextTo.setPreferredSize(new Dimension(100, 25));

        JLabel changeableSettingLabelStep = new JLabel("with step: ");
        JTextField changeableSettingTextStep = new JTextField();
        changeableSettingTextStep.setPreferredSize(new Dimension(100, 25));

        changeableParameterBox.add(changeableSettingLabelFrom);
        changeableParameterBox.add(changeableSettingTextFrom);

        changeableParameterBox.add(Box.createVerticalStrut(20));

        changeableParameterBox.add(changeableSettingLabelTo);
        changeableParameterBox.add(changeableSettingTextTo);

        changeableParameterBox.add(Box.createVerticalStrut(20));

        changeableParameterBox.add(changeableSettingLabelStep);
        changeableParameterBox.add(changeableSettingTextStep);

        changeableParameterBox.add(Box.createVerticalStrut(20));

        mainHorizontalBox.add(Box.createHorizontalStrut(100));
        mainHorizontalBox.add(changeableParameterBox);


        JButton countButton = new JButton("Start drawing");
        countButton.setPreferredSize(new Dimension(350, 25));

        JPanel countButtonPanel = new JPanel();
        countButtonPanel.add(countButton);

        CONTENT_PANE.add(selectParametersBox, BorderLayout.NORTH);
        CONTENT_PANE.add(mainHorizontalBox, BorderLayout.CENTER);
        CONTENT_PANE.add(countButtonPanel, BorderLayout.SOUTH);

        FRAME.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        FRAME.setSize(800, 300);
        FRAME.setResizable(false);
        FRAME.setVisible(true);

    }
}
