package codeAlpha;
import java.io.*;
import java.util.*;

class Room implements Serializable {
    int number;
    String category;
    double price;
    boolean booked;

    Room(int number, String category, double price) {
        this.number = number;
        this.category = category;
        this.price = price;
        this.booked = false;
    }

    public String toString() {
        return number + " | " + category + " | Rs." + price + " | "
                + (booked ? "Booked" : "Available");
    }
}

class Booking implements Serializable {
    String guestName;
    int roomNumber;
    String checkIn;
    String checkOut;
    double amount;
    boolean paid;

    Booking(String guestName, int roomNumber, String checkIn,
            String checkOut, double amount, boolean paid) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amount = amount;
        this.paid = paid;
    }

    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomNumber
                + " | " + checkIn + " to " + checkOut
                + " | Amount: Rs." + amount
                + " | " + (paid ? "Paid" : "Unpaid");
    }
}

class FileDatabase {
    static final String ROOM_FILE = "rooms.dat";
    static final String BOOK_FILE = "bookings.dat";

    @SuppressWarnings("unchecked")
    static List<Room> loadRooms() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(ROOM_FILE))) {
            return (List<Room>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<Room>();
        }
    }

    static void saveRooms(List<Room> rooms) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ROOM_FILE))) {
            oos.writeObject(rooms);
        } catch (Exception e) {
            System.out.println("Error saving rooms.");
        }
    }

    @SuppressWarnings("unchecked")
    static List<Booking> loadBookings() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(BOOK_FILE))) {
            return (List<Booking>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<Booking>();
        }
    }

    static void saveBookings(List<Booking> bookings) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(BOOK_FILE))) {
            oos.writeObject(bookings);
        } catch (Exception e) {
            System.out.println("Error saving bookings.");
        }
    }
}

public class HotelReservation {
    static Scanner sc = new Scanner(System.in);
    static List<Room> rooms = FileDatabase.loadRooms();
    static List<Booking> bookings = FileDatabase.loadBookings();

    public static void main(String[] args) {
        if (rooms.isEmpty()) setupRooms();

        System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
        int choice;
        do {
            System.out.println("\n1. View Rooms");
            System.out.println("2. Search & Book");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    showRooms();
                    break;
                case 2:
                    bookRoom();
                    break;
                case 3:
                    showBookings();
                    break;
                case 4:
                    cancelBooking();
                    break;
                case 5:
                    System.out.println("Thank you! Visit again.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    static void setupRooms() {
        rooms.add(new Room(101, "Standard", 2000));
        rooms.add(new Room(102, "Standard", 2000));
        rooms.add(new Room(201, "Deluxe", 3500));
        rooms.add(new Room(202, "Deluxe", 3600));
        rooms.add(new Room(301, "Suite", 5500));
        FileDatabase.saveRooms(rooms);
    }

    static void showRooms() {
        System.out.println("\n---- Room List ----");
        for (Room r : rooms) System.out.println(r);
    }

    static void bookRoom() {
        System.out.print("\nEnter category (Standard/Deluxe/Suite): ");
        String cat = sc.next();
        List<Room> available = new ArrayList<Room>();
        for (Room r : rooms)
            if (!r.booked && r.category.equalsIgnoreCase(cat))
                available.add(r);

        if (available.isEmpty()) {
            System.out.println("No rooms available in this category.");
            return;
        }

        System.out.println("Available Rooms:");
        for (Room r : available) System.out.println(r);

        System.out.print("Enter room number to book: ");
        int num = sc.nextInt();
        Room selected = null;
        for (Room r : rooms)
            if (r.number == num && !r.booked) selected = r;

        if (selected == null) {
            System.out.println("Invalid or already booked room.");
            return;
        }

        sc.nextLine(); // clear buffer
        System.out.print("Guest name: ");
        String guest = sc.nextLine();
        System.out.print("Check-in date (dd-mm-yyyy): ");
        String in = sc.next();
        System.out.print("Check-out date (dd-mm-yyyy): ");
        String out = sc.next();

        boolean paid = Math.random() > 0.1; // simulate payment success
        double amt = selected.price;

        if (paid) {
            selected.booked = true;
            bookings.add(new Booking(guest, num, in, out, amt, true));
            FileDatabase.saveRooms(rooms);
            FileDatabase.saveBookings(bookings);
            System.out.println("Booking successful. Payment received Rs." + amt);
        } else {
            System.out.println("Payment failed. Try again later.");
        }
    }

    static void showBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings yet.");
        } else {
            System.out.println("\n---- Booking List ----");
            for (Booking b : bookings) System.out.println(b);
        }
    }

    static void cancelBooking() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings to cancel.");
            return;
        }
        System.out.print("Enter guest name to cancel booking: ");
        String name = sc.next();
        Booking found = null;
        for (Booking b : bookings)
            if (b.guestName.equalsIgnoreCase(name)) found = b;

        if (found == null) {
            System.out.println("No booking found for " + name);
            return;
        }

        for (Room r : rooms)
            if (r.number == found.roomNumber) r.booked = false;

        bookings.remove(found);
        FileDatabase.saveRooms(rooms);
        FileDatabase.saveBookings(bookings);
        System.out.println("Booking cancelled and refund processed (if paid).");
    }
}
