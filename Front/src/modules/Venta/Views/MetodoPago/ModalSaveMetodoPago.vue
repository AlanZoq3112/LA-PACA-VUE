<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <b-modal hide-footer hide-header centered id="modal-guardar-metodopago" style="max-width: 80vw;">
            <header class="text-center border-bottom">
                <p>Registrar Método de Pago</p>
            </header>
            <main>
                <b-form @submit.prevent="save">
                    <div class="card mb-3">
                        <div class="card-header">
                            <h5 class="card-title">Información de la Tarjeta</h5>
                        </div>
                        <div class="card-body">
                            <b-form-group label="Tipo" label-for="tipo">
                                <b-form-select v-model="tarjeta.tipo" :options="tiposTarjeta" required></b-form-select>
                            </b-form-group>
                            <b-form-group label="Número" label-for="numero">
                                <div class="input-group">
                                    <input v-model="tarjeta.numero" type="text" class="form-control" id="numero"
                                        placeholder="0000 0000 0000 0000" required>
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fa fa-credit-card"></i></span>
                                    </div>
                                </div>
                            </b-form-group>
                            <div class="row">
                                <div class="col-md-6">
                                    <b-form-group label="CVV" label-for="cvv">
                                        <input v-model="tarjeta.cvv" type="text" class="form-control" id="cvv"
                                            placeholder="CVV" required>
                                    </b-form-group>
                                </div>
                                <div class="col-md-6">
                                    <b-form-group label="Titular" label-for="titular">
                                        <input v-model="tarjeta.titular" type="text" class="form-control" id="titular"
                                            placeholder="Titular" required>
                                    </b-form-group>
                                </div>
                            </div>
                            <b-form-group label="Fecha de vencimiento" label-for="fechaVencimiento">
                                <div class="input-group">
                                    <input v-model="tarjeta.fechaVencimiento" type="text" class="form-control"
                                        id="fechaVencimiento" placeholder="MM/YY" required>
                                    <div class="input-group-append">
                                        <span class="input-group-text"><i class="fa fa-calendar"></i></span>
                                    </div>
                                </div>
                            </b-form-group>
                        </div>
                    </div>
                    <div class="d-flex justify-content-center">
                        <b-button type="button" class="cancel mr-2" @click="onClose">Cancelar</b-button>
                        <b-button type="submit" class="success ml-2" variant="success">Registrar</b-button>
                    </div>
                </b-form>
            </main>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
    name: "ModalGuardarMetodoPago",
    data() {
        return {
            tarjeta: {
                tipo: "",
                numero: "",
                cvv: null,
                titular: "",
                fechaVencimiento: "",
            },
            loading: false,
            tiposTarjeta: ["CREDITO", "DEBITO"]
        };
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-metodopago");
            this.resetForm();
        },
        resetForm() {
            this.tarjeta = {
                tipo: "",
                numero: "",
                cvv: "",
                titular: "",
                fechaVencimiento: "",
            };
        },
        async save() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de registrar la dirección?",
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
                    axios.post('http://localhost:8091/api-carsi-shop/pago/insertarFormaPago', this.tarjeta, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                        .then(response => {
                            this.resetForm();
                            Swal.fire('Registrada', 'Método de pago registrado correctamente', 'success');
                            this.$emit('metodopago-saved');
                            this.$bvModal.hide("modal-guardar-metodopago");
                        })
                        .catch(error => {
                            let errorMessage = "Hubo un problema al registrar el método de pago";
                            if (error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                        });
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar la tarjeta",
                    icon: "error"
                });
            } finally {
                this.loading = false;
            };
        }
    }
};
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
</style>