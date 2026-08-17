package djnz.leetcode

// https://leetcode.com/problems/intersection-of-two-arrays/description/
object T349 {

  def intersection(nums1: Array[Int], nums2: Array[Int]): Array[Int] =
    (nums1.toSet intersect nums2.toSet)
      .toArray

}
