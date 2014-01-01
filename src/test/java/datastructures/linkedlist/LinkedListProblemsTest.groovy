package datastructures.linkedlist

import spock.lang.Specification

import static datastructures.linkedlist.LinkedListProblems.*
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

    def "Loop check using extra datastructure - 1"(){
        def node1 = new SingleLinkNode(null, "1")
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: !loop
    }

    def "Loop check using extra datastructure - 1 -> 1"(){
        def node1 = new SingleLinkNode(null, "1")
        node1.setNext(node1)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: !loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 1"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node2.setNext(node1)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: !loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 1"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node1)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 2"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: !loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 4 -> 1"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node1)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 4 -> 2"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node2)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 4 -> 3"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node3)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check using extra datastructure - 1 -> 2 -> 3 -> 2,4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckUsingExtraDataStructure(node1)
        then: loop
    }

    def "Loop check by Pollard- 1"(){
        def node1 = new SingleLinkNode(null, "1")
        when: def loop = loopCheckByPollard(node1)
        then: !loop
    }

    def "Loop check by Pollard- 1 -> 1"(){
        def node1 = new SingleLinkNode(null, "1")
        node1.setNext(node1)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByPollard(node1)
        then: !loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 1"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node2.setNext(node1)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByPollard(node1)
        then: !loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 1"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node1)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 2"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByPollard(node1)
        then: !loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 4 -> 1"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node1)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 4 -> 2"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node2)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 4 -> 3"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node3)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }

    def "Loop check by Pollard - 1 -> 2 -> 3 -> 2,4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckByPollard(node1)
        then: loop
    }


    def "Loop check by Brent- 1"(){
        def node1 = new SingleLinkNode(null, "1")
        when: def loop = loopCheckByBrent(node1)
        then: !loop
    }

    def "Loop check by Brent- 1 -> 1"(){
        def node1 = new SingleLinkNode(null, "1")
        node1.setNext(node1)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByBrent(node1)
        then: !loop
    }

    def "Loop check by Brent - 1 -> 2 -> 1"(){
        def node2 = new SingleLinkNode(null, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node2.setNext(node1)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByBrent(node1)
        then: !loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 1"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node1)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 2"(){
        def node3 = new SingleLinkNode(null, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        when: def loop = loopCheckByBrent(node1)
        then: !loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 4 -> 1"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node1)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 4 -> 2"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node2)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 4 -> 3"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node4.setNext(node3)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "Loop check by Brent - 1 -> 2 -> 3 -> 2,4"(){
        def node4 = new SingleLinkNode(null, "4")
        def node3 = new SingleLinkNode(node4, "3")
        def node2 = new SingleLinkNode(node3, "2")
        def node1 = new SingleLinkNode(node2, "1")
        node3.setNext(node2)
        when: def loop = loopCheckByBrent(node1)
        then: loop
    }

    def "List add - [1] + [1]"(){
        def list1 = new SingleLinkNode(null, "1")
        def list2 = new SingleLinkNode(null, "1")
        when: def result = add(list1, list2)
        then: Integer.valueOf(concat(result)) == 2
    }

    def "List add - [1 -> 1] + [1]"(){
        def list1 = new SingleLinkNode(new SingleLinkNode(null, "1"), "1")
        def list2 = new SingleLinkNode(null, "1")
        when: def result = add(list1, list2)
        then: Integer.valueOf(concat(result)) == 12
    }

    def "List add - [9 -> 9] + [9]"(){
        def list1 = new SingleLinkNode(new SingleLinkNode(null, "9"), "9")
        def list2 = new SingleLinkNode(null, "9")
        when: def result = add(list1, list2)
        then: Integer.valueOf(concat(result)) == 108
    }

    def "Concat"(){
        when: def result = concat(new SingleLinkNode(new SingleLinkNode(new SingleLinkNode(null, "7"), "8"), "9"))
        then: result == "987"
    }

    def "print in reverse - using concat"(){
        when: def result = printInReverseViaStringReversal(new SingleLinkNode(new SingleLinkNode(new SingleLinkNode(null, "7"), "8"), "9"))
        then: result == "789"
    }

    def "print in reverse - using recursion"(){
        def writer = new StringWriter();
        when: printInReverseViaRecursion(new SingleLinkNode(new SingleLinkNode(new SingleLinkNode(null, "7"), "8"), "9"), writer)
        then: writer.toString() == "789"
    }
}
