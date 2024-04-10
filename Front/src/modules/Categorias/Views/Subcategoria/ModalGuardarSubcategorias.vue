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
                                <InputTextMax @check="validNombre" @name="dataChildSub" :numMax="maximo" />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="categoria">Categoría:</label>                            
                                <!--
                                    <b-form-select v-model="subcategoria.categoria" :options="categorias" required />
                                -->
                                <multi-select id="categoria" :class="{
                                    'is-invalid': v$.subcategoria.categoria.$error,
                                    'is-valid': !v$.subcategoria.categoria.$invalid,
                                }" v-model="v$.subcategoria.categoria.$model" placeholder="Selecciona una categoria"
                                    label="text" :options="categorias" track-by="value" :multiple="false"
                                    selectLabel="Presiona para seleccionar" deselectLabel="Presiona para eliminar"
                                    selectedLabel="Seleccionado" @close="v$.subcategoria.categoria.$touch()">
                                </multi-select>
                                <b-form-invalid-feedback v-for="error in v$.subcategoria.categoria.$errors"
                                    :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-col>
                        </b-row>
                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" id="saveSubcategoria" @click="onClose">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" id="saveteam" type="submit" @click="save">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength } from "@vuelidate/validators";
export default {
    name: "ModalGuardarSubcategorias",
    components: {
        InputTextMax: () => import('../../../../components/input_validations/inputTextMax.vue'),
    },
    setup() {
        return {
            v$: useVuelidate(),
        };
    },
    data() {
        return {
            subcategoria: {
                nombre: "",
                categoria: null,
            },
            categoria: null,
            categorias: [],
            maximo: 25,
            valueSubcategoria: false,            
        }
    },

    methods: {
        dataChildSub(data) {
            this.subcategoria.nombre = data;
        },
        validNombre(data) {
            this.valueSubcategoria = data;
        },
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
                Swal.fire('Error', 'Hubo un problema al intentar OBTENER lsa categorias, intente mas tarde', 'error');
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
                const isValid = this.v$.subcategoria.$invalid;
                if (result.isConfirmed && this.valueSubcategoria && !isValid) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/subcategoria/insert", {
                        nombre: this.subcategoria.nombre,
                        categoria: {
                            id: this.subcategoria.categoria.value
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
                }else{
                    Swal.fire('Error', 'Revise los campos', 'error');
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
        }
    },
    validations() {
        return {
            subcategoria: {
                categoria: {
                    required: helpers.withMessage("Campo obligatorio", required)
                }
            }
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