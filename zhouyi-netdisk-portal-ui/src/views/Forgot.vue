<template>
	<div class="forgot">
		<div class="forgot-form">
			<div class="logo"><span class="logo-info">找回密码</span></div>
			<el-form ref="emailForm" :model="emailForm" :rules="emailRules" label-width="65px" v-if="isStepOne">
				<el-form-item prop="email" label="邮箱">
					<el-input prefix-icon="el-icon-message" placeholder="请输入账号绑定的邮箱" v-model.trim="emailForm.email" @keyup.enter.native="nextStep"></el-input>
				</el-form-item>
				<el-form-item prop="code" label="验证码">
					<el-input
						prefix-icon="iconfont icon-yanzhengma"
						placeholder="请输入验证码"
						v-model.trim="emailForm.code"
						class="forgot-code-input"
						@keyup.enter.native="nextStep"
					></el-input>
					<countDown delay="60" tagName="forgot" :to="emailForm.email" @setUuid="setEmailUuid"></countDown>
				</el-form-item>
				<el-button type="primary" style="width:100%" @click.native.prevent="nextStep"><span>下一步</span></el-button>
			</el-form>
			<el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="70px" v-else>
				<el-form-item prop="password" label="新密码">
					<el-input show-password prefix-icon="el-icon-lock" placeholder="请设置新密码" v-model.trim="pwdForm.password" @keyup.enter.native="resetPwd"></el-input>
				</el-form-item>
				<el-form-item prop="repeatPwd" label="确认密码">
					<el-input show-password prefix-icon="el-icon-lock" placeholder="请确认新密码" v-model.trim="pwdForm.repeatPwd" @keyup.enter.native="resetPwd"></el-input>
				</el-form-item>
				<el-button type="primary" style="width:100%" @click.native.prevent="resetPwd"><span>确定</span></el-button>
			</el-form>
		</div>
	</div>
</template>

<script>
import countDown from '@/components/CountDown';
export default {
	components: {
		countDown
	},
	data() {
		var validatePass = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请设置新密码'));
			} else {
				if (this.pwdForm.password !== '') {
					if(this.pwdForm.password.length < 6 || this.pwdForm.password.length > 16){
						callback(new Error('密码长度在 6 到 16 个字符'));
					}
				}
				callback();
			}
		};
		var validatePass2 = (rule, value, callback) => {
			if (value === '') {
				callback(new Error('请确认新密码'));
			} else if (value !== this.pwdForm.password) {
				callback(new Error('两次输入密码不一致!'));
			} else {
				callback();
			}
		};
		return {
			isStepOne: true,
			emailForm: {
				email: '',
				code: '',
				uuid: ''
			},

			emailRules: {
				email: [{ required: true, trigger: 'blur', message: '邮箱不能为空' }, { type: 'email', message: '邮箱地址格式不正确', trigger: 'blur' }],
				code: [{ required: true, trigger: 'change', message: '验证码不能为空' }]
			},
			pwdForm: {
				email: '',
				password: '',
				repeatPwd: '',
				uuid: ''
			},
			pwdRules: {
				password:[
					{ validator: validatePass, trigger: 'blur' }
				],
				repeatPwd: [
					{ validator: validatePass2, trigger: 'blur' }
				]
			}
		};
	},
	methods: {
		// 设置短信验证码返回的唯一标识
		setEmailUuid(uuid) {
			this.emailForm.uuid = uuid;
		},
		// 下一步，设置新密码
		nextStep() {
			this.$refs.emailForm.validate(valid => {
				if (valid) {
					// 验证验证码是否正确
					this.postParamsRequest('/portal/validate', this.emailForm).then(resp => {
						if (resp) {
							this.isStepOne = false;
							this.pwdForm.uuid = resp.data;
						}
					});
				}
			});
		},
		// 重置密码
		resetPwd() {
			this.$refs.pwdForm.validate(valid => {
				if (valid) {
					let paramData = {
						email: this.emailForm.email,
						password: this.pwdForm.password,
						uuid: this.pwdForm.uuid
					}
					this.postRequest('/portal/forgot', paramData).then(resp => {
						if(resp){
							// 重置密码成功，跳转到登录页面
							this.$router.replace('/login')
						}
					})
				}
			});
		}
	}
};
</script>

<style lang="less" scoped>
.forgot {
	display: flex;
	position: absolute;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background-image: url('../assets/image/login_bg.png');
	justify-content: center;
	align-items: center;
	user-select: none;

	.forgot-form {
		width: 400px;
		background: #fff;
		border-radius: 6px;
		box-shadow: 2px 2px 10px #888;
		padding: 25px;

		.logo {
			padding-bottom: 30px;
			margin-bottom: 0;
			.logo-info {
				font-size: 18px;
				color: #000;
			}
		}

		.forgot-code-input {
			width: 152px;
			margin-right: 5px;
		}
	}
}
</style>
