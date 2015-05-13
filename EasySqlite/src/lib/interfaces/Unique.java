package lib.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lib.Conflict;

/**
 * To define a field is unique
 * @author quanhv
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Unique
{
  public abstract Conflict conflict();
}