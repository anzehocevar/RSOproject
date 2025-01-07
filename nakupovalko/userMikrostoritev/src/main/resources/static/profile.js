const app = Vue.createApp({
    data() {
        return {
            avatarUrl: '', // URL for avatar
            user: null,    // active user json
            id: null,      // active user id
            error: null,
            editFields: {}, // tracks which fields are in edit mode
        };
    },
    methods: {
        goHome() {
            //TODO: change location to home
            window.location.href = 'http://localhost:8081/home';
            alert('Redirecting to home')
        },
        logout() {
            window.location.href = "/auth/realms/nakupovalko/protocol/openid-connect/logout";
            alert('Logging user out');
        },

        async fetchUserInfo() {
            try {
                const response = await fetch('http://20.61.156.48:8082/users/active');

                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }

                const idResponse = await fetch('http://20.61.156.48:8082/users/active/id');
                if (!response.ok) {
                    throw new Error('Failed to fetch user data');
                }

                this.user = await response.json();
                this.id = await idResponse.json();

                //calling the /users/id/avatar is a lot slower so we opted for this
                this.avatarUrl = `https://api.dicebear.com/9.x/pixel-art/svg?seed=${this.user.username}`;

                this.editFields = Object.keys(this.user).reduce((acc, key) => {
                    acc[key] = false;
                    return acc;
                }, {});
            } catch (error) {
                console.error('Error fetching user data:', error);
                this.error = 'Failed to load profile information.';
            }
        },

        saveEdit(key) {
            this.editFields[key] = false;

            const response = fetch(`http://20.61.156.48:8082/users/${this.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(this.user),
            });

            if (!response.ok) {
                throw new Error('Failed to update user data');
            }

            console.log(`Updated ${key}:`, this.user[key]);
        },
        cancelEdit(key) {
            this.editFields[key] = false;
            this.fetchUserInfo();
        },
    },
    mounted() {
        this.fetchUserInfo();
    },
});
app.mount("#app");
