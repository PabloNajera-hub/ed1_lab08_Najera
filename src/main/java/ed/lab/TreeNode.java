package ed.lab;

public class TreeNode <T> {
    protected T value;
    protected TreeNode<T> left;
    protected TreeNode<T> right;
    protected int height; // Altura del subárbol
    protected int size;   // Número de nodos en el subárbol

    public TreeNode(T value) {
        this.value = value;
        this.height = 1;
        this.size = 1;
    }

    @Override
    public String toString() {
        return String.format("(%s) [left: %s] [right: %s]",
                value,
                left != null ? left.value : "null",
                right != null ? right.value : "null");
    }
}
