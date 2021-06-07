<template>
	<div class="container">
		<div class="header">
			<div class="header-content">
				<div class="left-content">
					<div class="logo"><a href="/"></a></div>
					<div class="header-link">
						<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="menuRouter">
							<el-menu-item index="/netdisk/">网盘</el-menu-item>
							<el-menu-item index="/share">分享</el-menu-item>
						</el-menu>
						<div class="line"></div>
					</div>
				</div>
				<div class="right-content">
					<el-dropdown trigger="hover" :show-timeout="50" :hide-timeout="50" placement="bottom-start" @command="commandHandler" @visible-change="dropdownChange">
						<div class="user-info">
							<el-avatar size="large" :src="userInfo.avatar" class="avatar"></el-avatar>
							<span style="margin-left: 10px;">
								{{ userInfo.nickname }}
								<i class="el-icon-arrow-down icon-down" :class="isHover ? 'hover' : ''"></i>
							</span>
						</div>
						<el-dropdown-menu slot="dropdown" class="dropdown-info" :class="userType">
							<el-dropdown-item class="user-item">
								<i class="img-bg"></i>
								<el-avatar :size="45" :src="userInfo.avatar" class="avatar" style="position: relative;z-index: 3;"></el-avatar>
								<span style="margin-left: 10px;position: relative;z-index: 3;">{{ userInfo.nickname }}</span>
							</el-dropdown-item>
							<el-dropdown-item command="userinfo">个人资料</el-dropdown-item>
							<el-dropdown-item command="logout">退出</el-dropdown-item>
						</el-dropdown-menu>
					</el-dropdown>
					<div class="separated"></div>
					<a href="#" class="additional" title="通知"><i class="iconfont icon-tongzhi"></i></a>
					<a href="#" class="additional" title="意见反馈"><i class="iconfont icon-yjfk"></i></a>
					<a href="#" class="additional" title="更换皮肤"><i class="iconfont icon-pifu"></i></a>
					<span class="members"><a href="#" style="text-decoration: none;" title="会员中心">会员中心</a></span>
				</div>
			</div>
		</div>
		<router-view />
	</div>
</template>

<script>
export default {
	name: 'index',
	data() {
		return {
			activeIndex: '/netdisk/',
			menuRouter: true,
			userInfo: {
				avatar: '',
				username: '',
				groupId: '',
				nickname: ''
			},
			isHover: false
		};
	},
	created() {
		this.setActiveIndex();
		this.setUserInfo();
	},
	methods: {
		commandHandler(command) {
			if (command === 'logout') {
				this.$confirm('确定要退出舟意网盘吗?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消'
				})
					.then(() => {
						// 注销登录
						this.$store.dispatch('LogOut').then(() => {
							location.reload('/');
						});
					})
					.catch(() => {});
			}
		},
		dropdownChange(val) {
			if (val) {
				this.isHover = true;
			} else {
				this.isHover = false;
			}
		},
		setActiveIndex() {
			this.activeIndex = this.$route.path.indexOf('/netdisk/') !== -1 ? '/netdisk/' : this.$route.path;
		},
		setUserInfo() {
			this.userInfo.avatar = this.$store.getters.avatar;
			this.userInfo.username = this.$store.getters.name;
			this.userInfo.groupId = this.$store.getters.groupId;
			this.userInfo.nickname = this.$store.getters.nickname;
		}
	},
	computed: {
		userType: function() {
			if (this.userInfo.groupId === 1003) {
				return 'svip-dropdown';
			}
			return 'normal-dropdown';
		}
	},
	watch: {
		'$route.path': function(val) {
			this.activeIndex = this.$route.path.indexOf('/netdisk/') !== -1 ? '/netdisk/' : this.$route.path;
		}
	}
};
</script>

<style lang="less" scoped>
.container {
	position: absolute;
	background-color: #f7f7f7;
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
	overflow: hidden;
	min-width: 1103px;

	.header {
		background: #fff;
		box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.05);
		width: 100%;
		height: 62px;
		position: absolute;
		top: 0;
		left: 0;
		z-index: 99;

		.header-content {
			display: flex;
			justify-content: space-between;
			align-items: center;
			height: 100%;
			padding: 0 20px;

			.left-content {
				display: flex;
				justify-content: space-between;
				align-items: center;

				.logo {
					width: 150px;
					height: 32px;
					background-image: url('../assets/images/logo.png');
				}

				.header-link {
					margin-left: 26px;

					ul {
						border: none;

						li {
							color: #303133;
							font-family: PingFangSC-Semibold, serif !important;
							font-size: 16px;

							&.is-active {
								color: #409eff;
							}
						}
					}
				}
			}

			.right-content {
				div {
					display: inline-block;
				}

				.user-info {
					cursor: pointer;

					.icon-down.hover {
						transform: rotateZ(180deg);
					}

					.avatar {
						vertical-align: middle;
					}

					.icon-down {
						font-size: 14px;
						font-weight: 700;
						margin-left: 10px;
						transition: 0.5s;
					}
				}

				.separated {
					margin-left: 20px;
					width: 0;
					height: 12px;
					border-left: 1px solid #adb3be;
				}

				.additional {
					color: #7c878e;

					i {
						font-size: 17px;
						font-weight: 600;
						margin-left: 20px;
					}
				}

				.members {
					margin-left: 20px;

					a {
						color: #333;
						font-size: 12px;
						background: linear-gradient(-139deg, #f0d7a3 0, #efcb85 100%) #f0d7a3;
						border-radius: 100px;
						padding: 5px 15px;

						&:hover {
							background: #e7c062;
						}
					}
				}
			}
		}
	}

	.aside {
		width: 194px;
		position: absolute;
		padding-top: 10px;
		top: 62px;
		left: 0;
		bottom: 0;
		z-index: 9;
	}

	.main {
		position: absolute;
		background: #fff;
		left: 194px;
		top: 62px;
		bottom: 0;
		right: 0;
		padding: 20px;
		z-index: 1;
	}
}

.dropdown-info {
	width: 260px;
	padding: 0;
	li {
		line-height: 35px;
	}
	&.normal-dropdown {
		.user-item {
			background: url(../assets/images/vip-bg.png);
		}
	}
	&.svip-dropdown {
		.user-item {
			background-color: #dd6966;
		}
		.img-bg {
			position: absolute;
			width: 100%;
			height: 100%;
			left: 0;
			bottom: 0;
			background: url(../assets/images/svip-bg.png);
			background-size: cover;
			background-position: center center;
			opacity: 0.2;
			z-index: 2;
		}
	}
	.user-item {
		display: flex;
		align-items: center;
		height: 80px;
		color: #fff;
		cursor: default;
		position: relative;
		&:hover {
			color: #fff;
		}
	}
}
</style>
