package model.api.types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

/**
 * Created by byaxe on 18.12.16.
 */
public class StringArray implements UserType {

    /**
     * Constante contenant le type SQL "Array".
     */
    protected static final int[] SQL_TYPES = {Types.ARRAY};

    /**
     * Return the SQL type codes for the columns mapped by this type. The
     * codes are defined on <tt>java.sql.Types</tt>.
     *
     * @return int[] the typecodes
     * @see java.sql.Types
     */
    public final int[] sqlTypes() {
        return SQL_TYPES;
    }

    /**
     * The class returned by <tt>nullSafeGet()</tt>.
     *
     * @return Class
     */
    public final Class returnedClass() {
        return String[].class;
    }

    /**
     * Retrieve an instance of the mapped class from a JDBC resultset. Implementors
     * should handle possibility of null values.
     *
     * @param resultSet a JDBC result set.
     * @param names     the column names.
     * @param session   SQL en cours.
     * @param owner     the containing entity
     * @return Object
     * @throws org.hibernate.HibernateException exception levée par Hibernate
     *                                          lors de la récupération des données.
     * @throws java.sql.SQLException            exception SQL
     *                                          levées lors de la récupération des données.
     */
    @Override
    public Object nullSafeGet(
            final ResultSet resultSet,
            final String[] names,
            final SharedSessionContractImplementor session,
            final Object owner) throws HibernateException, SQLException {
        if (resultSet.wasNull()) {
            return null;
        }

        return resultSet.getArray(names[0]).getArray();
    }

    /**
     * Write an instance of the mapped class to a prepared statement. Implementors
     * should handle possibility of null values. A multi-column type should be written
     * to parameters starting from <tt>index</tt>.
     *
     * @param statement a JDBC prepared statement.
     * @param value     the object to write
     * @param index     statement parameter index
     * @param session   sql en cours
     * @throws org.hibernate.HibernateException exception levée par Hibernate
     *                                          lors de la récupération des données.
     * @throws java.sql.SQLException            exception SQL
     *                                          levées lors de la récupération des données.
     */
    @Override
    public final void nullSafeSet(final PreparedStatement statement, final Object value,
                                  final int index, final SharedSessionContractImplementor session) throws HibernateException, SQLException {

        if (value == null) {
            statement.setNull(index, SQL_TYPES[0]);
        } else {
            String[] castObject = (String[]) value;
            Array array = session.connection().createArrayOf("text", castObject);
            statement.setArray(index, array);
        }
    }

    @Override
    public final Object deepCopy(final Object value) throws HibernateException {
        return value;
    }

    @Override
    public final boolean isMutable() {
        return false;
    }

    @Override
    public final Object assemble(final Serializable arg0, final Object arg1)
            throws HibernateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final Serializable disassemble(final Object arg0) throws HibernateException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final boolean equals(final Object x, final Object y) throws HibernateException {
        return x == y || !(x == null || y == null) && x.equals(y);
    }

    @Override
    public final int hashCode(final Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public final Object replace(
            final Object original,
            final Object target,
            final Object owner) throws HibernateException {
        return original;
    }
}
