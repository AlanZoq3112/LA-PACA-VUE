<template>
    <div>

        <label for="name">Nombre:</label>
        <b-form-input id="name" type="text" placeholder="Nombre" required v-model="v$.form.name.$model"
            @blur="v$.form.name.$touch()" :state="v$.form.name.$dirty ? !v$.form.name.$error : null" trim
            @input="sendName" />
        <b-form-invalid-feedback v-for="error in v$.form.name.$errors" :key="error.$uid">
            {{ error.$message }}
        </b-form-invalid-feedback>


    </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, maxLength, minLength } from "@vuelidate/validators";

export default Vue.extend({
    name: "InputText",
    setup() {
        return { v$: useVuelidate() };
    },
    methods: {
        sendName() {
            const isValid = this.v$.form.$invalid; 
            if (!isValid) {
                this.$emit('check', true);
                this.$emit('name', this.form.name);
            } else {
                this.$emit('check', false);
                this.$emit('name', this.form.name);
            }
        },

    },
    data() {
        return {
            form: {
                name: "",
                valid: false
            },
        };
    },
    validations() {
        return {
            form: {
                name: {
                    required: helpers.withMessage(
                        "El campo es requerido",
                        required
                    ),
                    valid: helpers.withMessage(
                        'El campo no es válido, solo se permiten letras y los caracteres especiales: - _',
                        helpers.regex(/^[a-zA-Z ÁÉÍÓÚáéíóúñÑäëïöü\-_ \s]+$/)
                    ),
                    maxLength: helpers.withMessage(
                        "El campo no puede tener más de 35 caracteres",
                        maxLength(35)
                    ),
                    minLength: helpers.withMessage(
                        "El campo no puede tener menos de 10 caracteres",
                        minLength(10)
                    ),
                },
            }
        }
    }
})
    ;
</script>
