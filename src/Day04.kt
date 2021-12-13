fun main() {

    class Grid(
        val numbers: Array<IntArray?>,
    )

    fun hasgridwon(grid: Grid, drawnNumbers: List<Int>): Boolean{
        // Has this grid won yet?
        var won = false

        // Rows?
        // TODO a lot of iteration here, can probably be done better
        for (row in grid.numbers) {
            var rowGood = true
            for (num in row!!){
                if (num !in drawnNumbers) {
                    rowGood = false
                    break
                }
            }
            if (rowGood) {
                won = true
                break
            }
            for ((colum_num, row) in grid.numbers.withIndex()){  // grid always a square,
                // are columns good?
                var columnGood = true
                for (i in 0..grid.numbers[0]!!.size-1){
                    if (grid.numbers[i]!![colum_num] !in drawnNumbers) {
                        columnGood = false
                        break
                    }
                }
                if (columnGood) {
                    won = true
                    break
                }
            }
        }
        return won
    }

    fun gridScore(grid: Grid, lastCalled: Int, drawnNumbers: List<Int>): Int{
        var score = 0
        for (row in grid.numbers){
            for (num in row!!){
                if (num !in drawnNumbers){
                    score += num
                }
            }
        }
        return score * lastCalled
    }

    fun part1(input: List<String>): Int {
        val selectedNumbers = input[0].split(",").map { it.toInt() }
        var grids: MutableList<Grid> = mutableListOf()

        for (i in 2..input.size step 6){  // Each grid takes 6 lines to define
            var numbers: Array<IntArray?> = arrayOfNulls<IntArray>(5)
            for (j in 0..4){
                val lineNumbers = input[i+j].split(" ").filter { it != "" }.map { it.toInt() }
                numbers[j] = lineNumbers.toIntArray()
            }
            val newGrid = Grid(numbers)
            grids.add(newGrid)
        }

        // Now we have parsed the input, start to think if we have bingo
        for (chosenAmt in 5..selectedNumbers.size) {
            val drawnNumbers = selectedNumbers.subList(0, chosenAmt)  // The numbers that have been drawn so far

            for (grid in grids){
                if (hasgridwon(grid, drawnNumbers)) {
                    val score = gridScore(grid = grid, lastCalled = drawnNumbers.last(), drawnNumbers = drawnNumbers)
                    return score
                }
            }
        }
        return -1  // If no grid wins ever
    }

    fun part2(input: List<String>): Int {
        val selectedNumbers = input[0].split(",").map { it.toInt() }

        var grids: MutableList<Grid> = mutableListOf()
        for (i in 2..input.size step 6){  // Each grid takes 6 lines to define
            var numbers: Array<IntArray?> = arrayOfNulls<IntArray>(5)
            for (j in 0..4){
                val lineNumbers = input[i+j].split(" ").filter { it != "" }.map { it.toInt() }
                numbers[j] = lineNumbers.toIntArray()
            }
            val newGrid = Grid(numbers)
            grids.add(newGrid)
        }

        // Now we have parsed the input, start to think if we have bingo
        for (chosenAmt in 5..selectedNumbers.size) {
            val drawnNumbers = selectedNumbers.subList(0, chosenAmt)  // The numbers that have been drawn so far

            var unfinishedGrids: MutableList<Grid> = mutableListOf<Grid>()

            for (grid in grids){
                if (hasgridwon(grid, drawnNumbers)) {
                    if (grids.size == 1){
                        // If we are on the last grid and it has won, thats the answer
                        return gridScore(grid = grid, lastCalled = drawnNumbers.last(), drawnNumbers = drawnNumbers)
                    }
                } else {
                    unfinishedGrids.add(grid)
                }
            }
            grids = unfinishedGrids
        }
        return -1
    }

    // Change these for each day
    val day: String = "04"
    val testPart1Expected = 4512
    val testPart2Expected = 1924

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
