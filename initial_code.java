package com.badapp;

import java.util.*;
import java.sql.*;
import java.io.*;

public class OrderProcessor {
    
    public String processOrder(String orderType, double amount, String customerType, 
                               boolean isPremium, boolean isWeekend, String discountCode) {
        
        double a = amount;
        String b = orderType;
        String c = customerType;
        boolean d = isPremium;
        boolean e = isWeekend;
        String f = discountCode;
        
        double total = a;
        
        if (b.equals("STANDARD")) {
            if (c.equals("RETAIL")) {
                if (d) {
                    total = a * 0.9;
                } else {
                    total = a;
                }
            } else if (c.equals("WHOLESALE")) {
                if (a > 1000) {
                    total = a * 0.85;
                } else {
                    total = a * 0.95;
                }
            } else if (c.equals("INTERNATIONAL")) {
                total = a * 1.2;
                if (e) {
                    total = total * 1.05;
                }
            }
        } else if (b.equals("EXPRESS")) {
            total = a * 1.3;
            if (c.equals("WHOLESALE")) {
                total = total * 0.92;
            }
            if (d) {
                total = total * 0.88;
            }
        } else if (b.equals("BULK")) {
            if (a > 5000) {
                total = a * 0.75;
            } else if (a > 2000) {
                total = a * 0.85;
            } else {
                total = a * 0.95;
            }
        }
        
        if (f != null) {
            if (f.equals("SAVE10")) {
                total = total * 0.9;
            } else if (f.equals("SAVE20")) {
                total = total * 0.8;
            } else if (f.equals("BLACKFRIDAY")) {
                total = total * 0.7;
            } else if (f.equals("WELCOME")) {
                total = total * 0.85;
            }
        }
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "pass");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO orders (total, type, date) VALUES (?, ?, ?)");
            stmt.setDouble(1, total);
            stmt.setString(2, b);
            stmt.setDate(3, new java.sql.Date(new Date().getTime()));
            stmt.executeUpdate();
            conn.close();
            
            FileWriter fw = new FileWriter("orders.log", true);
            fw.write("Order processed: " + total + " for type " + b + "\n");
            fw.close();
            
            sendEmail(total, b);
            
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
        
        return "Order processed. Total: Q" + total;
    }
    
    private void sendEmail(double total, String type) {
        // Simulación de envío de email
        System.out.println("Sending email for order Q" + total + " type: " + type);
    }
    
    public static void main(String[] args) {
        OrderProcessor op = new OrderProcessor();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter order type:");
        String type = sc.nextLine();
        System.out.println("Enter amount:");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter customer type:");
        String custType = sc.nextLine();
        
        String result = op.processOrder(type, amount, custType, false, false, null);
        System.out.println(result);
        
        System.out.println("Testing: " + op.processOrder("STANDARD", 100, "RETAIL", true, false, "SAVE10"));
    }
}
