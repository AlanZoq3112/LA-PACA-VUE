<template>
    <div>
<<<<<<< HEAD
        <div>
            <b-modal hide-footer hide-header centered id="modal-guardar-productos" style="max-width: 80vw;">
                <header class="text-center border-bottom">
                    <p>Registrar producto</p>
                </header>
                <main>
                    <form id="registrarproducto" @submit.prevent="registrarProducto">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre del producto: *</label>
                                <InputTextMax @check="validNombre" @name="dataChildName" :numMax="maximoName" required/>                                                             
                                <p>{{ valueName }}</p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="descripcion">Descripción: *</label>
                                <InputTextMax @check="validDescription" @name="dataChildDescription" :numMax="maximoDescription" required/>
                                <p>{{ valueDescription }}</p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="precio">Precio: *</label>
                                <b-form-input v-model="producto.precio" type="number" class="form-control"
                                    placeholder="Precio del producto" required />
                            </b-col>
                            <b-col>
                                <label for="stock">Stock: *</label>
                                <b-form-input v-model="producto.stock" type="number" class="form-control"
                                    placeholder="Stock del producto" required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="categoria">Categoria: *</label>
                                <b-form-input v-model="producto.categoria.id" type="text" class="form-control"
                                    placeholder="Id de la categoria xd" required />
                            </b-col>
                            <b-col>
                                <label for="imagen">Imagenes del producto: *</label>
                                <b-form-input v-model="producto.imagenUrl" type="text" class="form-control"
                                    placeholder="Imagen del producto" required />
                            </b-col>
                        </b-row>
                    </form>
                </main>
                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" id="saveproducto" @click="onClose">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" id="saveteam" type="submit" @click="save">
                        Registrar
                    </button>
                </footer>
            </b-modal>
=======
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
>>>>>>> cf9a4d3507809a8f5df4eba445ef8ffd7c337d70
        </div>
        <b-modal hide-footer hide-header centered id="modal-guardar-productos" style="max-width: 80vw;">
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
                            <b-form-group label="Subcategoria" label-for="nombre">
                                <b-form-input v-model="producto.subCategoria" type="text" id="nombre" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>

                    <b-form-group label="Descripción" label-for="descripcion">
                        <b-form-textarea v-model="producto.descripcion" id="descripcion" rows="4" required></b-form-textarea>
                    </b-form-group>
                    <b-row>
                        <b-col>
                            <b-form-group label="Precio" label-for="precio">
                                <b-form-input v-model="producto.precio" type="number" id="precio" required></b-form-input>
                            </b-form-group>
                        </b-col>
                        <b-col>
                            <b-form-group label="Stock" label-for="stock">
                                <b-form-input v-model="producto.stock" type="number" id="stock" required></b-form-input>
                            </b-form-group>
                        </b-col>
                    </b-row>



                    <b-form-group label="Imagenes del producto" label-for="imagenes">
                        <input type="file" id="imagenes" multiple @change="handleFileUpload($event)" class="form-control" required>
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
import { required, helpers, minLength } from "@vuelidate/validators";
export default {
    name: "ModalGuardarProductos",
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
            producto: {
                nombre: "",
                descripcion: "",
                precio: 0,
                stock: 0,
                subCategoria: 0,
                imagenes: []
            },
            maximoName:40,
            maximoDescription: 100,
            valueName:false,
            valueDescription:false,
            subcategorias: [],
            loading: false
        }
    },

    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-productos");
            this.resetForm();
        },
<<<<<<< HEAD
        dataChildName(data) {
            this.producto.nombre = data;
        },
        validNombre(data) {
            this.valueName = data;
        },
        dataChildDescription(data) {
            this.producto.descripcion = data;
        },
        validDescription(data) {
            this.valueDescription = data;
=======
        handleFileUpload(event) {
            const files = event.target.files;
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                this.producto.imagenes.push(file);
            }
>>>>>>> cf9a4d3507809a8f5df4eba445ef8ffd7c337d70
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
                    console.log(this.producto);
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
                precio: 0.0,
                stock: 0,
                subCategoria: {
                    id: 0,
                }
            }
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
</style>