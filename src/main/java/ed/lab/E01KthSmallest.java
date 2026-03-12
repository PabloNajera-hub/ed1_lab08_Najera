package ed.lab;

public class E01KthSmallest {

    public int kthSmallest(TreeNode<Integer> root, int k) {
        // Recorrido inorden iterativo para encontrar el k-ésimo menor
        java.util.Stack<TreeNode<Integer>> stack = new java.util.Stack<>();
        TreeNode<Integer> curr = root;
        int count = 0;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            count++;
            if (count == k) return curr.value;
            curr = curr.right;
        }
        throw new IllegalArgumentException("k fuera de rango");
    }

}