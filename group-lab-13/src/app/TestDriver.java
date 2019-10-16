package app;

public class TestDriver {
	public static void main(String[] args) {
		BinaryTreeNode<Double> tree = BinaryTreeNode.put(null, 1.0, "1");
		BinaryTreeNode.put(tree, 2.0, "2");
		BinaryTreeNode.put(tree, 3.0, "3");
		BinaryTreeNode.put(tree, 4.0, "4");
		BinaryTreeNode.put(tree, 5.0, "5");

		BinaryTreeNode<Double> tree1 = BinaryTreeNode.put(null, 10.0, "1");
		BinaryTreeNode<Double> left = new BinaryTreeNode<Double>(9.0, "2");
		BinaryTreeNode<Double> right = new BinaryTreeNode<Double>(9.0, "2");
		tree1.setLeftChild(left);
		tree1.setRightChild(right);
		BinaryTreeNode<Double> left1 = new BinaryTreeNode<Double>(9.0, "2");
		BinaryTreeNode<Double> left2 = new BinaryTreeNode<Double>(9.0, "2");
		BinaryTreeNode<Double> right3 = new BinaryTreeNode<Double>(9.0, "2");
		BinaryTreeNode<Double> right4 = new BinaryTreeNode<Double>(9.0, "2");

		left.setLeftChild(left1);
		left.setRightChild(left2);
		right.setLeftChild(right3);
		right.setRightChild(right4);

		// The following insert is out of order
		// BinaryTreeNode.put(tree, 0.5, "1/2");

		// System.out.println(tree.heapOrdered());
		System.out.println(tree.balanced());
		System.out.println(tree1.balanced());

		// Look up 4.0 in the tree
		// System.out.println("get 4.0 = " + BinaryTreeNode.get(tree, 4.0));

		// Do an inorder walk and print the keys (will be in alphabetical order)
		// tree.printInorder();

		// Print the whole tree out
		// tree.printTree(0);
	}
}