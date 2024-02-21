<template>
  <div>
    <div class="text-center">
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
      </div>
      <br />
      <b>Top Ventas</b>
    </div>

    <br />

    <!--Card de Ropa-->

    <div>
      <b-card-group deck>
        <b-card
          title="Conjunto verano Niña"
          img-src="src/views/Nenes/Img/model8nino.jpg"
          img-alt="Image"
          img-height="450px"
          max-width="200px"
          img-top
        >
          <b-card-text>Conjunto de verano escolar niña</b-card-text>
          <template #footer>
            <b-row>
              <b-col>Precio: $240</b-col>
              <b-col>
                <div class="d-flex justify-content-end">
                  <b-button
                    v-b-tooltip.hover="'Agregar al carrito'"
                    class="boton"
                    to="kid-producto"
                    variant="faded"
                  >
                    <b-icon icon="cart-plus"></b-icon>
                  </b-button>
                </div>
              </b-col>
            </b-row>
          </template>
        </b-card>

        <b-card
          title="Conjunto Verano Niña v2"
          img-src="src/views/Nenes/Img/model11nino.jpg"
          img-alt="Image"
          img-height="450px"
          img-top
        >
          <b-card-text> Conjunto de verano escolar niña v2 </b-card-text>
          <template #footer>
            <b-row>
              <b-col> Precio: $284 </b-col>
              <b-col>
                <div class="d-flex justify-content-end">
                  <b-button
                    v-b-tooltip.hover="'Agregar al carrito'"
                    class="boton"
                    to=""
                    variant="faded"
                  >
                    <b-icon icon="cart-plus"></b-icon>
                  </b-button>
                </div>
              </b-col>
            </b-row>
          </template>
        </b-card>

        <b-card
          title="Conjunto Verano Niño"
          img-src="src/views/Nenes/Img/model14nino.jpg"
          img-alt="Image"
          img-height="450px"
          img-top
        >
          <b-card-text> Conjunto Verano Niño </b-card-text>
          <template #footer>
            <b-row>
              <b-col> Precio: $567 </b-col>
              <b-col>
                <div class="d-flex justify-content-end">
                  <b-button
                    v-b-tooltip.hover="'Agregar al carrito'"
                    class="boton"
                    to=""
                    variant="faded"
                  >
                    <b-icon icon="cart-plus"></b-icon>
                  </b-button>
                </div>
              </b-col>
            </b-row>
          </template>
        </b-card>

        <b-card
          title="Conjunto Verano niño v2"
          img-src="src/views/Nenes/Img/model9nino.jpg"
          img-alt="Image"
          img-height="450px"
          img-top
        >
          <b-card-text> Conjunto Verano escolar niño v2 </b-card-text>
          <template #footer>
            <b-row>
              <b-col> Precio: $568 </b-col>
              <b-col>
                <div class="d-flex justify-content-end">
                  <b-button
                    v-b-tooltip.hover="'Agregar al carrito'"
                    class="boton"
                    to=""
                    variant="faded"
                  >
                    <b-icon icon="cart-plus"></b-icon>
                  </b-button>
                </div>
              </b-col>
            </b-row>
          </template>
        </b-card>

      </b-card-group>
    </div>

  </div>
</template>

<script>
export default {
  name: "nenes-screen",
  data() {
    return {
      imageSrc: 'src/views/Nenes/Img/model8nino.jpg',
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
    changeImage(newSrc) {
      this.imageSrc = newSrc;
    },
  },
};
</script>

<style>
.custom-image {
  width: 300px;
  height: 400px;
}
.submenu {
  color: blue;
}

.ss {
  background-color: rgb(255, 255, 255);
  margin-bottom: 5px;
}
</style>