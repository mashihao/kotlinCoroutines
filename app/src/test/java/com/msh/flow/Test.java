package com.msh.flow;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 马世豪
 * time : 2021/9/24 14
 * email : ma_shihao@yeah.net
 * des :
 */
public class Test {
    @org.junit.Test
    public void test() {
        List<Integer> listA = new ArrayList<>();
        listA.add(1);
        listA.add(2);
        listA.add(3);
        listA.add(4);
        listA.add(5);
        List<Integer> listA_new = new ArrayList<>();
        for (int i = 0; i < listA.size(); i++) {
            if (listA.get(i) > 2) {
                listA_new.add(listA.get(i));
            }
        }
        List<String> listB = new ArrayList<>();

        for (int i = 0; i < listA_new.size(); i++) {
            listB.add("listb---->" + listA_new.get(i));
        }
        System.out.println(listB);
    }


    public interface IHello {
        String sayHello();
    }

    public static class HelloInvocationHandler implements InvocationHandler {
        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
            //根据不同的Method Name以及@return 单独处理调用okhttp call获取到相应的@return
            if (method.getName().equals("sayHello")) {
                return "sayHello";
            }
            return "null数据";
        }
    }

    @org.junit.Test
    public void main() {

        IHello o = (IHello) Proxy.newProxyInstance(
                IHello.class.getClassLoader(),
                new Class<?>[]{IHello.class},
                new HelloInvocationHandler());
        System.out.println(o.sayHello());
    }

}
