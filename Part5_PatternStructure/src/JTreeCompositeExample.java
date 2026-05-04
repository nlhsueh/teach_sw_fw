import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeCompositeExample {
    public static void main(String[] args) {
        // Building the hierarchy using DefaultMutableTreeNode
        // (which implements TreeNode and provides Composite functionality)
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
        DefaultMutableTreeNode parent1 = new DefaultMutableTreeNode("Parent 1");
        DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child 1");
        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child 2");
        DefaultMutableTreeNode parent2 = new DefaultMutableTreeNode("Parent 2");
        DefaultMutableTreeNode child3 = new DefaultMutableTreeNode("Child 3");

        // Composite structure
        root.add(parent1);
        root.add(parent2);
        parent1.add(child1);
        parent1.add(child2);
        parent2.add(child3);

        // JTree (Client) uses the structure
        JTree tree = new JTree(root);

        JFrame frame = new JFrame("JTree Composite Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(tree));
        frame.setSize(300, 200);
        frame.setVisible(true);
    }
}
