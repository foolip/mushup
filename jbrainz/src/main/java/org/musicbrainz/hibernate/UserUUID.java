package org.musicbrainz.hibernate;

import org.hibernate.*;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.util.UUID;

public class UserUUID implements UserType {

	private static final int[] SQL_TYPES = { Types.CHAR };

	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	public Class returnedClass() {
		return UUID.class;
	}

	public boolean equals(Object x, Object y) 
	throws HibernateException {
		if (x == y) {
			return true;
		} else if (x == null || y == null) {
			return false;
		} else {
			return ((UUID)x).equals((UUID)y);
		}
	}

	public int hashCode(Object x)
	throws HibernateException {
		return ((UUID)x).hashCode();
	}

	public Object nullSafeGet(ResultSet resultSet, 
			String[] names, Object owner)
	throws HibernateException, SQLException {
		String uuidString = resultSet.getString(names[0]);
		if (uuidString == null)
			return null;
		return UUID.fromString(uuidString);
	}

	public void nullSafeSet(PreparedStatement statement, 
			Object value, int index)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, Types.CHAR);
		} else {
			statement.setString(index, ((UUID)value).toString());
		}
	}

	public Object deepCopy(Object value) throws HibernateException {
		UUID old = (UUID)value;
		return new UUID(old.getMostSignificantBits(), old.getLeastSignificantBits());
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value)
	throws HibernateException {
		return (UUID)value;
	}

	public Object assemble(Serializable cached,
			Object owner)
	throws HibernateException {
		return (UUID)cached;
	}

	public Object replace(Object original,
			Object target,
			Object owner)
	throws HibernateException {
		return original;
	}
}
