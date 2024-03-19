<template>
    <div>
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-xl-12">
                    <div class="card rounded-3 text-black">
                        <div class="col-lg-12">
                            <div class="card-body p-md-5 mx-md-4">
                                <div class="text-center">
                                    <img src="https://4.bp.blogspot.com/-IXeMrjpLYvY/Ufe21ZaAbLI/AAAAAAAAADU/Sm3ZieV49bM/s1600/vendedor.png"
                                        style="width: 100px;" alt="logo" />
                                    <h2 class="mt-1 mb-5 pb-1">Solicitud de vendedor</h2>
                                    <h5 class="mt-1 mb-5 pb-1">Para poder ser parte de los vendedores de <b>Carsi
                                            Shop</b> debes proporcionar los siguientes datos</h5>
                                </div>

                                <form>
                                    <b-row>
                                        <b-col>
                                            <label class="form-label" for="curp">CURP: </label>
                                            <input v-model="user.CURP" type="text" id="curp" class="form-control"
                                                placeholder="CURP" />
                                        </b-col>
                                        <b-col>
                                            <label class="form-label" for="rfc">RFC: </label>
                                            <input v-model="user.RFC" type="text" id="rfc" class="form-control"
                                                placeholder="RFC" />
                                        </b-col>
                                    </b-row>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="direccion">Dirección: </label>
                                        <input v-model="user.Direccion" type="text" id="direccion" class="form-control"
                                            placeholder="Calle, número, colonia, ciudad, estado" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Teléfono: </label>
                                        <input v-model="user.Telefono" type="tel" id="telefono" class="form-control"
                                            placeholder="Teléfono" />
                                    </div>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="imagen">Identificación oficial (INE o IFE) por
                                            ambos lados:</label>
                                        <div class="input-group">
                                            <input type="file" id="imagen" @change="handleImageUpload" accept="image/*"
                                                multiple style="display: none;" />
                                            <label class="input-group-text" for="imagen">
                                                <i class="fas fa-cloud-upload-alt"></i>
                                            </label>
                                        </div>
                                    </div>

                                    <div class="form-outline mb-4" v-if="user.imagenes && user.imagenes.length > 0">
                                        <h5>Vista previa de las imágenes:</h5>
                                        <div class="image-preview-container">
                                            <img v-for="(imagen, index) in user.imagenes" :key="index" :src="imagen"
                                                alt="Vista previa de la imagen" class="image-preview" />
                                        </div>
                                    </div>


                                    <div class="text-center pt-1 mb-5 pb-1">
                                        <button class="btn btn-primary btn-block fa-lg gradient-custom-2 mb-3"
                                            @click="enviarSolicitud()" type="button">
                                            Enviar Solicitud <i class="fa fa-paper-plane" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import Swal from 'sweetalert2';
export default {
    name: "enviarSolicitdVendedor",
    data() {
        return {
            user: {
                Telefono: "",
                Direccion: "",
                CURP: "",
                RFC: "",
                imagenes: [],
            },
        };
    },
    methods: {
        enviarSolicitud() {
            console.log(this.user);
            Swal.fire('Enviada', 'Tu solicitud fue enviada correctamente', 'success');
            this.$router.push({ name: 'profile-screen' });
        },

        handleImageUpload(event) {
            if (this.user.imagenes.length >= 2) {
                Swal.fire('Error', 'No puedes agregar mas de 2 imagenes', 'error');
                return;
            }

            const files = event.target.files;

            // Itera sobre los archivos seleccionados
            for (let i = 0; i < files.length; i++) {
                const file = files[i];

                if (file) {
                    // Convierte la imagen a una URL de datos para la vista previa
                    const reader = new FileReader();
                    reader.onload = () => {
                        // Agrega la imagen al arreglo de imágenes
                        this.user.imagenes.push(reader.result);
                    };
                    reader.readAsDataURL(file);
                }
            }
        },


    }
};
</script>

<style scoped>
.input-group {
    position: relative;
    width: 100%;
}

.input-group input[type="file"] {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}

.input-group label {
    cursor: pointer;
}

.image-preview-container {
    display: flex;
    flex-wrap: nowrap;
    justify-content: flex-start;
    gap: 10px;
}

.image-preview {
    max-width: 200px;
}
</style>