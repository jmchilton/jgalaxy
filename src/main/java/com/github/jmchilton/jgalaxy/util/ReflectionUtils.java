package com.github.jmchilton.jgalaxy.util;


/**
 * 
 * @author John Chilton
 */

public class ReflectionUtils {

  public static <T> T newInstance(final Class<T> clazz) {
    try {
      return clazz.newInstance();
    } catch(final Exception e) {
      throw new ReflectionRuntimeException(e);
    }
  }
  
  /**
   * This class is a {@link RuntimeException} meant to wrap exceptions that occur
   * while performing reflection.
   *
   */
  public static class ReflectionRuntimeException extends RuntimeException {

    public ReflectionRuntimeException(final Exception cause) {
      super(cause);
    }
  }
  
}
