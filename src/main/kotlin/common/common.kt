package common

import java.io.File

fun resourceFile(name: String): File {
    return File(object {}.javaClass.getResource(name).toURI())
}

data class XY(val x: Int, val y: Int) {
    operator fun plus(other: XY) = XY(x + other.x, y + other.y)
    operator fun minus(other: XY) = XY(x - other.x, y - other.y)

    fun up(): XY = XY(x, y - 1)
    fun upLeft(): XY = XY(x - 1, y - 1)
    fun upRight(): XY = XY(x + 1, y - 1)
    fun left(): XY = XY(x - 1, y)
    fun right(): XY = XY(x + 1, y)
    fun down(): XY = XY(x, y + 1)
    fun downLeft(): XY = XY(x - 1, y + 1)
    fun downRight(): XY = XY(x + 1, y + 1)

    fun neighbors(): List<XY> = listOf(left(), up(), right(), down())

    fun neighbors8(): List<XY> =
        listOf(
            upLeft(), up(), upRight(),
            left(), right(),
            downLeft(), down(), downRight()
        )

    fun <T> neighbors8(array: Array<Array<T>>): List<T> =
        neighbors8().filter { it.isValid(array) }.map { array[it.y][it.x] }

    fun <T> isValid(array: Array<Array<T>>): Boolean = y in array.indices && x in array[0].indices
    fun isValid(width: Int, height: Int): Boolean = y in 0..<height && x in 0..<width
}

fun <T> value(array: Array<Array<T>>, xy: XY): T = array[xy.y][xy.x]
fun <T> setValue(array: Array<Array<T>>, xy: XY, value: T) {
    array[xy.y][xy.x] = value
}

fun <T> forEach(array: Array<Array<T>>, action: (XY) -> Unit) {
    for (y in array.indices) {
        for (x in array[y].indices) {
            action(XY(x, y))
        }
    }
}

data class XLY(val x: Long, val y: Long) {
    operator fun plus(other: XLY): XLY = XLY(x + other.x, y + other.y)
}

val directions: Array<XY> = arrayOf(
    /* left  */ XY(-1, 0),
    /* up    */ XY(0, -1),
    /* right */ XY(1, 0),
    /* down  */ XY(0, 1)
)
