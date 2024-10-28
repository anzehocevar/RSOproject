const app = Vue.createApp({

    methods: {
        handleLogin(){
            console.log("i clicked")
            window.location.href = 'http://localhost:8080/realms/nakupovalko/account'
        },
        handleRegistration() {

        }
    }

})
app.mount("#app")

