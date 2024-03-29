<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-save-user">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Registrar usuario</p>
                </header>
                <main>
                    <form id="registrarUsuario">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre del usuario: *</label>
                                <b-form-input v-model="usuario.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required />
                            </b-col>
                            <b-col>
                                <label for="email">Email del usuario: *</label>
                                <b-form-input v-model="usuario.email" type="text" class="form-control"
                                    placeholder="email@example.com" required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="imagen_url">URL de la foto de perfil: *</label>
                                <b-form-input v-model="usuario.imagenUrl" type="text" class="form-control"
                                    placeholder="URL De la imagen" required />
                            </b-col>
                            <b-col>
                                <label for="password">Contraseña del usuario: *</label>
                                <b-form-input v-model="usuario.password" type="password" class="form-control"
                                    required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="telefono">Número telefónico: *</label>
                                <b-form-input v-model="usuario.telefono" type="number" class="form-control"
                                    placeholder="1112224455" required />
                            </b-col>
                            <b-col>
                                <label for="genero">Género: *</label>
                                <b-form-select v-model="usuario.genero" :options="generos" required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="role">Rol del usuario: *</label>
                                <b-form-select v-model="usuario.role" :options="roles" required />
                            </b-col>
                            <b-col>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="fechaNacimiento">Fecha de nacimiento:
                                    </label>
                                    <input v-model="usuario.fechaNacimiento" type="date" id="fechaNacimiento"
                                        class="form-control" />
                                </div>
                            </b-col>
                        </b-row>
                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" @click="onClose" id="savemovie">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="save" id="saveteam" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from 'axios'
export default {
    name: "modal-save-user",

    data() {
        return {
            usuario: {
                nombre: "",
                email: "",
                imagenUrl: "",
                password: "",
                telefono: null,
                genero: "",
                role: "",
                fechaNacimiento: ""
            },
            generos: ["Masculino", "Femenino", "Otro"],
            roles: ["ADMIN", "VENDEDOR", "COMPRADOR"]

        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-save-user");
            this.resetForm();
        },
        async save() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de registrar el usuario?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    console.log("Usuario a guardar: ", this.usuario);
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/usuario/insert", this.usuario, {
                        headers: {
                            Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                        }
                    });

                    if (response.status === 201) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El usuario se registró correctamente",
                            icon: "success"
                        });

                        this.$emit('user-updated');
                        this.$bvModal.hide("modal-save-user");
                    } else {
                        console.log("Error al guardar el usuario. Estado del servidor:", response.status);
                    }
                }
            } catch (error) {
                console.error("Error al realizar la solicitud de guardado:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el usuario",
                    icon: "error"
                });
            }
        },
        resetForm() {
            this.usuario = {
                nombre: "",
                email: "",
                imagenUrl: "",
                password: "",
                telefono: null,
                genero: "",
                role: "",
                fechaNacimiento: ""
            };
        }
    }

}
</script>

<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: #ffce50;
    color: black;
}
</style>