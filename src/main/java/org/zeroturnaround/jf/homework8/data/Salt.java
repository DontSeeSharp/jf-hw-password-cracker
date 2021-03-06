package org.zeroturnaround.jf.homework8.data;

import java.util.Arrays;
import java.util.List;

/**
 * Interface that stores the salts.
 * <p>
 * The salts are public, and are required to successfully complete the exercise.
 * Please consult the comments in the {@link Hash} class to find out how these are used.
 */
public interface Salt {

  // Random words from https://www.randomlists.com/random-words?qty=10
  List<String> salts = Arrays.asList(
      "payment",
      "acid",
      "smelly",
      "pancake",
      "yawn",
      "mountainous",
      "capable",
      "work",
      "advice",
      "icy"
  );
}
