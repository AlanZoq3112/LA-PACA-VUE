<template>
    <div>
        <div class="text-center tabla">
            <b-table responsive :fields="fields" :items="usuarios" head-variant="light" bordered class="text-center shadow"
                id="table" ref="table" :sort-by.sync="sortBy" :sort-desc.sync="order" :per-page="perPage"
                :current-page="currentPage">
    
                <!-- ... Otras columnas existentes ... -->
    
                <!-- Nuevas columnas para mostrar datos del usuario -->
                <template #cell(id)="data">
                    {{ data.item.id }}
                </template>
    
                <template #cell(imagen_url)="data">
                    <img :src="data.item.imagen_url" alt="Imagen del usuario" style="max-width: 50px; max-height: 50px;">
                </template>
    
                <template #cell(email)="data">
                    {{ data.item.email }}
                </template>
    
                <template #cell(contrasena)="data">
                    {{ data.item.contrasena }}
                </template>
    
                <template #cell(telefono)="data">
                    {{ data.item.telefono }}
                </template>
    
                <!-- ... Resto de tus columnas ... -->
            </b-table>
        </div>
    </div>
</template>



<script>
import axios from 'axios';

export default {
    name: 'Usuarios',
    data() {
        return {
            usuarios: [],
        };
    },
    methods: {
        async getUsuarios() {
            try {
                const response = await axios.get('http://localhost:8090/api-carsi-shop/usuario/getAll');
                this.usuarios = response.data.data; // Asumiendo que la respuesta tiene una propiedad 'data'
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
    border: 1px solid #f5f5f5
    ;
    padding: 8px;
    text-align: left;
}
</style>