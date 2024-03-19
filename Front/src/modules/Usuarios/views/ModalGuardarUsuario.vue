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
                                <label for="usuario">Nombre del usuario: *</label>
                                <b-form-input v-model="usuario.nombre" type="text" class="form-control" placeholder="Nombre" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                            <b-col>
                                <label for="usuario">Email del usuario: *</label>
                                <b-form-input v-model="usuario.email" type="text" class="form-control"
                                    placeholder="email@example.com" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="usuario">URL de la foto de perfil: *</label>
                                <b-form-input v-model="usuario.imagen_url" type="text" class="form-control"
                                    placeholder="URL De la imagen" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                            <b-col>
                                <label for="usuario">Contraseña del usuario: *</label>
                                <b-form-input v-model="usuario.contrasena" type="password" class="form-control" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="usuario">Número telefonico: *</label>
                                <b-form-input v-model="usuario.telefono" type="number" class="form-control"
                                    placeholder="1112224455" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                            <b-col>
                                <label for="usuario">Rol del usuario: *</label>
                                <b-form-input v-model="usuario.role.id" type="number" class="form-control" required
                                    aria-describedby="input-live-help input-live-feedback" />
                            </b-col>
                            <b-col>
                                <label for="usuario">Nombre del rol del usuario: *</label>
                                <b-form-input v-model="usuario.role.nombre" type="text" class="form-control" required
                                    aria-describedby="input-live-help input-live-feedback" />
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
                id: null,
                nombre: "",
                imagen_url: "",
                email: "",
                contrasena: "",
                telefono: null,
                role: {
                    id: null,
                    nombre: ""
                }
            },
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-save-user");
            this.usuario.nombre = ""
            this.usuario.imagen_url = ""
            this.usuario.email = ""
            this.usuario.contrasena = ""
            this.usuario.telefono = null
            this.usuario.role.id = null
            this.usuario.role.nombre = ""

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
                    // Realiza la solicitud de guardado
                    const response = await axios.post("http://localhost:8090/api-carsi-shop/usuario/insert", this.usuario);

                    if (response.status === 201) {
                        this.usuario = {
                            nombre: '',
                            imagen_url: '',
                            email: '',
                            contrasena: '',
                            telefono: null,
                            role: {
                                id: null,
                                name: '',
                            },
                        };
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El usuario se registró correctamente",
                            icon: "success"
                        });

                        // Limpia el formulario después de guardar
                        this.onClose();

                        // Emite un evento para informar a otros componentes sobre la actualización del usuario
                        this.$emit('user-updated');
                    } else {
                        // Maneja situaciones donde la solicitud fue exitosa, pero el servidor devuelve un estado no exitoso
                        console.log("Error al guardar el usuario. Estado del servidor:", response.status);
                    }
                }
            } catch (error) {
                // Maneja errores generales, como problemas de red o errores en la solicitud
                console.error("Error al realizar la solicitud de guardado:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el usuario",
                    icon: "error"
                });
            }
        },


    },

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