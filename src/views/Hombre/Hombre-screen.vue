<template>
    <div>
        <!-- submenu -->
        <div class="text-center ">

            <b-dropdown split split-href="/hombre-screen" text="HOMBRE" class="m-2 ss" variant="light">
                <b-dropdown-item to="hombre-screen">Ropa</b-dropdown-item>
                <b-dropdown-item to="">Calzado</b-dropdown-item>
                <b-dropdown-item to="">Accesorios</b-dropdown-item>
            </b-dropdown>

            <b-dropdown split split-href="/mujer-screen" text="MUJER" class="m-2 ss" variant="light">
                <b-dropdown-item to="mujer-screen">Ropa</b-dropdown-item>
                <b-dropdown-item to="">Calzado</b-dropdown-item>
                <b-dropdown-item to="">Accesorios</b-dropdown-item>
            </b-dropdown>

            <b-dropdown split split-href="/nenes-screen" text="NIÑOS" class="m-2 ss" variant="light">
                <b-dropdown-item to="nenes-screen">Ropa</b-dropdown-item>
                <b-dropdown-item to="">Calzado</b-dropdown-item>
                <b-dropdown-item to="">Accesorios</b-dropdown-item>
            </b-dropdown>

            <b-button class="ss" to="ofertas-screen" variant="light">¡OFERTAS!</b-button>


        </div>


        <!-- contenido -->
        <div>
            
        </div>


    </div>
</template>

<script>
export default {
    name: "hombre-screen",
}

</script>

<style>
.submenu {
    color: blue;
}

.ss {
    background-color: rgb(255, 255, 255);
    margin-bottom: 5px;
}
</style>