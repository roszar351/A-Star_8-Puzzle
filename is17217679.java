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

    public static boolean validateInput(String in)
    {
        return true;
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