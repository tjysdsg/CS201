package app;

public class BinaryTreeNode<T extends Comparable<T>> {
	private T key;
	private String value;
	private BinaryTreeNode<T> leftChild;
	private BinaryTreeNode<T> rightChild;

	public BinaryTreeNode(T key, String value) {
		this.setKey(key);
		this.setValue(value);
	}

	public BinaryTreeNode<T> getRightChild() {
		return rightChild;
	}

	public void setRightChild(BinaryTreeNode<T> rightChild) {
		this.rightChild = rightChild;
	}

	public BinaryTreeNode<T> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(BinaryTreeNode<T> leftChild) {
		this.leftChild = leftChild;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public static <T extends Comparable<T>> String get(BinaryTreeNode<T> x, T key) {
		if (x == null) {
			return null;
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return get(x.leftChild, key);
		} else if (cmp > 0) {
			return get(x.rightChild, key);
		} else {
			return x.value;
		}
	}

	public static <T extends Comparable<T>> BinaryTreeNode<T> put(BinaryTreeNode<T> x, T key, String value) {
		if (x == null) {
			return new BinaryTreeNode<T>(key, value);
		}

		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			x.leftChild = put(x.leftChild, key, value);
		} else if (cmp > 0) {
			x.rightChild = put(x.rightChild, key, value);
		} else {
			x.value = value;
		}
		return x;
	}

	public void printTree() {
		printTree(0);
	}

	public void printTree(int indentLevel) {
		for (int i = 0; i < indentLevel; i++) {
			System.out.print("  ");
		}
		System.out.println(this.key + " = " + this.value);

		for (int i = 0; i < indentLevel; i++) {
			System.out.print("  ");
		}
		System.out.println("left:" + (this.leftChild == null ? " null" : ""));
		if (this.leftChild != null) {
			this.leftChild.printTree(indentLevel + 1);
		}

		for (int i = 0; i < indentLevel; i++) {
			System.out.print("  ");
		}
		System.out.println("right:" + (this.rightChild == null ? " null" : ""));
		if (this.rightChild != null) {
			this.rightChild.printTree(indentLevel + 1);
		}
	}

	public void printInorder() {
		if (this.leftChild != null) {
			this.leftChild.printInorder();
		}
		System.out.println(this.key + " = " + this.value);
		if (this.rightChild != null) {
			this.rightChild.printInorder();
		}
	}

	public boolean balanced() {
		if (this.leftChild == null && this.rightChild == null) {
			return true;
		} else if (this.leftChild == null) {
			return this.rightChild.leftChild == null && this.rightChild.rightChild == null
					&& this.rightChild.balanced();
		} else if (this.rightChild == null) {
			return this.leftChild.leftChild == null && this.leftChild.rightChild == null && this.leftChild.balanced();
		} else {
			return this.leftChild.balanced() && this.rightChild.balanced();
		}
	}

	public boolean heapOrdered() {
		boolean left = true;
		boolean right = true;
		if (this.leftChild == null && this.rightChild == null) {
			return true;
		}
		if (this.leftChild != null) {
			left = this.leftChild.heapOrdered() && this.key.compareTo(this.leftChild.key) > 0;
		}
		if (this.rightChild != null) {
			right = this.rightChild.heapOrdered() && this.key.compareTo(this.rightChild.key) > 0;
		}
		return left && right;
	}
}