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
                <b-form @submit.prevent="guardarProducto">
                    <b-row>
                        <b-col>
                            <b-form-group label="Nombre del producto" label-for="nombre">
                                <b-form-input v-model="producto.nombre" type="text" id="nombre" required
                                    :state="validarNombre" />

                                <b-form-invalid-feedback :state="validarNombre">
                                    {{ getNombreErrorMessage() }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Subcategoria" label-for="subCategoria">
                                <b-form-select v-model="producto.subCategoria" id="subCategoria"
                                    :options="subcategorias.map(subcategoria => ({ value: subcategoria.id, text: `${subcategoria.nombre} de ${subcategoria.categoria.nombre}` }))"
                                    :state="validarSubCategoria" required>
                                </b-form-select>
                                <b-form-invalid-feedback :state="validarSubCategoria">
                                    Debes seleccionar una subcategoría.
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-form-group label="Descripción" label-for="descripcion">
                        <b-form-textarea v-model="producto.descripcion" id="descripcion" rows="4"
                            :state="validarDescripcion" required></b-form-textarea>
                        <!-- Contador de caracteres -->
                        <small class="text-muted">{{ contadorDescripcion }} / 100</small>
                        <!-- Feedback de validación -->
                        <b-form-invalid-feedback :state="validarDescripcion">
                            {{ getDescripcionErrorMessage() }}
                        </b-form-invalid-feedback>
                    </b-form-group>
                    <b-row>
                        <b-col>
                            <b-form-group label="Precio" label-for="precio">
                                <b-form-input v-model="producto.precio" type="number" id="precio" :state="validarPrecio"
                                    required></b-form-input>
                                <!-- Feedback de validación -->
                                <b-form-invalid-feedback :state="validarPrecio">
                                    {{ getPrecioErrorMessage() }}
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Stock" label-for="stock">
                                <b-form-select v-model="producto.stock" id="stock" :options="generarOpcionesStock()"
                                    required :state="validarStock" />
                                <b-form-invalid-feedback :state="validarStock">
                                    Selecciona stock.
                                </b-form-invalid-feedback>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-row>
                        <b-col>
                            <b-form-group label="Imágenes del producto" label-for="imagenes">
                                <input type="file" id="imagenes" multiple @change="handleFileUpload($event)"
                                    class="form-control" required>
                                <!-- Vista previa de las imágenes seleccionadas -->
                                <div v-if="producto.imagenes.length > 0" class="preview-container mt-3">
                                    <div v-for="(imagen, index) in producto.imagenes.slice(0, 5)" :key="index"
                                        class="image-preview">
                                        <img :src="getImageURL(imagen)" alt="Imagen previa" class="preview-image">

                                    </div>
                                </div>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <b-button type="button" class="cancel" @click="onClose">Cancelar</b-button>
                        </b-col>
                        <b-col>
                            <b-button type="submit" class="success" variant="success" :disabled="!formValid">Registrar</b-button>
                        </b-col>
                        <b-col></b-col>
                    </b-row>

                </b-form>
            </main>
        </b-modal>
    </div>
</template>
<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "ModalGuardarProductos",
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
            subcategorias: [],
            loading: false
        }
    },

    methods: {

        onClose() {
            this.$bvModal.hide("modal-guardar-productos");
            this.resetForm();
        },
        handleFileUpload(event) {
            const files = event.target.files;
            const allowedTypes = ['image/jpeg', 'image/png', 'image/gif']; // Tipos de archivo permitidos

            // Verifica si hay al menos 2 y como máximo 5 imágenes seleccionadas
            if (files.length >= 2 && files.length <= 5) {
                // Verifica si los archivos son del tipo permitido
                for (let i = 0; i < files.length; i++) {
                    const file = files[i];
                    if (!allowedTypes.includes(file.type)) {
                        Swal.fire({
                            title: "Error",
                            text: "Solo se permiten imágenes en formato JPEG, PNG o GIF.",
                            icon: "error"
                        });
                        // Reinicia la selección de archivos
                        event.target.value = "";
                        return;
                    }
                }

                // Limpia el arreglo de imágenes antes de agregar nuevas imágenes
                this.producto.imagenes = [];

                // Agrega las nuevas imágenes al arreglo
                for (let i = 0; i < files.length; i++) {
                    const file = files[i];
                    this.producto.imagenes.push(file);
                }

                // Verifica si la cantidad de imágenes en la vista previa excede 5
                if (this.producto.imagenes.length > 5) {
                    Swal.fire({
                        title: "Error",
                        text: "Solo se permiten hasta 5 imágenes.",
                        icon: "error"
                    });
                    // Limpia el arreglo de imágenes
                    this.producto.imagenes = [];
                    // Reinicia la selección de archivos
                    event.target.value = "";
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
        },

        getImageURL(file) {
            return URL.createObjectURL(file);
        },
        async guardarProducto() {
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

                if (result.isConfirmed) {
                    this.loading = true;
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
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
                        Swal.fire({
                            title: "Error",
                            text: "Hubo un problema al intentar guardar el producto",
                            icon: "error"
                        });
                    }
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
        getNombreErrorMessage() {
            if (!this.validarNombre) {
                if (!this.producto.nombre.trim()) {
                    return "El nombre es requerido.";
                } else if (this.producto.nombre.length > 40) {
                    return "El nombre debe tener máximo 40 caracteres.";
                } else {
                    return "El nombre solo puede contener letras, números y espacios entre palabras.";
                }
            }
            return "";
        },

        getDescripcionErrorMessage() {
            if (!this.validarDescripcion) {
                if (!this.producto.descripcion.trim()) {
                    return "La descripción es requerida.";
                } else if (this.producto.descripcion.length > 100) {
                    return "La descripción debe tener máximo 100 caracteres.";
                } else {
                    return "La descripción solo puede contener letras, números y espacios.";
                }
            }
            return "";
        },

        getPrecioErrorMessage() {
            if (!this.validarPrecio) {
                if (isNaN(this.producto.precio)) {
                    return "El precio debe ser un número.";
                } else if (this.producto.precio <= 0) {
                    return "El precio debe ser mayor a 0.";
                } else if (this.producto.precio >= 3000) {
                    return "El precio debe ser menor a 3000.";
                } else {
                    return "El precio debe ser un número entero.";
                }
            }
            return "";
        },

        generarOpcionesStock() {
            const opciones = [];
            for (let i = 5; i <= 40; i += 5) {
                opciones.push({ value: i, text: `${i}` });
            }
            return opciones;
        },
    },

    mounted() {
        this.getSubcategorias();

    },

    computed: {
        validarNombre() {
            const regex = /^[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$/; // Expresión regular que permite letras, números y espacios entre palabras
            const nombre = this.producto.nombre.trim(); // Eliminar espacios en blanco al inicio y al final del nombre
            return nombre.length > 0 && nombre.length <= 40 && regex.test(nombre);
        },
        validarSubCategoria() {
            return !!this.producto.subCategoria;
        },

        contadorDescripcion() {
            return this.producto.descripcion.length;
        },

        validarDescripcion() {
            // Verificar que la descripción no esté vacía y no exceda los 100 caracteres
            return (
                this.producto.descripcion.length > 0 &&
                this.producto.descripcion.length <= 100 &&
                // Validar que la descripción no contenga caracteres especiales ni scripts
                /^[a-zA-Z0-9\s]*$/.test(this.producto.descripcion)
            );
        },

        validarPrecio() {
            // Verificar que el precio sea un número entero, mayor a 0 y menor a 3000
            return (
                this.producto.precio !== "" &&
                this.producto.precio > 0 &&
                this.producto.precio < 3000
            );
        },

        validarStock() {
            return this.producto.stock !== 0;
        },

        formValid() {
        // Verifica si todos los campos están validados
        return this.validarNombre && this.validarSubCategoria && this.validarDescripcion && this.validarPrecio && this.validarStock && this.producto.imagenes.length > 0;    }
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