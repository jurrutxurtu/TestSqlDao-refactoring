package org.jurrutxurtu.DoublyLinkedList;

import java.sql.Timestamp;

public class PedidoDto
{
    private Timestamp fecha;
    private double total;
    private double subtotal;
    private String direccion;
    private long idUsuario;
    
    public PedidoDto(Timestamp fecha, double total, double subtotal, String direccion)
    {
        this.fecha = fecha;
        this.total = total;
        this.subtotal = subtotal;
        this.direccion = direccion;
    }

    public Timestamp getFecha()
    {
        return fecha;
    }

    public double getTotal()
    {
        return total;
    }

    public double getSubtotal()
    {
        return subtotal;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public long getIdUsuario()
    {
        return idUsuario;
    }

    public void setIdUsuario(long idUser)
    {
        this.idUsuario = idUser;
    }

    public String asSqlValueString()
    {
        return "(" + fecha + ", " + total + ", " + subtotal + ", '" + direccion + ", " + idUsuario + "')";
    }

}
