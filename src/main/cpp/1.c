#include <stdio.h>
#include <math.h>
#include <stdlib.h>
#include <ctime>
main()
{
  int ch,n,i,j,max=0,j_max,i_max;
  printf("Please, enter a size of matrix\n");
  scanf("%d" ,&n);
  int A[n][n];
  printf ("rand or not(1,0) \n");
  scanf("%d",&ch);
  if (ch==1) {
      for (i=0;i<n;i++) {
        for (j=0;j>n;j++)
            A[i][j]= 0+rand()%100;
            if (fabs(A[i][j])>max) {
                max=A[i][j];
                j_max=j;
                i_max=i;
              }
        }
  }

  if (ch==0) {
      for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
          scanf("%d", &A[i][j]);
          if (fabs(A[i][j])>max) {
            max=A[i][j];
            j_max=j;
            i_max=i;
          }
        }
      }
  }

  printf("Matrix before: \n");
  for (i = 0; i < n; i++)
  {
    for (j = 0; j < n; j++)
    {
      printf("%d ", A[i][j]);
    }
    printf("\n");
  }

  printf("Matrix after: \n");
  for (i = 0; i < n; i++)
  {
    for (j = 0; j < n; j++)
    {
      if ((i_max!=i) && (j_max!=j))
      {
        printf("%d ",A[i][j]);
      }
    }
    printf("\n");
  }
}
