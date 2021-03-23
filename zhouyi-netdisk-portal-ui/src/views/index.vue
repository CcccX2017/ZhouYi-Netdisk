<template>
	<div class="container">
		<div class="header">
			<div class="header-content">
				<div class="left-content">
					<div class="logo" style="font-size: 24px;">舟意网盘</div>
					<div class="header-link">
						<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" :router="menuRouter">
						  <el-menu-item index="/netdisk">网盘</el-menu-item>
						  <el-menu-item index="/share">分享</el-menu-item>
						</el-menu>
						<div class="line"></div>
					</div>
				</div>
				<div class="right-content">
					<div class="user-info">
						<el-avatar size="large" :src="userInfo.avatar" class='avatar'></el-avatar>
						<span style="margin-left: 10px;">{{userInfo.username}}<i class="el-icon-arrow-down icon-down"></i></span>
					</div>
					<div class="separated"></div>
					<a href="#" class="additional" title="通知"><i class="iconfont icon-tongzhi"></i></a>
					<a href="#" class="additional" title="意见反馈"><i class="iconfont icon-yjfk"></i></a>
					<a href="#" class="additional" title="更换皮肤"><i class="iconfont icon-pifu"></i></a>
					<span class="members">
						<a href="#" style="text-decoration: none;" title="会员中心">会员中心</a>
					</span>
				</div>
			</div>
		</div>
		<router-view />
	</div>
</template>

<script>
export default {
	name: 'index',
	data(){
		return{
			activeIndex: '/netdisk',
			menuRouter: true,
			userInfo:{
				avatar: '',
				username: ''
			}
		}
	},
	created() {
		this.setActiveIndex()
		this.setUserInfo()
	},
	methods:{
		setActiveIndex(){
			this.activeIndex = this.$route.path
		},
		setUserInfo(){
			this.userInfo.avatar = this.$store.getters.avatar
			this.userInfo.username = this.$store.getters.name
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
			.left-content{
				display: flex;
				justify-content: space-between;
				align-items: center;
				.header-link{
					margin-left: 34px;
					ul{
						border: none;
						li{
							color: #303133;
							font-family: PingFangSC-Semibold !important;
							font-size: 16px;
							&.is-active{
								color: #409EFF;
							}
						}
					}
				}
			}
			.right-content{
				div{
					display: inline-block;
				}
				.user-info{
					cursor: pointer;
					&:hover{
						.icon-down{
							transform: rotateZ(180deg)
						}
					}
					.avatar{
						vertical-align: middle;
					}
					.icon-down{
						font-size: 14px;
						font-weight: 700;
						margin-left: 10px;
						transition: 0.5s;
					}
				}
				.separated{
					margin-left: 20px;
					width: 0;
					height: 12px;
					border-left: 1px solid #adb3be;
				}
				.additional{
					color: #7C878E;
					i{
						font-size: 17px;
						font-weight: 600;
						margin-left: 20px;
					}
				}
				.members{
					margin-left: 20px;
					a{
						color:#333;
						font-size: 12px;
						font-weight: 600;
						background: linear-gradient(-139deg, #F0D7A3 0, #EFCB85 100%) #F0D7A3;
						border-radius: 100px;
						padding: 5px 15px;
						&:hover{
							background: #E7C062;
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
</style>
