<template>
   <div >
    <Navbar class="fixed-top"/>
    <br>
    <br>
    <Submenu/>
    <router-view />
    <br>
    <!--
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
      <h3 class="wordsgames">
        ¡La tienda donde encontrarás las mejores ofertas sobre ruedas en moda y estilo!
      </h3>
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
    </div> -->
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