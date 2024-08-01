import java.util.NoSuchElementException;

class SelfBalancingBinarySearchTree {

    private class Node {
        int key;
        int height;
        int size;
        Node left, right;

        Node(int key) {
            this.key = key;
            this.height = 1;
            this.size = 1;
        }
    }

    private Node root;

    public boolean find(int key) {
        return find(root, key);
    }

    private boolean find(Node node, int key) {
        if (node == null) {
            return false;
        }
        if (key < node.key) {
            return find(node.left, key);
        } else if (key > node.key) {
            return find(node.right, key);
        } else {
            return true;
        }
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node;
        }

        return balance(node);
    }

    public void remove(int key) {
        root = remove(root, key);
    }

    private Node remove(Node node, int key) {
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = remove(node.right, node.key);
            }
        }

        if (node == null) {
            return null;
        }

        return balance(node);
    }

    private Node mostLeftChild(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private Node balance(Node node) {
        updateHeightAndSize(node);
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }

        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeightAndSize(y);
        updateHeightAndSize(x);

        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeightAndSize(x);
        updateHeightAndSize(y);

        return y;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    private void updateHeightAndSize(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        node.size = size(node.left) + size(node.right) + 1;
    }

    private int getBalanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    public int order_of_key(int key) {
        return order_of_key(root, key);
    }

    private int order_of_key(Node node, int key) {
        if (node == null) {
            return 0;
        }
        if (key < node.key) {
            return order_of_key(node.left, key);
        } else if (key > node.key) {
            return size(node.left) + 1 + order_of_key(node.right, key);
        } else {
            return size(node.left);
        }
    }

    public int get_by_order(int k) {
        if (k >= size(root) || k < 0) {
            throw new NoSuchElementException("Order is out of bounds");
        }
        return get_by_order(root, k).key;
    }

    private Node get_by_order(Node node, int k) {
        int leftSize = size(node.left);
        if (k < leftSize) {
            return get_by_order(node.left, k);
        } else if (k > leftSize) {
            return get_by_order(node.right, k - leftSize - 1);
        } else {
            return node;
        }
    }

    public static void main(String[] args) {
        SelfBalancingBinarySearchTree tree = new SelfBalancingBinarySearchTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(6);
        tree.insert(15);

        System.out.println(tree.find(10));
        System.out.println(tree.find(100)); 

        System.out.println(tree.order_of_key(15)); 
        System.out.println(tree.get_by_order(2));

        tree.remove(10);
        System.out.println(tree.find(10)); 
        System.out.println(tree.get_by_order(2)); 
    }
}
