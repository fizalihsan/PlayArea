package datastructures.linkedlist

import spock.lang.Specification

import static datastructures.linkedlist.LinkedListProblems.kthNodeFromEnd
import static datastructures.linkedlist.LinkedListProblems.reverse
/**
 * Comment here about the class
 * User: Fizal
 * Date: 1/1/14
 * Time: 11:33 AM
 */
class LinkedListProblemsTest extends Specification {



    def "Kth node in a list - with no nodes"() {
        def head = null
        when: def reverse = kthNodeFromEnd(head, 3)
        then: reverse == null
    }


    def "Kth node in a list - Get 1st node in a 1 node list"() {
        def node1 = new SingleLinkNode(null, "1")
        when:  def kthNode = kthNodeFromEnd(node1, 1)
        then: kthNode == node1
    }

    def "Kth node in a list - Get 2nd node in a 1 node list"() {
        def node1 = new SingleLinkNode(null, "1")
        when:  def kthNode = kthNodeFromEnd(node1, 2)
        then: kthNode == null
    }

    def "Kth node in a list - with 2 nodes"() {
        SingleLinkNode node2 = new SingleLinkNode(null, "2")
        SingleLinkNode node1 = new SingleLinkNode(node2, "1")
        when: def reverse = kthNodeFromEnd(node1, 3)
        then: reverse == null
    }

    def "Reverse list - with no nodes"() {
        def head = null
        when: def reverse = reverse(head)
        then: reverse == null
    }

    def "Reverse list - with 1 node"() {
        def head = new SingleLinkNode(null, "1")
        when: def reverse = reverse(head)
        then: reverse == head
    }

    def "Reverse list - with 2 nodes"() {
        SingleLinkNode node2 = new SingleLinkNode(null, "2")
        SingleLinkNode node1 = new SingleLinkNode(node2, "1")
        when: def reverse = reverse(node1)
        then: reverse == node2
    }

    def "Reverse list - with 3 nodes"() {
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def reverse = reverse(node1)
        then: reverse == node3
    }

    def "Reverse list - with 4 or more nodes"() {
        def node6 = new SingleLinkNode(null, "3")
        def node5 = new SingleLinkNode(node6, "3")
        def node4 = new SingleLinkNode(node5, "3")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def reverse = reverse(node1)
        then: reverse == node6
    }
}
