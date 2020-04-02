import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class simpleApp {

    public static void main(String[] args) throws SQLException {

        // Register the driver.  You must register the driver before you can use it.
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();

        boolean appRun = true;
        while (appRun) {
            System.out.println("Hello, welcome to the catering co application.");
            System.out.println("Please enter the number corresponding to the option you would like to take.");
            System.out.println("1: Update the status of an invoice.");
            System.out.println("2: Place an order for ingredients.");
            System.out.println("3: Cancel a reservation.");
            System.out.println("4: View or modify a menu.");
            System.out.println("5: Create a customer or supplier invoice.");
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
                    System.out.println("Selected option 4");
                    runOptionFour(scanned, statement);
                    break;
                case "5":
                    System.out.println("Selected option 5");
                    runOptionFive(scanned, statement);
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
        statement.close();
        con.close();

    }

    public static ArrayList<String> getAddresses()throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select emailaddress from customer where emailaddress LIKE 'C__@%' OR emailaddress LIKE 'C_@%'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }
        return addresses;
    }

    public static ArrayList<String> getInvoices(String Address)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select * from customerinvoices where emailaddress = '" + Address + "'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<String> invoices = new ArrayList<>();
        ArrayList<Integer> invoiceid = new ArrayList<>();

        while (rs.next()) {
            String currentIndex = "";
            if (!rs.getString(2).equals("null")) {
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
        return invoices;
    }

    public static ArrayList<Integer> getInvoiceId(String Address)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select * from customerinvoices where emailaddress = '" + Address + "'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<Integer> invoiceid = new ArrayList<>();

        while (rs.next()) {
            invoiceid.add(rs.getInt(4));
        }
        return invoiceid;
    }

    public static void updateInvoice(int invoiceId, String status) throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        java.sql.ResultSet rs = statement.executeQuery("select * from invoice where invoiceid = " + invoiceId);
        System.out.println("select * from invoice where invoiceid = " + invoiceId);
        rs.next();
        System.out.println("before: " + rs.getInt(4) + " , " + rs.getString(5));
        String insertString = "update invoice set status = '" + status + "' where invoiceid = " + invoiceId;
