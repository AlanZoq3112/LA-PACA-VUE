<template>
    <div>
        <div class="text-center ">
            <b>            
            <b-dropdown split split-href="/hombre-screen" text="HOMBRE" class="m-2 ss" variant="faded">
                <b-dropdown-item to="men-ropa">Ropa</b-dropdown-item>
                <b-dropdown-item to="men-calzado">Calzado</b-dropdown-item>
                <b-dropdown-item to="men-accesorios">Accesorios</b-dropdown-item>
            </b-dropdown>

            <b-dropdown split split-href="/mujer-screen" text="MUJER" class="m-2 ss" variant="faded">
                <b-dropdown-item to="mujer-screen">Ropa</b-dropdown-item>
                <b-dropdown-item to="">Calzado</b-dropdown-item>
                <b-dropdown-item to="">Accesorios</b-dropdown-item>
            </b-dropdown>

            <b-dropdown split split-href="/nenes-screen" text="NIÑOS" class="m-2 ss" variant="faded">
                <b-dropdown-item to="kid-ropa">Ropa</b-dropdown-item>
                <b-dropdown-item to="kid-calzado">Calzado</b-dropdown-item>
                <b-dropdown-item to="kid-accesorios">Accesorios</b-dropdown-item>
            </b-dropdown>

            </b>


        </div>
    </div>
</template>

<script>
export default {
    name: "submenu"
}
</script>
