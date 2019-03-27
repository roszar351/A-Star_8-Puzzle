import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

/*  Damian Skrzypek - 17217679
 *  Darragh Kelly   - 17235545
 *  Eoghan Russell  - 17202124
 *  Pawel Ostach    - 17214211
 */

public class is17217679
{   
    static class State implements Comparable<State>
    {
        private static int[][] endState;
        private int[][] board;
        private int gValue;
        private int hValue;
        private int fValue;
        private State previousState;

        State(int[][] b, int g, State previous)
        {
            board = b;
            gValue = g;
            hValue = calculateH();
            fValue = gValue + hValue;
            previousState = previous;
        }

        // Calculates the 'H' value, numbers out of place
        private int calculateH()
        {
            int counter = 0;

            for(int i = 0; i < board.length; i++)
                for(int j = 0; j < board[i].length; j++)
                    if(board[i][j] != endState[i][j])
                        counter++;
            
            return counter;
        }

        public ArrayList<State> getPossibleMoves()
        {
            ArrayList<State> moves = new ArrayList<>();
            boolean stop = false;
            int i = 0, j = 0;

            for(i = 0; i < board.length && !stop; i++)
                for(j = 0; j < board[i].length && !stop; j++)
                    stop = (board[i][j] == 0);
            
            i--;
            j--;

            int[][] tempBoard = new int[board.length][board[0].length];
            copy2DArray(tempBoard);

            if(i - 1 > -1)
            {
                tempBoard[i-1][j] = 0;
                tempBoard[i][j] = board[i-1][j];
                moves.add(new State(tempBoard, gValue + 1, this));
                tempBoard = new int[board.length][board[0].length];
                copy2DArray(tempBoard);
            }
            if(i + 1 < 3)
            {
                tempBoard[i+1][j] = 0;
                tempBoard[i][j] = board[i+1][j];
                moves.add(new State(tempBoard, gValue + 1, this));
                tempBoard = new int[board.length][board[0].length];
                copy2DArray(tempBoard);
            }
            if(j - 1 > -1)
            {
                tempBoard[i][j-1] = 0;
                tempBoard[i][j] = board[i][j-1];
                moves.add(new State(tempBoard, gValue + 1, this));
                tempBoard = new int[board.length][board[0].length];
                copy2DArray(tempBoard);
            }
            if(j + 1 < 3)
            {
                tempBoard[i][j+1] = 0;
                tempBoard[i][j] = board[i][j+1];
                moves.add(new State(tempBoard, gValue + 1, this));
                tempBoard = new int[board.length][board[0].length];
                copy2DArray(tempBoard);
            }

            return moves;
        }

        private void copy2DArray(int[][] ar)
        {
            for(int i = 0; i < board.length; i++)
                for(int j = 0; j < board[i].length; j++)
                    ar[i][j] = board[i][j];
        }

        // Prints out possible moves, max 4
        public void printPossibleMovements()
        {
            boolean stop = false;
            int i = 0, j = 0;

            for(i = 0; i < board.length && !stop; i++)
                for(j = 0; j < board[i].length && !stop; j++)
                    stop = (board[i][j] == 0);
            
            i--;
            j--;

            if(i - 1 > -1)
                System.out.println("-> " + board[i-1][j] + " to the south");
            if(i + 1 < 3)
                System.out.println("-> " + board[i+1][j] + " to the north");
            if(j - 1 > -1)
                System.out.println("-> " + board[i][j - 1] + " to the east");
            if(j + 1 < 3)
                System.out.println("-> " + board[i][j + 1] + " to the west");

            System.out.println("Value of h: " + hValue + "\n");
        }

        // Returns the board state as a string
        public String toString()
        {
            String temp = "";
            for(int i = 0; i < board.length; i++)
            {
                for(int j = 0; j < board[i].length; j++)
                    temp += board[i][j] + " ";
                
                temp += "\n";
            }
            return temp;
        }

        public boolean isEqual(State s)
        {
            int[][] temp = s.getBoard();
            
            for(int i = 0; i < temp.length; i++)
                for(int j = 0; j < temp[i].length; j++)
                    if(board[i][j] != temp[i][j])
                        return false;

            return true;
        }

