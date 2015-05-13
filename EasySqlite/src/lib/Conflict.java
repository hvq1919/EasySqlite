package lib;
/**
 * To handle conflict type while execute sql statements
 * @author quanhv
 *
 */
public enum Conflict {
	NONE, ROLLBACK, ABORT, FAIL, IGNORE, REPLACE;
}