import java.util.ArrayList;

import javax.swing.JOptionPane;

/*  Damian Skrzypek - 17217679
 *  Darragh Kelly   - 17235545
 *  Eoghan Russell  - 17202124
 *  Pawel Ostach    - 17214211
 */

public class is17217679
{   
    private static int[][] finalState;

    public static void main(String[] args)
    {
        String startState = "";
        String endState = "";
        int[][] initialState;

        startState = JOptionPane.showInputDialog("Enter the start state: ");
        while(!validateInput(startState))
            startState = JOptionPane.showInputDialog("Wrong format, renter the start state: ");

        endState = JOptionPane.showInputDialog("Enter the end state: ");
        while(!validateInput(endState))
            endState = JOptionPane.showInputDialog("Wrong format, renter the end state: ");

        initialState = convertInput(startState);
        finalState = convertInput(endState);

        possibleMovements(initialState);
        possibleMovements(finalState);
    }

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

    public static boolean validateInput(String in)
    {
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

    // At most 4 possible movements print them into console
    public static void possibleMovements(int[][] state)
    {
        boolean stop = false;
        showState(state);
        int i = 0, j = 0;

        for(i = 0; i < state.length && !stop; i++)
            for(j = 0; j < state[i].length && !stop; j++)
                stop = (state[i][j] == 0);
        
        i--;
        j--;

        if(i - 1 > -1)
            System.out.println("-> " + state[i-1][j] + " to the south");
        if(i + 1 < 3)
            System.out.println("-> " + state[i+1][j] + " to the north");
        if(j - 1 > -1)
            System.out.println("-> " + state[i][j - 1] + " to the east");
        if(j + 1 < 3)
            System.out.println("-> " + state[i][j + 1] + " to the west");

        System.out.println("Value of h: " + calculateH(state) + "\n");
    }

    // Numbers out of place or how far each number is from its final state
    public static int calculateH(int[][] state)
    {
        int counter = 0;

        for(int i = 0; i < state.length; i++)
            for(int j = 0; j < state[i].length; j++)
                if(state[i][j] != finalState[i][j])
                    counter++;
        
        return counter;
    }

    // This might not need to be here, g is which 
    // state u are on start state = 0, then next states are 1 more etc.
    public static int calculateG(String state)
    {
        return 0;
    }

    public static void showState(int[][] state)
    {
        for(int i = 0; i < state.length; i++)
        {
            for(int j = 0; j < state[i].length; j++)
                System.out.print(state[i][j] + " ");
            System.out.println();
        }
    }
}