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
                                            <h4>Categorias <b-icon icon="bookmarks"></b-icon></h4>
                                        </div>
                                        <div class="">
                                            <b-button v-b-modal.modal-guardar-categorias class="btnAdd">
                                                <b-icon icon="plus"></b-icon> Registrar Categoria
                                            </b-button>
                                        </div>
                                    </div>
                                    <b-table responsive :fields="fields" :items="categorias" head-variant="light"
                                        bordered class="text-center shadow" id="table" ref="table">
                                        <!-- Columna para mostrar el 'Nombre' -->
                                        <template #cell(nombre)="data">
                                            {{ data.item.nombre }}
                                        </template>
                                        <template #cell(subcategorias)="data">
                                            <div v-for="subcategoria in data.item.subcategorias" :key="subcategoria.id">
                                                {{ subcategoria.nombre }}
                                            </div>
                                        </template>
                                        <template #cell(actions)="data">
                                            <div class="text-center">
                                                <b-button size="sm" @click="edit(data.item)" variant="faded"
                                                    class="btnEdit">
                                                    <b-icon icon="pencil-square" style="color:blue"></b-icon>
                                                </b-button>
                                                <b-button size="sm" @click="deleteCategoria(data.item.id)"
                                                    variant="faded" class="btnDelete">
                                                    <b-icon icon="trash" style="color:red"></b-icon>
                                                </b-button>
                                            </div>
                                        </template>
                                    </b-table>
                                </div>

                                <br>
                                <div class="card-body p-md-5 mx-md-4">
                                    <div class="d-flex justify-content-between align-items-center mb-4 tabla">
                                        <div>
                                            <h4>Subcategorias <b-icon icon="bookmarks"></b-icon></h4>
                                        </div>
                                        <div class="">
                                            <b-button v-b-modal.modal-guardar-categorias class="btnAdd">
                                                <b-icon icon="plus"></b-icon> Registrar Subcategoria
                                            </b-button>
                                        </div>
                                    </div>
                                    <div class="text-center tabla">
                                        <b-table responsive :fields="fields2" :items="subcategorias"
                                            head-variant="light" bordered class="text-center shadow" id="table"
                                            ref="table">
                                            <!-- Columna para mostrar el 'Nombre' -->
                                            <template #cell(nombre)="data">
                                                {{ data.item.nombre }}
                                            </template>
                                            <template #cell(actions)="data">
                                                <div class="text-center">
                                                    <b-button size="sm" @click="edit(data.item)" variant="faded"
                                                        class="btnEdit">
                                                        <b-icon icon="pencil-square" style="color:blue"></b-icon>
                                                    </b-button>
                                                    <b-button size="sm" @click="deleteSubcategoria(data.item.id)"
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
        <ModalGuardarCategorias @categoria-saved="getCategorias" />
        <!-- Pasa la categoría seleccionada al modal de edición -->
        <ModalEditarCategoria ref="modal-editar-categorias" :categoria="selectCategoria"
            @categoria-saved="getCategorias" />
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import ModalGuardarCategorias from './ModalGuardarCategorias.vue';
import ModalEditarCategoria from './ModalEditarCategoria.vue';

export default {
    components: {
        ModalGuardarCategorias,
        ModalEditarCategoria
    },
    name: "Categorias",
    data() {
        return {
            selectCategoria: null,
            categorias: [],
            subcategorias: [],

            fields: [
                { key: 'nombre', label: 'Nombre', sortable: true },
                { key: 'subcategorias', label: 'Subcategorias', sortable: true },
                { key: 'actions', label: 'Acciones', visible: true },
            ],
            fields2: [
                { key: 'nombre', label: 'Nombre', sortable: true },
                { key: 'actions', label: 'Acciones', visible: true },
            ],
        }
    },
    methods: {
        async getCategorias() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8090/api-carsi-shop/admin/categoria/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.categorias = response.data.data;
            } catch (error) {
                console.error("Error al obtener las categorias", error);
            }
        },
        async deleteCategoria(categoriaId) {
            try {
                const result = await Swal.fire({
                    title: '¿Estás seguro de eliminar esta categoria?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    await axios.delete('http://localhost:8090/api-carsi-shop/admin/categoria/delete', {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        data: { id: categoriaId }
                    });
                    this.getCategorias();
                    Swal.fire('Eliminada', 'La categoria ha sido eliminada correctamente', 'success');
                }
            } catch (error) {
                console.error("Error al eliminar la categoria", error);
                Swal.fire('Error', 'Hubo un problema al intentar eliminar la categoria', 'error');
            }
        },
        async getSubcategoriaategorias() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8090/api-carsi-shop/admin/subcategoria/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.subcategorias = response.data.data;
                console.log("Sub xd: ", this.subcategorias);
            } catch (error) {
                console.error("Error al obtener las subcategorias", error);
            }
        },
        async deleteSubcategoria(subcategoriaId) {
            try {
                const result = await Swal.fire({
                    title: '¿Estás seguro de eliminar esta Subcategoria?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    await axios.delete('http://localhost:8090/api-carsi-shop/admin/subcategoria/delete', {
                        headers: {
                            Authorization: `Bearer ${token}`
                        },
                        data: { id: subcategoriaId }
                    });
                    this.getSubcategoriaategorias();
                    this.getCategorias();
                    Swal.fire('Eliminada', 'La subcategoria ha sido eliminada correctamente', 'success');
                }
            } catch (error) {
                console.error("Error al eliminar la categoria", error);
                Swal.fire('Error', 'Hubo un problema al intentar eliminar la categoria', 'error');
            }
        },
        edit(categoria) {
            this.selectCategoria = categoria;
            console.log(categoria);
            this.$refs['modal-editar-categorias'].show();
        },
    },
    mounted() {
        this.getCategorias();
        this.getSubcategoriaategorias();
    },
}
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