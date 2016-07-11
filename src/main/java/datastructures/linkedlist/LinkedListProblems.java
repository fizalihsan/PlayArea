package datastructures.linkedlist;


import java.io.StringWriter;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 12/31/13
 * Time: 10:27 PM
 */
public class LinkedListProblems {


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
