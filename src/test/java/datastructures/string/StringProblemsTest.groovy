package datastructures.string

import spock.lang.Specification

/**
 * Comment here about the class
 * User: Fizal
 * Date: 1/1/14
 * Time: 7:05 PM
 */
class StringProblemsTest extends Specification {
    def "replaceAll - Replace 'a' with 'x' in 'abc'"() {
        when: def result = StringProblems.replaceAll("abc", 'a' as char, "x")
        then: result == "xbc"
    }

    def "replaceAll - Replace 'a' with 'x' in 'aaa'"() {
        when: def result = StringProblems.replaceAll("aaa", 'a' as char, "x")
        then: result == "xxx"
    }

    def "replaceAll - Replace 'a' with 'ab' in 'abc'"() {
        when: def result = StringProblems.replaceAll("abc", 'a' as char, "ab")
        then: result == "abbc"
    }

    def "replaceAll - Replace ' ' with '%20' in 'Hello World'"() {
        when: def result = StringProblems.replaceAll("Hello World", ' ' as char, "%20")
        then: result == "Hello%20World"
    }

}
