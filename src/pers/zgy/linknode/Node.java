package pers.zgy.linknode;

/**
 * 双向链表节点类
 * @author 张国业
 * @date 2018-08-18
 */
public final class Node<E> {

    E data;

    Node<E> prev, next;

    Node(E e, Node<E> p, Node<E> n) {
        data = e;
        prev = p;
        next = n;
    }
}
