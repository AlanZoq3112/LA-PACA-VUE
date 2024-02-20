<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <img src="./../assets/not-found.jpg" height="400px" />
      <p class="not-found-message">¡La pagina que estas buscando no existe!</p>
      <router-link to="/inicio" class="not-found-link"
        >Vuelve a la pagina principal</router-link
      >
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.not-found-container {
  background-color: #f7f7f7;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.not-found-content {
  text-align: center;
}

.not-found-title {
  font-size: 3rem;
  color: #e74c3c;
  margin-bottom: 1rem;
}

.not-found-message {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 2rem;
}

.not-found-link {
  display: inline-block;
  padding: 0.5rem 1rem;
  background-color: #008c6f;
  color: #fff;
  text-decoration: none;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.not-found-link:hover {
  background-color: #2980b9;
}
</style>
</style>