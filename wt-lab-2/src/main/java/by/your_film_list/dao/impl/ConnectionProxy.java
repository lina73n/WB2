package by.your_film_list.dao.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * ConnectionProxy is a class that implements the InvocationHandler interface.
 * It acts as a proxy for a Connection object and provides additional functionality
 * for managing connections in a ConnectionPool.
 */
public class ConnectionProxy implements InvocationHandler {
    private final Connection connection;
    private final ConnectionPool connectionPool;

    private ConnectionProxy(Connection connection, ConnectionPool connectionPool) {
        this.connection = connection;
        this.connectionPool = connectionPool;
    }

    /**
     * Invokes the specified method on the proxied Connection object.
     * If the method is "close", the connection is returned to the connection pool.
     * Otherwise, the method is invoked on the connection itself.
     *
     * @param proxy  the proxy object
     * @param method the method to be invoked
     * @param args   the arguments to the method
     * @return the result of the method invocation
     * @throws Throwable if an exception occurs during method invocation
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("close")) {
            connectionPool.returnConnection(connection);
            return null;
        }

        return method.invoke(connection, args);
    }

    /**
     * Creates a proxy for the specified Connection object.
     *
     * @param connection    the Connection object to be proxied
     * @param connectionPool the ConnectionPool object
     * @return a proxy for the Connection object
     */
    public static Connection createProxy(Connection connection, ConnectionPool connectionPool) {
        ClassLoader classLoader = connection.getClass().getClassLoader();
        Class<?>[] interfaces = connection.getClass().getInterfaces();
        ConnectionProxy connectionProxy = new ConnectionProxy(connection, connectionPool);

        return (Connection) Proxy.newProxyInstance(classLoader, interfaces, connectionProxy);
    }
}
