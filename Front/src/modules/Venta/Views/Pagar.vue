<template>
    <div>
        <div v-if="loading" class="overlay">
            <div class="loader">
                <div class="spinner"></div>
            </div>
        </div>
        <div class="container d-flex justify-content-center">
            <div class="custom-container py-1">
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
                                    <b-button variant="faded" style="color: red;"><b-icon
                                            icon="trash"></b-icon></b-button>
                                </div>
                            </template>
                        </b-card>
                    </div>
                </div>
            </div>
            <div class="custom-container py-1">
                <!-- Parte derecha -->
                <div class="d-flex flex-column h-100">
                    <div class="mb-2">
                        <div class="card rounded-3 text-black">
                            <div class="text-center">
                                <br>
                                <h5>Dirección de entrega <b-icon icon="geo-alt"></b-icon></h5>
                            </div>
                            <div class="card-body p-md-5 mx-md-4">
                                <div v-if="direcciones.length > 0">
                                    <b-card v-for="direccion in direcciones" :key="direccion.id" class="mb-3">
                                        <h5>Código Postal {{ direccion.codigoPostal }}</h5>
                                        <h6> Calle {{ direccion.calle }} No. {{ direccion.numero }}, Colonia {{
            direccion.colonia
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
                                        class="btn fa-lg gradient-custom-2" type="button">Agregar nueva dirección <i
                                            class="fas fa-plus"></i></button>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="card rounded-3 text-black">
                            <div class="text-center">
                                <br>
                                <h5>Método de pago <b-icon icon="credit-card"></b-icon></h5>
                            </div>
                            <div class="card-body p-md-5 mx-md-4">
                                <div v-if="direcciones.length > 0">
                                    <b-card v-for="direccion in direcciones" :key="direccion.id" class="mb-3">
                                        <h5>Código Postal {{ direccion.codigoPostal }}</h5>
                                        <h6> Calle {{ direccion.calle }} No. {{ direccion.numero }}, Colonia {{
            direccion.colonia
        }}, {{ direccion.municipio }} {{ direccion.estado }}</h6>
                                        <p>Referencias: {{ direccion.referencia }}</p>
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" :value="direccion.id"
                                                v-model="metodoPagoElegido">
                                        </div>
                                    </b-card>
                                </div>
                                <div v-else>
                                    <p>No hay Metodos de Pago disponibles</p>
                                </div>
                                <div class="button-container mt-3 d-flex justify-content-between">
                                    <button v-b-modal.modal-guardar-metodopago
                                        style="background-color: black;color: white;"
                                        class="btn fa-lg gradient-custom-2" type="button">Agregar método de pago <i
                                            class="fas fa-plus"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <div class="container d-flex justify-content-center">
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
                            class="btn  btn-block fa-lg gradient-custom-2 mb-3" @click="pagar" type="button">
                            Confirmar compra </button>
                    </div>
                </div>
            </div>
        </div>
        <ModalSaveDireccion @direccion-saved="getDirecciones" />
        <ModalSaveMetodoPago @direccion-saved="getDirecciones" />
    </div>
</template>

<script>
import ModalSaveDireccion from "./Direcciones/ModalSaveDireccion.vue";
import ModalSaveMetodoPago from "./MetodoPago/ModalSaveMetodoPago.vue";
import axios from "axios";
import Swal from 'sweetalert2';
export default {
    components:
    {
        ModalSaveDireccion,
        ModalSaveMetodoPago
    },
    name: "pagar",
    data() {
        return {
            direcciones: [],
            productos: [],
            direccionElegida: null,
            metodoPagoElegido: 1,
            venta: {
                items: [],
                direccion: {
                    id: 1
                },
                pago: {
                    id: 2
                }
            },
            loading: false

        };
    },
    methods: {
        calculateTotal() {
            return this.productos.reduce((total, producto) => total + (producto.precio * producto.stock), 0);
        },
        continuar() {
            console.log("Id de la direccion seleccionada:", this.direccionElegida);
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

            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },

        async pagar() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de comprar estos productos?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });
                if (result.isConfirmed) {
                    this.loading = true;
                    this.venta.direccion.id = this.direccionElegida;
                    this.venta.pago.id = this.metodoPagoElegido;

                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    // Recorremos los productos y agregamos cada uno al array items de venta
                    this.productos.forEach(producto => {
                        this.venta.items.push({
                            cantidad: producto.stock,
                            producto: { id: producto.id }
                        });
                    });
                    console.log(this.venta);
                    axios.post('http://localhost:8091/api-carsi-shop/pago/realizar-pago', this.venta, {
                        headers: { Authorization: `Bearer ${token}` }
                    }).then(response => {
                            Swal.fire('Realizada', 'Se realizó la compra correctamente', 'success');
                            this.$router.push({ name: 'profile-screen' });

                        })
                        .catch(error => {
                            console.error("Error durante la solicitud:", error);
                            let errorMessage = "Hubo un problema al realizar la compra";
                            if (error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                        });
                }
            } catch (error) {
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar realizar la compra",
                    icon: "error"
                });
                this.loading = false; // Restablece la bandera si ocurre un error en el bloque try-catch
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
    color: white;
    font-size: 20px;
    text-align: center;
}

.loading-text {
    margin-bottom: 10px;
}

.spinner {
    border: 5px solid rgba(255, 255, 255, 0.3);
    border-radius: 50%;
    border-top: 5px solid #ffffff;
    width: 30px;
    height: 30px;
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
