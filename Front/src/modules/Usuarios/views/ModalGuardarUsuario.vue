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
                                <b-form-input v-model="usuario.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required
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
                                <b-form-input v-model="usuario.rol.id" type="number" class="form-control" required
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
                rol:{
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
            this.usuario.rol.id = null
            this.usuario.rol.nombre = ""

        },
        async save() {
            Swal.fire({
                title: "¿Estás seguro de registrar el usuario?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#008c6f',
                cancelButtonColor: '#e11c24',
                confirmButtonText: "Confirmar",
                cancelButtonText: 'Cancelar',
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        console.log(this.usuario);
                        await axios.post("http://localhost:8090/api-carsi-shop/usuario/insert", this.usuario);
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El usuario se registró correctamente",
                            icon: "success"
                        });
                        this.onClose();
                        this.$emit('user-updated');
                    } catch (error) {
                        console.log("Error al guardar el usuario", error);
                    }

                }
            });
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