<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-editar-productos" style="max-width: 80vw;">
            <header class="text-center border-bottom">
                <p>Editar producto</p>
            </header>
            <main>
                <b-form @submit.prevent="editarProducto">
                    <b-row>
                        <b-col>
                            <p>Nombre: {{ Editproducto.nombre }}</p>
                            <p>Stock actual: {{ Editproducto.stock }}</p>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <b-form-group label="Nuevo Stock" label-for="stock">
                                <b-form-select v-model="newStock" id="stock" :options="generarOpcionesStock()"
                                    required />
                            </b-form-group>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <b-button type="button" class="cancel" @click="onClose">Cancelar</b-button>
                        </b-col>
                        <b-col>
                            <b-button type="submit" class="success" variant="success">Editar</b-button>
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
    name: "ModalEditaProductos",
    props: {
        producto: Object,
    },
    data() {
        return {
            Editproducto: {
                stock: 0, // Solo necesitamos el campo de stock para la edición
            },
            newStock: 0, // Nuevo stock seleccionado por el usuario
            loading: false,
        }



    },

    methods: {
        onClose() {
            // Cerrar el modal
            this.$bvModal.hide("modal-editar-productos");
            // Resetear el formulario
            this.resetForm();
            // Mostrar en la consola el producto seleccionado
        },
        async editarProducto() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de editar el producto?",
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

                    // Crear un objeto con solo el campo 'stock' para enviar al servidor
                    const data = {
                        stock: this.newStock
                    };

                    // Enviar la solicitud de actualización al backend
                    const response = await axios.put("http://localhost:8091/api-carsi-shop/producto/update-stock", data, {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    });

                    if (response.status === 200) {
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El producto se actualizó correctamente",
                            icon: "success"
                        });
                        // Emitir evento para actualizar la tabla de productos
                        this.$emit('producto-actualizado');
                        // Cerrar modal
                        this.$bvModal.hide("modal-editar-productos");
                    } else {
                        Swal.fire({
                            title: "Error",
                            text: "Hubo un problema al intentar editar el producto",
                            icon: "error"
                        });
                    }
                }
            } catch (error) {
                console.error("Error al realizar la solicitud de guardado:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar editar el producto",
                    icon: "error"
                });
            }
        },
        generarOpcionesStock() {
            const opciones = [];
            for (let i = 5; i <= 40; i += 5) {
                opciones.push({ value: i, text: `${i}` });
            }
            return opciones;
        },
        resetForm() {
            this.Editproducto = {
                stock: 0,
            };
        },

    },
    // Watch para actualizar el producto cuando cambien las props
    watch: {
        producto: {
            handler(newVal) {
                this.Editproducto = { ...newVal };
            },
            immediate: true,
        },
    }
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
