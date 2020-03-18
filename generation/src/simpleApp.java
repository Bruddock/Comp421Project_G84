import javax.sound.midi.SysexMessage;
import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class simpleApp {
    public static void main ( String [ ] args ) throws SQLException {

        // Unique table names.  Either the user supplies a unique identifier as a command line argument, or the program makes one up.
//        String tableName = "";
//        String tableName1 = "";
//        String tableName2 = "";
//        int sqlCode=0;      // Variable to hold SQLCODE
//        String sqlState="00000";  // Variable to hold SQLSTATE
//
//        if ( args.length > 0 ){
//            tableName += args [ 0 ] ;
//            if(args.length > 1){
//                tableName1 += args [ 1 ];
//            }
//            if(args.length > 2){
//                tableName2 += args [ 2 ];
//            }
//        }
//        else {
//            tableName += "example3.tbl";
//        }


        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
        } catch (Exception cnfe){
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection (url,"cs421g84", "reduce2084") ;
        Statement statement = con.createStatement ( ) ;

        boolean appRun = true;
        while(appRun){
            System.out.println("Hello, welcome to the catering co application.");
            System.out.println("Please enter the number corresponding to the option you would like to take.");
            System.out.println("1: Update the status of an invoice.");
            System.out.println("2: Place an order for ingredients.");
            System.out.println("3: Cancel a reservation.");
            System.out.println("4: OPTION FOUR.");
            System.out.println("5: OPTION FIVE.");
            System.out.println("6: QUIT.");

            Scanner scanned = new Scanner(System.in);
            String y = scanned.nextLine();
            switch (y) {
                case "1":
                    System.out.println("Selected option 1");
                    runOptionOne(scanned, statement);
                    break;
                case "2":
                    System.out.println("Selected option 2");
                    runOptionTwo(scanned, statement);
                    break;
                case "3":
                    System.out.println("Selected option 3");
                    runOptionThree(scanned, statement);
                    break;
                case "4":
                    System.out.println("working");
                    runOptionFour();
                    break;
                case "5":
                    System.out.println("working");
                    runOptionFive();
                    break;
                case "6":
                    appRun = false;
                    break;
                default:
                    System.out.println("Please enter one of the valid options");
                    break;
            }

        }

        // Finally but importantly close the statement and connection
        statement.close ( ) ;
        con.close ( ) ;

    }


    public static void runOptionOne(Scanner scanner, Statement statement) throws SQLException {
        String insertString;
        String querySQL = "select emailaddress from customer where emailaddress LIKE \'C__@%\' OR emailaddress LIKE \'C_@%\'";
        java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
        ArrayList<String> addresses = new ArrayList();
        while ( rs.next ( ) ) {
            addresses.add(rs.getString(1));
        }
        int response = -1;
        String holder = "";
        while(true) {
            for (int i = 0; i < addresses.size(); i++) {
                System.out.println(i + " : " + addresses.get(i));
            }
            System.out.println("To view all current invoices.");
            System.out.println("Please select the target email address by entering the corresponding number");
            holder = scanner.nextLine();
            try {
                response = Integer.parseInt(holder);
                if (response > 0 && response < addresses.size()) {
                    System.out.println(addresses.get(response));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        querySQL = "select * from customerinvoices where emailaddress = \'" + addresses.get(response) + "\'";
        rs = statement.executeQuery ( querySQL );

        ArrayList<String> invoices = new ArrayList();
        ArrayList<Integer> invoiceid = new ArrayList();
        while ( rs.next ( ) ) {
            String currentIndex = "";
            if(!rs.getString(2).equals("null")){
                currentIndex = currentIndex + rs.getString(1) + ", ";
                currentIndex = currentIndex + rs.getDate(2) + ", ";
                currentIndex = currentIndex + rs.getInt(3) + ", ";
                currentIndex = currentIndex + rs.getInt(4) + ", ";
                currentIndex = currentIndex + rs.getString(5) + ", ";
                currentIndex = currentIndex + rs.getInt(6);
            }
            invoices.add(currentIndex);
            invoiceid.add(rs.getInt(4));
        }

        if(invoices.size() == 0) {
            System.out.println("This customer has no current invoices");
        }

        int target = -1;
        while(true) {
            for (int i = 0; i < invoices.size(); i++) {
                System.out.println((i + 1) + " : " + invoices.get(i));
            }
            System.out.println("Please select invoice you wish to alter");

            holder = scanner.nextLine();
            try {
                target = Integer.parseInt(holder);
                if (target > 0 && target< invoices.size()) {
                    System.out.println(invoices.get(target - 1));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        int change = 0;
        while(true) {
            System.out.println("Please select the updated status by entering the corresponding number.");
            System.out.println("1: under review");
            System.out.println("2: accepted");
            System.out.println("3: rejected");

            holder = scanner.nextLine();
            try {
                change = Integer.parseInt(holder);
                if (change > 0 && change < 4) {
                    System.out.println(invoices.get(target - 1));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        String status = "";
        switch(change){
            case 1: status = "under review"; break;
            case 2: status = "accepted"; break;
            case 3: status = "rejected"; break;
            default: break;
        }

        rs = statement.executeQuery( "select * from invoice where invoiceid = " + invoiceid.get(target));
        rs.next();
        System.out.println("before: " + rs.getInt(4) + " , " + rs.getString(5));
        insertString = "update invoice set status = \'" + status + "\' where invoiceid = " + invoiceid.get(target);
        System.out.println(insertString);
        statement.executeUpdate ( insertString );
        rs = statement.executeQuery( "select * from invoice where invoiceid = " + invoiceid.get(target));
        rs.next();
        System.out.println("after: " + rs.getInt(4) + " , " + rs.getString(5));
        return;
    }

    public static void runOptionTwo(Scanner scanner, Statement statement) throws SQLException {
        String querySQL = "";
        System.out.println("Please enter your staff id.");
        int id = scanner.nextInt();
        querySQL = "select * from menu where menuid in (select menuid from prepares where employeeid = " + id + ")";
        System.out.println(querySQL);
        java.sql.ResultSet rs = statement.executeQuery ( querySQL );
        ArrayList<String> menus = new ArrayList();
        ArrayList<Integer> menuId = new ArrayList();
        while ( rs.next ( ) ) {
            String currentMenu = "";
            currentMenu = currentMenu + rs.getInt(1) + ", ";
            currentMenu = currentMenu + rs.getInt(2) + ", ";
            currentMenu = currentMenu + rs.getString(3);
            
            menus.add(currentMenu);
            menuId.add(rs.getInt(2));
        }

        String holder = "";
        while(true){
            System.out.println("Please select the menu id from below.");
            for(int i = 0; i < menus.size(); i++){
                System.out.println((i) + " : " + menus.get(i));
            }
            scanner.nextLine();
            holder = scanner.nextLine();
            try {
                id = Integer.parseInt(holder);
                if (id >= 0 && id < menuId.size()) {
                    System.out.println(menuId.get(id));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }


        querySQL = "select * from dish where name in (select name from contains where menuid = " + menuId.get(id) + ")";
        System.out.println(querySQL);
        rs = statement.executeQuery ( querySQL );
        ArrayList<String> dishes = new ArrayList();
        ArrayList<String> dishname = new ArrayList();
        while ( rs.next ( ) ) {
            String currentDish = "";
            currentDish = currentDish + rs.getString(1) + ", ";
            currentDish = currentDish + rs.getTime(2) + ", ";
            currentDish = currentDish + rs.getString(3) + " , ";
            currentDish = currentDish + rs.getString(4) + " , ";
            currentDish = currentDish + rs.getBoolean(5);

            dishes.add(currentDish);
            dishname.add(rs.getString(1));
        }

        while(true){
            System.out.println("Please select a dish to order for.");
            for(int i = 0; i < dishes.size(); i++){
                System.out.println((i) + " : " + dishes.get(i));
            }

            holder = scanner.nextLine();

            try {
                id = Integer.parseInt(holder);
                if (id >= 0 && id < dishes.size()) {
                    System.out.println(dishes.get(id));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }


        String targetDish = dishname.get(id);
        System.out.println(targetDish);

        System.out.println("Enter the name of the ingredient you wish to order."); //could do with entering a string
        String ingredientName = scanner.next();

        int quantity = -1;
        while(true){
            System.out.println("Enter the quantity.");
            holder = scanner.nextLine();
            try {
                quantity = Integer.parseInt(holder);
                break;
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        //ask if they want it delivered
        boolean b;// = scanner.nextBoolean();
        while(true){
            System.out.println("Do you want it delivered? [y/n]");
            holder = scanner.nextLine();
            if(holder.equals("y")) {
                b = true;
                break;
            } else if(holder.equals("n")) {
                b = false;
                break;
            } else {
                System.out.println("Please enter y or n");
            }
        }

        System.out.println("Select the company to order from.");
        querySQL = "select companyName from supplier where delivers = \'" + b + "\'";
        System.out.println(querySQL);
        rs = statement.executeQuery ( querySQL );
        ArrayList<String> companies = new ArrayList();
        while ( rs.next ( ) ) {
            companies.add(rs.getString(1));
        }

        int company = -1;
        while(true){
            System.out.println("Please select a company to order from.");
            for(int i = 0; i < companies.size(); i++){
                System.out.println((i) + " : " + companies.get(i));
            }
            holder = scanner.nextLine();
            try {
                company = Integer.parseInt(holder);
                if (company >= 0 && company < companies.size()) {
                    System.out.println(companies.get(company));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        String today = "" + java.time.LocalDateTime.now();
        today = today.substring(0,10);
        System.out.println("The order date will be set to " + today);

        String dDate = today;
        while(true){
            dDate = today;
            try {
                System.out.println("Please enter your desired delivery year 2020/2021/2022.");
                int year = Integer.parseInt(scanner.nextLine());
                System.out.println("Please enter your desired delivery month. [1,12]");
                int month = Integer.parseInt(scanner.nextLine());
                System.out.println("Please enter your desired delivery day. [1,31]");
                int day = Integer.parseInt(scanner.nextLine());
                if (year >= 2020 && year <= 2022 && month > 0 && month <13 && day > 0 && day <32) {
                    dDate = year + "-" + month + "-" + day;
                    break;
                } else {
                    System.out.println("Please enter a valid date.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid date within the next year.");
            }
        }

        String insertSQL = "insert into ingredients values (\'" + companies.get(company) + "\' , \'" + targetDish + "\' , \'" + today + "\' , \'" + dDate + "\' , " + quantity + " , \'" + ingredientName + "\')";
        System.out.println(insertSQL);
        statement.executeUpdate(insertSQL);


    }

    public static void runOptionThree(Scanner scanner, Statement statement) throws SQLException {

        String insertString;
        String querySQL = "select emailaddress from customer where emailaddress LIKE \'C__@%\' OR emailaddress LIKE \'C_@%\'";
        java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
        ArrayList<String> addresses = new ArrayList();
        while ( rs.next ( ) ) {
            addresses.add(rs.getString(1));
        }

        for(int i = 0; i < addresses.size(); i++){
            System.out.println(i + " : " + addresses.get(i));
        }

        System.out.println("To view all current reservations.");
        System.out.println("Please select your email address by entering the corresponding number");
        int response = scanner.nextInt();
        System.out.println(addresses.get(response));

        querySQL = "select * from reservation where clientemail = \'" + addresses.get(response) + "\'";
        rs = statement.executeQuery ( querySQL );

        ArrayList<String> reservations = new ArrayList();
        while ( rs.next ( ) ) {
            String currentIndex = "";
            if(!rs.getString(2).equals("null")){
                currentIndex = currentIndex + rs.getString(1) + ", ";
                currentIndex = currentIndex + rs.getString(2) + ", ";
                currentIndex = currentIndex + rs.getInt(3) + ", ";
                currentIndex = currentIndex + rs.getString(4);
            }
            reservations.add(currentIndex);
        }

        if(reservations.size() == 0) System.out.println("This customer has no current reservations");

        for(int i = 0; i < reservations.size(); i++){
            System.out.println((i+1) + " : " + reservations.get(i));
        }

        System.out.println("Please select the reservation you wish to alter");
        int resId = scanner.nextInt();
        System.out.println(resId);

        querySQL = "select * from event where reservationid = " + resId;
        rs = statement.executeQuery ( querySQL );

        ArrayList<String> events = new ArrayList();
        String date = "";
        String time = "";
        while ( rs.next ( ) ) {
            String currentIndex = "";
            currentIndex = currentIndex + rs.getBoolean(1) + ", ";
            currentIndex = currentIndex + rs.getInt(2) + ", ";
            currentIndex = currentIndex + rs.getString(3) + ", ";
            currentIndex = currentIndex + rs.getBoolean(4) + ", ";
            currentIndex = currentIndex + rs.getString(5) + ", ";
            currentIndex = currentIndex + rs.getString(6) + ", ";
            currentIndex = currentIndex + rs.getInt(7) + ", ";
            currentIndex = currentIndex + rs.getString(8) + ", ";
            currentIndex = currentIndex + rs.getInt(9);
            events.add(currentIndex);
            date  = rs.getString(3);
            time = rs.getString(6);

        }

        if(events.size() == 0) System.out.println("this reservation has no known events");

        for(int i = 0; i < events.size(); i++){
            System.out.println((i+1) + " : " + events.get(i));
        }

        System.out.println("Would you like to cancel this event.");
        boolean cancelresponse = scanner.nextBoolean();
        if(cancelresponse){
            //has (menu)
            querySQL = "select * from has where reservationid = " + resId;
            insertString = "delete from has where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate ( insertString );
            //staffed by
            querySQL = "select * from staffby where reservationid = " + resId;
            insertString = "delete from staffby where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate ( insertString );
            //event
            querySQL = "select * from event where reservationid = " + resId;
            insertString = "delete from event where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate ( insertString );
            //reservation
            querySQL = "select * from reservation where reservationid = " + resId;
            insertString = "delete from reservation where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate ( insertString );
        } else {
            System.out.println("Would you like to access another reservation?");
        }

    }
    public static void runOptionFour(){

        return;
    }
    public static void runOptionFive(){

        return;
    }


}
