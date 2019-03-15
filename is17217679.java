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
    }

    public static boolean validateInput(String in)
    {
        return true;
    }

    // At most 4 possible movements print them into console
    public static void possibleMovements(int[][] state)
    {
        boolean stop = false;
        showState(state);
        int i = 0, j = 0;

        for(i = 0; i < state.length && !stop; i++)
        {
            for(j = 0; j < state[i].length && !stop; j++)
            {
                stop = (state[i][j] == 0);
            }
        }
        i--;
        j--;

        if(i - 1 > -1)
            System.out.println(state[i-1][j] + " to the south");
        if(i + 1 < 3)
            System.out.println(state[i+1][j] + " to the north");
        if(j - 1 > -1)
            System.out.println(state[i][j - 1] + " to the east");
        if(j + 1 < 3)
            System.out.println(state[i][j + 1] + " to the west");
    }

    // Numbers out of place or how far each number is from its final state
    public static int calculateH(String state)
    {
        return 0;
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
            {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
    }
}