        // Used for sorting, sorts by the 'f' value
        public int compareTo(State s)
        {
            return this.fValue - s.fValue;
        }

        public static void printEndState()
        {
            for(int i = 0; i < endState.length; i++)
            {
                for(int j = 0; j < endState[i].length; j++)
                    System.out.print(endState[i][j] + " ");
                
                System.out.println();
            }
        }

        public static void setEndState(int[][] e)
        {
            endState = e;
        }

        public static int[][] getEndState()
        {
            return endState;
        }

        public int[][] getBoard()
        {
            return board;
        }

        public int getG()
        {
            return gValue;
        }

        public int getH()
        {
            return hValue;
        }

        public int getf()
        {
            return fValue;
        }

        public State getPreviousState()
        {
            return previousState;
        }
    }
    public static void main(String[] args)
    {
        String startState = "";
        String endState = "";

        startState = JOptionPane.showInputDialog("Enter the start state: ");
        while(startState != null && !validateInput(startState))
            startState = JOptionPane.showInputDialog("Wrong format, renter the start state: ");

        if(startState != null)
        {
            endState = JOptionPane.showInputDialog("Enter the end state: ");
            while(endState != null && !validateInput(endState))
                endState = JOptionPane.showInputDialog("Wrong format, renter the end state: ");

            if(endState != null)
            {
                State.setEndState(convertInput(endState));
                // Set currentstate to the starting state

                ArrayList<State> openSet = new ArrayList<>();
                ArrayList<State> closedSet = new ArrayList<>();
                State currentState = null;
                boolean found = false;

                openSet.add(new State(convertInput(startState), 0, null));
                System.out.println("Finding solution for:\n" + openSet.get(0));

                while(!openSet.isEmpty() && !found)
                {
                    Collections.sort(openSet);
                    currentState = openSet.get(0);
                    if(currentState.getH() == 0)
                        found = true;
                    else
                    {
                        openSet.remove(0);
                        closedSet.add(currentState);
                        ArrayList<State> possibleMoves = currentState.getPossibleMoves();
                        for(int i = 0; i < possibleMoves.size(); i++)
                        {
                            State temp = possibleMoves.get(i);
                            if(!inSet(closedSet, temp) && !inSet(openSet, temp))
                                openSet.add(temp);
                        }
                    }
                }

                if(!found)
                    System.out.println("Solution not found!");
                else
                {
                    int moves = 0;
                    System.out.println("Solution found!");
                    while(currentState.getPreviousState() != null)
                    {
                        System.out.println(currentState);
                        currentState = currentState.getPreviousState();
                        moves++;
                    }
                    System.out.println(currentState + "\nIt took " + moves + " moves.");
                }
                
            }
        }
    }
//Change so it can take a bigger matrix, i.e. 8,15 puzzle
    public static int[][] convertInput(String in) 
    {
        String [] temp;
        temp = (in.split(" "));
        int count = 0;

        int [][] board = new int [3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
            {
                board[i][j] = Integer.parseInt(temp [count]);
                count++;
            }

        return board;
    }
//Change so it can take either 8 or 15 puzzle.
    public static boolean validateInput(String in)
    {
        if(in == null)
            return false;

        ArrayList<Integer> numbers = new ArrayList<>();
        String[] temp = in.split("\\s");
        ArrayList<String> checkIndividual = new ArrayList<>();

        if(temp.length == 9)
        {
            for(int i = 0; i < temp.length;i++)
                numbers.add(i);
            
            for(int i = 0; i < temp.length;i++)
                if(numbers.contains(Integer.parseInt(temp[i])) && (!checkIndividual.contains(temp[i])))
                    checkIndividual.add(temp[i]);
            
            if(checkIndividual.size() == numbers.size())
                return true;
        }

        return false;
    }

    public static boolean inSet(ArrayList<State> set, State s)
    {
        for(int i = 0; i < set.size(); i++)
            if(s.isEqual(set.get(i)))
                return true;

        return false;
    }
}