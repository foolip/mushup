package org.musicbrainz.hibernate;

import org.hibernate.*;
import org.hibernate.usertype.UserType;
import org.musicbrainz.model.Artist;

import java.io.Serializable;
import java.sql.*;

public class UserArtistType implements UserType {

	private static final int[] SQL_TYPES = { Types.SMALLINT };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class<Artist.Type> returnedClass() {
		return Artist.Type.class;
	}

	public boolean equals(Object x, Object y) 
	throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x)
	throws HibernateException {
		return ((Artist.Type)x).hashCode();
	}

	public Object nullSafeGet(ResultSet resultSet, 
			String[] names, Object owner)
	throws HibernateException, SQLException {
		int value = resultSet.getInt(names[0]);
		switch (value) {
		case 1:
			return Artist.Type.PERSON;
		case 2:
			return Artist.Type.GROUP;
		default:
			return Artist.Type.UNKNOWN;
		}
	}

	public void nullSafeSet(PreparedStatement statement, 
			Object value, int index)
	throws HibernateException, SQLException {
		switch ((Artist.Type)value) {
		case PERSON:
			statement.setInt(index, 1);
			break;
		case GROUP:
			statement.setInt(index, 2);
			break;
		default:
			statement.setNull(index, Types.SMALLINT);
			break;
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value)
	throws HibernateException {
		return (Artist.Type)value;
	}

	public Object assemble(Serializable cached,
			Object owner)
	throws HibernateException {
		return (Artist.Type)cached;
	}

	public Object replace(Object original,
			Object target,
			Object owner)
	throws HibernateException {
		return original;
	}
}
