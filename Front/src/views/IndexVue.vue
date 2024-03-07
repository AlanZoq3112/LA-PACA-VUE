<template>
    <div>
        <header-component/>
        <div class="main-content">
            <router-view/>
        </div>
    </div>
</template>


<script lang="ts">
import Vue from "vue"
import HeaderComponent from './../components/HeaderComponent/Navbar.vue'

export default Vue.extend({
    name: "IndexVue",
    components : { HeaderComponent },
    computed :{
        currentRoutePath(){
            return this.$route.path
        }
    }

})
</script>