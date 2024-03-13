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
                                                <input v-model="user.Nombre" type="text" id="nombre" class="form-control"
                                                    placeholder="Nombre" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="apellidos">Apellido(s): </label>
                                                <input v-model="user.Apellidos" type="text" id="apellidos" class="form-control"
                                                    placeholder="Apellidos" />
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="contrasena">Contraseña: </label>
                                                <input v-model="user.Contraseña" type="password" id="contrasena"
                                                    class="form-control" placeholder="Contraseña" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="contrasena">Repetir contraseña: </label>
                                                <input v-model="confirmPassword" type="password" id="contrasena"
                                                    class="form-control" placeholder="Contraseña" />
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="correo">Correo electrónico: </label>
                                                <input v-model="user.Correo" type="email" id="correo" class="form-control"
                                                    placeholder="Correo electrónico" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="genero">Género: </label>
                                                <div class="input-group">
                                                    <select v-model="user.Genero" id="genero" class="form-select">
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
                                                <label class="form-label" for="telefono">Teléfono: </label>
                                                <input v-model="user.Telefono" type="tel" id="telefono" class="form-control"
                                                    placeholder="Teléfono" />
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="fechaNacimiento">Fecha de nacimiento: </label>
                                                <input v-model="user.Fecha_de_nacimiento" type="date" id="fechaNacimiento"
                                                    class="form-control" />
                                            </div>
                                        </b-col>
                                    </b-row>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="direccion">Dirección: </label>
                                        <input v-model="user.Direccion" type="text" id="direccion" class="form-control"
                                            placeholder="Calle, numero, colonia, municipio, estado" />
                                    </div>





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

                                    <div class="form-outline mb-4"
                                        v-if="user.Imagen_de_perfil && user.Imagen_de_perfil.length > 0">
                                        <h5>Vista previa de la imágen:</h5>
                                        <div class="image-preview-container">
                                            <img v-for="(imagen, index) in user.Imagen_de_perfil" :key="index"
                                                :src="imagen" alt="Vista previa de la imagen" class="image-preview" />
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
                Nombre: "",
                Apellidos: "",
                Correo: "",
                Contraseña: "",
                Telefono: "",
                Direccion: "",
                Fecha_de_nacimiento: "",
                Genero: "",
                Imagen_de_perfil: []
            },
            confirmPassword: "",
        };
    },
    methods: {
        createAccount() {
            console.log(this.user);
            Swal.fire('Creada', 'Cuenta creada correctamente', 'success');
            this.$router.push({ name: 'login' });
        },

        handleImageUpload(event) {
            if (this.user.Imagen_de_perfil.length >= 1) {
                Swal.fire('Error', 'No puedes agregar mas de 1', 'error');
                return;
            }

            const files = event.target.files;

            // Itera sobre los archivos seleccionados
            for (let i = 0; i < files.length; i++) {
                const file = files[i];

                if (file) {
                    // Convierte la imagen a una URL de datos para la vista previa
                    const reader = new FileReader();
                    reader.onload = () => {
                        // Agrega la imagen al arreglo de imágenes
                        this.user.Imagen_de_perfil.push(reader.result);
                    };
                    reader.readAsDataURL(file);
                }
            }
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