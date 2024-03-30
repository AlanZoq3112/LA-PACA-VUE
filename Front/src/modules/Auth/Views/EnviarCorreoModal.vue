<template>
    <div>
        <div class="loading-overlay">
            <div class="loading-spinner"></div>
        </div>

        <b-modal id="EnviarCorreo" centered title="Recuperación de contraseña" ok-title="Enviar correo"
            no-close-on-backdrop :footer-class="['d-flex', 'justify-content-center']" hide-footer>
            <b-form class="text-center">
                <b-form-group class="text-weight-bold" label="Correo electrónico: " label-for="inputEmail">
                    <b-form-input id="inputEmail" v-model="email" type="email" placeholder="email@example.com"
                        class="mt-2 text-center w-75 mx-auto"
                        aria-describedby="input-live-help input-live-feedback"></b-form-input>

                </b-form-group>
                <b-row>
                    <b-col>
                        <b-button id="buttonPassword" class="mt-3" type="submit" @click="recuperarPassword">
                            Enviar
                        </b-button>
                    </b-col>
                </b-row>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "EnviarCorreo",
    data() {
        return {
            email: ""
        }
    },

    methods: {
        recuperarPassword() {
            axios.post('http://localhost:8090/api-carsi-shop/recovery/', {
                email: this.email
            })
                .then(response => {
                    if (response.status === 200) {
                        Swal.fire('Enviada', 'Solicitud de cambio de contraseña enviada correctamente', 'success');
                        console.log(response.data); // Puedes hacer algo con la respuesta si es necesario
                    } else {
                        Swal.fire('Error', 'Hubo un problema al procesar la solicitud', 'error');
                    }
                })
                .catch(error => {
                    let errorMessage = "Hubo un problema al mandar el correo";
                    if (error.response && error.response.data && error.response.data.length > 0) {
                        errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                    }
                    Swal.fire('Error', errorMessage, 'error');
                });
        }
    },
}
</script>

<style scoped>
#buttonPassword {
    width: 35%;
    margin-top: 1px;
    background: #008c6f !important;
    color: rgb(255, 255, 255);
}
</style>