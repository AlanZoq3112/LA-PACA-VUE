<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner">

                </div>
            </div>
        </div>
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
                                                <h4>Usuarios <b-icon icon="person"></b-icon></h4>
                                            </div>
                                            <div class="">

                                            </div>
                                        </div>
                                        <div class="text-center tabla">
                                            <b-table responsive :fields="fields" :items="usuarios" head-variant="light"
                                                bordered class="text-center shadow" id="table" ref="table">

                                                <!-- Columna para mostrar el 'Nombre' -->
                                                <template #cell(nombre)="data">
                                                    {{ data.item.nombre }}
                                                </template>

                                                <!-- Columna para mostrar el 'Email' -->
                                                <template #cell(accountNonExpired)="data">
                                                    {{ data.item.accountNonExpired ? 'Activo' : 'Expirado' }}
                                                </template>

                                                <!-- Columna para mostrar el estado de 'accountNonLocked' -->
                                                <template #cell(accountNonLocked)="data">
                                                    {{ data.item.accountNonLocked ? 'Desbloqueado' : 'Bloqueado' }}
                                                </template>

                                                <!-- Columna para mostrar las autoridades -->
                                                <template #cell(authorities)="data">
                                                    {{ data.item.authorities.join(', ') }}
                                                </template>

                                                <!-- Columna para mostrar el estado de 'credentialsNonExpired' -->
                                                <template #cell(credentialsNonExpired)="data">
                                                    {{ data.item.credentialsNonExpired ? 'Válido' : 'Expirado' }}
                                                </template>

                                                <template #cell(imagenPerfil)="data">
                                                    <img :src="data.item.image" alt="Imagen de perfil"
                                                        style="max-width: 100px; max-height: 100px;">
                                                </template>

                                                <template #cell(actions)="data">
                                                    <div class="text-center">

                                                        <b-button size="sm" @click="deleteUser(data.item.id)"
                                                            variant="faded" class="btnDelete">
                                                            <b-icon icon="trash" style="color:red"></b-icon>
                                                        </b-button>
                                                    </div>
                                                </template>


                                            </b-table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <ModalGuardarUsuario @user-updated="getUsuarios" />
    </div>
</template>



<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import ModalGuardarUsuario from './ModalGuardarUsuario.vue';
export default {
    components: {
        ModalGuardarUsuario,
    },
    name: 'Usuarios',
    data() {
        return {
            usuarios: [],
            fields: [
                { key: 'nombre', label: 'Nombre', sortable: true },
                { key: 'email', label: 'Email', sortable: true },
                { key: 'telefono', label: 'Telefono', sortable: true },
                { key: 'genero', label: 'Genero', sortable: true },
                { key: 'role', label: 'Rol', sortable: true },
                { key: 'imagenPerfil', label: 'Imagen de Perfil', sortable: false },
                {
                    key: 'actions',
                    label: 'Acciones',
                    visible: true,
                },
            ],
            loading: false
        };
    },
    methods: {
        async getUsuarios() {
            try {
                const token = localStorage.getItem('token'); // Obtener el token JWT del almacenamiento local
                const response = await axios.get('http://localhost:8091/api-carsi-shop/usuario/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                    }
                });
                this.usuarios = response.data.data.filter(usuario => {
                    return usuario.role === 'VENDEDOR' || usuario.role === 'COMPRADOR';
                });

                console.log(this.usuarios);
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener los usuarios, intente mas tarde', 'error');
            }
        },
        async deleteUser(userId) {
            try {
                const result = await Swal.fire({
                    title: '¿Estás seguro de eliminar este usuario?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    this.loading = true;
                    const token = localStorage.getItem('token');
                    await axios.delete('http://localhost:8091/api-carsi-shop/usuario/delete',
                        {
                            headers: {
                                Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                            },
                            data: {
                                id: userId // Enviar el ID del usuario que se va a eliminar en el cuerpo de la solicitud
                            }
                        });
                    this.getUsuarios();
                    Swal.fire('Eliminado', 'El usuario ha sido eliminado correctamente', 'success');
                }
            } catch (error) {
                let errorMessage = "Hubo un problema al crear la cuenta";
                if (error.response?.data && error.response.data.length > 0) {
                    errorMessage = error.response.data[0];
                }
                Swal.fire('Error', errorMessage, 'error');
            } finally {
                this.loading = false;
            }
        },
    },
    mounted() {
        this.getUsuarios();
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

.overlay {
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
}

.loader {
    border: 8px solid #f3f3f3;
    border-radius: 50%;
    border-top: 8px solid #3498db;
    width: 50px;
    height: 50px;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}
</style>