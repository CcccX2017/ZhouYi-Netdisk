<template>
	<div>
		<div class="file-fun-area">
			<div class="fun-btn">
				<el-button type="primary" icon="el-icon-upload" @click="uploader">上传</el-button>
				<el-button type="primary" plain icon="el-icon-folder-add" class="plain-btn" @click="openCreateFolderDialog">新建文件夹</el-button>
				<el-button-group class="btn-group" v-if="btnGroup.show">
					<el-button type="primary" plain icon="iconfont icon-share" class="plain-btn">分享</el-button>
					<el-button type="primary" plain icon="el-icon-download" class="plain-btn">下载</el-button>
					<el-button type="primary" plain icon="el-icon-delete" class="plain-btn" @click.native.stop="deleteFile">删除</el-button>
					<el-button type="primary" plain class="plain-btn" :disabled="btnGroup.disabled" @click.native.stop="openRenameDialog">重命名</el-button>
					<el-button type="primary" plain class="plain-btn">复制到</el-button>
					<el-button type="primary" plain class="plain-btn">移动到</el-button>
				</el-button-group>
			</div>
			<div class="right-fun clearfix">
				<div class="icon-fun-area">
					<i class="iconfont icon-paixu"></i>
					<i class="iconfont icon-viewgallery"></i>
				</div>
				<div class="fun-search clearfix">
					<el-input
						size="small"
						class="search-input"
						placeholder="搜索您的文件"
						v-model="searchParam.keyword"
						clearable
						@clear="clearKeyword"
						@keyup.enter.native="search"
					></el-input>
					<i class="el-icon-search search-icon" @click="search"></i>
				</div>
			</div>
		</div>
		<div class="file-container">
			<div class="title-info clearfix">
				<span class="menu-tag">
					<span v-show="queryParam.dir === '/' && isSearch === 0" class="textSpan">全部文件</span>
					<ul class="breadcrumbUl" v-show="queryParam.dir !== '/' || isSearch === 1">
						<li>
							<el-link type="primary" @click.native="goBack(null)">返回上一级</el-link>
							<span style="color: #c5d8f3;padding: 0 3px;vertical-align: middle;">|</span>
						</li>
						<li>
							<el-link type="primary" @click.native="goBack('/')" title="全部文件">全部文件</el-link>
							<span style="color: #c5d8f3;padding: 0 5px">></span>
							<template v-for="(item, index) in breadcrumb" v-show="isSearch === 0">
								<el-link type="primary" v-if="index != breadcrumb.length - 1 && item.name != '...'" @click="goBack(item.path)" :title="item.name">
									{{ item.name | breadcrumbFilter(breadcrumb.length) }}
								</el-link>
								<span class="breadcrumbTxtSpan" v-else :title="item.name">{{ item.name | breadcrumbFilter(breadcrumb.length) }}</span>
								<span style="color: #c5d8f3;padding: 0 5px" v-if="index != breadcrumb.length - 1">></span>
							</template>
							<template v-show="isSearch === 1">
								<span class="breadcrumbTxtSpan">{{ `搜索："${searchParam.keyword}"` }}</span>
							</template>
						</li>
					</ul>
				</span>
				<span class="load-count textSpan" v-if="loading != null">获取更多数据...</span>
				<span class="load-count textSpan" v-else-if="isAll == 1">已全部加载，共{{ count }}个</span>
				<span class="load-count textSpan" v-else>已加载{{ count }}个</span>
			</div>
			<div class="file-list">
				<div class="list-title">
					<ul class="title-ul" v-if="isSearch == 0">
						<li data-key="name" style="width: 60%;padding-left: 16px;" @click.stop="changeOrder('name')">
							<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange" @click.native="stopDefault($event)"></el-checkbox>
							<span style="padding-left: 10px;">{{tableTitle}}</span>
							<i :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'" class="sortIcon" v-if="queryParam.order == 'name'"></i>
						</li>
						<li data-key="size" style="width: 16%;" @click.stop="changeOrder('size')">
							<span>大小</span>
							<i :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'" class="sortIcon" v-if="queryParam.order == 'size'"></i>
						</li>
						<li data-key="time" style="width: 23%;" @click.stop="changeOrder('time')">
							<span>修改日期</span>
							<i :class="queryParam.desc == 1 ? 'el-icon-bottom' : 'el-icon-top'" class="sortIcon" v-if="queryParam.order == 'time'"></i>
						</li>
					</ul>
					<ul class="title-ul" v-else>
						<li data-key="name" style="width: 60%;padding-left: 16px;">
							<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange" @click.native="stopDefault($event)"></el-checkbox>
							<span style="padding-left: 10px;">{{tableTitle}}</span>
						</li>
						<li data-key="size" style="width: 16%;"><span>大小</span></li>
						<li data-key="time" style="width: 13%;"><span>修改日期</span></li>
						<li data-key="time" style="width: 10%;"><span>所在目录</span></li>
					</ul>
				</div>
				<div id="fileDiv" class="list-content" :style="content">
					<vue-scroll ref="vs" style="top: -1px;border: none;" @handle-scroll="handleScroll">
						<template v-for="(item, index) in list">
							<div class="content-row clearfix" :key="index + new Date().getTime()" @click.prevent="checkOne(item)" :class="index == 0 ? 'first' : ''">
								<div class="left content-col content-flex" style="width: 60%;padding-left: 16px;">
									<el-checkbox-group v-model="checkedList">
										<el-checkbox :label="item.id" @click.native="stopDefault($event)" @change="checked => checkMore(checked, item)">{{ '' }}</el-checkbox>
									</el-checkbox-group>
									<img :src="require('../assets/filetype/' + item.icon)" />
									<span class="txtSpan" style="padding-left: 10px;">
										<a href="javascript:;" v-if="item.isDir" @click.stop="openDir(item.path)">{{ item.name }}</a>
										<span v-else>{{ item.name }}</span>
									</span>
								</div>
								<div class="left content-col" style="width: 16%">
									<span class="txtSpan">{{ item.sizeStr }}</span>
								</div>
								<div class="left content-col" style="width: 23%" v-if="isSearch == 0">
									<span class="txtSpan">{{ item.gmtModified }}</span>
								</div>
								<template v-else>
									<div class="left content-col" style="width: 13%">
										<span class="txtSpan">{{ item.gmtModified }}</span>
									</div>
									<div class="left content-col" style="width: 10%">
										<span class="txtSpan">
											<a href="javascript:;" class="dirLink" :title="assembleDirectory(item.dir)" @click.stop="goBack(item.dir)">
												{{ assembleDirectory(item.dir) | directoryFilter }}
											</a>
										</span>
									</div>
								</template>
							</div>
						</template>
					</vue-scroll>
				</div>
			</div>
		</div>

		<!-- 新建文件夹 -->
		<el-dialog title="新建文件夹" :visible.sync="dialogFolderVisible" width="350px" custom-class="folderDialog" :close-on-click-modal="false">
			<el-form :model="folderForm" :rules="folderRules" ref="folderForm">
				<el-form-item prop="folderName">
					<el-input v-model="folderForm.folderName" placeholder="请输入文件夹名称" @keyup.enter.native="createFolder" ref="folderInput"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogFolderVisible = false">取 消</el-button>
				<el-button type="primary" @click="createFolder">确 定</el-button>
			</span>
		</el-dialog>

		<!-- 重命名 -->
		<el-dialog title="重命名" :visible.sync="dialogRenameVisible" width="350px" custom-class="folderDialog" :close-on-click-modal="false">
			<el-form :model="renameForm" :rules="renameRules" ref="renameForm">
				<el-form-item prop="fileName">
					<el-input v-model="renameForm.fileName" placeholder="请输入文件(夹)名称" @keyup.enter.native="rename" ref="renameInput"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogRenameVisible = false">取 消</el-button>
				<el-button type="primary" @click="rename">确 定</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
