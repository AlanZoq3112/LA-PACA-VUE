<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-guardar-subcategorias" @show="getCategorias">
                <header class="text-center border-bottom">
                    <p>Registrar subcategoria</p>
                </header>
                <main>
                    <form id="registrarSubcategoria" @submit.prevent="registrarSubcategoria">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre de la subcategoria: *</label>
                                <b-form-input v-model="subcategoria.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required />
                                <b-form-invalid-feedback v-if="!nombreValido">Nombre inválido. Debe contener solo
                                    letras, sin espacios al inicio o al final, y tener máximo 20
                                    caracteres.</b-form-invalid-feedback>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="categoria">Categoría:</label>
                                <b-form-select v-model="subcategoria.categoria" :options="categorias" required />
                            </b-col>
                        </b-row>
                    </form>
                </main>
                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" id="saveSubcategoria" @click="onClose">Cancelar</button>
                    <button class="btn m-1 success" id="saveteam" type="submit" @click="save"
                        :disabled="!nombreValido">Registrar</button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "ModalGuardarSubcategorias",
    data() {
        return {
            subcategoria: {
                nombre: "",
                categoria: null,
            },
            categorias: [],
            nombreValido: true
        }
    },

    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-subcategorias");
            this.resetForm();
        },
        async getCategorias() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/categoria/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.categorias = response.data.data.map(categoria => {
                    return { text: categoria.nombre, value: categoria.id };
                });
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar OBTENER las categorias, intente mas tarde', 'error');
            }
        },
        async save() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de registrar la subcategoría?",
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
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/subcategoria/insert", {
                        nombre: this.subcategoria.nombre,
                        categoria: {
                            id: this.subcategoria.categoria
                        }
                    }, {
                        headers: {
                            Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                        }
                    });

                    if (response.status === 201) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardada!",
                            text: "La subcategoría se registró correctamente",
                            icon: "success"
                        });

                        // Emitir evento para actualizar la tabla de subcategorías
                        this.$emit('subcategoria-saved');

                        // Cerrar modal
                        this.$bvModal.hide("modal-guardar-subcategorias");
                    } else {
                        Swal.fire('Error', 'Hubo un problema al intentar GUARDAR la subcategoria, intente mas tarde', 'error');
                    }
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar la subcategoría",
                    icon: "error"
                });
            }
        },
        resetForm() {
            this.subcategoria = {
                nombre: "",
                categoria: null,
            }
        },
        validateNombre() {
            // Validar el nombre aquí según los criterios requeridos
            const nombreRegExp = /^[a-zA-Z]+$/; // Expresión regular para permitir solo letras
            const trimmedNombre = this.subcategoria.nombre.trim(); // Eliminar espacios en blanco al inicio y al final
            const isValidLength = trimmedNombre.length <= 20; // Verificar longitud máxima
            const isValidFormat = nombreRegExp.test(trimmedNombre); // Verificar formato

            this.nombreValido = isValidLength && isValidFormat;
        }
    },
    watch: {
        'subcategoria.nombre': function (newValue) {
            this.validateNombre();
        }
    },
    mounted() {
        this.getCategorias();
    }
}
</script>

<style>
.success {
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
}
</style>