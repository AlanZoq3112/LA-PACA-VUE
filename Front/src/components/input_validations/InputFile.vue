<template>
    <div>
        <b-form @submit.prevent="onSubmit">
            <b-form-group label="Imagen perfil:">
                <!-- Styled -->
                <b-form-file v-model="v$.form.file.$model" placeholder="Selecciona o arrastra un archivo aquí..."
                    drop-placeholder="Suelta el archivo aquí..." browse-text="Buscar" accept="image/*" ref="fileInput"
                    :file-name-formatter="(files) => `${files[0].name} - ${files[0].size} bytes`"
                    :state="!v$.form.file.$dirty ? null : !v$.form.file.$error" @input="sendImage"></b-form-file>
                <div class="image-preview-container">
                    <b-img :src="imageSrc" class="mb-3 image-preview" rounded></b-img>
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
    name: "InputEmail",
    setup() {
        return { v$: useVuelidate() };
    },
    methods:{
        sendImage() {            
            this.$emit('img', this.imageSrc);            
        },
    },
    watch: {
        "form.file": function (newValue, oldValue) {
            if (newValue !== oldValue) {
                if (newValue) {
                    const file = newValue;
                    const img = new Image();
                    img.src = URL.createObjectURL(file);                    


                    base64Encode(newValue).then((data) => {
                        this.imageSrc = data;
                    }).catch((error) => {
                        this.imageSrc = null;
                    });
                } else this.imageSrc = null;
            }
        },
    },
    data() {
        return {
            form: {
                file: null,
            },
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
                image: helpers.withMessage(
                    "El campo solo acepta archivos de tipo imagen",
                    value => {
                        if (!value) return true
                        return value && value.type.includes("image")
                    }
                ),
                size: helpers.withMessage(
                    "El campo solo acepta archivos con un tamaño menor a 2MB",
                    value => {
                        if (!value) return true
                        return value && value.size < 2000000
                    }
                ),
                sizeMin: helpers.withMessage(
                    "El campo solo acepta archivos con un tamaño mayor a 100bytes",
                    value => {
                        if (!value) return true
                        return value && value.size > 100
                    }
                ),
                name: helpers.withMessage(
                    "El campo solo acepta archivos con un nombre de archivo que no contenga espacios en blanco",
                    value => {
                        if (!value) return true
                        return value && !value.name.includes(" ")
                    }
                ),
                nameSpecial: helpers.withMessage(
                    "El campo solo acepta archivos con un nombre de archivo que no contenga caracteres especiales",
                    value => {
                        if (!value) return true
                        return value && !value.name.match(/[!@#$%^&*(),?":{}|<>]/)
                    }
                ),
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
    width: 200px;
    height: 200px;
    object-fit: fill;
}</style>