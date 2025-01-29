package com.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserInterface {
	private JFrame frame;
	private JPanel buttonPanel;
	private JButton scheduleButton;
	private JButton optimizeButton;
	private JButton historyButton;
	private JButton estimateCostButton;
	private JButton visualizeMapButton;
	private JButton logoutButton;
	private UserAuth user;

	private List<Delivery> deliveries;

	public UserInterface(UserAuth user) {
		this.user = user;
		this.deliveries = new ArrayList<>();
		initialize();
	}

	private void initialize() {
		// Frame setup
		frame = new JFrame("OptiRoute – A solution for optimizing routes for efficient delivery");
		frame.setSize(800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Title Label
		JLabel titleLabel = new JLabel("OptiRoute – A solution for optimizing routes for efficient delivery",
				JLabel.CENTER);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		frame.add(titleLabel, BorderLayout.NORTH);

		// Button Panel setup
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 2, 15, 15)); // Organizing buttons in a 3x2 grid with padding
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

		// Initialize buttons with icons for a modern look
		scheduleButton = createButton("Schedule Delivery", "C:\\Users\\admin\\Downloads\\1483142.png");
		optimizeButton = createButton("Optimize Route", "C:\\Users\\admin\\Downloads\\optiroute.png");
		historyButton = createButton("View History", "C:\\Users\\admin\\Downloads\\route.png");
		estimateCostButton = createButton("Estimate Cost", "C:\\Users\\admin\\Downloads\\cost.jpg");
		visualizeMapButton = createButton("Visualize Map", "C:\\Users\\admin\\Downloads\\map.png");
		logoutButton = createButton("Logout", "C:\\Users\\admin\\Downloads\\DaaProimg1.jpg");

		// Add buttons to the panel
		buttonPanel.add(scheduleButton);
		buttonPanel.add(optimizeButton);
		buttonPanel.add(historyButton);
		buttonPanel.add(estimateCostButton);
		buttonPanel.add(visualizeMapButton);
		buttonPanel.add(logoutButton);

		// Adding action listeners for buttons
		scheduleButton.addActionListener(e -> scheduleDelivery());
		optimizeButton.addActionListener(e -> collectInputData());
		historyButton.addActionListener(e -> showHistory());
		estimateCostButton.addActionListener(e -> estimateCost());
		visualizeMapButton
				.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Map visualization feature coming soon!"));
		logoutButton.addActionListener(e -> logout());

		// Add button panel to the center of the frame
		frame.add(buttonPanel, BorderLayout.CENTER);

		frame.setVisible(true);
	}

	// Helper method to create a button with text and icon
	private JButton createButton(String text, String iconPath) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.setFocusPainted(false);

		// Set an icon if the file path is valid
		ImageIcon icon = new ImageIcon(iconPath);
		if (icon.getIconWidth() > 0) {
			Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Scale icon to fit
																								// button
			button.setIcon(new ImageIcon(scaledImage));
		}

		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBackground(new Color(240, 240, 240)); // Light background for a modern look
		button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)), // Light
																														// border
				BorderFactory.createEmptyBorder(10, 10, 10, 10) // Padding inside button
		));
		button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover

		return button;
	}

	private void collectInputData() {
		JTextField startField = new JTextField();
		JTextField endField = new JTextField();
		JTextField costField = new JTextField();
		JTextField numRoutesField = new JTextField();

		Object[] message = { "Enter number of routes:", numRoutesField, "Start Location:", startField, "End Location:",
				endField, "Cost (comma-separated):", costField };

		int option = JOptionPane.showConfirmDialog(frame, message, "Optimize Route", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			int numRoutes = Integer.parseInt(numRoutesField.getText());
			RouteOptimizer routeOptimizer = new RouteOptimizer();

			String[] costs = costField.getText().split(",");

			// Collect routes from the user
			for (int i = 0; i < numRoutes; i++) {
				String from = JOptionPane.showInputDialog("Enter starting location " + (i + 1) + ":");
				String to = JOptionPane.showInputDialog("Enter destination location " + (i + 1) + ":");
				double cost = Double.parseDouble(costs[i]);
				routeOptimizer.addRoute(from, to, cost);
			}

			// Calculate optimized routes
			List<String> path = new ArrayList<>();
			Map<String, Double> optimizedCosts = routeOptimizer.calculateShortestPath(startField.getText(),
					endField.getText(), path);
			double optimizedCost = optimizedCosts.get(endField.getText());

			if (optimizedCost == Double.MAX_VALUE) {
				JOptionPane.showMessageDialog(frame,
						"No available path from " + startField.getText() + " to " + endField.getText());
			} else {
				JOptionPane.showMessageDialog(frame,
						"Optimized route: " + String.join(" -> ", path) + "\nOptimized cost: $" + optimizedCost);
			}
		}
	}

	private void scheduleDelivery() {
		JTextField destinationField = new JTextField();
		JTextField dateField = new JTextField();
		JTextField instructionsField = new JTextField();

		Object[] message = { "Delivery Destination:", destinationField, "Delivery Date (YYYY-MM-DD):", dateField,
				"Special Instructions:", instructionsField };

		int option = JOptionPane.showConfirmDialog(frame, message, "Schedule Delivery", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			String destination = destinationField.getText();
			String date = dateField.getText();
			String instructions = instructionsField.getText();
			Delivery newDelivery = new Delivery(destination, date, instructions);
			deliveries.add(newDelivery);
			JOptionPane.showMessageDialog(frame, "Delivery Scheduled: Delivery to " + destination + " on " + date
					+ ". Instructions: " + instructions);
		}
	}

	private void showHistory() {
		if (deliveries.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No deliveries scheduled yet.");
			return;
		}

		StringBuilder history = new StringBuilder("Scheduled Deliveries:\n");
		for (Delivery delivery : deliveries) {
			history.append(delivery).append("\n");
		}
		JOptionPane.showMessageDialog(frame, history.toString());
	}

	private void estimateCost() {
		if (deliveries.isEmpty()) {
			JOptionPane.showMessageDialog(frame, "No deliveries to estimate.");
			return;
		}

		double totalCost = deliveries.size() * 45.67;
		JOptionPane.showMessageDialog(frame, "Total Estimated Cost: $" + totalCost);
	}

	private void logout() {
		user.logout();
		JOptionPane.showMessageDialog(frame, "Logged out successfully.");
		System.exit(0);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			UserAuth userAuth = new UserAuth();
			UserInterface ui = new UserInterface(userAuth);
		});
	}

	// Inner class to represent a Delivery
	private static class Delivery {
		private String destination;
		private String date;
		private String instructions;

		public Delivery(String destination, String date, String instructions) {
			this.destination = destination;
			this.date = date;
			this.instructions = instructions;
		}

		@Override
		public String toString() {
			return "To: " + destination + ", Date: " + date + ", Instructions: " + instructions;
		}
	}
}
