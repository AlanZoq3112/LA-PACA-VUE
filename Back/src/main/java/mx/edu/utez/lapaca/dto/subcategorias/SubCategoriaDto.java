package mx.edu.utez.lapaca.dto.subcategorias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCategoriaDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 25, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;

    @NotNull(message = "La categoria no puede ser nula")
    private Categoria categoria;

    public SubCategoria getSubCategoria() {
        return new SubCategoria(
                getId(),
                getNombre(),
                getCategoria()
        );
    }
}
