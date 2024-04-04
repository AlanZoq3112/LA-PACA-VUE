<template>
    <div >
        <div>
            <div class="custom-container py-1 h-200">
                <h4>Mi cuenta <b-icon icon="person"></b-icon></h4>
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-12">
                        <div class="card rounded-3 text-black">
                            <div class="col-lg-12">
                                <div class="card-body p-md-5 mx-md-4">
                                    <b-row>
                                        <b-col cols="2">
                                            <!-- Aquí se mostrará la imagen de perfil -->
                                            <img v-if="user.imagenUrl" :src="user.imagenUrl"
                                                class="rounded-circle img-thumbnail" alt="Avatar"
                                                style="width: 150px; height: 150px;">
                                            <img v-else
                                                src="https://static-2.ivoox.com/canales/7/4/3/8/4001478748347_XXL.jpg"
                                                class="rounded-circle img-thumbnail" alt="Avatar"
                                                style="width: 150px; height: 150px;">
                                        </b-col>

                                        <b-col cols="8">
                                            <br>
                                            <br>
                                            <h4 style="color:black">{{ user.nombre }}</h4>
                                            <h5>{{ user.email }}</h5>
                                            <p>{{ user.role }}</p>
                                        </b-col>

                                        <b-col>
                                            <br>
                                            <br>
                                            <b-button @click="logout" variant="faded">
                                                <b-icon icon="person-fill"></b-icon> Cerrar sesion
                                            </b-button>
                                        </b-col>
                                    </b-row>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>

            <div class="custom-container py-1 h-200">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-12">
                        <div class="card rounded-3 text-black">
                            <div class="col-lg-12">
                                <div class="card-body p-md-5 mx-md-4">
                                    <!-- Info personal -->
                                    <div>
                                        <b-button v-b-toggle.collapsePersonal class="m-1" variant="faded">
                                            Información personal
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapsePersonal">
                                            <b-card>
                                                <b-row class="mb-3">
                                                    <b-col cols="5" class="pr-2">
                                                        <label for=""><b>Nombre y apellido</b></label>
                                                        <p>{{ user.nombre }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Correo</b></label>
                                                        <p>{{ user.email }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Teléfono</b></label>
                                                        <p>{{ user.telefono }}</p>
                                                    </b-col>
                                                </b-row>

                                                <b-row>
                                                    <b-col cols="8">
                                                        <label for=""><b>Fecha de Nacimiento</b></label>
                                                        <p>{{ user.fechaNacimiento }}</p>
                                                    </b-col>
                                                    <b-col>
                                                        <br>
                                                        <b-button style="color: black;" variant="faded">
                                                            <b-icon icon="pencil-square"></b-icon> Actualizar datos
                                                        </b-button>
                                                    </b-col>
                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>
                                    <!-- Historial compras -->
                                    <br>
                                    <div>
                                        <b-button v-b-toggle.collapseCompras class="m-1" variant="faded">Mis Compras
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapseCompras">
                                            <b-card>
                                                <b-button to="productos" variant="faded"><i class="fa fa-history"
                                                        aria-hidden="true"></i> Hisotrial de compras</b-button>

                                            </b-card>
                                        </b-collapse>
                                    </div>

                                    <br>
                                    <!-- Productos -->
                                    <div>
                                        <b-button v-b-toggle.collapsVendedor class="m-1" variant="faded">
                                            Administrar productos
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapsVendedor">
                                            <b-card>
                                                <div v-if="user.role == 'COMPRADOR'">
                                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                                        <p class="mb-0 me-2"><b>Aún no eres un vendedor </b></p>
                                                    </div>
                                                    <div class="d-flex pb-4">
                                                        <p class="mb-0 me-2"><b>¿Quieres vender tus productos?
                                                                <b-link class="text-muted"
                                                                    href="enviarSolicitdVendedor">Enviar solicitud de
                                                                    vendedor</b-link></b>
                                                        </p>
                                                    </div>
                                                </div>
                                                <b-row v-if="user.role == 'VENDEDOR' || user.role === 'ADMIN'">
                                                    <b-col>
                                                        <b-button  to="productos"
                                                            variant="faded" ><i
                                                                class="fa fa-shopping-bag" aria-hidden="true"></i>
                                                            Administrar productos</b-button>
                                                    </b-col>
                                                    <b-col >
                                                        <b-button to="historialVentas" variant="faded"><i class="fa fa-credit-card"
                                                                aria-hidden="true"></i> Administrar ventas</b-button>
                                                    </b-col>
                                                    <b-col>
                                                        <b-button to="historialVentas" variant="faded"><i class="fa fa-truck"
                                                                aria-hidden="true"></i> Administrar envios</b-button>
                                                    </b-col>
                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>

                                    <!-- Info fiscal -->
                                    <br>
                                    <div v-if="user.role == 'VENDEDOR' || user.role === 'COMPRADOR'">
                                        <b-button v-b-toggle.collapseFiscal class="m-1" variant="faded">Datos vendedor
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapseFiscal">
                                            <b-card>
                                                <div v-if="user.role == 'COMPRADOR'">
                                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                                        <p class="mb-0 me-2"><b>Aún no eres un vendedor </b></p>
                                                    </div>
                                                    <div class="d-flex pb-4">
                                                        <p class="mb-0 me-2"><b>¿Quieres vender tus productos?
                                                                <b-link class="text-muted"
                                                                    href="enviarSolicitdVendedor">Enviar solicitud de
                                                                    vendedor</b-link></b>
                                                        </p>
                                                    </div>
                                                </div>
                                                <b-row class="mb-3" v-if="user.role == 'VENDEDOR'">
                                                    <b-col class="pr-2">
                                                        <label for=""><b>CURP</b></label>
                                                        <p>{{ user.vendedor.curp }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>RFC</b></label>
                                                        <p>{{ user.vendedor.rfc }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Teléfono</b></label>
                                                        <p>{{ user.vendedor.telefonoVendedor }}</p>
                                                    </b-col>
                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>
                                    <!-- Administrador -->
                                    <br>
                                    <div v-if="user.role == 'ADMIN'">
                                        <b-button v-b-toggle.collapsBitacoras class="m-1" variant="faded">
                                            Administrar
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapsBitacoras">
                                            <b-card>
                                                <b-row>
                                                    <b-col>
                                                        <b-button variant="fades" to="usuarios"><b-icon
                                                                icon="people"></b-icon> Lista de usuarios</b-button>
                                                    </b-col>
                                                    <b-col>
                                                        <b-button variant="fades" to="usuarios"><b-icon
                                                                icon="card-checklist"></b-icon> Bitacora de
                                                            usuarios</b-button>
                                                    </b-col>
                                                    <b-col>
                                                        <b-button variant="fades" to="usuarios"><b-icon
                                                                icon="shop"></b-icon> Administrar ventas</b-button>
                                                    </b-col>
                                                </b-row>
                                                <br>
                                                <b-row>
                                                    <b-col>
                                                        <b-button variant="fades" to="vendedores"><b-icon
                                                                icon="clock"></b-icon> Solicitudes de
                                                            vendedores</b-button>
                                                    </b-col>
                                                    <b-col>
                                                        <b-button variant="fades" to="productos"><b-icon
                                                                icon="hourglass-split"></b-icon> Solicitudes de
                                                            productos</b-button>
                                                    </b-col>
                                                    <b-col>
                                                        <b-button to="historialVentas" variant="faded"><i
                                                                class="fa fa-truck" aria-hidden="true"></i> Administrar
                                                            envios</b-button>
                                                    </b-col>
                                                </b-row>
                                                <br>
                                                <b-row>
                                                    <b-col>
                                                        <b-button variant="fades" to="categorias-screen"><b-icon
                                                                icon="bookmarks"></b-icon> Categorias y
                                                            subcategorias</b-button>
                                                    </b-col>

                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>

                                    <br>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <b-row>
                <b-col>

                </b-col>
                <b-col>

                </b-col>
            </b-row>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
import { jwtDecode } from "jwt-decode";
export default {
    name: "profile-screen",
    data() {
        return {
            user: {},
        }
    },

    methods: {
        logout() {
            Swal.fire({
                title: 'Cerrar sesión',
                text: '¿Estás seguro de que deseas cerrar la sesión?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#008c6f',
                cancelButtonColor: '#e11c24',
                confirmButtonText: 'Sí, cerrar sesión',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    localStorage.clear();
                    Swal.fire({
                        title: '¡Vuelve pronto!',
                        text: 'Has cerrado sesión correctamente.',
                        icon: 'success',
                        position: 'top-end', 
                        toast: true, 
                        showConfirmButton: false, 
                        timer: 3000 
                    });
                    this.$router.push({ name: 'login' });
                }
            });
        },
        async getInfo() {
            try {
                const token = localStorage.getItem('token');
                const decoded = jwtDecode(token);
                const email = decoded.sub;

                const response = await axios.post('http://localhost:8091/api-carsi-shop/usuario/getOne', { email }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                        'Content-Type': 'application/json'
                    }
                });

                this.user = response.data.data;
                console.log("Datos del usuario logueado", this.user);
            } catch (error) {
                console.error("Error al obtener la información del usuario", error);
                Swal.fire({
                    title: "Error",
                    text: "Tu sesion se a cerrado por seguridad, vuelve a iniciar sesion",
                    icon: "error"
                });
                this.$router.push({ name: 'login' });
            }
        }


    },
    mounted() {
        this.getInfo();
    }
}

</script>

<style>
.carta {
    margin-left: 10px;
    margin-right: 10px;
    margin-top: 10px;
    margin-bottom: 0px;
}

.userList {
    background-color: #F5F5F5;
    color: black;
}

.custom-container {
    max-width: 1200px;
    margin: 0 auto;
}
</style>