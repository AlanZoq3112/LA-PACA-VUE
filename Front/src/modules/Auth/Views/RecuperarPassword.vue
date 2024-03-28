<template>
    <div>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-10">
                    <div class="card rounded-3 text-black">
                        <div class="row g-0">
                            <!-- Envia el correo para recuperar la contraseña -->
                            <div class="col-lg-6">
                                <div class="card-body p-md-5 mx-md-4">
                                    <!-- Contenido del formulario de envío de correo de recuperación -->
                                    <div class="text-center">
                                        <img src="./../../../assets/Correo.png" style="width: 150px;" alt="logo">
                                        <h4 class="mt-1 mb-5 pb-1">Carsi Shop</h4>
                                        <p>En este apartado debes colocar el correo electrónico ligado a tu inicio de
                                            sesión, asegúrate de tener acceso a ese correo ya que se te enviará un
                                            código de confirmación</p>
                                    </div>

                                    <form>
                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example11">Correo electrónico: </label>
                                            <input v-model="recoverPassword.email" type="email" id="form2Example11"
                                                class="form-control" placeholder="Correo electronico de tu cuenta"
                                                :disabled="showRecoveryForm" />
                                        </div>

                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                                @click="sendEmail" type="button">
                                                Enviar correo de recuperación <i class="fas fa-sign-in-alt"></i>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <!-- Restaura la contraseña -->
                            <div class="col-lg-6" v-if="showRecoveryForm">
                                <div class="card-body p-md-5 mx-md-4">
                                    <!-- Contenido del formulario de restaurar contraseña -->
                                    <form>
                                        <div class="text-center">
                                            <img src="./../../../assets/Candado.png" style="width: 150px;" alt="logo">
                                            <h4 class="mt-1 mb-5 pb-1">Restaurar contraseña</h4>
                                        </div>
                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example12">Código de seguridad: </label>
                                            <input v-model="newPassword.secretPass" type="text" id="form2Example12"
                                                class="form-control" placeholder="Código de seguridad" />
                                        </div>
                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example13">Nueva contraseña: </label>
                                            <input v-model="newPassword.newPassword" type="password" id="form2Example13"
                                                class="form-control" placeholder="Nueva contraseña" />
                                        </div>
                                        <div class="form-outline mb-4">
                                            <label class="form-label" for="form2Example14">Repite la contraseña:
                                            </label>
                                            <input v-model="repitNewPassword" type="password" id="form2Example14"
                                                class="form-control" placeholder="Repite la contraseña" />
                                        </div>

                                        <div class="text-center pt-1 mb-5 pb-1">
                                            <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                                @click="recoveriPassword" type="button">
                                                Restaurar contraseña <i class="fas fa-sign-in-alt"></i>
                                            </button>
                                        </div>
                                    </form>
                                </div>
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
    name: "RecuperarPassword",
    data() {
        return {
            recoverPassword: {
                email: ""
            },
            newPassword: {
                newPassword: "",
                secretPass: ""
            },
            repitNewPassword: "",
            showRecoveryForm: false
        }
    },

    methods: {
        sendEmail() {
            axios.post('http://localhost:8090/api-carsi-shop/recovery/', {
                email: this.recoverPassword.email
            })
                .then(response => {
                    if (response.status === 200) {
                        Swal.fire('Enviada', 'Solicitud de cambio de contraseña enviada correctamente', 'success');
                        // Cambia el valor de showRecoveryForm para mostrar el formulario de restauración
                        this.showRecoveryForm = true;
                        console.log(response.data);
                    } else {
                        Swal.fire('Error', 'Hubo un problema al procesar la solicitud', 'error');
                    }
                })
                .catch(error => {
                    let errorMessage = "Hubo un problema al crear la cuenta";
                    if (error.response && error.response.data && error.response.data.length > 0) {
                        errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                    }
                    Swal.fire('Error', errorMessage, 'error');
                });
        },
        recoveriPassword() {
            console.log(this.recoverPassword.email, this.newPassword.newPassword, this.newPassword.secretPass);
            axios.put('http://localhost:8090/api-carsi-shop/recovery/updatePassword', {
                email: this.recoverPassword.email,
                newPassword: this.newPassword.newPassword,
                secretPass: this.newPassword.secretPass
            })
                .then(response => {
                    if (response.status === 200) {
                        Swal.fire('Actualizada', 'Tu contraseña fue restaurada correctamente', 'success');
                        console.log(response.data);
                    } else {
                        Swal.fire('Error', 'Hubo un problema al procesar la solicitud', 'error');
                    }
                })
                .catch(error => {
                    let errorMessage = "Hubo un problema al crear la cuenta";
                    if (error.response && error.response.data && error.response.data.length > 0) {
                        errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                    }
                    Swal.fire('Error', errorMessage, 'error');
                });
        }

    }

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