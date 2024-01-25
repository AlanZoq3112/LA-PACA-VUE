<template>
  <div>
    <Navbar class="fixed-top"/>
    <br>
    <br>
    <Submenu/>
    <router-view />
  </div>
</template>

<script>
import Navbar from './components/HeaderComponent/Navbar.vue';
import Submenu from './components/Submenu/Submenu.vue';
  export default {
    components: {Submenu, Navbar},
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