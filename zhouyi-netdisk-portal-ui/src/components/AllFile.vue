<template>
	<div>
		<div class="file-fun-area">
			<div class="fun-btn">
				<el-button type="primary" icon="el-icon-upload">上传</el-button>
				<el-button type="primary" plain icon="el-icon-folder-add" class="plain-btn">新建文件夹</el-button>
				<el-button-group class="btn-group" v-if="btnGroup.show">
					<el-button type="primary" plain icon="iconfont icon-share" class="plain-btn">分享</el-button>
					<el-button type="primary" plain icon="el-icon-download" class="plain-btn">下载</el-button>
					<el-button type="primary" plain icon="el-icon-delete" class="plain-btn">删除</el-button>
					<el-button type="primary" plain class="plain-btn" :disabled="btnGroup.disabled">重命名</el-button>
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
					<el-input size="small" class="search-input" placeholder="搜索您的文件" v-model="searchText"></el-input>
					<i class="el-icon-search search-icon"></i>
				</div>
			</div>
		</div>
		<div class="file-container">
			<div class="title-info clearfix">
				<span class="menu-tag">全部文件</span>
				<span class="load-count" v-if="isAll == 1">已全部加载，共{{ count }}个</span>
				<span class="load-count" v-else>已加载{{ count }}个</span>
			</div>
			<div class="file-list">
				<div class="list-title">
					<ul class="title-ul">
						<li data-key="name" style="width: 60%;padding-left: 16px;" @click.stop="changeOrder('name')">
							<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange"></el-checkbox>
							<span style="padding-left: 10px;">文件名</span>
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
				</div>
				<div class="list-content" :style="content">
					<vue-scroll ref="vs" style="top: -1px;border: none;" @handle-scroll="handleScroll">
						<template v-for="(item, index) in list">
							<div class="content-row clearfix" :key="item.id" @click.prevent="checkOne(item.id)" :class="index == 0 ? 'first' : ''">
								<div class="left content-col content-flex" style="width: 60%;padding-left: 16px;">
									<el-checkbox-group v-model="checkedList">
										<el-checkbox :label="item.id" @click.native="stopDefault($event)">{{ '' }}</el-checkbox>
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
								<div class="left content-col" style="width: 23%">
									<span class="txtSpan">{{ item.gmtModified }}</span>
								</div>
							</div>
						</template>
					</vue-scroll>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: 'AllFile',
	data() {
		return {
			checkAll: false,
			isIndeterminate: false,
			searchText: '',
			queryParam: {
				page: 1,
				limit: 100,
				order: 'time',
				desc: 0,
				dir: '/'
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
			}
		};
	},
	created() {
		window.addEventListener('resize', this.transferHeight);
		this.transferHeight();
		this.initOrder();
		this.getList();
	},
	methods: {
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
				if (this.queryParam.desc === 1) {
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
			this.queryParam.page = 1;
			this.getList();
		},
		// 打开目录
		openDir(dir) {
			console.log(dir);
		},
		checkOne(val) {
			if (this.checkedList.indexOf(val) === -1) {
				this.checkedList = [val];
			}
		},
		handleCheckAllChange(val) {
			if (val) {
				// 全选
				for (let index = 0; index < this.list.length; index++) {
					const element = this.list[index];
					this.checkedList.push(element.id);
				}
			} else {
				// 取消全选
				this.checkedList = [];
			}
		},
		getList() {
			this.getRequest('/portal/list/', this.queryParam).then(resp => {
				this.list = resp.data.list;
				this.count = resp.data.count;
				this.isAll = resp.data.isAll;
			});
		},
		stopDefault(e) {
			e.stopPropagation();
		},
		transferHeight() {
			this.content.height = window.innerHeight - 187 + 'px';
			// 刷新滚动条
			this.$nextTick(() => {
				this.$refs['vs'].refresh();
			});
		},
		handleScroll(vertical) {
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
			&:hover:not(.is-disabled) {
				background: #409eff;
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
			color: #666;
		}
		.menu-tag {
			float: left;
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
</style>
