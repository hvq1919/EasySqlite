package lib.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lib.Conflict;

/**
 * To set a field is NotNull
 * @author quanhv
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface NotNull
{
  public abstract Conflict conflict();
}