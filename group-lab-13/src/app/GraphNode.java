package app;

import java.util.HashSet;
import java.util.Iterator;

public class GraphNode<K> {
	private K key;
	private HashSet<GraphNode<K>> outboundEdges = new HashSet<>();

	public GraphNode(K key) {
		this.key = key;
	}

	public K getKey() {
		return this.key;
	}

	public void addEdge(GraphNode<K> node) {
		this.outboundEdges.add(node);
	}

	public Iterator<GraphNode<K>> outboundEdgeIterator() {
		return this.outboundEdges.iterator();
	}
}