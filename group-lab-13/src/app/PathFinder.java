package app;

import java.util.*;

/******************************************************************************
 * Compilation: javac PathFinder.java Execution: java Pathfinder input.txt
 * delimiter source Dependencies: ArrayDeque.java Graph.java Data files:
 * https://introcs.cs.princeton.edu/java/45graph/routes.txt
 * https://introcs.cs.princeton.edu/java/45graph/movies.txt
 * 
 * Runs breadth first search algorithm from source s on a graph G. After
 * preprocessing the graph, can process shortest path queries from s to any
 * vertex t.
 *
 * % java PathFinder routes.txt " " JFK LAX JFK ORD PHX LAX distance 3 MCO JFK
 * MCO distance 1 DFW JFK ORD DFW distance 2
 *
 ******************************************************************************/

public class PathFinder {

	// prev[v] = previous vertex on shortest path from s to v
	// dist[v] = length of shortest path from s to v
	private HashMap<String, String> prev = new HashMap<String, String>();
	private HashMap<String, Integer> dist = new HashMap<String, Integer>();

	// run BFS in graph G from given source vertex s
	public PathFinder(Graph G, String s) {

		// put source on the queue
		ArrayDeque<String> queue = new ArrayDeque<String>();
		queue.add(s);
		dist.put(s, 0);

		// repeated remove next vertex v from queue and insert
		// all its neighbors, provided they haven't yet been visited
		while (!queue.isEmpty()) {
			String v = queue.pop();
			for (String w : G.adjacentTo(v)) {
				if (!dist.containsKey(w)) {
					queue.add(w);
					dist.put(w, 1 + dist.get(v));
					prev.put(w, v);
				}
			}
		}
	}

	// is v reachable from the source s?
	public boolean hasPathTo(String v) {
		return dist.containsKey(v);
	}

	// return the length of the shortest path from v to s
	public int distanceTo(String v) {
		if (!hasPathTo(v))
			return Integer.MAX_VALUE;
		return dist.get(v);
	}

	// return the shortest path from v to s as an Iterable
	public Iterable<String> pathTo(String v) {
		ArrayDeque<String> path = new ArrayDeque<String>();
		while (v != null && dist.containsKey(v)) {
			path.push(v);
			v = prev.get(v);
		}
		return path;
	}

	public static void main(String[] args) {
		// args = new String[] { "routes.txt", " ", "LAX" };
		// args = new String[] { "movies.txt", "/", "Zhang, Ziyi" };
		String filename = args[0];
		String delimiter = args[1];
		Graph G = new Graph(filename, delimiter);
		String s = args[2];
		PathFinder pf = new PathFinder(G, s);
		while (!StdIn.isEmpty()) {
			String t = StdIn.readLine();
			for (String v : pf.pathTo(t)) {
				StdOut.println("   " + v);
			}
			StdOut.println("distance " + pf.distanceTo(t));
		}
	}

}
