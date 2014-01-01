package datastructures.linkedlist;


/**
 * Comment here about the class
 * User: Fizal
 * Date: 12/31/13
 * Time: 10:27 PM
 */
public class LinkedListProblems {

    /**
     * Get the Kth node from end of a linked list. It counts from 1 here, so the 1st node from end is the tail of list.
     * For instance, given a linked list with 6 nodes, whose value are 1, 2, 3, 4, 5, 6, its 3rd node from end is the node with value 4
     * Efficiency= O(n)
     * @param head
     * @param k
     * @return If list size is less than k, then null. Else kth node.
     */
    static SingleLinkNode kthNodeFromEnd(SingleLinkNode head, int k){
        SingleLinkNode p1 = head, p2 = head;

        for (int i = 0; i <k; i++) {
            if(p1 == null) return p1;
            p1 = p1.getNext();
        }

        while(p1 != null) {
            p1 = p1.getNext();
            p2 = p2.getNext();
        }

        return p2;
    }

    /**
     * Reverses the given singly-linked list.
     * Efficiency= O(n)
     * @param head
     * @return Head of the reversed list
     */
    static SingleLinkNode reverse(SingleLinkNode head){
        SingleLinkNode reverseHead = null, node = head, prev = null;

        while(node!=null){
            SingleLinkNode next = node.getNext();
            if(next == null) reverseHead = node;

            node.setNext(prev);
            prev = node;
            node = next;
        }

        return reverseHead;
    }

}
