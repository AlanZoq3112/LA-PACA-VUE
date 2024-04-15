<template>
    <div>
        <b-form @submit.prevent="onSubmit">
            <b-form-group>
                <!-- Styled -->
                <b-form-file v-model="v$.form.file.$model" placeholder="Selecciona o arrastra un archivo aquí..."
                    drop-placeholder="Suelta el archivo aquí..." browse-text="Buscar" accept="image/*" ref="fileInput"
                    :state="!v$.form.file.$dirty ? null : !v$.form.file.$error" @input="sendImage($event)"
                    multiple></b-form-file>
                <div class="image-preview-container">
                    <b-img v-for="(file, index) in form.file" :key="index" :src="getImageUrl(file)"
                        class="mb-3 image-preview" rounded></b-img>
                </div>
                <b-form-invalid-feedback v-for="error in v$.form.file.$errors" :key="error.$uid">
                    {{ error.$message }}
                </b-form-invalid-feedback>
            </b-form-group>
        </b-form>


    </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";
const base64Encode = data =>
    new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(data);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });
export default Vue.extend({
    name: "InputFilesMax2",
    setup() {
        return { v$: useVuelidate() };
    },
    methods: {
        getImageUrl(file) {
            if (file) {
                return URL.createObjectURL(file);
            }
            console.log(file)
            return null;
        },
        sendImage() {
            // Validar cantidad de imágenes seleccionadas
            const isValidAmount = this.form.file.length >= 2 && this.form.file.length <= 5;
            this.$emit("check", isValidAmount);
            this.$emit("img", this.form.file);

        },
    },
    data() {
        return {
            form: {
                file: [],
            },
            valid: false,
            imageSrc: null,
        };
    },
    validations: {
        form: {
            file: {
                required: helpers.withMessage(
                    "El campo es requerido",
                    required
                ),
                minLength: helpers.withMessage(
                    "Debes seleccionar al menos 2 imágenes",
                    value => value && value.length >= 2
                ),
                maxLength: helpers.withMessage(
                    "Solo puedes seleccionar un máximo de 2 imágenes",
                    value => value && value.length <= 2
                ),
                $each: {
                    type: helpers.withMessage(
                        "El campo solo acepta archivos de tipo imagen",
                        (value) => value.type.includes("image")
                    ),
                    size: helpers.withMessage(
                        "El campo solo acepta archivos con un tamaño menor a 2MB",
                        (value) => value.size < 2000000
                    ),
                    sizeMin: helpers.withMessage(
                        "El campo solo acepta archivos con un tamaño mayor a 100 bytes",
                        (value) => value.size > 100
                    ),
                    name: helpers.withMessage(
                        "El campo solo acepta archivos con un nombre que no contenga espacios en blanco",
                        (value) => !value.name.includes(" ")
                    ),
                    nameSpecial: helpers.withMessage(
                        "El campo solo acepta archivos con un nombre que no contenga caracteres especiales",
                        (value) => !value.name.match(/[!@#$%^&*(),?":{}|<>]/)
                    ),
                    image: helpers.withMessage(
                        "El campo solo acepta archivos de tipo imagen",
                        value => {
                            if (!value) return true
                            return value && value.type.includes("image")
                        }
                    ),
                },
            },
            imageDimensions: {
                width: {},
                height: {}
            }
        },
    },
});
</script>

<style scoped>
.image-preview-container {
    display: flex;
    flex-wrap: nowrap;
    /* Evita que las imágenes se desborden a la siguiente línea */
    justify-content: flex-start;
    /* Alinea las imágenes hacia el principio del contenedor */
    gap: 10px;
    /* Espacio entre las imágenes */
}

.image-preview {
    width: 50px;
    height: 50px;
    object-fit: fill;
}
</style>