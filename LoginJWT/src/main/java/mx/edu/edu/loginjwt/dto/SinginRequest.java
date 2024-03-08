package mx.edu.edu.loginjwt.dto;

import lombok.Data;

@Data
public class SinginRequest {
    private String email;
    private String password;
}
