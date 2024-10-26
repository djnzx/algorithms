package djnz.interviews.nixa

object Task1 {

  /*
   * Complete the 'fetchItemsToDisplay' function below.
   *
   * The function is expected to return a STRING_ARRAY.
   * The function accepts following parameters:
   *  1. 2D_STRING_ARRAY items
   *  2. INTEGER sortParameter
   *  3. INTEGER sortOrder
   *  4. INTEGER itemsPerPage
   *  5. INTEGER pageNumber
   */

  def fetchItemsToDisplay(
    items0: Array[Array[String]],
    sortParameter: Int,
    sortOrder: Int,
    itemsPerPage: Int,
    pageNumber: Int,
  ): Array[String] = {

    case class Item(name: String, rel: Int, price: Int)
    object Item {
      def from(line: Array[String]) = line match {
        case Array(name, rel, price) => Some(Item(name, rel.toInt, price.toInt))
        case _                       => None
      }
    }

    val items: Array[Item] = items0.flatMap(Item.from)

    val sorted = (sortParameter, sortOrder) match {
      case (0, 0) => items.sortBy(_.name)
      case (0, 1) => items.sortBy(_.name)(Ordering.String.reverse)
      case (1, 0) => items.sortBy(_.rel)
      case (1, 1) => items.sortBy(_.rel)(Ordering.Int.reverse)
      case (2, 0) => items.sortBy(_.price)
      case (2, 1) => items.sortBy(_.price)(Ordering.Int.reverse)
      case _      => sys.error("wrong combination")
    }

    val from = itemsPerPage * (pageNumber - 1)
    val until = from + itemsPerPage

    sorted
      .map(_.name)
      .slice(from, until)
  }

}
