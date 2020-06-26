import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class GUI {
    private JButton fileDirectory;
    private JButton saveDirectory;
    private JTextField splitPixelSize;
    private JButton startSplit;
    private JPanel mainPanel;
    private JTextArea UIText;

    private File selectedFileDirectory;
    private File selectedSaveDirectory;
    private int splitHeight = -1;
    private String acceptedFileTypes = ".png .bmp .jpg .jpeg .gif";

    public GUI() {
        // setup
        JFrame main = new JFrame("Image Splitter");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(600,200);
        main.add(mainPanel);
        main.setVisible(true);

        UIText.setEditable(false);

        fileDirectory.setText("Select image file or directory");
        saveDirectory.setText("Select save directory");
        splitPixelSize.setText("Set pixel height of each split image");
        startSplit.setText("START");
        UIText.setText("Enter all fields of input. Note: To enter the pixel split height, press enter in the text field.");

        fileDirectory.addActionListener(this::actionPerformed);
        saveDirectory.addActionListener(this::actionPerformed);
        splitPixelSize.addActionListener(this::actionPerformed);
        startSplit.addActionListener(this::actionPerformed);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == fileDirectory) {
            selectedFileDirectory = Main.chooseDirectory();
            if(selectedFileDirectory != null) {
                UIText.setText("Image file/directory selected");
            }
        } else if(e.getSource() == saveDirectory) {
            selectedSaveDirectory = Main.chooseDirectory();
            if(selectedSaveDirectory != null) {
                UIText.setText("Save file/directory selected");
            }
        } else if(e.getSource() == splitPixelSize) {
            try {
                int pixelInput = Integer.parseInt(splitPixelSize.getText());
                if(pixelInput > 0) {
                    splitHeight = pixelInput;
                    UIText.setText("DPI Set");
                } else {
                    splitHeight = -1;
                    UIText.setText("Enter a number greater than 0");
                }
            } catch (Exception exception) {
                UIText.setText("Input not recognised as an integer");
                splitHeight = -1;
            }
        } else if(e.getSource() == startSplit) {
            if(selectedFileDirectory != null && selectedSaveDirectory != null && splitHeight != -1) {
                if(selectedFileDirectory.isDirectory()) {
                    File[] imageFiles = Main.getDirectoryFiles(selectedFileDirectory);
                    for(int i = 0; i < imageFiles.length; i++) {
                        if(imageFiles[i] != null) {
                            String fileName = imageFiles[i].getName();
                            if(acceptedFileTypes.contains(fileName.substring(fileName.indexOf(".")))) {
                                Main.splitImage(imageFiles[i], splitHeight, selectedSaveDirectory);
                            }
                        }
                    }
                } else {
                    Main.splitImage(selectedFileDirectory, splitHeight, selectedSaveDirectory);
                }
                UIText.setText("Images split and saved at " + selectedSaveDirectory);
            } else {
                UIText.setText("Not all inputs have been set");
            }

        }
    }
}
