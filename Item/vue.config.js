module.exports = {
    publicPath: '/items',
    devServer: {
        proxy: {
            '/api': {
                target: 'http://item-service:8089',
                changeOrigin: true,
            },
        },
    },
};
