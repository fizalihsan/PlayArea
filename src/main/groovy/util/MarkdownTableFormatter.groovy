

def str = """
| Type | Sub-type | Problem |
| ---- | -------- | ------- |
| Number | Fibonacci | [Find nth fibonacci number](https://github.com/fizalihsan/FunPrograms/blob/master/src/main/java/com/algos/numbers/Fibonacci.java) (with and without recursion) |
| | | Frog jumpting stairs  - pg 81|
| | | Rectangle problem  - pg 81|
| Search | Binary Search| Binary Search array (in Github) |
| | | Binary search matrix - pg 37|
| | | Binary search in partially sorted array (HarryHe pg 89 - unable to follow)|
| | | Depth-first search |
| Sorting| Array Sorting| Quick sort - *from Sedgewick book* (in Github) |
| | | Selection sort (in Github)|
| | | Shell sort|
| | | Insertion sort|
| | | Merge sort|
| | String sorting| Counting sort or key-index counting (Sedgewick pg 703)|
| | List sorting| ??|
| Backtracking | | String paths |
| | Permutations| Simple recursive method (Sedgewick) (in Github)|
"""

def colVsSize = [:]
def numOfCols = 0

str.split("\n").each{ row -> // split rows by new line

    if(row.trim().length()==0) return //skip empty lines

    def cols = row.split("\\|") // split cols by |

    if(numOfCols == 0){
        numOfCols = cols.length
    } else {
        if(cols.length != numOfCols){
            throw new RuntimeException("# of columns not same in all rows")
        }
    }

    cols.eachWithIndex{ col, i ->
        if(i!=0){//skip 1st column
            col = col.trim()
            if(col.length() > colVsSize[i]){
                colVsSize[i] = col.length()
            }
        }
    }
}

def finalString = ""

str.split("\n").each { row ->
    if(row.trim().length()==0) return //skip empty lines

    row.split("\\|").eachWithIndex { col, i ->
        if(i == 0){
            finalString += "| "
        } else {
            finalString += String.format("%-${colVsSize[i]}s", col.trim()) + " | "
        }
    }
    finalString += "\n"
}

println finalString