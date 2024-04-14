<template>
    <div>
        <b-container>
            <div class="sec-1 text-center" :class="{
                active:
                    activeSection === 1 ||
                    activeSection === 2 ||
                    activeSection === 3 ||
                    activeSection === 4,
            }">
                <h1 class="lp-main-title fw-bold animate__animated animate__fadeInUp">
                    ARLINDO
                </h1>
                <h3 class="lp-second-title fw-bold animate__animated animate__fadeInUp">
                    Buffet
                </h3>

                <p class="subtitle-1">
                    Reserva tu evento con nosotros y disfruta de la mejor experiencia
                </p>
                <p class="subtitle-2">Explora nuestros paquetes</p>

                <img :src="principalImg" alt="principal" class="image-principal" />

                <b-icon-arrow-down-circle-fill class="icon-arrow" />
            </div>

            <div class="sec-2" :class="{
                active:
                    activeSection === 2 || activeSection === 3 || activeSection === 4,
            }">
                <b-row class="mt-5">
                    <h1 class="lp-subtitle lp-subtitle-size fw-bold my-5" v-if="showTitlePackages">
                        Paquetes
                    </h1>
                    <b-col v-for="(pkg, id) in productos" :key="id" cols="12" sm="12" md="4" class="mb-5">
                        <Card :packet="pkg" />
                    </b-col>
                    <b-button block class="m-1 mb-5 r-button" to="/paquetes" v-if="showMore">Ver más</b-button>
                </b-row>
            </div>
            <div class="sec-3" :class="{ active: activeSection === 3 || activeSection === 4 }">
                <b-row>
                    <b-col cols="12" sm="12" md="6" class="mb-5">
                        <b-card>
                            <h1 class="mt-4 mb-4 title-cards">Calidad</h1>
                            <span class="lp-line"></span>

                            <p class="m-5 text-body-cards">
                                Nos destacamos en la creación de eventos, donde cada detalle se
                                adapta a sus necesidades y supera sus expectativas.
                            </p>
                        </b-card>
                    </b-col>
                    <b-col cols="12" sm="12" md="6" class="mb-5">
                        <b-card class="lp-img-container">
                            <div class="lp-image-quality"></div>
                        </b-card>
                    </b-col>
                </b-row>
            </div>
            <div class="sec-3" :class="{ active: activeSection === 3 || activeSection === 4 }">
                <b-row>
                    <b-col cols="12" sm="12" md="6" class="mb-5">
                        <b-card class="lp-img-container">
                            <div class="lp-image-personalization"></div>
                        </b-card>
                    </b-col>
                    <b-col cols="12" sm="12" md="6" class="mb-5">
                        <b-card>
                            <h1 class="mt-4 mb-4">Personalización</h1>
                            <span class="lp-line"></span>
                            <p class="m-5 text-body-cards">
                                Realizamos un paquetes con todos los servicios y amenidades
                                incluidas de acuerdo a tus gustos y necesidades.
                            </p>
                        </b-card>
                    </b-col>
                </b-row>
            </div>
            <div class="sec-4" :class="{ active: activeSection === 4 }">
                <b-row class="mt-5">
                    <h1 class="my-5 lp-subtitle fw-bold lp-subtitle-size">
                        Nuestros eventos
                    </h1>
                    <b-col v-for="(event, id) in eventImages" :key="id" cols="12" sm="6" md="3" class="mb-5">
                        <div>
                            <b-img :src="event.image" fluid rounded :alt="event.alt"></b-img>
                        </div>
                    </b-col>
                </b-row>
            </div>
            <Loading v-if="showLoading" />
        </b-container>
    </div>
</template>

<script>
import event1 from "./../../../assets/1.png";
import event2 from "./../../../assets/2.png";
import event3 from "./../../../assets/amongus.png";
import event4 from "./../../../assets/Candado.png";
import principal from "./../../../assets/Logo.png";

export default {
    name: "men-accesorios",
    components: {
        Card,
        Loading,
    },
    data() {
        return {
            showMore: false,
            showTitlePackages: false,
            showLoading: true,
            activeSection: 1,
            principalImg: principal,
            eventImages: [
                {
                    image: event1,
                    alt: "Nuestros-eventos-1",
                },
                {
                    image: event2,
                    alt: "Nuestros-eventos-2",
                },
                {
                    image: event3,
                    alt: "Nuestros-eventos-3",
                },
                {
                    image: event4,
                    alt: "Nuestros-eventos-4",
                },
            ],
            productos: [],
        };
    },
    mounted() {
        this.getProductos();
        window.addEventListener("scroll", this.handleScroll);
    },
    methods: {
        async getProductos() {
            try {
                const token = localStorage.getItem("token");
                const response = await axios.get(
                    "http://localhost:8091/api-carsi-shop/producto/productos-aprobados",
                    {
                        headers: {
                            Authorization: `Bearer ${token}`,
                        },
                    }
                );
                // Filtrar los productos por subcategoría para hombres
                this.productos = response.data.data.filter(producto => {
                    return producto.estado === 3 && producto.stock > 0;;
                });
            } catch (error) {
                console.error("Error al obtener los datos del usuario", error);
            }
        },
        handleScroll() {
            const sections = document.querySelectorAll("[class^='sec-']");
            let scrollPosition = window.scrollY + window.innerHeight / 4;
            sections.forEach((section, index) => {
                if (
                    scrollPosition >= section.offsetTop &&
                    scrollPosition < section.offsetTop + section.offsetHeight
                ) {
                    if (index + 1 !== 5) {
                        this.activeSection = index + 1;
                    }
                }
            });
        },
    },
    destroyed() {
        window.removeEventListener("scroll", this.handleScroll);
    },
};
</script>

<style>
.sec-1,
.sec-2,
.sec-3,
.sec-4,
.sec-5 {
    transform: translateX(-100%);
    opacity: 0;
    transition: opacity 1s ease-out, transform 1s ease-out;
}

.sec-1.active,
.sec-2.active,
.sec-3.active,
.sec-4.active,
.sec-5.active {
    transform: translateX(0);
    opacity: 1;
}

.lp-main-title {
    font-size: 5rem;
    margin-top: 1.5rem;
    color: #ed8003;
}

.lp-second-title {
    font-size: 3rem;
    margin-top: 1.5rem;
    color: #212529;
}

.subtitle-1 {
    font-size: 2rem;
    margin-top: 1.5rem;
    color: gray;
}

.subtitle-2 {
    font-size: 1.5rem;
    margin-top: 1.5rem;
    color: gray;
}

.image-principal {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 0.5rem;
}

.icon-arrow {
    width: 4rem;
    height: 4rem;
    color: #212529;
    text-align: center;
    margin: 2rem auto;
}

.lp-subtitle-size {
    font-size: 3rem;
}

.title-cards {
    font-size: 3rem;
    margin-top: 1.5rem;
    color: #212529;
}

.text-body-cards {
    font-size: 1.4rem;
    margin-top: 1.5rem;
    color: #212529;
    text-align: justify;
}
</style>