import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {
    FileManager fileManager = new FileManager();

    JTextArea textArea;
    JTabbedPane tabbedPane;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JComboBox<String> fontBox;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem darkModeItem;
    JMenuItem exitItem;

    TextEditor() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Notepad");
        this.setSize(800, 600);

        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        fontLabel = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e){
                JTextArea currentTextArea = getCurrentTextArea();
                if(currentTextArea != null){
                    currentTextArea.setFont(new Font(currentTextArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
                }
            }
        });

        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontBox = new JComboBox<>(fonts);
        fontBox.setSelectedItem("Monospaced");
        fontBox.addActionListener(this);

        // Control Panel
        controlPanel.add(fontLabel);
        controlPanel.add(fontSizeSpinner);
        controlPanel.add(fontColorButton);
        controlPanel.add(fontBox);

        tabbedPane = new JTabbedPane();

        addNewTab("Untitled", "");

        // Menu-bar
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        darkModeItem = new JMenuItem("Toggle Dark Mode");
        exitItem = new JMenuItem("Exit");
        
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        darkModeItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(darkModeItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);


        this.add(controlPanel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);

        this.setVisible(true);
    }

private void addNewTab(String title, String content){
    JTextArea newTextArea = new JTextArea(content);
    newTextArea.setCaretColor(Color.BLACK);
    newTextArea.setFont(new Font("Monospaced", Font.PLAIN, (int) fontSizeSpinner.getValue()));
    newTextArea.setLineWrap(true);
    newTextArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(newTextArea);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    tabbedPane.addTab(title, scrollPane);
    tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
}


private JTextArea getCurrentTextArea(){
    int selectedIndex = tabbedPane.getSelectedIndex();
    if (selectedIndex != -1){
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(selectedIndex);
        return (JTextArea) scrollPane.getViewport().getView();
    }
    return null;
}

@Override
public void actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File("."));
    
    // Grab the text area for the active tab
    JTextArea activeTextArea = getCurrentTextArea();

    if (e.getSource() == fontColorButton && activeTextArea != null) {
        Color color = JColorChooser.showDialog(this, "Choose a color", Color.BLACK);
        activeTextArea.setForeground(color);
    }

    if (e.getSource() == fontBox && activeTextArea != null) {
        activeTextArea.setFont(new Font((String) fontBox.getSelectedItem(), Font.PLAIN, activeTextArea.getFont().getSize()));
    }

    if (e.getSource() == openItem) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(filter);

        int response = fileChooser.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String fileContent = fileManager.openFile(file);
                
                // INSTEAD of overwriting: Open a brand new tab displaying this file!
                addNewTab(file.getName(), fileContent);
                
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "Error: Could not open the file!", "File Error", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }

    if (e.getSource() == saveItem && activeTextArea != null) {
        int response = fileChooser.showSaveDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                fileManager.saveFile(file, activeTextArea.getText());
                
                // Dynamically update the tab heading to the newly saved name
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "Error: Could not save the file!", "File Error", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
    }

    if (e.getSource() == darkModeItem) {
    Color darkBackground = new Color(43, 43, 43);
    Color lightText = new Color(169, 183, 198);
    
    // Check the active tab to see if we are currently in dark mode
    JTextArea activeArea = getCurrentTextArea();
    boolean isCurrentlyDark = activeArea != null && activeArea.getBackground().equals(darkBackground);

    // Loop through every single open tab and update its colors
    for (int i = 0; i < tabbedPane.getTabCount(); i++) {
        JScrollPane scrollPane = (JScrollPane) tabbedPane.getComponentAt(i);
        JTextArea area = (JTextArea) scrollPane.getViewport().getView();
        
        if (isCurrentlyDark) {
            // Switch back to Light Mode
            area.setBackground(Color.WHITE);
            area.setForeground(Color.BLACK);
            area.setCaretColor(Color.BLACK);
        } else {
            // Switch to Dark Mode
            area.setBackground(darkBackground);
            area.setForeground(lightText);
            area.setCaretColor(Color.WHITE);
        }
    }
}
    if (e.getSource() == exitItem) {
        System.exit(0);
    }
}
}