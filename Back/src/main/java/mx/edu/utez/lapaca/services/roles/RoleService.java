package mx.edu.utez.lapaca.services.roles;


import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.roles.RoleRepository;
import mx.edu.utez.lapaca.models.usuarios.Usuario;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository repository;

    //insert
    @Transactional(rollbackFor = {SQLException.class})
    public CustomResponse<Role> insert(Role role) {
        return new CustomResponse<>(
                this.repository.saveAndFlush(role),
                false,
                200,
                "Rol registrado"
        );
    }

    //get all
    public CustomResponse<List<Role>> getAll() {
        return new CustomResponse<>(
                this.repository.findAll(),
                false,
                200,
                "Ok"
        );
    }

    //get one by id
    public CustomResponse<Role> getOne(Long id) {
        Optional<Role> role = repository.findById(id);
        try {
            if (role.isPresent()) {
                return new CustomResponse<>(
                        role.get(),
                        false,
                        200,
                        "Role con el id " + role.get().getId() + " encontrado"
                );
            } else {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El role con el id " + id + " no existe"
                );
            }
        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al buscar el role"
            );
        }


    }

    //delete by id
    public CustomResponse<String> deleteById(Long id) {

        try {
            Optional<Role> roleId = repository.findById(id);
            if (!roleId.isPresent()) {
                return new CustomResponse<>(
                        null,
                        true,
                        400,
                        "El role con el id " + id + " no existe"
                );
            }
            Role role = roleId.get();
            repository.delete(role);
            return new CustomResponse<>(
                    null,
                    false,
                    200,
                    "El role con el id " + id + " ha sido eliminado correctamente"
            );

        } catch (DataAccessException e) {
            return new CustomResponse<>(
                    null,
                    true,
                    500,
                    "Error interno del servidor al eliminar el role"
            );
        }

    }

}
