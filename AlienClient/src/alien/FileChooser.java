package alien;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class FileChooser extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel l;

	public FileChooser() {

		setTitle("WARZONE 3 | Send Report");
		setSize(400, 400); 
		setVisible(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        JButton button1 = new JButton("send");
        JButton button2 = new JButton("open"); 
        button1.addActionListener(this); 
        button2.addActionListener(this); 
        JPanel p = new JPanel(); 
        p.add(button1); 
        p.add(button2); 
        l = new JLabel("no file selected"); 
        p.add(l); 
        add(p); 
        setIconImage(new ImageIcon("aliens.png").getImage());
        show(); 
    } 
    public void actionPerformed(ActionEvent evt) 
    { 
        // if the user presses the save button show the save dialog 
        String com = evt.getActionCommand(); 
  
        if (com.equals("send")) { 
            // create an object of JFileChooser class 
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
  
            // invoke the showsSaveDialog function to show the save dialog 
            int r = j.showSaveDialog(null); 
  
            // if the user selects a file 
            if (r == JFileChooser.APPROVE_OPTION) 
  
            { 
                // set the label to the path of the selected file 
                l.setText(j.getSelectedFile().getAbsolutePath()); 
            } 
            // if the user cancelled the operation 
            else
                l.setText("the user cancelled the operation"); 
        } 
  
        // if the user presses the open dialog show the open dialog 
        else { 
            // create an object of JFileChooser class 
            JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
  
            // invoke the showsOpenDialog function to show the save dialog 
            int r = j.showOpenDialog(null); 
  
            // if the user selects a file 
            if (r == JFileChooser.APPROVE_OPTION) 
  
            { 
                // set the label to the path of the selected file 
                l.setText(j.getSelectedFile().getAbsolutePath()); 
            } 
            // if the user cancelled the operation 
            else
                l.setText("the user cancelled the operation"); 
        } 
    }
	public static JLabel getL() {
		return l;
	}
	public static void setL(JLabel l) {
		FileChooser.l = l;
	}
    
    
} 


