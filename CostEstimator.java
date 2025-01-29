package com.model;

import javax.swing.*;

public class CostEstimator {
    public void estimateCost() {
        String distanceInput = JOptionPane.showInputDialog("Enter total distance (miles):");
        double distance = Double.parseDouble(distanceInput);
        double fuelCostPerMile = 2.5; // Example cost

        double totalCost = distance * fuelCostPerMile;
        JOptionPane.showMessageDialog(null, "Estimated cost for the route: $" + totalCost);
    }
}
