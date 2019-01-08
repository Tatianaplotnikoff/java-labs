package ua.nure.plotnykova.usermanagement.db.factory;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection createConnection() throws SQLException;
}
