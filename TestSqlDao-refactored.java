package org.jurrutxurtu.DoublyLinkedList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mejorar cada uno de los métodos a nivel SQL y código cuando sea necesario
 * Razonar cada una de las mejoras que se han implementado No es necesario que
 * el código implementado funcione
 */
public class TestSqlDao
{
    
    /**
     * Obtiene el ID del último pedido para cada usuario
     * 
     */
    public Hashtable<Long, Long> getMaxUserOrderId (long idTienda) throws SQLException
    {
        String query = String.format("SELECT ID_PEDIDO, ID_USUARIO FROM PEDIDOS AS P " + "WHERE p1.ID_PEDIDO = (SELECT MAX(ID_PEDIDO) FROM PEDIDOS "
                + "WHERE ID_USUARIO = P.ID_USUARIO)" + " AND ID_TIENDA = %s", idTienda);
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        Hashtable<Long, Long> maxOrderUser = new Hashtable<Long, Long>();

        while (rs.next())
        {
            long idPedido = rs.getInt("ID_PEDIDO");
            long idUsuario = rs.getInt("ID_USUARIO");
            maxOrderUser.put(idUsuario, idPedido);
        }
        return maxOrderUser;
    }

    /**
     * Copia todos los pedidos de un usuario a otro
     */
    public void copyUserOrders(long idUserOri, long idUserDes) throws Exception
    {
        List<PedidoDto> listaPedidosUsuarioOrigen = getUserOrders(idUserOri);
        addOrdersToUser(idUserDes, listaPedidosUsuarioOrigen);
    }

    public List<PedidoDto> getUserOrders(long idUser) throws Exception
    {
        String query = String.format("SELECT FECHA, TOTAL, SUBTOTAL, DIRECCION FROM PEDIDOS WHERE ID_USUARIO = %s", idUser);
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.executeBatch();
        ResultSet rs = stmt.executeQuery();
        List<PedidoDto> listaPedidos = new ArrayList<PedidoDto>();
        while (rs.next())
        {
            PedidoDto pedido = new PedidoDto(rs.getTimestamp("FECHA"), rs.getDouble("TOTAL"), rs.getDouble("SUBTOTAL"), rs.getString("DIRECCION"));
            listaPedidos.add(pedido);
        }
        return listaPedidos;
    }

    public void addOrdersToUser(long idUser, List<PedidoDto> pedidos) throws Exception
    {
        if (pedidos.isEmpty())
            return;
        Connection connection2 = getConnection();
        connection2.setAutoCommit(false);
        StringBuilder insertSb = new StringBuilder();
        insertSb.append("INSERT INTO PEDIDOS (FECHA, TOTAL, SUBTOTAL, DIRECCION, ID_USUARIO) VALUES ");
        pedidos.forEach((pedido) -> pedido.setIdUsuario(idUser));
        insertSb.append(pedidos.stream().map(x -> x.asSqlValueString()).collect(Collectors.joining(",")));
        PreparedStatement stmt2 = connection2.prepareStatement(insertSb.toString());
        stmt2.executeUpdate();
        connection2.commit();
    }

    /**
     * Obtiene los datos del usuario y pedido con el pedido de mayor importe
     * para la tienda dada
     */
    public PedidoDeUsuarioDto getUserMaxOrder(long idTienda, long userId, long orderId, String name, String address) throws Exception
    {
        String query = String.format("SELECT U.ID_USUARIO, P.ID_PEDIDO, MAX(P.TOTAL), U.NOMBRE, U.DIRECCION FROM PEDIDOS AS P "
                + "INNER JOIN USUARIOS AS U ON P.ID_USUARIO = U.ID_USUARIO WHERE P.ID_TIENDA = %", idTienda);
        Connection connection = getConnection();
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        rs.first();
        return new PedidoDeUsuarioDto(rs.getString("DIRECCION"), rs.getString("NOMBRE"), rs.getInt("ID_USUARIO"), rs.getInt("ID_PEDIDO"));
    }

    private Connection getConnection()
    {
        // return JDBC connection
        return null;
    }
}
