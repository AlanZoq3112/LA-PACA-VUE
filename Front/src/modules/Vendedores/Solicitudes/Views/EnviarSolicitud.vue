<template>
    <div>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-12">
                    <div class="card rounded-3 text-black">
                        <div class="col-lg-12">
                            <div class="card-body p-md-5 mx-md-4">
                                <div class="text-center">
                                    <img src="https://4.bp.blogspot.com/-IXeMrjpLYvY/Ufe21ZaAbLI/AAAAAAAAADU/Sm3ZieV49bM/s1600/vendedor.png"
                                        style="width: 100px;" alt="logo" />
                                    <h2 class="mt-1 mb-5 pb-1">Solicitud de vendedor</h2>
                                    <h5 class="mt-1 mb-5 pb-1">Para poder ser parte de los vendedores de <b>Carsi
                                            Shop</b> debes proporcionar los siguientes datos</h5>
                                </div>

                                <form>
                                    <b-row>
                                        <b-col>
                                            <label class="form-label" for="curp">CURP: </label>
                                            <b-form-input id="curp" type="text" placeholder="CURP"
                                                v-model="v$.vendedor.curp.$model" :state="v$.vendedor.curp.$dirty
                                                    ? !v$.vendedor.curp.$error
                                                    : null
                                                    " @blur="v$.vendedor.curp.$touch()" maxlength="18" />
                                            <b-form-invalid-feedback v-for="error in v$.vendedor.curp.$errors"
                                                :key="error.$uid">
                                                {{ error.$message }}
                                            </b-form-invalid-feedback>
                                        </b-col>
                                        <b-col>
                                            <label class="form-label" for="rfc">RFC: </label>
                                            <b-form-input id="rfc" type="text" placeholder="RFC"
                                                v-model="v$.vendedor.rfc.$model" :state="v$.vendedor.rfc.$dirty
                                                    ? !v$.vendedor.rfc.$error
                                                    : null
                                                    " @blur="v$.vendedor.rfc.$touch()" maxlength="13" />
                                            <b-form-invalid-feedback v-for="error in v$.vendedor.rfc.$errors"
                                                :key="error.$uid">
                                                {{ error.$message }}
                                            </b-form-invalid-feedback>
                                        </b-col>
                                    </b-row>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Teléfono: </label>
                                        <b-form-input id="telefono" type="text" placeholder="Teléfono"
                                            v-model="v$.vendedor.telefonoVendedor.$model" :state="v$.vendedor.telefonoVendedor.$dirty
                                                ? !v$.vendedor.telefonoVendedor.$error
                                                : null
                                                " @blur="v$.vendedor.telefonoVendedor.$touch()" maxlength="10"
                                            @keypress="onlynumbers" />
                                        <b-form-invalid-feedback v-for="error in v$.vendedor.telefonoVendedor.$errors"
                                            :key="error.$uid">
                                            {{ error.$message }}
                                        </b-form-invalid-feedback>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Ine: </label>
                                        <!--
                                            <input v-model="vendedor.ine" type="text" id="ine" class="form-control"
                                            placeholder="INE" />
                                        -->
                                        <InputFilesMax2 @img="dataChildFile" @check="validFile" />
                                    </div>


                                    <div class="text-center pt-1 mb-5 pb-1">
                                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                            @click="enviarSolicitud()" type="button">
                                            Enviar Solicitud <i class="fa fa-paper-plane" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <p>{{ vendedor }}</p>

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
import { required, helpers, minLength, maxLength } from "@vuelidate/validators";
export default {
    name: "enviarSolicitdVendedor",
    components: {        
        InputFilesMax2: () => import('../../../../components/input_validations/InputFilesMax2.vue'),
    },
    setup() {
        return {
            v$: useVuelidate(),
        };
    },
    data() {
        return {
            vendedor: {
                telefonoVendedor: "",
                curp: "",
                rfc: "",
                ine: [],
            },
            valueFile: false,
        };
    },
    methods: {
        dataChildFile(data) {
            this.vendedor.ine = data;
        },
        validFile(data) {
            this.valueFile = data;
        },
        onlynumbers(evt) {
            signal(evt);
        },
        async enviarSolicitud() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de enviar la solicitud de vededor?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                const isFormCorrect = await this.v$.$validate();
                console.log(isFormCorrect);
                if (result.isConfirmed && isFormCorrect) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    axios.post('http://localhost:8091/api-carsi-shop/vendedor/insert', this.vendedor, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                        .then(response => {
                            Swal.fire('Enviada', 'Solicitud de vendedor enviada correctamente', 'success');
                            this.$router.push({ name: 'profile-screen' });
                        })
                        .catch(error => {
                            let errorMessage = "Hubo un problema al enviar la solicitud de vendedor";
                            if (error.response && error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                        });
                }else{
                    Swal.fire('Error', "Revise todos los campos", 'error');
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el usuario",
                    icon: "error"
                });
            }
        },


    },
    validations() {
        return {
            vendedor: {
                telefonoVendedor: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    validFormat: helpers.withMessage(
                        "Teléfono inválido",
                        helpers.regex(/(?:\d\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})/)
                    ),
                },
                curp: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    validFormat: helpers.withMessage(
                        "CURP inválida",
                        helpers.regex(/^[a-zA-Z0-9]+$/)
                    ),
                    minLength: helpers.withMessage("La CURP debe tener exactamente 18 caracteres", minLength(18)),
                    maxLength: helpers.withMessage("La CURP debe tener exactamente 18 caracteres", maxLength(18)),
                },
                rfc: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    validFormat: helpers.withMessage(
                        "RFC inválido",
                        helpers.regex(/^[a-zA-Z0-9]+$/)
                    ),
                    minLength: helpers.withMessage("El RFC debe tener exactamente 13 caracteres", minLength(13)),
                    maxLength: helpers.withMessage("El RFC debe tener exactamente 13 caracteres", maxLength(13)),
                },
                ine: {
                    required: helpers.withMessage("Campo obligatorio", required),
                    maxLength: helpers.withMessage("Maximo 30 caracteres", maxLength(30)),
                    validFormat: helpers.withMessage(
                        "INE invalida",
                        helpers.regex(/^[a-zA-Z0-9]+$/)
                    ),
                },
            },
        };
    },
};
</script>

<style scoped>
.input-group {
    position: relative;
    width: 100%;
}

.input-group input[type="file"] {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}

.input-group label {
    cursor: pointer;
}

.image-preview-container {
    display: flex;
    flex-wrap: nowrap;
    justify-content: flex-start;
    gap: 10px;
}

.image-preview {
    max-width: 200px;
}
</style>