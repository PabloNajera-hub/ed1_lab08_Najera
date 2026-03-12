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
        // Inserta un valor en el árbol AVL
        root = insert(root, value);
    }

    private TreeNode<T> insert(TreeNode<T> node, T value) {
        if (node == null) {
            // Si el nodo es nulo, crea uno nuevo
            return new TreeNode<>(value);
        }
        int cmp = comparator.compare(value, node.value);
        if (cmp < 0) {
            node.left = insert(node.left, value);
        } else if (cmp > 0) {
            node.right = insert(node.right, value);
        } else {
            // No se permiten duplicados
            return node;
        }
        // Actualiza altura y tamaño
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);
        // Balancea el árbol
        int bf = height(node.left) - height(node.right);
        if (bf > 1) {
            if (comparator.compare(value, node.left.value) > 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (bf < -1) {
            if (comparator.compare(value, node.right.value) < 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    public void delete(T value) {
        // Elimina un valor del árbol AVL
        root = delete(root, value);
    }

    private TreeNode<T> delete(TreeNode<T> node, T value) {
        if (node == null) {
            return null;
        }
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
        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);
        int bf = height(node.left) - height(node.right);
        if (bf > 1) {
            if (height(node.left.left) < height(node.left.right)) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (bf < -1) {
            if (height(node.right.right) < height(node.right.left)) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }
        return node;
    }

    private TreeNode<T> getMin(TreeNode<T> node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public T search(T value) {
        // Busca un valor en el árbol AVL
        TreeNode<T> node = root;
        while (node != null) {
            int cmp = comparator.compare(value, node.value);
            if (cmp == 0) return node.value;
            if (cmp < 0) node = node.left;
            else node = node.right;
        }
        return null;
    }

    public int height() {
        // Retorna la altura del árbol
        return height(root);
    }

    private int height(TreeNode<T> node) {
        if (node == null) return 0;
        return node.height;
    }

    public int size() {
        // Retorna el tamaño del árbol
        return size(root);
    }

    private int size(TreeNode<T> node) {
        if (node == null) return 0;
        return node.size;
    }

    /**
     * Retorna el k-ésimo elemento más pequeño del árbol (1-based).
     * Lanza IllegalArgumentException si k es inválido.
     */
    private int contador;
    private T resultado;

    public T kthSmallest(int k) {
        contador = 0;
        resultado = null;
        kthSmallestInOrder(root, k);
        if (resultado == null) throw new IllegalArgumentException("k fuera de rango");
        return resultado;
    }

    // Recorrido inorden para encontrar el k-ésimo elemento
    private void kthSmallestInOrder(TreeNode<T> node, int k) {
        if (node == null) return;
        kthSmallestInOrder(node.left, k);
        contador++;
        if (contador == k) {
            resultado = node.value;
            return;
        }
        kthSmallestInOrder(node.right, k);
    }

    // Rotación simple a la derecha
    private TreeNode<T> rotateRight(TreeNode<T> y) {
        TreeNode<T> x = y.left;
        TreeNode<T> T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.size = 1 + size(y.left) + size(y.right);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    // Rotación simple a la izquierda
    private TreeNode<T> rotateLeft(TreeNode<T> x) {
        TreeNode<T> y = x.right;
        TreeNode<T> T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.size = 1 + size(x.left) + size(x.right);
        y.size = 1 + size(y.left) + size(y.right);
        return y;
    }
}
