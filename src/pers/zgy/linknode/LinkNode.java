package pers.zgy.linknode;

/**
 * 双向链表公共接口
 * @author 张国业
 * @date 2018-08-18
 */
public interface LinkNode<E> {

    /**
     * 添加元素
     * @description 在尾部添加一个新元素
     * @param element
     */
    boolean add(E element);

    /**
     * 指定位置添加元素
     * @description 在尾部添加一个新元素
     * @param index
     * @param element
     */
    boolean add(int index, E element);

    /**
     * 删除元素
     * @description 在删除指定位置元素
     * @param index
     */
    boolean remove(int index);

    /**
     * 链表长度
     * @description 获取链表长度
     * @return int
     */
    int size();

    /**
     * 反转链表
     * @description 将链表从后向前反转
     * @return LinkNode<E>
     */
    void reverse();

    /**
     * 链表指向开始节点
     * @description 获取链表长度
     */
    void first();

    /**
     * 链表指向最后节点
     * @description 获取链表长度
     */
    void last();

    /**
     * 获取元素
     * @description 获取指定位置元素
     * @param index
     * @return E
     */
    E get(int index);

}
