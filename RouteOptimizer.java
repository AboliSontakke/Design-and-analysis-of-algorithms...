package com.model;

import javax.swing.*;
import java.util.*;

public class RouteOptimizer {
	private Map<String, List<Edge>> graph;

	public RouteOptimizer() {
		graph = new HashMap<>();
	}

	// Method to add a route with cost to the graph
	public void addRoute(String from, String to, double cost) {
		graph.putIfAbsent(from, new ArrayList<>());
		graph.putIfAbsent(to, new ArrayList<>());
		graph.get(from).add(new Edge(to, cost));
		graph.get(to).add(new Edge(from, cost)); // Assuming undirected graph
	}

	// Dijkstra's algorithm to find the shortest path
	public Map<String, Double> calculateShortestPath(String start, String end, List<String> path) {
		Map<String, Double> distances = new HashMap<>();
		Map<String, String> previousNodes = new HashMap<>();
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.cost));

		// Initialize distances
		for (String vertex : graph.keySet()) {
			distances.put(vertex, Double.MAX_VALUE);
		}
		distances.put(start, 0.0);
		priorityQueue.add(new Edge(start, 0));

		while (!priorityQueue.isEmpty()) {
			Edge current = priorityQueue.poll();
			String currentDestination = current.destination;

			for (Edge neighbor : graph.get(currentDestination)) {
				double newDist = distances.get(currentDestination) + neighbor.cost;
				if (newDist < distances.get(neighbor.destination)) {
					distances.put(neighbor.destination, newDist);
					previousNodes.put(neighbor.destination, currentDestination);
					priorityQueue.add(new Edge(neighbor.destination, newDist));
				}
			}
		}

		// Retrieve path from previousNodes map
		String step = end;
		path.clear(); // Clear any previous data in path
		if (distances.get(end) != Double.MAX_VALUE) { // Path exists
			while (step != null) {
				path.add(0, step); // Add to front of path
				step = previousNodes.get(step);
			}
		}
		return distances;
	}

	// Inner class for Edge
	private static class Edge {
		String destination;
		double cost;

		Edge(String destination, double cost) {
			this.destination = destination;
			this.cost = cost;
		}
	}
}
