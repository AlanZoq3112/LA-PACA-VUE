<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-guardar-subcategorias">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Registrar subcategoria</p>
                </header>
                <main>
                    <form id="registrarSubategoria">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre de la subcategoria: *</label>
                                <b-form-input v-model="subcategoria.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required />
                            </b-col>
                        </b-row>
                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" id="saveSubcategoria">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" id="saveteam" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ModalGuardarSubcategorias",
        data() {
            return {
                subcategoria: {
                    nombre: "",

                }
            }
        },
    }
</script>

<style>

</style>