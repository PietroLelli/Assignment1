package u04lab.polyglot.a01b

import scala.jdk.javaapi.OptionConverters
import u04lab.polyglot.{OptionToOptional, Pair}
import u04lab.code.Option
import u04lab.code.Option.*
import u04lab.code.List
import u04lab.code.List.*
import scala.util.Random

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */

private trait Square:
  def position: Pair[Int, Int]
  def hasMine: Boolean
  def hasMine_=(state: Boolean): Unit
  def isAdjacent(cell: Square): Boolean

object Square:
  def apply(position: Pair[Int, Int]): Square =
    SquareImpl(position, false)

  //Cosa cambia se metto override o no?
  //Cosa cambia tra val, var, def?
  //Cosa comporta riscrivere la def con _ indicando state?
  case class SquareImpl(position: Pair[Int, Int], override var hasMine: Boolean) extends Square :
    override def isAdjacent(cell: Square): Boolean =
      Math.abs(position.getX - cell.position.getX) <= 1 &&
        Math.abs(position.getY - cell.position.getY) <= 1


class LogicsImpl2(private val size: Int, private val mines: Int) extends Logics:

  private var cells: List[Square] = empty
  //private var random: Random = Random(size * size)
  private var hitCells: List[Square] = empty

  //private def getRandomPosition(): Pair[Int, Int] = Pair(random.nextInt(size), random.nextInt(size))
  private def numberOfAdjacentMine(cell: Square): Int =
    length(filter(cells)(c => c.isAdjacent(cell) && c.hasMine))

  for
    i <- 0 until size
    j <- 0 until size
  do
    cells = append(cells, cons(Square(Pair(i, j)), empty))

  List.apply(List.getRandoms(cells)(mines)){_.hasMine = true}

  def hit(x: Int, y: Int): java.util.Optional[Integer] =
    val cell: Option[Square] = List.get(filter(cells)(c => c.position.equals(Pair(x, y))), 0)
    cell match
      case None() => java.util.Optional.empty()
      case Some(c) if c.hasMine => java.util.Optional.empty()
      case Some(c) if !c.hasMine => {
        List.append(hitCells, cons(Option.get(cell), Nil()))
        java.util.Optional.of(numberOfAdjacentMine(c))
      }

  def won: Boolean = length(hitCells) + mines == length(cells)
