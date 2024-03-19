<template>
    <div>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-10">
                    <div class="card rounded-3 text-black">
                        <div class="col-lg-12">
                            <div class="card-body p-md-5 mx-md-4">
                                <div class="text-center">
                                    <img src="https://png.pngtree.com/template/20191024/ourmid/pngtree-shopping-cart-vector-logo-design-shopping-logo-design-on-line-shopping-image_322990.jpg"
                                        style="width: 200px;" alt="logo" />
                                    <h4 class="mt-1 mb-5 pb-1">Crear cuenta</h4>
                                </div>

                                <form>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="nombre">Nombre(s): </label>
                                                <input v-model="user.nombre" type="text" id="nombre"
                                                    class="form-control" placeholder="Nombre" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="genero">Género: </label>
                                                <div class="input-group">
                                                    <select v-model="user.genero" id="genero" class="form-select">
                                                        <option value="">Seleccionar</option>
                                                        <option value="masculino">Masculino</option>
                                                        <option value="femenino">Femenino</option>
                                                        <option value="otro">Otro</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="correo">Correo electrónico: </label>
                                                <input v-model="user.email" type="email" id="correo"
                                                    class="form-control" placeholder="Correo electrónico" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="contrasena">Contraseña: </label>
                                                <input v-model="user.password" type="password" id="contrasena"
                                                    class="form-control" placeholder="Contraseña" />
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="telefono">Teléfono: </label>
                                                <input v-model="user.telefono" type="tel" id="telefono"
                                                    class="form-control" placeholder="Teléfono" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="fechaNacimiento">Fecha de nacimiento:
                                                </label>
                                                <input v-model="user.fechaNacimiento" type="date" id="fechaNacimiento"
                                                    class="form-control" />
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="imagen">Foto de perfil:</label>
                                        <div class="input-group">
                                            <input type="file" id="imagen" @change="handleImageUpload" accept="image/*"
                                                multiple style="display: none;" />
                                            <label class="input-group-text" for="imagen">
                                                <i class="fas fa-cloud-upload-alt"></i>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="form-outline mb-4" v-if="user.imagenUrl && user.imagenUrl.length > 0">
                                        <h5>Vista previa de la imagen:</h5>
                                        <div class="image-preview-container">
                                            <img class="image-preview" :src="user.imagenUrl" alt="Vista previa de la imagen" />
                                        </div>
                                    </div>
                                    <div class="text-center pt-1 mb-5 pb-1">
                                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                            @click="createAccount" type="button">
                                            Crear Cuenta
                                        </button>
                                    </div>

                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                        <p class="mb-0 me-2">¿Ya tienes cuenta?</p>
                                        <a class="text-muted" href="/login">Iniciar Sesión</a>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "crearCuenta",
    data() {
        return {
            user: {
                nombre: "",
                genero: "",
                imagenUrl: "",
                email: "",
                password: "",
                telefono: "",
                fechaNacimiento: ""
            },
            confirmPassword: "",
        };
    },
    methods: {
        createAccount() {
            console.log(this.user);
            axios.post('http://localhost:8090/api-carsi-shop/auth/singupUser', this.user)
                .then(response => {
                    console.log(response.data);
                    Swal.fire('Creada', 'Cuenta creada correctamente', 'success');
                    this.$router.push({ name: 'login' });
                })
                .catch(error => {
                    let errorMessage = "Hubo un problema al crear la cuenta";
                    if (error.response && error.response.data && error.response.data.length > 0) {
                        errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                    }
                    Swal.fire('Error', errorMessage, 'error');
                });
        },

        handleImageUpload(event) {
            const file = event.target.files[0];
            if (!file) return;

            // Crea un objeto FileReader para leer el contenido del archivo como una URL de datos
            const reader = new FileReader();

            // Configura la función que se ejecutará cuando se complete la lectura del archivo
            reader.onload = () => {
                // Asigna el resultado de la lectura del archivo (base64) a la propiedad imagenUrl
                this.user.imagenUrl = reader.result;

                // Muestra la imagen en la vista previa
                const preview = document.querySelector('.image-preview');
                preview.src = reader.result;
            };

            // Lee el contenido del archivo como una URL de datos (base64)
            reader.readAsDataURL(file);
        },
    },
    
};
</script>

<style scoped>
.image-preview-container {
    display: flex;
    flex-wrap: nowrap;
    /* Evita que las imágenes se desborden a la siguiente línea */
    justify-content: flex-start;
    /* Alinea las imágenes hacia el principio del contenedor */
    gap: 10px;
    /* Espacio entre las imágenes */
}

.image-preview {
    width: 200px;
    height: 200px;
    object-fit: fill;
}
</style>