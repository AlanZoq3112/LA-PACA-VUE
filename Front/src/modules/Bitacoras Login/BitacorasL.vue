<template>
  <div>
    <br />
    <h4>Bitacora Inicio de Sesión <b-icon icon="book"></b-icon></h4>
    <b-card class="shadow" style="display: flex; justify-content: center; align-items: center">
      <b-row>
        <b-col cols="3">
          <b-form-group label="Tabla:" label-for="tabla">
            <b-form-input placeholder="Nombre de la tabla" :type="'search'" />
          </b-form-group>
        </b-col>
        <b-col cols="3">
          <b-form-group label="Operación:" label-for="operacion">
            <b-form-input  placeholder="Tipo de operación" :type="'search'" />
          </b-form-group>
        </b-col>
        <b-col cols="3">
          <b-form-group label="De:" label-for="fecha1">
            <b-form-datepicker id="fecha1" ></b-form-datepicker>
          </b-form-group>
        </b-col>
        <b-col cols="3">
          <b-form-group label="Hasta:" label-for="fecha2">
            <b-form-datepicker id="fecha2" ></b-form-datepicker>
          </b-form-group>
        </b-col>

        <b-col class="mt-3">
          <b-row class="d-flex flex-row-reverse">
            <b-button @click="searchMovies" variant="primary" class="mr-3 my-2">
              <b-icon icon="search"></b-icon>
              Buscar
            </b-button>
          </b-row>
        </b-col>
      </b-row>
    </b-card>
    <br />
    <b-card class="shadow">
      <b-table :items="pagedLogs" striped hover :per-page="10">
        <template #thead="{ fields }">
          <tr>
            <th v-for="field in fields" :key="field.key">{{ field.label }}</th>
          </tr>
        </template>
        <template #tbody="{ items }">
          <tr v-for="log in items" :key="log.id">
            <td>{{ log.id }}</td>
            <td>{{ log.descripcion }}</td>
            <td>{{ log.fecha_hora }}</td>
            <td>{{ log.usuario_id }}</td>
            <td>{{ log.operacion }}</td>
            <td>{{ log.tabla }}</td>
          </tr>
        </template>
      </b-table>
      <b-pagination v-model="currentPage" :total-rows="logs.length" :per-page="10" class="my-4" />
    </b-card>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      logs: [],
      currentPage: 1,
    };
  },
  created() {
    this.fetchLogs();
  },
  computed: {
    pagedLogs() {
      const startIndex = (this.currentPage - 1) * 10;
      const endIndex = startIndex + 10;
      return this.logs.slice(startIndex, endIndex);
    },
  },
  methods: {
    async fetchLogs() {
      try {
        const response = await axios.get('http://localhost:8091/api-carsi-shop/loglogin/getAllLogslogin');
        this.logs = response.data;
      } catch (error) {
        console.error("Error al obtener los logs:", error);
        throw error;
      }
    },
  },
};
</script>

<style>
.shadow {
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
