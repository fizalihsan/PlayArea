package util

problem5()

def problem1(){
    def total = 0
    (1..999).each { if(it%3==0 || it%5==0) total+=it}
    assert total == 233168
}

def problem2(){
    long prev = 1, curr = 2, total = 2
    while(curr < 4_000_000) {
        def tmp = prev
        prev = curr
        curr += tmp
        if(curr % 2==0) total+=curr
    }
    assert total == 4613732
}

def problem3(){
    def calculatePrimeFactors = { num ->
        def primeFactors = [] as Set
        while ((num / 2) == Math.round(num / 2)) {
            num = num / 2;
            primeFactors << 2
        }
        i = 3;
        while (num > 1) {
            if ((num / i) == Math.round(num / i)) {
                num = num / i;
                primeFactors << i
            } else {
                i += 2;
            }
        }
        return primeFactors
    }

    def maxPrimeFactor = calculatePrimeFactors(600_851_475_143L).max()
    println "Max Prime Factor ${maxPrimeFactor}"
    maxPrimeFactor == 6857
}

def problem4(){
    def isPalindrome = {str -> str == str.reverse() }
    def palindromes = [] as Set
    (100..999).each {i->
        (100..999).each { j ->
            def product = i * j
            def palindrome = isPalindrome(product.toString())
            if(palindrome) {
                println "i = $i j = $j product = $product"
                palindromes << product
            }
        }
    }
    println palindromes.max()
}

def problem5(){
    def to = 20
    def n = 1

    while(true){
        def found = true
        n++
        for (int i = 2; i < to; i++) {
            if(n%i!=0) {
                found = false
                break
            }
        }
        if (found) break
    }

    println "smallest positive number = $n"
}
