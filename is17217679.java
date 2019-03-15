import java.util.ArrayList;

import javax.swing.JOptionPane;

/*  Damian Skrzypek - 17217679
 *  Darragh Kelly   - 17235545
 *  Eoghan Russell  - 17202124
 *  Pawel Ostach    - 17214211
 */

public class is17217679
{
    public static void main(String[] args)
    {
        String startState = "";
        String endState = "";

        startState = JOptionPane.showInputDialog("Enter the start state: ");
        while(!validateInput(startState))
        {
            startState = JOptionPane.showInputDialog("Wrong format, renter the start state: ");
        }

        endState = JOptionPane.showInputDialog("Enter the end state: ");
        while(!validateInput(endState))
        {
            endState = JOptionPane.showInputDialog("Wrong format, renter the end state: ");
        }

        showState(startState);
        showState(endState);
    }

    public static int[] convertInput(String in) 
    {
        String [] temp = new int[9];
        temp = (in.split(" "));
        int count = 0;

        int [][] board = new int [3][3];

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++){
                board[i][j] = Integer.parseInt(temp [count]);
                count++;
            }
                
            

    }

    public static boolean validateInput(String in)
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        String[] temp = in.split("\\s");
        ArrayList<String> checkIndividual = new ArrayList<>();

        if(temp.length == 9)
        {
            for(int i = 0; i < temp.length;i++)
            {
                numbers.add(i);
            }
            for(int i = 0; i < temp.length;i++)
                 if(numbers.contains(Integer.parseInt(temp[i])) && (!checkIndividual.contains(temp[i])))
                 {
                    checkIndividual.add(temp[i]);
                 }
            
            if(checkIndividual.size() == numbers.size())
            {
                return true;
            }
        }
        return false;
    }

    // At most 4 possible movements print them into console
    public static void possibleMovements(String state)
    {

    }

    // Numbers out of place or how far each number is from its final state
    public static int calculateH(int[][] state, int[][] finalState)
    {
        int counter = 0;

        for(int i = 0; i < state.length; i++)
        {
            for(int j = 0; j < state[i].length; j++)
            {
                if(state[i][j] != finalState[i][j])
                {
                    counter++;
                }
            }
        }
    return counter;
    }

    // This might not need to be here, g is which 
    // state u are on start state = 0, then next states are 1 etc.
    public static int calculateG(String state)
    {
        return 0;
    }

    public static void showState(String state)
    {
        String output = "";
        for(int i = 0; i < state.length(); i += 2)
        {
            output += state.charAt(i) + " ";
            if(i % 3 == 1)
                output += "\n";
        }
        JOptionPane.showMessageDialog(null, output);
    }
}