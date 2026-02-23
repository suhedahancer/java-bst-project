/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import java.util.Random;
import java.util.Arrays;
import java.lang.Math;

public class SuhedaHancer_project3 {
    
    static class Node{
        int value;
        Node left;
        Node right;

        Node(int value){
            this.value = value;
        }
    }

    static class BST {
       private Node root;

        public void insert(int value){
            root = insertRecursive(root, value);
        }

       private Node insertRecursive(Node current, int value){
            if (current == null) {
                return new Node(value);
            }

           if (value<current.value){
                current.left = insertRecursive(current.left, value);
          } else{
                current.right =insertRecursive(current.right, value);
            }
          return current;
        }

        public int[] toSortedArray(int size){
            int[] result = new int[size];
            int[] index = {0};
            inorderRecursive(root, result, index);
            return result;
        }

      private void inorderRecursive(Node current, int[] array, int[] index){
            if (current == null) return;

            inorderRecursive(current.left, array, index);
            array[index[0]] = current.value;
            index[0]++;
            inorderRecursive(current.right, array, index);
        }

      public int height(){
            return heightRecursive(root);
        }

      private int heightRecursive(Node current){
            if (current == null) return 0;
            return Math.max(heightRecursive(current.left), heightRecursive(current.right)) +1;
        }
        public boolean isBalanced(){
           return isBalancedRecursive(root);
        }

        private boolean isBalancedRecursive(Node current){
            if (current == null) return true;

            int leftHeight = heightRecursive(current.left);
            int rightHeight = heightRecursive(current.right);

            if (Math.abs(leftHeight - rightHeight) > 1) return false;

            return isBalancedRecursive(current.left) && isBalancedRecursive(current.right);
        }

       public int imbalanceScore(){
           return imbalanceScoreRecursive(root);
       }

       private int imbalanceScoreRecursive(Node current){
           if (current == null) return 0;

            int difference = Math.abs(heightRecursive(current.left) - heightRecursive(current.right));

            return difference + imbalanceScoreRecursive(current.left)+ imbalanceScoreRecursive(current.right);
        }
        
        public static BST fromSortedArray(int[] sortedArray) {
            BST tree = new BST();
            tree.root = buildBalancedRecursive(sortedArray, 0, sortedArray.length -1);
            return tree;
      }

        private static Node buildBalancedRecursive(int[] array, int start, int end){
            if (start > end) return null;

           int middle = (start + end) / 2;
           Node current = new Node(array[middle]);

            current.left = buildBalancedRecursive(array, start, middle -1);
            current.right = buildBalancedRecursive(array, middle + 1, end);

            return current;
      }
    }

    static int idealHeight(int n){
        if (n <= 1) return 1;
        return (int) Math.ceil(Math.log(n)/Math.log(2));
    }

    static void printInfo(BST tree, int n, String title){
        System.out.println(title);

        int actualHeight = tree.height();
        int ideal = idealHeight(n);
        double ratio = (double) actualHeight/ideal;

        System.out.println("height: " + actualHeight);
        System.out.println("ideal : " + ideal);
        System.out.printf("ratio : %.2f%n", ratio);
        System.out.println("is balanced?: " + tree.isBalanced());
        System.out.println("score: " + tree.imbalanceScore());
    }

    public static void main(String[] args){
        int n = 10;
        int[] numbers = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++){
            numbers[i] = random.nextInt(100) +1;
        }

        System.out.println("random array:");
        System.out.println(Arrays.toString(numbers));

        BST tree1 = new BST();
        for (int i = 0; i < n; i++) {
            tree1.insert(numbers[i]);
        }

        int[] sorted = tree1.toSortedArray(n);
        System.out.println("\n sorted:");
        System.out.println(Arrays.toString(sorted));

        BST tree2 = BST.fromSortedArray(sorted);

        System.out.println();
        printInfo(tree1, n, "BST1:");
        System.out.println();
        printInfo(tree2, n, "BST2:");
    }
}

/*  EXAMPLE OUTPUT
random array:
[40, 91, 20, 6, 46, 25, 24, 26, 28, 42]

sorted (inorder):
[6, 20, 24, 25, 26, 28, 40, 42, 46, 91]

BST1:
height: 5
ideal : 4
ratio : 1,25
is balanced?: false
score: 8

BST2:
height: 4
ideal : 4
ratio : 1,00
is balanced?: true
score: 4  */