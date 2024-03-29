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
                                            <input v-model="vendedor.curp" type="text" id="curp" class="form-control"
                                                placeholder="CURP" />
                                        </b-col>
                                        <b-col>
                                            <label class="form-label" for="rfc">RFC: </label>
                                            <input v-model="vendedor.rfc" type="text" id="rfc" class="form-control"
                                                placeholder="RFC" />
                                        </b-col>
                                    </b-row>

                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Teléfono: </label>
                                        <input v-model="vendedor.telefonoVendedor" type="tel" id="telefono"
                                            class="form-control" placeholder="Teléfono" />
                                    </div>
                                    <div class="form-outline mb-4">
                                        <label class="form-label" for="telefono">Ine: </label>
                                        <input v-model="vendedor.ine" type="text" id="ine" class="form-control"
                                            placeholder="INE" />
                                    </div>

                                    <!-- <div class="form-outline mb-4">
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

                                    <div class="form-outline mb-4" v-if="vendedor.ine && vendedor.ine.length > 0">
                                        <h5>Vista previa de las imágenes:</h5>
                                        <div class="image-preview-container">
                                            <img v-for="(imagen, index) in vendedor.ine" :key="index" :src="imagen"
                                                alt="Vista previa de la imagen" class="image-preview" />
                                        </div>
                                    </div> -->


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
            vendedor: {
                telefonoVendedor: "",
                curp: "",
                rfc: "",
                ine: "",
            },
        };
    },
    methods: {
        async enviarSolicitud() {
            try {
                const result = await Swal.fire({
                    title: "¿Estás seguro de enviar la solicitud de vededor?",
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
                    console.log("Datos del vendedor: ", this.vendedor);
                    axios.post('http://localhost:8091/api-carsi-shop/vendedor/insert', this.vendedor, {
                            headers: { Authorization: `Bearer ${token}` }
                        })
                        .then(response => {
                            console.log(response.data);
                            Swal.fire('Enviada', 'Solicitud de vendedor enviada correctamente', 'success');
                            this.$router.push({ name: 'profile-screen' });
                        })
                        .catch(error => {
                            let errorMessage = "Hubo un problema al enviar la solicitud de vendedor";
                            if (error.response && error.response.data && error.response.data.length > 0) {
                                errorMessage = error.response.data[0]; // Utiliza el primer mensaje de error recibido del servidor
                            }
                            Swal.fire('Error', errorMessage, 'error');
                        });
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

        // handleImageUpload(event) {
        //     if (this.vendedor.ine.length >= 2) {
        //         Swal.fire('Error', 'No puedes agregar mas de 2 imagenes', 'error');
        //         return;
        //     }

        //     const files = event.target.files;

        //     // Itera sobre los archivos seleccionados
        //     for (let i = 0; i < files.length; i++) {
        //         const file = files[i];

        //         if (file) {
        //             // Convierte la imagen a una URL de datos para la vista previa
        //             const reader = new FileReader();
        //             reader.onload = () => {
        //                 // Agrega la imagen al arreglo de imágenes
        //                 this.vendedor.ine.push(reader.result);
        //             };
        //             reader.readAsDataURL(file);
        //         }
        //     }
        // },


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