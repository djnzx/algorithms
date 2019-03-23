package myheritage;

public class Shoes01 {

  class Tail {
    public int l, r;

    boolean empty() {
      return (r==0) && (l==0);
    }
  }

  public int solution(String s) {
    if (s.length() % 2 != 0) {
      return -1;
    }
    Tail t = new Tail();
    final char L = 'L';
    final char R = 'R';
    char last = s.charAt(0);
    if (last == L) { t.l++; }
    if (last == R) { t.r++; }
    int count = 0;

    for (int i = 1; i < s.length(); i++) {
      if (t.empty()) {
        count++;
      }
      char current = s.charAt(i);
      if (last == R && current == L) {
        t.r--;
      } else if (last == L && current == R) {
        t.l--;
      } else if (last == R && current == R) {
        t.r++;
      } else if (last == L && current == L) {
        t.l++;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    Shoes01 s = new Shoes01();
    int cnt0 = s.solution("L");
    System.out.println(cnt0);
    //int cnt1 = s.solution("RLRRLLRLRRLL");
    //int cnt2 = s.solution("LLRLRLRLRLRLRR");
  }
}
/*

Ellen is a new Assembly Line Manager in a shoe factory. So far, everything has been going very smoothly for her and N shoes of the same model and size have been produced. Exactly half of them are left shoes and the other half are right shoes.

The freshly sewn shoes are standing in a line, in no particular order (i.e. with no regard to being left or right shoes). They now need to be matched into pairs and packed into boxes.

Ellen would like to assign this task to her subordinate workers. Each worker should get a distinct interval of adjacent shoes, such that the number of left shoes is equal to the number of right shoes. Each shoe must be assigned to exactly one worker.

What is the maximum number of workers that Ellen can assign to this task?

Write a function:

class Solution { public int solution(String S); }

that, given a string S of letters "L" and "R", denoting the types of shoes in line (left or right), returns the maximum number of intervals such that each interval contains an equal number of left and right shoes.

For example, given S = "RLRRLLRLRRLL", the function should return 4, because S can be split into intervals: "RL", "RRLL", "RL" and "RRLL". Note that the intervals do not have to be of the same size.

Given S = "RLLLRRRLLR", the function should return 4, because S can be split into intervals: "RL", "LLRR", "RL" and "LR".

Given S = "LLRLRLRLRLRLRR", the function should return 1.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [2..100,000];
string S consists only of the characters "R" and/or "L";
the number of letters "L" and "R" in string S is the same.
*/

/*
class Solution {

  class Tail {
    public int l, r;

    boolean empty() {
      return (r==0) && (l==0);
    }
  }

  public int solution(String s) {
    if (s.length() % 2 != 0) {
      return -1;
    }
    Tail t = new Tail();
    final char L = 'L';
    final char R = 'R';
    char last = s.charAt(0);
    if (last == L) { t.l++; }
    if (last == R) { t.r++; }
    int count = 0;

    for (int i = 1; i < s.length(); i++) {
      if (t.empty()) {
        count++;
      }
      char current = s.charAt(i);
      if (last == R && current == L) {
        t.r--;
      } else if (last == L && current == R) {
        t.l--;
      } else if (last == R && current == R) {
        t.r++;
      } else if (last == L && current == L) {
        t.l++;
      }
      last = current;
    }

    return count;
  }

  public static void main(String[] args) {
    Shoes01 s = new Shoes01();
    int expected1 = 4;
    int real1 = s.solution("RLRRLLRLRRLL");
    int expected2 = 4;
    int real2 = s.solution("LLRLRLRLRLRLRR");
    if(real1!=expected1) {s"}
    }



  }
 */