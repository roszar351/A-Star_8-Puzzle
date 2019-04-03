import java.util.ArrayList;

import javax.swing.JOptionPane;

/*  Damian Skrzypek - 17217679
 *  Darragh Kelly   - 17235545
 *  Eoghan Russell  - 17202124
 *  Pawel Ostach    - 17214211
 */

public class is17217679
{   
    static class State
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

        // Calculates the 'H' value
        private int calculateH()
        {
            int counter = 0;

            for(int i = 0; i < board.length; i++)
                for(int j = 0; j < board.length; j++)
                    for(int x = 0; x < board.length; x++)
                        for(int y = 0; y < board.length; y++)
                            if(board[i][j] == endState[x][y] && board[i][j] != 0)
                            {
                                counter += Math.abs(i - x) + Math.abs(j - y);
                                x = board.length;
                                y = board.length;
                            }
            
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
            if(i + 1 < board.length)
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
            if(j + 1 < board.length)
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
            if(i + 1 < board.length)
                System.out.println("-> " + board[i+1][j] + " to the north");
            if(j - 1 > -1)
                System.out.println("-> " + board[i][j - 1] + " to the east");
            if(j + 1 < board.length)
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
            while(endState != null && !validateInput(endState, startState.length()))
                endState = JOptionPane.showInputDialog("Wrong format, renter the end state: ");

            if(endState != null)
            {
                State.setEndState(convertInput(endState));

                ArrayList<State> openSet = new ArrayList<>();
                ArrayList<State> closedSet = new ArrayList<>();
                State currentState = null;
                boolean found = false;

                openSet.add(new State(convertInput(startState), 0, null));
                System.out.println("Finding solution for:\n" + openSet.get(0));

                while(!openSet.isEmpty() && !found)
                {
                    int index = 0;
                    int lowestf = openSet.get(0).getf();
                    int lowestg = openSet.get(0).getG();

                    for(int i = 1; i < openSet.size(); i++)
                    {
                        if(openSet.get(i).getf() < lowestf)
                        {
                            index = i;
                            lowestf = openSet.get(i).getf();
                        }
                        else if(openSet.get(i).getf() == lowestf)
                        {
                            if(openSet.get(i).getG() < lowestg)
                            {
                                lowestg = openSet.get(i).getG();
                                index = i;
                            }
                        }
                    }

                    currentState = openSet.get(index);
                    if(currentState.getH() == 0)
                        found = true;
                    else
                    {
                        openSet.remove(index);
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
                    ArrayList<State> path = new ArrayList<>();
                    System.out.println("Solution found!");
                    System.out.println("-------END-------");
                    while(currentState.getPreviousState() != null)
                    {
                        path.add(currentState);
                        currentState = currentState.getPreviousState();
                    }
                    for(int i = 0 ; i < path.size(); i++)
                    {
                        System.out.println("Move " + (path.size() - i) + ":\n" + path.get(i));
                    }
                    System.out.println("Scroll up for moves\n-------Start-------\nInitial State:\n" + currentState);
                    System.out.println("\nIt took " + path.size() + " moves.");
                }
                
            }
        }
    }

    public static int[][] convertInput(String in) 
    {
        String [] temp;
        temp = (in.split(" "));
        int count = 0;
        int size = (int)Math.sqrt(temp.length);

        int [][] board = new int [size][size];

        for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
            {
                board[i][j] = Integer.parseInt(temp [count]);
                count++;
            }

        return board;
    }

    public static boolean validateInput(String in)
    {
        if(in == null)
            return false;

        ArrayList<Integer> numbers = new ArrayList<>();
        String[] temp = in.split("\\s");
        ArrayList<String> checkIndividual = new ArrayList<>();

        if(temp.length == 9 || temp.length == 16)
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

    public static boolean validateInput(String in, int s)
    {
        if(in == null || in.length() != s)
            return false;

        ArrayList<Integer> numbers = new ArrayList<>();
        String[] temp = in.split("\\s");
        ArrayList<String> checkIndividual = new ArrayList<>();

        if(temp.length == 9 || temp.length == 16)
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
