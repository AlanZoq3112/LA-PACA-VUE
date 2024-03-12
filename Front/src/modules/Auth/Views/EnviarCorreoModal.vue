<template>
    <div>
        <div class="loading-overlay" >
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
export default {
    name: "EnviarCorreo",
    data() {
        return {
            email: ""
        }
    },

    methods: {
        recuperarPassword(){
            console.log(this.email);
        }
    },
}
</script>

<style scoped>
#buttonPassword {
    width: 35%;
    margin-top: 1px;
    background: #c6bedd !important;
    color: black;
}
</style>