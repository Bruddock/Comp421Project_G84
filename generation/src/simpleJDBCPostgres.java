/**
 * Make sure the Postgresql JDBC driver is in your classpath.
 * You can download the JDBC 4 driver from here if required.
 * https://jdbc.postgresql.org/download.html
 *
 * take care of the variables usernamestring and passwordstring to use 
 * appropriate database credentials before you compile !
 *
**/

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


class simpleJDBC
{
    public static void main ( String [ ] args ) throws SQLException
    {
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
String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
Connection con = DriverManager.getConnection (url,"cs421g84", "reduce2084") ;
//Connection con = DriverManager.getConnection (url,"bruddo", "x@i&nr*o") ;
Statement statement = con.createStatement ( ) ;

	// Creating a table
//	try {
//	    String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
//	    System.out.println (createSQL ) ;
//	    statement.executeUpdate (createSQL ) ;
//	    System.out.println ("DONE");
//	}catch (SQLException e)
//            {
//                sqlCode = e.getErrorCode(); // Get SQLCODE
//                sqlState = e.getSQLState(); // Get SQLSTATE
//
//                // Your code to handle errors comes here;
//                // something more meaningful than a print would be good
//                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
//            }

	// Inserting Data into the table

//		genAcct("account",sqlCode, sqlState, statement);
//		genCustomer("customer",sqlCode, sqlState, statement);
//		genDish("dish",sqlCode, sqlState, statement);
//		gendrink("drink","dish",sqlCode, sqlState, statement);
//		genvenue("venue", sqlCode, sqlState, statement);
//		genMenu("menu",sqlCode, sqlState, statement);
//		genStaff("staff", sqlCode, sqlState, statement);
//		genSupplier("supplier", sqlCode, sqlState, statement);
//		genspecaccts("account", sqlCode, sqlState, statement);

//		genReservation("reservation", sqlCode, sqlState, statement);
//		genEvent("reservation", "event", sqlCode, sqlState, statement);
//		geninvoice("invoice", sqlCode, sqlState, statement);

//		genidstring( "reserves", "customer", "reservation", sqlCode, sqlState, statement,"reservationid", "emailaddress"); //id string
		//		genpayment("reservationpayment", "invoice", "accountreceivable", "customer", sqlCode, sqlState, statement, "emailaddress", "acctid", "invoiceid");
//		genpayment("supplypayment", "invoice", "accountpayable", "supplier", sqlCode, sqlState, statement, "companyname", "acctid", "invoiceid");
//		geniddatetime("initiate", "event", "reservation", sqlCode, sqlState, statement, "reservationid", "edate", "etime", "reservationid");
//		genstringdatetime("located", "event", "venue", sqlCode, sqlState, statement, "address", "edate", "etime", "reservationid");

//		genidstring( "contains", "dish", "menu", sqlCode, sqlState, statement, "menuid", "name"); //id string

		//generate salary amount
//		gensalary("salary", "staff", "accountpayable", sqlCode, sqlState, statement, "acctid", "employeeid"); //id id
//		gentwoidtemplate("runs", "account", "staff", sqlCode, sqlState, statement, "employeeid", "acctid"); //id id
//		gentwoidtemplate("prepares", "staff", "menu", sqlCode, sqlState, statement, "menuid", "employeeid"); //id id

		geningreds("ingredients", "dish", "supplier", sqlCode, sqlState, statement, "companyname", "name");

//		geniddatetime("has", "event", "menu", sqlCode, sqlState, statement, "menuid", "edate", "etime", "reservationid");
//		genstaffby("staffby", "event", "staff", sqlCode, sqlState, statement, "employeeid", "edate", "etime", "reservationid");

		// Querying a table
//	try {
//	    String querySQL = "SELECT id, name from " + tableName + " WHERE NAME = \'Vicki\'";
//	    System.out.println (querySQL) ;
//	    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
//	    while ( rs.next ( ) ) {
//		int id = rs.getInt ( 1 ) ;
//		String name = rs.getString (2);
//		System.out.println ("id:  " + id);
//		System.out.println ("name:  " + name);
//	    }
//	    System.out.println ("DONE");
//	} catch (SQLException e)
//	    {
//		sqlCode = e.getErrorCode(); // Get SQLCODE
//		sqlState = e.getSQLState(); // Get SQLSTATE
//
//		// Your code to handle errors comes here;
//		// something more meaningful than a print would be good
//		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
//	    }

	//Updating a table
//	try {
//	    String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
//	    System.out.println(updateSQL);
//	    statement.executeUpdate(updateSQL);
//	    System.out.println("DONE");
//
//	    // Dropping a table
////	    String dropSQL = "DROP TABLE " + tableName;
////	    System.out.println ( dropSQL ) ;
////	    statement.executeUpdate ( dropSQL ) ;
////	    System.out.println ("DONE");
//	} catch (SQLException e)
//	    {
//		sqlCode = e.getErrorCode(); // Get SQLCODE
//		sqlState = e.getSQLState(); // Get SQLSTATE
//
//		// Your code to handle errors comes here;
//		// something more meaningful than a print would be good
//		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
//	    }


	// Finally but importantly close the statement and connection
	statement.close ( ) ;
	con.close ( ) ;
    }

