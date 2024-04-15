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
                                    <div class="text-center" v-if="productos.length === 0">
                                        <p>No tienes productos creados.</p>
                                    </div>
                                    <div class="row">
                                        <div v-for="producto in paginatedProductos" :key="producto.id"
                                            class="col-lg-3 mb-4">
                                            <!-- Tu tarjeta de producto aquí -->
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
                                                </b-card-text>
                                                <template #footer>
                                                    <b-row>
                                                        <b-col :class="{
                                            'text-warning': producto.estado === 0,
                                            'text-warning': producto.estado === 1,
                                            'text-success': producto.estado === 3,
                                            'text-danger': producto.estado === 2
                                        }">
                                                            <span v-if="producto.estado === 0">Inactivo</span>
                                                            <span v-if="producto.estado === 1">Pendiente</span>
                                                            <span v-else-if="producto.estado === 3">Aprobado</span>
                                                            <span v-else-if="producto.estado === 2">Rechazado</span>
                                                        </b-col>
                                                        <b-col>
                                                            <div class="d-flex justify-content-end">

                                                                <b-button v-b-tooltip.hover="'Deshabilitar Producto'"
                                                                    @click="deshabilitarProducto(producto.id)"
                                                                    class="boton" variant="faded">
                                                                    <b-icon icon="trash"></b-icon>
                                                                </b-button>
                                                            </div>
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
        };
    },
    computed: {
        paginatedProductos() {
        // Filtrar productos activos y luego inactivos
        const productosActivos = this.productos.filter(producto => producto.estado !== 0);
        const productosInactivos = this.productos.filter(producto => producto.estado === 0);

        // Concatenar productos activos e inactivos y luego aplicar la paginación
        const productosFiltrados = [...productosActivos, ...productosInactivos];

        const start = (this.currentPage - 1) * this.perPage;
        const end = start + this.perPage;

        return productosFiltrados.slice(start, end);
    },

    },
    methods: {
        async getProductos() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/producto/mis-productos', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.productos = response.data.data;
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al procesar la solicitud', 'error');
            }
        },
        async deshabilitarProducto(productoId) {
            try {
                const token = localStorage.getItem('token');
                const result = await Swal.fire({
                    title: '¿Estás seguro de deshabilitar este producto?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#009475',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Aceptar'
                });

                if (result.isConfirmed) {
                    const response = await axios.delete(`http://localhost:8091/api-carsi-shop/producto/delete`, {
                        headers: { Authorization: `Bearer ${token}` },
                        data: { id: productoId } // Envía el ID como datos en el cuerpo de la solicitud
                    });

                    if (response.status === 200) {
                        this.getProductos(); // Método para obtener los productos actualizados
                        Swal.fire('¡Éxito!', 'El producto se deshabilitó correctamente', 'success');
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar deshabilitar el producto', 'error');
                    }
                }
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
            }
        },

        activarProducto(idProducto) {
            Swal.fire({
                title: '¿Estás seguro?',
                text: '¿Quieres activar este producto?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#008c6f',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, activar',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Encuentra el índice del producto en la lista
                    const index = this.productos.findIndex(producto => producto.id === idProducto);
                    // Cambia el estado del producto a 1 (activo)
                    this.productos[index].estado = 1;
                    Swal.fire(
                        'Activado',
                        'El producto ha sido activado correctamente.',
                        'success'
                    );
                }
            });
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
