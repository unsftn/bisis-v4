package com.gint.app.bisis4.utils;

import java.math.BigDecimal;

public class FormatBigDecimal {
  // Returns the BigDecimal value n with trailing
  // zeroes removed.
  public static BigDecimal trim(BigDecimal n) {
    try {
      while (true) {
        n = n.setScale(n.scale() - 1);
      }
    } catch (ArithmeticException e) {
      // no more trailing zeroes so exit.
    }
    return n;
  }

  // Returns the BigDecimal value n with exactly
  // 'prec' decimal places.
  // Zeroes are padded to the right of the decimal
  // point if necessary.
  public static BigDecimal format(BigDecimal n, int prec) {
    return n.setScale(prec, BigDecimal.ROUND_HALF_UP);
  }

  // Some examples.
  public static void main(String[] args) {
    System.out.println(trim(new BigDecimal("12.3230000")));
    // 12.323
    System.out.println(format(new BigDecimal("12.32534"), 2));
    // 12.33
    System.out.println(format(new BigDecimal("12"), 2));
    // 12.00
  }
}
