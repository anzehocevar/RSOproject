module.exports = {
    devServer: {
        proxy: {
            '/items': {
                target: 'http://item-service:8089', // Kubernetes service name for backend
                changeOrigin: true,
            },
        },
    },
};
