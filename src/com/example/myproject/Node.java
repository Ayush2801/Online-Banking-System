package com.example.myproject;
public class Node {
    private int[] keys;
    private Node[] children;
    private Node parent;
    private int size;
    private boolean isLeaf;

    public Node() {
        keys = new int[3];
        children = new Node[4];
        parent = null;
        size = 0;
        isLeaf = true;
    }

    public int getKey(int index) {
        return keys[index];
    }

    public void setKey(int index, int key) {
        keys[index] = key;
        size++;
    }

    public Node getChild(int index) {
        return children[index];
    }

    public void setChild(int index, Node child) {
        children[index] = child;
        if (child != null) {
            child.setParent(this);
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }
    public boolean isEmpty() {
        return size == 0;
    }
}
