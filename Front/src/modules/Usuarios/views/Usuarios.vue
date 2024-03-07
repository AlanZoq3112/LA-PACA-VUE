<template>
    <div>
        <template>
            <div class="d-flex justify-content-between align-items-center mb-4 tabla">
                <div>
                    <b>
                        <h3>Usuarios <b-icon icon="people"></b-icon></h3>
                    </b>
                </div>
                <div class="">
                    <b-button v-b-modal.modal-save-user class="btnAdd">
                        <b-icon icon="person-plus"></b-icon> Registrar usuario
                    </b-button>
                </div>
            </div>
        </template>
        <div class="text-center tabla">
            <b-table responsive :fields="fields" :items="usuarios" head-variant="light" bordered class="text-center shadow" id="table"
                ref="table">

                <!-- Columna para mostrar el 'Nombre' -->
                <template #cell(nombre)="data">
                    {{ data.item.nombre }}
                </template>

                <!-- Columna para mostrar el 'Email' -->
                <template #cell(email)="data">
                    {{ data.item.email }}
                </template>

                <!-- Columna para mostrar el 'Correo' -->
                <template #cell(imagen_url)="data">
                    <img :src="data.item.imagen_url" alt="Imagen del usuario"
                        style="max-width: 50px; max-height: 50px;">
                </template>

                <!-- Columna para mostrar el 'Nombre del Rol' -->
                <template #cell(rol)="data">
                    {{ data.item.rol.nombre }}
                </template>

                
            </b-table>
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
                { key: 'imagen_url', label: 'Foto de perfil', sortable: true },
                { key: 'rol', label: 'Rol', sortable: true },
                {
					key: 'actions',
					label: 'Acciones',
					visible: true,
				},
            ],
        };
    },
    methods: {
        async getUsuarios() {
            try {
                const response = await axios.get('http://localhost:8090/api-carsi-shop/usuario/getAll');
                this.usuarios = response.data.data; // Asumiendo que la respuesta tiene una propiedad 'data'
                console.log(this.usuarios);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
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
</style>