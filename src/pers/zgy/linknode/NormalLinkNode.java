package pers.zgy.linknode;

/**
 * 非线程安全实现类
 * @author 张国业
 * @date 2018-4-18
 * @description 非线程安全双向链表实现类，头节点值为null，位置为0。第一个非空元素位置为1。
 * @param <E>
 */
public class NormalLinkNode<E> implements LinkNode<E> {

    private Node<E> node;

    private int size = 0;

    public NormalLinkNode() {
        node = new Node<>(null, null, null);
    }

    /**
     * 添加元素
     * @description 在尾部添加一个新元素
     * @param element
     */
    @Override
    public boolean add(E element) {

        // 确保链表移动到最后节点
        last();

        // 创建一个新节点，前指针指向当前节点，当前节点后指针指向新节点
        Node<E> newNode = new Node<>(element, node, null);
        node.next = newNode;
        size = size + 1;

        return true;
    }

    /**
     * 添加元素
     * @description 在指定位置添加一个新元素
     * @param index
     * @param element
     */
    @Override
    public boolean add(int index, E element) {
        // 指定位置必须大于1小于当前的容量
        if(index <= 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size) {
            // 如果指定位置与容量相等，直接在最后插入元素
            add(element);
            return true;
        }

        // 将链表定位到指定位置
        node = getNode(index);
        // 创建一个新节点，前指针指向当前节点的前节点，后指针指向当前节点
        Node<E> newNode = new Node<E>(element, node.prev, node);
        node.prev.next = newNode;
        node.prev = newNode;
        size = size + 1;
        return true;
    }

    /**
     * 删除元素
     * @description 在删除指定位置元素
     * @param index
     */
    @Override
    public boolean remove(int index) {
        if(index <= 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        // 将链表定位到指定位置
        node = getNode(index);

        // 当遍历到指定位置时，插入元素
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size = size - 1;
        return true;
    }

    /**
     * 链表长度
     * @description 获取链表长度
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 转置链表
     * @description 将链表从后向前转置
     * @return LinkNode<E>
     */
    @Override
    public void reverse() {
        // 获取链表的头节点，为第一个元素的前节点
        first();
        Node<E> newNode = node.prev;

        if (newNode != null) {
            // 链表移动到最后节点
            last();
            Node<E> oldNode;
            while(true) {
                oldNode = node.prev;
                if (oldNode == null) {
                    newNode.next = null;
                    break;
                }
                node.prev = newNode;
                newNode.next = node;
                newNode = node;
                node = oldNode;
            }
        }
    }

    /**
     * 链表指向开始节点
     * @description 获取链表长度
     */
    @Override
    public void first() {
        if (node.prev == null) {
            if (node.next == null) {
                return;
            } else {
                node = node.next;
            }
            return;
        }

        // 确保链表移动到第一个非空节点
        while (node.prev.prev != null) {
            node = node.prev;
        }
    }

    /**
     * 链表指向最后节点
     * @description 获取链表长度
     */
    @Override
    public void last() {
        // 确保链表移动到最后节点
        while (node.next != null) {
            node = node.next;
        }
    }

    /**
     * 获取元素
     * @description 获取指定位置元素
     * @param index
     * @return E
     */
    @Override
    public E get(int index) {
        if (index <= 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        return getNode(index).data;
    }

    /**
     * @description 查找指定节点
     * @param index
     * @return
     */
    private Node<E> getNode(int index) {
        // 将链表定位到指定位置
        int i;
        // 简易二分查找指定位置
        if (index < (size >> 1)) {
            first();
            i = 1;
            while (i < index) {
                node = node.next;
                i++;
            }
        } else {
            last();
            i = size;
            while(i > index) {
                node = node.prev;
                i--;
            }
        }

        if (i == index) {
            return node;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }


}
