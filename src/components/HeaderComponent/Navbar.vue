<template>
    <div class="margin">
        <!-- Navbar -->
        <b-navbar toggleable="lg" type="dark" variant="light">
            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav>
                    <b-nav-item  to="inicio">
                        <b-button variant="light"><b-icon icon="house-door"></b-icon></b-button>
                    </b-nav-item>
                </b-navbar-nav>

                <b-navbar-nav class="ml-auto">
                    <b-nav-form>
                        <b-button variant="light"><b-icon icon="cart3"></b-icon></b-button>
                        <b-button to="profile-screen" variant="light"><b-icon icon="person"></b-icon></b-button>
                    </b-nav-form>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
     

    </div>
</template>

<script>
export default {
    name: "Navbar"
}
</script>
<style>
.margin{
    margin-bottom: 5px;
}
</style>