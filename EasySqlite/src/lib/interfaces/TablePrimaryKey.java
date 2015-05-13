package lib.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lib.Conflict;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface TablePrimaryKey
{
  public abstract String[] indexedColumn();

  public abstract Conflict conflict();
}