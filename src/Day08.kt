fun main() {

    val digitRepresentation = mapOf<Int, List<Char>>(
        0 to listOf('a', 'b', 'c', 'e', 'f', 'g'),
        1 to listOf('c', 'f'),
        2 to listOf('a', 'c', 'd', 'e', 'g'),
        3 to listOf('a', 'c', 'd', 'f', 'g'),
        4 to listOf('b', 'c', 'd', 'f'),
        5 to listOf('a', 'b', 'd', 'f', 'g'),
        6 to listOf('a', 'b', 'd', 'e', 'f', 'g'),
        7 to listOf('a', 'c', 'f'),
        8 to listOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),
        9 to listOf('a', 'b', 'c', 'd', 'f', 'g'),
    )


    fun incrementHashmap(map: MutableMap<Int, Int>, key: Int, incrementAmmount: Int = 1) {
        val count = map.getOrDefault(key, 0)
        map[key] = count + incrementAmmount
    }

    fun part1(input: List<String>): Long {

        val outputDigitFrequency = mutableMapOf<Int, Int>()  //<Length of string, freq of string>
        val easyChars = listOf<Int>(1, 4, 7, 8)
        var easyCharCount: Long = 0

        for (line in input) {
            var uniqueSignalPatternList = line.split("|")[0].split(" ")
            uniqueSignalPatternList = uniqueSignalPatternList.subList(0, 10)
            var digitOutputList = line.split("|")[1].split(" ")  //List of strings
            digitOutputList = digitOutputList.subList(1, 5)

            for (digitOutput in digitOutputList) {
                // For each string in output
                incrementHashmap(map = outputDigitFrequency, key = digitOutput.length)
            }
        }


        for (easyNum in easyChars) {  // For the digits we care about
            val sizeofNum: Int = digitRepresentation[easyNum]!!.size  // Find the size we are looking for
            easyCharCount += outputDigitFrequency.getOrDefault(sizeofNum, 0)
        }

        return easyCharCount
    }

    fun part2(input: List<String>): Long {
        for (line in input) {
            val uniqueSignalPatternList = line.split("|")[0].split(" ").subList(0, 10).map { it.toSortedSet() }
            val digitOutputList = line.split("|")[1].split(" ").subList(1, 5).map { it.toSortedSet() } //List of strings

            val uniqueSignalPatternSizeMap = mutableMapOf<Int, List<Char>>()  // For this line, build a mapping of size to chars
            for (uniqueSignal in uniqueSignalPatternList){
               uniqueSignalPatternSizeMap[uniqueSignal.size] = uniqueSignal.toList()
            }

            val segmentMappingToCanonical = mutableMapOf<Char, Char>()  // Map the "reference" char pos to actual for this line

            // Whats in 7 but not 1 = a
            for (char in uniqueSignalPatternSizeMap[digitRepresentation[7]!!.size]!!){
                if (!uniqueSignalPatternSizeMap[digitRepresentation[1]!!.size]!!.contains(char)){
                    segmentMappingToCanonical['a'] = char
                }
            }
            // TODO of the two signals with size 5, whats in 2 but not 3, f
            // Whats in 7 but not in 6 = c
            for (char in uniqueSignalPatternSizeMap[digitRepresentation[7]!!.size]!!){
                if (!uniqueSignalPatternSizeMap[digitRepresentation[6]!!.size]!!.contains(char)){
                    segmentMappingToCanonical['c'] = char
                }
            }
            // Whats in 7 but not in 2 = f
            for (char in uniqueSignalPatternSizeMap[digitRepresentation[7]!!.size]!!){
                if (!uniqueSignalPatternSizeMap[digitRepresentation[2]!!.size]!!.contains(char)){
                    segmentMappingToCanonical['f'] = char
                }
            }

        }
        return -1
    }

    // Change these for each day
    val day: String = "08"
    val testPart1Expected: Long = 26
    val testPart2Expected: Long = 61229




    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected) { "Test for part 1 returned ${testResPart1}, expected $testPart1Expected" }
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

    val smallTestInput = readInput("Day${day}_test_small")
    val testResSmallPart2 = part2(smallTestInput)
    check(testResSmallPart2.toInt() == 5353)

    val testResPart2 = part2(testInput)
    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
    println("Part 2 result: ${part2(input)}")
}
