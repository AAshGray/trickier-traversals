import java.util.*;

public class Traversals {

  /**
   * Returns the sum of the values of all leaf nodes in the given tree of integers.
   * A leaf node is defined as a node with no children.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the sum of leaf node values, or 0 if the tree is null
   */
  public static int sumLeafNodes(TreeNode<Integer> node) {
    if (node == null) return 0;

    
    if (node.left == null && node.right == null) {
      return node.value;
    }

    return sumLeafNodes(node.left) + sumLeafNodes(node.right);
  }

  /**
   * Counts the number of internal nodes (non-leaf nodes) in the given tree of integers.
   * An internal node has at least one child.
   * If node is null, this method returns 0.
   *
   * @param node the node of the tree
   * @return the count of internal nodes, or 0 if the tree is null
   */
  public static int countInternalNodes(TreeNode<Integer> node) {
    if (node == null) return 0;
    
    if (node.left != null || node.right != null) {
      return 1 + countInternalNodes(node.left) + countInternalNodes(node.right);
    }

    return 0;
  }

  /**
   * Creates a string by concatenating the string representation of each node's value
   * in a post-order traversal of the tree. For example, if the post-order visitation
   * encounters values "a", "b", and "c" in that order, the result is "abc".
   * If node is null, returns an empty string.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a post-order traversal string, or an empty string if the tree is null
   */
  public static <T> String buildPostOrderString(TreeNode<T> node) {
    if (node == null) return "";

    return buildPostOrderString(node.left) + buildPostOrderString(node.right) + node.value;
  }

  /**
   * Collects the values of all nodes in the tree level by level, from top to bottom.
   * If node is null, returns an empty list.
   *
   * @param node the node of the tree
   * @param <T>  the type of values stored in the tree
   * @return a list of node values in a top-to-bottom order, or an empty list if the tree is null
   */
  public static <T> List<T> collectLevelOrderValues(TreeNode<T> node) {
    // returning a list
    List<T> list = new ArrayList<>();
    
    // using a queue for level-order traversal
    Queue<TreeNode<T>> queue = new LinkedList<>();
    queue.add(node);

    // until the queue is empty
    while (!queue.isEmpty()) {

      // look at the next node on the list
      TreeNode<T> current = queue.poll();

      if (current != null) {

        // (pre-order) if current node isn't null, add the node value to the list
        list.add(current.value);

        // then add left and right to the queue
        queue.add(current.left);
        queue.add(current.right);
      }
    }

    // return the list
    return list;
  }

  /**
   * Counts the distinct values in the given tree.
   * If node is null, returns 0.
   *
   * @param node the node of the tree
   * @return the number of unique values in the tree, or 0 if the tree is null
   */
  public static int countDistinctValues(TreeNode<Integer> node) {
    // using a stack for practice
    // we know it is always going to hold a TreeNode of Integers (parameter list)
    Stack<TreeNode<Integer>> stack = new Stack<>();
    
    // saving a set (unique values by default)
    // we know it is always going to be Integers
    Set<Integer> set = new HashSet<>();

    // setting up the stack
    stack.push(node);

    // while the stack isn't empty
    while (!stack.empty()) {
      // take a node off the stack
      TreeNode<Integer> current = stack.pop(); 
      
      // check if it is null before we interact with the data
      if (current != null) {
        // add the value of the non-null node to our set
        set.add(current.value);

        // add left and right to our stack
        stack.add(current.left);
        stack.add(current.right);
      }
    }
    
    // at the end, return the size of our set of unique values
    // an empty set (if first node == null) is size 0
    return set.size();
  }

  /**
   * Determines whether there is at least one root-to-leaf path in the tree
   * where each successive node's value is strictly greater than the previous node's value.
   * If node is null, returns false.
   *
   * @param node the node of the tree
   * @return true if there exists a strictly increasing root-to-leaf path, false otherwise
   */

  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node) {
    // if the first node is false, return false
    if (node == null) return false;

    // if the node is a leaf, return true (technically increasing)
    if (node.left == null && node.right == null) return true;

    // send both branches to the helper method with the current value
    // only the left OR right branch needs to be true for there to be at least one 'strictly increasing path'
    return hasStrictlyIncreasingPath(node.left, node.value) || hasStrictlyIncreasingPath(node.right, node.value);
  }

  public static boolean hasStrictlyIncreasingPath(TreeNode<Integer> node, int prev) {
    
    // if this node is null, then we haven't reached a leaf yet. ok to return false because of the earlier OR
    // if the value isn't increasing over the previous, this is also a false branch
    if (node == null || node.value <= prev) return false;

    // if we've arrived here and it's a leaf node
    // 1.) the values always went up
    // 2.) we're not on a branch corner (null)
    // so we must have found a 'correct' pathway
    if (node.left == null && node.right == null) return true;

    // otherwise continue checking left and right branches
    return hasStrictlyIncreasingPath(node.left, node.value) || hasStrictlyIncreasingPath(node.right, node.value);
  }

  // OPTIONAL CHALLENGE
  /**
   * Checks if two trees have the same shape. Two trees have the same shape
   * if they have exactly the same arrangement of nodes, irrespective of the node values.
   * If both trees are null, returns true. If one is null and the other is not, returns false.
   *
   * @param nodeA the node of the first tree
   * @param nodeB the node of the second tree
   * @param <T>   the type of values stored in the trees
   * @return true if the trees have the same shape, false otherwise
   */
  public static <T> boolean haveSameShape(TreeNode<T> nodeA, TreeNode<T> nodeB) {
    return false;
  }


  // OPTIONAL CHALLENGE
  // Very challenging!
  // Hints:
  // List.copyOf may be helpful
  // Consider adding a helper method
  // Consider keeping the current path in a separate variable
  // Consider removing the current node from the current path after the node's subtrees have been traversed.
  /**
   * Finds all paths from the root to every leaf in the given tree.
   * Each path is represented as a list of node values from root to leaf.
   * The paths should be added pre-order.
   * If node is null, returns an empty list.
   * 
   * Example:
   *
   *         1
   *        / \
   *       2   3
   *      / \    \
   *     4   5    6
   * 
   * Expected output:
   *   [[1, 2, 4], [1, 2, 5], [1, 3, 6]]
   * 
   * @param node the root node of the tree
   * @return a list of lists, where each inner list represents a root-to-leaf path in pre-order
   */
  public static <T> List<List<T>> findAllRootToLeafPaths(TreeNode<T> node) {
    return null;
  }
}
