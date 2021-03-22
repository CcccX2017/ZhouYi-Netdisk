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
				<countDown delay='60' tagName='register' :to='registerForm.email' @setUuid='setUuid' @checkEmail='checkEmail'></countDown>
			</el-form-item>
			<el-button type="primary" style="width:100%" :loading="loading" @click.native.prevent="register">
				<span v-if="!loading">注册</span>
				<span v-else>注册中...</span>
			</el-button>
			<div style="display: flex;align-items: center;justify-content: center;margin-top: 5px;">
				<el-checkbox v-model="agree">阅读并接受</el-checkbox>
				<el-link type="primary" title="《舟意网盘用户协议》" href="#">《舟意网盘用户协议》</el-link>
				及
				<el-link type="primary" title="《舟意网盘隐私权保护声明》" href="#">《舟意网盘隐私权保护声明》</el-link>
			</div>
		</el-form>
		<div class="footer">Copyright © 2021-2031 舟意网盘 All Rights Reserved.</div>
	</div>
</template>

<script>
	import countDown from '@/components/CountDown'
	export default {
		components:{
			countDown
		},
		data() {
			return {
				loading: false,
				agree: false,
				registerForm: {
					username: '',
					password: '',
					nickname: '',
					email: '',
					code: '',
					uuid: ''
				},
				registerRules: {
					username: [
						{ required: true, trigger: 'blur', message: '用户名不能为空' },
						{ pattern:  /^[a-zA-Z]\w{4,15}$/, trigger: 'blur', message: '用户名须以英文字母开头长度为5-16位的英文/数字/下划线' }
					],
					nickname: [
						{ required: true, trigger: 'blur', message: '昵称不能为空' },
						{ pattern:  /^[\u4E00-\u9FA5\w]{1,12}$/, trigger: 'blur', message: '用户昵称只能是长度为1-12位的中英文/数字/下划线' }
					],
					password: [
						{ required: true, trigger: 'blur', message: '密码不能为空' },
						{ min: 6, max: 16, message: '密码长度在 6 到 16 个字符', trigger: 'blur' }
					],
					email: [
						{ required: true, trigger: 'blur', message: '邮箱不能为空' },
						{ type: 'email', message: '邮箱地址格式不正确', trigger: 'blur'}
					],
					code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
				}
			};
		},
		created() {
			
		},
		methods: {
			// 验证码发送成功，设置uuid
			setUuid(uuid){
				this.registerForm.uuid = uuid
			},
			login(){
				this.$router.push('/login')
			},
			sendEmailCode(){
				this.$refs.registerForm.validateField('email', valid => {
					if(!valid){
						this.sendCode = true
					}
				})
			},
			register(){
				this.$refs.registerForm.validate(valid => {
					if(valid){
						if(!this.agree){
							this.$message.error("同意并接受本站协议及隐私保护声明再进行注册", {duration: 5 * 1000})
							return
						}
						// 发送注册请求
						this.postRequest('/portal/register', this.registerForm).then(resp => {
							// 注册成功，跳转到登录页面
							this.$router.replace('/login')
						})
					}
				})
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
