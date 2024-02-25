package com.example.myproject;

import com.example.myproject.Node;
public class Main {
    public static void main(String[] args) {
        Node root = new Node();

        // Insert keys into the tree
        root.setKey(0, 10);
        root.setKey(1, 20);
        root.setKey(2, 30);

        // Create child nodes
        Node child1 = new Node();
        Node child2 = new Node();
        Node child3 = new Node();

        // Connect child nodes to the root
        root.setChild(0, child1);
        root.setChild(1, child2);
        root.setChild(2, child3);

        // Print the tree
        printTree(root);
    }

    public static void printTree(Node node) {

    	 if (node != null && !node.isEmpty()) {
             for (int i = 0; i < node.getSize(); i++) {
                 System.out.print(node.getKey(i) + " ");
             }
             System.out.println();

             if (!node.isLeaf()) {
                 for (int i = 0; i <= node.getSize(); i++) {
                     printTree(node.getChild(i));
                 }
                }
            }
        }
    }

