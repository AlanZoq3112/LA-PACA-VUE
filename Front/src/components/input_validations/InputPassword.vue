<template>
    <div>
        <b-form @submit.prevent="onSubmit">
            <b-form-group>
                <label for="name">Nueva contraseña:</label>
                <div class="input-group">
                    <b-form-input id="name" type="password" placeholder="*********" required
                    v-model="v$.form.password.$model" @blur="v$.form.password.$touch()"
                    :state="v$.form.password.$dirty ? !v$.form.password.$error : null" @input="sendEmail" :type="showPassword ? 'text' : 'password'"/>
                    <button class="btn btn-outline-secondary" type="button" @click="togglePasswordVisibility">
                        <i :class="['fas', showPassword ? 'fa-eye-slash' : 'fa-eye']"></i>
                    </button>
                    <b-form-invalid-feedback v-for="error in v$.form.password.$errors" :key="error.$uid">
                    {{ error.$message }}
                </b-form-invalid-feedback>
                </div>                                


            </b-form-group>
            <b-form-group>
                <label for="name">Confirma contraseña:</label>
                <div class="input-group">
                    <b-form-input id="name" type="password" placeholder="*********" required
                        v-model="v$.form.confirmPassword.$model" @blur="v$.form.confirmPassword.$touch()"
                        :state="v$.form.confirmPassword.$dirty ? !v$.form.confirmPassword.$error : null" trim
                        @input="sendEmail" :type="showPasswordConfirm ? 'text' : 'password'"/>
                    <button class="btn btn-outline-secondary" type="button" @click="togglePasswordVisibilityConfirm">
                        <i :class="['fas', showPasswordConfirm ? 'fa-eye-slash' : 'fa-eye']"></i>
                    </button>
                    <b-form-invalid-feedback v-for="error in v$.form.confirmPassword.$errors" :key="error.$uid">
                    {{ error.$message }}
                </b-form-invalid-feedback>
                </div>                
            </b-form-group>

        </b-form>



    </div>
</template>

<script>
import Vue from "vue";
import { useVuelidate } from "@vuelidate/core";
import { helpers, maxLength, minLength } from "@vuelidate/validators";
export default Vue.extend({
    name: "InputText",
    components: {
        Information: () => import('@/components/Information.vue'),
    },
    setup() {
        return { v$: useVuelidate() };
    },
    data() {
        return {
            form: {
                password: "",
                confirmPassword: "",
            },
            valid: "",
            showPasswordConfirm: false,
            showPassword: false,
        };
    },
    methods: {
        sendEmail() {
            const isValid = this.v$.form.$invalid;
            this.valid = isValid;
            this.$emit('check', !this.valid);
            this.$emit('contra', this.form.password);
        },
        togglePasswordVisibility() {
            this.showPassword = !this.showPassword;
        },
        togglePasswordVisibilityConfirm() {
            this.showPasswordConfirm = !this.showPasswordConfirm;
        }
    },
    validations: {
        form: {
            password: {
                required: helpers.withMessage(
                    "El campo es requerido",
                    (value) => {
                        return value.length > 0;
                    }
                ),
                minLength: helpers.withMessage(
                    "El campo debe tener mas de 8 caracteres",
                    minLength(8)
                ),
                maxLength: helpers.withMessage(
                    "El campo no puede tener más de 35 caracteres",
                    maxLength(35)
                ),
            },
            confirmPassword: {
                checkPassword: helpers.withMessage(
                    "Las contraseñas no coinciden",
                    function (value, { password }) {
                        if (!value) return true;
                        return value === password;
                    }
                ),
            },
        },
    },
});
</script>
