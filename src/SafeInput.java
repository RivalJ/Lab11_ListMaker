import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SafeInput {
    /*
    overall notes for future self:
        * check names and change them to the appropriate names provided in lab
        * type in the param info for each method
        * Need to make regex string, I also need to figure out what that even is
     */
    public static int getInt(Scanner pipe, String prompt){

        int userInput = 0;
        String trash = "";//used to echo bad input back to the user
        boolean done = false;//used to end the input loop

        do {
            System.out.print(prompt + ": ");
            if (pipe.hasNextInt()) {
                userInput = pipe.nextInt();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.next();
                pipe.nextLine();
                System.out.println("invalid input: " + trash);
                done = false;
            }
        }while(!done);
        //System.out.println("user input: " + userInput);

        return userInput;

    }

    public static double getDouble(Scanner pipe, String prompt){

        double userInput = 0;
        String trash = "";//used to echo bad input back to the user
        boolean done = false;//used to end the input loop

        do {
            System.out.print(prompt + ": ");
            if (pipe.hasNextDouble()) {
                userInput = pipe.nextDouble();
                pipe.nextLine();
                done = true;
            } else {
                trash = pipe.next();
                pipe.nextLine();
                System.out.println("invalid input: " + trash);
                done = false;
            }
        }while(!done);
        //System.out.println("user input: " + userInput);

        return userInput;

    }

    /**
     *
     * @param pipe
     * @param prompt
     * @param upperLimit
     * @param lowerLimit
     * @return
     */
    public static int getRangedInt(Scanner pipe, String prompt, int upperLimit, int lowerLimit){

        int userInput = 0;
        String trash = "";//used to echo bad input back to the user
        boolean done = false;//used to end the input loop

        do {
            System.out.print(prompt + "[" + lowerLimit + " - " + upperLimit + "]"+ ": ");
            if (pipe.hasNextInt()) {
                userInput = pipe.nextInt();
                pipe.nextLine();
                if (userInput>upperLimit || userInput<lowerLimit){
                    System.out.println("invalid input, out of range: " + userInput);
                    done = false;
                }
                else{
                    done = true;
                }

            } else {
                trash = pipe.next();
                pipe.nextLine();
                System.out.println("invalid input: " + trash);
                done = false;
            }
        }while(!done);
        //System.out.println("user input: " + userInput);

        return userInput;

    }

    public static double getRangedDouble(Scanner pipe, String prompt, double upperLimit, double lowerLimit){

        double userInput = 0.0;
        String trash = "";//used to echo bad input back to the user
        boolean done = false;//used to end the input loop

        do {
            System.out.print(prompt + "[" + lowerLimit + " - " + upperLimit + "]"+ ": ");
            if (pipe.hasNextDouble()) {
                userInput = pipe.nextDouble();
                pipe.nextLine();
                if (userInput>upperLimit || userInput<lowerLimit){
                    System.out.println("invalid input, out of range: " + userInput);
                    done = false;
                }
                else{
                    done = true;
                }

            } else {
                trash = pipe.next();
                pipe.nextLine();
                System.out.println("invalid input: " + trash);
                done = false;
            }
        }while(!done);
        //System.out.println("user input: " + userInput);

        return userInput;

    }

    /**
     *
     * @param pipe a Scanner opened to read from System.in
     * @param prompt prompt for the user
     * @return a String response that is not zero length
     */
    public static String getNonZeroLenString(Scanner pipe, String prompt)
    {
        String retString = ""; // Set this to zero length. Loop runs until it isn’t
        do
        {
            System.out.print("\n" +prompt + ": "); // show prompt add space
            retString = pipe.nextLine();
        }while(retString.length() == 0);
        return retString;
    }

    public static boolean getYNconfirm(Scanner pipe, String prompt)
    { //reusing portions of the getnonzerolenstring method to account for, you guessed it, nonzerolenstrings

        String retString = ""; // Set this to zero length. Loop runs until it isn’t
        boolean done = false;
        boolean retBool = false;
        String trash = "";
        do {
            do {
                System.out.print("\n" + prompt + "[Y/N]: "); // show prompt add space
                retString = pipe.nextLine();
            } while (retString.length() == 0); //gets user input

            retString = retString.toUpperCase();//set to uppercase so we can take lowercase as acceptable inputs
            //validates user input
            if(retString.equals("Y")){
                retBool = true;
                done = true;

            }
            else if (retString.equals("N")) {
                retBool = false;
                done = true;
            }
            else{
                trash = retString;
                System.out.println("invalid input: " + trash);
                done = false;
            }


        }while(!done);

        //System.out.println("user input: " + retString); //lets the user know that we have recieved valid input
        return retBool;
    }

    public static void prettyHeader(String msg){
        final int INIT_SPACE = 54;
        int space = 0;
        space = INIT_SPACE - msg.length();
        space = space/2;
        System.out.println();
        for(int cnt = 0; cnt<60; cnt++)
            System.out.print("*");

        System.out.println();
        for(int cnt = 0; cnt<3; cnt++)
            System.out.print("*");
        for(int cnt = 0; cnt<space; cnt++)
            System.out.print(" ");

        System.out.print(msg);

        for(int cnt = 0; cnt<space; cnt++)
            System.out.print(" ");
        for(int cnt = 0; cnt<3; cnt++)
            System.out.print("*");
        System.out.println();

        for(int cnt = 0; cnt<60; cnt++)
            System.out.print("*");
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx){
        String retString = "";
        Pattern regex = Pattern.compile(regEx);
        Matcher matcher = regex.matcher("");
        boolean matchFound = false;

        do {

            retString = getNonZeroLenString(pipe, prompt); //gets a none zero length string

            matcher = regex.matcher(retString);
            matchFound = matcher.find();
            if(!matchFound)
                System.out.println("invalid input: " + retString);
        }while(!matchFound);

        //System.out.println("valid input recieved: " + retString);
        return retString;
    }
}