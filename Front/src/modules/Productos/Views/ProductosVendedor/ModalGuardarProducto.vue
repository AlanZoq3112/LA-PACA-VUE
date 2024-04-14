<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <b-modal hide-footer hide-header centered id="modal-guardar-productos" style="max-width: 80vw;"
            @show="getSubcategorias">
            <header class="text-center border-bottom">
                <p>Registrar producto</p>
            </header>
            <main>
                <b-form @submit.prevent="save">
                    <b-row>
                        <b-col>
                            <b-form-group label="Nombre del producto" label-for="nombre">
                                <InputTextMax @check="validNombre" @name="dataChildName" :numMax="maximoName"
                                    required />
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Subcategoria" label-for="nombre">
                                <multi-select id="subCategoria" :class="{
                                    'is-invalid': v$.producto.subCategoria.$error,
                                    'is-valid': !v$.producto.subCategoria.$invalid,
                                }" v-model="v$.producto.subCategoria.$model" placeholder="Selecciona alguna"
                                    label="nombre" :options="subcategorias" track-by="nombre" :multiple="false"
                                    selectLabel="Aceptar" deselectLabel="Eliminar" selectedLabel="Seleccionado"
                                    @close="v$.producto.subCategoria.$touch()">
                                </multi-select>
                                <b-form-invalid-feedback v-for="error in v$.producto.subCategoria.$errors"
                                    :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                            <b-form-group label="Subcategoria" label-for="subCategoria">
                                <b-form-select v-model="producto.subCategoria" id="subCategoria"
                                    :options="subcategorias.map(subcategoria => ({ value: subcategoria.id, text: `${subcategoria.nombre} de ${subcategoria.categoria.nombre}` }))"
                                    required></b-form-select>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-form-group label="Descripción" label-for="descripcion">
                        <InputTextMax @check="validDescription" @name="dataChildDescription" :numMax="maximoDescription"
                            required />
                    </b-form-group>
                    <b-row>
                        <b-col>
                            <b-form-group label="Precio" label-for="precio">
                                <b-form-input id="precio" type="number" v-model="v$.producto.precio.$model"
                                    @blur="v$.producto.precio.$touch()"
                                    :state="v$.producto.precio.$dirty ? !v$.producto.precio.$error : null" trim
                                    required />
                                <b-form-invalid-feedback v-for="error in v$.producto.precio.$errors" :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Stock" label-for="stock">
                                <b-form-input id="stock" type="number" v-model="v$.producto.stock.$model"
                                    @blur="v$.producto.stock.$touch()"
                                    :state="v$.producto.stock.$dirty ? !v$.producto.stock.$error : null" trim
                                    required />
                                <b-form-invalid-feedback v-for="error in v$.producto.stock.$errors" :key="error.$uid">
                                    {{ error.$message }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-form-group>
                        <InputFiles @img="dataChildFile" @check="validFile" />
                    </b-form-group>
                    <b-button type="submit" variant="success">Registrar</b-button>
                </b-form>
            </main>
        </b-modal>
    </div>
</template>
<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import { useVuelidate } from "@vuelidate/core";
import {
    required,
    alphaNum,
    helpers,
    maxLength,
    minLength,
    integer,
    decimal,
    minValue,
    maxValue
} from "@vuelidate/validators";
const base64Encode = data =>
    new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(data);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
export default {
    name: "ModalGuardarProductos",
    components: {
        InputTextMax: () => import('../../../../components/input_validations/inputTextMax.vue'),
        InputFiles: () => import('../../../../components/input_validations/InputFiles.vue'),
    },
    setup() {
        return {
            v$: useVuelidate(),
        };
    },
    data() {
        return {
            producto: {
                nombre: "",
                descripcion: "",
                precio: 0,
                stock: 0,
                subCategoria: 0,
                imagenes: []
            },
            maximoName: 40,
            maximoDescription: 100,
            valueName: false,
            valueDescription: false,
            subcategorias: [],
            loading: false,
            valueFile: false,
            id: "",
        }
    },

    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-productos");
            this.resetForm();
        },
        dataChildName(data) {
            this.producto.nombre = data;
        },
        validNombre(data) {
            this.valueName = data;
        },
        validFile(data) {
            this.valueFile = data;
        },
        dataChildFile(data) {
            for (let i = 0; i < data.length; i++) {
                if (data) {
                    base64Encode(data[0]).then((file) => {
                        this.producto.imagenes.push(file);
                        console.log(this.producto.imagenes)
                    }).catch((error) => {
                        this.producto.imagenes = null;
                    });
                }
            }
        },
        dataChildDescription(data) {
            this.producto.descripcion = data;
        },
        validDescription(data) {
            this.valueDescription = data;
        },
        handleFileUpload(event) {
            const files = event.target.files;
            // Verifica si hay al menos 2 y como máximo 5 imágenes seleccionadas
            if (files.length >= 2 && files.length <= 5) {
                for (let i = 0; i < files.length; i++) {
                    const file = files[i];
                    this.producto.imagenes.push(file);
                }
            } else {
                // Muestra un mensaje de error si la cantidad de imágenes no es válida
                Swal.fire({
                    title: "Error",
                    text: "Debes seleccionar entre 2 y 5 imágenes.",
                    icon: "error"
                });
                // Reinicia la selección de archivos
                event.target.value = "";
            }
            console.log(this.producto.imagenes)
        },
        getImageURL(file) {
            return URL.createObjectURL(file);
        },
        async save() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de registrar el producto?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });
                const isValid = this.v$.producto.$invalid;
                if (result.isConfirmed && isValid) {
                    this.loading = true;
                    this.id = this.producto.subCategoria.id;
                    this.producto.subCategoria = this.id;
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    console.log(this.producto);
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/producto/insert", this.producto, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                            'Content-Type': 'multipart/form-data'
                        },

                    });
                    if (response.status === 201) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El producto se registró correctamente",
                            icon: "success"
                        });
                        // Emitir evento para actualizar la tabla de subcategorías
                        this.$emit('producto-saved');
                        // Cerrar modal
                        this.$bvModal.hide("modal-guardar-productos");
                    } else {
                        console.log("Error al guardar el producto. Estado del servidor:", response.status);
                    }
                }else{
                    Swal.fire('Error', 'Revise todo los campos', 'error');
                }
            } catch (error) {
                console.error("Error al realizar la solicitud de guardado:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el producto",
                    icon: "error"
                });
            } finally {
                this.loading = false;
            }
        },
        resetForm() {
            this.producto = {
                nombre: "",
                descripcion: "",
                precio: 0,
                stock: 0,
                subCategoria: 0,
                imagenes: [],
            }
        },
        async getSubcategorias() {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get('http://localhost:8091/api-carsi-shop/subcategoria/getAll', {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                });
                this.subcategorias = response.data.data;
            } catch (error) {
                Swal.fire('Error', 'Hubo un problema al intentar obtener las subcategorias, intente mas tarde', 'error');
            }
        },

    },
    mounted() {
        this.getSubcategorias();
    },
    validations() {
        return {
            producto: {
                subCategoria: {
                    required: helpers.withMessage("Campo obligatorio", required),
                },
            },
        };
    },
    validations: {
        producto: {
            stock: {
                required: helpers.withMessage("El campo es requerido.", required),
                integer: helpers.withMessage("El campo debe ser un número entero.", integer),
                minValue: helpers.withMessage("El campo no puede ser negativo.", minValue(0)),
            },
            precio: {
                required: helpers.withMessage("El campo es requerido.", required),
                decimal: helpers.withMessage("El campo debe ser un número decimal.", decimal),
                minValue: helpers.withMessage("El campo debe ser mayor a 0.", (value) => value > 0),
                numberOfDecimals: helpers.withMessage("El campo debe tener solo dos decimales.", (value) => {
                    const decimals = value.toString().split(".")[1]
                    return decimals ? decimals.length <= 2 : true
                }),
            },
            subCategoria: {
                required: helpers.withMessage("Campo obligatorio", required),
            },
        },
    },
}
</script>

<style>
.textarea-limit {
    resize: none;
    /* Evita que el área de texto sea redimensionable */
    max-height: calc(1.5em * 3);
    /* Establece la altura máxima en función del número máximo de filas */
}

.success {
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
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

.preview-container {
    display: flex;
    flex-wrap: wrap;
}

.image-preview {
    margin-right: 10px;
    margin-bottom: 10px;
}

.preview-image {
    max-width: 100px;
    max-height: 100px;
}
</style>