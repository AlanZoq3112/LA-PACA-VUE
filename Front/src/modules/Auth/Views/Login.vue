<template>
    <div>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-10">
                    <div class="card rounded-3 text-black">
                        <div class="row g-0">
                            <div class="col-lg-6">
                                <div class="card-body p-md-5 mx-md-4">
                                    <div class="text-center">
                                        <img src="./../../../assets/Logo.png" style="width: 200px;" alt="logo">
                                        <h4 class="mt-1 mb-5 pb-1">Carsi Shop</h4>
                                    </div>

                                    <form>

                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example11">Correo electrónico</label>
                                            <input v-model="user.username" type="email" id="form2Example11"
                                                class="form-control" placeholder="Correo electronico de tu cuenta" />
                                        </div>

                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example22">Contraseña</label>
                                            <div class="input-group">
                                                <input v-model="user.password"
                                                    :type="showPassword ? 'text' : 'password'" id="form2Example22"
                                                    class="form-control" placeholder="Ingresa tu contraseña" />
                                                <button class="btn btn-outline-secondary" type="button"
                                                    @click="togglePasswordVisibility">
                                                    <i :class="['fas', showPassword ? 'fa-eye-slash' : 'fa-eye']"></i>
                                                </button>
                                            </div>
                                        </div>

                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                                @click="login" type="button">
                                                Iniciar Sesion <i class="fas fa-sign-in-alt"></i>
                                            </button>
                                            <a class="text-muted" href="recuperarContrasena">¿Olvidaste tu contraseña?</a>
                                        </div>

                                        <div class="d-flex align-items-center justify-content-center pb-4">
                                            <p class="mb-0 me-2">¿No tienes cuenta?</p>
                                            <a href="crearCuenta" class="text-muted"> Crear cuenta</a>

                                        </div>

                                    </form>

                                </div>
                            </div>
                            <div class="col-lg-6 d-flex align-items-center gradient-custom-2">
                                <div class="text-center px-3 py-4 p-md-5 mx-md-4">
                                    <img src="https://img.freepik.com/vector-premium/mucha-ropa-colgada-perchero-sobre-fondo-blanco_1308-53922.jpg"
                                        alt="Logo de Carsi Shop" class="mb-3" style="max-width: 100px;max-width: 300px">
                                    <h4 class="mb-4" style="color: black;">Carsi Shop</h4>
                                    <p style="color: black;" class="small mb-0">Donde la moda y los precios van de la
                                        mano</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <EnviarCorreoModal />
    </div>
</template>
<script>
import EnviarCorreoModal from './EnviarCorreoModal.vue';
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    components: {
        EnviarCorreoModal,
    },
    name: "login",
    data() {
        return {
            user: {
                username: "",
                password: ""
            },
            showPassword: false
        }
    },

    methods: {
        async login() {
            try {
                // Envia las credenciales al backend
                const response = await axios.post('http://localhost:8090/api-carsi-shop/auth/signin', {
                    email: this.user.username,
                    password: this.user.password
                });

                // Extrae el token JWT y los datos del usuario de la respuesta
                const token = response.data.token;
                const usuario = response.data.usuario;

                // Guarda el token JWT y los datos del usuario en el localStorage
                localStorage.setItem('token', token);
                localStorage.setItem('usuario', JSON.stringify(usuario));

                // Redirige al usuario a la pantalla de perfil
                this.$router.push({ name: 'profile-screen' });

                // Muestra una alerta de bienvenida al sistema
                Swal.fire({
                    title: '¡Bienvenido!',
                    text: 'Has iniciado sesión correctamente.',
                    icon: 'success',
                    position: 'top-end', // Posiciona la alerta en la esquina superior derecha
                    toast: true, // Activa el modo toast para la alerta
                    showConfirmButton: false, // No muestra el botón de confirmación
                    timer: 3000 // Cierra automáticamente la alerta después de 3 segundos
                });
            } catch (error) {
                // Si hay un error en la autenticación, muestra un mensaje de error
                let errorMessage = 'Error al iniciar sesión, revisa correctamente tu correo y contraseña';
                if (error.response && error.response.data && error.response.data.length > 0) {
                    errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                }
                Swal.fire('Error', errorMessage, 'error');
            }
        },
        togglePasswordVisibility() {
            this.showPassword = !this.showPassword; // Cambia el estado de visibilidad de la contraseña
        }

    },
}
</script>

<style scoped>
* {
    box-sizing: border-box;
}

.login-container {
    background: linear-gradient(110.52deg,
            #ffffff 81.1%,
            #edf5fd 81.1%);
    display: flex;
    justify-content: center;
    align-items: center;
}

.login {
    width: 100%;
    /* Ajusta el ancho aquí según tus necesidades */
    max-width: 700px;
    padding: 40px;
    border-radius: 15px;
    background: #f9f9f9;
    text-align: center;
    border: 1px solid #808080;
    height: 100%;
}

.login-form input,
.login-form button {
    height: 60px;
    font-family: inherit;
    font-size: 16px;
    border-radius: 8px;
}

.login-form input {
    background: transparent;
    border: solid rgb(169, 165, 165);
    font-size: 18px;
    padding: 0 20px 0 20px;
}

#loginBtn {
    cursor: pointer;
    background: #f5f5f5;
    color: #f9f9f9;
    font-weight: 600;
    width: 100%;
}

.login-form a {
    color: #f5f5f5;
    font-size: 15px;
    text-align: center;
}
</style>