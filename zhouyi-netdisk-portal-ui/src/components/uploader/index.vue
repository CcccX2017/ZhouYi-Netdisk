<template>
	<div id="uploader-panel" v-show="isUpload">
		<uploader ref="uploader" :autoStart="false" :options="options" @file-added="onFileAdded">
			<uploader-unsupport></uploader-unsupport>

			<uploader-btn id="uploader-btn" ref="uploadBtn">选择文件</uploader-btn>

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
							<li class="file-size" style="width: 9%">大小</li>
							<li class="file-path" style="width: 10%">上传目录</li>
						</ul>
						<div class="file-list" style="height: 314px">
							<ul class="fileContainer clearfix" id="fileContainer" v-for="(item, index) in fileList" :key="index">
								<div class="clearfix file-box">
									<li class="file-info file-name" :title="item.name">
										<img :src="require('../../assets/filetype/' + item.icon)" :alt="item.name" width="25" height="25" style="vertical-align: middle" />
										{{ item.name }}
									</li>
									<li class="file-info file-size" :title="item.size">{{ item.size }}</li>
									<li class="file-info file-path" :title="item.path">
										<a :href="item.targetPath">{{ item.path }}</a>
									</li>
									<div class="file-status" :title="item.status">{{ item.status }}</div>
									<div class="file-operate">
										<i class="iconfont icon-zanting" style="margin-right: 10px" v-if="!item.pause" title="暂停"></i>
										<i class="iconfont icon-kaishi" style="margin-right: 10px" v-else title="开始"></i>
										<i class="iconfont icon-cancel" title="取消" @click="cancelUploader(item.id)"></i>
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
import bus from '@/utils/bus.js';
import { getToken } from '@/utils/token';

export default {
	name: 'index',
	data() {
		return {
			fileListHeight: 'height: 350px',
			isMaximize: true,
			isUpload: false,
			fileList: [],
			options: {
				target: '/portal/files/',
				chunkSize: '2048000',
				fileParameterName: 'upfile',
				maxChunkRetries: 3,
				testChunks: true, //是否开启服务器分片校验
				// 服务器分片校验函数，秒传及断点续传基础
				checkChunkUploadedByResponse: function(chunk, message) {
					let objMessage = JSON.parse(message);
					if (objMessage.skipUpload) {
						return true;
					}
					return (objMessage.uploaded || []).indexOf(chunk.offset + 1) >= 0;
				},
				headers: {
					Authorization: 'Bearer ' + getToken()
				},
				query() {}
			},
			params: null
		};
	},
	created() {},
	mounted() {
		bus.$on('openUploader', query => {
			this.params = query || {};
			if (this.$refs.uploadBtn) {
				document.getElementById('uploader-btn').click();
			}
		});
	},
	methods: {
		// 取消文件上传
		cancelUploader(id){
			for(let i = 0;i < this.uploader.fileList.length; i++){
				if(this.uploader.fileList[i].id === id){
					this.uploader.fileList[i].cancel()
				}
			}
			this.fileList.forEach((file, index) => {
				if(file.id === id){
					this.fileList.splice(index, 1)
				}
			})
			if(this.uploader.fileList.length === 0){
				this.isUpload = false
			}
		},
		// 选择文件
		onFileAdded(file) {
			let size = this.converSize(file.size);
			let fileObj = {
				id: file.id,
				name: file.name,
				size: size,
				path: this.params.path,
				targetPath: this.params.targetPath,
				pause: true
			};
			this.getRequest(`/portal/list/${file.getExtension()}`)
				.then(response => {
					fileObj.icon = response.data;
					this.fileList.push(fileObj);
					this.setStatus(file.id, 'md5');
				})
				.catch(() => {
					fileObj.icon = 'unknow.png';
					this.fileList.push(fileObj);
					this.setStatus(file.id, 'md5');
				});
			this.isUpload = true;
		},
		// 关闭上传组件
		closeUploadPanel() {
			this.isUpload = false;
			this.fileList = [];
			this.uploader.cancel();
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
		},
		// 格式化文件大小
		converSize(size) {
			if (size === 0) return '0B';
			let sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
			let k = 1024;
			let i = Math.floor(Math.log(size) / Math.log(1024));
			return (size / Math.pow(k, i)).toPrecision(3) + sizes[i];
		},
		// 设置上传状态
		setStatus(id, status) {
			let statusMap = {
				md5: '校验MD5...',
				merging: '合并中...',
				transcoding: '转码中...',
				failed: '上传失败'
			};
			for (let i = 0; i < this.fileList.length; i++) {
				if (this.fileList[i].id === id) {
					this.fileList[i].status = statusMap[status];
					return;
				}
			}
		}
	},
	computed: {
		// 返回uploader实例
		uploader() {
			return this.$refs.uploader.uploader;
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

	#uploader-btn {
		display: none;
	}
}
</style>
