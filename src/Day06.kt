fun main() {

    val newFishValue = 8
    val postSpawnValue = 6

    fun incrementHashmap(map: MutableMap<Int, Int>, key: Int, incrementAmmount: Int = 1){
        val count = map.getOrDefault(key, 0)
        map[key] = count + incrementAmmount
    }

    fun calculateNextGen(currGen: HashMap<Int, Int>): HashMap<Int, Int>{
        val newGen = HashMap<Int, Int>()
        for ((key, value) in currGen){
            if (key == 0){
                // New Fish spawn
                incrementHashmap(newGen, newFishValue, incrementAmmount = value)
                // Existing fish reset
                incrementHashmap(newGen, postSpawnValue, incrementAmmount = value)
            } else {
                incrementHashmap(newGen, key-1, incrementAmmount = value)
            }
        }
        return newGen
    }


   fun part1(input: List<String>): Int {
       val initialFish = input[0].split(",").map { it.toInt() }

       val generationFish: MutableList<HashMap<Int, Int>> = mutableListOf()

       val initialGen = hashMapOf<Int, Int>()
       for (fishCurr in initialFish){
           incrementHashmap(initialGen, fishCurr)
       }

       generationFish.add(initialGen)

       for (i in 1..80){
           generationFish.add(
               calculateNextGen(generationFish[i-1])
           )
       }
       return generationFish.last().values.sum()
    }

    fun part2(input: List<String>): Int {
        return -1
    }

    // Change these for each day
    val day: String = "06"
    val testPart1Expected = 5934
    val testPart2Expected = 12

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
