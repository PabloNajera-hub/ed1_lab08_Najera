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
        root = insert(root, value);
    }

    private TreeNode<T> insert(TreeNode<T> node, T value) {
        if (node == null) return new TreeNode<>(value);
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            return node; // No duplicados
        }
        update(node);
        return balance(node);
    }

    public void delete(T value) {
        root = delete(root, value);
    }

    private TreeNode<T> delete(TreeNode<T> node, T value) {
        if (node == null) return null;
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = delete(node.left, value);
        } else if (cmp > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            TreeNode<T> min = getMin(node.right);
            node.value = min.value;
            node.right = delete(node.right, min.value);
        }
        update(node);
        return balance(node);
    }

    private TreeNode<T> getMin(TreeNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public T search(T value) {
        TreeNode<T> node = root;
        while (node != null) {
            int cmp = comparator.compare(value, node.value);
            if (cmp == 0) return node.value;
            node = cmp < 0 ? node.left : node.right;
        }
        return null;
    }

    public int height() {
        return height(root);
    }

    private int height(TreeNode<T> node) {
        return node == null ? 0 : node.height;
    }

    public int size() {
        return size(root);
    }

    private int size(TreeNode<T> node) {
        return node == null ? 0 : node.size;
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

    // --- AVL helpers ---
    private void update(TreeNode<T> node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);
    }

    private int balanceFactor(TreeNode<T> node) {
        return height(node.left) - height(node.right);
    }

    private TreeNode<T> balance(TreeNode<T> node) {
        int bf = balanceFactor(node);
        if (bf > 1) {
            if (balanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (bf < -1) {
            if (balanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        update(y);
        update(x);
        return x;
    }

    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        update(x);
        update(y);
        return y;
    }
}
