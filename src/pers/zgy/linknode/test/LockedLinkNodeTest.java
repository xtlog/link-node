package pers.zgy.linknode.test;

import pers.zgy.linknode.LinkNode;
import pers.zgy.linknode.LockedLinkNode;
import pers.zgy.linknode.NormalLinkNode;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张国业
 * @description 非线程安全双向链表单元测试类
 * @date 2018-08-18
 */
public class LockedLinkNodeTest {

    public static void main(String[] args) {

        // 基础操作测试
        basicCaseGroup();

        // 链表转置操作测试
        reverseCaseGroup();

        // 多线程添加元素用例
        concurrentAddCase();

        // 多线程转置链表用例
        concurrentReverseCase();
    }

    /**
     * 测试用例组
     * @description 链表基本操作测试用例组
     */
    private static void basicCaseGroup(){
        // 创建新链表
        LinkNode<Integer> linkNodeInteger = new LockedLinkNode<>();

        // 添加随机个数元素
        Random ra = new Random();
        int randomIntegerNum = ra.nextInt(1000);
        System.out.println("Case1.1: 添加个数随机整数元素：" + randomIntegerNum);
        for(int i=1;i<=randomIntegerNum;i++) {
            // 添加元素到链表中
            linkNodeInteger.add(i);
        }
        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeInteger.size());

        // 正向反向输出链表
        outLinkNode(linkNodeInteger);
        // 反向输出链表
        outLinkNodeReverse(linkNodeInteger);


        // 测试字符串类型
        LinkNode<String> linkNodeString = new LockedLinkNode<>();
        int randomStringNum = ra.nextInt(1000);
        System.out.println("Case1.2: 添加随机个数字符串元素：" + randomStringNum);
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        int j = 0;
        for(int i=1;i<=randomStringNum;i++) {
            // 添加元素到链表中
            linkNodeString.add(letters[j] + "" + i);
            if (i%10 == 0) {
                j++;
            }
            if (j > 10) {
                j = 0;
            }
        }
        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeString.size());

        // 正向反向输出链表
        outLinkNode(linkNodeString);
        // 反向输出链表
        outLinkNodeReverse(linkNodeString);

        //获取指定位置数据
        System.out.println("Case1.3：获取第" + randomStringNum/3 + "个位置数据为：" + linkNodeString.get(randomStringNum/3));


    }

    /**
     * 测试用例组
     * @description 链表转置操作测试用例组
     */
    private static void reverseCaseGroup() {

        System.out.println("Case2.1 空链表转置测试");
        // 创建空链表
        LinkNode<Integer> linkNodeInteger = new LockedLinkNode<>();
        // 空链表转置测试
        linkNodeInteger.reverse();
        // 输出链表
        outLinkNode(linkNodeInteger);

        System.out.println("Case2.2 单节点链表转置测试");
        linkNodeInteger.add(10);
        // 输出链表
        outLinkNode(linkNodeInteger);

        System.out.println("Case2.3 随机个数整型元素转置测试");
        linkNodeInteger = new LockedLinkNode<>();
        // 添加随机个数元素
        Random ra = new Random();
        int randomIntegerNum = ra.nextInt(1000);
        System.out.println("随机整数元素个数：" + randomIntegerNum);
        for(int i=1;i<=randomIntegerNum;i++) {
            // 添加元素到链表中
            linkNodeInteger.add(i);
        }
        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeInteger.size());
        System.out.println("当前链表结果为：");
        // 输出链表
        outLinkNode(linkNodeInteger);
        // 将链表转置
        linkNodeInteger.reverse();
        System.out.println("转置后链表结果为：");
        // 输出链表
        outLinkNode(linkNodeInteger);

        long nowTime = System.currentTimeMillis();
        System.out.println("Case 2.4 超长个数字符串元素转置测试");

        // 测试字符串类型
        LinkNode<String> linkNodeString = new LockedLinkNode<>();
        int randomStringNum = 1000000;
        System.out.println("字符串元素个数：" + randomStringNum);
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        int j = 0;
        for(int i=1;i<=randomStringNum;i++) {
            // 添加元素到链表中
            linkNodeString.add(letters[j] + "" + i % 1000);
            if (i%10 == 0) {
                j++;
            }
            if (j > 10) {
                j = 0;
            }
        }

        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeString.size() + "\n创建链表总耗时(毫秒)：" + (System.currentTimeMillis() - nowTime));
        // 输出链表
        //outLinkNode(linkNodeString);
        nowTime = System.currentTimeMillis();
        linkNodeString.reverse();

        // 输出链表长度
        System.out.println("转置链表总耗时(毫秒)：" + (System.currentTimeMillis() - nowTime));


    }

    private static void concurrentAddCase() {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        // 创建新链表
        LockedLinkNode<Integer> linkNodeInteger = new LockedLinkNode<>();

        System.out.println("Case3.1 并发添加元素测试，元素添加先后顺序不保证，预期正常完成即可");

        pool.execute(() -> {
            for (int i = 1; i <= 3000; i++) {
                linkNodeInteger.add(i);
            }
        });

        pool.execute(() -> {
            for (int i = 3001; i <= 6000; i++) {
                linkNodeInteger.add(i);
            }
        });

        pool.execute(() -> {
            for (int i = 6001; i <= 9000; i++) {
                linkNodeInteger.add(i);
            }
        });

        // 输出链表
        outLinkNode(linkNodeInteger);


    }

    private static void concurrentReverseCase() {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        // 创建新链表
        LinkNode<Integer> linkNodeInteger = new LockedLinkNode<>();

        for (int i = 1; i <= 6000; i++) {
            linkNodeInteger.add(i);
        }

        System.out.println("Case3.2 并发转置链表测试，转置加锁操作，多线程同时执行仍可保证顺序转置的一致性");
        // 并发转置测试。 预期：经五次转置，输出结果应与转置前相反。

        //linkNodeInteger.reverse();
        outLinkNode(linkNodeInteger);
        pool.execute(() -> {
            linkNodeInteger.reverse();
        });

        pool.execute(() -> {
            linkNodeInteger.reverse();
        });

        pool.execute(() -> {
            linkNodeInteger.reverse();
        });

        pool.execute(() -> {
            linkNodeInteger.reverse();
        });

        pool.execute(() -> {
            linkNodeInteger.reverse();
        });

        System.out.println("转置结果：");
        // 输出链表
        outLinkNode(linkNodeInteger);

    }

    private static void outLinkNode(LinkNode linkNode) {
        System.out.print("输出链表：");
        for(int i=1;i<=linkNode.size();i++) {

            System.out.print(linkNode.get(i) + " ");
        }
        System.out.print("\n");
    }

    private static void outLinkNodeReverse(LinkNode linkNode) {
        System.out.print("反向输出链表：");
        for(int i=linkNode.size();i>0;i--) {
            System.out.print(linkNode.get(i) + " ");
        }
        System.out.print("\n");
    }

}
