<template>
   <div >
    <Navbar class="fixed-top"/>
    <br>
    <br>
    <Submenu/>
    <router-view />
    <br>
    <br>
    <Footer ></Footer>
  </div>
</template>

<script>
import Navbar from './components/HeaderComponent/Navbar.vue';
import Submenu from './components/Submenu/Submenu.vue';
import Footer from './components/FooterComponent/Footer.vue';

  export default {
    data() {
    return {
      slides: [
        {
          src: "src/assets/2.png",
          caption: "",
        },
        {
          src: "src/assets/1.png",
          caption: "",
        },
      ],
    };
  },
    components: {Submenu, Navbar, Footer},
}
</script>

<style>
  .submenu {
    color: blue;
}

.ss{
    background-color: rgb(255, 255, 255);
}

.wordsgames{
  text-align: center;
  margin-top: 10px;
}
</style>