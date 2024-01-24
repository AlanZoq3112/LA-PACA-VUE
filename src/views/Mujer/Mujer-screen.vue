<template>
    <div>
        <!-- submenu -->
        <div class="text-center">
            <b-button class="ss" to="hombre-screen" variant="light">HOMBRE</b-button>
            <b-button class="submenu ss" to="mujer-screen" variant="light">MUJER</b-button>
            <b-button class="ss" to="nenes-screen" variant="light">NIÑOS</b-button>
            <b-button class="ss" to="ofertas-screen" variant="light">¡OFERTAS!</b-button>
        </div>

        <!-- contenido -->
        <div class="text-center">
            contenido Mujer
        </div>
    </div>
</template>

<script>
export default {
    name: "mujer-screen",
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