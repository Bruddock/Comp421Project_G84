import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


public class simpleApp {
    public static void main ( String [ ] args ) throws SQLException {

        // Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
        String tableName = "";
        String tableName1 = "";
        String tableName2 = "";
        int sqlCode=0;      // Variable to hold SQLCODE
        String sqlState="00000";  // Variable to hold SQLSTATE

        if ( args.length > 0 ){
            tableName += args [ 0 ] ;
            if(args.length > 1){
                tableName1 += args [ 1 ];
            }
            if(args.length > 2){
                tableName2 += args [ 2 ];
            }
        }
        else {
            tableName += "example3.tbl";
        }


        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
        } catch (Exception cnfe){
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
//        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
//        Connection con = DriverManager.getConnection (url,"cs421g84", "reduce2084") ;
//        Statement statement = con.createStatement ( ) ;

        boolean appRun = true;
        while(appRun){
            System.out.println("Hello, welcome to the catering co application.");
            System.out.println("Please enter the number corresponding to the option you would like to take.");
            System.out.println("1 OPTION ONE.");
            System.out.println("2 OPTION TWO.");
            System.out.println("3 OPTION THREE.");
            System.out.println("4 OPTION FOUR.");
            System.out.println("5 OPTION FIVE.");
            System.out.println("6 quit.");

            Scanner myObj = new Scanner(System.in);
            String y = myObj.nextLine();
            if(y.equals("1")){
                System.out.println("working");
                runOptionOne();
            } else if(y.equals("2")){
                System.out.println("working");
                runOptionTwo();
            } else if(y.equals("3")){
                System.out.println("working");
                runOptionThree();
            } else if(y.equals("4")){
                System.out.println("working");
                runOptionFour();
            } else if(y.equals("5")){
                System.out.println("working");
                runOptionFive();
            } else if (y.equals("6")){
                appRun = false;
            }




        }

        // Finally but importantly close the statement and connection
//        statement.close ( ) ;
//        con.close ( ) ;

    }


    public static void runOptionOne(){

        return;
    }
    public static void runOptionTwo(){

        return;
    }
    public static void runOptionThree(){

        return;
    }
    public static void runOptionFour(){

        return;
    }
    public static void runOptionFive(){

        return;
    }


}
