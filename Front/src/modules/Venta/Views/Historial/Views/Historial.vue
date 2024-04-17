<template>
    <div>
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
                                            <b-card class="card-custom mb-2" img-alt="Image" img-height="450px"
                                                max-width="250px" img-top>
                                                <template #header>

                                                </template>
                                                <b-card-text
                                                    style="min-height:250px; min-width: 100%; max-height: 350px; max-width: 100%;">
                                                    <p>Enviado: {{ producto.direccion.calle }} {{
                                            producto.direccion.numero }} {{ producto.direccion.colonia }} ,
                                                        {{
                                            producto.direccion.municipio }} ,
                                                        {{ producto.direccion.estado }}</p>
                                                    <p>El producto esta: {{ producto.estado }}</p>
                                                </b-card-text>
                                                <template #footer>
                                                    <b-row>
                                                        <b-col>
                                                            <b-button variant="faded" @click="openModal(producto)">Ver
                                                                detalles</b-button> </b-col>
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
        <ModalInfo ref="ModalInfo" :selectedProduct="selectedProduct" />
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
            fields: [
                { key: 'nombre', label: 'Nombre', sortable: true },
                { key: 'descripcion', label: 'Descripción', sortable: true },
                { key: 'precio', label: 'Precio', sortable: true },
                { key: 'stock', label: 'Stock', sortable: true },
                { key: 'estado', label: 'Status', sortable: true },
                { key: 'usuario', label: 'Nombre vendedor', sortable: true },
                {
                    key: 'actions',
                    label: 'Acciones',
                    visible: true,
                },
            ],
            selectedProduct: null,
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
                this.productos = response.data.data
                console.log(this.productos);;
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener los productos, intente mas tarde', 'error');
            }
        },
        async openModal(producto) {
            this.selectedProduct = producto; // Almacena el producto seleccionado
            console.log(this.selectedProduct);
            this.$bvModal.show('ModalInfo');        },
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
