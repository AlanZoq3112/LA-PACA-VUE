<template>
    <div>
        <div class="custom-container py-1">
            <div class="row">
                <div v-for="vendedor in vendedores" :key="vendedor.id" class="col-lg-3 mb-4">
                    <b-card class="card-custom mb-2" img-alt="Image" img-height="450px" max-width="200px" img-top>
                        <template #header>
                            <b-carousel :controls="false" indicators :interval="0">
                                <b-carousel-slide
                                    style="min-height: 300px; min-width: 100%; max-height: 300px; max-width: 100%;"
                                    v-for="(imagen, index) in vendedor.imagenes" :key="index"
                                    :img-src="imagen.imageUrl"></b-carousel-slide>
                            </b-carousel>
                        </template>
                        <b-card-text style="min-height:200px; min-width: 100%; max-height: 300px; max-width: 100%;">
                            <h5>{{ vendedor.curp }}</h5>
                            <p>{{ vendedor.telefonoVendedor }}</p>
                            <p>{{ vendedor.rfc }}</p>
                            <p>{{ vendedor.estado ? 'Aprobado' : 'Rechazado' }}</p>
                        </b-card-text>
                        <template #footer>
                            <b-row>
                                <b-col>
                                    <div class="d-flex justify-content-end">
                                        <b-button v-if="!vendedor.estado" @click="changeStatus(vendedor.id, true)"
                                            variant="success">
                                            Aceptar
                                        </b-button>
                                        <b-button v-if="vendedor.estado" @click="changeStatus(vendedor.id, false)"
                                            variant="danger">
                                            Rechazar
                                        </b-button>
                                    </div>
                                </b-col>
                            </b-row>
                        </template>
                    </b-card>
                </div>
                <div v-if="vendedores.length === 0" class="text-center">No hay solicitudes de vendedores.</div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
    name: 'Vendedores',
    data() {
        return {
            vendedores: [],
        };
    },
    methods: {
        async getVendedores() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/vendedor/getAll', {
                    headers: { Authorization: `Bearer ${token}` }
                });
                this.vendedores = response.data.data;
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener los vendedores, intente mas tarde', 'error');
            }
        },
        async changeStatus(vendedorId, status) {
            try {
                const result = await Swal.fire({
                    title: `¿Estás seguro de ${status ? 'aceptar' : 'rechazar'} esta solicitud?`,
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: 'Confirmar',
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    const response = await axios.put('http://localhost:8091/api-carsi-shop/vendedor/aprobarSolicitudVendedor', {
                        id: vendedorId,
                        estado: status
                    }, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    if (response.status === 200) {
                        this.getVendedores();
                        Swal.fire('¡Éxito!', `La solicitud ha sido ${status ? 'aceptada' : 'rechazada'} correctamente`, 'success');
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
                    }
                }
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
            }
        },
    },
    mounted() {
        this.getVendedores();
    },
};
</script>

<style scoped>
.custom-container {
    max-width: 1200px;
    margin: 0 auto;
}

.card-custom {
    max-width: 300px;
    /* Tamaño máximo de la tarjeta */
    min-height: 450px;
    /* Altura mínima de la tarjeta */
    overflow: hidden;
    /* Ocultar el desbordamiento de las imágenes */
}

.card-custom .carousel-inner img {
    width: 100%;
    /* Ajustar el ancho de las imágenes al 100% del contenedor */
    height: auto;
    /* Altura automática para mantener la proporción */
}
</style>