package mx.edu.utez.lapaca.services.vendedores;


import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.models.usuarios.UsuarioRepository;
import mx.edu.utez.lapaca.models.vendedores.Vendedor;
import mx.edu.utez.lapaca.models.vendedores.VendedorRepository;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendedorService {


    private final VendedorRepository repository;
    private final UsuarioRepository usuarioRepository;

    public VendedorService(VendedorRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }



    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Vendedor> insert(Vendedor vendedor) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // obtener el nombre de usuario

            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                Optional<Vendedor> existingVendedorOptional = repository.findByUsuario(usuario);
                if (existingVendedorOptional.isPresent()) {
                    // si ya existe una solicitud de vendedor, pues epale epale
                    return new CustomResponse<>(
                            null,
                            true,
                            HttpStatus.BAD_REQUEST.value(),
                            "Ya has realizado una solicitud de vendedor previamente"
                    );
                }

                vendedor.setUsuario(usuario);

                vendedor.setEstado(false);

                Vendedor savedVendedor = repository.save(vendedor);

                return new CustomResponse<>(
                        savedVendedor,
                        false,
                        200,
                        "Solicitud de vendedor registrada. Pendiente de aprobación"
                );
            } else {
                // si no se encuentra el usuario, msj de error
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.BAD_REQUEST.value(),
                        "No se pudo encontrar el usuario autenticado"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al registrar la solicitud"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para insertar vendededor ilegal" + e.getMessage()
            );
        }
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<List<Vendedor>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Vendedor> getOne(String curp) {
        Optional<Vendedor> vendedor = repository.findByCurp(curp);
        try {
            if (vendedor.isPresent()) {
                return new CustomResponse<>(
                        vendedor.get(),
                        false,
                        200,
                        "Vendedor con el CURP: " + curp + " encontrado"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El usuario con el CURP " + curp + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar el vendedor solicitado"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para obetener un vendededor ilegal" + e.getMessage()
            );
        }
    }


    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Vendedor> update(Vendedor vendedor) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); // obtener el nombre de usuario
            // se asigna el usuario al vendedor
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
            vendedor.setUsuario(usuarioOptional.get());

            // verificar si el usuario existe en la base de datos
            Optional<Usuario> existingUsuarioOptional = usuarioRepository.findById(vendedor.getId());
            if (existingUsuarioOptional.isEmpty()) {
                return new CustomResponse<>(
                        null,
                        true,
                        HttpStatus.NOT_FOUND.value(),
                        "El usuario no existe");
            }
            // se guarda la solicitud de vendedor
            vendedor.setEstado(true);
            Vendedor savedVendedor = repository.save(vendedor);

            return new CustomResponse<>(
                    savedVendedor,
                    false,
                    200,
                    "Perfil modificado exitosamente"
            );

        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al modificarel perfil de vendedor"
            );
        } catch (IllegalArgumentException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.BAD_REQUEST.value(),
                    "Error... datos para actualizar un vendededor ilegal" + e.getMessage()
            );
        }
    }




    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Vendedor> aprobarSolicitudVendedor(long id, boolean estado) {
        Optional<Vendedor> vendedorOptional = repository.findById(id);
        if (vendedorOptional.isPresent()) {
            Vendedor vendedor = vendedorOptional.get();
            vendedor.setEstado(estado);
            repository.save(vendedor);
            // se actualiza el rol del usuario asociado si se aprueba como vendedor
            if (estado) {
                Usuario usuario = vendedor.getUsuario();
                usuario.setRole(Role.VENDEDOR);
                usuarioRepository.save(usuario);
            } else if (!estado) {
                Usuario usuario = vendedor.getUsuario();
                usuario.setRole(Role.COMPRADOR);
                usuarioRepository.save(usuario);
                return new CustomResponse<>(
                        vendedor,
                        true,
                        HttpStatus.OK.value(),
                        "Solicitud denegada correctamente"
                );

            }
            return new CustomResponse<>(
                    vendedor,
                    false,
                    HttpStatus.OK.value(),
                    "Solicitud aprobada correctamente"
            );
        } else {
            return new CustomResponse<>(
                    null,
                    true,
                    HttpStatus.NOT_FOUND.value(),
                    "No se encontró el vendedor con el ID proporcionado"
            );
        }
    }
}


