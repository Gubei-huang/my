const { defineConfig } = require('@vue/cli-service')
module.exports = ({
  transpileDependencies: true,
  devServer: {
    port:9876,
    proxy: {
      '/api': { //访问路径，可以自己设置，
        target: 'http://localhost:8080/api/', //代理接口，即请求的url的前缀
        changeOrigin: true, //设置是否跨域，开启代理：在本地会创建一个虚拟服务端，然后发送请求的数据，并同时接收请求的数据，这样客户端和服务端进行数据的交互就不会有跨域问题
        pathRewrite: { //访问路径重写
          '^/api': ''
        }
      }
    }
  }
})
