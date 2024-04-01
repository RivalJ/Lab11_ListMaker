import java.util.ArrayList;
import java.util.Scanner;
// I want to add some random features later, It would be interesting to see if I could make this less clunky.
public class Main {
    public static ArrayList<String> lines = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);
    public static String cmd;
    public static String line = "";

    public static void main(String[] args) {

        String menuPrompt = "A - add | D - delete | V - view | Q - quit | [AaDdVvQq]";
        boolean done = false;
        boolean confirmQuit = false;

        do {
            /// get a cmd from the user display the cmd menu in the input prompt
            getCmd(menuPrompt,"[AaDdVvQq]");
            // execute it
            switch(cmd){
                case "A":
                    addLine();
                    break;
                case "D":
                    deleteItem();
                    //display the list with item numbers
                    //get an item number for the item to delete from the user
                    //convert the item to an index --
                    break;
                case "V":
                    displayList();
                    break;
                case "Q" :
                    confirmQuit = SafeInput.getYNconfirm(in,"Are you sure you would like to quit?");
                    if(confirmQuit)
                        System.exit(0);
                    break;
            }
        }while(!done);
    }
    //methods start below

    /**
     * displays the list that the user creates, has borders to give clear start and end to the list
     */
    private static void displayList() {
        System.out.println("===========================================================");
        if(lines.size() > 0){
            int itemNum = 1;
            for(String l: lines) {
                System.out.println(itemNum + ". " + l);
                itemNum++;
            }
        }
        else{
            System.out.println("Currently, the list is empty!");
        }
        System.out.println("===========================================================");
    }

    /**
     * Deletes an item from the list
     */
    private static void deleteItem(){
        int itemToDelete = 0;
        int indexToDelete = 0;

        itemToDelete = SafeInput.getRangedInt(in,"Enter the number of the item to delete",lines.size(),1);
        //convert item to index
        itemToDelete = itemToDelete - 1;
        lines.remove(itemToDelete);

        //display the list with item numbers
        //get an item number for the item to delete from the user
        //convert the item to an index --

        //probably should move these to the parts they match with, will do this later. If I do it at all.
    }

    /**
     * Adds an item to list
     */
    private static void addLine(){
        line = SafeInput.getNonZeroLenString(in,"Enter the new item to add to the list");
        lines.add(line);
    }

    /**
     * gets a command from the user, although we return the string named "cmd". declaring a return type
     * should not be necessary as the variable cmd is declared at the class level.
     * @param menuPrompt
     * @param menuRegEx
     */
    private static void getCmd(String menuPrompt, String menuRegEx){
        cmd = SafeInput.getRegExString(in, menuPrompt, menuRegEx);
        cmd = cmd.toUpperCase();
    }
}