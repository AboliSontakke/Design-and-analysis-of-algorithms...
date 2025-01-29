package com.model;

import java.util.*;

public class Graph {
	private final Map<String, List<Edge>> adjacencyList = new HashMap<>();

	public void addEdge(String source, String destination, double cost) {
		adjacencyList.putIfAbsent(source, new ArrayList<>());
		adjacencyList.putIfAbsent(destination, new ArrayList<>());
		adjacencyList.get(source).add(new Edge(destination, cost));
		adjacencyList.get(destination).add(new Edge(source, cost)); // For undirected graph
	}

	public List<String> dijkstra(String start, String end) {
		PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(edge -> edge.cost));
		Map<String, Double> distances = new HashMap<>();
		Map<String, String> previous = new HashMap<>();
		Set<String> visited = new HashSet<>();

		for (String vertex : adjacencyList.keySet()) {
			distances.put(vertex, Double.MAX_VALUE);
			previous.put(vertex, null);
		}
		distances.put(start, 0.0);
		queue.add(new Edge(start, 0.0));

		while (!queue.isEmpty()) {
			Edge currentEdge = queue.poll();
			String currentVertex = currentEdge.destination;

			if (visited.contains(currentVertex)) {
				continue;
			}
			visited.add(currentVertex);

			for (Edge neighbor : adjacencyList.get(currentVertex)) {
				double newDist = distances.get(currentVertex) + neighbor.cost;
				if (newDist < distances.get(neighbor.destination)) {
					distances.put(neighbor.destination, newDist);
					previous.put(neighbor.destination, currentVertex);
					queue.add(new Edge(neighbor.destination, newDist));
				}
			}
		}

		// Reconstruct path
		List<String> path = new ArrayList<>();
		for (String at = end; at != null; at = previous.get(at)) {
			path.add(at);
		}
		Collections.reverse(path); // Reverse path to get the correct order

		return path.size() > 1 ? path : Collections.emptyList(); // Return empty if no valid path
	}

	private static class Edge {
		String destination;
		double cost;

		Edge(String destination, double cost) {
			this.destination = destination;
			this.cost = cost;
		}
	}
}
