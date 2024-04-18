<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <div>
            <div class="custom-container py-1 h-200">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-12">
                        <div class="card rounded-3 text-black">
                            <div class="col-lg-12">
                                <div class="card-body p-md-5 mx-md-4">
                                    <div class="d-flex justify-content-between align-items-center mb-4 tabla">
                                        <div>
                                            <h4>Mis compras <i class="fa fa-shopping-basket" aria-hidden="true"></i>
                                            </h4>

                                        </div>

                                    </div>
                                    <div class="row">
                                        <div v-for="producto in paginatedProductos" :key="producto.id"
                                            class="col-lg-3 mb-4">
                                            <!-- Tu tarjeta de producto aquí -->
                                            <b-card class="card-custom mb-2" img-alt="Image" img-height="50px"
                                                max-width="250px" img-top>
                                                <b-card-text
                                                    style="min-height:200px; min-width: 100%; max-height: 50px; max-width: 100%;">
                                                    <p>Enviado: {{ producto.direccion.calle }} {{
                                            producto.direccion.numero }} {{ producto.direccion.colonia }} ,
                                                        {{
                                            producto.direccion.municipio }} ,
                                                        {{ producto.direccion.estado }}</p>
                                                    <p>El producto fué: {{ producto.estado }}</p>
                                                </b-card-text>
                                                <template #footer>
                                                    <b-row>
                                                        <b-col>
                                                            <b-button variant="faded" v-b-tooltip.hover="'Detalles de la compra'"  @click="openModal(producto)">Ver</b-button>
                                                        </b-col>

                                                        <b-col v-if="producto.estado === 'EN_CAMINO'">
                                                            <b-button variant="faded" type="button" style="color:green;" v-b-tooltip.hover="'Recibí mi compra'"
                                                                @click="recibiCompra(producto)">Completar</b-button>
                                                        </b-col>
                                                        <b-col v-if="producto.estado === 'ENTREGADO'">
                                                            <b-button variant="faded" type="button" style="color:red;" v-b-tooltip.hover="'Cancelar mi compra'"
                                                                @click="cancelarCompra(producto)">Cancelar</b-button>
                                                        </b-col>
                                                    </b-row>
                                                </template>
                                            </b-card>
                                        </div>
                                    </div>
                                    <!-- Agrega la paginación aquí -->
                                    <b-pagination v-model="currentPage" :total-rows="productos.length"
                                        :per-page="perPage" align="center" class="my-4" />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ModalInfo :producto="selectedProduct" />
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import ModalInfo from './ModalInfo.vue';

export default {
    name: 'Historial',
    components: {
        ModalInfo,
    },
    data() {
        return {
            productos: [],
            currentPage: 1,
            perPage: 8,
            selectedProduct: null,
            loading: false,
        };
    },
    computed: {
        paginatedProductos() {
            const start = (this.currentPage - 1) * this.perPage;
            const end = start + this.perPage;
            return this.productos.slice(start, end);
        },
    },
    methods: {
        async getProductos() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/pago/mis-pedidos', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.productos = response.data.data;
                console.log(this.productos);
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener tus compras, intente mas tarde', 'error');
            }
        },
        async openModal(producto) {
            this.selectedProduct = producto;
            this.$bvModal.show('modal-info-productos');
        },
        async recibiCompra(producto) {
            try {
                const result = await Swal.fire({
                    title: '¿Estás seguro de que recibiste la compra?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    this.loading = true;
                    let idVenta = producto.id;
                    console.log(idVenta);
                    const token = localStorage.getItem('token');
                    await axios.post('http://localhost:8091/api-carsi-shop/pago/marcar-como-entregado', { id: idVenta }, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        
                    });
                    Swal.fire('Entregada', 'La compra se registró como entregada correctamente', 'success');
                }
            } catch (error) {
                console.log(error);
                let errorMessage = "Hubo un problema al intentar marcar como entregada la compra, intente más tarde";
                if (error.response?.data && error.response.data.length > 0) {
                    errorMessage = error.response.data[0];
                }
                Swal.fire('Error', errorMessage, 'error');
            } finally {
                this.loading = false;
                this.getProductos();
            }
        },
        async cancelarCompra(producto) {
            try {
                const result = await Swal.fire({
                    title: '¿Estás seguro de cancelar la compra?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    this.loading = true;
                    let idVenta = producto.id;
                    console.log(idVenta);
                    const token = localStorage.getItem('token');
                    await axios.post('http://localhost:8091/api-carsi-shop/pago/marcar-como-devuelto', { id: idVenta }, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        
                    });
                    Swal.fire('Cancelada', 'La compra se canceló correctamente', 'success');
                }
            } catch (error) {
                console.log(error);
                let errorMessage = "Hubo un problema al intentar marcar como entregada la compra, intente más tarde";
                if (error.response?.data && error.response.data.length > 0) {
                    errorMessage = error.response.data[0];
                }
                Swal.fire('Error', errorMessage, 'error');
            } finally {
                this.loading = false;
                this.getProductos();
            }
        },
    },
    mounted() {
        this.getProductos();
    },
};
</script>

<style>
.tabla {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
    margin-left: 20px;
    margin-right: 20px;
    margin-bottom: 20px;
}

th,
td {
    border: 1px solid #f5f5f5;
    padding: 8px;
    text-align: left;
}


.btnAdd {
    background-color: #089779;
}

.carta {
    margin-left: 10px;
    margin-right: 10px;
    margin-top: 10px;
    margin-bottom: 0px;
}

.userList {
    background-color: #F5F5F5;
    color: black;
}

.custom-container {
    max-width: 1500px;
    margin: 0 auto;
}
</style>
