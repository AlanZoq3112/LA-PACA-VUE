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
                                            <h4>Mis ventas <i class="fa fa-shopping-basket" aria-hidden="true"></i>
                                            </h4>

                                        </div>

                                    </div>
                                    <div class="row">
                                        <div v-for="producto in paginatedProductos" :key="producto.id"
                                            class="col-lg-3 mb-4">
                                            <!-- Tu tarjeta de producto aquí -->
                                            <b-card class="card-custom mb-2" img-alt="Image" img-height="50px"
                                                max-width="500px" img-top>
                                                <b-card-text
                                                    style="min-height:400px; min-width: 100%; max-height: 50px; max-width: 100%;">
                                                    <p>Vendido a: </p>
                                                    <p>{{ producto.usuario.nombre }}</p>
                                                    <p>Dirección de envio:</p>
                                                    <p> {{ producto.direccion.calle }} ,
                                                        {{ producto.direccion.numero }} ,
                                                        {{ producto.direccion.colonia }} ,
                                                        {{ producto.direccion.municipio }} ,
                                                        {{ producto.direccion.estado }}</p>

                                                    <p>Productos vendidos:</p>
                                                    <ul>
                                                        <li v-for="producto in producto.items" :key="producto.id">{{ producto.producto.nombre }}</li>
                                                    </ul>
                                                    <p>El producto está: {{ producto.estado }}</p>
                                                </b-card-text>
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
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
    name: 'MisVentas',

    data() {
        return {
            productos: [],
            currentPage: 1,
            perPage: 8,
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
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener los productos, intente mas tarde', 'error');
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
