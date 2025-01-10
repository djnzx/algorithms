package algorithms.l04linkedlist;

/**
 * Stack and Queue are actually just a Linked List:
 *
 * Stack: LIFO
 *  - push
 *  - pop
 *  - isEmpty
 *
 * Queue: FIFO
 *  - enqueue
 *  - dequeue
 *  - isEmpty
 *
 *  Stack Implementation:
 *  - consider usage Linked List or Arrays (with resize, or what to deal with overflow).
 *  and make them available for usage in different implementations.
 *
 *  in pop() implementation - consider data[n--] = null to allow GC
 *  in array resizing consider "erasing" an array
 *  on pop() if (n<=size/4) resize/2
 *
 *  Queue based on Linked List,
 *  but holding first and end pointers
 *
 *  SO => we need to have Queue and Stack,
 *  and we (highly probable) don't need to use Linked List directly.
 *  TASK1 => implement Linked List
 *  TASK2 => implement Stack by using (array and LinkedList)
 *  TASK3 => implement Queue by using (array and LinkedList)
 */
public class XQueueApp {
}
