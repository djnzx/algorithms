package algorithms.l08graph;

import java.util.Deque;
import java.util.LinkedList;

/**
 * needs to be polished
 * ... or removed
 */
public class DFS_plain {
    public static void main(String[] args) {
        String[] cities = {
                "Київ", "Житомир", "Лубни", "Бориспіль", "Фастів", "Ніжин", "Умань", "Суми", "Хмельницький", "Миколаїв"};
               // 0       1          2        3            4         5         6        7       8               9
        int[][] roads = {
/* 0 Київ  */    {1, 5, 7, 8, 9}, // індекси міст з якими сполучений Київ
/* 1 Житомир */  {0, 2, 8}, // міста з'днані з Житомиром
/* 2 Лубни */    {4, 9},
/* 3 Бориспіль */{2, 5},
/* 4 Фастів */   {9},
/* 5 Ніжин */    {0, 3},
/* 6 Умань */    {8, 9},
/* 7 Суми */     {0, 2, 6},
/* 8 Хмельн. */  {6},
/* 9 Миколаїв */ {2, 6}
        };

        int totalCities = cities.length;
        boolean[] visited = new boolean[totalCities]; // будемо відмічати досяжні міста

        int cityStart = 4; // Запускаємо пошук з Фастова
        Deque<Integer> currents = new LinkedList<>();
        currents.push(cityStart);
        //visited[cityStart] = true;
        int[] from = new int[totalCities];

        while (!currents.isEmpty()) {
            int current = currents.pop();
            visited[current] = true;

            for (Integer city : roads[current]) {
                if (!visited[city]) {
                    currents.push(city);
                    // this is only for path
                    from[city] = current;
                }
            }
        }


        System.out.printf("From '%s' it is impossible to visit ", cities[cityStart]);

        for (int i = 0; i < totalCities; i++) {
            if (!visited[i]) {
                System.out.printf(" '%s'", cities[i]);
            }
        }

        System.out.print("\nBut you can go to ");
        for (int i = 0; i < totalCities; i++) {
            if (visited[i]) {
                System.out.printf(" '%s'", cities[i]);
            }
        }

        int cityFinish = 2;// Лубни
        Deque<String> path = new LinkedList<>();

        while (cityFinish != cityStart) {
            path.push(cities[cityFinish]);
            cityFinish = from[cityFinish];
        }

        path.push(cities[cityStart]);
        System.out.print("\n Path : ");

        while (!path.isEmpty()) {
            System.out.printf("--> '%s'", path.pop());
        }
    }

}
