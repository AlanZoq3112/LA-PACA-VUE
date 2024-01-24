<template>
  <div>
    <Navbar/>
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/HeaderComponent/Navbar.vue';
  export default {
    components: { Navbar }
}
</script>

<style>
  .submenu {
    color: blue;
}

.ss{
    background-color: rgb(255, 255, 255);
}
</style>