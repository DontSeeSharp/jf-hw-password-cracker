package org.zeroturnaround.jf.homework8.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Weakened SHA-512, easily broken (collisions found), don't ever use this in real life!
 */
public final class BramHash {

  private BramHash() {
    throw new RuntimeException("Should not create instances of this Util class");
  }

  /**
   * Calculate SHA-512 of the input, and keep the 16 most significant bits.
   * This reduces the search space considerably to find a collision, as there are only about 64k possible outputs.
   */
  public static String hash(String input) {
    return DigestUtils.sha512Hex(input).substring(0, 4);
  }
}
