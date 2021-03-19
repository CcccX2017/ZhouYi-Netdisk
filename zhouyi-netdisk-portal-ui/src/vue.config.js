module.exports = {
    publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
    outputDir: 'dist',
    assetsDir: 'assets',
    productionSourceMap: false,
    devServer: {
        host: '0.0.0.0',
        port: 8080,
        open: true,
        proxy: {
          // detail: https://cli.vuejs.org/config/#devserver-proxy
          "/dev-api": {
            target: 'http://localhost:9001',
            changeOrigin: true,
            pathRewrite: {
              '^/dev-api': ''
            }
          }
        },
        disableHostCheck: true
    },
    configureWebpack: {
        name: "舟意网盘",
        resolve: {
          alias: {
            '@': resolve('src')
          }
        }
    }
}
