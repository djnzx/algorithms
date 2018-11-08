#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctime>

main() {
  int ch,n,i,j,max=0,j_max,i_max;
  printf("Please, enter a size of matrix\n");
  scanf("%d" , &n);
  int A[n][n];
  printf ("rand or not(1,0) \n");

    for (i = 0; i < n; i++) {
      for (j = 0; j < n; j++) {
        A[i][j]= 0+rand()%100;
      }
    }

  printf("Matrix before: \n");
  for (i = 0; i < n; i++) {
    for (j = 0; j < n; j++) {
      printf("%d ", A[i][j]);
    }
    printf("\n");
  }

}