    static void genCustomer(String tableName, int sqlCode, String sqlState, Statement statement){
		try{
    	String insertSQL;
		for(int i=0; i<100; i++){
			insertSQL = "INSERT INTO " + tableName + " VALUES (";
//			'2013-05-15', '2018-06-08', 'C.Krebs@fakemail.com', 6475550311, 'Charlene Krebs'
			Random x = new Random();
			int year = 2013 + x.nextInt(7);
			int month = 1 + x.nextInt(12);
			int day = 1 + x.nextInt(28);
			insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
			year = 2013 + x.nextInt(7);
			month = 1 + x.nextInt(12);
			day = 1 + x.nextInt(28);
			insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
			String email = "\'C" + i + "@fakemail.com\' , ";
			insertSQL += email;
			String phone = "647555" + (x.nextInt(9000) + 1000);
			insertSQL += phone + " , ";
			String name = "\'Customer" + i + "\'";
			insertSQL += name + " )";
			System.out.println ( insertSQL ) ;
			statement.executeUpdate ( insertSQL ) ;
			System.out.println ( "DONE" ) ;
		}

		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genAcct (String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			for(int i=0; i<10; i++){
				Random x = new Random();
				insertSQL = "INSERT INTO " + tableName + " VALUES (" + x.nextInt(1000) + ")";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genReservation(String tableName, int sqlCode, String sqlState, Statement statement){
		try {
			//need to get the customer email address to put into the final spot
			String querySQL = "SELECT emailaddress from customer";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				commands.add(rs.getString(1));
			}

			String insertSQL;
			for(int i=0; i<100; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				int year = 2013 + x.nextInt(7);
				int month = 1 + x.nextInt(12);
				int day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				int hour = x.nextInt(24);
				int minute = x.nextInt(60);
				int second = x.nextInt(60);
				insertSQL += "\'" + hour + ":" + minute + ":" + second + "\', ";
				insertSQL += (x.nextInt(10000) + 3000) + ",";
				insertSQL += "\'" + commands.get(x.nextInt(commands.size())) + "\')";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}

	}

	static void genMenu (String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			for(int i=0; i<14; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				Random x = new Random();
				insertSQL += x.nextInt(5) + ",";
				insertSQL += x.nextInt(1000) + ",";
				if(x.nextInt(2) == 1) insertSQL += "\'yes\')";
				else insertSQL += "\'no\')";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genDish (String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			for(int i=0; i<50; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				Random x = new Random();
				insertSQL += "\'dish" + i + "\',";
				int hour = x.nextInt(5);
				int minute = x.nextInt(60);
				int second = x.nextInt(60);
				insertSQL += "\'" + hour + ":" + minute + ":" + second + "\', ";
				int check = x.nextInt(4);
				switch (check) {
					case 0:
						insertSQL += "\'vegetarian\',";
						break;
					case 1:
						insertSQL += "\'vegan\',";
						break;
					case 2:
						insertSQL += "\'gluten free\',";
						break;
					case 3:
						insertSQL += "\'none\',";
						break;
				}
				if(x.nextInt(2) == 1) insertSQL += "\'yes\',";
				else insertSQL += "\'no\',";
				if(x.nextInt(2) == 1) insertSQL += "\'true\')";
				else insertSQL += "\'false\')";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genStaff(String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			for(int i=0; i<50; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				Random x = new Random();
				int check = x.nextInt(4);
				switch (check) {
					case 0:
						insertSQL += "\'waitstaff\',";
						break;
					case 1:
						insertSQL += "\'chef\',";
						break;
					case 2:
						insertSQL += "\'accountant\',";
						break;
					case 3:
						insertSQL += "\'manager\',";
						break;
				}
				int year = 2013 + x.nextInt(7);
				int month = 1 + x.nextInt(12);
				int day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				insertSQL += "\'staff" + i + "\',";
				insertSQL += x.nextInt(1000) + ")";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genSupplier(String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			for(int i=0; i<30; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				Random x = new Random();
				if(x.nextInt(2 ) == 1) insertSQL += "\'true\' , ";
				else insertSQL += "\'false\' , ";
				insertSQL += "\'Company" + i + "\' , ";
				String address = "\'" + i + " street av\' )";
				insertSQL += address;
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genEvent(String tableName1, String tableName, int sqlCode, String sqlState, Statement statement){

    	//need to get the venue locations to generated the located in spot 8
		//need to get the reservationid from reservation to generate spot 9


    	try {

			String querySQL = "SELECT address from venue";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> locations = new ArrayList();
			while ( rs.next ( ) ) {
				locations.add(rs.getString(1));
			}

			String insertSQL;
			querySQL = "SELECT reservationid from " + tableName1 ;
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				//cooperate
				if(x.nextInt(2) == 1) insertSQL += "\'true\' , ";
				else insertSQL += "\'false\' , ";
				//staff amount
				insertSQL += (x.nextInt(10) + 1) + " , ";
				//time
				int hour = x.nextInt(5);
				int minute = x.nextInt(60);
				int second = x.nextInt(60);
				insertSQL += "\'" + hour + ":" + minute + ":" + second + "\', ";
				//mature
				if(x.nextInt(2) == 1) insertSQL += "\'true\' , ";
				else insertSQL += "\'false\' , ";
				//event descript
				switch(x.nextInt(4)){
					case 0: insertSQL += "\'party\' , ";
							break;
					case 1: insertSQL += "\'wedding\' , ";
							break;
					case 2: insertSQL += "\'wake\' , ";
							break;
					case 3: insertSQL += "\'conferences\' , ";
							break;

				}
				//date
				int year = 2013 + x.nextInt(7);
				int month = 1 + x.nextInt(12);
				int day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				//attendee amount
				insertSQL += x.nextInt(100) + " , ";
				insertSQL += "\'" + locations.get(x.nextInt(locations.size())) + "\', ";
				insertSQL += rs.getInt ( 1 ) + ")";
				System.out.println ( insertSQL ) ;
				commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}

		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genspecaccts(String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			String querySQL = "SELECT acctid from " + tableName + " where acctid > 400";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				insertSQL = "INSERT INTO " + tableName + "payable" + " VALUES (";
				//budget amount
				insertSQL += x.nextInt(10000) + " , ";
				//acctid
				int id = rs.getInt ( 1 ) ;
				insertSQL += id + ")";
				System.out.println ( insertSQL ) ;
				commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}
			querySQL = "SELECT acctid from " + tableName + " where acctid < 400";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			commands = new ArrayList();
			while ( rs.next ( ) ) {
				insertSQL = "INSERT INTO " + tableName + "receivable" + " VALUES (";
				//budget amount
				insertSQL += x.nextInt(10000) + " , ";
				//acctid
				int id = rs.getInt ( 1 ) ;
				insertSQL += id + ")";
				System.out.println ( insertSQL ) ;
				commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}

		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void gendrink (String tableName1, String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			String querySQL = "SELECT name from " + tableName + " where name > \'dish\'";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				if(x.nextInt(4) == 1) {
					insertSQL = "INSERT INTO " + tableName1 + " VALUES (";
					String name = rs.getString(1);
					insertSQL += "\'" + name + "\'";
					//mature
					if (x.nextInt(2) == 1) insertSQL += " , \'true\'";
					else insertSQL += " , \'false\'";
					insertSQL += ")";
					System.out.println(insertSQL);
					commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
					System.out.println("DONE");
				}
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genvenue(String tableName, int sqlCode, String sqlState, Statement statement){
		try {

			String insertSQL;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			for(int i=0; i<50; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				String address = "\'" + i + " street av\'";
				insertSQL += address;
				//kitchen
				if(x.nextInt(2) == 1) insertSQL += " , \'true\'";
				else insertSQL += " , \'false\'";
				//tableware
				if(x.nextInt(2) == 1) insertSQL += " , \'true\'";
				else insertSQL += " , \'false\'";
				//rentid
				if(x.nextInt(2) == 1) insertSQL += " , \'true\'";
				else insertSQL += " , \'false\'";
				insertSQL += " )";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}

	}

	static void geninvoice(String tableName, int sqlCode, String sqlState, Statement statement) {
		//need to get client emails for spot 8 OR
		//need to get supplier name for spot 9
		//need to use some acct id from correct set of accounts

    	try {

			String querySQL = "SELECT emailaddress from customer";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> emails = new ArrayList();
			while ( rs.next ( ) ) {
				emails.add("\'" + rs.getString(1) + "\'");
			}

			querySQL = "SELECT companyname from supplier";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			ArrayList<String> suppliers = new ArrayList();
			while ( rs.next ( ) ) {
				suppliers.add("\'" + rs.getString(1) + "\'");
			}

			querySQL = "SELECT acctid from accountreceivable";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			ArrayList<Integer> ingoing = new ArrayList();
			while ( rs.next ( ) ) {
				ingoing.add(rs.getInt(1));
			}

			querySQL = "SELECT acctid from accountpayable";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			ArrayList<Integer> outgoing = new ArrayList();
			while ( rs.next ( ) ) {
				outgoing.add(rs.getInt(1));
			}

			String insertSQL;
			for(int i=0; i<450; i++){
				insertSQL = "INSERT INTO " + tableName + " VALUES (";
				//date
				int year = 2013 + x.nextInt(7);
				int month = 1 + x.nextInt(12);
				int day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				//amount
				insertSQL += x.nextInt(10000) + ",";

				//seller/buyer
				int check = x.nextInt(2);
				if( check == 0){
					insertSQL += "\'reservation payment\' ,";
//
				} else {
					insertSQL += "\'ingredients purchase\' ,";
				}
				//invoice id
				insertSQL += (x.nextInt(10000) + 10000) + ",";
				//status
				switch(x.nextInt(4)){
					case 0:
						insertSQL += "\'pending\' ,";
						break;
					case 1:
						insertSQL += "\'rejected\' ,";
						break;
					case 2:
						insertSQL += "\'accepted\' ,";
						break;
					case 3:
						insertSQL += "\'under review\' ,";
						break;
				}

				if(check == 1){ //outgoing
					insertSQL += "NULL, ";
					insertSQL += suppliers.get(x.nextInt(suppliers.size())) + ", ";
					insertSQL += outgoing.get(x.nextInt(outgoing.size()));
				} else { //incoming
					insertSQL += emails.get(x.nextInt(emails.size())) + ", ";
					insertSQL += "NULL, ";
					insertSQL += ingoing.get(x.nextInt(ingoing.size()));
				}

				insertSQL += " )";
				System.out.println ( insertSQL ) ;
				statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genidstring(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2){
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName + " where " + id1 + " > 0";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();

			ArrayList<Integer> menus = new ArrayList<>();
			while(rs.next ()){
				menus.add(rs.getInt(1));
			}

			querySQL = "SELECT "+ id2 +" from " + tableName1 + " where " + id2 + " > \'C\'";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;

			ArrayList<String> dishes = new ArrayList<>();
			while(rs.next ()){
				dishes.add(rs.getString(1));
			}


			ArrayList<String> commands = new ArrayList();
			for(int i = 0; i< 100; i++) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += menus.get(x.nextInt(menus.size()))+ " , ";
				insertSQL += "\'" + dishes.get(x.nextInt(dishes.size())) + "\')";
				System.out.println(insertSQL);

				commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
//				System.out.println ( "DONE" ) ;
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void gentwoidtemplate(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2){
		try {


			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName;
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();

			ArrayList<Integer> ids1 = new ArrayList<>();
			while(rs.next ()){
				ids1.add(rs.getInt(1));
			}

			querySQL = "SELECT "+ id2 +" from " + tableName1;
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;

			ArrayList<Integer> ids2 = new ArrayList<>();
			while(rs.next ()){
				ids2.add(rs.getInt(1));
			}

			ArrayList<String> commands = new ArrayList();
			for(int i = 0; i < 100; i++) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += ids1.get(x.nextInt(ids1.size())) + " , ";
				insertSQL += ids2.get(x.nextInt(ids2.size())) + ")";
				commands.add(insertSQL);
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void gensalary(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2){
		try {


			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName;
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();

			ArrayList<Integer> ids1 = new ArrayList<>();
			while(rs.next ()){
				ids1.add(rs.getInt(1));
			}

			querySQL = "SELECT "+ id2 +" from " + tableName1;
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;

			ArrayList<Integer> ids2 = new ArrayList<>();
			while(rs.next ()){
				ids2.add(rs.getInt(1));
			}

			ArrayList<String> commands = new ArrayList();
			for(int i = 0; i < ids2.size(); i++) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += ids1.get(x.nextInt(ids1.size())) + " , ";
				insertSQL += ids2.get(i) + ", ";
				insertSQL += (x.nextInt(15000) + 20000) + " ) ";
				commands.add(insertSQL);
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genpayment(String tableName3, String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2, String id3) {
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName + " where "+ id1 + " > \'C\'";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				insertSQL = "INSERT INTO " + tableName3 + " VALUES (";
				//budget amount
				insertSQL += "\'" + rs.getString(1) + "\' , ";
				System.out.println ( insertSQL ) ;
				commands.add(insertSQL);
//			statement.executeUpdate ( insertSQL ) ;
//				System.out.println ( "DONE" ) ;
			}
			querySQL = "SELECT " + id2 + " from " + tableName1 + " where " + id2 + " > 0";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
//		commands = new ArrayList();
			int spot = 0;
			while ( rs.next ( ) ) {
				if(spot != commands.size()) {
					String temp = commands.get(spot);
					temp +=  rs.getInt(1) + " , ";
					System.out.println(temp);
					commands.set(spot, temp);
					spot++;
				}
//			statement.executeUpdate ( insertSQL ) ;
//				System.out.println ( "DONE" ) ;
			}
			querySQL = "SELECT " + id3 + " from " + tableName2 + " where " + id3 + " > 0";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
//		commands = new ArrayList();
			spot = 0;
			while ( rs.next ( ) ) {
				if(spot != commands.size()) {
					String temp = commands.get(spot);
					temp +=  rs.getInt(1) + ")";
					System.out.println(temp);
					commands.set(spot, temp);
					spot++;
				}
//			statement.executeUpdate ( insertSQL ) ;
				System.out.println ( "DONE" ) ;
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void geningreds(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2){
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName + " where "+ id1 + " > \'a\'";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> company = new ArrayList<>();
			while ( rs.next ( ) ) {
				company.add(rs.getString(1));
			}

			querySQL = "SELECT "+ id2 +" from " + tableName1 + " where "+ id2 + " > \'a\'";
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
			ArrayList<String> dishname = new ArrayList<>();
			while ( rs.next ( ) ) {
				dishname.add(rs.getString(1));
			}

			ArrayList<String> commands = new ArrayList();

			for(int i = 0; i<400; i++) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += "\'" + company.get(x.nextInt(company.size())) + "\' , ";
				insertSQL +=  "\'" + dishname.get(x.nextInt(dishname.size())) + "\' , ";
				//date
				int year = 2013 + x.nextInt(7);
				int month = 1 + x.nextInt(12);
				int day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				//date
				year = 2013 + x.nextInt(7);
				month = 1 + x.nextInt(12);
				day = 1 + x.nextInt(28);
				insertSQL += "\'" + year + "-" + month + "-" + day + "\', ";
				insertSQL += x.nextInt(100) + " , ";
				switch(x.nextInt(22)){
					case 0:
						insertSQL += "\'Tofu\')";
						break;
					case 1:
						insertSQL += "\'Beyond Meat\')";
						break;
					case 2:
						insertSQL += "\'Pork\')";
						break;
					case 3:
						insertSQL += "\'Chicken\')";
						break;
					case 4:
						insertSQL += "\'Beef\')";
						break;
					case 5:
						insertSQL += "\'Fish\')";
						break;
					case 6:
						insertSQL += "\'Shellfish\')";
						break;
					case 7:
						insertSQL += "\'Potato\')";
						break;
					case 8:
						insertSQL += "\'Rice\')";
						break;
					case 9:
						insertSQL += "\'Olive Oil\')";
						break;
					case 10:
						insertSQL += "\'Produce\')";
						break;
					case 11:
						insertSQL += "\'Legumes\')";
						break;
					case 12:
						insertSQL += "\'Leafy Greens\')";
						break;
					case 13:
						insertSQL += "\'Pasta\')";
						break;
					case 14:
						insertSQL += "\'Eggs\')";
						break;
					case 15:
						insertSQL += "\'Beer\')";
						break;
					case 16:
						insertSQL += "\'Cider\')";
						break;
					case 17:
						insertSQL += "\'Wine\')";
						break;
					case 18:
						insertSQL += "\'Flour\')";
						break;
					case 19:
						insertSQL += "\'Nuts\')";
						break;
					case 20:
						insertSQL += "\'Spices\')";
						break;
					case 21:
						insertSQL += "\'Fruits\')";
						break;
				}
				commands.add(insertSQL);
			}
			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void geniddatetime(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2, String id3, String id4){
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName + " where " + id1 + " > 0";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<Integer> ids = new ArrayList<>();
			while(rs.next()){
				ids.add(rs.getInt(1));
			}
			ArrayList<String> dates = new ArrayList<>();
			ArrayList<String> times = new ArrayList<>();
			ArrayList<String> resids = new ArrayList<>();
			querySQL = "SELECT "+ id2 + "," + id3 + ","+ id4 +" from " + tableName1;
			rs = statement.executeQuery ( querySQL ) ;
			while(rs.next()){
				dates.add(rs.getString(1));
				times.add(rs.getString(2));
				resids.add(rs.getString(3));
			}


			ArrayList<String> commands = new ArrayList();
			for(int i = 0; i < 250; i++) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += ids.get(x.nextInt(ids.size())) + " , ";
				insertSQL += "\'" + dates.get(i)+ "\' ,";
				insertSQL += "\'" + times.get(i) + "\' ,";
				insertSQL += "\'" + resids.get(i) + "\' )";
				commands.add(insertSQL);
			}

			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genstaffby(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2, String id3, String id4){
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName + " where role =\'waitstaff\'";
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<Integer> ids = new ArrayList<>();
			while(rs.next()){
				ids.add(rs.getInt(1));
			}
			ArrayList<String> dates = new ArrayList<>();
			ArrayList<String> times = new ArrayList<>();
			ArrayList<String> resids = new ArrayList<>();
			ArrayList<Integer> stafflim = new ArrayList<>();
			querySQL = "SELECT "+ id2 + "," + id3 + ","+ id4 + ", staffamount"  +" from " + tableName1;
			rs = statement.executeQuery ( querySQL ) ;
			while(rs.next()){
				dates.add(rs.getString(1));
				times.add(rs.getString(2));
				resids.add(rs.getString(3));
				stafflim.add(rs.getInt(4));
			}

			ArrayList<String> commands = new ArrayList();
			for(int i = 0; i < dates.size(); i++) {
				Collections.shuffle(ids);
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += "\'" + dates.get(i)+ "\' ,";
				insertSQL += "\'" + times.get(i) + "\' ,";
				insertSQL += "\'" + resids.get(i) + "\' ,";
				for(int j = 0; j< stafflim.get(i); j++){
					insertSQL += ids.get(j) + " ,";
				}
				for(int j = 0; j<(10-stafflim.get(i)); j++){
					insertSQL += " NULL ,";
				}
				insertSQL = insertSQL.substring(0, insertSQL.length() - 1) + ")";
				commands.add(insertSQL);
			}

			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

	static void genstringdatetime(String tableName2, String tableName1, String tableName, int sqlCode, String sqlState, Statement statement, String id1, String id2, String id3, String id4){
		try {

			String insertSQL;
			String querySQL = "SELECT "+ id1 +" from " + tableName;
			System.out.println (querySQL) ;
			java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			Random x = new Random();
			ArrayList<String> commands = new ArrayList();
			while ( rs.next ( ) ) {
				insertSQL = "INSERT INTO " + tableName2 + " VALUES (";
				//budget amount
				insertSQL += rs.getString(1) + " , ";
				System.out.println ( insertSQL ) ;
				commands.add(insertSQL);
			}

			querySQL = "SELECT "+ id2 + "," + id3 + ","+ id4 +" from " + tableName1;
			System.out.println (querySQL) ;
			rs = statement.executeQuery ( querySQL ) ;
//		commands = new ArrayList();
			int spot = 0;
			while ( rs.next ( ) ) {
				if(spot != commands.size()) {
					String temp = commands.get(spot);
					temp += "\'" + rs.getString(1) + "\' ,";
					temp += "\'" + rs.getString(2) + "\' ,";
					temp += "\'" + rs.getString(3) + "\' )";
					System.out.println(temp);
					commands.set(spot, temp);
					spot++;
				}
			}

			for(String s:commands){
				System.out.println ( s ) ;
				statement.executeUpdate ( s ) ;
				System.out.println ( "DONE" ) ;
			}


		} catch (SQLException e)
		{
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE

			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		}
	}

}

