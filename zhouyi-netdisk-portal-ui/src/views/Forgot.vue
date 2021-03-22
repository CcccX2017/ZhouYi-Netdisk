<template>
	<div class="forgot">
		<div class="forgot-form">
			<div class="logo">
				<span class="logo-info">找回密码</span>
			</div>
			<el-form ref="emailForm" :model="emailForm" :rules="emailRules" label-width="55px" v-if='isStepOne'>
				<el-form-item prop="email" label="邮箱">
					<el-input prefix-icon="el-icon-message" placeholder="请输入账号绑定的邮箱" v-model.trim="emailForm.email" @keyup.enter.native="checkCode"></el-input>
				</el-form-item>
				<el-form-item prop="code" label="验证码">
					<el-input prefix-icon="iconfont icon-yanzhengma" placeholder="请输入验证码" v-model.trim="emailForm.code" class="forgot-code-input" @keyup.enter.native="checkCode"></el-input>
					<countDown delay='60' tagName='forgot' :to='emailForm.email' @setUuid='setUuid'></countDown>
				</el-form-item>
				<el-button type="primary" style="width:100%" @click.native.prevent="nextStep">
					<span>下一步</span>
				</el-button>
			</el-form>
			<el-form ref="pwdForm" :model="pwdForm" :rules="pwdRules" label-width="70px" v-else>
				<el-form-item prop="email" label="新密码">
					<el-input prefix-icon="el-icon-message" placeholder="请设置新密码" v-model.trim="pwdForm.password" @keyup.enter.native="resetPwd"></el-input>
				</el-form-item>
				<el-form-item prop="code" label="确认密码">
					<el-input prefix-icon="iconfont icon-yanzhengma" placeholder="请确认新密码" v-model.trim="pwdForm.oldPwd" @keyup.enter.native="resetPwd"></el-input>
				</el-form-item>
				<el-button type="primary" style="width:100%" @click.native.prevent="nextStep">
					<span>确定</span>
				</el-button>
			</el-form>
		</div>
	</div>
</template>

<script>
	import countDown from '@/components/CountDown'
	export default {
		components:{
			countDown
		},
		data(){
			return{
				isStepOne: true,
				emailForm:{
					email: '',
					code: '',
					uuid: ''
				},
				emailRules:{},
				pwdForm: {
					email: '',
					password: '',
					oldPwd: ''
				}
			}
		},
		methods:{
			// 验证输入的验证码
			checkCode(){},
			// 设置短信验证码返回的唯一标识
			setUuid(){},
			// 下一步，设置新密码
			nextStep(){
				this.isStepOne = false
			},
			// 重置密码
			resetPwd(){}
		}
	}
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
		
		.forgot-form{
			width: 400px;
			background: #fff;
			border-radius: 6px;
			box-shadow: 2px 2px 10px #888;
			padding: 25px;
			
			.logo{
				padding-bottom: 30px;
				margin-bottom: 0;
				.logo-info{
					font-size: 18px;
					color: #000;
				}
			}
			
			.forgot-code-input {
				width: 160px;
				margin-right: 5px;
			}
		}
		
	}
</style>