//        statement.executeUpdate(insertString);
        System.out.println(insertString);
        rs = statement.executeQuery("select * from invoice where invoiceid = " + invoiceId);
        rs.next();
        System.out.println("after: " + rs.getInt(4) + " , " + rs.getString(5));
    }

    public static void runOptionOne(Scanner scanner, Statement statement) throws SQLException {
        String insertString;
        String querySQL = "select emailaddress from customer where emailaddress LIKE 'C__@%' OR emailaddress LIKE 'C_@%'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }
        int response;
        String holder;
        while (true) {
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
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        querySQL = "select * from customerinvoices where emailaddress = '" + addresses.get(response) + "'";
        rs = statement.executeQuery(querySQL);

        ArrayList<String> invoices = new ArrayList<>();
        ArrayList<Integer> invoiceid = new ArrayList<>();
        while (rs.next()) {
            String currentIndex = "";
            if (!rs.getString(2).equals("null")) {
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
        if (invoices.size() == 0) {
            System.out.println("This customer has no current invoices");
        }

        int target;
        while (true) {
            for (int i = 0; i < invoices.size(); i++) {
                System.out.println((i) + " : " + invoices.get(i));
            }
            System.out.println("Please select invoice you wish to alter");
            holder = scanner.nextLine();
            try {
                target = Integer.parseInt(holder);
                if (target > 0 && target < invoices.size()) {
                    System.out.println(invoices.get(target));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        int change;
        while (true) {
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
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        String status = "";
        switch (change) {
            case 1:
                status = "under review";
                break;
            case 2:
                status = "accepted";
                break;
            case 3:
                status = "rejected";
                break;
            default:
                break;
        }

        rs = statement.executeQuery("select * from invoice where invoiceid = " + invoiceid.get(target));
        rs.next();
        System.out.println("before: " + rs.getInt(4) + " , " + rs.getString(5));
        insertString = "update invoice set status = '" + status + "' where invoiceid = " + invoiceid.get(target);
        System.out.println(insertString);
        statement.executeUpdate(insertString);
        rs = statement.executeQuery("select * from invoice where invoiceid = " + invoiceid.get(target));
        rs.next();
        System.out.println("after: " + rs.getInt(4) + " , " + rs.getString(5));
        System.out.println("Returning to main menu.");
    }

    public static ArrayList<Integer> getStaffId()throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select employeeId from staff order by employeeId ASC";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<Integer> staffid = new ArrayList<>();

        while (rs.next()) {
            staffid.add(rs.getInt(1));
        }
        return staffid;
    }

    public static ArrayList<String> getMenu(int id)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select * from menu where menuid in (select menuid from prepares where employeeid = " + id + ")";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<String> menus = new ArrayList<>();

        while (rs.next()) {
            String currentMenu = "";
            currentMenu = currentMenu + rs.getInt(1) + ", ";
            currentMenu = currentMenu + rs.getInt(2) + ", ";
            currentMenu = currentMenu + rs.getString(3);
            menus.add(currentMenu);
        }
        return menus;
    }

    public static ArrayList<Integer> getMenuId(int id)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select menuid from menu where menuid in (select menuid from prepares where employeeid = " + id + ")";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<Integer> invoiceid = new ArrayList<>();

        while (rs.next()) {
            invoiceid.add(rs.getInt(1));
        }
        return invoiceid;
    }

    public static ArrayList<String> getDish(int id)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select * from dish where name in (select name from contains where menuid = " + id + ")";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<String> dishes = new ArrayList<>();

        while (rs.next()) {
            String currentDish = "";
            currentDish = currentDish + rs.getString(1) + ", ";
            currentDish = currentDish + rs.getTime(2) + ", ";
            currentDish = currentDish + rs.getString(3) + " , ";
            currentDish = currentDish + rs.getString(4) + " , ";
            currentDish = currentDish + rs.getBoolean(5);
            dishes.add(currentDish);
        }
        return dishes;
    }

    public static ArrayList<String> getDishName(int id)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select * from dish where name in (select name from contains where menuid = " + id + ")";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);

        ArrayList<String> dishname = new ArrayList<>();

        while (rs.next()) {
            dishname.add(rs.getString(1));
        }
        return dishname;
    }

    public static ArrayList<String> getCompany(boolean b)throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();
        String querySQL = "select companyName from supplier where delivers = '" + b + "'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }
        return addresses;
    }

    public static void orderIngredients(String company, String targetDish, String dDate, int quantity, String ingredientName) throws SQLException {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (Exception cnfe) {
            System.out.println("Class not found");
        }

        // This is the url you must use for Postgresql.
        //Note: This url may not valid now !
        String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
        Connection con = DriverManager.getConnection(url, "cs421g84", "reduce2084");
        Statement statement = con.createStatement();

        String today = "" + java.time.LocalDateTime.now();
        today = today.substring(0, 10);

//        String insertSQL = "insert into ingredients values ('" + company + "' , '" + targetDish + "' , '" + today + "' , '" + dDate + "' , " + quantity + " , '" + ingredientName + "')";
        String insertSQL = "insert into ingredients values ('" + company + "' , '" + targetDish + "' , '" + today + "' , '" + today + "' , " + quantity + " , '" + ingredientName + "')";
        System.out.println(insertSQL);
//        statement.executeUpdate(insertSQL);
    }

    public static void runOptionTwo(Scanner scanner, Statement statement) throws SQLException {
        String querySQL;
        int id;
        ArrayList<String> menus = new ArrayList<>();
        ArrayList<Integer> menuId = new ArrayList<>();
        java.sql.ResultSet rs;
        String holder;
        while (true) {
            System.out.println("Please enter your staff id.");
            holder = scanner.nextLine();
            try {
                id = Integer.parseInt(holder);
            } catch (Exception NumberFormatException) {
                continue;
            }
            querySQL = "select * from menu where menuid in (select menuid from prepares where employeeid = " + id + ")";
            System.out.println(querySQL);
            rs = statement.executeQuery(querySQL);
            while (rs.next()) {
                String currentMenu = "";
                currentMenu = currentMenu + rs.getInt(1) + ", ";
                currentMenu = currentMenu + rs.getInt(2) + ", ";
                currentMenu = currentMenu + rs.getString(3);
                menus.add(currentMenu);
                menuId.add(rs.getInt(2));
            }
            if (menus.size() == 0) {
                System.out.println("The id that you entered either doesn't exist or isn't in charge of preparing menus.");
            } else {
                break;
            }
        }

        while (true) {
            System.out.println("Please select the menu id from below.");
            for (int i = 0; i < menus.size(); i++) {
                System.out.println((i) + " : " + menus.get(i));
            }
//            scanner.nextLine();
            holder = scanner.nextLine();
            try {
                id = Integer.parseInt(holder);
                if (id >= 0 && id < menuId.size()) {
                    System.out.println(menuId.get(id));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }


        querySQL = "select * from dish where name in (select name from contains where menuid = " + menuId.get(id) + ")";
        System.out.println(querySQL);
        rs = statement.executeQuery(querySQL);
        ArrayList<String> dishes = new ArrayList<>();
        ArrayList<String> dishname = new ArrayList<>();
        while (rs.next()) {
            String currentDish = "";
            currentDish = currentDish + rs.getString(1) + ", ";
            currentDish = currentDish + rs.getTime(2) + ", ";
            currentDish = currentDish + rs.getString(3) + " , ";
            currentDish = currentDish + rs.getString(4) + " , ";
            currentDish = currentDish + rs.getBoolean(5);
            dishes.add(currentDish);
            dishname.add(rs.getString(1));
        }

        while (true) {
            System.out.println("Please select a dish to order for.");
            for (int i = 0; i < dishes.size(); i++) {
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
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }


        String targetDish = dishname.get(id);
        System.out.println(targetDish);

        System.out.println("Enter the name of the ingredient you wish to order."); //could do with entering a string
        String ingredientName = scanner.next();

        int quantity;
        while (true) {
            System.out.println("Enter the quantity.");
            holder = scanner.nextLine();
            try {
                quantity = Integer.parseInt(holder);
                break;
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        //ask if they want it delivered
        boolean b;// = scanner.nextBoolean();
        while (true) {
            System.out.println("Do you want it delivered? [y/n]");
            holder = scanner.nextLine();
            if (holder.equals("y")) {
                b = true;
                break;
            } else if (holder.equals("n")) {
                b = false;
                break;
            } else {
                System.out.println("Please enter y or n");
            }
        }

        System.out.println("Select the company to order from.");
        querySQL = "select companyName from supplier where delivers = '" + b + "'";
        System.out.println(querySQL);
        rs = statement.executeQuery(querySQL);
        ArrayList<String> companies = new ArrayList<>();
        while (rs.next()) {
            companies.add(rs.getString(1));
        }

        int company;
        while (true) {
            System.out.println("Please select a company to order from.");
            for (int i = 0; i < companies.size(); i++) {
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
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        String today = "" + java.time.LocalDateTime.now();
        today = today.substring(0, 10);
        System.out.println("The order date will be set to " + today);

        String dDate;
        while (true) {
            try {
                System.out.println("Please enter your desired delivery year 2020/2021/2022.");
                int year = Integer.parseInt(scanner.nextLine());
                System.out.println("Please enter your desired delivery month. [1,12]");
                int month = Integer.parseInt(scanner.nextLine());
                System.out.println("Please enter your desired delivery day. [1,31]");
                int day = Integer.parseInt(scanner.nextLine());
                if (year >= 2020 && year <= 2022 && month > 0 && month < 13 && day > 0 && day < 32) {
                    dDate = year + "-" + month + "-" + day;
                    break;
                } else {
                    System.out.println("Please enter a valid date.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid date within the next year.");
            }
        }
        String insertSQL = "insert into ingredients values ('" + companies.get(company) + "' , '" + targetDish + "' , '" + today + "' , '" + dDate + "' , " + quantity + " , '" + ingredientName + "')";
        System.out.println(insertSQL);
        statement.executeUpdate(insertSQL);
        System.out.println("Returning to main menu.");
    }

    public static void runOptionThree(Scanner scanner, Statement statement) throws SQLException {

        String insertString;
        String querySQL = "select emailaddress from customer where emailaddress LIKE 'C__@%' OR emailaddress LIKE 'C_@%'";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }
        int response;
        String holder;
        while (true) {
            for (int i = 0; i < addresses.size(); i++) {
                System.out.println(i + " : " + addresses.get(i));
            }
            System.out.println("To view all current reservations.");
            System.out.println("Please select your email address by entering the corresponding number");
            holder = scanner.nextLine();
            try {
                response = Integer.parseInt(holder);
                if (response >= 0 && response < addresses.size()) {
                    System.out.println(addresses.get(response));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        querySQL = "select * from reservation where clientemail = '" + addresses.get(response) + "'";
        rs = statement.executeQuery(querySQL);

        ArrayList<String> reservations = new ArrayList<>();
        ArrayList<Integer> reservationids = new ArrayList<>();
        while (rs.next()) {
            String currentIndex = "";
            if (!rs.getString(2).equals("null")) {
                currentIndex = currentIndex + rs.getString(1) + ", ";
                currentIndex = currentIndex + rs.getString(2) + ", ";
                currentIndex = currentIndex + rs.getInt(3) + ", ";
                currentIndex = currentIndex + rs.getString(4);
            }
            reservations.add(currentIndex);
            reservationids.add(rs.getInt(3));
        }

        if (reservations.size() == 0) System.out.println("This customer has no current reservations");
        int resId;
        while (true) {
            for (int i = 0; i < reservations.size(); i++) {
                System.out.println((i) + " : " + reservations.get(i));
            }

            System.out.println("Please select the reservation you wish to alter");
            holder = scanner.nextLine();
            try {
                resId = Integer.parseInt(holder);
                if (resId >= 0 && resId < reservations.size()) {
                    System.out.println(reservationids.get(resId));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        querySQL = "select * from event where reservationid = " + reservationids.get(resId);
        rs = statement.executeQuery(querySQL);

        ArrayList<String> events = new ArrayList<>();
        while (rs.next()) {
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
        }

        if (events.size() == 0) System.out.println("this reservation has no known events");

        boolean cancelresponse;
        while (true) {
            System.out.println("Would you like to cancel this event. [y/n]");
            for (int i = 0; i < events.size(); i++) {
                System.out.println((i + 1) + " : " + events.get(i));
            }
            holder = scanner.nextLine();
            if (holder.equals("y")) {
                cancelresponse = true;
                break;
            } else if (holder.equals("n")) {
                cancelresponse = false;
                break;
            } else {
                System.out.println("Please enter y or n");
            }
        }
        if (cancelresponse) {
            //has (menu)
            insertString = "delete from has where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate(insertString);
            //staffed by
            insertString = "delete from staffby where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate(insertString);
            //event
            insertString = "delete from event where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate(insertString);
            //reservation
            insertString = "delete from reservation where reservationid = " + resId;
            System.out.println(insertString);
            statement.executeUpdate(insertString);
        } else {
            System.out.println("Reservation was not canceled, returning to main menu.");
        }
    }

    public static void runOptionFour(Scanner scanner, Statement statement) throws SQLException {

        java.sql.ResultSet rs = statement.executeQuery("select reservationid from has");
        ArrayList<String> reservations = new ArrayList<>();
        while (rs.next()) {
            reservations.add(rs.getString(1));
        }
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println(i + " : " + reservations.get(i));
        }

        System.out.println("Please select the corresponding number of the reservation " +
                "of which menu you would like to modify.");
        int response;
        while (true) {
            String input = scanner.nextLine();
            try {
                response = Integer.parseInt(input);
                if (response < 0 || response >= reservations.size()) {
                    System.out.println("Please enter a valid number.");
                } else {
                    break;
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }
        String reservation = reservations.get(response);

        rs = statement.executeQuery("select menuid from has where reservationid = '" + reservation + "'");
        rs.next();
        String menuid = rs.getString(1);

        rs = statement.executeQuery("select name from contains where menuid = '" + menuid + "'");
        ArrayList<String> dishes = new ArrayList<>();
        while (rs.next()) {
            dishes.add(rs.getString(1));
        }

        System.out.println("Would you like to:");
        System.out.println("1: add an item");
        System.out.println("2: remove an item");
        System.out.println("3: replace an item");
        System.out.println("4: view menu");
        int action;
        while (true) {
            String input = scanner.nextLine();
            try {
                action = Integer.parseInt(input);
                if (action < 0 || action > 4) {
                    System.out.println("Please enter a valid number.");
                } else {
                    break;
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        System.out.println("The dishes in this menu are:");
        for (int i = 0; i < dishes.size(); i++) {
            System.out.println(i + " : " + dishes.get(i));
        }

        switch (action) {
            case 1: { //add menu item
                while (true) {
                    System.out.println("Which dish name you would like to add");
                    scanner.nextLine();
                    String dishName = scanner.nextLine();
                    rs = statement.executeQuery("select name from dish where dish.name = '" + dishName + "'");
                    if (!rs.next()) { //loop if dish doesn't exist
                        System.out.println("Please insert an existing dish!");
                        continue;
                    }
                    statement.executeUpdate("insert into contains values (" + menuid + ", '" + dishName + "')");
                    statement.executeUpdate("update menu set numcourses = numcourses + 1 where menuid = " + menuid);
                    break;
                }
                return;
            }
            case 2: { //remove menu item
                while (true) {
                    System.out.println("Which item number would you like to remove?");
                    int item;
                    while (true) {
                        String input = scanner.nextLine();
                        try {
                            item = Integer.parseInt(input);
                            break;
                        } catch (Exception NumberFormatException) {
                            System.out.println("Please enter a valid number.");
                        }
                    }
                    String dish = dishes.get(item);
                    rs = statement.executeQuery("select '" + dish + "' from contains where menuid = " + menuid);
                    if (!rs.next()) {
                        System.out.println("Please select a dish in the menu!");
                        continue;
                    }
                    statement.executeUpdate("delete from contains where menuid = " + menuid + " and name = '" + dish + "'");
                    statement.executeUpdate("update menu set numcourses = numcourses - 1 where menuid = " + menuid);
                    break;
                }
                return;
            }
            case 3: { //replace menu item
                String dishRemove;
                String dishAdd;
                while (true) {
                    System.out.println("Which item number would you like to replace?");
                    int item;
                    while (true) {
                        String input = scanner.nextLine();
                        try {
                            item = Integer.parseInt(input);
                            break;
                        } catch (Exception NumberFormatException) {
                            System.out.println("Please enter a valid number.");
                        }
                    }
                    dishRemove = dishes.get(item);
                    rs = statement.executeQuery("select '" + dishRemove + "' from contains where menuid = " + menuid);
                    if (!rs.next()) {
                        System.out.println("Please select a dish in the menu!");
                        continue;
                    }
                    break;
                }
                while (true) {
                    System.out.println("Please enter the dish name that you like to add in its place.");
                    scanner.nextLine();
                    dishAdd = scanner.nextLine();
                    rs = statement.executeQuery("select name from dish where dish.name = '" + dishAdd + "'");
                    if (!rs.next()) { //loop if dish doesn't exist
                        System.out.println("Please insert an existing dish!");
                        continue;
                    }
                    break;
                }
                statement.executeUpdate("update contains set name = '" + dishAdd + "' where menuid = " + menuid + " and name = '" + dishRemove + "'");
                return;
            }
            case 4: //do nothing after printing menu
        }
    }

    public static void runOptionFive(Scanner scanner, Statement statement) {
        String holder;
        int response;
        while(true){
            System.out.println("Please select the type of invoice that you would like to make.");
            System.out.println("1: Ingredients purchase");
            System.out.println("2: ReservationPayment");
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response == 1){
                    createIngredientInvoice(scanner, statement);
                    break;
                } else if(response == 2){
                    createReservationInvoice(scanner, statement);
                    break;
                } else {
                    System.out.println("Please select a valid option.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please select a number from the list below.");
            }
        }
        System.out.println("Returning to the main menu.");
    }

    private static void createIngredientInvoice(Scanner scanner, Statement statement) throws SQLException {
        String querySQL = "select acctid from accountpayable";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<Integer> accts = new ArrayList<>();
        while (rs.next()) {
            accts.add(rs.getInt(1));
        }
        int response;
        String holder;
        while(true){
            System.out.println("Please select an account from the following list.");
            for(int i = 0; i < accts.size(); i ++){
                System.out.println(i + " : " + accts.get(i));
            }
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response >= 0 && response < accts.size()) break;
                else System.out.println("Please enter a number from the list below.");
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a number from the list below.");
            }
        }
        int accountId = accts.get(response);
        boolean rejectedcheck;
        while(true){
            System.out.println("Who would you like to make this invoice to?");
            System.out.println("1: A supplier with an outstanding rejected invoice with this account.");
            System.out.println("2: A supplier associated with this account");
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response == 1){
                    System.out.println(response);
                    rejectedcheck = true;
                    break;
                } else if(response == 2){
                    rejectedcheck = false;
                    break;
                } else {
                    System.out.println("Please select a valid option.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please select a number from the list below.");
            }
        }

        if(rejectedcheck){
            querySQL = "select suppliername from invoice where descriptionofservices = 'ingredients purchase' AND status = 'rejected' AND acctid = " + accountId;
        } else {
            querySQL = "select suppliername from invoice where descriptionofservices = 'ingredients purchase' AND acctid = " + accountId;
        }
        System.out.println(querySQL);
        rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }

        while(true){
            for(int i = 0; i < addresses.size(); i++){
                System.out.println(i + " : " + addresses.get(i));
            }
            System.out.println("Please select the supplier by entering the corresponding number");
            holder = scanner.nextLine();
            try {
                response = Integer.parseInt(holder);
                if (response >= 0 && response < addresses.size()) {
                    System.out.println(addresses.get(response));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        String supplier = addresses.get(response);

        while(true){
            System.out.println("Please enter the amount for the invoice.");
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response < 5000){
                    break;
                } else {
                    System.out.println("That amount exceeds the accepted limit for a single invoice.");
                    System.out.println("Please enter another amount.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        int amount = response;

        String today = "" + java.time.LocalDateTime.now();
        today = today.substring(0, 10);

        Random x = new Random();
        int invoiceid = x.nextInt(10000) + 30000;


        querySQL = "insert into invoice Values( '" + today + "' , " + amount + " , 'ingredients purchase' , " + invoiceid + " , 'pending' , null , '" + supplier + "')";
        System.out.println(querySQL);
        statement.executeUpdate(querySQL);

    }

    private static void createReservationInvoice(Scanner scanner, Statement statement) throws SQLException {
        String querySQL = "select acctid from accountreceivable";
        java.sql.ResultSet rs = statement.executeQuery(querySQL);
        ArrayList<Integer> accts = new ArrayList<>();
        while (rs.next()) {
            accts.add(rs.getInt(1));
        }
        int response;
        String holder;
        while(true){
            System.out.println("Please select an account from the following list.");
            for(int i = 0; i < accts.size(); i ++){
                System.out.println(i + " : " + accts.get(i));
            }
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response >= 0 && response < accts.size()) break;
                else System.out.println("Please enter a number from the list below.");
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a number from the list below.");
            }
        }
        int accountId = accts.get(response);
        boolean rejectedcheck;
        while(true){
            System.out.println("Who would you like to make this invoice to?");
            System.out.println("1: A customer with an outstanding rejected invoice with this account.");
            System.out.println("2: A customer associated with this account");
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response == 1){
                    System.out.println(response);
                    rejectedcheck = true;
                    break;
                } else if(response == 2){
                    rejectedcheck = false;
                    break;
                } else {
                    System.out.println("Please select a valid option.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please select a number from the list below.");
            }
        }

        if(rejectedcheck){
            querySQL = "select clientemail from invoice where descriptionofservices = 'reservation payment' AND status = 'rejected' AND acctid = " + accountId;
        } else {
            querySQL = "select clientemail from invoice where descriptionofservices = 'reservation payment' AND acctid = " + accountId;
        }
        System.out.println(querySQL);
        rs = statement.executeQuery(querySQL);
        ArrayList<String> addresses = new ArrayList<>();
        while (rs.next()) {
            addresses.add(rs.getString(1));
        }

        while(true){
            for(int i = 0; i < addresses.size(); i++){
                System.out.println(i + " : " + addresses.get(i));
            }
            System.out.println("Please select the clients email by entering the corresponding number");
            holder = scanner.nextLine();
            try {
                response = Integer.parseInt(holder);
                if (response >= 0 && response < addresses.size()) {
                    System.out.println(addresses.get(response));
                    break;
                } else {
                    System.out.println("Please enter a valid number.");
                }
            } catch (Exception NumberFormatException) {
                System.out.println("Please enter a valid number.");
            }
        }

        String clientmail = addresses.get(response);

        while(true){
            System.out.println("Please enter the amount for the invoice.");
            holder = scanner.nextLine();
            try{
                response = Integer.parseInt(holder);
                if(response < 5000){
                    break;
                } else {
                    System.out.println("That amount exceeds the accepted limit for a single invoice.");
                    System.out.println("Please enter another amount.");
                }
            } catch (Exception NumberFormatException){
                System.out.println("Please enter a valid number.");
            }
        }

        int amount = response;

        String today = "" + java.time.LocalDateTime.now();
        today = today.substring(0, 10);

        Random x = new Random();
        int invoiceid = x.nextInt(10000) + 30000;


        querySQL = "insert into invoice Values( '" + today + "' , " + amount + " , 'ingredients purchase' , " + invoiceid + " , 'pending' , '" + clientmail + "' , null)";
        System.out.println(querySQL);
        statement.executeUpdate(querySQL);
    }


    public static void printArrayList(ArrayList a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.println((i) + " : " + a.get(i));
        }
    }
}
