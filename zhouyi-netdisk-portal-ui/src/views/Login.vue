<template>
   <div class="login">
      <el-form class="login-form" ref="loginForm" :model="loginForm" :rules="loginRules">
         <div class="logo">
            <span>账号密码登录</span>
            <el-link type="primary" class="logo-link" title="短信快捷登录" @click="undeveloped">短信快捷登录></el-link>
         </div>
         <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user-solid" placeholder="用户名/邮箱"
                      v-model.trim="loginForm.username" @keyup.enter.native="login"></el-input>
         </el-form-item>
         <el-form-item prop="password">
            <el-input show-password prefix-icon="el-icon-lock" placeholder="密码"
                      v-model.trim="loginForm.password" @keyup.enter.native="login"></el-input>
         </el-form-item>
         <el-form-item prop="code">
            <el-input prefix-icon="iconfont icon-yanzhengma" placeholder="验证码" v-model.trim="loginForm.code" class="login-code-input" @keyup.enter.native="login"></el-input>
            <div class="login-code">
               <img :src="codeUrl" @click="getCaptcha" class="login-code-img"/>
            </div>
         </el-form-item>
         <el-form-item style="margin: -12px 0 5px 0">
            <el-checkbox v-model="loginForm.rememberMe">记住密码</el-checkbox>
         </el-form-item>
         <el-button type="primary" style="width:100%" :loading="loading" @click.native.prevent="login">
            <span v-if="!loading">登录</span>
            <span v-else>登录中...</span>
         </el-button>
         <div class="footer-bar">
            <el-link title="忘记密码?">忘记密码?</el-link>
            <el-link type="primary" title="立即注册">立即注册</el-link>
         </div>
      </el-form>
      <div class="footer">
         Copyright © 2021-2031 舟意网盘 All Rights Reserved.
      </div>
   </div>
</template>

<script>
   import Cookies from 'js-cookie'
   import {encrypt , decrypt} from "../utils/jsencrypt";

   export default {
      data() {
         return {
            codeUrl: "",
            loading: false,
            loginForm: {
               username: "",
               password: "",
               rememberMe: false,
               code: "",
               uuid: ""
            },
            loginRules: {
               username: [
                  { required: true, trigger: "blur", message: "用户名不能为空" }
               ],
               password: [
                  { required: true, trigger: "blur", message: "密码不能为空" }
               ],
               code: [{ required: true, trigger: "change", message: "验证码不能为空" }]
            }
         }
      },
      created() {
         this.getCaptcha()
         this.getCookie()
      },
      methods: {
         getCookie(){
            const username = Cookies.get("username");
            const password = Cookies.get("password");
            const rememberMe = Cookies.get('rememberMe')
            this.loginForm = {
               username: username === undefined ? this.loginForm.username : username,
               password: password === undefined ? this.loginForm.password : decrypt(password),
               rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
            };
         },
         login(){
            this.$refs.loginForm.validate(valid => {
               if(valid){
                  this.loading = true
                  if(this.loginForm.rememberMe){
                     Cookies.set("username", this.loginForm.username, { expires: 30})
                     Cookies.set("password", encrypt(this.loginForm.password), { expires: 30})
                     Cookies.set("rememberMe", this.loginForm.rememberMe, { expires: 30})
                  }else{
                     Cookies.remove("username")
                     Cookies.remove("password")
                     Cookies.remove("rememberMe")
                  }
                  this.$store.dispatch("Login", this.loginForm).then(() => {
                     this.$router.replace({ path: this.redirect || "/home" }).catch(()=>{});
                  }).catch(() => {
                     this.loading = false
                     this.getCaptcha()
                  })
               }
            })

         },
         getCaptcha() {
            this.getRequest("/portal/captcha", {height: 34}).then(resp => {
               this.codeUrl = "data:image/gif;base64," + resp.data.img
               this.loginForm.uuid = resp.data.uuid
            })
         },
         undeveloped(){
            this.$message.error("o(╥﹏╥)o抱歉，该功能尚未开发")
         }
      }
   }
</script>
<style lang='less' scoped>
   .login {
      display: flex;
      position: absolute;
      width: 100%;
      height: 100%;
      overflow: hidden;
      background-image: url("../assets/image/login_bg.png");
      justify-content: center;
      align-items: center;
      user-select: none;

      .login-form {
         width: 400px;
         background: #fff;
         border-radius: 6px;
         box-shadow: 2px 2px 10px #888;
         padding: 25px;

         .logo {
            padding-bottom: 30px;
            margin-bottom: 0;
            display: flex;
            justify-content: space-between;

            span {
               color: #333;
               font-weight: 600;
               font-size: 18px;
               text-align: left;
            }

            .logo-link {
               font-size: 16px;
            }
         }

         .footer-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
         }

         .login-code-input{
            width: 240px;
            margin-right: 5px;
         }
         .login-code{
            float: right;
            .login-code-img{
               cursor: pointer;
               vertical-align: middle;
               margin-top: -3px;
               border: 1px solid #DCDFE6;
               border-radius: 4px;
            }
         }

      }

      .footer {
         position: absolute;
         bottom: 20px;
         color: #666;
         font-size: 12px;
         font-family: Arial !important;
      }
   }
</style>
