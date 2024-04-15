package mx.edu.utez.lapaca.dto.vendedores;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.vendedorImagen.VendedorImagen;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VendedorDto {

    private Long id;

    @NotNull(message = "La CURP es obligatoria")
    @Size(min = 18, max = 18, message = "La CURP debe tener exactamente 18 caracteres")
    private String curp;

    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\d{1,10}$", message = "El teléfono debe contener solo números y tener como máximo 10 dígitos")
    private String telefonoVendedor;

    private List<MultipartFile> imagenes = new ArrayList<>();

    @NotBlank(message = "El RFC no puede estar vacío")
    @Pattern(regexp = "^[A-Z]{4}\\d{6}[A-Z\\d]{3}$", message = "RFC no válido")
    @Size(min = 13, max = 13, message = "El RFC debe tener exactamente 13 caracteres")
    private String rfc;

    @NotNull(message = "El estado no puede ser nulo")
    private boolean estado;


    private Usuario usuario;

    public Vendedor getVendedor() {
        Vendedor vendedor = new Vendedor();
        List<VendedorImagen> listaImagenes = new ArrayList<>();
        for (MultipartFile imagen : imagenes) {
            VendedorImagen vendedorImagen = new VendedorImagen();
            vendedorImagen.setVendedor(vendedor);
            listaImagenes.add(vendedorImagen);
        }
        vendedor.setImagenes(listaImagenes);


        return new Vendedor(
                getId(),
                getCurp(),
                getTelefonoVendedor(),
                listaImagenes,
                getRfc(),
                isEstado(),
                getUsuario()
        );
    }
}
