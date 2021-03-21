<template>
	<div class="register">
		<el-form class="register-form" ref="registerForm" :model="registerForm" :rules="registerRules" label-width="80px">
			<div class="logo">
				<div class="logo-title">
					<h3>舟意网盘用户注册</h3>
					<p>
						已有帐号？
						<span class="login_btn" @click="login">登录</span>
					</p>
				</div>
			</div>
			<el-form-item prop="username" label="用户名">
				<el-input prefix-icon="el-icon-user-solid" placeholder="请设置用户名" v-model.trim="registerForm.username" @keyup.enter.native="register"></el-input>
			</el-form-item>
			<el-form-item prop="nickname" label="昵称">
				<el-input prefix-icon="el-icon-chat-dot-round" placeholder="请设置昵称" v-model.trim="registerForm.nickname" @keyup.enter.native="register"></el-input>
			</el-form-item>
			<el-form-item prop="password" label="密码">
				<el-input show-password prefix-icon="el-icon-lock" placeholder="请设置登录密码" v-model.trim="registerForm.password" @keyup.enter.native="register"></el-input>
			</el-form-item>
			<el-form-item prop="email" label="邮箱">
				<el-input prefix-icon="el-icon-message" placeholder="邮箱可用于登录和找回密码" v-model.trim="registerForm.email" @keyup.enter.native="register"></el-input>
			</el-form-item>
			<el-form-item prop="code" label="验证码">
				<el-input prefix-icon="iconfont icon-yanzhengma" placeholder="请输入验证码" v-model.trim="registerForm.code" class="register-code-input" @keyup.enter.native="register"></el-input>
				<el-button type="primary" v-if="!sendCode" @click="sendEmailCode" class="code_btn">获取验证码</el-button>
				<el-button v-else disabled class="code_btn">重新获取(60)</el-button>
			</el-form-item>
			<el-button type="primary" style="width:100%" :loading="loading" @click.native.prevent="register">
				<span v-if="!loading">注册</span>
				<span v-else>注册中...</span>
			</el-button>
			<el-checkbox v-model="checked">
				<p style="cursor: default;">阅读并接受<el-link type="primary" title="《舟意网盘用户协议》">《舟意网盘用户协议》</el-link>及<el-link type="primary" title="《舟意网盘隐私权保护声明》">《舟意网盘隐私权保护声明》</el-link></p>
			</el-checkbox>
		</el-form>
		<div class="footer">Copyright © 2021-2031 舟意网盘 All Rights Reserved.</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			loading: false,
			sendCode: false,
			registerForm: {
				username: '',
				password: '',
				nickname: '',
				email: '',
				code: '',
				uuid: ''
			},
			registerRules: {}
		};
	},
	created() {},
	methods: {
		login(){
			this.$router.push('/login')
		},
		sendEmailCode(){
			this.sendCode = true
		},
		register(){
			
		}
	}
};
</script>
<style lang="less" scoped>
.register {
	display: flex;
	position: absolute;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background-image: url('../assets/image/login_bg.png');
	justify-content: center;
	align-items: center;
	user-select: none;

	.register-form {
		width: 480px;
		background: #fff;
		border-radius: 6px;
		box-shadow: 2px 2px 10px #888;
		padding: 25px;

		.logo {
			padding-bottom: 30px;
			margin-bottom: 0;
			.logo-title {
				h3{
					font-size: 24px;
					color: #333;
					padding-bottom: 4px;
				}
				p{
					color: #9B9B9B;
					font-size: 14px;
					.login_btn{
						color: #2e58ff;
						cursor: pointer;
					}
				}
			}
		}
		
		.register-code-input {
			width: 215px;
			margin-right: 5px;
		}
		
		.code_btn{
			width: 128px;
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
