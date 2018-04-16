# in the given input array, eliminate all the elements that occur 3 or more times consecutively


def findRepitition(input):
    """
    If repetition found, returns the starting and ending index of the repeated values
    :return:
    """
    prev = None
    count = 0
    start = -1
    end = -1
    for i, x in enumerate(input):
        if x == prev:
            count += 1
        else:
            if count > 2:
                end = i - 1
                count = 0
                break
            count = 1
            start = i
        prev = x
    if count > 2:
        end = len(input) - 1
    return start, end


def crush(input, start, end):
    if start == -1 or end == -1 or start >= end:
        return input

    if start == 0 and end == len(input) - 1:
        return []

    return input[0:start] + input[end + 1:len(input)]


input = [1, 1, 2, 2, 2, 3, 3, 4, 4, 4]
output = []

while True:
    start, end = findRepitition(input)
    output = crush(input, start, end)
    print(output)
    if input == output:
        break
    input = output

print("Final output = {}".format(output))
