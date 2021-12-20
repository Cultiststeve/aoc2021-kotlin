fun main() {

    fun parseInput(input: List<String>): List<List<Int>>{
        return input.map { it.map { it.digitToInt() } }
    }

    val maxHeight = 9
    val minHeight = -1


    fun part1(input: List<String>): Long {
        val inputGrid = parseInput(input)
        var dangerScore: Long = 0

        for ((y, row) in inputGrid.withIndex()){
            for ((x, spotValue) in row.withIndex()){
                // Look Left
                if (inputGrid[y].getOrElse(x-1){maxHeight} <= spotValue){
                    continue
                }
                // Right
                if (inputGrid[y].getOrElse(x+1){maxHeight} <= spotValue){
                    continue
                }
                // Up
                if (inputGrid.getOrElse(y-1){ List(row.size) {maxHeight} }[x] <= spotValue){
                    continue
                }
                // Down
                if (inputGrid.getOrElse(y+1){  List(row.size) {maxHeight} }[x] <= spotValue){
                    continue
                }
                dangerScore += 1 + spotValue
            }
        }
        return dangerScore
    }


    fun getBasinSize(inputGrid: List<List<Int>>, x: Int, y: Int, inBasin: MutableSet<Pair<Int, Int>>): Set<Pair<Int, Int>>{
        val spotValue = inputGrid[y][x]
        if (spotValue == 9) {
            return inBasin  // 9's are  not in basin, dont add or search from here
        }
        inBasin.add(Pair(x, y))


        if (inputGrid[y].getOrElse(x-1){minHeight} >= spotValue){
            if (Pair(x-1, y) !in inBasin){
                getBasinSize(inputGrid, x-1, y, inBasin)
            }
        }
        // Right
        if (inputGrid[y].getOrElse(x+1){minHeight} >= spotValue){
            if (Pair(x+1, y) !in inBasin){
                getBasinSize(inputGrid, x+1, y, inBasin)
            }
        }
        // Up
        if (inputGrid.getOrElse(y-1){ List(inputGrid[y].size) {minHeight} }[x] >= spotValue){
            if (Pair(x, y-1) !in inBasin){
                getBasinSize(inputGrid, x, y-1, inBasin)
            }
        }
        // Down
        if (inputGrid.getOrElse(y+1){  List(inputGrid[y].size) {minHeight} }[x] >= spotValue){
            if (Pair(x, y+1) !in inBasin){
                getBasinSize(inputGrid, x, y+1, inBasin)
            }
        }

        return inBasin   // +1 for current spot
    }


    fun part2(input: List<String>): Long {
        val inputGrid = parseInput(input)

        val basinSets = mutableListOf<Set<Pair<Int, Int>>>()

        for ((y, row) in inputGrid.withIndex()){
            for ((x, spotValue) in row.withIndex()){
                // Look Left
                if (inputGrid[y].getOrElse(x-1){maxHeight} <= spotValue){
                    continue
                }
                // Right
                if (inputGrid[y].getOrElse(x+1){maxHeight} <= spotValue){
                    continue
                }
                // Up
                if (inputGrid.getOrElse(y-1){ List(row.size) {maxHeight} }[x] <= spotValue){
                    continue
                }
                // Down
                if (inputGrid.getOrElse(y+1){  List(row.size) {maxHeight} }[x] <= spotValue){
                    continue
                }
                basinSets.add(getBasinSize(inputGrid, x, y, mutableSetOf()))

            }
        }
        basinSets.sortBy { it.size }
        var threeLargestScore: Long = 1
        for (i in 0..2){
            threeLargestScore *= basinSets.last().size
            basinSets.removeLast()
        }

        return threeLargestScore
    }

    // Change these for each day
    val day: String = "09"
    val testPart1Expected: Long = 15
    val testPart2Expected: Long = 1134




    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
    val testResPart1 = part1(testInput)
    check(testResPart1 == testPart1Expected) { "Test for part 1 returned ${testResPart1}, expected $testPart1Expected" }
    val input = readInput("Day${day}")
    println("Part 1 result: ${part1(input)}")

//    val smallTestInput = readInput("Day${day}_test_small")
//    val testResSmallPart2 = part2(smallTestInput)
//    check(testResSmallPart2.toInt() == 5353)

    val testResPart2 = part2(testInput)
    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
    println("Part 2 result: ${part2(input)}")
}
