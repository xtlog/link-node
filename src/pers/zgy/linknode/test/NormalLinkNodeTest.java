package pers.zgy.linknode.test;

import pers.zgy.linknode.NormalLinkNode;
import pers.zgy.linknode.LinkNode;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 张国业
 * @description 非线程安全双向链表单元测试类
 * @date 2018-08-18
 */
public class NormalLinkNodeTest {

    public static void main(String[] args) {

        // 基础操作测试
        basicCaseGroup();

        // 链表转置操作测试
        reverseCaseGroup();


        // 并发测试与基础测试同时进行时，因内存空间已分配元素，不容易产生预期并发错误，可单独运行用例。
        // 多线程添加元素用例
        //concurrentAddCase();

        // 多线程转置链表用例
        //concurrentReverseCase();

    }

    /**
     * 测试用例组
     * @description 链表基本操作测试用例组
     */
    private static void basicCaseGroup(){
        // 创建新链表
        LinkNode<Integer> linkNodeInteger = new NormalLinkNode<>();

        // 添加随机个数元素
        Random ra = new Random();
        int randomIntegerNum = ra.nextInt(1000);
        System.out.println("Case1.1: 添加个数随机整数元素：" + randomIntegerNum);
        for(int i = 1; i <= randomIntegerNum; i++) {
            // 添加元素到链表中
            linkNodeInteger.add(i);
        }
        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeInteger.size());

        // 输出链表
        outLinkNode(linkNodeInteger);
        // 反向输出链表
        outLinkNodeReverse(linkNodeInteger);


        // 测试字符串类型
        LinkNode<String> linkNodeString = new NormalLinkNode<>();
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

        // 输出链表
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
        LinkNode<Integer> linkNodeInteger = new NormalLinkNode<>();
        // 空链表转置测试
        linkNodeInteger.reverse();
        // 输出链表
        outLinkNode(linkNodeInteger);

        System.out.println("Case2.2 单节点链表转置测试");
        linkNodeInteger.add(10);
        // 输出链表
        outLinkNode(linkNodeInteger);

        System.out.println("Case2.3 随机个数整型元素转置测试");
        linkNodeInteger = new NormalLinkNode<>();
        // 添加随机个数元素
        Random ra = new Random();
        int randomIntegerNum = ra.nextInt(1000);
        System.out.println("随机整数元素个数：" + randomIntegerNum);
        for (int i=1;i<=randomIntegerNum;i++) {
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
        LinkNode<String> linkNodeString = new NormalLinkNode<>();
        int randomStringNum = 1000000;
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'};
        int j = 0;
        for (int i = 1; i <= randomStringNum; i++) {
            // 添加元素到链表中
            linkNodeString.add(letters[j] + "" + i % 1000);
            if (i % 10 == 0) {
                j++;
            }
            if (j > 10) {
                j = 0;
            }
        }

        // 输出链表长度
        System.out.println("当前链表长度为：" + linkNodeString.size() + "\n创建链表总耗时(毫秒)：" + (System.currentTimeMillis() - nowTime));

        nowTime = System.currentTimeMillis();
        linkNodeString.reverse();

        // 输出链表长度
        System.out.println("转置链表总耗时(毫秒)：" + (System.currentTimeMillis() - nowTime));


    }

    private static void concurrentAddCase() {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        // 创建新链表
        LinkNode<Integer> linkNodeInteger = new NormalLinkNode<>();

        System.out.println("Case3.1 并发添加元素测试，因链表指针丢失，无法正常完成运行");

        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 2000; i++) {
                    linkNodeInteger.add(i);
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 2000; i++) {
                    linkNodeInteger.add(i);
                }
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 2000; i++) {
                    linkNodeInteger.add(i);
                }
            }
        });

        // 输出链表
        outLinkNode(linkNodeInteger);


    }

    private static void concurrentReverseCase() {


        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5));
        // 创建新链表
        LinkNode<Integer> linkNodeInteger = new NormalLinkNode<>();

        for (int i = 1; i <= 5000; i++) {
            linkNodeInteger.add(i);
        }

        System.out.println("Case3.2 并发转置链表测试，多线程容易导致指针丢失，顺序错乱");
        outLinkNode(linkNodeInteger);
        // 并发转置测试。 预期：经三次转置，输出结果应与转置前相反。
        pool.execute(new Runnable() {
            @Override
            public void run() {
                linkNodeInteger.reverse();
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                linkNodeInteger.reverse();
            }
        });

        pool.execute(new Runnable() {
            @Override
            public void run() {
                linkNodeInteger.reverse();
            }
        });

        System.out.println("转置结果：");
        // 输出链表
        outLinkNode(linkNodeInteger);

    }

    private static void outLinkNode(LinkNode linkNode) {
        System.out.print("输出链表：");
        for(int i = 1; i <= linkNode.size(); i++) {

            System.out.print(linkNode.get(i) + " ");
        }
        System.out.print("\n");
    }

    private static void outLinkNodeReverse(LinkNode linkNode) {
        System.out.print("反向输出链表：");
        for(int i = linkNode.size(); i > 0; i--) {
            System.out.print(linkNode.get(i) + " ");
        }
        System.out.print("\n");
    }

}
