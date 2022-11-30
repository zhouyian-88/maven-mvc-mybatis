package com.qfedu.util;

import java.util.List;

public class ListToArrayUtil {

    public static Object[] toArray(List<Object> list){
        Object[] obj=new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            obj[i]=list.get(i);
        }
        return obj;
    }
}
