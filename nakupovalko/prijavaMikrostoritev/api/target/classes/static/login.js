const app = Vue.createApp({

    methods: {
        handleLogin(){
            console.log("i clicked")
            const url = "http://localhost:8080/realms/nakupovalko/protocol/openid-connect/auth?scope=openid&response_type=code&client_id=nakupovalko-prijava-client&redirect_uri=http://localhost:8081/home"
            window.location.href = url

            fetch(url)
                .then(response => {
                    const jwtToken = response.headers.get('X-JWT-Token');
                    sessionStorage.setItem('jwt', jwtToken);
                    console.log(jwtToken);
                })
                .catch(error => {
                    console.error('Error:', error);
                });

        },
        handleRegistration() {

        }
    }

})
app.mount("#app")

