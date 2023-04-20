import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.jgrapht.*;


class Sudoku{

    private int[][] board;

    public Sudoku(File coords){
        board = init(coords);
    }

    /*takes a .txt file with coordinates for numbers from
      a 9x9 sudoku board input: X:(X,X)*/ 
    private static int[][] init(File numCoords){

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

    //gets a value at a certain (x,y) coordinate
    public int getVal(int x, int y){
        return board[x][y];
    }

    //solves the board using coloring algorithm
    public void solve(){

    }

    //prints sudoku board using the GUI
    public void printBoard(){

    }

    public static void main(String[] args){

        File coords = new File("testboard0.txt");   
        Sudoku s = new Sudoku(coords);
        

        
    System.out.println("tesst");
    }
}