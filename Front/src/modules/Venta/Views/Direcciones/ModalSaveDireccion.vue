<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <b-modal hide-footer hide-header centered id="modal-guardar-direcciones" style="max-width: 80vw;">
            <header class="text-center border-bottom">
                <p>Registrar dirección</p>
            </header>
            <main>
                <b-form @submit.prevent="enviarSolicitud">
                    <b-row>
                        <b-col>
                            <b-form-group label="Calle" label-for="calle">
                                <b-form-input v-model="direccion.calle" type="text" id="calle" required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Número" label-for="Numero">
                                <b-form-input v-model="direccion.numero" type="text" id="numero" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <b-form-group label="Colonia" label-for="Colonia">
                                <b-form-input v-model="direccion.colonia" type="text" id="colonia" required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Municipio" label-for="Municipio">
                                <b-form-input v-model="direccion.municipio" type="text" id="municipio" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <b-form-group label="Estado" label-for="Estado">
                                <b-form-input v-model="direccion.estado" type="text" id="estado" required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Código Postal" label-for="CP">
                                <b-form-input v-model="direccion.codigoPostal" type="text" id="CP" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-form-group label="Referencias" label-for="referencia">
                        <b-form-textarea v-model="direccion.referencia" id="descripcion" rows="3"
                            required></b-form-textarea>
                    </b-form-group>

                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <b-button type="button" class="cancel" @click="onClose">Cancelar</b-button>
                        </b-col>
                        <b-col>
                            <b-button type="submit" class="success" variant="success">Registrar</b-button>
                        </b-col>
                        <b-col></b-col>
                    </b-row>

                </b-form>
            </main>
        </b-modal>
    </div>
</template>
<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "ModalGuardarDireccion",
    data() {
        return {
            direccion: {
                calle: "",
                numero: null,
                colonia:"",
                estado: "",
                municipio: "",
                codigoPostal: "",
                referencia: "",
            },
            loading: false
        }
    },

    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-direcciones");
            this.resetForm();
        },

        resetForm() {
            this.direccion = {
                calle: "",
                numero: "",
                colonia:"",
                estado: "",
                municipio: "",
                codigoPostal: "",
                referencia: "",
            }
        },

        async enviarSolicitud() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás registrar la dirección?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    this.loading = true;
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    axios.post('http://localhost:8091/api-carsi-shop/direccion/insert', this.direccion, {
                            headers: { Authorization: `Bearer ${token}` }
                        })
                        .then(response => {
                            Swal.fire('Registrada', 'Direccion del usuario registrada correctamente', 'success');
                            this.$emit('direccion-saved');
                            this.$bvModal.hide("modal-guardar-direcciones");
                        })
                        .catch(error => {
                            let errorMessage = "Hubo un problema al registrar la direccion";
                            if (error.response && error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                        });
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar la direccion",
                    icon: "error"
                });
            } finally {
                this.loading = false;
            };
        },

    },
}
</script>

<style>
.success {
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
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