<template>
    <div>
        <!-- submenu -->
        <div class="text-center ">
            <b-button class="ss" to="hombre-screen" variant="light">HOMBRE</b-button>
            <b-button class="ss" to="mujer-screen" variant="light">MUJER</b-button>
            <b-button class="ss" to="nenes-screen" variant="light">NIÑOS</b-button>
            <b-button class="ss" to="ofertas-screen" variant="light">¡OFERTAS!</b-button>
        </div>

        <!-- contenido -->
        <div>
            <b-card overlay img-src="https://picsum.photos/900/250/?image=3" img-alt="Card Image" text-variant="white"
                title="Image Overlay" sub-title="Subtitle">
                <b-card-text>
                    Some quick example text to build on the card and make up the bulk of the card's content.
                </b-card-text>
            </b-card>
        </div>
    </div>
</template>

<script>
export default {
    name: "inicio"
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