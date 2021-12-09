fun main() {
    fun part1(input: List<String>): Int {
        val lineLen = input[0].length
        val inputSize = input.size
        val bitSums: MutableMap<Int, Int> = mutableMapOf<Int, Int>()

        for (line in input){
            for ((j, bit) in line.withIndex()){
                if (bitSums.containsKey(j)){
                    bitSums[j] = (bitSums[j]?.plus(bit.digitToInt())!!)
                } else {
                    bitSums[j] = bit.digitToInt()
                }

            }
        }
        var gamma: String = ""
        var epsilon: String = ""
        for (i in 0..lineLen-1){
            if (bitSums[i]!! > inputSize/2){
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += 1
            }
        }

        var gammaInt: Int = Integer.parseInt(gamma, 2)
        var epsilonInt: Int = Integer.parseInt(epsilon, 2)

        return gammaInt * epsilonInt
    }

    fun part2(input: List<String>): Int {
        val lineLen = input[0].length
        val inputSize = input.size
        val bitSums: MutableMap<Int, Int> = mutableMapOf<Int, Int>()

        fun filterList(input: List<String>, index: Int, mostCommon: Boolean = true): List<String>{
            // Return a subset of the list with only the most/least common values at given index
            var indexSum = 0 // Sum of values at index
            var selectFor: Char  // We Find most common, invert if required, and select on this

            for (line in input){
                indexSum += line[index].digitToInt()
            }
            if (indexSum >= input.size.toDouble() / 2) {
                // 1 is most common
                if (mostCommon){
                    selectFor = '1'
                } else {
                    selectFor = '0'
                }
            } else {
                if (mostCommon){
                    selectFor = '0'
                } else {
                    selectFor = '1'
                }
            }

            var subList: List<String> = mutableListOf()
            for (line in input){
                if (line[index] == selectFor) {
                    subList = subList + line
                }
            }
            return subList
        }  // end filterlist

        // Oxygen
        var oxygenList = input
        for (i in 0..input[0].length-1){  // for length of each number
            oxygenList = filterList(oxygenList, i)
            if (oxygenList.size == 1) {
                break
            }
        }
        // C02
        var C02List = input
        for (i in 0..input[0].length-1){  // for length of each number
            C02List = filterList(C02List, i, mostCommon = false)
            if (C02List.size == 1) {
                break
            }
        }

        return Integer.parseInt(oxygenList[0], 2) * Integer.parseInt(C02List[0], 2)
    }

    // Change these for each day
    val day: String = "03"
    val testPart1Expected = 198
    val testPart2Expected = 230

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected){"Test for part 1 returned ${testResPart1}, expected $testPart1Expected"}
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

    val testResPart2 = part2(testInput)
    check(testResPart2 == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
    println("Part 2 result: ${part2(input)}")
}
