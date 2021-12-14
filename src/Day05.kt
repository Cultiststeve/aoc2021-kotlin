fun main() {

    fun incrementHashmap(map: MutableMap<Pair<Int, Int>, Int>, key: Pair<Int, Int>){
        val count = map.getOrDefault(key, 0)
        map[key] = count + 1
    }

    fun printmap(map: MutableMap<Pair<Int, Int>, Int>, x: Int, y: Int){
        println("Map:")
        for (yCurr in 0 until y){
            for (xCurr in 0 until x){
                print(map.getOrDefault(Pair(xCurr, yCurr), "."))
            }
            print("\n")
        }
    }

    fun part1(input: List<String>): Int {
        val cordinateList = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (line in input){
            val firstCords = line.split(" ")[0].split(",")
            val secondCords = line.split(" ")[2].split(",")
            cordinateList.add(Pair(
                Pair(firstCords[0].toInt(), firstCords[1].toInt()),
                Pair(secondCords[0].toInt(), secondCords[1].toInt())
            )
            )
        }
        val unDiagonalCordinates = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (cordinatePair in cordinateList){
            if ((cordinatePair.first.first == cordinatePair.second.first) //x
            or (cordinatePair.first.second == cordinatePair.second.second)) { // y
                unDiagonalCordinates.add(cordinatePair)
            }
        }

        val ventMap = HashMap<Pair<Int, Int>, Int>()

        for (cordinatePair in unDiagonalCordinates){
            // Traverse the line
            if (cordinatePair.first.first == cordinatePair.second.first) {
                for (y in minOf(cordinatePair.first.second, cordinatePair.second.second) ..
                        maxOf(cordinatePair.first.second, cordinatePair.second.second)
                ) {
                    incrementHashmap(ventMap, Pair(cordinatePair.first.first, y))
                }
            } else {
                for (x in minOf(cordinatePair.first.first, cordinatePair.second.first) ..
                        maxOf(cordinatePair.first.first, cordinatePair.second.first)
                ) {
                    incrementHashmap(ventMap, Pair(x, cordinatePair.first.second))
                }
            }
        }

        printmap(ventMap, 10, 10)

        // How many 2+
        var dangerZones = 0
        for (spotVal in ventMap.values){
            if (spotVal > 1){
                dangerZones += 1
            }
        }

        return dangerZones
    }

    fun part2(input: List<String>): Int {
        val cordinateList = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (line in input){
            val firstCords = line.split(" ")[0].split(",")
            val secondCords = line.split(" ")[2].split(",")
            cordinateList.add(Pair(
                Pair(firstCords[0].toInt(), firstCords[1].toInt()),
                Pair(secondCords[0].toInt(), secondCords[1].toInt())
            )
            )
        }

        val unDiagonalCordinates = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        for (cordinatePair in cordinateList){
            if ((cordinatePair.first.first == cordinatePair.second.first) //x
                or (cordinatePair.first.second == cordinatePair.second.second)) { // y
                unDiagonalCordinates.add(cordinatePair)
            }
        }

        val ventMap = HashMap<Pair<Int, Int>, Int>()

        for (cordinatePair in cordinateList){
            // Traverse the line
            val minX = minOf(cordinatePair.first.first, cordinatePair.second.first)
            val maxX = maxOf(cordinatePair.first.first, cordinatePair.second.first)
            val minY = minOf(cordinatePair.first.second, cordinatePair.second.second)
            val maxY = maxOf(cordinatePair.first.second, cordinatePair.second.second)
            val lineLength = maxOf(maxX - minX, maxY-minY)

            for (i in 0 .. lineLength){
                if (minX == maxX){
                    //Horizontal
                    incrementHashmap(ventMap, Pair(minX, minY + i))
                } else if (minY == maxY){
                    //vertical
                    incrementHashmap(ventMap, Pair(minX + i, minY ))

                } else if (cordinatePair.first.first < cordinatePair.second.first){
                    // left to
                    if (cordinatePair.first.second < cordinatePair.second.second){
                        // top
                        incrementHashmap(ventMap, Pair(minX + i, minY + i))
                    } else {
                        // bottom
                        incrementHashmap(ventMap, Pair(minX + i, maxY - i))
                    }
                } else {
                    // right to
                    if (cordinatePair.first.second < cordinatePair.second.second){
                        // top
                        incrementHashmap(ventMap, Pair(minX + i, maxY - i))
                    } else {
                        // bottom
                        incrementHashmap(ventMap, Pair(maxX - i, maxY - i))
                    }
                }
            }
        }

        printmap(ventMap, 10, 10)

        // How many 2+
        var dangerZones = 0
        for (spotVal in ventMap.values){
            if (spotVal > 1){
                dangerZones += 1
            }
        }

        return dangerZones
    }

    // Change these for each day
    val day: String = "05"
    val testPart1Expected = 5
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
