<template>
    <div>
        <div>
            <b-modal hide-footer hide-header centered id="modal-guardar-categorias">
                <header class="text-center border-bottom">
                    <p>Registrar categoria</p>
                </header>
                <main>
                    <form id="registrarCategoria">
                        <b-row>
                            <b-col>
                                <label for="nombre">Nombre de la categoria: *</label>
                                <InputTextMax @check="validNombre" @name="dataChild" :numMax="maximo" />
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
    components: {
        InputTextMax: () => import('../../../../components/input_validations/InputTextMax.vue'),
    },
    data() {
        return {
            categoria: {
                nombre: "",
            },
            valueCategoria: "",
            maximo: 15
        }
    },
    methods: {
        dataChild(data) {
            this.categoria.nombre = data;
        },
        validNombre(data) {
            this.valueCategoria = data;
        },
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

                if (result.isConfirmed && this.valueCategoria) {
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
                        Swal.fire('Error', 'Hubo un problema al intentar REGISTRAR la categoria, intente mas tarde', 'error');
                    }
                } else {
                    Swal.fire('Error', 'Revise todo los campos', 'error');
                }
            } catch (error) {
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
    background-color: #009475;
    color: white;
}

.cancel {
    background-color: #ffce50;
    color: black;
}
</style>
