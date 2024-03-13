<template>
    <div>
        <div>
            <div class="custom-container py-1 h-200">
                <h4>Mi cuenta</h4>
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-12">
                        <div class="card rounded-3 text-black">
                            <div class="col-lg-12">
                                <div class="card-body p-md-5 mx-md-4">
                                    <b-row>
                                        <b-col cols="2">
                                            <img src="https://static-2.ivoox.com/canales/7/4/3/8/4001478748347_XXL.jpg"
                                                class="rounded-circle img-thumbnail" alt="Avatar"
                                                style="width: 150px; height: 150px;">
                                        </b-col>

                                        <b-col cols="10">
                                            <br>
                                            <br>
                                            <h4>{{ user.Nombre }} {{ user.Apellidos }}</h4>
                                            <h5>{{ user.Correo }}</h5>
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
                                                        <p>{{ user.Nombre }} {{ user.Apellidos }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Correo</b></label>
                                                        <p>{{ user.Correo }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Teléfono</b></label>
                                                        <p>{{ user.Telefono }}</p>
                                                    </b-col>
                                                </b-row>

                                                <b-row>
                                                    <b-col cols="8">
                                                        <label for=""><b>Dirección</b></label>
                                                        <p>{{ user.Direccion }}</p>
                                                    </b-col>
                                                    <b-col>
                                                        <br>
                                                        <b-button style="color: blue;" variant="faded">
                                                            <b-icon icon="pencil-square"></b-icon> Actualizar datos
                                                        </b-button>
                                                    </b-col>
                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>
                                    <br>
                                    <div>
                                        <b-button v-b-toggle.collapseFiscal class="m-1" variant="faded">Datos vendedor
                                            <b-icon icon="caret-right" style="height: 20px; width: 16px;"></b-icon>
                                        </b-button>
                                        <b-collapse id="collapseFiscal">
                                            <b-card>
                                                <b-row class="mb-3">
                                                    <b-col class="pr-2">
                                                        <label for=""><b>CURP</b></label>
                                                        <p>{{ user.datosFiscales.CURP }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>RFC</b></label>
                                                        <p>{{ user.datosFiscales.RFC }}</p>
                                                    </b-col>
                                                    <b-col class="pl-2">
                                                        <label for=""><b>Teléfono</b></label>
                                                        <p>{{ user.datosFiscales.Telefono }}</p>
                                                    </b-col>
                                                </b-row>
                                            </b-card>
                                        </b-collapse>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                        <p class="mb-0 me-2">¿Quieres vender tus productos?</p>
                                    </div>
                                    <div class="d-flex align-items-center justify-content-center pb-4">
                                        <b-link style="color:blue" class="text-muted"
                                            href="enviarSolicitdVendedor">Enviar solicitud de vendedor</b-link>
                                    </div>
                                    <br>
                                    <div>
                                        <b-button class="userList" to="usuarios"><b-icon icon="people"></b-icon> Lista
                                            de usuarios</b-button>
                                    </div>
                                    <br>
                                    <div>
                                        <b-button class="userList" to="usuarios"><b-icon icon="card-checklist"></b-icon>
                                            Bitacora de
                                            usuarios</b-button>
                                    </div>
                                    <br>
                                    <div>
                                        <b-button class="userList" to="usuarios"><b-icon icon="shop"></b-icon> Bitacora
                                            de
                                            ventas</b-button>
                                    </div>
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
export default {
    name: "profile-screen",
    data() {
        return {
            user: {
                Nombre: "Alan Matthew Esteban",
                Apellidos: "Dominguez Castañeda",
                Correo: "alanmat3112@gmail.com",
                Contraseña: "******",
                Telefono: "7775992016",
                Direccion: "20 de Noviembre, 204, Emiliano Zapata, Puente de Ixtla, Morelos",
                Fecha_de_nacimiento: "31/12/2003",
                Genero: "Masculino",
                Imagen_de_perfil: [],
                datosFiscales: {
                    Telefono: "7513440461",
                    Direccion: "Carretera Temixco, Lote 17 Bodega 1 D.I.E.Z., Col. Palo Escrito, 62760 Emiliano Zapata, Mor.",
                    CURP: "DOCA031231HMSMSLA4",
                    RFC: "DOCA0312312D8",
                }
            },
        }
    },
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
    /* Centra el contenedor en la pantalla */
}
</style>