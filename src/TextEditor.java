import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TextEditor extends JFrame implements ActionListener{

    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;

    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Notepad");
        this.setSize(640, 480);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setCaretColor(Color.black);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        fontLabel = new JLabel("Font: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50,25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN, (int)fontSizeSpinner.getValue()));
            }

        });
        
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);

        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(scrollPane);
        this.setVisible(true);
    }

     @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == fontColorButton){

            Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);

            textArea.setForeground(color);
        }
    }
}