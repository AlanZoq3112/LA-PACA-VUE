<template>
    <div>
        <b-modal hide-footer hide-header centered id="modal-info-productos" style="max-width: 80vw;">
            <header class="text-center border-bottom">
                <p>Información de la compra</p>
            </header>
            <main>
                <b-form>
                    <b-row>
                        <b-col>
                            <p>Folio: {{ Editproducto.idPago }}</p>
                            <p>Status del pedido: {{ Editproducto.estado }}</p>
                            <p>Dirección del envio: {{ Editproducto.direccion.calle }} , {{ Editproducto.direccion.numero }} , {{ Editproducto.direccion.colonia }}
                                , {{ Editproducto.direccion.municipio }} , {{ Editproducto.direccion.estado }}.</p>
                            <p>Productos comprados:</p>
                            <ul>
                                <li v-for="producto in Editproducto.items" :key="producto.id">{{ producto.producto.nombre }}</li>
                            </ul>
                            <p>Total del pago: ${{ Editproducto.monto }}</p>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col></b-col>
                        <b-col>
                            <b-button type="button" class="cancel" @click="onClose">Ok</b-button>
                        </b-col>

                        <b-col></b-col>
                    </b-row>
                </b-form>
            </main>
        </b-modal>
    </div>
</template>

<script>
export default {
    name: "ModalInfo",
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
            this.$bvModal.hide("modal-info-productos");
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