import { Loading } from 'element-ui';

import bus from '@/utils/bus.js';

export default {
	name: 'AllFile',
	data() {
		return {
			dialogFolderVisible: false,
			checkAll: false,
			isIndeterminate: false,
			isSearch: 0,
			queryParam: {
				page: 1,
				limit: 100,
				order: 'time',
				desc: 0,
				dir: '/'
			},
			searchParam: {
				page: 1,
				limit: 100,
				order: 'time',
				desc: 1,
				keyword: ''
			},
			list: [],
			count: null,
			isAll: null,
			checkedList: [],
			btnGroup: {
				show: false,
				disabled: false
			},
			content: {
				height: ''
			},
			loading: null,
			folderForm: {
				folderName: ''
			},
			folderRules: {
				folderName: [
					{ required: true, message: '请输入文件(夹)名称', trigger: 'blur' },
					{
						min: 1,
						max: 255,
						message: '文件(夹)名称不能超过255个字节',
						trigger: 'blur'
					}
				]
			},
			// 面包屑导航数组
			breadcrumb: [],
			// 选中的文件列表
			checkList: {
				fileIds: [],
				folderIds: []
			},
			// 选择的文件名称，供重命名使用
			checkedFileName: '',
			dialogRenameVisible: false,
			renameForm: {
				fileName: ''
			},
			renameRules: {
				fileName: [
					{ required: true, message: '请输入文件(夹)名称', trigger: 'blur' },
					{
						min: 1,
						max: 255,
						message: '文件(夹)名称不能超过255个字节',
						trigger: 'blur'
					}
				]
			}
		};
	},
	created() {
		window.addEventListener('resize', this.transferHeight);
		this.transferHeight();
		this.initOrder();
	},
	mounted() {
		this.getList();
	},
	computed: {
		tableTitle() {
			let count = this.checkedList.length;
			if(count === 0){
				return "文件名";
			}
			return `已选中${count}个文件/文件夹`;
		}
	},
	methods: {
		// 上传文件
		uploader() {
			bus.$emit('openUploader', {
				path: this.assembleDirectory(this.queryParam.dir),
				targetPath: this.queryParam.dir
			});
		},
		// 重命名
		rename() {
			this.$refs.renameForm.validate(valid => {
				if (valid) {
					let param = {
						newName: this.renameForm.fileName,
						dir: this.queryParam.dir
					};

					let id, isDir;

					let fileIds = this.checkList.fileIds;
					let folderIds = this.checkList.folderIds;
					if (fileIds.length > 0) {
						id = fileIds[0];
						isDir = 0;
					} else if (folderIds.length > 0) {
						id = folderIds[0];
						isDir = 1;
					} else {
						return;
					}
					let msg = this.$message({
						message: '正在重命名文件，请稍后...',
						iconClass: 'el-icon-loading',
						duration: 0
					});
					// 发送重命名请求
					this.putRequest(`/portal/list/${id}/${isDir}`, param).then(resp => {
						msg.close();
						if (resp) {
							if (resp.status !== 20) {
								this.resetQueryParam();
								this.checkedFileName = '';
								this.checkedList = [];
								this.checkList.fileIds = [];
								this.checkList.folderIds = [];
								this.getList();
								this.dialogRenameVisible = false;
							} else {
								this.$confirm(`${resp.msg}`, '提示', {
									confirmButtonText: '保留两个文件',
									cancelButtonText: '取消',
									type: 'warning'
								})
									.then(() => {
										msg = this.$message({
											message: '正在重命名文件，请稍后...',
											iconClass: 'el-icon-loading',
											duration: 0
										});
										param.type = 'newCopy';
										this.putRequest(`/portal/list/${id}/${isDir}`, param).then(resp => {
											if (resp) {
												msg.close();
												this.resetQueryParam();
												this.checkedFileName = '';
												this.checkedList = [];
												this.checkList.fileIds = [];
												this.checkList.folderIds = [];
												this.getList();
												this.dialogRenameVisible = false;
											}
										});
									})
									.catch(() => {});
							}
						}
					});
				}
			});
		},
		// 重命名弹出框
		openRenameDialog() {
			if (this.$refs.renameForm) {
				this.$refs.renameForm.resetFields();
			}
			this.renameForm.fileName = this.checkedFileName;
			this.dialogRenameVisible = true;
			this.$nextTick(() => {
				let el = this.$refs.renameInput.$el.children[0];
				el.select();
				el.selectionEnd = this.renameForm.fileName.lastIndexOf('.');
			});
		},
		// 组装所在目录
		assembleDirectory(dir) {
			if (dir == '/') {
				return '全部文件';
			}
			let dirArr = dir.split('/');
			return dirArr[dirArr.length - 1];
		},
		// 清空搜索条件
		clearKeyword() {
			this.searchParam.keyword = '';
			this.isSearch = 0;
			this.resetQueryParam();
			this.getList();
		},
		// 条件搜索
		search() {
			if (this.searchParam.keyword) {
				this.list = [];
				this.isSearch = 1;
				this.searchParam.page = 1;
				this.breadcrumb = [`搜索："${this.searchParam.keyword}"`];
				console.log(this.breadcrumb)
				this.getList();
			}
		},
		// 删除选中的文件
		deleteFile() {
			this.$confirm(`确认要把所选文件放入回收站吗?<br>删除的文件可在15天内通过回收站还原`, '删除确认', {
				dangerouslyUseHTMLString: true,
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			})
				.then(() => {
					this.deleteRequest('/portal/list/', this.checkList).then(resp => {
						if (resp) {
							this.resetQueryParam();
							this.checkedList = [];
							this.checkList.fileIds = [];
							this.checkList.folderIds = [];
							this.getList();
						}
					});
				})
				.catch(() => {});
		},
		// 面包屑导航
		goBack(path) {
			if (this.isSearch == 1) {
				this.isSearch = 0;
			}
			if (path) {
				this.queryParam.dir = path;
			} else {
				// 返回上一级
				let index = this.queryParam.dir.lastIndexOf('/');
				if (index === 0) {
					this.queryParam.dir = '/';
				} else {
					this.queryParam.dir = this.queryParam.dir.substr(0, index);
				}
			}
			this.assembleDir(this.queryParam.dir);
			this.resetQueryParam();
			this.getList();
		},
		// 重置参数
		resetQueryParam() {
			this.queryParam.page = 1;
		},
		// 新建文件夹
		createFolder() {
			this.$refs.folderForm.validate(valid => {
				if (valid) {
					let param = {
						folderName: this.folderForm.folderName,
						dir: this.queryParam.dir
					};
					// 发送新增文件夹请求
					this.postRequest('/portal/folders/', param).then(resp => {
						if (resp) {
							this.resetQueryParam();
							this.getList();
							this.dialogFolderVisible = false;
						}
					});
				}
			});
		},
		// 打开新建文件夹弹出框
		openCreateFolderDialog() {
			if (this.$refs.folderForm) {
				this.$refs.folderForm.resetFields();
			}
			this.dialogFolderVisible = true;
			this.$nextTick(() => {
				this.$refs.folderInput.$el.children[0].focus();
			});
		},
		// loading效果
		startLoading() {
			this.loading = Loading.service({ target: '#fileDiv', fullscreen: false });
		},
		// 关闭loading效果
		closeLoading() {
			if (this.loading) {
				this.$nextTick(() => {
					this.loading.close();
					this.loading = null;
				});
			}
		},
		// 排序历史
		initOrder() {
			let order = localStorage.getItem('order');
			let desc = localStorage.getItem('desc');
			if (order) {
				this.queryParam.order = order;
			} else {
				// 设置localStorage
				localStorage.setItem('order', this.queryParam.order);
			}

			if (desc) {
				this.queryParam.desc = desc;
			} else {
				// 设置localStorage
				localStorage.setItem('desc', this.queryParam.desc);
			}
		},
		// 排序
		changeOrder(order) {
			if (this.queryParam.order === order) {
				// 点击的是同一个分类，修改降序和升序
				if (parseInt(this.queryParam.desc) === 1) {
					this.queryParam.desc = 0;
					localStorage.setItem('desc', 0);
				} else {
					this.queryParam.desc = 1;
					localStorage.setItem('desc', 1);
				}
			} else {
				this.queryParam.order = order;
				localStorage.setItem('order', order);
			}
			// 重新查询数据
			this.resetQueryParam();
			this.getList();
		},
		// 打开目录
		openDir(dir) {
			this.isSearch = 0;
			this.queryParam.dir = dir;
			this.queryParam.page = 1;
			this.checkedList = [];
			this.isIndeterminate = false;
			this.assembleDir(dir);
			this.getList();
		},
		// 封装面包屑导航路径
		assembleDir(dir) {
			this.breadcrumb = [];
			let dirArr = dir.split('/');
			let dirLength = dirArr.length;
			let path = '';
			if (dirLength <= 4) {
				for (let i = 0; i < dirLength; i++) {
					if (dirArr[i] !== '') {
						let obj = {};
						obj.name = dirArr[i];
						path += '/' + dirArr[i];
						obj.path = path;
						this.breadcrumb.push(obj);
					}
				}
			} else {
				this.breadcrumb.push({ name: '...', path: 'null' });
				for (let i = dirLength - 4; i < dirLength; i++) {
					path += '/' + dirArr[i];
					this.breadcrumb.push({ name: dirArr[i], path: path });
				}
			}
		},
		// 点击非复选框区域单选
		checkOne(obj) {
			if (this.checkedList.indexOf(obj.id) === -1) {
				this.checkedList = [obj.id];
				if (obj.isDir === 1) {
					this.checkList.folderIds.push(obj.id);
				} else {
					this.checkList.fileIds.push(obj.id);
				}
				this.checkedFileName = obj.name;
			}
		},
		// 点击复选框多选
		checkMore(checked, obj) {
			if (checked) {
				if (obj.isDir === 1) {
					this.checkList.folderIds.push(obj.id);
				} else {
					this.checkList.fileIds.push(obj.id);
				}
				this.checkedFileName = obj.name;
			} else {
				if (obj.isDir === 1) {
					this.checkList.folderIds.splice(this.checkList.folderIds.indexOf(obj.id), 1);
				} else {
					this.checkList.fileIds.splice(this.checkList.folderIds.indexOf(obj.id), 1);
				}
				if (this.checkedList.length === 1) {
					this.list.forEach(item => {
						if (item.id === this.checkedList[0]) {
							this.checkedFileName = item.name;
							return;
						}
					});
				}
			}
		},
		// 全选反选
		handleCheckAllChange(val) {
			if (val) {
				// 全选
				this.checkedList = [];
				this.checkList.folderIds = [];
				this.checkList.fileIds = [];
				for (let index = 0; index < this.list.length; index++) {
					const element = this.list[index];
					this.checkedList.push(element.id);
					if (element.isDir === 1) {
						this.checkList.folderIds.push(element.id);
					} else {
						this.checkList.fileIds.push(element.id);
					}
				}
			} else {
				// 取消全选
				this.checkedList = [];
				this.checkList.folderIds = [];
				this.checkList.fileIds = [];
			}
		},
		// 获取列表数据
		getList(isScroll = false) {
			this.startLoading();
			let param;
			if (this.isSearch == 0) {
				param = this.queryParam;
				param.isSearch = 0;
			} else {
				param = this.searchParam;
				param.isSearch = 1;
			}
			this.getRequest('/portal/list/', param).then(resp => {
				this.closeLoading();
				if (isScroll) {
					this.list.push(...resp.data.list);
				} else {
					this.list = resp.data.list;
				}

				this.count = resp.data.count;
				this.isAll = resp.data.isAll;
			});
		},
		// 禁止时间冒泡
		stopDefault(e) {
			e.stopPropagation();
		},
		// 自适应文件列表高度
		transferHeight() {
			this.content.height = window.innerHeight - 187 + 'px';
			// 刷新滚动条
			this.$nextTick(() => {
				this.$refs['vs'].refresh();
			});
		},
		// 滚动加载数据
		handleScroll(vertical) {
			if (vertical.process === 1 && this.isAll === 0) {
				this.queryParam.page += 1;
				this.getList(true);
				this.$nextTick(() => {
					this.$refs['vs'].refresh();
				});
			}
		}
	},
	watch: {
		checkedList: {
			handler() {
				if (this.checkedList.length > 0) {
					this.btnGroup.show = true;
					if (this.checkedList.length > 1) {
						this.btnGroup.disabled = true;
					} else {
						this.btnGroup.disabled = false;
					}

					if (this.checkedList.length < this.count) {
						this.checkAll = false;
						this.isIndeterminate = true;
					} else {
						this.isIndeterminate = false;
						this.checkAll = true;
					}
				} else {
					this.btnGroup.show = false;
					this.btnGroup.disabled = false;
					this.isIndeterminate = false;
					this.checkAll = false;
				}
			},
			deep: true
		}
	},
	filters: {
		breadcrumbFilter: function(val, deep) {
			if (!val) return '';
			if (deep == 1) {
				if (val.length > 30) {
					return val.substr(0, 30) + '...';
				}
				return val;
			}

			if (deep == 2) {
				if (val.length > 20) {
					return val.substr(0, 10) + '...';
				}
				return val;
			}

			if (deep == 3) {
				if (val.length > 15) {
					return val.substr(0, 8) + '...';
				}
				return val;
			}

			if (deep > 3) {
				if (val.length > 10) {
					return val.substr(0, 5) + '...';
				}
				return val;
			}
		},
		directoryFilter: function(val) {
			if (val.length > 15) {
				return val.substr(0, 15) + '...';
			}

			return val;
		}
	}
};
</script>

