<template>
    <div class="container d-flex justify-content-center">
        <div class="custom-container bigger py-1">
            <div class="card rounded-3 text-black ">
                <div class="text-center">
                    <br>
                    <h5>Productos</h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <b-card v-for="product in producto" :key="product.id">
                        <div class="row">
                            <div class="col-md-7">
                                <h6>{{ product.Nombre }}</h6>
                                <p>{{ product.Descripcion }}</p>
                                <p>Precio: {{ product.Precio }}</p>
                                <p>Cantidad: {{ product.Cantidad }}</p>
                            </div>
                            <div class="col-md-5">

                            </div>
                        </div>
                        <template #footer>
                            <div class="icono">
                                <b-button variant="faded"><b-icon icon="pencil"></b-icon></b-button>
                                <b-button variant="faded" style="color: red;"><b-icon icon="trash"></b-icon></b-button>
                            </div>
                        </template>
                    </b-card>
                </div>
            </div>
        </div>
        <div class="custom-container2 py-1">
            <div class="card rounded-3 text-black ">
                <div class="text-center">
                    <br>
                    <h5>Resumen de compra</h5>
                </div>
                <div class="card-body p-md-5 mx-md-4">
                    <div v-if="producto.length > 0">
                        <p>Total de Productos: {{ calculateTotal() }}</p>
                        <p>Envío: {{ calculateEnvio() }}</p>
                        <p>Total: {{ calculateTotal() + calculateEnvio() }}</p>
                    </div>
                    <div v-else>
                        <p>No hay productos en el carrito</p>
                    </div>

                    <div class="text-center mt-3">
                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3" @click="continuar" type="button">
                            Continuar compra <i class="fas fa-sign-in-alt"></i></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "CarritoCompras",
    data() {
        return {
            producto: [
                {
                    id: "1",
                    Nombre: "Playera",
                    Descripcion: "Es una playera asi bien mamalona bien aca",
                    Precio: 500,
                    Cantidad: "2",
                    Imagenes: ["https://grupogranpremio.net/wp-content/uploads/2018/05/12.png"],
                    Categorias: "Hombre",
                    Subcategoria: "Playera",
                    envioGratis: false, // Inicialmente asumimos que el envío no es gratis
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
                    envioGratis: false,
                },
            ],
        };
    },
    mounted() {
        this.producto.forEach(product => {
            if (product.Precio > 300) {
                product.envioGratis = true;
            }
        });
    },
    methods: {
        calculateTotal() {
            return this.producto.reduce((total, product) => total + (product.Precio * product.Cantidad), 0);
        },
        calculateEnvio() {
            const totalProductos = this.calculateTotal();
            return totalProductos > 300 ? 0 : 100;
        },
        continuar() {
            console.log(this.user);
            this.$router.push({ name: 'checkoutDireccion' });
        }
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