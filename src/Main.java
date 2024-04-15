import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.CREATE;

// I want to add some random features later, It would be interesting to see if I could make this less clunky.
public class Main {
    public static ArrayList<String> lines = new ArrayList<>();
    public static Scanner in = new Scanner(System.in);
    public static String cmd;
    public static String line = "";

    public static void main(String[] args) {



        String menuPrompt = "A - add | D - delete | V - view | Q - quit | S - save | O - open | C - clear | [AaDdVvQqSsOoCc]";
        boolean done = false;
        boolean confirmQuit = false;
        boolean dirty = false;
        boolean dirtyCheck = false;

        do {
            /// get a cmd from the user display the cmd menu in the input prompt
            getCmd(menuPrompt,"[AaDdVvQqSsOoCc]");
            // execute it
            switch(cmd){
                case "A":
                    addLine();
                    dirty = true;
                    break;
                case "D":
                    deleteItem();
                    //display the list with item numbers
                    //get an item number for the item to delete from the user
                    //convert the item to an index --
                    dirty=true;
                    break;
                case "V":
                    displayList();
                    break;
                case "Q" :

                    confirmQuit = SafeInput.getYNconfirm(in, "Are you sure you would like to quit?");

                    if(confirmQuit) {
                        if (dirty) {
                            dirtyCheck = SafeInput.getYNconfirm(in, "You have unsaved changes, would you like to save them?");

                            if (dirtyCheck) {
                                SaveFile(lines);
                            }
                            dirty=false;
                        }
                        System.exit(0);
                    }
                    break;
                case "S":
                    SaveFile(lines);
                    dirty = false;
                    break;
                case "O":
                    if (dirty) {
                        dirtyCheck = SafeInput.getYNconfirm(in, "You have unsaved changes, would you like to save them?");

                        if (dirtyCheck) {
                            SaveFile(lines);
                            dirty=false;
                        }
                    }
                    OpenFile(lines);
                    break;
                case "C":
                    lines.clear();
                    System.out.println("List cleared!");
                    dirty=true;
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

    private static void SaveFile(ArrayList<String> lines){
        String saveName = "";

        File workingDirectory = new File(System.getProperty("user.dir"));

        saveName = SafeInput.getNonZeroLenString(in,"PLease input a file name");

        Path file = Paths.get(workingDirectory.getPath() + "\\src\\" + saveName);

        try{
            OutputStream out =
                    new BufferedOutputStream(Files.newOutputStream(file, CREATE));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(out));

            for(String rec : lines)
            {
                writer.write(rec, 0, rec.length());
                writer.newLine();
            }
            writer.close();
            System.out.println("Your list has been saved.");
        }

        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void OpenFile(ArrayList<String> lines){

            JFileChooser chooser = new JFileChooser();
            File selectedFile;
            String rec = "";

            try {
                File workingDirectory = new File(System.getProperty("user.dir"));

                chooser.setVisible(true);

                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {


                    selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();

                    InputStream in =
                            new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(in));


                    int line = 0;
                    while (reader.ready()) { //this is the important part, where the file is read and opened
                        rec = reader.readLine();

                        line++;
                        lines.add(rec);
                            //lines.set((line-1), rec);//  this could probably be improved




                        //System.out.printf("\nLine %4d %-60s ", line, rec);
                    }
                    reader.close();
                    System.out.println("\n\nData file read!");


                } else {
                    System.out.println("No file selected!!! ... exiting.\nRun the program again and select a file.");
                }

            } catch (FileNotFoundException e) {
                System.out.println("File not found!!!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
}