<style lang="less">
.el-button [class*='iconfont'] + span {
	margin-left: 5px;
}

.file-fun-area {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 3px 0;

	.fun-btn {
		padding: 11px 10px;

		.btn-group {
			margin-top: -1px;
			margin-left: 10px;

			.el-button--primary:first-child {
				border-right-color: #b3d8ff;
			}

			.el-button--primary:not(:first-child):not(:last-child) {
				border-left-color: #b3d8ff;
				border-right-color: #b3d8ff;
			}

			.el-button--primary:last-child {
				border-left-color: #b3d8ff;
			}
		}

		.plain-btn.is-plain {
			background: #fff;
			font-weight: normal;

			&:hover:not(.is-disabled) {
				background: #409eff;
				color: #fff;
			}

			&:active,
			&:focus {
				color: #409eff;
			}
		}
	}

	.right-fun {
		width: 30%;

		.fun-search {
			float: right;
			width: 80%;
			min-width: 165px;
			max-width: 315px;
			padding-left: 15px;
			padding-right: 13px;
			background: #f1f2f4;
			border-radius: 33px;
			display: flex;
			align-items: center;
			margin-right: 8px;

			.search-input {
				input {
					background: 0 0;
					border: none;
					padding: 0;
				}
			}

			.search-icon {
				font-weight: 700;
				cursor: pointer;
				color: #687176;

				&:hover {
					color: #409eff;
				}
			}
		}

		.icon-fun-area {
			float: right;
			display: flex;
			height: 30px;
			align-items: center;

			.iconfont {
				cursor: pointer;
				color: #687176;
			}

			.icon-paixu {
				font-size: 24px;
				line-height: 30px;
				margin-right: 10px;
			}

			.icon-viewgallery {
				font-size: 18px;
				margin-right: 20px;
				font-weight: 600;
			}
		}
	}
}

