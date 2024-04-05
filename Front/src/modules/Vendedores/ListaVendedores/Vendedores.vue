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
                                            <h4>Solicitud de vendedores <b-icon icon="clock"></b-icon></h4>
                                        </div>
                                    </div>
                                    <div class="text-center tabla">
                                        <b-table responsive :fields="fields" :items="vendedores" head-variant="light"
                                            bordered class="text-center shadow" id="table" ref="table">
                                            <!-- Columna para mostrar el 'Nombre' -->
                                            <template #cell(usuario)="data">
                                                {{ data.item.usuario.nombre }}
                                            </template>
                                            <!-- Columna para mostrar el 'CURP' -->
                                            <template #cell(curp)="data">
                                                {{ data.item.curp }}
                                            </template>
                                            <!-- Columna para mostrar el 'Telefono' -->
                                            <template #cell(telefonoVendedor)="data">
                                                {{ data.item.telefonoVendedor }}
                                            </template>
                                            <!-- Columna para mostrar el 'RFC' -->
                                            <template #cell(rfc)="data">
                                                {{ data.item.rfc }}
                                            </template>
                                            <!-- Columna para mostrar el 'Estado' -->
                                            <template #cell(estado)="data">
                                                {{ data.item.estado ? 'Aprobado' : 'Rechazado' }}
                                            </template>
                                            <template #cell(actions)="data">
                                                <div class="text-center">
                                                    <!-- Mostrar solo el botón 'Aceptar' si el estado es 'Rechazado' -->
                                                    <b-button v-if="!data.item.estado" size="sm" @click="changeStatus(data.item.id, true)" variant="success" class="btnAccept">
                                                        <b-icon icon="check" class="mr-1"></b-icon> Aceptar
                                                    </b-button>
                                                    <!-- Mostrar solo el botón 'Rechazar' si el estado es 'Aprobado' -->
                                                    <b-button v-if="data.item.estado" size="sm" @click="changeStatus(data.item.id, false)" variant="danger" class="btnReject">
                                                        <b-icon icon="x" class="mr-1"></b-icon> Rechazar
                                                    </b-button>
                                                </div>
                                            </template>
                                        </b-table>
                                        <div v-if="vendedores.length === 0" class="text-center">No hay solicitudes de vendedores.</div>
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
    name: 'Vendedores',
    data() {
        return {
            vendedores: [],
            fields: [
                { key: 'curp', label: 'CURP', sortable: true },
                { key: 'telefonoVendedor', label: 'Telefono', sortable: true },
                { key: 'rfc', label: 'RFC', sortable: true },
                { key: 'estado', label: 'Status', sortable: true },
                { key: 'actions', label: 'Acciones', visible: true },
            ],
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
                        console.log(`Error al ${status ? 'aceptar' : 'rechazar'} la solicitud. Estado del servidor:`, response.status);
                    }
                }
            } catch (error) {
                console.error(`Error al ${status ? 'aceptar' : 'rechazar'} la solicitud:`, error);
                Swal.fire('Error', 'Hubo un problema al intentar realizar la acción', 'error');
            }
        },
        edit() {
            console.log("Editar");
        }
    },
    mounted() {
        this.getVendedores();
    },
};
</script>

<style scoped>
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