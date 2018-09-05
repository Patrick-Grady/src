import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel chessboard;

    
    public Board() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(10 * SQUARE_SIZE, 10 * SQUARE_SIZE);
        mainFrame.setLayout(new GridBagLayout());

        headerLabel = new JLabel();
        statusLabel = new JLabel();        
        statusLabel.setSize(0,0);

        mainFrame.addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent windowEvent){
              System.exit(0);
           }        
        });    
        
        makeBoard();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        mainFrame.add(headerLabel, gbc);
        gbc.gridy = 1;
        mainFrame.add(chessboard, gbc);
        gbc.gridy = 2;
        mainFrame.add(statusLabel, gbc);
        mainFrame.pack();
        mainFrame.setVisible(true);  
    }
    
    private final int SQUARE_SIZE = 50;
    
    private void makeBoard() {
        chessboard = new JPanel();
        chessboard.setLayout(new GridLayout(10,10));
        
        for(int i = 0; i < 10; i++) {
            chessboard.add(new JLabel());
        }
        
        for(int i = 0; i < 9; i++) {
            if(i < 8) {
                JLabel label = new JLabel("" + (i+1) + " ");
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                chessboard.add(label);
                for(int j = 0; j < 8; j++) {
                    
                    
                    
                    
                    JButton square = new JButton("\u2654");                    
                    //square.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
                    square.setBackground((i+j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY);
                    // Set the label's font size to the newly determined size.
                    square.setFont(new Font("Serif", Font.PLAIN, 30));
                    //square.setMargin(new Insets(0, 0, 0, 0));
                    
                    chessboard.add(square);
                }
                chessboard.add(new JLabel());
            }
            else {
                chessboard.add(new JLabel());
                for(int j = 0; j < 8; j++) {
                    JLabel label = new JLabel("" + (char)('A' + j));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setVerticalAlignment(SwingConstants.TOP);
                    chessboard.add(label);
                }
                chessboard.add(new JLabel());
            }            
        }
    }
    
//    private void showEventDemo(){
//      headerLabel.setText("Control in action: Button"); 
//
//      JButton okButton = new JButton("OK");
//      JButton submitButton = new JButton("Submit");
//      JButton cancelButton = new JButton("Cancel");
//
//      okButton.setActionCommand("OK");
//      submitButton.setActionCommand("Submit");
//      cancelButton.setActionCommand("Cancel");
//
//      okButton.addActionListener(new ButtonClickListener()); 
//      submitButton.addActionListener(new ButtonClickListener()); 
//      cancelButton.addActionListener(new ButtonClickListener()); 
//
//      controlPanel.add(okButton);
//      controlPanel.add(submitButton);
//      controlPanel.add(cancelButton);       
//
//      mainFrame.setVisible(true);  
//   }
    private class ButtonClickListener implements ActionListener{
        private int x, y;
       
        public ButtonClickListener(int i, int j) {
            x = i;
            y = j;
        }
       
        public void actionPerformed(ActionEvent e) {
            
            
//            String command = e.getActionCommand();  
//
//            if( command.equals( "OK" ))  {
//               statusLabel.setText("Ok Button clicked.");
//            } else if( command.equals( "Submit" ) )  {
//               statusLabel.setText("Submit Button clicked."); 
//            } else {
//               statusLabel.setText("Cancel Button clicked.");
//            }  	
        }		
   }
    
//    public static void main(String[] args) {
//        // TODO code application logic here
//        SwingEx se = new SwingEx();
//        //se.showEventDemo();
//    }
    
}
