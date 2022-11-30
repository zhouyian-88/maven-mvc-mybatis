package com.qfedu.util;

public class Test {
    public static void main(String[] args) {
        StringBuffer sb=new StringBuffer();
        sb.append("hello");

        sb.delete(sb.length()-1,sb.length());

        System.out.println(sb);
    }
}
