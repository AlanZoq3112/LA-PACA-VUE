<template>
    <div>
        <div>
            <b-modal ref="modal-editar-categorias" hide-footer hide-header centered>
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Actualizar categoria</p>
                </header>
                <main>
                    <form id="registrarCategoria">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre de la categoria: *</label>
                                <b-form-input v-model="categoria.nombre" type="text" class="form-control"
                                    placeholder="Nombre" required />
                            </b-col>
                        </b-row>
                    </form>

                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" @click="onClose" id="saveCategorisa">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="edit" id="saveCategoria" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
export default {
    name: "modal-editar-categorias",
    data() {
        return {
            categoria: {
                id: "",
                nombre: "",
            }
        }

    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-editar-categorias");
            this.resetForm();
        },
        async edit() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de editar la categoria?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });
                if (result.isConfirmed) {
                    await axios.put(`http://localhost:8090/api-carsi-shop/admin/categoria/update`, this.categoria);
                    this.$bvModal.hide("modal-editar-categorias");
                    this.$emit("categoria-saved");
                    this.resetForm();
                    Swal.fire({
                        title: "Categoria registrada",
                        icon: "success",
                        confirmButtonColor: '#008c6f',
                    });
                }
            } catch (error) {
                Swal.fire({
                    title: "Error al registrar la categoria",
                    icon: "error",
                    confirmButtonColor: '#e11c24',
                });
            }
        },
        resetForm() {
            this.categoria = {
                nombre: "",
            }
        }
    },
}
</script>

<style scoped></style>