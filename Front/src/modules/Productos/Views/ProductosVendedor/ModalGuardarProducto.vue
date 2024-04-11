<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <b-modal hide-footer hide-header centered id="modal-guardar-productos" style="max-width: 80vw;"  @show="getSubcategorias">
            <header class="text-center border-bottom">
                <p>Registrar producto</p>
            </header>
            <main>
                <b-form @submit.prevent="save">
                    <b-row>
                        <b-col>
                            <b-form-group label="Nombre del producto" label-for="nombre">
                                <b-form-input v-model="producto.nombre" type="text" id="nombre" required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Subcategoria" label-for="subCategoria">
                                <b-form-select v-model="producto.subCategoria" id="subCategoria" :options="subcategorias.map(subcategoria => ({ value: subcategoria.id, text: `${subcategoria.nombre} de ${subcategoria.categoria.nombre}` }))" required></b-form-select>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-form-group label="Descripción" label-for="descripcion">
                        <b-form-textarea v-model="producto.descripcion" id="descripcion" rows="4"
                            required></b-form-textarea>
                    </b-form-group>
                    <b-row>
                        <b-col>
                            <b-form-group label="Precio" label-for="precio">
                                <b-form-input v-model="producto.precio" type="number" id="precio"
                                    required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Stock" label-for="stock">
                                <b-form-input v-model="producto.stock" type="number" id="stock" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-row>
                        <b-form-group label="Imágenes del producto" label-for="imagenes">
                            <input type="file" id="imagenes" multiple @change="handleFileUpload($event)"
                                class="form-control" required>
                            <!-- Vista previa de las imágenes seleccionadas -->
                            <div v-if="producto.imagenes.length > 0" class="preview-container mt-3">
                                <div v-for="(imagen, index) in producto.imagenes" :key="index" class="image-preview">
                                    <img :src="getImageURL(imagen)" alt="Imagen previa" class="preview-image">
                                </div>
                            </div>
                        </b-form-group>       
                    </b-row>
                    
                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <b-button type="button" class="cancel" @click="onClose">Cancelar</b-button>
                        </b-col>
                        <b-col>
                            <b-button type="submit" class="success" variant="success">Registrar</b-button>
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

                if (result.isConfirmed) {
                    this.loading = true;
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
                Swal.fire('Error', 'Hubo un problema al intentar obtener las subcategorias, intente mas tarde', 'error');            }
        },

    },

    mounted() {
        this.getSubcategorias();
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