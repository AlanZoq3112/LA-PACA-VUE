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
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="nombre">Nombre(s): </label>
                                        <input v-model="user.Nombre" type="text" id="nombre" class="form-control"
                                            placeholder="Nombre" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="apellidos">Apellido(s): </label>
                                        <input v-model="user.Apellidos" type="text" id="apellidos" class="form-control"
                                            placeholder="Apellidos" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="correo">Correo electrónico: </label>
                                        <input v-model="user.Correo" type="email" id="correo" class="form-control"
                                            placeholder="Correo electrónico" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="contrasena">Contraseña: </label>
                                        <input v-model="user.Contraseña" type="password" id="contrasena"
                                            class="form-control" placeholder="Contraseña" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Teléfono: </label>
                                        <input v-model="user.Telefono" type="tel" id="telefono" class="form-control"
                                            placeholder="Teléfono" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="direccion">Dirección: </label>
                                        <input v-model="user.Direccion" type="text" id="direccion" class="form-control"
                                            placeholder="Dirección" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="fechaNacimiento">Fecha de nacimiento: </label>
                                        <input v-model="user.Fecha_de_nacimiento" type="date" id="fechaNacimiento"
                                            class="form-control" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="genero">Género: </label>
                                        <select v-model="user.Genero" id="genero" class="form-select">
                                            <option value="">Seleccionar</option>
                                            <option value="masculino">Masculino</option>
                                            <option value="femenino">Femenino</option>
                                            <option value="otro">Otro</option>
                                        </select>
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="imagenPerfil">Imagen de perfil</label>
                                        <input @change="handleFileChange" type="file" id="imagenPerfil"
                                            class="form-control" />
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
                Imagen_de_perfil: null
            },
        };
    },
    methods: {
        createAccount() {
            console.log(this.user);
            this.$router.push({ name: 'login' });
        },

        handleFileChange(event) {
            const file = event.target.files[0]; // get the selected file
            this.user.Imagen_de_perfil = file; // update the property with the file
        },
    },
};
</script>

<style scoped>
/* Agrega estilos específicos si es necesario */
</style>