package org.jurrutxurtu.DoublyLinkedList;

public class PedidoDeUsuarioDto
{
    private String direccion;
    private String nombre;
    private long idUsuario;
    private long idPedido;
    
    public PedidoDeUsuarioDto(String direccion,String nombre, long idUsuario, long idPedido)
    {
        this.direccion = direccion;
        this.nombre = nombre;
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
    }
    
    public String getDireccion()
    {
        return direccion;
    }
    public String getNombre()
    {
        return nombre;
    }
    public long getIdUsuario()
    {
        return idUsuario;
    }
    public long getIdPedido()
    {
        return idPedido;
    }
}
