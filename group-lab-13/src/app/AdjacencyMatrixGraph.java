package app;

import java.util.List;

public class AdjacencyMatrixGraph {
	private boolean[][] matrix;
	private List<String> vertices;

	public AdjacencyMatrixGraph(String[] vertices) {
		this.vertices = List.of(vertices);
		this.matrix = new boolean[vertices.length][vertices.length];
	}

	public void addEdge(String start, String end) {
		int startIndex = this.vertices.indexOf(start);
		int endIndex = this.vertices.indexOf(end);
		this.matrix[startIndex][endIndex] = true;
	}

	public boolean isConnected(String start, String end) {
		int startIndex = this.vertices.indexOf(start);
		int endIndex = this.vertices.indexOf(end);
		return this.matrix[startIndex][endIndex];
	}
}