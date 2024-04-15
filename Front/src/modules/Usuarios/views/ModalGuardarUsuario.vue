<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-save-user">
                <header class="text-center border-bottom">
                    <p>Registrar usuario</p>
                </header>
                <main>
                    <form id="registrarUsuario">
                        <b-row>
                            <b-col>
                                <InputText @name="dataChildText" @check="validNombre" />
                            </b-col>
                            <b-col>
                                <InputEmail @email="dataChild" @check="validEmail" />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="role">Rol del usuario: *</label>
                                <b-form-select v-model="usuario.role" :options="roles" required />
                            </b-col>
                            <b-col>
                                <label for="password">Contraseña del usuario: *</label>
                                <b-form-input id="contrasena" type="password" placeholder="Contraseña"
                                    v-model="v$.usuario.password.$model" :state="v$.usuario.password.$dirty
                                        ? !v$.usuario.password.$error
                                        : null
                                        " @blur="v$.usuario.password.$touch()" />
                                <b-form-invalid-feedback v-for="error in v$.usuario.password.$errors" :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="telefono">Número telefónico: *</label>
                                <b-form-input id="telefono" type="text" placeholder="Teléfono"
                                    v-model="v$.usuario.telefono.$model" :state="v$.usuario.telefono.$dirty
                                        ? !v$.usuario.telefono.$error
                                        : null
                                        " @blur="v$.usuario.telefono.$touch()" maxlength="10"
                                    @keypress="onlynumbers" />
                                <b-form-invalid-feedback v-for="error in v$.usuario.telefono.$errors" :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-col>
                            <b-col>
                                <label for="genero">Género: *</label>
                                <b-form-select v-model="usuario.genero" :options="generos" required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <InputFile @img="dataChildImg" @check="validFile" />
                            </b-col>
                            <b-col>
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="fechaNacimiento">Fecha de nacimiento:
                                    </label>
                                    <b-form-datepicker id="fechaNacimiento" class="mb-2"
                                        placeholder="Selecciona una fecha" :label-help="null"
                                        v-model="v$.usuario.fechaNacimiento.$model" :state="v$.usuario.fechaNacimiento.$dirty ? !v$.usuario.fechaNacimiento.$error : null
                                            " @blur="v$.usuario.fechaNacimiento.$touch()"
                                        label-current-month="Fecha máxima" hide-header :date-format-options="{
                                            year: 'numeric',
                                            month: 'numeric',
                                            day: 'numeric',
                                        }" :max="fechaMax"
                                        @hide="v$.usuario.fechaNacimiento.$touch()"></b-form-datepicker>
                                    <b-form-invalid-feedback v-for="error in v$.usuario.fechaNacimiento.$errors"
                                        :key="error.$uid">
                                        {{ error.$message }}
                                    </b-form-invalid-feedback>
                                </div>
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
import axios from 'axios';
import moment from "moment/moment";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength } from "@vuelidate/validators";
const base64Encode = data =>
    new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(data);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
export default {
    name: "modal-save-user",
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
            usuario: {
                nombre: "",
                email: "",
                imagenUrl: "",
                password: "",
                telefono: null,
                genero: "",
                role: "",
                fechaNacimiento: ""
            },
            fechaMax: fechaMax,
            generos: ["Masculino", "Femenino", "Otro"],
            roles: ["ADMIN", "VENDEDOR", "COMPRADOR"],
            valueNombre: "",
            valueEmail: "",
            valueFile: "",

        };
    },
    methods: {
        dataChild(data) {
            this.usuario.email = data;
        },
        validNombre(data) {
            this.valueNombre = data;
        },
        validEmail(data) {
            this.valueEmail = data;
        },
        validFile(data) {
            this.valueFile = data;
        },
        dataChildText(data) {
            this.usuario.nombre = data;
        },
        dataChildImg(data) {
            this.usuario.imagenUrl = data
        },
        onlynumbers(evt) {
            signal(evt);
        },
        onClose() {
            this.$bvModal.hide("modal-save-user");
            this.resetForm();
            this.usuario = {
                nombre: "",
                email: "",
                imagenUrl: "",
                password: "",
                telefono: null,
                genero: "",
                role: "",
                fechaNacimiento: ""
            };
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
                const isValid = this.v$.usuario.$invalid;
                if (result.isConfirmed && !isValid && this.valueEmail && this.valueFile && this.valueNombre) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/usuario/insert", this.usuario, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });

                    if (response.status === 201) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El usuario se registró correctamente",
                            icon: "success"
                        });

                        this.$emit('user-updated');
                        this.$bvModal.hide("modal-save-user");
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar GUARDAR al usuario, intente mas tarde', 'error');
                    }
                } else {
                    Swal.fire('Error', 'Revise los campos', 'error');
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el usuario",
                    icon: "error"
                });
            }
        },
        resetForm() {
            this.usuario = {
                nombre: "",
                email: "",
                imagenUrl: "",
                password: "",
                telefono: null,
                genero: "",
                role: "",
                fechaNacimiento: ""
            };
        }
    },
    validations() {
        return {
            usuario: {
                telefono: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    validFormat: helpers.withMessage(
                        "Teléfono inválido",
                        helpers.regex(/(?:\d\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})/)
                    ),
                },
                fechaNacimiento: {
                    required,
                    maxValue: helpers.withMessage(
                        "Tiene que ser mayor de edad",
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
                password: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    minLength: helpers.withMessage("Mínimo 8 caracteres", minLength(8))
                },
            },
        };
    },

}
</script>

<style scoped>
.success {
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
}
</style>