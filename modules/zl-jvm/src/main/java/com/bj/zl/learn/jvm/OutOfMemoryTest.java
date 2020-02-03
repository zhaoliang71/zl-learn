/**
 * create by Administrator
 * 2020-01-31
 */
package com.bj.zl.learn.jvm;

import com.bj.zl.learn.asm.MetaspaceClass;
import com.bj.zl.learn.model.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryTest {


    public static void main(String[] args) {

        OutOfMemoryTest test = new OutOfMemoryTest();
        //test.heap();
        test.loop();
    }

    /**
     * 死循环
     *
     * @return
     */
    public Object loop(){
        String str = "<xml>文章内容</xml>";
        while (true) {
            str = str.replace("<xml>", "").replace("</xml>", "");
            System.out.println(str);
        }
    }


    /**
     * 非堆的溢出 （元空间溢出）
     *
     * @return
     */
    public Object nonheap() {
        MetaspaceClass.classLoading();
        return "Finish";
    }


    /**
     *  堆溢出
     * @return
     */
    public Object heap() {
        //List
        List<Order> orderList = new ArrayList<Order>();

        //死循环
        for (;;) {
            //Order
            Order order = new Order();
            order.setId(1);
            order.setMoney(new BigDecimal(990));
            order.setName("支付订单");

            //放入List
            orderList.add(order);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }





}
