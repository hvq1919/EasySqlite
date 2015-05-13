package lib.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Implements this interface to define private key
 * @author quanhv
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface PrimaryKey
{
  public abstract boolean autoIncrement();

  //public Conflict conflict();
}