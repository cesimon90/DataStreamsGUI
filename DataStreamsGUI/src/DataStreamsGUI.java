import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class DataStreamsGUI extends JFrame {
    private JTextArea originalTextArea, filteredTextArea;
    private JTextField searchTextField;

    public DataStreamsGUI() {
        // GUI setup
        setTitle("DataStreams");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        originalTextArea = new JTextArea();
        filteredTextArea = new JTextArea();
        searchTextField = new JTextField();

        JScrollPane originalScrollPane = new JScrollPane(originalTextArea);
        JScrollPane filteredScrollPane = new JScrollPane(filteredTextArea);

        JButton loadButton = new JButton("Load File");
        JButton searchButton = new JButton("Search");
        JButton quitButton = new JButton("Quit");

        // Load button action
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadFile();
            }
        });

        // Search button action
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFile();
            }
        });

        // Quit button action
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Add components to the GUI
        JPanel topPanel = new JPanel();
        topPanel.add(loadButton);

        // Set size for search text field
        searchTextField.setPreferredSize(new Dimension(200, 25));
        topPanel.add(searchTextField);

        topPanel.add(searchButton);
        topPanel.add(quitButton);

        add(topPanel, BorderLayout.NORTH);
        add(originalScrollPane, BorderLayout.WEST);
        add(filteredScrollPane, BorderLayout.EAST);

        // Set minimum width for the window
        setMinimumSize(new Dimension(600, 400));

        // Set text box sizes
        originalScrollPane.setPreferredSize(new Dimension(getWidth() / 2, getHeight()));
        filteredScrollPane.setPreferredSize(new Dimension(getWidth() / 2, getHeight()));

        pack();
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    private void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                Path filePath = fileChooser.getSelectedFile().toPath();
                List<String> lines = Files.readAllLines(filePath);
                lines.forEach(line -> originalTextArea.append(line + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchFile() {
        String searchString = searchTextField.getText().toLowerCase();

        if (!searchString.isEmpty()) {
            List<String> filteredLines = originalTextArea
                    .getText()
                    .lines()
                    .filter(line -> line.toLowerCase().contains(searchString))
                    .collect(Collectors.toList());

            filteredTextArea.setText("");
            filteredLines.forEach(line -> filteredTextArea.append(line + "\n"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DataStreamsGUI());
    }
}
