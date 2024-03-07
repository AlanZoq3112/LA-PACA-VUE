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
import java.util.Map;

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
    @GetMapping("/getOne")
    public ResponseEntity<CustomResponse<Role>> getOne(@Valid @RequestBody Map<String, Long> requestBody){
        Long roleId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.getOne(roleId),
                HttpStatus.OK
        );
    }

    //delete by id
    @DeleteMapping("/delete")
    public ResponseEntity<CustomResponse<String>> deleteById(@Valid @RequestBody Map<String, Long> requestBody) {
        Long roleId = requestBody.get("id");
        return new ResponseEntity<>(
                this.service.deleteById(roleId),
                HttpStatus.OK
        );
    }

}
