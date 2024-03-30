package mx.edu.utez.lapaca.models.cantidadPago;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.pagos.Pago;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

@Entity
@Table(name = "cantidad_pagos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CantidadPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double monto;

    // Relación con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // Relación con Pago
    @ManyToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}