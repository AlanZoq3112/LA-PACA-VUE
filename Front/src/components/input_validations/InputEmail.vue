<template>
    <div>
        <b-form>
            <b-form-group label="Correo electrónico" label-for="email">
                <b-form-input id="email" type="text" placeholder="Correo electrónico" required
                    v-model="v$.form.name.$model" @blur="v$.form.name.$touch()"
                    :state="v$.form.name.$dirty ? !v$.form.name.$error : null" :formatter="value => value.toLowerCase()"
                    trim @input="sendEmail" />

                <b-form-invalid-feedback v-for="error in v$.form.name.$errors" :key="error.$uid">
                    {{ error.$message }}
                </b-form-invalid-feedback>
            </b-form-group>
        </b-form>
    </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { required,helpers, email } from "@vuelidate/validators";

export default Vue.extend({
    props: {
        emailInput: String // Prop para recibir el valor del input
    },
    name: "InputEmail",
    setup() {
        return { v$: useVuelidate() };
    },
    data() {
        return {
            form: {
                name: "",
            },
            valid: false,
        };
    },
    methods: {
        sendEmail() {
            const isValid = this.v$.form.$invalid;
            if (!isValid) {
                this.$emit('check', true);
                this.$emit('email', this.form.name);
            } else {
                this.$emit('check', false);
                this.$emit('email', this.form.name);
            }            
        },
    },
    validations: {
        form: {
            name: {
                required: helpers.withMessage(
                    "El campo es requerido",
                    required
                ),
                valid: helpers.withMessage(
                    "El formato del correo no es válido",
                    email
                ),
                whiteListDomains: helpers.withMessage(
                    "El dominio no es válido",
                    value => {
                        if (!value.includes("@")) return true
                        return ["gmail.com", "hotmail.com", "yahoo.com", "utez.edu.mx"].includes(value.split("@")[1])
                    }
                ),
                validCharacters: helpers.withMessage(
                    "El campo no es válido, solo se permiten letras y los caracteres especiales [ @./*-?']",
                    value => {
                        if (!value) return true
                        return /^[a-zA-Z0-9@./*-_]+$/.test(value)
                    }
                ),
            },
        },
    },
});
</script>
    