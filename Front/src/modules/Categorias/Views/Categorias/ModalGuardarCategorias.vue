<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-guardar-categorias">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Registrar categoria</p>
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
                    <button class="btn m-1 cancel" @click="onClose" id="savemovie">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="save" id="saveteam" type="submit">
                        Registrar
                    </button>
                </footer>
            </b-modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "modal-guardar-categorias",
    data() {
        return {
            categoria: {
                nombre: "",
            }
        }
    },
    methods: {
        onClose() {
            this.$bvModal.hide("modal-guardar-categorias");
            this.resetForm();
        },
        async save() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de registrar la categoria?",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#008c6f',
                    cancelButtonColor: '#e11c24',
                    confirmButtonText: "Confirmar",
                    cancelButtonText: 'Cancelar',
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem('token');
                    if (!token) {
                        Swal.fire('Error', 'No se encontró un token válido', 'error');
                        return;
                    }
                    const response = await axios.post("http://localhost:8091/api-carsi-shop/categoria/insert", this.categoria, {
                        headers: {
                            Authorization: `Bearer ${token}` // Incluir el token JWT en el encabezado de autorización
                        }
                    });

                    if (response.status === 201) {
                        this.resetForm();
                        Swal.fire({
                            title: "¡Guardada!",
                            text: "La categoria se registró correctamente",
                            icon: "success"
                        });

                        this.$emit('categoria-saved');
                        this.$bvModal.hide("modal-guardar-categorias");
                    } else {
                        console.log("Error al guardar la categoria. Estado del servidor:", response.status);
                    }
                }
            } catch (error) {
                console.error("Error al realizar la solicitud de guardado:", error);
                Swal.fire({
                    title: "Error",
                    text: "Hubo un problema al intentar guardar el usuario",
                    icon: "error"
                });
            }
        },

        resetForm() {
            this.categoria = {
                nombre: "",
            }
        }
    }
}
</script>

<style scoped>
.success {
    font-family: Cabin;
    background-color: #009475;
    color: white;
}

.cancel {
    font-family: Cabin;
    background-color: #ffce50;
    color: black;
}
</style>