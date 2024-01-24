<template>
  <div>
    <!-- Navbar -->
    <b-navbar toggleable="lg" type="dark" variant="light">
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item to="inicio">Inicio</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-button variant="light"><b-icon icon="cart3"></b-icon></b-button>
            <b-button to="profile-screen" variant="light"><b-icon icon="person"></b-icon></b-button>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
   
    <div>
      <center>
        
          <b-button to="hombre-screen" variant="light">HOMBRE</b-button>
          <b-button to="mujer-screen" variant="light">MUJER</b-button>
          <b-button to="nenes-screen" variant="light">NIÑOS</b-button>
          <b-button to="ofertas-screen" variant="light">¡OFERTAS!</b-button>
      </center>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  methods: {
    name: "app",
    showAlert() {
      this.$swal("Hello Vue world!!!");
    },
  },
};
</script>

<style scoped>
.links {
  padding-left: 30px;
  padding-top: 10px;
}

.sidebare {
  padding-left: 5px;
  border: 1px solid #ccc;
  min-height: 100vh;
  box-sizing: border-box;
}
</style>
