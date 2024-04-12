<template>
    <div>
        <b-form-input id="name" type="text" required v-model="v$.form.name.$model" @blur="v$.form.name.$touch()"
            :state="v$.form.name.$dirty ? !v$.form.name.$error : null" trim @input="sendName" />
        <b-form-invalid-feedback v-for="error in v$.form.name.$errors" :key="error.$uid">
            {{ error.$message }}
        </b-form-invalid-feedback>
    </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, maxLength, } from "@vuelidate/validators";

export default Vue.extend({
    name: "InputCodigo",
    props: {
        numMax: Number,
        dato: String
    },
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
    mounted() {
        this.form.name = this.dato;
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
                        'Solo se permiten letras y números',
                        helpers.regex(/^[a-zA-Z0-9]+$/)
                    ),
                    maxLength: helpers.withMessage(
                        () => `El campo no puede tener más de ${this.numMax} caracteres`,
                        maxLength(this.numMax)
                    ),
                },
            }
        }
    }
})
    ;
</script>

<style></style>