package datastructures.linkedlist;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 12/31/13
 * Time: 10:26 PM
 */
public class SingleLinkNode {
    private SingleLinkNode next;
    private String value;

    public SingleLinkNode(SingleLinkNode next, String value) {
        this.next = next;
        this.value = value;
    }

    public SingleLinkNode getNext() {
        return next;
    }

    public void setNext(SingleLinkNode next) {
        this.next = next;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return new StringBuilder(value).append(" -> ").append(next).toString();
    }
}
