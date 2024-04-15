<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
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
                                        <label class="form-label" for="ine-images">INE (Selecciona 2 imágenes): </label>
                                        <div class="input-group">
                                            <input type="file" id="ine-images" multiple
                                                @change="handleIneImageUpload($event)" class="form-control"
                                                accept="image/*" :disabled="vendedor.imagenes.length >= 2">
                                            <label class="input-group-text" for="ine-images">Seleccionar
                                                imágenes</label>
                                        </div>
                                        <!-- Vista previa de las imágenes seleccionadas -->
                                        <div v-if="vendedor.imagenes.length > 0" class="preview-container mt-3">
                                            <div v-for="(image, index) in vendedor.imagenes" :key="index" class="image-preview">
                                                <img :src="getImageURL(image)" alt="Imagen previa"
                                                    class="preview-image">
                                            </div>
                                        </div>
                                    </div>


                                    <div class="text-center pt-1 mb-5 pb-1">
                                        <button style="background-color: black; color: white;"
                                            class="btn btn-block fa-lg gradient-custom-2 mb-3"
                                            @click="enviarSolicitud()" type="button" :disabled="disableSendButton">
                                            Enviar Solicitud <i class="fa fa-paper-plane" aria-hidden="true"></i>
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
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength, maxLength } from "@vuelidate/validators";
export default {
    name: "enviarSolicitdVendedor",
    data() {
        return {
            vendedor: {
                telefonoVendedor: "",
                curp: "",
                rfc: "",
                imagenes: [],
            },
            loading: false
        };
    },
    setup() {
        return {
            v$: useVuelidate(),
        };
    },
    methods: {
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
                this.loading = true;
                const isFormCorrect = await this.v$.$validate();
                if (result.isConfirmed && isFormCorrect) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    axios.post('http://localhost:8091/api-carsi-shop/vendedor/insert', this.vendedor, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'multipart/form-data'
                        },
                        
                    })
                        .then(response => {
                            Swal.fire('Enviada', 'Solicitud de vendedor enviada correctamente', 'success');
                            this.$router.push({ name: 'profile-screen' });
                        })
                        .catch(error => {
                            let errorMessage = "Hubo un problema al enviar la solicitud de vendedor";
                            if (error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                            this.loading = false;
                        });
                } else {
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
        handleIneImageUpload(event) {
            const files = event.target.files;
            // Reiniciar el array de imágenes
            this.vendedor.imagenes = [];
            // Verificar si se seleccionaron más de dos imágenes
            if (files.length > 2) {
                Swal.fire({
                    title: "Error",
                    text: "Solo puedes seleccionar hasta 2 imágenes como tu INE",
                    icon: "error"
                });
                // Reiniciar la selección de archivos
                event.target.value = "";
            } else {
                // Agregar las imágenes seleccionadas al array
                for (let i = 0; i < files.length; i++) {
                    const file = files[i];
                    this.vendedor.imagenes.push(file);
                }
            }
        },
        getImageURL(file) {
            return URL.createObjectURL(file);
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
            },
        };
    },
    computed: {
        disableSendButton() {
            // Verificar si el formulario es válido y retornar el resultado inverso (true si es inválido)
            return this.v$.$invalid;
        }
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


.overlay {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.loader {
    border: 8px solid #f3f3f3;
    border-radius: 50%;
    border-top: 8px solid #3498db;
    width: 50px;
    height: 50px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

.preview-container {
    display: flex;
    flex-wrap: wrap;
}

.image-preview {
    margin-right: 10px;
    margin-bottom: 10px;
}

.preview-image {
    max-width: 100px;
    max-height: 100px;
}
</style>