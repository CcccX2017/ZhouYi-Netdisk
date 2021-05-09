<template>
	<div id="uploader-panel" v-show="isUpload">
		<uploader>
			<uploader-unsupport></uploader-unsupport>
			<uploader-list>
				<el-card class="box-card">
					<div slot="header" class="clearfix">
						<span>文件列表</span>
						<div style="float: right" class="operation">
							<el-button type="text" icon="el-icon-minus" class="operation-btn" @click="minimize" v-if="isMaximize"></el-button>
							<el-button type="text" icon="el-icon-full-screen" class="operation-btn" @click="maximize" v-else></el-button>
							<el-button type="text" icon="el-icon-close" class="operation-btn" @click="closeUploadPanel"></el-button>
						</div>
					</div>
					<div class="file-list-parent" :style="fileListHeight">
						<ul class="file-header clearfix">
							<li class="file-name">文件名</li>
							<li class="file-size" style="width: 9%;">大小</li>
							<li class="file-path" style="width: 10%;">上传目录</li>
						</ul>
						<div class="file-list" style="height: 314px;">
							<ul class="fileContainer clearfix" id="fileContainer">
								<div class="clearfix file-box" v-for="item in 20">
									<li class="file-info file-name">发生的缓解康复哈萨克的恢复数据库的话费卡技术得分卡接收到.jpg</li>
									<li class="file-info file-size">921.9M</li>
									<li class="file-info file-path"><a href="#">全部文件</a></li>
									<div class="file-status">50%(1.5 MB/s)</div>
									<div class="file-operate">
										<i class="iconfont icon-zanting" style="margin-right: 10px;"></i>
										<i class="iconfont icon-kaishi" style="margin-right: 10px;"></i>
										<i class="iconfont icon-cancel"></i>
									</div>
								</div>
							</ul>
						</div>
					</div>
				</el-card>
			</uploader-list>
		</uploader>
	</div>
</template>

<script>
export default {
	name: 'index',
	data() {
		return {
			fileListHeight: 'height: 350px',
			isMaximize: true,
			isUpload: true,
			fileList: []
		};
	},
	created() {},
	methods: {
		// 关闭上传组件
		closeUploadPanel() {
			this.isUpload = false;
		},
		// 最小化
		minimize() {
			this.fileListHeight = 'height: 0';
			setTimeout(() => {
				this.isMaximize = false;
			}, 500);
		},
		// 最大化
		maximize() {
			this.fileListHeight = 'height: 350px';
			setTimeout(() => {
				this.isMaximize = true;
			}, 500);
		}
	}
};
</script>

<style lang="less">
#uploader-panel {
	position: fixed;
	right: 20px;
	bottom: 1px;
	z-index: 20;

	.box-card {
		width: 720px;

		.operation {
			.operation-btn {
				padding: 0;
				font-size: 18px;
				font-weight: bold;
			}
		}

		.el-card__header {
			border-color: rgba(179, 216, 255, 0.2);
		}

		.el-card__body {
			padding: 0;

			.file-header {
				border-bottom: 1px solid rgba(179, 216, 255, 0.2);
				user-select: none;
				li {
					float: left;
					height: 35px;
					line-height: 35px;
					font-size: 13px;
				}
			}

			.file-list-parent {
				transition: height 0.5s;
				transition-timing-function: linear;
				overflow: hidden;
				.file-list {
					overflow-x: hidden;
					overflow-y: auto;
					.fileContainer {
						.file-box {
							border-bottom: 1px solid rgba(179, 216, 255, 0.2);
							.file-info {
								height: 50px;
								line-height: 50px;
								float: left;
								font-size: 13px;
							}
							.file-status,
							.file-operate {
								display: inline-block;
								font-size: 13px;
								line-height: 50px;
							}
							.file-operate {
								float: right;
								padding: 0 17px;
								i {
									font-size: 14px;
									color: #424e67;
									cursor: pointer;
									font-weight: 400;
								}
							}
						}
					}
				}
			}

			.file-name {
				width: 45%;
				text-indent: 14px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}

			.file-size {
				width: 9%;
				text-indent: 10px;
			}

			.file-path {
				width: 15%;
				text-indent: 10px;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				a {
					color: #5d9cff;
					&:hover {
						text-decoration: underline;
					}
				}
			}
		}
	}
}
</style>
