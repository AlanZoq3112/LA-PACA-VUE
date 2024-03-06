package mx.edu.utez.lapaca.services.roles;


import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.models.roles.RoleRepository;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (role.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    400,
                    "El rol no existe"
            );
        }
        return new CustomResponse<>(
                role.get(),
                false,
                200,
                "Rol encontrado"
        );
    }

    //delete by id
    public CustomResponse<String> deleteById(Long id) {
        Optional<Role> roleOptional = repository.findById(id);
        if (roleOptional.isEmpty()) {
            return new CustomResponse<>(
                    null,
                    true,
                    404,
                    "El rol no existe"
            );
        }
        repository.deleteById(id);

        return new CustomResponse<>(
                null,
                false,
                200,
                "Rol eliminado correctamente"
        );
    }

}
