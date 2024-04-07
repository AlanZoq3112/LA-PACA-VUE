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
                                            <h4>Mis productos <i class="fa fa-shopping-basket"
                                                    aria-hidden="true"></i></h4>

                                        </div>
                                        <div class="">
                                            <b-button v-b-modal.modal-guardar-productos class="btnAdd">
                                                <i class="fa fa-shopping-basket" aria-hidden="true"></i> Registrar
                                                producto
                                            </b-button>
                                        </div>
                                    </div>
                                    <div class="text-center tabla">

                                        
                                        <b-table responsive :fields="fields" :items="productos" head-variant="light"
                                            bordered class="text-center shadow" id="table" ref="table">

                                            <!-- Columna para mostrar el 'Nombre' -->
                                            <template #cell(nombre)="data">
                                                {{ data.item.nombre }}
                                            </template>
                                            <!-- Columna para mostrar  'Description' -->
                                            <template #cell(descripcion)="data">
                                                {{ data.item.descripcion }}
                                            </template>
                                            <!-- Columna para mostrar el 'Nombre' -->
                                            <template #cell(precio)="data">
                                                {{ data.item.precio }}
                                            </template>
                                            <!-- Columna para mostrar el 'Nombre' -->
                                            <template #cell(stock)="data">
                                                {{ data.item.stock }}
                                            </template>


                                            <template #cell(estado)="data">
                                                {{ data.item.usuario.estado ? 'Aprobado' : 'Rechazado' }}
                                            </template>

                                            <template #cell(usuario)="data">
                                                {{ data.item.usuario.nombre }}
                                            </template>



                                            <template #cell(actions)="data">
                                                <div class="text-center">
                                                    <!-- Mostrar solo el botón 'Aceptar' si el estado es 'Rechazado' -->
                                                    <b-button v-if="!data.item.estado" size="sm"
                                                        @click="changeProductStatus(data.item.id, true)" variant="success"
                                                        class="btnAccept">
                                                        <b-icon icon="check" class="mr-1"></b-icon> Aceptar
                                                    </b-button>
                                                    <!-- Mostrar solo el botón 'Rechazar' si el estado es 'Aprobado' -->
                                                    <b-button v-if="data.item.estado" size="sm"
                                                        @click="changeProductStatus(data.item.id, false)" variant="danger"
                                                        class="btnReject">
                                                        <b-icon icon="x" class="mr-1"></b-icon> Rechazar
                                                    </b-button>
                                                </div>
                                            </template>


                                        </b-table>
                                        
                                        <div v-if="productos.length === 0" class="text-center">No tienes productos registrados.</div>
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
                const response = await axios.get('http://localhost:8091/api-carsi-shop/producto/mis-productos', {
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