<template>
    <div class="login">
        <el-form class="login-form">
            <div class="logo">
                <span>账号密码登录</span>
                <el-link type="primary" class="logo-link" title="短信快捷登录">短信快捷登录></el-link>
            </div>
            <el-form-item>
                <el-input prefix-icon="el-icon-user-solid" placeholder="用户名/邮箱" v-model.trim="login.username"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input show-password prefix-icon="el-icon-lock" placeholder="密码" v-model.trim="login.password"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input prefix-icon="el-icon-lock" placeholder="验证码" v-model.trim="login.code"></el-input>
            </el-form-item>
            <el-form-item style="margin: -15px 0 5px 0">
                <el-checkbox>记住密码</el-checkbox>
            </el-form-item>
            <el-button type="primary" style="width:100%">登录</el-button>
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
export default {
    data() {
        return {
            login:{
                username:"",
                password:"",
                code:"",
                uuid:""
            }
        }
    },
    methods: {}
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
    .login-form{
        width: 400px;
        background: #fff;
        border-radius: 6px;
        box-shadow: 2px 2px 10px #888;
        padding: 25px;
        .logo{
            padding-bottom: 30px;
            margin-bottom: 0;
            display: flex;
            justify-content: space-between;
            span{
                color: #333;
                font-weight: 600;
                font-size: 18px;
                text-align: left;
            }
            .logo-link{
                font-size: 16px;
            }
        }
        .footer-bar{
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }
    }
    .footer{
        position: absolute;
        bottom: 20px;
        color: #666;
        font-size: 12px;
        font-family: Arial !important;
    }
}
</style>