import java.sql.*;
import java.util.Scanner;

public class HotelManagementSystem {
    private static final String url="jdbc:mysql://localhost:3306/hotel_db";
    private static final String username="root";
    private static final String password="ekta";
    public static void main(String[] args) throws ClassNotFoundException{
        try{
           Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            Connection connection= DriverManager.getConnection(url,username,password);
            Statement statement=connection.createStatement();
            while (true){
                System.out.println("Hotel Reservation System");
                Scanner sc=new Scanner(System.in);
                System.out.println("1. Reserve a Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservations");
                System.out.println("0. Exit");
                System.out.println("Choose an Option");
                int choice=sc.nextInt();
                switch(choice){
                    case 1:
                        reserveRoom(connection,sc,statement);
                        break;
                    case 2:
                        viewReservations(connection,statement);
                        break;
                    case 3:
                        getRoom(connection,sc,statement);
                        break;
                    case 4:
                        updateReservation(connection,sc,statement);
                        break;
                    case 5:
                        deletereservations(connection,sc,statement);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice: Please try again");
                }
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void deletereservations(Connection connection, Scanner sc,Statement statement) {
        System.out.println("Enter the reservation Id to delete: ");
        int id=sc.nextInt();

        if(!reservationExists(statement,id)){
            System.out.println("No reservation for this Id");
            return;
        }

        String sql = "Delete from reservations WHERE reservation_id = " + id;
        try{
            int affectedRows=statement.executeUpdate(sql);
            if(affectedRows>0){
                System.out.println("Reservation deleted successfully");
            }else{
                System.out.println("Reservation deletion failed");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Connection connection, Scanner sc,Statement statement) {
        System.out.println("Enter the reservation Id to update: ");
        int id=sc.nextInt();

        if(!reservationExists(statement,id)){
            System.out.println("No reservation for this Id");
            return;
        }

        System.out.println("Enter the new guests name: ");
        String name=sc.next();
        sc.nextLine();
        System.out.println("Enter the new room number: ");
        int room=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter the new contact number: ");
        String number=sc.next();
        sc.nextLine();

        String sql = "UPDATE reservations SET guests_name = '" + name + "', room_number = " + room + ", contact_number = '" + number + "' WHERE reservation_id = " + id;
        try{
            int affectedRows=statement.executeUpdate(sql);
            if(affectedRows>0){
                System.out.println("Reservation updated successfully");
            }else{
                System.out.println("Reservation updated failed");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static boolean reservationExists(Statement statement, int id) {
        try{
            String sql = "select reservation_id from reservations WHERE reservation_id = " + id;
            ResultSet set=statement.executeQuery(sql);
            return set.next();
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    private static void getRoom(Connection connection, Scanner sc,Statement statement) {
        System.out.println("Enter reservation id: ");
        int id=sc.nextInt();
        System.out.println("Enter Guest name: ");
        String name=sc.next();
        sc.nextLine();

        String sql = "SELECT room_number FROM reservations WHERE reservation_id = " + id + " AND guests_name = '" + name + "';";
        try{
        ResultSet set=statement.executeQuery(sql);
        if(set.next()){
            int room=set.getInt("room_number");
            System.out.println("Your room number for reservation id: "+id+" and guest name: "+name+" is: "+room);
        }else{
            System.out.println("Reservation is not found for given id and name");
        }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservations(Connection connection,Statement statement) {
        String sql="select * from reservations;";
        try{
            ResultSet set=statement.executeQuery(sql);
            System.out.println("Current Reservations:");
            System.out.println("+-----------------+--------------------+-------------+-----------------+-------------------------+");
            System.out.println("| Reservation Id  | Guest Name         | Room Number | Contact Number  | Date                    |");
            System.out.println("+-----------------+--------------------+-------------+-----------------+-------------------------+");
            while(set.next()){
                String name=set.getString("guests_name");
                String number=set.getString("contact_number");
                String date=set.getTimestamp("reservation_date").toString();
                int room=set.getInt("room_number");
                int id=set.getInt("reservation_id");

                System.out.printf("| %-15d | %-18s | %-11d | %-15s | %-19s   |\n",
                        id,name,room,number,date);
            }
            System.out.println("+-----------------+--------------------+-------------+-----------------+-------------------------+");

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner sc,Statement statement) {
            System.out.println("Enter guest name: ");
            String guestName=sc.next();
            sc.nextLine();
            System.out.println("Enter room number: ");
            int roomNumber=sc.nextInt();
            System.out.println("Enter contact number: ");
            String contactNumber=sc.next();
            sc.nextLine();

            String sql="Insert into Reservations(guests_name,room_number,contact_number)"+
                    "Values ('"+guestName+"',"+roomNumber+",'"+contactNumber+"');";

            try{
                int affectedRows=statement.executeUpdate(sql);
                if(affectedRows>0){
                    System.out.println("Reservation Successfull");
                }
                else{
                    System.out.println("Reservation Failed");
                }

            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
    }
    private static void exit() throws InterruptedException{
        System.out.print("Exiting System");
        int i=5;
        while(i!=0){
            System.out.print(".");
            Thread.sleep(1000);
            i--;
        }
        System.out.println();
        System.out.println("Thank you for using Hotel reservation system");
    }
}