<template>
    <div>
        <div class="custom-container py-1">
            <div class="row">
                <div v-for="producto in productos" :key="producto.id" class="col-lg-3 mb-4">
                    <b-card class="card-custom mb-2">
                        <template #header>
                            <b-carousel :controls="false" indicators :interval="0">
                                <b-carousel-slide style="min-height: 300px; min-width: 100%; max-height: 300px; max-width: 100%;"
                                
                                v-for="(imagen, index) in producto.imagenes" :key="index" :img-src="imagen.imageUrl"></b-carousel-slide>
                            </b-carousel>
                        </template>
                        <b-card-text style="min-height:200px; min-width: 100%; max-height: 300px; max-width: 100%;">
                            <h5>{{ producto.nombre }}</h5>
                            <p>Description: {{ producto.descripcion }}</p>
                            <p>{{ producto.subCategoria.nombre }} para {{ producto.subCategoria.categoria.nombre }}</p>
                            <p>Stock: {{ producto.stock }}</p>
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
import Swal from "sweetalert2";
export default {
    name: "Inicio",
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
                this.productos = response.data.data;
                console.log(this.productos);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
        async deleteUser(userId) {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de eliminar este usuario?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#008c6f",
                    cancelButtonColor: "#e11c24",
                    confirmButtonText: "Confirmar",
                    cancelButtonText: "Cancelar",
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem("token");
                    await axios.delete(
                        "http://localhost:8091/api-carsi-shop/usuario/delete",
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                            data: {
                                id: userId,
                            },
                        }
                    );
                    this.getProductos();
                    Swal.fire(
                        "Eliminado",
                        "El usuario ha sido eliminado correctamente",
                        "success"
                    );
                }
            } catch (error) {
                console.error("Error al eliminar el usuario", error);
                Swal.fire(
                    "Error",
                    "Hubo un problema al intentar eliminar el usuario",
                    "error"
                );
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
    max-width: 1200px; /* Reducido para hacerlo más responsivo */
    margin: 0 auto;
}

.card-custom {
    max-width: 300px; /* Tamaño máximo de la tarjeta */
    min-height: 450px; /* Altura mínima de la tarjeta */
    overflow: hidden; /* Ocultar el desbordamiento de las imágenes */
}

.card-custom .carousel-inner img {
    width: 100%; /* Ajustar el ancho de las imágenes al 100% del contenedor */
    height: auto; /* Altura automática para mantener la proporción */
}
</style>
