import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Laptop {
    private String laptopId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Laptop(String laptopId, String brand, String model, double basePricePerDay) {
        this.laptopId = laptopId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getLaptopId() {
        return laptopId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnLaptop() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Laptop laptop;
    private Customer customer;
    private int days;

    public Rental(Laptop laptop, Customer customer, int days) {
        this.laptop = laptop;
        this.customer = customer;
        this.days = days;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class LaptopRentalSystem {
    private List<Laptop> laptops;
    private List<Customer> customers;
    private List<Rental> rentals;

    public LaptopRentalSystem() {
        laptops = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addLaptop(Laptop laptop) {
        laptops.add(laptop);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentLaptop(Laptop laptop, Customer customer, int days) {
        if (laptop.isAvailable()) {
            laptop.rent();
            rentals.add(new Rental(laptop, customer, days));

        } else {
            System.out.println("Laptop is not available for rent.");
        }
    }

    public void returnLaptop(Laptop laptop) {
        laptop.returnLaptop();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getLaptop() == laptop) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);

        } else {
            System.out.println("Laptop was not rented.");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("===== Laptop Rental System =====");
            System.out.println("1. Rent a  Laptop");
            System.out.println("2. Return a Laptop");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (choice == 1) {
                System.out.println("\n== Rent a Laptop ==\n");
                System.out.print("Enter your name: ");
                String customerName = scanner.nextLine();

                System.out.println("\nAvailable Laptop:");
                for (Laptop laptop : laptops) {
                    if (laptop.isAvailable()) {
                        System.out.println(laptop.getLaptopId() + " - " + laptop.getBrand() + " " + laptop.getModel());
                    }
                }

                System.out.print("\nEnter the Laptop ID you want to rent: ");
                String laptopId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Laptop selectedLaptop = null;
                for (Laptop laptop : laptops) {
                    if (laptop.getLaptopId().equals(laptopId) && laptop.isAvailable()) {
                        selectedLaptop = laptop;
                        break;
                    }
                }

                if (selectedLaptop != null) {
                    double totalPrice = selectedLaptop.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Laptop: " + selectedLaptop.getBrand() + " " + selectedLaptop.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("Y")) {
                        rentLaptop(selectedLaptop, newCustomer, rentalDays);
                        System.out.println("\nLaptop rented successfully.");
                    } else {
                        System.out.println("\nRental cancelled.");
                    }
                } else {
                    System.out.println("\nInvalid Laptop selection or Laptop not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Laptop ==\n");
                System.out.print("Enter the Laptop ID you want to return: ");
                String laptopId = scanner.nextLine();

                Laptop laptopToReturn = null;
                for (Laptop laptop : laptops) {
                    if (laptop.getLaptopId().equals(laptopId) && !laptop.isAvailable()) {
                        laptopToReturn = laptop;
                        break;
                    }
                }

                if (laptopToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getLaptop() == laptopToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnLaptop(laptopToReturn);
                        System.out.println("Laptop returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Laptop was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid Laptop ID or Laptop is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Laptop Rental System!");
    }

}
public class Main1{
    public static void main(String[] args) {
        LaptopRentalSystem rentalSystem = new LaptopRentalSystem();

        Laptop laptop1 = new Laptop("l001", "lenovo(ideapad slim)", "Touchpad", 600.0); // Different base price per day for each car
        Laptop laptop2 = new Laptop("l002", "hp rayzen", "gaming pro", 700.0);
        Laptop laptop3 = new Laptop("l003", "dell advanced", "Coding pro", 650.0);
        rentalSystem.addLaptop(laptop1);
        rentalSystem.addLaptop(laptop2);
        rentalSystem.addLaptop(laptop3);

        rentalSystem.menu();
    }
}
