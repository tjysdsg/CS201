
package app;

public class Problem_4_4_35 {
    public static void main(String[] args) {
        BinaryTreeNode<Double> tree = BinaryTreeNode.put(null, 1.0, "1");
        BinaryTreeNode.put(tree, 2.0, "2");
        BinaryTreeNode.put(tree, 3.0, "3");
        BinaryTreeNode.put(tree, 4.0, "4");
        BinaryTreeNode.put(tree, 5.0, "5");

        BinaryTreeNode<Double> tree1 = BinaryTreeNode.put(null, 10.0, "1");
        BinaryTreeNode<Double> left = new BinaryTreeNode<Double>(9.0, "2");
        BinaryTreeNode<Double> right = new BinaryTreeNode<Double>(9.0, "3");
        tree1.setLeftChild(left);
        tree1.setRightChild(right);
        BinaryTreeNode<Double> left1 = new BinaryTreeNode<Double>(8.5, "4");
        BinaryTreeNode<Double> left2 = new BinaryTreeNode<Double>(8.0, "5");
        BinaryTreeNode<Double> right3 = new BinaryTreeNode<Double>(7.0, "6");
        BinaryTreeNode<Double> right4 = new BinaryTreeNode<Double>(6.0, "7");
        left.setLeftChild(left1);
        left.setRightChild(left2);
        right.setLeftChild(right3);
        right.setRightChild(right4);

        tree.printTree();
        System.out.println("");
        System.out.println("Is heap ordered: " + tree.heapOrdered());
        System.out.println("");
        System.out.println("======");
        System.out.println("");

        tree1.printTree();
        System.out.println("");
        System.out.println("Is heap ordered: " + tree1.heapOrdered());
        System.out.println("");
        System.out.println("======");
        System.out.println("");
    }
}