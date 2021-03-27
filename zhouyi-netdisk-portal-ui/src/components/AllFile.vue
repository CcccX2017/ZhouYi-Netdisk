<template>
	<div>
		<div class="file-fun-area">
			<div class="fun-btn">
				<el-button type="primary" icon="el-icon-upload">上传</el-button>
				<el-button type="primary" plain icon="el-icon-folder-add" class="plain-btn">新建文件夹</el-button>
				<el-button-group class="btn-group">
					<el-button type="primary" plain icon="iconfont icon-share" class="plain-btn">分享</el-button>
					<el-button type="primary" plain icon="el-icon-download" class="plain-btn">下载</el-button>
					<el-button type="primary" plain icon="el-icon-delete" class="plain-btn">删除</el-button>
					<el-button type="primary" plain class="plain-btn">重命名</el-button>
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
				<span class="load-count">已加载10个</span>
			</div>
			<div class="file-list">
				<div class="list-title">
					<ul class="title-ul">
						<li data-key="name" style="width: 60%;padding-left: 16px;">
							<el-checkbox></el-checkbox>
							<span style="padding-left: 10px;">文件名</span>
						</li>
						<li data-key="size" style="width: 16%;">大小</li>
						<li data-key="time" style="width: 23%;">修改日期</li>
					</ul>
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
			searchText: ''
		};
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
			&:hover {
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
					&:hover {
						background-color: rgba(179, 216, 255, 0.2);
					}
				}
			}
		}
	}
}
</style>
