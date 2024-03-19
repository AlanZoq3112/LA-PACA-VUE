<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-save-user">
            <header class="text-center border-bottom">
                <p style="font-family: cabin">Registrar usuario</p>
            </header>

            <main>
                <form @submit.prevent="save" id="registrarUsuario">
                    <b-row>
                        <b-col>
                            <label for="nombre">Nombre del usuario: *</label>
                            <b-form-input v-model="usuario.nombre" type="text" placeholder="Nombre" required />
                        </b-col>
                        <b-col>
                            <label for="email">Email del usuario: *</label>
                            <b-form-input v-model="usuario.email" type="email" placeholder="email@example.com"
                                required />
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <label for="imagen_url">URL de la foto de perfil: *</label>
                            <b-form-input v-model="usuario.imagen_url" type="text" placeholder="URL De la imagen"
                                required />
                        </b-col>
                        <b-col>
                            <label for="contrasena">Contraseña del usuario: *</label>
                            <b-form-input v-model="usuario.contrasena" type="password" required />
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <label for="telefono">Número telefonico: *</label>
                            <b-form-input v-model="usuario.telefono" type="number" placeholder="1112224455" required />
                        </b-col>
                        <b-col>
                            <label for="role">Rol del usuario: *</label>
                            <b-form-input v-model="usuario.role.id" type="number" required />
                        </b-col>
                        <b-col>
                            <label for="nombre_role">Nombre del rol del usuario: *</label>
                            <b-form-input v-model="usuario.role.nombre" type="text" required />
                        </b-col>
                    </b-row>
                </form>
            </main>

            <footer class="text-center mt-5">
                <button class="btn m-1 cancel" @click="onClose">Cancelar</button>
                <button class="btn m-1 success" type="submit">Registrar</button>
            </footer>
        </b-modal>
    </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from 'axios';

export default {
    name: "modal-save-user",

    data() {
        return {
            usuario: {
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
                    // Realiza la solicitud de guardado
                    const response = await axios.post("http://localhost:8090/api-carsi-shop/usuario/insert", this.usuario);

                    if (response.status === 201) {
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El usuario se registró correctamente",
                            icon: "success"
                        });

                        this.resetForm();
                        this.$emit('user-updated');
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
                nombre: '',
                imagen_url: '',
                email: '',
                contrasena: '',
                telefono: null,
                role: {
                    id: null,
                    nombre: '',
                },
            };
        },
    }
};
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