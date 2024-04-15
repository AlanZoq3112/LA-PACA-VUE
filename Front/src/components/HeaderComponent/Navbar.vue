<template>
    <div>
        <div>
            <b-navbar fixed-top toggleable="lg" type="dark" variant="faded" class="shadow-navbar navbar-background">
                <b-navbar-brand to="inicio">
                    <b-button variant="faded">
                        <b-icon v-b-tooltip.hover="'Volver a la pagina de inicio'" icon="house-door"></b-icon>
                    </b-button>
                </b-navbar-brand>

                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav class="ml-auto">
                        <b-button v-b-tooltip.hover="'Carrito de compra'" class="boton" @click="goToCart"
                            variant="faded">
                            <b-icon icon="cart3"></b-icon>
                        </b-button>
                        <b-button v-b-tooltip.hover="'Mi perfil'" class="boton" @click="goToProfile" variant="faded">
                            <b-icon icon="person"></b-icon>
                        </b-button>
                    </b-navbar-nav>
                </b-collapse>
            </b-navbar>
        </div>
    </div>
</template>

<script>
export default {
    name: "Navbar",
    methods: {
        goToProfile() {
            // Verificar si hay un token de sesión almacenado
            const token = localStorage.getItem('token');

            // Si hay un token de sesión, redirigir al usuario a la página de perfil
            if (token) {
                this.$router.push({ name: 'profile-screen' });
            } else {
                // Si no hay un token de sesión, redirigir al usuario a la página de inicio de sesión
                this.$router.push({ name: 'login' });
            }
        },
        goToCart() {
            // Verificar si hay un token de sesión almacenado
            const token = localStorage.getItem('token');

            // Si hay un token de sesión, redirigir al usuario a la página de perfil
            if (token) {
                this.$router.push({ name: 'pagar' });
            } else {
                // Si no hay un token de sesión, redirigir al usuario a la página de inicio de sesión
                this.$router.push({ name: 'login' });
            }
        }

    },
}
</script>
<style>
.margin {
    margin-bottom: 6px;
}
.shadow-navbar {
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1); /* Añadir sombra leve */
}

.navbar-background {
    background: #f5f5f5;
}
</style>