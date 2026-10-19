package com.gabasa.ui;

import com.gabasa.dao.ScenarioDao;
import com.gabasa.model.ScenarioEntity;

import java.util.List;
import java.util.Scanner;

public class ScenarioMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final ScenarioDao dao = new ScenarioDao();

    public void show(){
        boolean running = true;
        while(running){
            System.out.println("\n==================================================");
            System.out.println("            CAR RENTAL FLEET TRACKER");
            System.out.println("==================================================");
            System.out.println("[1] Add New Vehicle");
            System.out.println("[2] View Fleet Vehicles");
            System.out.println("[3] Exit System");
            System.out.println("==================================================");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice){
                case "1" -> addVehicle();
                case "2" -> viewFleet();
                case "3" -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1, 2, or 3.");
            }
        }
    }

    public void addVehicle() {
        System.out.println("\n--- ADD NEW VEHICLE ---");
        String model, licensePlate, status;
        double dailyRate;

        while (true) {
            System.out.print("Enter Model Name: ");
            model = scanner.nextLine().trim();
            if (!model.isEmpty()) {
                break;
            }
            System.out.println("Model name cannot be empty.");
        }

        while (true) {
            System.out.print("Enter License Plate Number: ");
            licensePlate = scanner.nextLine().trim().toUpperCase();
            if (!licensePlate.isEmpty()) {
                break;
            }
            System.out.println("License plate cannot be empty.");
        }

        while (true) {
            System.out.print("Enter Daily Rate: ");
            try {
                dailyRate = Double.parseDouble(scanner.nextLine().trim());
                if (dailyRate > 0) {
                    break;
                }
                System.out.println("Rate must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        while (true) {
            System.out.print("Enter Status (AVAILABLE / RENTED): ");
            status = scanner.nextLine().trim().toUpperCase();
            if (status.equals("AVAILABLE") || status.equals("RENTED")) {
                break;
            }
            System.out.println("Status must be AVAILABLE or RENTED.");
        }

        ScenarioEntity vehicle = new ScenarioEntity(0, model, licensePlate, dailyRate, status);
        if (dao.insertVehicles(vehicle)) {
            System.out.println("\nSUCCESS: Vehicle registered to fleet!");
        } else {
            System.out.println("\nERROR: Vehicle was not saved (plate number may already exist).");
        }
    }

    public void viewFleet() {
        List<ScenarioEntity> fleet = dao.findAll();

        System.out.println("\n========================================================================");
        System.out.println("                         RENTAL FLEET VEHICLES");
        System.out.println("========================================================================");
        System.out.println("+----+--------------------+------------+--------------+----------------+");
        System.out.printf("| %-2s | %-18s | %-10s | %-12s | %-14s |%n",
                "ID", "MODEL NAME", "PLATE NO", "DAILY RATE", "STATUS");
        System.out.println("+----+--------------------+------------+--------------+----------------+");

        for (ScenarioEntity v : fleet) {
            System.out.printf("| %-2d | %-18s | %-10s | %-12.2f | %-14s |%n",
                    v.id(), v.model_name(), v.plate_no(), v.daily_rate(), v.status());
        }

        System.out.println("+----+--------------------+------------+--------------+----------------+");
        System.out.println("Total Records Displayed: " + fleet.size());
    }

}
