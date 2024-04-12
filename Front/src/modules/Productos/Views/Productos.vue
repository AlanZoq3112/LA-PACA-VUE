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
                                            <h4>Solicitud de productos <i class="fa fa-shopping-basket"
                                                    aria-hidden="true"></i>
                                            </h4>

                                        </div>
                                    </div>
                                    <div class="row">
                                        <div v-for="producto in productos" :key="producto.id" class="col-lg-3 mb-4">
                                            <b-card class="card-custom mb-2" img-alt="Image" img-height="450px"
                                                max-width="200px" img-top>
                                                <template #header>
                                                    <b-carousel :controls="false" indicators :interval="0">
                                                        <b-carousel-slide
                                                            style="min-height: 300px; min-width: 100%; max-height: 300px; max-width: 100%;"
                                                            v-for="(imagen, index) in producto.imagenes" :key="index"
                                                            :img-src="imagen.imageUrl"></b-carousel-slide>
                                                    </b-carousel>
                                                </template>
                                                <b-card-text
                                                    style="min-height:200px; min-width: 100%; max-height: 300px; max-width: 100%;">
                                                    <h5>{{ producto.nombre }}</h5>
                                                    <p>{{ producto.descripcion }}</p>
                                                    <p>{{ producto.subCategoria.nombre }} para {{
                                            producto.subCategoria.categoria.nombre }}</p>
                                                    <p>{{ producto.stock }} disponibles</p>
                                                    <b-col :class="{
                                            'text-warning': producto.estado === 1,
                                            'text-success': producto.estado === 3,
                                            'text-danger': producto.estado === 2
                                        }">
                                                        <span v-if="producto.estado === 1">Pendiente</span>
                                                        <span v-else-if="producto.estado === 3">Aprobado</span>
                                                        <span v-else-if="producto.estado === 2">Rechazado</span>
                                                    </b-col>
                                                </b-card-text>
                                                <template #footer>
                                                    <b-row>
                                                        <b-col>
                                                            <div class="d-flex justify-content-end">
                                                                <b-button v-b-tooltip.hover="'Aceptar producto'"
                                                                    class="boton" variant="faded"
                                                                    v-if="producto.estado === 1"
                                                                    @click="acceptProduct(producto.id)">
                                                                    <b-icon icon="check"></b-icon> Aceptar
                                                                </b-button>
                                                                <b-button v-b-tooltip.hover="'Rechazar producto'"
                                                                    class="boton" variant="faded"
                                                                    v-if="producto.estado === 1"
                                                                    @click="rejectProduct(producto.id)">
                                                                    <b-icon icon="times"></b-icon> Rechazar
                                                                </b-button>
                                                                <b-button v-b-tooltip.hover="'Cambiar a pendiente'"
                                                                    class="boton" variant="faded"
                                                                    v-else-if="producto.estado === 3 || producto.estado === 2"
                                                                    @click="changeToPending(producto.id)">
                                                                    <b-icon icon="undo"></b-icon> Cambiar a pendiente
                                                                </b-button>
                                                            </div>
                                                        </b-col>
                                                    </b-row>
                                                </template>
                                            </b-card>
                                        </div>
                                    </div>
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

    name: 'Productos',
    data() {
        return {
            productos: [],
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
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener los productos, intente mas tarde', 'error');
            }
        },
        async acceptProduct(productoId) {
            try {
                const token = localStorage.getItem('token');
                const result = await Swal.fire({
                    title: '¿Estás seguro de aceptar este producto?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#009475',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Aceptar'
                });

                if (result.isConfirmed) {
                    const response = await axios.put(`http://localhost:8091/api-carsi-shop/producto/aprobarSolicitudProducto`, {
                        id: productoId,
                        estado: 3 // Cambiar de "Pendiente" a "Aprobado"
                    }, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    if (response.status === 200) {
                        this.getProductos(); // Método para obtener los productos actualizados
                        Swal.fire('¡Éxito!', 'El producto ha sido aceptado correctamente', 'success');
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar aceptar el producto', 'error');
                    }
                }
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
            }
        },
        async rejectProduct(productoId) {
            try {
                const token = localStorage.getItem('token');
                const result = await Swal.fire({
                    title: '¿Estás seguro de rechazar este producto?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#d33',
                    cancelButtonColor: '#009475',
                    confirmButtonText: 'Rechazar'
                });

                if (result.isConfirmed) {
                    const response = await axios.put(`http://localhost:8091/api-carsi-shop/producto/aprobarSolicitudProducto`, {
                        id: productoId,
                        estado: 2 // Cambiar de "Pendiente" a "Rechazado"
                    }, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    if (response.status === 200) {
                        this.getProductos(); // Método para obtener los productos actualizados
                        Swal.fire('¡Éxito!', 'El producto ha sido rechazado correctamente', 'success');
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar rechazar el producto', 'error');
                    }
                }
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
            }
        },
        async changeToPending(productoId) {
            try {
                const token = localStorage.getItem('token');
                const result = await Swal.fire({
                    title: '¿Estás seguro de cambiar este producto a pendiente?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#009475',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Cambiar a pendiente'
                });

                if (result.isConfirmed) {
                    const response = await axios.put(`http://localhost:8091/api-carsi-shop/producto/aprobarSolicitudProducto`, {
                        id: productoId,
                        estado: 1 // Cambiar de "Aprobado" o "Rechazado" a "Pendiente"
                    }, {
                        headers: { Authorization: `Bearer ${token}` }
                    });

                    if (response.status === 200) {
                        this.getProductos(); // Método para obtener los productos actualizados
                        Swal.fire('¡Éxito!', 'El producto ha sido cambiado a pendiente correctamente', 'success');
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar cambiar el estado del producto', 'error');
                    }
                }
            } catch (error) {
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