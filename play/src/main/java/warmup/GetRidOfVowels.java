package warmup;

public class GetRidOfVowels {
  public String filter(String origin) {
    return origin.chars()
        .mapToObj(value -> (char)value)
        .filter(ch -> !java.util.Arrays.asList('A','E','O','I','U')
        .contains(Character.toUpperCase(ch)))
        .map(String::valueOf)
        .collect(java.util.stream.Collectors.joining());
  }
}
