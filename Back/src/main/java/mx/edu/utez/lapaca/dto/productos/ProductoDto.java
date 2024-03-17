package mx.edu.utez.lapaca.dto.productos;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.pedidos.Pedido;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.models.usuarios.Usuario;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDto {

    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Size(max = 40, message = "El nombre del producto debe tener como máximo {max} caracteres")
    private String nombre;

    @NotBlank(message = "La URL de la imagen no puede estar vacía")
    private String imagenUrl;

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    @Size(max = 100, message = "La descripción del producto debe tener como máximo {max} caracteres")
    private String descripcion;

    @Positive(message = "El precio del producto debe ser un número positivo")
    @NotBlank(message = "El precio del producto no puede estar vacía")
    private double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser igual o mayor que cero")
    @Positive(message = "El stock del producto debe ser un número positivo")
    private int stock;

    @NotNull(message = "El estado no puede ser nulo")
    private boolean estado;

    @NotNull(message = "El id de usuario no puede ser nulo")
    private Usuario usuario;

    @NotNull(message = "El id de categoria no puede ser nulo")
    private Categoria categoria;


    private List<Oferta> ofertas;
    public Producto getProducto() {

        return new Producto(
                getId(),
                getNombre(),
                getImagenUrl(),
                getDescripcion(),
                getPrecio(),
                getStock(),
                getProducto().isEstado(),
                getUsuario(),
                getCategoria(),
                getOfertas()
        );
    }





}
