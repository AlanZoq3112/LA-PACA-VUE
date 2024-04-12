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
                                                </b-card-text>
                                                <template #footer>
                                                    <b-row>
                                                        <b-col :class="{
                                            'text-warning': producto.estado === 1,
                                            'text-success': producto.estado === 3,
                                            'text-danger': producto.estado === 2
                                        }">
                                                            <span v-if="producto.estado === 1">Pendiente</span>
                                                            <span v-else-if="producto.estado === 3">Aprobado</span>
                                                            <span v-else-if="producto.estado === 2">Rechazado</span>
                                                        </b-col>
                                                        <b-col>
                                                            <div class="d-flex justify-content-end">
                                                                <b-button v-b-tooltip.hover="'Editar Producto'"
                                                                    class="boton" to="kid-producto" variant="faded">
                                                                    <b-icon icon="pencil"></b-icon>
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
        <ModalGuardarProducto @producto-saved="getProductos" />
    </div>
</template>

<script>
import axios from 'axios';
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
                const response = await axios.get('http://localhost:8091/api-carsi-shop/producto/mis-productos', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.productos = response.data.data;
            } catch (error) {
                console.error("Error al obtener los datos de los productos", error);
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
