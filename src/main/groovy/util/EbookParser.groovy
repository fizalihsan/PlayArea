package util

import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * This is a fun program to print book titles from http://it-ebooks.info/book
 */

def range = 5726..6000
def pool = Executors.newFixedThreadPool(1)
def latch = new CountDownLatch(range.size())
range.each { id ->
    bookDetailPrinter = {
        def url = "http://it-ebooks.info/book/$id"
//        println "At $url..."
        try {
            def text = url.toURL().getText(connectTimeout: 120_000, readTimeout: 120_000, useCaches: true)
            def title = getTextWithin(text, "<title>", "</title>")
            def year = getTextWithin(text, "datePublished\">", "</b>")

            if(year.length()>4 && year.contains("<")){
                println "$url - !!! Book not found !!!"
            } else {
                println "$url - $title - $year"
            }


        } catch (e) {
            println "$url - Failed - ${e.message}"
        } finally{
            latch.countDown()
        }
    }
    pool.submit(bookDetailPrinter as Callable)
}
latch.await()
println "************************ Shutting down ****************************"
pool.shutdownNow()


def getTextWithin(String text, String startTag, String endTag) {
    def from = text.indexOf(startTag) + startTag.length()
    def to = text.indexOf(endTag, from)
    text.substring(from, to)
}