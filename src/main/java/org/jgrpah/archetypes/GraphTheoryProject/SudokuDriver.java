package org.jgrpah.archetypes.GraphTheoryProject;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SudokuDriver extends JPanel{
	
	public static void main(String[] args){

    	JFrame frame = new JFrame("Sudoku Solver");
	    frame.setSize(900, 9000);
	    frame.setLocation(0,0);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       
	    frame.setContentPane(new Sudoku("testboard0.txt"));
	    frame.setVisible(true);
        
        Sudoku s = new Sudoku("testboard0.txt");
        if(s != null)
        	s.printBoard();
        	s.solve();
        	
    }
}
