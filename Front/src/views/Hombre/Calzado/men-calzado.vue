<template>
    <div>
        <div class="custom-container py-1">
            <div class="row">
                <div v-for="producto in productos" :key="producto.id" class="col-lg-3 mb-4">
                    <b-card class="card-custom mb-2" img-alt="Image" img-height="450px" max-width="200px" img-top>
                        <template #header>
                            <b-carousel :controls="false" indicators :interval="0">
                                <b-carousel-slide
                                    style="min-height: 300px; min-width: 100%; max-height: 300px; max-width: 100%;"
                                    v-for="(imagen, index) in producto.imagenes" :key="index"
                                    :img-src="imagen.imageUrl"></b-carousel-slide>
                            </b-carousel>
                        </template>
                        <b-card-text style="min-height:200px; min-width: 100%; max-height: 300px; max-width: 100%;">
                            <h5>{{ producto.nombre }}</h5>
                            <p>{{ producto.descripcion }}</p>
                            <p>{{ producto.subCategoria.nombre }} para {{ producto.subCategoria.categoria.nombre }}</p>
                            <p>{{ producto.stock }} disponibles</p>
                        </b-card-text>
                        <template #footer>
                            <b-row>
                                <b-col>Precio: ${{ producto.precio }}</b-col>
                                <b-col>
                                    <div class="d-flex justify-content-end">
                                        <b-button v-b-tooltip.hover="'Agregar al carrito'" class="boton"
                                            to="kid-producto" variant="faded">
                                            <b-icon icon="cart-plus"></b-icon>
                                        </b-button>
                                    </div>
                                </b-col>
                            </b-row>
                        </template>
                    </b-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
    name: "men-calzado",
    data() {
        return {
            productos: [],
        };
    },
    methods: {
        async getProductos() {
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
                        producto.subCategoria.nombre.toLowerCase() === "calzado" && producto.estado === 3 &&
                        producto.stock > 0
                });
                console.log(this.productos);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
    },
    mounted() {
        this.getProductos();
    },
};
</script>

<style scoped>
.userList {
    background-color: #F5F5F5;
    color: black;
}

.custom-container {
    max-width: 1200px;
    /* Reducido para hacerlo más responsivo */
    margin: 0 auto;
}

.card-custom {
    max-width: 300px;
    /* Tamaño máximo de la tarjeta */
    min-height: 450px;
    /* Altura mínima de la tarjeta */
    overflow: hidden;
    /* Ocultar el desbordamiento de las imágenes */
}

.card-custom .carousel-inner img {
    width: 100%;
    /* Ajustar el ancho de las imágenes al 100% del contenedor */
    height: auto;
    /* Altura automática para mantener la proporción */
}
</style>
