<template>
    <div>

        <div class="custom-container py-1 h-200">
            <div class="row">
                <div v-for="usuario in usuarios" :key="usuario.id" class="col-lg-3 mb-4">
                    <b-card :img-src="usuario.imageObj.src" img-alt="Image" img-top img-height="250px" max-width="150px"
                        class="mb-2">
                        <b-card-text>
                            <h5>{{ usuario.nombre }}</h5>
                            <p>Email: {{ usuario.email }}</p>
                            <p>Rol: {{ usuario.role }}</p>
                        </b-card-text>
                        <template #footer>
                            <b-row>
                                <b-col>Precio: $240</b-col>
                                <b-col>
                                    <div class="d-flex justify-content-end">
                                        <b-button v-b-tooltip.hover="'Agregar al carrito'" class="boton"
                                            to="kid-producto" variant="faded">
                                            <b-icon icon="cart-plus"></b-icon>
                                        </b-button>
                                    </div>
                                </b-col>
                            </b-row>
                        </template>
                    </b-card>
                </div>
            </div>
        </div>
    </div>

</template>

<script>
import axios from "axios";
import Swal from "sweetalert2";
export default {
    name: "Inicio",
    data() {
        return {
            usuarios: [],
        };
    },
    methods: {
        async getUsuarios() {
            try {
                const token = localStorage.getItem("token");
                const response = await axios.get(
                    "http://localhost:8091/api-carsi-shop/usuario/getAll",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                this.usuarios = response.data.data;

                // Convertir URLs base64 en objetos de imagen
                for (const usuario of this.usuarios) {
                    const base64 = usuario.imagenUrl.split(',')[1]; // Extraer la parte base64 de la URL
                    const imageUrl = `data:image/png;base64,${base64}`; // Construir la URL base64 completa
                    usuario.imageObj = { src: imageUrl }; // Convertir la URL en un objeto de imagen
                }

                console.log(this.usuarios);
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
        async deleteUser(userId) {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de eliminar este usuario?",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#008c6f",
                    cancelButtonColor: "#e11c24",
                    confirmButtonText: "Confirmar",
                    cancelButtonText: "Cancelar",
                });

                if (result.isConfirmed) {
                    const token = localStorage.getItem("token");
                    await axios.delete(
                        "http://localhost:8091/api-carsi-shop/usuario/delete",
                        {
                            headers: {
                                Authorization: `Bearer ${token}`,
                            },
                            data: {
                                id: userId,
                            },
                        }
                    );
                    this.getUsuarios();
                    Swal.fire(
                        "Eliminado",
                        "El usuario ha sido eliminado correctamente",
                        "success"
                    );
                }
            } catch (error) {
                console.error("Error al eliminar el usuario", error);
                Swal.fire(
                    "Error",
                    "Hubo un problema al intentar eliminar el usuario",
                    "error"
                );
            }
        },
        edit(usuario) {
            // Método edit
        },
    },
    mounted() {
        this.getUsuarios();
    },
};
</script>

<style scoped>
.userList {
    background-color: #F5F5F5;
    color: black;
}

.custom-container {
    max-width: 3000px;
    margin: 0 auto;
}
</style>