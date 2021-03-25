module.exports = {
	publicPath: './',
    devServer: {
        host: '0.0.0.0',
        port: '8080',
        proxy: {
            [process.env.VUE_APP_BASE_API]: {
                target: `http://localhost:9001`,
                changeOrigin: true,
                pathRewrite: {
                    ['^' + process.env.VUE_APP_BASE_API]: ''
                }
            }
        },
        disableHostCheck: true
    }
}
