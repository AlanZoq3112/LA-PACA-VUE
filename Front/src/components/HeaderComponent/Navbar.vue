<template>
    <div>
        <div>
            <b-navbar fixed-top toggleable="lg" type="dark" variant="faded">
                <b-navbar-brand to="inicio"><b-button variant="faded">
                <b-icon v-b-tooltip.hover="'Volver a la pagina de inicio'" icon="house-door"></b-icon></b-button></b-navbar-brand>
                
                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav class="ml-auto">
                            <b-button v-b-tooltip.hover="'Carrito de compra'" class="boton" to="carritoCompras" variant="faded"><b-icon icon="cart3"></b-icon></b-button>
                            <b-button v-b-tooltip.hover="'Ventas'" class="boton" to="login" variant="faded"><i class="fa fa-tags" aria-hidden="true"></i></b-button>
                            <b-button v-b-tooltip.hover="'Mi perfil'" class="boton" to="login" variant="faded"><b-icon icon="person"></b-icon></b-button>
                    </b-navbar-nav>
                </b-collapse>
            </b-navbar>
        </div>  
    </div>
</template>

<script>
export default {
    name: "Navbar"
}
</script>
<style>
.margin {
    margin-bottom: 6px;
}
</style>