package org.jgrpah.archetypes.GraphTheoryProject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javax.swing.JPanel;

import java.awt.*;
import javax.swing.*;


class Sudoku extends JPanel{

    private int[][] board;
    private ArrayList<Integer>[] squares;
    private ArrayList<Integer>[] rows;
    private ArrayList<Integer>[] cols;
    private static int myWidth = 900;
    private static int myHeight = 900;

    public Sudoku(String coords){
    	File coord_file = new File(coords);   
        board = init_board(coord_file);
        
        initGraphs();
  
    }

    /*takes a .txt file with coordinates for numbers from
      a 9x9 sudoku board input: X:(X,X)*/
    //also adds to a graph that repersents a sudoku board
    private static int[][] init_board(File numCoords){

        try{    
            Scanner scan = new Scanner(numCoords);
            int[][] board = new int[9][9];

            //reads the coords for each number from the file and places
            //the numbers on the board 
            while(scan.hasNextLine()){
                String coord = scan.nextLine();
                
                int num = (int)coord.charAt(0)-48;
                int x = (int)coord.charAt(3)-48;
                int y = (int)coord.charAt(5)-48;

                board[x][y] = num;
            }
            scan.close();

            return board;
        }
        catch(FileNotFoundException e){
            
            System.out.println("ERROR: file given was not valid");
            return null;
        }
    }
    
    private void initGraphs() {
    	
    	squares = new ArrayList[9];
    	rows = new ArrayList[9];
    	cols = new ArrayList[9];
    	
    	for(int i=0; i<9;i++) {
    		squares[i] = new ArrayList<Integer>();
    		rows[i] = new ArrayList<Integer>();
    		cols[i] = new ArrayList<Integer>();

    	}
    	
    	for(int x= 0; x<9; x++) {
    		
    		for(int y=0; y<9; y++) {
    			
    			//if has a valid num (aka "color")
    			//then adds that value to its respective graph
    			if(board[x][y] != 0) {
	    			//finds which 3x3 square each value
    				//is appart of
    				int sq_i = 3*(x/3)+(y/3);
	    			
    				/*adds to value its repsective square, row, col
    				 * on a traditional sudoku board. Note that every 
    				 * value in a list shares an edge with all other 
    				 * values in the list */
	    			squares[sq_i].add(board[x][y]);
	    			rows[x].add(board[x][y]);
	    			cols[y].add(board[x][y]);
    			}
    		}
    		
    	}
    	
    }
    
    public void paintComponent(Graphics g) {
    	ImageIcon grid = new ImageIcon("grid.PNG");
    	
    	g.setColor(Color.BLUE.darker());
    	g.fillRect(0,0,myWidth,myHeight);
    	
    	//draws the grid
    	int imageH = (myHeight*2)/3;
    	int imageW =( myWidth*2)/3;
    	g.drawImage(grid.getImage(), 100,100, imageH, imageW, null);
    	
    	//place numbers on the board
    	g.setColor(Color.BLACK);
    	g.setFont(new Font("Serif",Font.BOLD, 30));
    	for(int x =0; x<9; x++) {
    		for(int y=0; y<9; y++) {
    			if(board[x][y] != 0)
    				g.drawString(""+board[x][y], 100+imageW/18+y*(imageW/9), 100+imageH/12+x*(imageH/9));
    		}
    	}
    	
    }

    //gets a value at a certain (x,y) coordinate
    public int getVal(int x, int y){
        return board[x][y];
    }

    //sets a value at a certain (x,y) position	
    public void setVal(int x, int y, int val) {
    	board[x][y] = val;
    	
    	int sq_i = 3*(x/3)+(y/3);
    	
    	squares[sq_i].add(board[x][y]);
		rows[x].add(board[x][y]);
		cols[y].add(board[x][y]);
    }
    
    //finds the potential colors at a certain position
    private ArrayList<Integer> potentialColors(int x, int y){
    	
    	 ArrayList<Integer> pCol = new ArrayList<Integer>();
    	int sq_i = 3*(x/3)+(y/3);
    	
    	for(int i= 1; i<=9; i++) {
    		if(squares[sq_i].indexOf(i) == -1 && rows[y].indexOf(i) == -1 && cols[y].indexOf(i) == -1) {
    			pCol.add(i);
    		}
    		
    	}
    	
    	return pCol;
    	
    }
    
    //solves the board using coloring algorithm
    public void solve(){
    	
    	int numColored = 0;
    	ArrayList<Integer> pCol = new ArrayList<Integer>();
    	
    	//checks if every spot has a color
    	 while(numColored < 81){
    		 
    		 numColored = 0;
    		 boolean hasOne = false;
    		 
    		 //used for backtracking
    		 ArrayList<int[][]> pastBoards = new ArrayList<int[][]>();    		 
    		 
    		 
    		//holds the coloring of the coords with the smallest number of colors 
    		 int[] minCoords = {0,0};
    		 int minCol = potentialColors(0,0).size();
    		 
    		 //first checks the board for naked singles
    		 for(int x=0; x<9; x++) {
    			 for(int y=0; y<9; y++) {
    				 if(board[x][y] == 0) {
	    				 
    					 pCol = potentialColors(x,y);
    					 if(pCol.size() == 0) {
    						 board = pastBoards.get(0);
    						 pastBoards.remove(0);
    			
    						 for(int i =0; i<9; i++) {
    							 squares[i].clear();
    							 rows[i].clear();
    							 cols[i].clear();
    						 }
    						 initGraphs();
    						 
    						 hasOne = true;
    						 break;
    					 }    					 
    					 else if(pCol.size() == 1) {
	    					 setVal(x,y,pCol.get(0));
	    					 hasOne = true;
	    				 }
	    				 else if(pCol.size() < minCol) {
	    					 minCoords[0] = x;
	    					 minCoords[1] = y;
	    				 }
	    				 
    				 }
    				 else {
    					 numColored++;
    				 }
	    				 
    			 }
    		 }
    		 
    		 if(numColored<81 && !hasOne) {
    			 int[][] pastBoard = board;
    			 pastBoards.add(0, pastBoard);
    			 setVal(minCoords[0], minCoords[1], potentialColors(minCoords[0], minCoords[1]).get(0));
    			
    		 }
    		 
        		 
    	}
    	
    }

    //prints sudoku board using the GUI
    
    
    public void printBoard(){
    	for(int i=0; i< 9; i++) {
    		for(int j=0; j<9; j++) {
    			System.out.print(board[i][j]+"  ");
    		}
    		System.out.println();
    	}
    }

    public static void main(String[] args){

//    	JFrame frame = new JFrame("Sudoku Solver");
//	    frame.setSize(myWidth, myHeight);
//	    frame.setLocation(0,0);
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	       
//	    frame.setContentPane(new Sudoku("testboard0.txt"));
//	    frame.setVisible(true);
//        
//        //Sudoku s = new Sudoku(coords);
//        //if(s != null)
//        	//s.printBoard();
        	
    }
}