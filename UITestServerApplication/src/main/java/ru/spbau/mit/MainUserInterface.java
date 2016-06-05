package ru.spbau.mit;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import ru.spbau.mit.Clients.BaseClient;
import ru.spbau.mit.Clients.TCPClient;
import ru.spbau.mit.Clients.UDPClient;
import sun.security.pkcs11.wrapper.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class MainUserInterface {
    //Main frame and it's content_pane
    private static final JFrame FRAME = new JFrame("Servers");
    private static final Container CONTENT_PANE = FRAME.getContentPane();

    //We'll need this frames to show graphics
    private static JFrame frameClientSummary = new JFrame();
    private static JFrame frameClientProcessing = new JFrame();
    private static JFrame frameRequestHandling = new JFrame();

    //Function, that return an array of data, which needed to create a chart
    private static int[] getChartData(int arraySize, int numberOfClients, int delta, int numberOfRequests,
                                      String serverType) {
        try {
            Socket socket = new Socket("localhost", 8082);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            //Create a request to the main server
            outputStream.writeByte(0); //Byte of the requestType (0 for start, 1 for stop)
            outputStream.writeUTF(serverType); //Which type of server we want to start
            outputStream.writeInt(numberOfClients); //Number of the client (we need it for calculating data)

            Thread.sleep(100);
            outputStream.close();
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        //Create two arrays - for clients and for they threads
        List<BaseClient> clients = new ArrayList<BaseClient>();
        List<Thread> clientThreads = new ArrayList<>();

        //Simulation of the access of different clients to the server (create a thread for each client
        // with chosen type)
        for (int i = 0; i < numberOfClients; i++) {
            BaseClient client; // Create base client

            //We will create different client (two types for the task) for different requests
            //false mean - we needn't to create new connection for every client, true - need to
            switch (serverType) {
                case "TCP, thread for each client":
                    client = new TCPClient(arraySize, delta, numberOfRequests, false);
                    break;
                case "TCP, CachedThreadPool":
                    client = new TCPClient(arraySize, delta, numberOfRequests, false);
                    break;
                case "UDP, fixedThreadPool":
                    client = new UDPClient(arraySize, delta, numberOfRequests);
                    break;
                case "UDP, one request - one thread":
                    client = new UDPClient(arraySize, delta, numberOfRequests);
                    break;
                default:
                    client = new TCPClient(arraySize, delta, numberOfRequests, true);
                    break;
            }
            clients.add(client);
            Thread clientThread = new Thread(() -> {
                try {
                    client.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            clientThreads.add(clientThread);
            clientThread.start();
        }

        //Calculate summary client time
        long summaryClientTime = 0;
        for (int i = 0; i < numberOfClients; i++) {
            try {
                clientThreads.get(i).join();
                summaryClientTime += clients.get(i).getClientTime();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Create array for our data
        int[] data = new int[3];

        //Get client data for our chart
        data[2] = (int) (summaryClientTime / numberOfClients);
        try {
            Socket socket = new Socket("localhost", 8082);

            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeByte(1); //Send the main server stop request

            //Get data from the main server (about processing clients time and request handling time)
            data[0] = inputStream.readInt();
            data[1] = (int)inputStream.readLong();

            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }

    public static void createStatisticsFile(String architecture, String changingParameter, int firstConstantParameter,
                         int secondConstantParameter, int from, int to, int step, String metric, List<Integer> X,
                         List<Integer> Y, int numberOfRequests) {

        Path pathDirectoryResults = Paths.get(".", "dataResults");
        if (!Files.exists(pathDirectoryResults)) {
            try {
                Files.createDirectory(pathDirectoryResults);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path pathDirectoryFiles = Paths.get(pathDirectoryResults.toString(), "statisticFiles");
        if (!Files.exists(pathDirectoryFiles)) {
            try {
                Files.createDirectory(pathDirectoryFiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String fileName = "changing_" + changingParameter + "_metric_" + metric + ".csv";
        Path dataFilePath = Paths.get(pathDirectoryFiles.toString(), fileName);

        if (!Files.exists(dataFilePath)) {
            try {
                Files.createFile(dataFilePath);
                try (PrintWriter printer = new PrintWriter(new FileOutputStream(dataFilePath.toString(), true))) {
                    printer.printf("Number of requests = %d\n", numberOfRequests);
                    if (changingParameter.equals("number of elements")) {
                        printer.printf("Array size changes from %d to %d with step %d\n", from, to, step);
                        printer.printf("Delta = %d\n", secondConstantParameter);
                        printer.printf("Clients = %d\n", firstConstantParameter);
                    } else if (changingParameter.equals("delta")) {
                        printer.printf("Array size = %d\n", secondConstantParameter);
                        printer.printf("Delta changes from %d to %d with step %d\n", from, to, step);
                        printer.printf("Clients = %d\n", firstConstantParameter);
                    } else {
                        printer.printf("Array size = %d\n", firstConstantParameter);
                        printer.printf("Delta = %d\n", secondConstantParameter);
                        printer.printf("Count of clients changes from %d to %d with step %d\n", from, to, step);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        //Create first box with architecture and changeable parameter

        JLabel architectureLabel = new JLabel("Architecture: ");
        String[] architectures = {"TCP, new client - new tread", "TCP, one thread", "TCP, CachedThreadPool",
                "UDP, one request - one thread", "UDP, fixedThreadPool"};
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

        //Select second box with parameters settings (for two constant parameters)

        Box configureSettingsBox = Box.createVerticalBox();
        configureSettingsBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 10));

        JLabel clientsConstantParameterLabel = new JLabel("Number of clients:");
        JTextField clientsConstantParameterText = new JTextField("1");
        clientsConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(clientsConstantParameterLabel);
        configureSettingsBox.add(clientsConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel requestsConstantParameterLabel = new JLabel("Number of requests:");
        JTextField requestsConstantParameterText = new JTextField("1");
        requestsConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(requestsConstantParameterLabel);
        configureSettingsBox.add(requestsConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel deltaConstantParameterLabel = new JLabel("Delta: ");
        JTextField deltaConstantParameterText = new JTextField("1");
        deltaConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(deltaConstantParameterLabel);
        configureSettingsBox.add(deltaConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        JLabel sizeConstantParameterLabel = new JLabel("Number of elements: ");
        JTextField sizeConstantParameterText = new JTextField("1");
        sizeConstantParameterText.setPreferredSize(new Dimension(100, 25));

        configureSettingsBox.add(sizeConstantParameterLabel);
        configureSettingsBox.add(sizeConstantParameterText);
        configureSettingsBox.add(Box.createVerticalStrut(20));

        //Create horizontal box, including two vertical boxes with constant parameters
        // and changeable parameter (just for beauty :))

        Box mainHorizontalBox = Box.createHorizontalBox();
        mainHorizontalBox.add(configureSettingsBox);

        // This is the box for everything about changeable parameter,
        // which will be selected in the changeableParameterChoice comboBox in first box

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

        // Add second vertical box to the horizontal
        mainHorizontalBox.add(Box.createHorizontalStrut(100));
        mainHorizontalBox.add(changeableParameterBox);

        //Create startButton
        JButton startButton = new JButton("Start drawing");
        startButton.setPreferredSize(new Dimension(350, 25));

        List<String> metrics = new ArrayList<String>() {{
            add("clientProcessing");
            add("requestHandling");
            add("summaryClientTime");
        }};

        //Add action listener to the button
        startButton.addActionListener(e -> {
            String xString = (String)changeableParameterChoice.getSelectedItem();
            for (String metric: metrics) {
                String fileName = "change_" + xString + "_metric_" + metric + ".csv";
                Path pathFile = Paths.get(".", "results", "Data_files", fileName);
                try {
                    Files.deleteIfExists(pathFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            int numberOfRequests = Integer.parseInt(requestsConstantParameterText.getText());

            //Create three arrays for data for three metrics
            java.util.List<Integer> requestHandlingTimeData = new ArrayList<>();
            java.util.List<Integer> clientProcessingTimeData = new ArrayList<>();
            java.util.List<Integer> summaryClientTimeData = new ArrayList<>();
            java.util.List<Integer> changeableParameterData = new ArrayList<>();

            //requestHandlingTimeData.add(0);
            //clientProcessingTimeData.add(0);
            //summaryClientTimeData.add(0);

            //Get data for the changing parameter from textFields of our form
            int from = Integer.parseInt(changeableSettingTextFrom.getText());
            int to = Integer.parseInt(changeableSettingTextTo.getText());
            int step = Integer.parseInt(changeableSettingTextStep.getText());

            String serverType = (String) architectureChoice.getSelectedItem();
            int numberOfClients, arraySize, delta;

            //For every type of changing parameter we read two other constant parameters
            // and get the data for the chart for every value of the changing parameter between "from", "to"
            //with a step "step", using special function "getChartData"
            switch ((String) changeableParameterChoice.getSelectedItem()) {
                case "number of elements":
                    numberOfClients = Integer.parseInt(clientsConstantParameterText.getText());
                    delta = Integer.parseInt(deltaConstantParameterText.getText());

                    for (int i = from; i <= to; i += step) {
                        int[] data = getChartData(i, numberOfClients, delta, numberOfRequests, serverType);
                        changeableParameterData.add(i);
                        requestHandlingTimeData.add(data[0]);
                        clientProcessingTimeData.add(data[1]);
                        summaryClientTimeData.add(data[2]);
                    }
                    createStatisticsFile(serverType, "number of elements", numberOfClients, delta, from,
                            to, step, "clientProcessing", changeableParameterData,
                            clientProcessingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "number of elements", numberOfClients, delta, from,
                            to, step, "requestHandling", changeableParameterData,
                            requestHandlingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "number of elements", numberOfClients, delta, from,
                            to, step, "summaryClientTime", changeableParameterData,
                            summaryClientTimeData, numberOfRequests );

                    break;
                case "clients":
                    arraySize = Integer.parseInt(sizeConstantParameterText.getText());
                    delta = Integer.parseInt(deltaConstantParameterText.getText());

                    for (int i = from; i <= to; i += step) {
                        int[] data = getChartData(arraySize, i, delta, numberOfRequests, serverType);
                        changeableParameterData.add(i);
                        requestHandlingTimeData.add(data[0]);
                        clientProcessingTimeData.add(data[1]);
                        summaryClientTimeData.add(data[2]);
                    }

                    createStatisticsFile(serverType, "clients", arraySize, delta, from,
                            to, step, "clientProcessing", changeableParameterData,
                            clientProcessingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "clients", arraySize, delta, from,
                            to, step, "requestHandling", changeableParameterData,
                            requestHandlingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "clients", arraySize, delta, from,
                            to, step, "summaryClientTime", changeableParameterData,
                            summaryClientTimeData, numberOfRequests );
                    break;
                default:
                    numberOfClients = Integer.parseInt(clientsConstantParameterText.getText());
                    arraySize = Integer.parseInt(sizeConstantParameterText.getText());

                    for (int i = from; i <= to; i += step) {
                        int[] data = getChartData(arraySize, numberOfClients, i,
                                numberOfRequests, serverType);
                        changeableParameterData.add(i);
                        requestHandlingTimeData.add(data[0]);
                        clientProcessingTimeData.add(data[1]);
                        summaryClientTimeData.add(data[2]);
                    }

                    createStatisticsFile(serverType, "delta", numberOfClients, arraySize, from,
                            to, step, "clientProcessing", changeableParameterData,
                            clientProcessingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "delta", numberOfClients, arraySize, from,
                            to, step, "requestHandling", changeableParameterData,
                            requestHandlingTimeData, numberOfRequests );
                    createStatisticsFile(serverType, "delta", numberOfClients, arraySize, from,
                            to, step, "summaryClientTime", changeableParameterData,
                            summaryClientTimeData, numberOfRequests );
            }

            //After we collect data, we're going to create 3 frames for graphics (one for every metric)
            XYChart clientChart = QuickChart.getChart("ClientProcessingChart", "X", "Y", "y(x)",
                    changeableParameterData, clientProcessingTimeData);

            XChartPanel chartPanelClient = new XChartPanel(clientChart);
            frameClientProcessing.getContentPane().add(chartPanelClient);
            frameClientProcessing.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameClientProcessing.setSize(600, 400);


            XYChart clientSumChart = QuickChart.getChart("ClientSummaryChart", "X", "Y", "y(x)",
                    changeableParameterData, summaryClientTimeData);

            XChartPanel chartPanelSumClient = new XChartPanel(clientSumChart);
            frameClientSummary.getContentPane().add(chartPanelSumClient);
            frameClientSummary.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameClientSummary.setSize(600, 400);

            XYChart requestHandlingChart = QuickChart.getChart("RequestHandlingChart", "X", "Y", "y(x)",
                    changeableParameterData, summaryClientTimeData);

            XChartPanel chartPanelRequestHandling = new XChartPanel(requestHandlingChart);
            frameRequestHandling.getContentPane().add(chartPanelRequestHandling);
            frameRequestHandling.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            frameRequestHandling.setSize(600, 400);


            frameClientProcessing.setVisible(true);
            frameClientSummary.setVisible(true);
            frameRequestHandling.setVisible(true);

        });

        //Create panel for the startButton
        JPanel countButtonPanel = new JPanel();
        countButtonPanel.add(startButton);

        //Add elements to the content_pane (using BorderLayout)
        CONTENT_PANE.add(selectParametersBox, BorderLayout.NORTH);
        CONTENT_PANE.add(mainHorizontalBox, BorderLayout.CENTER);
        CONTENT_PANE.add(countButtonPanel, BorderLayout.SOUTH);

        //Setting for the frame
        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        FRAME.setSize(800, 350);
        FRAME.setResizable(false);

        //Finally, making frame visible
        FRAME.setVisible(true);

    }
}
