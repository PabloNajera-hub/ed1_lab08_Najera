package ed.lab;

import java.util.Comparator;
import ed.lab.TreeNode;
import java.lang.IllegalArgumentException;

public class E02AVLTree<T> {

    private final Comparator<T> comparator;
    private TreeNode<T> root;

    public E02AVLTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public void insert(T value) {

    }

    public void delete(T value) {

    }

    public T search(T value) {
        return null;
    }

    public int height() {
        return 0;
    }

    public int size() {
        return 0;
    }

    /**
     * Retorna el k-ésimo elemento más pequeño del árbol (1-based).
     * Lanza IllegalArgumentException si k es inválido.
     */
    public T kthSmallest(int k) {
        if (k < 1 || k > size()) {
            throw new IllegalArgumentException("k fuera de rango");
        }
        int[] count = {0};
        TreeNode<T>[] result = new TreeNode[1];
        kthSmallestInOrder(root, k, count, result);
        return result[0].value;
    }

    // Recorrido inorden para encontrar el k-ésimo elemento
    private void kthSmallestInOrder(TreeNode<T> node, int k, int[] count, TreeNode<T>[] result) {
        if (node == null || result[0] != null) return;
        kthSmallestInOrder(node.left, k, count, result);
        count[0]++;
        if (count[0] == k) {
            result[0] = node;
            return;
        }
        kthSmallestInOrder(node.right, k, count, result);
    }
}
