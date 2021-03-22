<template>
	<div style="display: inline-block;">
		<el-button type="primary" style="width: 128px;" v-if="!isSendCode" @click="sendCode()">获取验证码</el-button>
		<el-button type="primary" disabled style="width: 128px;" v-else>重新获取({{ timeCount }})</el-button>
	</div>
</template>

<script>
export default {
	name: 'countDown',
	props: ['delay', 'tagName', 'to'],
	data() {
		return {
			timeCount: 0,
			isSendCode: false,
			timer: null
		};
	},
	mounted() {
		this.monitor();
	},
	methods: {
		// 监听是否已经在发送
		monitor() {
			let localDelay = this.getLocalDelay(this.tagName);
			if (localDelay.time) {
				// 已经发送过，直接进行倒计时
				this.countDown();
			}
		},
		sendCode() {
			this.isSendCode = true;
			this.countDown();
			// 用户注册
			if (this.tagName === 'register') {
				// 验证邮箱
				this.$root.$children[0].$children[0].$refs.registerForm.validateField('email', valid => {
					if (valid) {
						this.destoryCountDown();
					} else {
						// 发送验证码
						this.getRequest('/portal/registerCode', { to: this.to })
							.then(resp => {
								if (resp) {
									this.$emit('setUuid', resp.data);
								}
							})
							.catch(() => {
								this.destoryCountDown();
							});
					}
				});
			} else if (this.tagName === 'forgot') {
				// 忘记密码
				this.$root.$children[0].$children[0].$refs.emailForm.validateField('email', valid => {
					if (valid) {
						this.destoryCountDown();
					} else {
						// 发送验证码
						this.getRequest('/portal/sendCode', { email: this.to })
							.then(resp => {
								if (resp) {
									this.$emit('setUuid', resp.data);
								}
							})
							.catch(() => {
								this.destoryCountDown();
							});
					}
				});
			}
		},
		countDown() {
			let localDelay = this.getLocalDelay(this.tagName);
			if (localDelay.time) {
				let timeLine = this.delay - parseInt((new Date().getTime() - localDelay.time) / 1000);
				this.timeCount = timeLine <= 0 ? 0 : timeLine;
				this.isSendCode = true;
			} else {
				this.timeCount = this.delay;
				this.setLocalDelay(this.tagName);
			}

			this.timer = setInterval(() => {
				if (this.timeCount > 1 && this.timeCount <= this.delay) {
					this.timeCount--;
				} else {
					this.destoryCountDown();
				}
			}, 1000);
		},
		destoryCountDown() {
			this.removeLocalDelay(this.tagName);
			clearInterval(this.timer);
			this.isSendCode = false;
		},
		setLocalDelay(tag) {
			localStorage.setItem('time_' + location.href + '_' + tag, new Date().getTime());
		},
		getLocalDelay(tag) {
			let LocalDelay = {};
			LocalDelay.time = localStorage.getItem('time_' + location.href + '_' + tag);
			return LocalDelay;
		},
		removeLocalDelay(tag) {
			localStorage.removeItem('time_' + location.href + '_' + tag);
		}
	}
};
</script>

<style></style>
