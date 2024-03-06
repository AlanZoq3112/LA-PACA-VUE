package mx.edu.utez.lapaca.controllers.roles;


import jakarta.validation.Valid;
import mx.edu.utez.lapaca.dto.roles.RoleDto;
import mx.edu.utez.lapaca.models.roles.Role;
import mx.edu.utez.lapaca.services.roles.RoleService;
import mx.edu.utez.lapaca.utils.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-carsi-shop/role/")
//@CrossOrigin(origins = {"*"})
public class RoleController {

    @Autowired
    private RoleService service;

    //insert
    @PostMapping("/insert")
    public ResponseEntity<CustomResponse<Role>> insert(@Valid @RequestBody RoleDto roleDto){
        return new ResponseEntity<>(
                this.service.insert(roleDto.getRole()),
                HttpStatus.CREATED
        );
    }

    //get all
    @GetMapping("/getAll")
    public ResponseEntity<CustomResponse<List<Role>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }

    //get one
    @GetMapping("/getOne/{id}")
    public ResponseEntity<CustomResponse<Role>> getOne(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.getOne(id),
                HttpStatus.OK
        );
    }

    //delete by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse<String>> deleteById(@PathVariable("id") Long id) {
        CustomResponse<String> response = service.deleteById(id);
        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(
                        response.getStatusCode()
                ));
    }

}
