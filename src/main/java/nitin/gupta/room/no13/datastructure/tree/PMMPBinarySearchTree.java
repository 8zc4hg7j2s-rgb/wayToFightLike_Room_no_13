package nitin.gupta.room.no13.datastructure.tree;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.Queue;

@NoArgsConstructor
public class PMMPBinarySearchTree<T extends Comparable<T>> {

    int leftcount = 0;
    int rightCount = 0;
    private Tree<T> root;

    public void add(T data) {
        if (root == null) root = new Tree(data, null);
        else addRec(data, root);
    }

    private void addRec(T data, Tree<T> root) {
        if (root.data.compareTo(data) < 0) {
            if (root.left == null) root.left = new Tree(data, null);
            else addRec(data, root.left);
        } else {
            if (root.right == null) root.right = new Tree(data, null);
            else addRec(data, root.right);
        }
    }

    public void remove(T data) {
        Tree<T> parent = null;
        Tree<T> current = root;
        // 1. Find the Tree to remove, tracking its parent
        while (current != null && current.data.compareTo(data) != 0) {
            parent = current;
            if (data.compareTo(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) {
            return; // value not found, nothing to remove
        }
        // 2. Case: Tree has two children — find in-order successor (min of right subtree)
        if (current.left != null && current.right != null) {
            Tree<T> successorParent = current;
            Tree<T> successor = current.right;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            // Copy successor's value into the Tree we wanted to remove
            current.data = successor.data;
            // Now remove the successor Tree instead (it has at most a right child)
            parent = successorParent;
            current = successor;
        }

        // 3. Case: Tree has zero or one child
        Tree<T> child = (current.left != null) ? current.left : current.right;

        if (parent == null) {
            root = child; // removing the root
        } else if (parent.left == current) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    public T search(T value) {
        Tree<T> current = root;
        while (current != null) {
            int cmp = value.compareTo(current.data);
            if (cmp == 0) {
                return current.data;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        return null; // not found
    }

    public Tree<T> findKthSmallest(int k, Tree<T> tree) {
        if (tree == null) return null;
        Tree<T> left = findKthSmallest(k, tree.left);
        if (left != null) left = left;
        leftcount++;
        if (leftcount == k) return tree;
        return findKthSmallest(k, tree.right);
    }

    public Tree<T> findKthLargest(int k, Tree<T> tree) {
        if (tree == null) return null;
        Tree<T> right = findKthLargest(k, tree.right);
        if (right != null) right = right;
        rightCount++;
        if (rightCount == k) return tree;
        return findKthLargest(k, tree.left);
    }

    public T minimum() {
        Tree<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }

    public T maximum() {
        Tree<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }

    public int height(Tree<T> tree) {
        if (tree == null) {
            return 0;
        }
        return Math.max(height(tree.left), height(tree.right)) + 1;
    }

    public int count(Tree<T> tree) {
        if (tree == null) {
            return 0;
        }
        return count(tree.left) + count(tree.right) + 1;
    }

    //Root ->Left $ -> Right
    public void preOrder() {
        if (root == null) return;
        Tree<T> current = root;
        System.out.println(current.data);
        while (current.left != null) current = current.left;
        while (current.right != null) current = current.right;
    }

    //Left->root->Right
    public void inOrder() {
        if (root == null) return;
        Tree<T> current = root;
        while (current.left != null) current = current.left;
        System.out.println(current.data);
        while (current.right != null) current = current.right;
    }

    //Left->Right->Root
    public void postOrder() {
        if (root == null) return;
        Tree<T> current = root;
        while (current.left != null) current = current.left;
        while (current.right != null) current = current.right;
        System.out.println(current.data);
    }

    public void levelOrder() {
        if (root == null) return;
        Tree<T> current = root;
        Queue<Tree<T>> queue = new LinkedList<>();
        queue.add(current);
        while (!queue.isEmpty()) {
            Tree<T> temp = queue.poll();
            System.out.println(current.data);
            if (temp.left != null) queue.add(temp.left);
            if (temp.right != null) queue.add(temp.right);
        }
    }

    public void leafLevelOrderTravel() {
        if (root == null) return;
        Tree<T> current = root;
        while (current != null) {
            if (current.left == null && current.right == null) {
                System.out.println(current.data);
            }
            if (current.left != null) current = current.left;
            if (current.right != null) current = current.right;
        }
    }

    @ToString
    @EqualsAndHashCode
    static class Tree<T extends Comparable<T>> {
        T data;
        Tree<T> left;
        Tree<T> right;
        Tree<T> parent;

        public Tree(T data, Tree<T> parent) {
            this.data = data;
            this.parent = parent;
        }
    }


}
