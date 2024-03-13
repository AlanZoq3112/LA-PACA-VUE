<template>
    <div>
        <div>
            <div class="text-center">
                <h4>Mi cuenta</h4>
            </div>
            <div class="custom-container py-1 h-200">
                <div class="row d-flex justify-content-center align-items-center h-100">
                    <div class="col-xl-12">
                        <div class="card rounded-3 text-black">
                            <div class="col-lg-12">
                                <div class="card-body p-md-5 mx-md-4">

                                    <div class="text-center">
                                        <img src="https://static-2.ivoox.com/canales/7/4/3/8/4001478748347_XXL.jpg" class="rounded-circle img-thumbnail" alt="Avatar" style="width: 150px; height: 150px;">
                                    </div>
                                    <div class="carta">
                                        <b-row>
                                            <b-col>
                                                <b-form-group label="Nombre">
                                                    <b-form-input type="text" placeholder="Nombre"
                                                        disabled></b-form-input>
                                                </b-form-group>
                                            </b-col>
                                            <b-col>
                                                <b-form-group label="Apellido">
                                                    <b-form-input type="text" placeholder="Apellido"
                                                        disabled></b-form-input>
                                                </b-form-group>
                                            </b-col>
                                        </b-row>
                                        <b-row>
                                            <b-col>
                                                <b-form-group label="Email">
                                                    <b-form-input type="email" placeholder="email@gmail.com"
                                                        disabled></b-form-input>
                                                </b-form-group>
                                            </b-col>
                                            <b-col>
                                                <b-form-group label="Telefono">
                                                    <b-form-input type="number" placeholder="77711122333"
                                                        disabled></b-form-input>
                                                </b-form-group>
                                            </b-col>
                                        </b-row>
                                        <div class="d-flex align-items-center justify-content-center pb-4">
                                            <p class="mb-0 me-2">¿Quieres vender tus productos?</p>
                                        </div>
                                        <div class="d-flex align-items-center justify-content-center pb-4">
                                            <b-link style="color:blue" class="text-muted"
                                                href="enviarSolicitdVendedor">Enviar solicitud de vendedor</b-link>
                                        </div>
                                    </div>
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
                                    <div class="carta">
                                        <h4>Adminstración de usuarios</h4>
                                    </div>
                                    <div>
                                        <b-button class="userList" to="usuarios"><b-icon icon="people"></b-icon> Lista
                                            de
                                            usuarios</b-button>
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