const home = Vue.createApp({
    methods: {
        handleRedirect() {
            // Check if the URL contains an authorization code after the redirect
            const urlParams = new URLSearchParams(window.location.search);
            console.log(urlParams)
            const code = urlParams.get('code');
            console.log(code)

            if (code) {
                // Exchange authorization code for JWT token
                this.fetchToken(code);
            } else {
                console.error("Authorization code not found in the URL");
            }
        },

        fetchToken(code) {
            const tokenUrl = "http://localhost:8080/realms/nakupovalko/protocol/openid-connect/token";
            const body = new URLSearchParams({
                client_id: 'nakupovalko-prijava-client',
                client_secret: '9so9VdJZJXQnH8pBzfXSGZivWY2Bq5ZY',
                code: code,
                redirect_uri: 'http://localhost:8081/home',
                grant_type: 'authorization_code'
            });

            fetch(tokenUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: body
            })
                .then(response => response.json())
                .then(data => {
                    const jwtToken = data.access_token;
                    sessionStorage.setItem('jwt', jwtToken);
                    console.log("JWT Token:", jwtToken);
                    this.saveToken();
                })
                .catch(error => {
                    console.error('Error fetching token:', error);
                });
        },

        saveToken() {
            fetch('http://localhost:8081/api/set-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    token: sessionStorage.getItem('jwt')
                })
            })
                .then(response => {
                    if (response.ok) {
                        console.log('Token saved successfully');
                    } else {
                        console.error('Error saving token');
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    },

    mounted() {
        this.handleRedirect();
    }
})

home.mount("#home");