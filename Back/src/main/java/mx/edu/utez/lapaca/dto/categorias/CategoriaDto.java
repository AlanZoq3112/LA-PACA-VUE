package mx.edu.utez.lapaca.dto.categorias;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.categorias.Categoria;
import mx.edu.utez.lapaca.models.subcategorias.SubCategoria;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDto {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 10, message = "El nombre debe tener como máximo {max} caracteres")
    private String nombre;

    @NotNull(message = "La lista de id de subcategorias no puede ser nula")
    @Valid//valida recursivamente cada campo/id
    private List<SubCategoria> subcategorias;

    public Categoria getCategoria() {
        return new Categoria(
                getId(),
                getNombre(),
                getSubcategorias()
        );
    }
}
