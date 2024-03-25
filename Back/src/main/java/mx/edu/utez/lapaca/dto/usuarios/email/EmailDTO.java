package mx.edu.utez.lapaca.dto.usuarios.email;

public class EmailDTO {
    private String email;

    // Constructor
    public EmailDTO() {
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
