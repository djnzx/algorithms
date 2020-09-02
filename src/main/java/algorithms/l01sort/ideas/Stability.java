package algorithms.l01sort.ideas;

/**
 * Stability: we SHOULD never change order elements with the same values
 *
 *
 * is about preserving initial ordering
 * of elements with the same values:
 * A1, B, A2, C => A1, A2, B, C, nothing else
 *
 * Merge Sort, Insertion Sort - YES
 * Selection Sort, Shell Sort - NO, they change elements to far from each other.
 *
 * never run unstable after stable, it can break initial ordering
 *
 */
public class Stability {
}
