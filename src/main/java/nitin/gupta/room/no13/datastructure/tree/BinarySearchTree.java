package nitin.gupta.room.no13.datastructure.tree;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

@NoArgsConstructor
public class BinarySearchTree <T extends Comparable<T>> {
    private Tree<T> root;

    @ToString
    @EqualsAndHashCode
    static class Tree<T extends Comparable<T>> {
        T data;
        Tree<T> left, right,parent;
        Tree(T data ,Tree<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }
    public void insert(T data) {
        if (root == null) {
            root = new Tree<>(data ,null);
        }else {
            insertRec(root, data);
        }
    }

    private void insertRec(Tree<T> root, T data) {
        if (data.compareTo(root.data) < 0) {
            if (root.left == null) {
                root.left = new Tree<>(data, root);
            } else {
                insertRec(root.left, data);
            }
        } else if (data.compareTo(root.data) > 0) {
            if (root.right == null) {
                root.right = new Tree<>(data, root);
            } else {
                insertRec(root.right, data);
            }
        }
    }

    public void delete(T data) {
        root = deleteRecursive(root, data);
    }

    // Recursive helper method
    private Tree<T> deleteRecursive(Tree<T> current, T data) {
        if (current == null) {
            return null; // Element not found
        }
        int cmp = data.compareTo(current.data);
        if (cmp < 0) {
            // Traverse left
            current.left = deleteRecursive(current.left, data);
        } else if (cmp > 0) {
            // Traverse right
            current.right = deleteRecursive(current.right, data);
        } else {
            // Node to delete found!
            // Case 1 & 2: Node has 0 or 1 child
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            // Case 3: Node has 2 children
            // Find the minimum value in the right subtree (In-Order Successor)
            current.data = findMin(current.right);

            // Delete the successor node from the right subtree
            current.right = deleteRecursive(current.right, current.data);
        }

        return current;
    }
    private T findMin(Tree<T> node) {
        T minVal = node.data;
        while (node.left != null) {
            minVal = node.left.data;
            node = node.left;
        }
        return minVal;
    }
    public T search(T data) {
        Tree<T> current = root;
        while (current != null) {
            if (current.data.compareTo(data) == 0) {
                return current.data;
            }
            current = current.left;
        }
        return null;
    }
    public T minimum() {
        Tree<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }
    public  T maximum() {
        Tree<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    public  int height() {
        return heightRec(root);
    }
    private int heightRec(Tree<T> node) {
        if (node == null) {
            return -1;
        }
        int leftHeight = heightRec(node.left);
        int rightHeight = heightRec(node.right);
        return Math.max(leftHeight, rightHeight) + 1;
    }
    public  int size() {
        return sizeRec(root);
    }
    private int sizeRec(Tree<T> node) {
        if (node == null) {
            return 0;
        }
        return sizeRec(node.left) + sizeRec(node.right) + 1;
    }
    //Left->root->right
    public void inOrderTraversal() {
        Tree<T> current = root;
        while (current != null) {
            if (current.left != null) current = current.left;
            System.out.println(current.data);
            if (current.right != null) current = current.right;
        }

    }
    //Root ->Left $ -> Right
    public void preOrderTraversal() {
        Tree<T> current = root;
        while (current != null) {
            System.out.println(current.data);
            if (current.left != null) current = current.left;
            if (current.right != null) current = current.right;
        }
    }
    //Left->Right->Root
    public void postOrderTraversal(){
        Tree<T> current = root;
        while (current != null) {
                if (current.left != null) current = current.left;
                if (current.right != null) current = current.right;
                System.out.println(current.data);
            }
    }
    //Level-by-level top to bottom
    public void levelOrderTraversal() {
        if (root == null) return;
        Queue<Tree<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Tree<T> current = queue.poll();
            System.out.print(current.data + " ");

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
    }
    public void printLeafNode(Tree<T> left) {
        Tree<T> current = root;
        while (current != null) {
            if (current.left == null && current.right == null) {
                System.out.println(current.data);
            }
            if (current.left != null) {
                printLeafNode(current.left);
            }
            if (current.right != null) {
                printLeafNode(current.right);
            }
        }
    }

    int leftcount=0;
    public Tree<T> kthSmallesdsfsPr4stNode(Tree<T> node,int k){
        if(node==null) return null;
        Tree<T> left = kthSmallesdsfsPr4stNode(node.left ,k);
        if(left!=null) left=left;
        leftcount++;
        if(leftcount==k) return node;
        return kthSmallesdsfsPr4stNode(node.right ,k);
    }


}
