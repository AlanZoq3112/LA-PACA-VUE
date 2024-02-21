
<template>
    <footer>
      <b-container class="text-center">
        <b-row>
          <b-col>
            <!-- Contenido de tu footer -->
            <p>Todos los derechos reservados &copy; {{ currentYear }}</p>
          </b-col>
        </b-row>
      </b-container>
    </footer>
  </template>
  
  <script>
  
  export default {
    name: "Footer",
    data() {
      return {
        currentYear: new Date().getFullYear()
      }
    }
  }
  </script>
  
  <style scoped>
  footer {
  color:white;
    background-color: #181818;
    padding: 20px 0;
  }
  </style>