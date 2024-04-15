import axios from 'axios';

const API_URL = 'http://localhost:8091/api-carsi-shop/captcha/verificar-captcha'; // Reemplaza con la URL de tu backend Spring Boot

const CaptchaService = {
    async verificarCaptcha(solution) {
        return axios.post(API_URL + "?solution=" + solution)
            .then(response => response.data)
            .catch(error => {
                console.error('Error al verificar el captcha:', error);
                throw error; // Re-lanzar el error para que el componente que llama pueda manejarlo
            });
    }
};

export default CaptchaService;