package com.example.xjb.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Trie树
 *
 * @author huang jiahui
 * @date 2022/6/10 9:52
 */
public class TrieTree {
    private final Node root;
    private int size;

    public TrieTree() {
        root = new Node();
    }

    /**
     * 插入字符串
     *
     * @param str 字符串
     */
    public void insert(String str) {
        if (str == null || str.length() == 0) {
            return;
        }

        Node current = root;
        char[] array = str.toCharArray();

        for (int i = 0; i < array.length; i++) {

            char c = array[i];

            if (current.children != null && current.children.containsKey(c)) {
                current = current.children.get(c);
                continue;
            }

            Node node = new Node(c, false);
            node.parent = current;

            if (i == array.length - 1) {
                node.isString = true;
            }

            if (current.children == null) {
                current.children = new HashMap<>(8);
                current.children.put(c, node);
            } else if (!current.children.containsKey(c)) {
                current.children.put(c, node);
            }

            current = current.children.get(c);
        }
        this.size++;
    }

    /**
     * 添加的字符串个数
     *
     * @return int
     */
    public int size() {
        return this.size;
    }

    /**
     * 清空树
     */
    public void clear() {
        this.root.children = null;
    }

    /**
     * 判断是否含有字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public boolean containsString(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        if (root.children == null || root.children.size() == 0) {
            return false;
        }

        char[] array = str.toCharArray();

        Node current = root;

        for (char c : array) {
            if (!current.children.containsKey(c)) {
                return false;
            }

            current = current.children.get(c);
        }

        return current.isString;
    }

    /**
     * 最长前缀
     *
     * @return {@link String}
     */
    public String longestCommonPrefix() {
        // 没字符串
        if (root.children == null || root.children.size() != 1) {
            return "";
        }
        Node current = root;
        StringBuilder result = new StringBuilder();

        while (current.children.size() == 1) {
            Character c = current.children.keySet().iterator().next();
            current = current.children.get(c);
            result.append(c);

            if (current.isString) {
                return result.toString();
            }
        }
        return result.toString();
    }

    /**
     * 删除字符串
     * @param str 字符串
     */
    public void delete(String str) {
        if (str == null || str.length() == 0) {
            return;
        }

        if (root.children == null || root.children.size() == 0) {
            return;
        }

        char[] array = str.toCharArray();

        Node current = root;

        for (char c : array) {
            if (!current.children.containsKey(c)) {
                return;
            }

            current = current.children.get(c);
        }

        if(current.children != null){
            current.isString = false;
            return;
        }

        while (current.parent != null){
            char c = current.val;
            current  = current.parent;
            current.children.remove(c);

            if(current.children.size() != 0 || current.isString){
                break;
            }
        }
    }


    static class Node {
        char val;
        boolean isString;
        Map<Character, Node> children;
        Node parent;

        public Node() {
        }

        public Node(char c, boolean isString) {
            this.val = c;
            this.isString = isString;
        }
    }
}
