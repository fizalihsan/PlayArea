package datastructures.linkedlist;


import java.io.StringWriter;
import java.util.IdentityHashMap;
import java.util.Map;

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
     * Complexity: Time = O(n), Space = O(1)
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

    /**
     * Check if the given list has a loop using an IdentityHashMap
     * Complexity: Time = O(n), Space = O(n). Space complexity of this algo is unnecessarily large.
     *
     * @param head
     * @return true, if loop exists
     */
    static boolean loopCheckUsingExtraDataStructure(SingleLinkNode head){
        Map<SingleLinkNode, SingleLinkNode> map = new IdentityHashMap<>();

        SingleLinkNode next = head;
        while (next!=null){
            if(map.containsKey(next)) return true;
            map.put(next, next);
            next = next.getNext();
        }

        return false;
    }

    /**
     * Check if the given list has a loop using Pollard's Rho algorithm which is based on Floyd's algorithm or Tortoise and Hare algorithm.
     * (http://en.wikipedia.org/wiki/Cycle_detection#Tortoise_and_hare)
     * http://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm
     * Efficiency: O(n) time complexity, O(1) space complexity.
     *
     * @param head
     * @return true, if loop exists
     */
    static boolean loopCheckByPollard(SingleLinkNode head){
        if(head==null) return false;

        SingleLinkNode slow = head, fast = head;

        while (fast.getNext()!=null && fast.getNext().getNext()!=null){ // no need to check for slow!=null since fast did that already
            slow = slow.getNext();
            fast = fast.getNext().getNext();

            if(slow == fast) return true;
        }
        return false;
    }

    /**
     * Check if the given list has a loop using Brent's algorithm. Claimed to be 24%-36% faster than Pollard's algo since it does less equality checks.
     * Efficiency: O(n) time complexity, O(1) space complexity
     * @param head
     * @return true, if loop exists
     */
    static boolean loopCheckByBrent(SingleLinkNode head){
        if(head==null) return false;

        SingleLinkNode slow = head, fast = head;
        int taken = 0, limit = 2;

        while (/*slow.getNext()!=null && */fast.getNext()!=null ){
            fast = fast.getNext();
            taken++;
            if(slow == fast) return true;

            if(taken == limit){
                taken = 0;
                limit <<= 1;  // equivalent to limit *= 2;
                slow = fast;  // teleporting the turtle (to the hare's position)
            }
        }
        return false;
    }

    /**
     * Adding numbers in 2 lists. Each node has a digit. 2 lists could be of different length.
     * http://codercareer.blogspot.com/2013/02/no-40-add-on-lists.html
     * Algo: Reverse 2 lists to begin addition from least significant digit; add them; reverse the result.
     *
     * @param head1
     * @param head2
     * @return
     */
    static SingleLinkNode add(SingleLinkNode head1, SingleLinkNode head2){
        int carry = 0;

        head1 = reverse(head1);
        head2 = reverse(head2);
        SingleLinkNode head = null, prev = null;

        while(head1!=null || head2!=null){
            int value1 =0, value2 = 0;
            if(head1!=null){
                value1 = Integer.valueOf(head1.getValue());
                head1 = head1.getNext();
            }
            if(head2!=null){
                value2 = Integer.valueOf(head2.getValue());
                head2 = head2.getNext();
            }
            Integer result = value1 + value2 + carry;
            if(result>9){
                carry = 1;
                result -= 10;
            } else {
                carry = 0;
            }

            SingleLinkNode node = new SingleLinkNode(null, String.valueOf(result));
            if(head == null) head = node;
            if(prev!=null){
                prev.setNext(node);
            }
            prev = node;
        }

        if(carry>0){
            prev.setNext(new SingleLinkNode(null, String.valueOf(carry)));
        }

        return reverse(head);
    }

    /**
     * Concats the values in each node of the given list
     * @param head
     * @return
     */
    static String concat(SingleLinkNode head){
        StringBuilder builder = new StringBuilder();
        while(head!=null){
            builder.append(head.getValue());
            head = head.getNext();
        }
        return builder.toString();
    }

    /**
     * Print the list in reverse.
     * Reverse the list is not recommended since print is a read only operation
     * Concat values in a string and reverse the string.
     * Space complexity is high in this approach
     * @param head
     */
    static String printInReverseViaStringReversal(SingleLinkNode head){
        return new StringBuilder(concat(head)).reverse().toString();
    }

    /**
     * Print the list in reverse (using recursion)
     * @param head
     */
    static void printInReverseViaRecursion(SingleLinkNode head, StringWriter writer){
        if(head!=null){
            if(head.getNext()!=null){
                printInReverseViaRecursion(head.getNext(), writer);
            }
            writer.write(head.getValue());
        }
    }
}
