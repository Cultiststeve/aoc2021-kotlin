fun main() {

    val newFishValue: Long = 8
    val postSpawnValue: Long = 6

    fun incrementHashmap(map: MutableMap<Long, Long>, key: Long, incrementAmmount: Long = 1){
        val count = map.getOrDefault(key, 0)
        map[key] = count + incrementAmmount
    }

    fun calculateNextGen(currGen: HashMap<Long, Long>): HashMap<Long, Long>{
        val newGen = HashMap<Long, Long>()
        for ((key, value) in currGen){
            if (key == 0.toLong()){
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


//   fun part1(input: List<String>): Long {
//       val initialFish = input[0].split(",").map { it.toInt() }
//
//       val generationFish: MutableList<HashMap<Int, Int>> = mutableListOf()
//
//       val initialGen = hashMapOf<Int, Int>()
//       for (fishCurr in initialFish){
//           incrementHashmap(initialGen, fishCurr)
//       }
//
//       generationFish.add(initialGen)
//
//       for (i in 1..80){
//           generationFish.add(
//               calculateNextGen(generationFish[i-1])
//           )
//       }
//       return generationFish.last().values.sum()
//    }

    fun part2(input: List<String>): Long {
        val initialFish = input[0].split(",").map { it.toLong() }

        val generationFish: MutableList<HashMap<Long, Long>> = mutableListOf()

        val initialGen = hashMapOf<Long, Long>()
        for (fishCurr in initialFish){
            incrementHashmap(initialGen, fishCurr)
        }

        generationFish.add(initialGen)

        for (i in 1..256){
            generationFish.add(
                calculateNextGen(generationFish[i-1])
            )
        }
        return generationFish.last().values.sum()
    }

    // Change these for each day
    val day: String = "06"
    val testPart1Expected = 5934
    val testPart2Expected = 26984457539

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day${day}_test")
//    val testResPart1 = part1(testInput)
//    check(testResPart1 == testPart1Expected){"Test for part 1 returned ${testResPart1}, expected $testPart1Expected"}
    val input = readInput("Day${day}")
//    println("Part 1 result: ${part1(input)}")

    val testResPart2 = part2(testInput)
    check(testResPart2.toLong() == testPart2Expected){"Test for part 2 returned $testResPart2, expected $testPart2Expected"}
    println("Part 2 result: ${part2(input)}")
}
