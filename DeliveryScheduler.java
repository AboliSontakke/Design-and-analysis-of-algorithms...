package com.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryScheduler {

    public void scheduleDelivery() {
        int numberOfDeliveries = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of deliveries:"));
        List<String> deliveryTimes = new ArrayList<>();

        for (int i = 0; i < numberOfDeliveries; i++) {
            String time = JOptionPane.showInputDialog("Enter delivery time for delivery " + (i + 1) + " (HH:MM):");
            deliveryTimes.add(time);
        }

        // Display scheduled deliveries
        StringBuilder schedule = new StringBuilder("Scheduled Deliveries:\n");
        for (int i = 0; i < deliveryTimes.size(); i++) {
            schedule.append("Delivery ").append(i + 1).append(": ").append(deliveryTimes.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(null, schedule.toString());
    }
}
