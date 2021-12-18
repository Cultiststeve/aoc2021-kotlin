fun main() {

    val digitRepresentation = mapOf<Int, List<Char>>(
        0 to listOf('a', 'b', 'c', 'e', 'f', 'g'),          // 6
        1 to listOf('c', 'f'),                      // 2
        2 to listOf('a', 'c', 'd', 'e', 'g'),           // 5
        3 to listOf('a', 'c', 'd', 'f', 'g'),           // 5
        4 to listOf('b', 'c', 'd', 'f'),            //4
        5 to listOf('a', 'b', 'd', 'f', 'g'),           // 5
        6 to listOf('a', 'b', 'd', 'e', 'f', 'g'),          // 6
        7 to listOf('a', 'c', 'f'),                 // 3
        8 to listOf('a', 'b', 'c', 'd', 'e', 'f', 'g'),         //7
        9 to listOf('a', 'b', 'c', 'd', 'f', 'g'),          // 6
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
        var answerTotal = 0
        for (line in input) {
            val uniqueSignalPatternList = line.split("|")[0].split(" ").subList(0, 10).map { it.toSortedSet() }
            val digitOutputList = line.split("|")[1].split(" ").subList(1, 5).map { it.toSortedSet() } //List of strings

            val uniqueSignalToDigitMap = mutableMapOf<Int, List<Char>>()

            val uniqueSignalPatternSizeMap = mutableMapOf<Int, List<Char>>()  // For this line, build a mapping of size to chars
            for (uniqueSignal in uniqueSignalPatternList){
                // This is only usefull for 1,4,7,8 as they are unique
                // Other values will overwrite
               uniqueSignalPatternSizeMap[uniqueSignal.size] = uniqueSignal.toList()

                //1, 4, 7,8 are unique
                when (uniqueSignal.size) {
                    digitRepresentation[1]!!.size -> uniqueSignalToDigitMap[1] = uniqueSignal.toList()
                    digitRepresentation[4]!!.size -> uniqueSignalToDigitMap[4] = uniqueSignal.toList()
                    digitRepresentation[7]!!.size -> uniqueSignalToDigitMap[7] = uniqueSignal.toList()
                    digitRepresentation[8]!!.size -> uniqueSignalToDigitMap[8] = uniqueSignal.toList()
                }
            }


            val segmentMappingToCanonical = mutableMapOf<Char, Char>()  // Map the "reference" char pos to actual for this line

            // Whats in 7 but not 1 = a
            for (char in uniqueSignalPatternSizeMap[digitRepresentation[7]!!.size]!!){
                if (!uniqueSignalPatternSizeMap[digitRepresentation[1]!!.size]!!.contains(char)){
                    segmentMappingToCanonical['a'] = char
                }
            }
            // F is in all but one, which?
            for (char in digitRepresentation[8]!!){
                var timesFound = 0
                for (charList in uniqueSignalPatternList){
                    if (char in charList){
                        timesFound += 1
                    }
                }
                if (timesFound == 9){
                    segmentMappingToCanonical['f'] = char
                }
            }
            // Now we have a and f, can solve C
            for (char in uniqueSignalPatternSizeMap[digitRepresentation[1]!!.size]!!){
                if (char != segmentMappingToCanonical['f']){
                    segmentMappingToCanonical['c'] = char
                }
            }
            // We now have canonical a, c, f
            // we can identify 6, only display with 6 chars and no c
            for (signal in uniqueSignalPatternList){
                if (signal.size == digitRepresentation[6]!!.size){
                    // If its the right size
                    if (segmentMappingToCanonical['c'] !in signal){
                        // And the local c mapping not in it
                        uniqueSignalToDigitMap[6] = signal.toList()
                    }
                }
            }
            // Whats in 4  and only in 1 of [0, 9]
            // Its d
            for (char in uniqueSignalToDigitMap[4]!!){
                var inBoth = true
                for (signal in uniqueSignalPatternList){
                    if (signal.size != 6){
                        continue
                    }
                    if (signal.toList() == uniqueSignalToDigitMap[6]){
                        continue
                    }
                    //For bcdf for 0 and 9
                    if (char !in signal){
                        segmentMappingToCanonical['d'] = char
                    }
                }
            }
            // Now we have a,c,d,f
            // digiit 3 has acdf, and g
            // only 5 size with acdf
            for (signal in uniqueSignalPatternList){
                if (signal.size != digitRepresentation[3]!!.size){
                    continue
                }
                // If any of our recognised values are not in signal (acdf)
                if (segmentMappingToCanonical.values.any { it !in signal }){
                    continue
                }

                // Now we only have 3
                uniqueSignalToDigitMap[3] = signal.toList()
                for (char in signal){
                    if (char !in segmentMappingToCanonical.values){
                        segmentMappingToCanonical['g'] = char
                    }
                }
            }
            // Now we have a,c,d,f, g
            // Just need b and e
            // 4 has only b unrecognised
            for (char in uniqueSignalToDigitMap[4]!!){
                if (char !in segmentMappingToCanonical.values){
                    segmentMappingToCanonical['b'] = char
                }
            }
            // Finaly, e, using 8
            for (char in uniqueSignalToDigitMap[8]!!){
                if (char !in segmentMappingToCanonical.values){
                    segmentMappingToCanonical['e'] = char
                }
            }

            // Reverse mapping so we can put in local value and get cannonical
            val segmentMappingFromCanonical = segmentMappingToCanonical.entries.associateBy({ it.value }) { it.key }

            // Now we know the mapping for this line!
            var answer: Int = 0
            for (digit in digitOutputList){
                var canonicalDigitList = digit.toList().map{segmentMappingFromCanonical[it]}.toList()
                for ((digitKey, digitRep) in digitRepresentation){
                    if (canonicalDigitList.sortedByDescending { it } == digitRep.sortedByDescending { it }){
                        answer = "$answer$digitKey".toInt()
                    }
                }
            }
            answerTotal += answer


        }  // End for all lines in input
        return answerTotal.toLong()
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
