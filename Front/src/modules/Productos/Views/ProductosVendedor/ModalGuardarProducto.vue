<template>
    <div>
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
                                <b-form-input v-model="producto.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required />
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <label for="descripcion">Descripción: *</label>
                                <b-form-textarea v-model="producto.descripcion" rows="4" class="form-control textarea-limit"
                                    placeholder="Descripción del producto" required />
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
        </div>
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
                imagenUrl: "",
                descripcion: "",
                precio: 0.0,
                stock: 0,
                categoria: {
                    id: 0,
                },
            },
            subcategorias: [],
        }
    },

    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-productos");
            this.resetForm();
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
                    console.log(this.producto);
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/producto/insert", this.producto, {
                        headers: {
                            Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                        }
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
            }
        },
        resetForm() {
            this.producto = {
                nombre: "",
                imagenUrl: "",
                descripcion: "",
                precio: 0.0,
                stock: 0,
                categoria: null,
            }
        }
    },
}
</script>

<style>
.textarea-limit {
    resize: none; /* Evita que el área de texto sea redimensionable */
    max-height: calc(1.5em * 3); /* Establece la altura máxima en función del número máximo de filas */
}
.success {
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
}
</style>