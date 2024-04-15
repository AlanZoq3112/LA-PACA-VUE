<template>
    <div class="container d-flex justify-content-center">
        <div class="custom-container bigger py-1">
            <div class="card rounded-3 text-black">
                <div class="text-center">
                    <br>
                    <h5>Dirección de entrega <b-icon icon="geo-alt"></b-icon></h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <div v-if="direcciones.length > 0">
                        <b-card v-for="direccion in direcciones" :key="direccion.id" class="mb-3">
                            <h5>Código Postal {{ direccion.codigoPostal }}</h5>
                            <h6> Calle {{ direccion.calle }} No. {{ direccion.numero }}, Colonia {{ direccion.colonia
                                }}, {{ direccion.municipio }} {{ direccion.estado }}</h6>
                            <p>Referencias: {{ direccion.referencia }}</p>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" :value="direccion.id"
                                    v-model="direccionElegida">
                            </div>
                        </b-card>
                    </div>
                    <div v-else>
                        <p>No hay direcciones disponibles</p>
                    </div>
                    <div class="button-container mt-3 d-flex justify-content-between">
                        <button v-b-modal.modal-guardar-direcciones
                        style="background-color: black;color: white;" 
                        class="btn fa-lg gradient-custom-2"
                        type="button">
                        Agregar nueva dirección <i class="fas fa-plus"></i>
                        </button>
                        <button class="btn fa-lg gradient-custom-2" style="background-color: black;color: white;"
                            @click="continuar" type="button">Continuar <i class="fa fa-arrow-right"
                                aria-hidden="true"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="custom-container2 py-1">
            <div class="card rounded-3 text-black">
                <div class="text-center">
                    <br>
                    <h5>Resumen de compra</h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <div v-if="productos.length > 0">
                        <p>Total: ${{ calculateTotal()}}</p>
                    </div>
                    <div v-else>
                        <p>No hay productos en el carrito</p>
                    </div>
                </div>
            </div>
        </div>
        <ModalSaveDireccion @direccion-saved="getDirecciones"/>
    </div>
</template>

<script>
import ModalSaveDireccion from "./ModalSaveDireccion.vue";
import axios from "axios";
export default {
    components:
    {
        ModalSaveDireccion,
    },
    name: "metodoPago",
    data() {
        return {
            direcciones: [],
            productos: [],
            direccionElegida: null,
        };
    },
    methods: {
        calculateTotal() {
            return this.productos.reduce((total, producto) => total + (producto.precio * producto.stock), 0);
        },
        continuar() {
            this.$router.push({ name: 'checkoutMetodoPago' });
        },

        async getproductos() {
            try {
                const token = localStorage.getItem("token");
                const response = await axios.get(
                    "http://localhost:8091/api-carsi-shop/producto/getAll",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                // Filtrar los productos por subcategoría para hombres
                this.productos = response.data.data.filter(producto => {
                    return producto.subCategoria.categoria.nombre.toLowerCase() === "hombre" &&
                        producto.subCategoria.nombre.toLowerCase() === "calzado";
                });

            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },

        async getDirecciones() {
            try {
                const token = localStorage.getItem("token");
                const response = await axios.get(
                    "http://localhost:8091/api-carsi-shop/direccion/mis-direcciones",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                // Filtrar los productos por subcategoría para hombres
                this.direcciones = response.data.data;

                console.log("Direcciones del usuario: ", this.direcciones);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
    },
    mounted() {
        this.getproductos();
        this.getDirecciones();
    },
};
</script>

<style scoped>
.container {
    display: flex;
    justify-content: space-between;
    gap: 20px;
    flex-wrap: wrap;
}

.custom-container {
    flex: 2;
    max-width: calc(66.66% - 10px);
}

.custom-container2 {
    flex: 1;
    max-width: calc(33.33% - 10px);
}
</style>
