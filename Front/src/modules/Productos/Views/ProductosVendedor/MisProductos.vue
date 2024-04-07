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
                                            <h4>Mis productos <i class="fa fa-shopping-basket" aria-hidden="true"></i>
                                            </h4>

                                        </div>
                                        <div class="">
                                            <b-button v-b-modal.modal-guardar-productos class="btnAdd">
                                                <i class="fa fa-shopping-basket" aria-hidden="true"></i> Registrar
                                                producto
                                            </b-button>
                                        </div>
                                    </div>

                                    <div>
                                        <TransitionGroup name="roll" tag="div" class="d-flex d-fixed">
                                            <div v-for="producto in productos" :key="producto.id" class="col-lg-3 mb-4">
                                                <b-card class="card-custom mb-2" img-top img-height="250px"
                                                    max-width="150px">
                                                    <b-card-text>
                                                        <h5>{{ producto.nombre }}</h5>
                                                        <p>Description: {{ producto.descripcion }}</p>
                                                        <p>Categoria: {{ producto.categoria.nombre }}</p>
                                                        <p>Stock: {{ producto.stock }}</p>
                                                    </b-card-text>
                                                    <template #footer>
                                                        <b-row>
                                                            <b-col>Precio: ${{ producto.precio }}</b-col>
                                                            <b-col>
                                                                <div class="d-flex justify-content-end">
                                                                    <span v-if="producto.estado"
                                                                        class="text-success">Producto Aceptado</span>
                                                                    <span v-else class="text-danger">Rechazado</span>
                                                                </div>
                                                            </b-col>
                                                        </b-row>
                                                    </template>
                                                </b-card>
                                            </div>
                                        </TransitionGroup>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <ModalGuardarProducto @producto-saved="getProductos" />
    </div>
</template>



<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import ModalGuardarProducto from './ModalGuardarProducto.vue';

export default {
    components: {
        ModalGuardarProducto
    },
    name: 'MisProductos',
    data() {
        return {
            productos: [],
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
        };
    },
    methods: {
        async getProductos() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/producto/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.productos = response.data.data;
                console.log("Prodcutos: ", this.productos);
            } catch (error) {
                console.error("Error al obtener los datos de los productos", error);
            }
        },
        async changeProductStatus(productoId, status) {
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
                    const response = await axios.put(`http://localhost:8091/api-carsi-shop/producto/update/${productoId}`, {
                        estado: status
                    }, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    if (response.status === 200) {
                        this.getProductos(); // Método para obtener los productos actualizados
                        Swal.fire('¡Éxito!', `La solicitud ha sido ${status ? 'aceptada' : 'rechazada'} correctamente`, 'success');
                    } else {
                        console.log(`Error al ${status ? 'aceptar' : 'rechazar'} la solicitud. Estado del servidor:`, response.status);
                        Swal.fire('Error', `Hubo un problema al intentar ${status ? 'aceptar' : 'rechazar'} la solicitud`, 'error');
                    }
                }
            } catch (error) {
                console.error(`Error al ${status ? 'aceptar' : 'rechazar'} la solicitud:`, error);
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
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