.file-container {
	.title-info {
		padding-left: 16px;
		height: 16px;
		line-height: 16px;
		margin-bottom: 5px;

		span {
			font-size: 12px;

			&.textSpan {
				color: #666;
			}
		}

		.menu-tag {
			float: left;

			.breadcrumbUl {
				li {
					float: left;
				}

				.breadcrumbTxtSpan {
					font-size: 12px;
					vertical-align: middle;
					color: #666;
				}
			}
		}

		.load-count {
			float: right;
			margin-right: 20px;
		}
	}

	.file-list {
		.list-title {
			.title-ul {
				height: 36px;
				line-height: 36px;
				color: #888;
				border-bottom: 1px solid rgba(179, 216, 255, 0.2);

				li {
					float: left;
					cursor: pointer;
					font-size: 12px;
					padding-left: 10px;
					user-select: none;

					&:hover {
						background-color: rgba(179, 216, 255, 0.2);
					}
				}

				.sortIcon {
					font-weight: 700;
					color: #409eff;
					margin-left: 5px;
				}
			}
		}

		.list-content {
			height: auto;
			font: 12px/1.5 tahoma, arial !important;

			.content-row {
				height: 44px;
				line-height: 44px;
				border-bottom: 1px solid rgba(179, 216, 255, 0.2);
				white-space: nowrap;
				text-overflow: ellipsis;
				position: relative;
				cursor: pointer;

				&.first:hover::before {
					top: 0;
				}

				&:hover {
					background: rgba(179, 216, 255, 0.2);
					border-bottom: 1px solid rgba(179, 216, 255, 0.6);

					&::before {
						content: '';
						border-top: 1px solid rgba(179, 216, 255, 0.6);
						position: absolute;
						top: -1px;
						display: block;
						width: 100%;
						z-index: 1;
						visibility: visible;
					}
				}

				.left {
					float: left;
				}

				.content-flex {
					display: flex;
					align-items: center;
				}

				.content-col {
					height: 44px;
					line-height: 44px;
					padding-left: 10px;
					font-family: tahoma, arial !important;
					margin-top: -1px;

					.txtSpan {
						color: #424e67;
						text-decoration: none;
						display: inline-block;
						cursor: default;

						a {
							color: #424e67;

							&:hover {
								color: #09aaff;
							}
						}

						.dirLink {
							text-decoration: underline;
						}
					}

					img {
						width: 25px;
						height: 25px;
						cursor: default;
					}

					.el-checkbox {
						margin-top: 4px;
					}
				}
			}
		}
	}
}

.folderDialog {
	.el-dialog__body {
		padding-top: 10px;
		padding-bottom: 0;
	}

	.el-dialog__footer {
		padding-top: 0;
		padding-bottom: 15px;
	}
}
</style>
