<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-editar-categorias">
            <header class="text-center border-bottom">
                <p style="font-family: cabin">Editar categoria</p>
            </header>
            <main>
                <form id="editarCategoria">
                    <b-row>
                        <b-col>
                            <label for="nombre">Nombre de la categoria: *</label>
                            <b-form-input v-model="categoriaEdit.nombre" type="text" class="form-control"
                                placeholder="Nombre" required />
                        </b-col>
                    </b-row>
                </form>
            </main>
            <footer class="text-center mt-5">
                <button class="btn m-1 cancel" @click="onClose" id="savemovie">
                    Cancelar
                </button>
                <button class="btn m-1 success" @click="update" id="saveteam" type="submit">
                    Editar
                </button>
            </footer>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';

export default {
    name: "ModalEditarCategorias",
    props: {
        categoria: Object
    },
    data() {
        return {
            categoriaEdit: {
                nombre: ""
            }
        }
    },
    computed: {
        categoriaNombre() {
            return this.categoria ? this.categoria.nombre : '';
        }
    },
    watch: {
        categoriaNombre(newValue) {
            this.categoriaEdit.nombre = newValue;
        }
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-editar-categorias");
            this.resetForm();
        },
        async update() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de editar la categoría?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }

                    const categoriaId = this.categoria ? this.categoria.id : null;
                    if (!categoriaId) {
                        Swal.fire('Error', 'No se ha proporcionado un ID de categoría válido', 'error');
                        return;
                    }

                    // Reemplaza la URL con la correcta para la edición de categorías
                    const response = await axios.put(`http://localhost:8091/api-carsi-shop/categoria/update/${categoriaId}`, this.categoriaEdit, {
                        headers: {
                            Authorization: `Bearer ${token}`
                        }
                    });

                    if (response.status === 200) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardada!",
                            text: "La categoría se editó correctamente",
                            icon: "success"
                        });

                        this.$emit('categoria-saved');
                        this.$bvModal.hide("modal-editar-categorias");
                    } else {
                        console.log("Error al editar la categoría. Estado del servidor:", response.status);
                    }
                }
            } catch (error) {
                console.error("Error al realizar la solicitud de edición:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar editar la categoría",
                    icon: "error"
                });
            }
        },
        resetForm() {
            this.categoriaEdit = {
                nombre: "",
            }
        }
    }
}
</script>

<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: #ffce50;
    color: black;
}
</style>