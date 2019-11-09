package warmup.brackets;

class BracketsNestingLevel {

  int calc(String origin) {
    int depth = 0;
    int max_depth = 0;
    for (int i = 0; i < origin.length(); i++) {
      switch (origin.charAt(i)) {
        case '(': depth++; break;
        case ')': depth--; break;
        default : throw new IllegalArgumentException(String.format("Unexpected symbol: %s", origin.charAt(i)));
      }
      if (depth < 0) throw new IllegalArgumentException("Opening and closing parenthesis aren't corresponding to each other");
      max_depth = Math.max(depth, max_depth);
    }
    if (depth > 0) throw new IllegalArgumentException("Opening and closing parenthesis aren't corresponding to each other");
    return max_depth;
  }

}
