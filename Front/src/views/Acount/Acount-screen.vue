<template>
    <div>
        <div>
            <b-row>
                <b-col>
                    <div class="carta"><h4>Información personal</h4></div>
                    <b-card class="carta"> 
                        <div class="text-center"><b-avatar></b-avatar></div>
                        <div class="carta">
                            <b-row>
                                <b-col>
                                    <b-form-group label="Nombre">
                                        <b-form-input type="text" placeholder="Nombre" disabled></b-form-input>
                                    </b-form-group>
                                </b-col>
                                <b-col>
                                    <b-form-group label="Apellido">
                                        <b-form-input type="text" placeholder="Apellido" disabled></b-form-input>
                                    </b-form-group>
                                </b-col>
                            </b-row>
                            <b-row>
                                <b-col>
                                    <b-form-group label="Email">
                                        <b-form-input type="email" placeholder="email@gmail.com" disabled></b-form-input>
                                    </b-form-group>
                                </b-col>
                                <b-col>
                                    <b-form-group label="Telefono">
                                        <b-form-input type="number" placeholder="77711122333" disabled></b-form-input>
                                    </b-form-group>
                                </b-col>
                            </b-row>
                        </div>
                    </b-card>
                </b-col>
                <b-col>
                    <div class="carta"><h4>Adminstración de usuarios</h4></div>
                    <div>
                        <b-button class="userList" to="usuarios"><b-icon icon="people"></b-icon> Lista de usuarios</b-button>
                    </div>
                    <br>
                    <div>
                        <b-button class="userList" to="usuarios"><b-icon icon="card-checklist"></b-icon> Bitacora de usuarios</b-button>
                    </div>
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
.carta{
    margin-left: 20px;
    margin-right: 20px;
    margin-top: 20px;
    margin-bottom: 20px;
}

.userList{
    background-color:#f5f5f5;
    color: black;
}

</style>