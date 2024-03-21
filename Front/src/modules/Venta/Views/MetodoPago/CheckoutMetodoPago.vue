<template>
    <div class="container d-flex justify-content-center">
        <div class="custom-container bigger py-1">
            <div class="card rounded-3 text-black">
                <div class="text-center">
                    <br>
                    <h5>¿Como quieres pagar? <b-icon icon="credit-card"></b-icon></h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <div v-if="metodosPago.length > 0">
                        <b-card v-for="metodoPago in metodosPago" :key="metodoPago.id" class="mb-3">
                            <h6>{{ metodoPago.nombre }}</h6>
                            <p>{{ metodoPago.descripcion }}</p>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" :value="metodoPago.id"
                                    v-model="metodoPagoSeleccionado">
                                <label class="form-check-label">
                                    Elegir método de pago
                                </label>
                            </div>
                        </b-card>
                    </div>
                    <div v-else>
                        <p>No hay direcciones disponibles</p>
                    </div>
                    <div class="button-container mt-3 d-flex justify-content-between">
                        <button class="btn btn-primary fa-lg gradient-custom-2" @click="agregarMetodoPago"
                            type="button">
                            Agregar nuevo método de pago <i class="fas fa-plus"></i>
                        </button>
                        <button class="btn btn-primary fa-lg gradient-custom-2" @click="continuar"
                            type="button">Continuar <i class="fa fa-arrow-right" aria-hidden="true"></i>
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
                    <div v-if="producto.length > 0">
                        <p>Total de Productos: {{ calculateTotal() }}</p>
                        <p>Total: {{ calculateTotal() + calculateEnvio() }}</p>
                    </div>
                    <div v-else>
                        <p>No hay productos en el carrito</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "CheckoutMetodoPago",
    data() {
        return {
            metodosPago: [
                {
                    id: 1,
                    nombre: "Tarjeta de crédito",
                    descripcion: "Paga con tu tarjeta de crédito",
                    seleccionado: false
                },
                {
                    id: 2,
                    nombre: "Tarjeta de débito",
                    descripcion: "Paga con tu tarjeta de débito",
                    seleccionado: false
                },
                {
                    id: 3,
                    nombre: "Paypal",
                    descripcion: "Paga con tu cuenta de Paypal",
                    seleccionado: false
                }
            ],
            metodoPagoSeleccionado: null,
            producto: [
                {
                    id: "1",
                    Nombre: "Playera",
                    Descripcion: "Es una playera así bien mamalona bien aca",
                    Precio: 500,
                    Cantidad: "2",
                    Imagenes: ["https://grupogranpremio.net/wp-content/uploads/2018/05/12.png"],
                    Categorias: "Hombre",
                    Subcategoria: "Playera",
                    envioGratis: false // Inicialmente asumimos que el envío no es gratis
                },
                {
                    id: "2",
                    Nombre: "Pantalón",
                    Descripcion: "Es un pantalón chido",
                    Precio: 250,
                    Cantidad: "1",
                    Imagenes: ["https://getlavado.com//wp-content/uploads/2020/05/pantalon.png"],
                    Categorias: "Hombre",
                    Subcategoria: "Pantalón",
                    envioGratis: false
                },
            ],
        }
    },
    methods: {
        calculateTotal() {
            return this.producto.reduce((total, product) => total + (product.Precio * product.Cantidad), 0);
        },
        calculateEnvio() {
            const totalProductos = this.calculateTotal();
            return totalProductos > 300 ? 0 : 100;
        },
        agregarMetodoPago() {
            // Aquí podrías implementar la lógica para agregar una nueva dirección
        },
        continuar() {
            // Aquí podrías implementar la lógica para continuar con el proceso de compra
        },
    }
}
</script>

<style>
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
