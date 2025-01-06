const app = Vue.createApp({

    methods: {
        handleLogin(){
            console.log("i clicked")
            const url = "http://20.61.156.48:8080/realms/nakupovalko/protocol/openid-connect/auth?scope=openid&response_type=code&client_id=nakupovalko-prijava-client&redirect_uri=http://20.61.156.48:8081/home"
            window.location.href = url

            fetch(url)
                .then(response => {
                    const jwtToken = response.headers.get('X-JWT-Token');
                    sessionStorage.setItem('jwt', jwtToken);
                    console.log(jwtToken);
                    setTimeout(() => { console.log(sessionStorage.getItem('jwt')); console.log(jwtToken); }, 6000);

                })
                .catch(error => {
                    console.error('Error:', error);
                });
            this.saveToken();
        },
        saveToken() {
            fetch('http://20.61.156.48:8081/api/set-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: sessionStorage.getItem('jwt')
                })
            })
        },
        handleRegistration() {

        }
    }

})
app.mount("#app")

