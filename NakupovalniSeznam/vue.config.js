module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://shoppinglist:8090', // Kubernetes service name for backend
                changeOrigin: true,
            },
        },
    },
};


// module.exports = {
//     devServer: {
//         proxy: {
//             '/api': {
//                 target: 'http://localhost:8090', // Proxy backend API requests
//                 changeOrigin: true,
//             },
//         },
//         port: 8092, // Set Vue's dev server to run on port 8090
//     },
// };
