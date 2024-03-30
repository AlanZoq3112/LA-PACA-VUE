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
                                                <InputText @name="dataChildText" @check="valid"/>                                                
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="genero">Género: </label>
                                                <multi-select id="genero" :class="{
                                                        'is-invalid': v$.user.genero.$error,
                                                        'is-valid': !v$.user.genero.$invalid,
                                                    }" v-model="v$.user.genero.$model"
                                                    placeholder="Selecciona un género" label="name" :options="generos"
                                                    track-by="name" :multiple="false"
                                                    selectLabel="Presiona para seleccionar"
                                                    deselectLabel="Presiona para eliminar" selectedLabel="Seleccionado"
                                                    @close="v$.user.genero.$touch()">
                                                </multi-select>
                                                <b-form-invalid-feedback v-for="error in v$.user.genero.$errors"
                                                    :key="error.$uid">
                                                    {{ error.$message }}
                                                </b-form-invalid-feedback>
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <InputEmail @email="dataChild"/> 
                                                <p>{{ confirmPassword }}</p>                                                                                               
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="contrasena">Contraseña: </label>
                                                <b-form-input id="contrasena" type="password" placeholder="Contraseña"
                                                    v-model="v$.user.password.$model" :state="v$.user.password.$dirty
                                                        ? !v$.user.password.$error
                                                        : null
                                                        " @blur="v$.user.password.$touch()" />
                                                <b-form-invalid-feedback v-for="error in v$.user.password.$errors"
                                                    :key="error.$uid">
                                                    {{ error.$message }}
                                                </b-form-invalid-feedback>
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <b-row>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="telefono">Teléfono: </label>
                                                <b-form-input id="telefono" type="text" placeholder="Teléfono"
                                                    v-model="v$.user.telefono.$model" :state="v$.user.telefono.$dirty
                                                        ? !v$.user.telefono.$error
                                                        : null
                                                        " @blur="v$.user.telefono.$touch()" maxlength="10"
                                                    @keypress="onlynumbers" />
                                                <b-form-invalid-feedback v-for="error in v$.user.telefono.$errors"
                                                    :key="error.$uid">
                                                    {{ error.$message }}
                                                </b-form-invalid-feedback>
                                            </div>
                                        </b-col>
                                        <b-col>
                                            <div class="form-outline mb-4">
                                                <label class="form-label" for="fechaNacimiento">Fecha de nacimiento:
                                                </label>
                                                <b-form-datepicker id="fechaNacimiento" class="mb-2"
                                                    placeholder="Selecciona una fecha" :label-help="null"
                                                    v-model="v$.user.fechaNacimiento.$model" :state="v$.user.fechaNacimiento.$dirty ? !v$.user.fechaNacimiento.$error : null
                                                        " @blur="v$.user.fechaNacimiento.$touch()"
                                                    label-current-month="Fecha máxima" hide-header :date-format-options="{
                                                        year: 'numeric',
                                                        month: 'numeric',
                                                        day: 'numeric',
                                                    }" :max="fechaMax"
                                                    @hide="v$.user.fechaNacimiento.$touch()"></b-form-datepicker>
                                                <b-form-invalid-feedback
                                                    v-for="error in v$.user.fechaNacimiento.$errors" :key="error.$uid">
                                                    {{ error.$message }}
                                                </b-form-invalid-feedback>
                                            </div>
                                        </b-col>
                                    </b-row>
                                    <div class="form-outline mb-4">
                                        <InputFile @img="dataChildImg"/>                                                                             
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
import { useVuelidate } from "@vuelidate/core";
import moment from "moment/moment";
import { required, helpers, minLength, maxLength, } from "@vuelidate/validators";
export default {
    name: "crearCuenta",
    components: {
        InputEmail: () => import('../../../components/input_validations/InputEmail.vue'),
        InputText: () => import('../../../components/input_validations/InputText.vue'),
        InputFile: () => import('../../../components/input_validations/InputFile.vue'),
    },
    setup() {
        return {
            v$: useVuelidate(),
        };
    },
    data() {
        const ahora = new Date();
        const hoy = new Date(ahora.getFullYear(), ahora.getMonth(), ahora.getDate());
        const fechaMax = new Date(hoy);
        fechaMax.setFullYear(fechaMax.getFullYear() - 18);
        return {
            input: "",
            generos: [
                { name: "Masculino" },
                { name: "Femenino" },
                { name: "Otro" },
            ],
            fechaMax: fechaMax,
            user: {
                nombre: "",
                genero: "",
                imagenUrl: "",
                email: "",
                password: "",
                telefono: "",
                fechaNacimiento: ""
            },
            generoOption: "",
            confirmPassword: "",
        };
    },
    methods: {
        dataChild(data){            
            this.confirmPassword = data;
        },
        valid(data){            
            this.user.email = data;
        },
        dataChildText(data){            
            this.user.nombre = data;
        },
        dataChildImg(data){            
            this.user.imagenUrl = data;
        },
        onlynumbers(evt) {
            signal(evt);
        },
        createAccount() {
            console.log(this.user);
            const generoFinal = this.user.genero.name;
            this.user.genero = generoFinal;
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
    },
    validations() {        
        return {
            user: {                
                telefono: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    validFormat: helpers.withMessage(
                        "Teléfono inválido",
                        helpers.regex(/(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})/)
                    ),
                },
                fechaNacimiento: {
                    required,
                    maxValue: helpers.withMessage(
                        "Sobrepasa la fecha máxima",
                        (value) => {
                            return moment(value).isSameOrBefore(
                                new Date(
                                    new Date().getFullYear() - 18,
                                    new Date().getMonth(),
                                    new Date().getDate()
                                )
                            );
                        }
                    ),
                },
                genero: {
                    required: helpers.withMessage("Campo obligatorio", required),
                },
                password: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    minLength: helpers.withMessage("Mínimo 8 caracteres", minLength(8))
                },                
            },
        };
    },
};
</script>

<style scoped>

</style>