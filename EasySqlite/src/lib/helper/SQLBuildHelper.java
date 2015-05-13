package lib.helper;

import java.util.Arrays;
import java.util.Date;

import lib.Conflict;
import lib.interfaces.Default;
import lib.interfaces.NotNull;
import lib.interfaces.PrimaryKey;
import lib.interfaces.TablePrimaryKey;
import lib.interfaces.TableUnique;
import lib.interfaces.Unique;

/**
 * To get Sql from interfaces and handle conflict sql
 * @author quanhv
 *
 */
public class SQLBuildHelper
{
  public static String getSQLType(Class<?> type)
  {
    if ((type == Integer.TYPE) || (type == Integer.class) || (type == Long.TYPE) || (type == Long.class) || (type == Date.class) || (type == Boolean.TYPE) || (type == Boolean.class) || (type == Short.TYPE) || (type == Short.class) || (type == Byte.TYPE) || (type == Byte.class))
      return "INTEGER";
    if ((type == Float.TYPE) || (type == Double.TYPE))
      return "REAL";
    if ((type == String.class) || (type.isEnum()))
      return "TEXT";
    if (type == Byte.class) {
      return "BLOB";
    }
    return "";
  }

  public static String getPrimaryKeySQL(PrimaryKey annotation)
  {
    StringBuilder sb = new StringBuilder(" PRIMARY KEY");

//    if (annotation.conflict() != Conflict.NONE) {
//      sb.append(" ON CONFLICT ").append(annotation.conflict().name());
//    }

    if (annotation.autoIncrement()) {
      sb.append(" AUTOINCREMENT");
    }

    return sb.toString();
  }

  public static String getNotNullSQL(NotNull annotation) {
    StringBuilder sb = new StringBuilder(" NOT NULL");

    if (annotation.conflict() != Conflict.NONE) {
      sb.append(" ON CONFLICT ").append(annotation.conflict().name());
    }

    return sb.toString();
  }

  public static String getUniqueSQL(Unique annotation) {
    StringBuilder sb = new StringBuilder(" UNIQUE");

    if (annotation.conflict() != Conflict.NONE) {
      sb.append(" ON CONFLICT ").append(annotation.conflict().name());
    }

    return sb.toString();
  }


  public static String getDefaultSQL(Default annotation) {
    return " DEFAULT " + annotation.value();
  }

//  public static String getCollateSQL(Collate annotation) {
//    return " COLLATE " + annotation.value();
//  }

  public static String getTablePrimaryKeySQL(TablePrimaryKey annotation) {
    String indexedColumn = Arrays.toString(annotation.indexedColumn());
    StringBuilder sb = new StringBuilder(" PRIMARY KEY (").append(indexedColumn.substring(1, indexedColumn.length() - 1)).append(")");

    if (annotation.conflict() != Conflict.NONE) {
      sb.append(" ON CONFLICT ").append(annotation.conflict().name());
    }

    return sb.toString();
  }

  public static String getTableUniqueSQL(TableUnique annotation) {
    String indexedColumn = Arrays.toString(annotation.indexedColumn());
    StringBuilder sb = new StringBuilder(" UNIQUE (").append(indexedColumn.substring(1, indexedColumn.length() - 1)).append(")");

    if (annotation.conflict() != Conflict.NONE) {
      sb.append(" ON CONFLICT ").append(annotation.conflict().name());
    }

    return sb.toString();
  }
}