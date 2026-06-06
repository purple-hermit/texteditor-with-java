import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class TextEditor extends JFrame implements ActionListener{

    JTextArea textArea;
    JScrollPane scrollPane;

    TextEditor(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Notepad");
        this.setSize(640, 480);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setCaretColor(Color.white);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        

        
        this.add(scrollPane);
        this.setVisible(true);
    }

     @Override
    public void actionPerformed(ActionEvent e){
        //TODO Auto-generated method stub
    }
}