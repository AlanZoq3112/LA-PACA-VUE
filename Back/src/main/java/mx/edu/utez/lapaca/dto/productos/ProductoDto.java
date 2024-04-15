package mx.edu.utez.lapaca.dto.productos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.ofertas.Oferta;
import mx.edu.utez.lapaca.models.productos.Producto;
import mx.edu.utez.lapaca.models.productos_imagenes.ProductoImagen;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    private List<MultipartFile> imagenes = new ArrayList<>();

    @NotBlank(message = "La descripción del producto no puede estar vacía")
    @Size(max = 100, message = "La descripción del producto debe tener como máximo {max} caracteres")
    private String descripcion;

    @Positive(message = "El precio del producto debe ser un número positivo")
    @NotNull(message = "El precio del producto no puede estar vacía")
    private double precio;

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock debe ser igual o mayor que cero")
    @Positive(message = "El stock del producto debe ser un número positivo")
    private int stock;

    @NotNull(message = "El estado no puede ser nulo")
    private int estado;

    private Usuario usuario;

    @NotNull(message = "El id de subcategoria no puede ser nulo")
    private SubCategoria subCategoria;


    private List<Oferta> ofertas = new ArrayList<>();


    public Producto getProducto() {
        Producto producto = new Producto();
        List<ProductoImagen> listaImagenes = new ArrayList<>();
        for (MultipartFile imagen : imagenes) {
            ProductoImagen productoImagen = new ProductoImagen();
            productoImagen.setProducto(producto);
        }
        producto.setImagenes(listaImagenes);


        return new Producto(
                getId(),
                getNombre(),
                listaImagenes,
                getDescripcion(),
                getPrecio(),
                getStock(),
                getEstado(),
                getUsuario(),
                getSubCategoria(),
                getOfertas()
        );
    }
}
