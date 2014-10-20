---
layout: post
title:  "R Programming "
date:   2014-10-18 22:18:03
categories: jekyll update
---


##Basic Data Types
R has five basic or “atomic” classes of objects:
1. **character**
* **numeric** (real numbers) 
  * Numbers in R as numeric objects by default. (*Double precision real numbers*)
  * `Inf` represents infinity. 
  * `NA` & `NaN` represents an undefined value and not a number.
    * `is.na()` and `is.nan()` are used to test objects.
    * `NA` values have a class also, so there are integer `NA`, character `NA`, etc.
    * A `NaN` value is also `NA` but the converse is not true.
  * Attributes of an object like length and other metadata can be access using the `attributes()` function.
* **integer** -
 `1` is a numeric object. `1L` is an integer.
* **complex**
* **logical** (True/False)

##Complex Data Types
###Vectors
The most basic object is a vector. 
*  A vector can only contain objects of the same class.
* BUT: The one exception is a list, which is represented as a vector but can contain objects of
different classes (indeed, that’s usually why we use them)
* Empty vectors can be created with the `vector()` function.
* Vector examples
{% highlight r %}
> x <- c(0.5, 0.6) ## numeric
> x <- c(TRUE, FALSE) ## logical
> x <- c(T, F) ## logical
> x <- c("a", "b", "c") ## character
> x <- 9:29 ## integer
> x <- c(1+0i, 2+4i) ## complex
{% endhighlight %}

* Lists are a special type of vector that can contain elements of different classes.
{% highlight r %}
> x <- list(1, "a", TRUE, 1 + 4i) 
{% endhighlight %}

* **Mixing Objects**  
When different objects are mixed in a vector, *coercion* occurs so that every element in the vector is
of the same class.
{% highlight r %}
> y <- c(1.7, "a") ## character
> y <- c(TRUE, 2) ## numeric
> y <- c("a", TRUE) ## character
{% endhighlight %}
* **Explicit Coercion **  
Objects can be explicitly coerced from one class to another using the `as.*()` functions, if available.
{% highlight r %}
> x <- 0:6
> class(x)
[1] "integer"
> as.numeric(x)
[1] 0 1 2 3 4 5 6
> as.logical(x)
[1] FALSE TRUE TRUE TRUE TRUE TRUE TRUE
> as.character(x)
[1] "0" "1" "2" "3" "4" "5" "6"
{% endhighlight %}
* **Nonsensical coercion results in *NAs* **
{% highlight r  %}
> x <- c("a", "b", "c")
> as.numeric(x)
[1] NA NA NA
Warning message:
NAs introduced by coercion
> as.logical(x)
[1] NA NA NA
> as.complex(x)
[1] 0+0i 1+0i 2+0i 3+0i 4+0i 5+0i 6+0i
{% endhighlight %}
* **Matrices**
Matrices are vectors with a *dimension* attribute. The dimension attribute is itself an integer vector of
length 2 (nrow, ncol).
{% highlight r %}
> m <- matrix(nrow = 2, ncol = 3) 
> m
 [,1] [,2] [,3]
[1,] NA NA NA
[2,] NA NA NA
> dim(m)
[1] 2 3
> attributes(m)
$dim
[1] 2 3
{% endhighlight %}
* **cbind-ing and rbind-ing**
Matrices can be created by column-binding or row-binding with `cbind()` and `rbind()`.
{% highlight r  %}
> x <- 1:3
> y <- 10:12
> cbind(x, y)
 x y 
[1,] 1 10
[2,] 2 11
[3,] 3 12
> rbind(x, y) 
 [,1] [,2] [,3]
x 1 2 3
y 10 11 12
{% endhighlight %}

### Factors
* Factors are nothing but enumeration data types used to represent categorical data.
* Can be ordered or unordered.
* Factor can be thought of as an integer vector where each integer has a *label*. 
{% highlight r  %}
> x <- factor(c("yes", "yes", "no", "yes", "no")) 
> x
[1] yes yes no yes no
Levels: no yes
> table(x) 
x
no yes 
2 3
> unclass(x)
[1] 2 2 1 2 1
attr(,"levels")
[1] "no" "yes"
{% endhighlight %}


### Data Frames
* A special type of list where every list is of same length.
* Unlike matrices, data frames can store different classes of objects in each column.
* To create - `read.table()` or `read.csv()`
* To convert to a matrix - `data.matrix()`
{% highlight r  %}
> x <- data.frame(foo = 1:4, bar = c(T, T, F, F)) 
> x
 foo bar
1 1 TRUE
2 2 TRUE
3 3 FALSE
4 4 FALSE
> nrow(x)
[1] 4
> ncol(x)
[1] 2
{% endhighlight %}


##Appendix

###Command Reference 
{% highlight r linenos %}
> ls() or ls(all.names = TRUE) # Lists all variables/objects defined in the session
> setwd(“c:/xyz”) # sets working directory
> getwd() # Gets working directory
> runif(8) # generates 8 random numbers
> x <- 9 # assigns 9 to object x in workspace
> x # prints the value of x
> rm(x) # removes the object x
> rm(list=ls()) # removes all objects in workspace
> # Save & Load (Binary)
> save() # saves all objects to default file .RData. Objects still exist in memory (binary format)
> save(obj1, obj2, file=”filename”)
> load(“filename”) # loads from file to memory
> # Save & Load (Text)
> write.table(obj1, file=”filename”) # only 1 obj at a time
> load.table()
{% endhighlight %}

###Packages
* `install.packages(c("ggplot2", "devtools", "KernSmooth")` # install the collection of packages from CRAN
* `library()`        #list all available packages
* `library(package)` # loads package on to memory
* `require(package)` # loads package on to memory. Used in scripts. Returns loading status as boolean.
* `detach(package:name)` # unloads package from memory.

###Help 
* `?func`           # open help page on function 'func'
* `help(func)`      # same as above
* `apropos("foo")`  # list all functions containing string foo
* `example(foo)`    # show an example of function foo
* `vignette()`      # show available vignettes on installed packages
* `vignette("foo")` # show specific vignette
		
