<template>
  <div>
    <!--Carrusel de imagenes-->
    <div id="carousel-example">
      <b-carousel :interval="3000" controls indicators>
        <b-carousel-slide
          v-for="(slide, index) in slides"
          :key="index"
          :img-src="slide.src"
          :caption="slide.caption"
        ></b-carousel-slide>
      </b-carousel>
      <!--Carrusel de imagenes-->
    </div>
    <br />
    <div class="text-center">
      <b>Producto</b>
    </div>
    <br />
    <!--Card de Producto-->
    <div class="container">
      <div class="row">
        <div class="col-md-1">
          <div class="d-flex flex-column">
            <img
              v-for="(imagen, index) in imagenes"
              :src="imagen"
              :alt="imagen"
              class="img-fluid img-thumbnail mb-3 img-perpectives"
              @click="cambiarImagen(index)"
              :key="index"
            />
          </div>
        </div>
        <div class="col-md-6">
          <img
            :src="imagenPrincipal"
            alt="Imagen del producto"
            class="img-fluid img-producto ml-5"
          />
        </div>
        <div class="col-md-5">
          <b-card>
            <h5 class="card-title">Información del producto</h5>
            <h5>{{ nombreProducto }}</h5>
            <p>ID: {{ idProducto }}</p>
            <p>Descripción: {{ descripcionProducto }}</p>
            <div class="tallas">
              <button class="btn-talla">CH (S)</button>
              <button class="btn-talla">M (M)</button>
              <button class="btn-talla">G (L)</button>
              <button class="btn-talla">XG (XL)</button>
              <button class="btn-talla">XXG (XXL)</button>
            </div>
            <span @click="openModal" class="product-size-guide"
              >Guia de tallas</span
            >
            <br />
            <b-modal v-model="modalOpen" title="Guía de tallas" hide-footer>
              <img
                src="src/views/Nenes/Img/Captura de pantalla 2024-02-20 003609.png"
                alt="Guía de tallas"
                class="modal-image"
              />
            </b-modal>
            <p>Precio: {{ precioProducto }}</p>
            <b-button id="boton-pagar" class="btn btn-pagar" @click="pagar"
              >AÑADIR A LA BOLSA</b-button
            >
          </b-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  
  data() {
    return {
      modalOpen: false,
      nombreProducto: "Camisa cuadros blanco/negro",
      idProducto: "#13839482",
      descripcionProducto:
        "Camisa de cuadro blanco/negro, increible para este regreso a clases hecha de algodon e hilos de camello",
      precioProducto: "$293",
      imagenes: [
        "src/views/Nenes/Img/model8nino.jpg",
        "src/views/Nenes/Img/model11nino.jpg",
        "src/views/Nenes/Img/model14nino.jpg",
        "src/views/Nenes/Img/model9nino.jpg",
      ],
      imagenPrincipal: "src/views/Nenes/Img/model8nino.jpg",
      slides: [
        {
          src: "src/views/Nenes/Img/bannerniños.jpg",
          caption: "",
        },
        {
          src: "src/views/Nenes/Img/collage-tiro-completo-escolares-uniformes.jpg",
          caption: "",
        },
        {
          src: "src/views/Nenes/Img/collage-plano-medio-ninos.jpg",
          caption: "",
        },
      ],
    };
  },
  methods: {
    pagar() {
      // Lógica para procesar el pago
    },
    cambiarImagen(index) {
      this.imagenPrincipal = this.imagenes[index];
    },
    openModal() {
      this.modalOpen = true;
    },
  },
  name: "kid-producto",
};
</script>

<style scoped>
.modal-image {
  max-width: 100%;
  max-height: 100%;
}
.btn-talla {
  border: 1px solid #ccc;
  border-radius: 5%;
  padding: 10px;
  margin-right: 10px;
  background-color: transparent;
  color: #000;
  cursor: pointer;
}

.btn-talla:hover {
  background-color: transparent;
  border-color: #000;
}
.btn-pagar {
  background-color: rgb(34, 34, 34);
  color: white;
}

.product-size-guide {
  cursor: pointer;
  color: #2d68a8;
  font-size: 15px;
  line-height: 1;
}
.img-producto {
  width: 85%; /* Modifica el ancho de la imagen */
  height: auto; /* Ajusta la altura automáticamente */
}
.img-perpectives {
  cursor: pointer;
}
</style>