package com.xj.demo.algorithm;

import java.util.HashMap;

public class AlgorithmUtil {


    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.next = l2;
        l2.next = l3;

        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(4);
        l4.next = l5;
        l5.next = l6;

//        addTwoNumbers(l1, l4);
//        lengthOfLongestSubstring("aabaab!bb");

        String str ="";
        new StringBuilder(str).reverse();

        System.out.println(Integer.valueOf("023"));

//        System.out.println(getNum(100, 100) + 100);
    }

    public static int lengthOfLongestSubstring(String s) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        int n = s.length();
        int start = 0;
        int res = 0;//最长子串长度
        for (int i = 0; i < n; i++) {
            int index = s.charAt(i);
            if (hashMap.get(index) == null) {
                hashMap.put(index, i);//记录第一次出现的下标
                res = Math.max(i - start + 1, res);
            } else {
                int last = hashMap.get(index);
                start = Math.max(last + 1, start);//往右滑动一个位置
                hashMap.put(index, i);//覆盖下标
                res = Math.max(i - start + 1, res);
            }
        }

//        // 记录字符上一次出现的位置
//        int[] last = new int[128];
//        for(int i = 0; i < 128; i++) {
//            last[i] = -1;
//        }
//        int n = s.length();
//
//        int res = 0;
//        int start = 0; // 窗口开始位置
//        for(int i = 0; i < n; i++) {
//            int index = s.charAt(i);
//            start = Math.max(start, last[index] + 1);
//            res   = Math.max(res, i - start + 1);
//            last[index] = i;//在数组中记录最新一次出现的位置
//        }

        return res;
    }

    /**
     * 3个空瓶可以换一瓶，7个瓶盖可以换一瓶
     *
     * @param emptyBottle 空瓶数
     * @param bottleCap 瓶盖
     */
    public static int getNum(int emptyBottle, int bottleCap) {
        if (emptyBottle < 3 && bottleCap < 7) {
            //无法继续兑换
            return 0;
        }
        int increaseBottle = emptyBottle / 3 + emptyBottle % 3 + bottleCap / 7;
        int increaseCap = bottleCap / 7 + bottleCap % 7 + emptyBottle / 3;
        return emptyBottle / 3 + bottleCap / 7 + getNum(increaseBottle, increaseCap);
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode root = new ListNode(0);
        ListNode cursor = root;
        int carry = 0;//相加之和是否进1
        while (l1 != null || l2 != null || carry != 0) {
            int l1Value = l1 != null ? l1.val : 0;
            int l2Value = l2 != null ? l2.val : 0;
            int sum = l1Value + l2Value + carry;
            carry = sum / 10;

            ListNode sumNode = new ListNode(sum % 10);
            cursor.next = sumNode;
            cursor = sumNode;

            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        return root.next;
    }

    static public class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

}


