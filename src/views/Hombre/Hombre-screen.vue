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
            <div>
                <b-card-group deck>
                    <b-card title="Title" img-src="https://static.vecteezy.com/system/resources/previews/008/847/307/original/isolated-back-white-hoodie-free-png.png" img-alt="Image"  img-height="400" img-top>
                        <b-card-text>
                            This is a wider card with supporting text below as a natural lead-in to additional content.
                            This content is a little bit longer.
                        </b-card-text>
                        <template #footer>
                            <small class="text-muted">Last updated 3 mins ago</small>
                        </template>
                    </b-card>

                    <b-card title="Title" img-src="https://cdn1.coppel.com/images/catalog/pr/8072032-1.jpg" img-alt="Image" img-height="400" img-top>
                        <b-card-text>
                            This card has supporting text below as a natural lead-in to additional content.
                        </b-card-text>
                        <template #footer>
                            <small class="text-muted">Last updated 3 mins ago</small>
                        </template>
                    </b-card>

                    <b-card title="Title" img-src="https://www.elpalaciodehierro.com/on/demandware.static/-/Library-Sites-palacio-content-global/default/dw550aa212/images/PLP/PLP-segmentos/Hombre/Hombre_Ropa/Jeans/hombre-ropa-jeans-TRUE-RELIGION.jpg" img-alt="Image"  img-height="400" img-top>
                        <b-card-text>
                            This is a wider card with supporting text below as a natural lead-in to additional content.
                            This card has even longer content than the first to show that equal height action.
                        </b-card-text>
                        <template #footer>
                            <small class="text-muted">Last updated 3 mins ago</small>
                        </template>
                    </b-card>
                    <b-card title="Title" img-src="https://cdn1.coppel.com/images/catalog/mkp/6392/3000/63921239-1.jpg" img-alt="Image" img-height="400" img-top>
                        <b-card-text>
                            This is a wider card with supporting text below as a natural lead-in to additional content.
                            This card has even longer content than the first to show that equal height action.
                        </b-card-text>
                        <template #footer>
                            <small class="text-muted">Last updated 3 mins ago</small>
                        </template>
                    </b-card>
                </b-card-group>
            </div>
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