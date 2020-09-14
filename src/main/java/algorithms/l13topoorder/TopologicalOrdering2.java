package algorithms.l13topoorder;

import java.util.ArrayDeque;
import java.util.Deque;

public class TopologicalOrdering2 {

  public static void main(String[] args) {

    int[][] graph = {
        {0,0,0,1,1,0,0,0},
        {0,0,0,1,0,0,0,0},
        {0,0,0,0,1,0,0,1},
        {0,0,0,0,0,1,1,1},
        {0,0,0,0,0,0,1,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}};

    Deque<Integer> noIncoming = new ArrayDeque<Integer>();
    int[] topoSort = new int[graph.length];
    int topoSortIndex = 0; //index at which we should insert into the array topoSort
    int[] countOfIncoming = new int[graph.length];
    int i,j,node;

    for(i=0;i<graph.length;i++) {
      for(j=0;j<graph.length;j++) {
        if(graph[j][i]==1) {
          countOfIncoming[i]++;
        }
      }

      if(countOfIncoming[i]==0) {
        noIncoming.add(i);
      }
    }

    while(!noIncoming.isEmpty()) {
      node=noIncoming.remove();
      for(i=0;i<graph.length;i++) {
        if(graph[node][i]==1) {
          graph[node][i]=0;
          countOfIncoming[i]--;
          if(countOfIncoming[i]==0) {
            noIncoming.add(i);
          }
        }
      }
      topoSort[topoSortIndex++]=node;
    }

    if(topoSortIndex!=topoSort.length) {
      System.out.println("Cycles Exist");
    }
    else {
      for(i=0;i<topoSort.length;i++) {
        System.out.print(topoSort[i]+",");
      }
    }
  }
}
