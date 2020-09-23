package algorithms.l09edgeweightedgraph.rep;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public class ReadEdgesApp {
  public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
    String f = ReadEdgesApp.class.getClassLoader().getResource("twg.txt").getFile();
    try (Stream<String> lines = new BufferedReader(new FileReader(f)).lines()) {
      lines.map(line -> {
        String[] s1 = line.split(" ");
        String[] s2 = s1[0].split("-");
        double weight = Double.parseDouble(s1[1]);
        int v = Integer.parseInt(s2[0]);
        int w = Integer.parseInt(s2[1]);
        return new Edge(v, w, weight);
      })
        .forEach(System.out::println);
    }
  }
}
