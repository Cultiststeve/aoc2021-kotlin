import kotlin.math.min

fun main() {



    fun calculatePositionCost(postion: Map<Int, Int>, desiredPosition: Long): Long{
        var cost: Long = 0
        for ((key, value) in postion){
            cost += Math.abs(desiredPosition - key) * value
        }
        return cost
    }

    
    fun calculatePositionCost2(postion: Map<Int, Int>, desiredPosition: Long): Long{
        var cost: Long = 0
        for ((key, value) in postion){
            val dist = Math.abs(desiredPosition - key)
            cost +=  (0.5 * dist * (dist + 1) * value).toLong()
        }
        return cost
    }

   fun part1(input: List<String>): Long {
       val initialPositionsList = input[0].split(",").map { it.toInt() }

       val initialPositions = initialPositionsList.groupingBy { it }.eachCount()

       val x = initialPositions.keys.minOf { it }


       var minCost: Long = Long.MAX_VALUE


       for (i in initialPositions.keys.minOf { it }..initialPositions.keys.maxOf { it }){
           minCost = min(minCost, calculatePositionCost(initialPositions, i.toLong()))
       }
       return minCost
    }

    fun part2(input: List<String>): Long {
        val initialPositionsList = input[0].split(",").map { it.toInt() }

        val initialPositions = initialPositionsList.groupingBy { it }.eachCount()

        val x = initialPositions.keys.minOf { it }


        var minCost: Long = Long.MAX_VALUE


        for (i in initialPositions.keys.minOf { it }..initialPositions.keys.maxOf { it }){
            minCost = min(minCost, calculatePositionCost2(initialPositions, i.toLong()))
        }
        return minCost
    }

    // Change these for each day
    val day: String = "07"
    val testPart1Expected: Long = 37
    val testPart2Expected: Long = 168

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected){"Test for part 1 returned ${testResPart1}, expected $testPart1Expected"}
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

    val testResPart2 = part2(testInput)
    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
    println("Part 2 result: ${part2(input)}")
}
