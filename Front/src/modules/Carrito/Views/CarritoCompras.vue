<template>
    <div class="container d-flex justify-content-center">
        <div class="custom-container bigger py-1">
            <div class="card rounded-3 text-black">
                <div class="text-center">
                    <br>
                    <h5>Productos</h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <b-card v-for="producto in productos" :key="producto.id">
                        <div class="row">
                            <div class="col-md-7">
                                <h6>{{ producto.nombre }}</h6>
                                <p>{{ producto.descripcion }}</p>
                                <p>Precio: {{ producto.precio }}</p>
                                <p>Cantidad: {{ producto.stock }}</p>
                            </div>
                            <div class="col-md-5">
                                <b-carousel :controls="false" indicators :interval="0" class="custom-carousel">
                                    <b-carousel-slide
                                        style="min-height: 200px; min-width: 100%; max-height: 300px; max-width: 100%;"
                                        v-for="(imagen, index) in producto.imagenes" :key="index"
                                        :img-src="imagen.imageUrl"></b-carousel-slide>
                                </b-carousel>
                            </div>
                        </div>
                        <template #footer>
                            <div class="icono">
                                <b-button variant="faded" style="color: red;"><b-icon icon="trash"></b-icon></b-button>
                            </div>
                        </template>
                    </b-card>
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

                    <div class="text-center mt-3">
                        <button style="background-color: black; color: white;"
                            class="btn  btn-block fa-lg gradient-custom-2 mb-3" @click="continuar" type="button">
                            Continuar compra </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";

export default {
    name: "CarritoCompras",
    data() {
        return {
            productos: [],
        };
    },
    mounted() {
        this.getproductos();
    },
    methods: {
        calculateTotal() {
            return this.productos.reduce((total, producto) => total + (producto.precio * producto.stock), 0);
        },
        continuar() {
            console.log(this.user);
            this.$router.push({ name: 'checkoutDireccion' });
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
                console.log("productos carrito: ", this.productos);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
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

/* Estilos para el carrusel personalizado */
.custom-carousel .carousel-inner {
    background-color: black;
    /* Color de fondo de los slides */
}
</style>
